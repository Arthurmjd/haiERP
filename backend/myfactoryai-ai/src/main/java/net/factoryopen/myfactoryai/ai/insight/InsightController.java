package net.factoryopen.myfactoryai.ai.insight;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 数据洞察控制器
 * 提供洞察报告生成接口，支持动态选择大模型
 *
 * @author factoryopen
 */
@Slf4j
@RestController
@RequestMapping("/ai/insight")
public class InsightController {

    @Autowired
    private InsightDataService insightDataService;

    /**
     * 生成数据洞察报告
     * @param request 请求体，包含：
     *                dataSummary（数据摘要文本，必填）
     *                dimensions（可选的分析维度列表）
     *                model（可选，模型名称，默认 qwen-plus）
     * @return 标准响应格式
     */
    @PostMapping("/generate")
    public Map<String, Object> generateInsight(@RequestBody Map<String, Object> request) {
        log.info("接收到洞察生成请求，参数: {}", request);
        long start = System.currentTimeMillis();

        // 提取参数
        String dataSummary = (String) request.get("dataSummary");
        List<String> dimensions = (List<String>) request.get("dimensions");
        String model = (String) request.getOrDefault("model", "qwen-plus");

        // 参数校验
        if (dataSummary == null || dataSummary.trim().isEmpty()) {
            log.warn("数据摘要为空");
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("errorMessage", "数据摘要不能为空");
            return error;
        }

        try {
            // 调用服务生成洞察，传入模型参数
            String insight = insightDataService.generateInsight(dataSummary, dimensions, model);
            long cost = System.currentTimeMillis() - start;
            log.info("洞察报告生成成功，耗时 {} ms，模型: {}", cost, model);

            Map<String, Object> result = new HashMap<>();
            result.put("success", true);
            result.put("insight", insight);
            return result;
        } catch (Exception e) {
            log.error("洞察报告生成失败，模型: {}", model, e);
            Map<String, Object> error = new HashMap<>();
            error.put("success", false);
            error.put("errorMessage", "洞察报告生成失败: " + e.getMessage());
            return error;
        }
    }
}