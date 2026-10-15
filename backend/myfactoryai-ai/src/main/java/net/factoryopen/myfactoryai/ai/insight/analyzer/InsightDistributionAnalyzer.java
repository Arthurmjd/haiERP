package net.factoryopen.myfactoryai.ai.insight.analyzer;

import net.factoryopen.myfactoryai.ai.insight.InsightAnalyzer;
import net.factoryopen.myfactoryai.ai.insight.InsightDataSummary;
import org.springframework.stereotype.Component;

/**
 * 分布分析器
 * <p>分析类型：分布分析（Distribution Analysis）</p>
 * <p>适用场景：了解数据的集中趋势、离散程度、偏度峰度，判断数据分布形态（正态、均匀、偏态等）</p>
 * <p>适用要求：至少需要2个数据点</p>
 *
 * @author factoryopen
 */
@Component
public class InsightDistributionAnalyzer extends InsightAnalyzer {

    @Override
    public String getDimensionCode() {
        return "DISTRIBUTION";
    }

    @Override
    public String getDimensionNameCn() {
        return "分布分析";
    }

    @Override
    public String getDescription() {
        return "分析数据分布形态，包括中心趋势、离散程度、偏度和峰度，适用于了解数据的整体分布特征。";
    }

    @Override
    public boolean canAnalyze(InsightDataSummary summary) {
        if (summary.getRowCount() < 2) {
            log.debug("分布分析不适合：数据行数={} < 2", summary.getRowCount());
            return false;
        }
        return true;
    }

    @Override
    public String analyze(InsightDataSummary summary) {
        log.info("[分布分析] 开始分析，数据行数={}", summary.getRowCount());
        long start = System.currentTimeMillis();

        // TODO: 调用实际分析逻辑（如统计计算或大模型）
        String result = getAnalysisResult(summary);

        log.info("[分布分析] 分析完成，耗时 {} ms", System.currentTimeMillis() - start);
        return result;
    }

    private String getAnalysisResult(InsightDataSummary summary) {
        // 示例结果，实际应根据数据动态生成
        return "数据呈现单峰分布，峰值位于中间类别，两侧对称，离散程度较低。";
    }

    @Override
    public String getUnsuitableReason(InsightDataSummary summary) {
        if (summary.getRowCount() < 2) {
            return "数据行数不足2条，无法分析分布形态";
        }
        return "数据特征不满足分布分析要求";
    }
}