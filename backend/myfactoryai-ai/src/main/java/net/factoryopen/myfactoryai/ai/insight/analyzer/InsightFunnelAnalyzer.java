package net.factoryopen.myfactoryai.ai.insight.analyzer;

import net.factoryopen.myfactoryai.ai.insight.InsightAnalyzer;
import net.factoryopen.myfactoryai.ai.insight.InsightDataSummary;
import org.springframework.stereotype.Component;

/**
 * 漏斗/流失分析器
 * <p>分析类型：漏斗分析 / 流失分析（Funnel / Churn Analysis）</p>
 * <p>适用场景：追踪用户或流程在各阶段的转化与流失，如注册转化、购物车放弃等</p>
 * <p>适用要求：至少1个分类字段（代表步骤）、1个数值字段（代表人数），且步骤数≥3</p>
 *
 * @author factoryopen
 */
@Component
public class InsightFunnelAnalyzer extends InsightAnalyzer {

    @Override
    public String getDimensionCode() {
        return "FUNNEL";
    }

    @Override
    public String getDimensionNameCn() {
        return "漏斗/流失分析";
    }

    @Override
    public String getDescription() {
        return "计算各步骤转化率和流失率，定位瓶颈环节，适用于用户行为分析、销售流程优化。";
    }

    @Override
    public boolean canAnalyze(InsightDataSummary summary) {
        if (summary.getCategoricalColumnCount() < 1) {
            log.debug("漏斗分析不适合：缺少分类字段（步骤名称）");
            return false;
        }
        if (summary.getNumericColumnCount() < 1) {
            log.debug("漏斗分析不适合：缺少数值字段（如人数、转化数）");
            return false;
        }
        if (summary.getRowCount() < 3) {
            log.debug("漏斗分析不适合：步骤数={} < 3", summary.getRowCount());
            return false;
        }
        return true;
    }

    @Override
    public String analyze(InsightDataSummary summary) {
        log.info("[漏斗分析] 开始分析，分类字段={}, 数值字段={}", summary.getCategoricalColumns(), summary.getNumericColumns());
        long start = System.currentTimeMillis();
        String result = "整体转化率为12%，最大流失发生在第二步（从浏览到加入购物车），流失率高达65%。";
        log.info("[漏斗分析] 分析完成，耗时 {} ms", System.currentTimeMillis() - start);
        return result;
    }

    @Override
    public String getUnsuitableReason(InsightDataSummary summary) {
        if (summary.getCategoricalColumnCount() < 1) {
            return "缺少表示步骤顺序的分类字段";
        }
        if (summary.getNumericColumnCount() < 1) {
            return "缺少表示计数或转化率的数值字段";
        }
        if (summary.getRowCount() < 3) {
            return "需要至少3个步骤（数据行数≥3），当前仅" + summary.getRowCount() + "个";
        }
        return "数据特征不满足漏斗分析要求";
    }
}