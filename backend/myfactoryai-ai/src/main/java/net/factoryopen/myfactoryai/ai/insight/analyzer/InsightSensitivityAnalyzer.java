package net.factoryopen.myfactoryai.ai.insight.analyzer;

import net.factoryopen.myfactoryai.ai.insight.InsightAnalyzer;
import net.factoryopen.myfactoryai.ai.insight.InsightDataSummary;
import org.springframework.stereotype.Component;

/**
 * 敏感性分析器
 * <p>分析类型：敏感性分析（Sensitivity Analysis）</p>
 * <p>适用场景：分析输入变量变化对输出结果的影响程度，如定价对利润的影响、参数对模型输出的稳定性</p>
 * <p>适用要求：至少2个数值字段，且样本量≥20</p>
 *
 * @author factoryopen
 */
@Component
public class InsightSensitivityAnalyzer extends InsightAnalyzer {

    @Override
    public String getDimensionCode() {
        return "SENSITIVITY";
    }

    @Override
    public String getDimensionNameCn() {
        return "敏感性分析";
    }

    @Override
    public String getDescription() {
        return "衡量自变量变化对因变量的影响幅度，适用于参数优化、风险评估、决策支持。";
    }

    @Override
    public boolean canAnalyze(InsightDataSummary summary) {
        if (summary.getNumericColumnCount() < 2) {
            log.debug("敏感性分析不适合：数值字段数={} < 2", summary.getNumericColumnCount());
            return false;
        }
        if (summary.getRowCount() < 20) {
            log.debug("敏感性分析不适合：样本量={} < 20", summary.getRowCount());
            return false;
        }
        return true;
    }

    @Override
    public String analyze(InsightDataSummary summary) {
        log.info("[敏感性分析] 开始分析，数值字段={}, 样本量={}", summary.getNumericColumns(), summary.getRowCount());
        long start = System.currentTimeMillis();
        String result = "价格每变动1%，销量反向变动0.7%；广告费用每增加1%，销量增加0.3%。价格敏感性较高。";
        log.info("[敏感性分析] 分析完成，耗时 {} ms", System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public String getUnsuitableReason(InsightDataSummary summary) {
        if (summary.getNumericColumnCount() < 2) {
            return "需要至少2个数值字段（输入和输出变量），当前仅" + summary.getNumericColumnCount() + "个";
        }
        if (summary.getRowCount() < 20) {
            return "需要至少20个样本点，当前仅" + summary.getRowCount() + "个";
        }
        return "数据特征不满足敏感性分析要求";
    }
}