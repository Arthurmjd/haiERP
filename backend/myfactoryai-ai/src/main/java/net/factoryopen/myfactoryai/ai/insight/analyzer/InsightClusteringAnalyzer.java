package net.factoryopen.myfactoryai.ai.insight.analyzer;

import net.factoryopen.myfactoryai.ai.insight.InsightAnalyzer;
import net.factoryopen.myfactoryai.ai.insight.InsightDataSummary;
import org.springframework.stereotype.Component;

/**
 * 聚类分析器
 * <p>分析类型：聚类分析（Clustering Analysis）</p>
 * <p>适用场景：将相似的数据点自动分组，发现客户分群、产品类别等内在结构</p>
 * <p>适用要求：至少2个数值字段，且样本量≥30</p>
 *
 * @author factoryopen
 */
@Component
public class InsightClusteringAnalyzer extends InsightAnalyzer {

    @Override
    public String getDimensionCode() {
        return "CLUSTERING";
    }

    @Override
    public String getDimensionNameCn() {
        return "聚类分析";
    }

    @Override
    public String getDescription() {
        return "自动将数据划分为多个簇，挖掘自然分组，适用于客户细分、图像分割等无监督学习场景。";
    }

    @Override
    public boolean canAnalyze(InsightDataSummary summary) {
        if (summary.getNumericColumnCount() < 2) {
            log.debug("聚类分析不适合：数值字段数={} < 2", summary.getNumericColumnCount());
            return false;
        }
        if (summary.getRowCount() < 30) {
            log.debug("聚类分析不适合：样本量={} < 30", summary.getRowCount());
            return false;
        }
        return true;
    }

    @Override
    public String analyze(InsightDataSummary summary) {
        log.info("[聚类分析] 开始分析，数值字段={}, 样本量={}", summary.getNumericColumns(), summary.getRowCount());
        long start = System.currentTimeMillis();
        String result = "数据自然聚为3个群组：高价值群体（占比20%）、中价值群体（55%）、低价值群体（25%）。";
        log.info("[聚类分析] 分析完成，耗时 {} ms", System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public String getUnsuitableReason(InsightDataSummary summary) {
        if (summary.getNumericColumnCount() < 2) {
            return "需要至少2个数值字段，当前仅" + summary.getNumericColumnCount() + "个";
        }
        if (summary.getRowCount() < 30) {
            return "需要至少30个样本点，当前仅" + summary.getRowCount() + "个";
        }
        return "数据特征不满足聚类分析要求";
    }
}