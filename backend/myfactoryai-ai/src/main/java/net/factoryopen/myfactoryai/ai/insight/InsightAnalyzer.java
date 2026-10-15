package net.factoryopen.myfactoryai.ai.insight;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据洞察分析器抽象类
 * <p>所有分析维度需继承此类，实现具体的分析逻辑</p>
 *
 * @author factoryopen
 */
public abstract class InsightAnalyzer {
    protected final Logger log = LoggerFactory.getLogger(getClass());

    /**
     * 获取分析维度代码（大写英文，如 DISTRIBUTION）
     */
    public abstract String getDimensionCode();

    /**
     * 获取分析维度中文名称（如 分布分析）
     */
    public abstract String getDimensionNameCn();

    /**
     * 获取该分析器的简要说明（适用于什么场景）
     */
    public abstract String getDescription();

    /**
     * 判断当前数据是否适合该分析
     * @param summary 数据摘要
     * @return true-适合，false-不适合
     */
    public abstract boolean canAnalyze(InsightDataSummary summary);

    /**
     * 执行分析，返回详细的洞察文本（不含标题）
     * 前置条件：canAnalyze 必须返回 true
     * @param summary 数据摘要
     * @return 洞察内容字符串
     */
    public abstract String analyze(InsightDataSummary summary);

    /**
     * 当不适合分析时，返回不适合的原因（用于报告中的说明）
     * @param summary 数据摘要
     * @return 原因描述
     */
    public String getUnsuitableReason(InsightDataSummary summary) {
        return "数据特征不满足分析要求";
    }
}