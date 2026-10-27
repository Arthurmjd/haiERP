package net.factoryopen.myfactoryai.web.controller.erp;

import net.factoryopen.myfactoryai.erp.support.ErpErrorCode;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import com.ruoyi.common.utils.SecurityUtils;
import com.ruoyi.framework.web.service.PermissionService;
import net.factoryopen.myfactoryai.erp.mapper.ErpAiReadMapper;
import net.factoryopen.myfactoryai.erp.support.ErpAiPrompts;
import net.factoryopen.myfactoryai.web.ai.DeepSeekConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/ai/erp")
public class ErpAiQuestionController {
    public record Question(String mode,String question) {}

    private static final Logger log = LoggerFactory.getLogger(ErpAiQuestionController.class);

    /** 每用户每分钟允许的 AI 提问次数（DeepSeek 按 token 计费，防止刷量） */
    private static final int ASK_RATE_LIMIT_PER_MINUTE = 10;

    /** 查询范围 → 对应的数据权限。范围是否合法以 ErpAiPrompts.supports 为准 */
    private static final Map<String,String> MODE_PERMISSIONS = Map.of(
        "orders","sd:order:list",
        "inventory","st:inventory:list",
        "shortage","pu:suggestion:list",
        "cost","dashboard:styleCost:list",
        "ontime","dashboard:ontime:list");

    private final ErpAiReadMapper mapper;
    private final PermissionService permissions;
    private final DeepSeekConfig llm;
    private final StringRedisTemplate redis;

    public ErpAiQuestionController(ErpAiReadMapper mapper,PermissionService permissions,DeepSeekConfig llm,
                                   StringRedisTemplate redis) {
        this.mapper=mapper; this.permissions=permissions; this.llm=llm; this.redis=redis;
    }

    @PreAuthorize("@ss.hasPermi('dashboard:overview:list')")
    @PostMapping("/ask")
    public AjaxResult ask(@RequestBody Question request) {
        String rateKey = "ai:ask:rate:" + SecurityUtils.getUserId();
        Long asked = redis.opsForValue().increment(rateKey);
        if (asked != null && asked == 1L) redis.expire(rateKey, Duration.ofMinutes(1));
        if (asked != null && asked > ASK_RATE_LIMIT_PER_MINUTE) {
            throw new ServiceException("提问过于频繁，请稍后再试", ErpErrorCode.INVALID_STATE.code());
        }
        if (request == null || request.question() == null || request.question().isBlank()
                || request.question().length() > 500) throw new ServiceException("问题长度应为 1 到 500 字", ErpErrorCode.INVALID_PARAM.code());
        if (!ErpAiPrompts.supports(request.mode())) throw new ServiceException("查询范围不合法", ErpErrorCode.INVALID_PARAM.code());
        String permission = MODE_PERMISSIONS.get(request.mode());
        if (!permissions.hasPermi(permission)) throw new ServiceException("没有该业务数据权限", ErpErrorCode.DATA_SCOPE_DENIED.code());
        List<Map<String,Object>> rows = switch (request.mode()) {
            case "orders" -> mapper.orders();
            case "inventory" -> mapper.inventory();
            case "shortage" -> mapper.shortage();
            case "cost" -> mapper.cost();
            case "ontime" -> mapper.ontime();
            default -> throw new ServiceException("查询范围不合法", ErpErrorCode.INVALID_PARAM.code());
        };
        String scopeTitle = ErpAiPrompts.scope(request.mode()) == null
                ? request.mode() : ErpAiPrompts.scope(request.mode()).title();
        String fallback = rows.isEmpty()
                ? "「" + scopeTitle + "」范围内暂无记录。"
                : "已按「" + scopeTitle + "」查出 " + rows.size() + " 条记录（非全量），请核对下方数据。";
        String answer = fallback;
        boolean modelUsed = false;
        if (llm.isConfigured()) {
            try {
                Map<String,Object> body = Map.of(
                    "model",llm.getModel(),
                    "stream",false,
                    // 业务分析要稳定复现，温度压低；答案限长以控成本，提示词里也要求 200 字内
                    "temperature",0.3,
                    // 必须关掉思考模式：deepseek-v4 默认会先输出 reasoning_content，
                    // 推理 token 同样计入 max_tokens，预算不够时正文 content 会是空的，
                    // 表现就是"接口 200 但没有回答"。这个任务是按给定记录做归纳，不需要思考链。
                    "thinking",Map.of("type","disabled"),
                    "max_tokens",800,
                    "messages",List.of(
                        Map.of("role","system","content",ErpAiPrompts.QA_SYSTEM),
                        Map.of("role","user","content",
                            ErpAiPrompts.userMessage(request.mode(),request.question(),rows,LocalDate.now()))));
                Map<?,?> response = llm.deepSeekWebClient().post().bodyValue(body).retrieve()
                        .bodyToMono(Map.class).block(Duration.ofSeconds(10));
                List<?> choices = response == null ? null : (List<?>) response.get("choices");
                Map<?,?> first = choices == null || choices.isEmpty() ? null : (Map<?,?>) choices.get(0);
                Map<?,?> message = first == null ? null : (Map<?,?>) first.get("message");
                String content = message == null ? null : (String) message.get("content");
                if (content != null && !content.isBlank()) { answer=content.trim(); modelUsed=true; }
                else log.warn("[AI] 模型返回空内容，降级为数据摘要。finish_reason={} 是否含推理={}",
                        first == null ? null : first.get("finish_reason"),
                        message != null && message.get("reasoning_content") != null);
            } catch (RuntimeException e) {
                // 静默降级会让"配了 key 却没回答"变得无法排查，这里至少留一条可查的日志
                log.warn("[AI] 调用模型失败，降级为数据摘要：{}", e.getMessage());
            }
        }
        return AjaxResult.success(Map.of("answer",answer,"rows",rows,"source",request.mode(),
                "dataTime",LocalDateTime.now().toString(),"modelUsed",modelUsed));
    }
}
