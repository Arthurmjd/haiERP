package net.factoryopen.myfactoryai.web.controller.erp;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.exception.ServiceException;
import net.factoryopen.myfactoryai.ai.config.LLMConfig;
import net.factoryopen.myfactoryai.erp.mapper.ErpReadMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ErpAlertController {
    private final ErpReadMapper mapper;
    private final LLMConfig llm;
    public ErpAlertController(ErpReadMapper mapper,LLMConfig llm) { this.mapper=mapper; this.llm=llm; }

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
        if (!llm.isDeepseekConfigured())
            return AjaxResult.success(Map.of("alertId",id,"explanation",fallback,"modelUsed",false));
        try {
            Map<String,Object> body = Map.of("model",llm.getDeepseekModel(),"stream",false,"messages",List.of(
                    Map.of("role","system","content","你是服装工厂 ERP 运营助手。仅根据给定规则预警，写两句简短中文：说明风险和下一步核查。不推断未提供的事实，不给自动执行指令。"),
                    Map.of("role","user","content",fallback)));
            Map<?,?> response = llm.getWebClient("deepseek").post().bodyValue(body).retrieve()
                    .bodyToMono(Map.class).block(Duration.ofSeconds(8));
            List<?> choices = response == null ? null : (List<?>)response.get("choices");
            Map<?,?> first = choices == null || choices.isEmpty() ? null : (Map<?,?>)choices.get(0);
            Map<?,?> message = first == null ? null : (Map<?,?>)first.get("message");
            String content = message == null ? null : (String)message.get("content");
            if (content != null && !content.isBlank()) return AjaxResult.success(Map.of("alertId",id,"explanation",content.trim(),"modelUsed",true));
        } catch (RuntimeException ignored) { /* Rules remain available when the model is offline. */ }
        return AjaxResult.success(Map.of("alertId",id,"explanation",fallback,"modelUsed",false));
    }
}
