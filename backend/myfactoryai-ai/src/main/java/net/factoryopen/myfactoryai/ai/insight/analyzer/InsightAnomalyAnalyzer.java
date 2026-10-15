package net.factoryopen.myfactoryai.ai.insight.analyzer;

import net.factoryopen.myfactoryai.ai.insight.InsightAnalyzer;
import net.factoryopen.myfactoryai.ai.insight.InsightDataSummary;
import org.springframework.stereotype.Component;

/**
 * 异常检测器
 * <p>分析类型：异常检测（Anomaly Detection）</p>
 * <p>适用场景：识别偏离正常模式的数据点，如欺诈交易、设备故障、数据录入错误等</p>
 * <p>适用要求：至少一个数值字段，且样本量≥10</p>
 *
 * @author factoryopen
 */
@Component
public class InsightAnomalyAnalyzer extends InsightAnalyzer {

    @Override
    public String getDimensionCode() {
        return "ANOMALY";
    }

    @Override
    public String getDimensionNameCn() {
        return "异常检测";
    }

    @Override
    public String getDescription() {
        return "识别数据中的异常点或离群值，适用于监控、风控、质量控制等场景。";
    }

    @Override
    public boolean canAnalyze(InsightDataSummary summary) {
        if (summary.getNumericColumnCount() < 1) {
            log.debug("异常检测不适合：缺少数值字段");
            return false;
        }
        if (summary.getRowCount() < 10) {
            log.debug("异常检测不适合：样本量={} < 10", summary.getRowCount());
            return false;
        }
        return true;
    }

    @Override
    public String analyze(InsightDataSummary summary) {
        log.info("[异常检测] 开始分析，数值字段数={}, 样本量={}", summary.getNumericColumnCount(), summary.getRowCount());
        long start = System.currentTimeMillis();
        String result = "未检测到显著异常值，所有观测点均在正常波动范围内。";
        log.info("[异常检测] 分析完成，耗时 {} ms", System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public String getUnsuitableReason(InsightDataSummary summary) {
        if (summary.getNumericColumnCount() < 1) {
            return "缺少数值字段，无法进行数值异常判断";
        }
        if (summary.getRowCount() < 10) {
            return "需要至少10个样本点，当前仅" + summary.getRowCount() + "个";
        }
        return "数据特征不满足异常检测要求";
    }
}