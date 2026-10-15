package net.factoryopen.myfactoryai.ai.insight;

import java.util.List;

/**
 * 数据洞察服务接口
 */
public interface InsightDataService {

    /**
     * 生成数据洞察报告
     * @param dataSummaryText 数据摘要文本（含行数、字段、部分数据等）
     * @param requestedDimensions 请求重点分析的维度列表（可选，如为空则自动判断）
     * @param model 使用的大模型名称，如 qwen-plus, deepseek-v4-flash 等
     * @return 格式化后的洞察报告文本（Markdown结构）
     */
    String generateInsight(String dataSummaryText, List<String> requestedDimensions, String model);
}