package net.factoryopen.myfactoryai.ai.insight;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import net.factoryopen.myfactoryai.ai.config.LLMConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@Service
public class InsightDataServiceImpl implements InsightDataService {

    private static final List<Dimension> ALL_DIMENSIONS = List.of(
            new Dimension("TREND", "趋势分析", "需要时间序列或有序索引"),
            new Dimension("COMPARISON", "对比分析", "需要至少两个类别"),
            new Dimension("DISTRIBUTION", "分布分析", "需要单数值字段分布"),
            new Dimension("ANOMALY", "异常检测", "需要至少10个样本"),
            new Dimension("CORRELATION", "相关性分析", "需要两个数值字段"),
            new Dimension("CLUSTERING", "聚类分析", "需要多维数值且样本>10"),
            new Dimension("FORECASTING", "预测分析", "需要时间序列且≥5点"),
            new Dimension("PERIODICITY", "周期性分析", "需要时间序列跨度>2周期"),
            new Dimension("FUNNEL", "漏斗/流失分析", "需要有序步骤转化"),
            new Dimension("PARETO", "贡献度分析", "需要类别及对应数值"),
            new Dimension("SENSITIVITY", "敏感性分析", "需要输入输出变量")
    );

    @Autowired
    private LLMConfig llmConfig;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public String generateInsight(String dataSummaryText, List<String> requestedDimensions, String model) {
        log.info("生成洞察，模型: {}", model);
        String prompt = buildPrompt(dataSummaryText, requestedDimensions);
        String provider = model.startsWith("deepseek") ? "deepseek" : "dashscope";
        WebClient client = llmConfig.getWebClient(provider);

        Object requestBody = buildRequestBody(provider, model, prompt);
        String responseJson = client.post()
                .bodyValue(requestBody)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (responseJson == null) return "洞察生成失败：空响应";
        String insight = extractContent(responseJson, provider);
        return insight != null ? insight : "生成失败";
    }

    private String buildPrompt(String dataSummary, List<String> requestedDimensions) {
        StringBuilder sb = new StringBuilder();
        sb.append("你是数据分析专家。根据以下数据，完成三项任务，**必须严格按照 Markdown 格式输出**。\n\n");
        sb.append("## 数据摘要\n").append(dataSummary).append("\n\n");

        sb.append("## 预定义分析维度（共11种）\n");
        for (Dimension d : ALL_DIMENSIONS) {
            sb.append("- **").append(d.code).append("**（").append(d.nameCn).append("）：").append(d.description).append("\n");
        }
        sb.append("\n");

        if (requestedDimensions != null && !requestedDimensions.isEmpty()) {
            sb.append("## 用户重点关注维度\n").append(String.join(", ", requestedDimensions)).append("\n\n");
        }

        // 任务1：表格 - 强制每行4个竖线，并对齐
        sb.append("## 任务1：维度适用性分析\n");
        sb.append("请生成以下 Markdown 表格，**每行必须恰好有4个竖线（|）**，且竖线两侧各有一个空格：\n\n");
        sb.append("| 维度代码 | 维度中文名 | 是否适合 | 原因/说明 |\n");
        sb.append("|----------|------------|----------|------------|\n");
        sb.append("| TREND | 趋势分析 | 不适合 | 无时间序列 |\n");
        sb.append("| COMPARISON | 对比分析 | 适合 | 多类别可对比 |\n");
        sb.append("| DISTRIBUTION | 分布分析 | 适合 | 单数值分布 |\n");
        sb.append("| ANOMALY | 异常检测 | 不适合 | 样本量＜10 |\n");
        sb.append("| CORRELATION | 相关性分析 | 不适合 | 缺双数值字段 |\n");
        sb.append("| CLUSTERING | 聚类分析 | 不适合 | 样本量少 |\n");
        sb.append("| FORECASTING | 预测分析 | 不适合 | 非时间序列 |\n");
        sb.append("| PERIODICITY | 周期性分析 | 不适合 | 无等距时间轴 |\n");
        sb.append("| FUNNEL | 漏斗/流失分析 | 不适合 | 缺步骤顺序 |\n");
        sb.append("| PARETO | 贡献度分析 | 适合 | 可算累计贡献 |\n");
        sb.append("| SENSITIVITY | 敏感性分析 | 不适合 | 缺输入输出 |\n");
        sb.append("\n");
        sb.append("**要求**：\n");
        sb.append("- “是否适合”列只能填“适合”或“不适合”，**不要添加任何注释**。\n");
        sb.append("- “原因/说明”列不超过12字，必须放在第四列。\n");
        sb.append("- 表格前后不要添加多余空行或代码块标记。\n\n");

        // 任务2：详细洞察（三级标题）
        sb.append("## 任务2：详细洞察报告\n");
        sb.append("对上述表格中 **“适合”** 的维度，按以下格式输出（每个维度一段）：\n");
        sb.append("### 【维度代码 维度中文名洞察】\n");
        sb.append("（具体洞察内容，2-3句话）\n\n");

        // 任务3：总结
        sb.append("## 任务3：综合总结\n");
        sb.append("- 第一段：核心数据特征和主要发现。\n");
        sb.append("- 第二段：给业务人员的具体建议。\n\n");
        sb.append("严格按以上 Markdown 输出，不要输出任何额外解释。");
        return sb.toString();
    }

    private Object buildRequestBody(String provider, String model, String prompt) {
        if ("dashscope".equals(provider)) {
            return Map.of(
                    "model", model,
                    "stream", false,
                    "input", Map.of("messages", List.of(
                            Map.of("role", "system", "content", "你是专家，必须严格按照 Markdown 格式输出。"),
                            Map.of("role", "user", "content", prompt)
                    )),
                    "parameters", Map.of("result_format", "message")
            );
        } else {
            return Map.of(
                    "model", model,
                    "stream", false,
                    "messages", List.of(
                            Map.of("role", "system", "content", "你是专家，必须严格按照 Markdown 格式输出。"),
                            Map.of("role", "user", "content", prompt)
                    )
            );
        }
    }

    private String extractContent(String responseJson, String provider) {
        try {
            Map<String, Object> root = objectMapper.readValue(responseJson, Map.class);
            if ("dashscope".equals(provider)) {
                Map<String, Object> output = (Map<String, Object>) root.get("output");
                List<Map<String, Object>> choices = (List<Map<String, Object>>) output.get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            } else {
                List<Map<String, Object>> choices = (List<Map<String, Object>>) root.get("choices");
                Map<String, Object> message = (Map<String, Object>) choices.get(0).get("message");
                return (String) message.get("content");
            }
        } catch (Exception e) {
            log.error("解析响应失败", e);
            return null;
        }
    }

    private static class Dimension {
        String code, nameCn, description;
        Dimension(String code, String nameCn, String description) {
            this.code = code; this.nameCn = nameCn; this.description = description;
        }
    }
}