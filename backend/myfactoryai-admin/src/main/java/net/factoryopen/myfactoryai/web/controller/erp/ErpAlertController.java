package net.factoryopen.myfactoryai.web.controller.erp;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import net.factoryopen.myfactoryai.erp.mapper.ErpReadMapper;
import net.factoryopen.myfactoryai.erp.support.ErpAiPrompts;
import net.factoryopen.myfactoryai.web.ai.DeepSeekConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErpAlertController {
    private static final Logger log = LoggerFactory.getLogger(ErpAlertController.class);
    private final ErpReadMapper mapper;
    private final DeepSeekConfig llm;
    public ErpAlertController(ErpReadMapper mapper,DeepSeekConfig llm) { this.mapper=mapper; this.llm=llm; }

    @PreAuthorize("@ss.hasPermi('dashboard:alerts:list')")
    @GetMapping("/erp/dashboard/alerts")
    public AjaxResult alerts() { return AjaxResult.success(mapper.alerts()); }

    @PreAuthorize("@ss.hasPermi('dashboard:alerts:explain')")
    @PostMapping("/ai/alerts/explain")
    public AjaxResult explain(@RequestBody Map<String,String> input) {
        String id = input == null ? null : input.get("alertId");
        if (id == null || id.length() > 80) throw new ServiceException("预警 ID 不合法",1001);
        Map<String,Object> alert = mapper.alerts().stream().filter(item -> id.equals(item.get("id"))).findFirst()
                .orElseThrow(() -> new ServiceException("预警不存在或已解除",1001));
        String fallback = "shortage".equals(alert.get("alertType"))
                ? "物料 " + alert.get("subject") + " 当前净缺口 " + alert.get("triggerValue") + alert.get("unit") + "。请核对在途采购和生产计划。"
                : "订单 " + alert.get("subject") + " 已逾期 " + alert.get("triggerValue") + " 天且尚未发齐。请核对工单与成品库存。";
        if (!llm.isConfigured())
            return AjaxResult.success(Map.of("alertId",id,"explanation",fallback,"modelUsed",false));
        try {
            Map<String,Object> body = Map.of(
                    "model",llm.getModel(),
                    "stream",false,
                    "temperature",0.3,
                    // 同业务问答：v4 默认带思考，推理 token 会挤占 max_tokens 导致正文为空
                    "thinking",Map.of("type","disabled"),
                    "max_tokens",400,
                    "messages",List.of(
                        Map.of("role","system","content",ErpAiPrompts.ALERT_SYSTEM),
                        Map.of("role","user","content",ErpAiPrompts.alertMessage(alert,fallback))));
            Map<?,?> response = llm.deepSeekWebClient().post().bodyValue(body).retrieve()
                    .bodyToMono(Map.class).block(Duration.ofSeconds(10));
            List<?> choices = response == null ? null : (List<?>)response.get("choices");
            Map<?,?> first = choices == null || choices.isEmpty() ? null : (Map<?,?>)choices.get(0);
            Map<?,?> message = first == null ? null : (Map<?,?>)first.get("message");
            String content = message == null ? null : (String)message.get("content");
            if (content != null && !content.isBlank()) return AjaxResult.success(Map.of("alertId",id,"explanation",content.trim(),"modelUsed",true));
            log.warn("[AI] 预警解读返回空内容，使用规则文案。finish_reason={}", first == null ? null : first.get("finish_reason"));
        } catch (RuntimeException e) {
            log.warn("[AI] 预警解读调用模型失败，使用规则文案：{}", e.getMessage());
        }
        return AjaxResult.success(Map.of("alertId",id,"explanation",fallback,"modelUsed",false));
    }
}
