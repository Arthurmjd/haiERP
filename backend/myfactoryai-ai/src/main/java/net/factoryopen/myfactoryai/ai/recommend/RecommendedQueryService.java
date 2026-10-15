package net.factoryopen.myfactoryai.ai.recommend;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RecommendedQueryService {

    private static final Logger log = LoggerFactory.getLogger(RecommendedQueryService.class);

    /**
     * 获取所有推荐会话列表。
     * 子类可重写此方法实现动态配置。
     */
    public List<RecommendedQueryItem> getRecommendedQueries() {
        log.debug("加载硬编码推荐会话列表");
        List<RecommendedQueryItem> list = new ArrayList<>();

        // 每日订单量走势（修改 queryText，移除窗口函数需求，确保 MySQL 5.6 兼容）
        list.add(new RecommendedQueryItem(
                "daily_orders",
                "📊 每日订单量走势",
                "最近7天每天的订单数量趋势",
                "统计最近7天每天的订单数量，按日期升序",   // 不再要求同比前一日的增长率
                "chart",
                null
        ));

        // 当前在制批次
        list.add(new RecommendedQueryItem(
                "current_wip_batches",
                "🏭 当前在制批次",
                "查看所有正在生产的批次",
                "查询当前所有状态为'生产中'的批次，显示批次号、产品名称、计划完成时间、当前完成率",
                "chat",
                null
        ));

        // 当前物料库存（低库存预警）
        list.add(new RecommendedQueryItem(
                "material_stock",
                "📦 低库存物料预警",
                "列出低于安全库存的物料",
                "列出库存数量低于安全库存的物料，显示物料编码、物料名称、当前库存量、安全库存值、短缺数量",
                "chat",
                null
        ));

        // 近7天质量合格率趋势
        list.add(new RecommendedQueryItem(
                "quality_defect_rate",
                "📉 近7天质量合格率趋势",
                "查看最近一周每天的质量合格率变化",
                "按天统计最近7天生产任务的合格率（合格总数/生产总数），生成折线图，并标注合格率低于95%的日期",
                "chart",
                null
        ));

        // 各产线昨日产量
        list.add(new RecommendedQueryItem(
                "production_output",
                "⚙️ 各产线昨日产量",
                "各产线昨天的实际产出对比",
                "查询各产线昨天的实际产出数量，按产出量从高到低排序，用柱状图展示",
                "chart",
                null
        ));

        // 批次追踪
        list.add(new RecommendedQueryItem(
                "batch_trace",
                "🔍 批次追溯查询",
                "输入批次号查询生产全流程",
                "请查询批次号为 '20240516-001' 的生产记录，包括工序、质检结果、操作人员",
                "chat",
                null
        ));

        return list;
    }
}