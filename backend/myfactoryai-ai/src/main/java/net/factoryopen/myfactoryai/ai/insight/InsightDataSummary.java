package net.factoryopen.myfactoryai.ai.insight;

import lombok.Data;
import java.util.List;
import java.util.Map;

/**
 * 数据摘要对象，封装从用户请求中提取的数据特征
 * 用于各个分析器判断是否适合分析以及执行分析
 *
 * @author factoryopen
 */
@Data
public class InsightDataSummary {
    /** 原始数据行数（记录条数） */
    private int rowCount;
    /** 数值型字段数量 */
    private int numericColumnCount;
    /** 分类型字段数量 */
    private int categoricalColumnCount;
    /** 是否包含时间序列（x轴有序且为时间或序号） */
    private boolean hasTimeSeries;
    /** 时间序列点数量（如果 hasTimeSeries = true） */
    private int timePointsCount;
    /** 数值字段名称列表 */
    private List<String> numericColumns;
    /** 分类字段名称列表 */
    private List<String> categoricalColumns;
    /** 完整原始数据（可选，用于复杂分析） */
    private List<Map<String, Object>> rawData;
    /** 图表类型（如 pie, bar, line） */
    private String chartType;
}