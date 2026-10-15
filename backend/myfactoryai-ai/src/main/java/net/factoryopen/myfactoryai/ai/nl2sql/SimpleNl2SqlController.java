package net.factoryopen.myfactoryai.ai.nl2sql;

import com.fasterxml.jackson.databind.ObjectMapper;
import net.factoryopen.myfactoryai.ai.config.LLMConfig;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * NL2SQL + 图表分析控制器
 * 支持动态模型选择，自动生成 ECharts 配置
 * 新增：结果集自动字典转换（将编码替换为中文标签）
 * 新增：结果集字段名自动中文化（将 status → 状态）
 */
@RestController
@RequestMapping("/ai/nl2sql")
@PreAuthorize("@ss.hasRole('admin')")
public class SimpleNl2SqlController {

    private static final Logger log = LoggerFactory.getLogger(SimpleNl2SqlController.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SqlSecurityValidator sqlSecurityValidator;

    @Autowired
    private MetadataProvider metadataProvider;   // 用于获取表结构描述

    @Autowired
    private LLMConfig llmConfig;

    @Autowired
    private MetadataSchemaCache metadataSchemaCache;  // 用于字段枚举映射及中文名

    private final ObjectMapper objectMapper = new ObjectMapper();

    // 缓存 MySQL 主版本号
    private volatile Integer mysqlMajorVersion = null;

    // ---------- MySQL 版本检测 ----------
    private int getMySQLMajorVersion() {
        // ... 保持不变 ...
        // 省略，请复制原有代码
        return 5;
    }

    private String getVersionSpecificSqlHint() {
        // ... 保持不变 ...
        return "MySQL 5.7";
    }

    // ---------- 测试接口 ----------
    @GetMapping("/test")
    public Map<String, Object> test() {
        log.info("NL2SQL 测试接口被调用");
        return Map.of("status", "ok", "message", "智能图表服务正常运行，支持动态模型 + 字典转换 + 字段名中文化");
    }

    // ---------- 核心查询 ----------
    @PostMapping("/query")
    public Map<String, Object> query(@RequestBody Map<String, String> request) {
        String userQuestion = request.get("content");
        String model = request.getOrDefault("model", "deepseek-chat");
        Map<String, Object> result = new HashMap<>();
        log.info("========================================");
        log.info("[NL2SQL] 用户问题: {}", userQuestion);
        log.info("[NL2SQL] 使用模型: {}", model);

        try {
            // 1. 获取表结构元数据（用于 Prompt）
            String schemaInfo = metadataProvider.getSchemaDescription();
            log.info("[NL2SQL] 元数据获取完成，长度 {} 字符", schemaInfo.length());

            // 2. 调用大模型生成 SQL 和图表配置
            String aiJson = callAiForChartConfig(userQuestion, schemaInfo, model);
            log.debug("[NL2SQL] AI 返回 JSON: {}", aiJson);
            Map<String, Object> chartPlan = objectMapper.readValue(aiJson, Map.class);

            String sql = (String) chartPlan.get("sql");
            String chartType = (String) chartPlan.getOrDefault("chartType", "table");
            String xAxisColumn = (String) chartPlan.get("xAxisColumn");
            String yAxisColumn = (String) chartPlan.get("yAxisColumn");
            String chartTitle = (String) chartPlan.getOrDefault("chartTitle", "智能图表");
            if (chartTitle == null || chartTitle.trim().isEmpty()) {
                chartTitle = "智能分析图表";
            }
            log.info("[NL2SQL] AI解析: sql={}, chartType={}, xAxis={}, yAxis={}, title={}",
                    sql, chartType, xAxisColumn, yAxisColumn, chartTitle);

            // 3. SQL 安全校验
            sqlSecurityValidator.validateSql(sql);
            log.info("[NL2SQL] SQL 安全校验通过");

            // 4. 执行 SQL
            long start = System.currentTimeMillis();
            List<Map<String, Object>> data = jdbcTemplate.queryForList(sql);
            long duration = System.currentTimeMillis() - start;
            log.info("[NL2SQL] SQL 执行完成，耗时 {} ms，返回 {} 行", duration, data.size());

            // 5. 提取主表名（用于字典转换）
            String mainTableName = extractMainTableFromSql(sql);
            if (mainTableName != null) {
                // 5.1 转换结果集中的字典编码为中文标签
                data = convertEnumValues(data, mainTableName);
                // 5.2 将结果集中的字段名（列名）替换为中文（如 status → 状态）
                data = convertFieldNamesToChinese(data, mainTableName);
            } else {
                log.debug("无法从 SQL 中提取表名，跳过字典转换和字段名中文化");
            }

            // 6. 如果是图表模式，将 X/Y 轴字段名也替换为对应的中文（以便与数据中的中文列名匹配）
            boolean wantsChart = !"table".equals(chartType) && xAxisColumn != null && yAxisColumn != null;
            if (wantsChart && mainTableName != null) {
                String xChinese = metadataSchemaCache.getFieldChineseName(mainTableName, xAxisColumn);
                if (xChinese != null && !xChinese.isEmpty()) {
                    log.debug("X轴字段中文化: {} -> {}", xAxisColumn, xChinese);
                    xAxisColumn = xChinese;
                } else {
                    log.debug("X轴字段 {} 无中文映射，保持不变", xAxisColumn);
                }
                String yChinese = metadataSchemaCache.getFieldChineseName(mainTableName, yAxisColumn);
                if (yChinese != null && !yChinese.isEmpty()) {
                    log.debug("Y轴字段中文化: {} -> {}", yAxisColumn, yChinese);
                    yAxisColumn = yChinese;
                } else {
                    log.debug("Y轴字段 {} 无中文映射，保持不变", yAxisColumn);
                }
            }

            // 7. 封装返回
            result.put("success", true);
            result.put("data", data);
            result.put("rowCount", data.size());
            result.put("sqlExecuted", sql);

            // 8. 生成图表配置
            if (wantsChart) {
                Map<String, Object> echartsOption = buildEChartsOption(data, chartType, xAxisColumn, yAxisColumn);
                result.put("chartConfig", Map.of(
                        "echartsOption", echartsOption,
                        "chartType", chartType,
                        "xAxis", xAxisColumn,
                        "yAxis", yAxisColumn,
                        "chartTitle", chartTitle
                ));
                log.info("[NL2SQL] 已生成图表配置: {}", chartType);
            } else {
                log.info("[NL2SQL] 不生成图表，原因: chartType={}, xAxis={}, yAxis={}", chartType, xAxisColumn, yAxisColumn);
            }
        } catch (Exception e) {
            log.error("[NL2SQL] 查询失败", e);
            result.put("success", false);
            result.put("errorMessage", e.getMessage());
            if (e instanceof SecurityException) {
                result.put("errorType", "SECURITY_VIOLATION");
            }
        }
        log.info("========================================");
        return result;
    }

    // ==================== AI 调用（动态模型） ====================

    private String callAiForChartConfig(String question, String schema, String model) throws Exception {
        String versionHint = getVersionSpecificSqlHint();
        String prompt = """
                你是数据分析专家。根据下面的表结构，回答用户问题，并返回严格的 JSON 格式。

                %s

                表结构及业务说明：
                %s

                用户问题：%s

                要求：
                1. 生成 SQL 时，必须为聚合字段（如 COUNT、SUM、AVG、MAX、MIN 等）使用有业务含义的中文别名。
                   例如 COUNT(*) 应别名为 "数量"，SUM(quantity) 应别名为 "总数"，COUNT(DISTINCT status) 应别名为 "状态数" 等。
                2. chartTitle 使用简洁的中文标题（不超过15字）。
                3. xAxisColumn 填写 X 轴字段的原始字段名（如 status）。
                4. yAxisColumn 填写 Y 轴字段的中文别名（如 "数量"）。

                返回 JSON 格式（不要有其他文字）：
                {
                  "sql": "完整的 SELECT 查询，必须加 LIMIT 100，聚合字段带中文别名",
                  "chartType": "line/bar/pie/table",
                  "xAxisColumn": "原始字段名",
                  "yAxisColumn": "中文别名",
                  "chartTitle": "简短标题"
                }

                示例：
                问题：“不同状态制造批数量统计”
                输出：{
                  "sql": "SELECT status, COUNT(*) AS 数量 FROM pln_ml GROUP BY status LIMIT 100",
                  "chartType": "bar",
                  "xAxisColumn": "status",
                  "yAxisColumn": "数量",
                  "chartTitle": "制造批状态数量统计"
                }
                """.formatted(versionHint, schema, question);

        log.debug("[NL2SQL] 发送 Prompt 长度: {}，模型: {}", prompt.length(), model);

        String provider = model.startsWith("deepseek") ? "deepseek" : "dashscope";
        WebClient client = llmConfig.getWebClient(provider);

        Object body;
        if ("dashscope".equals(provider)) {
            body = Map.of(
                    "model", model,
                    "stream", false,
                    "input", Map.of("messages", List.of(Map.of("role", "user", "content", prompt))),
                    "parameters", Map.of("result_format", "message")
            );
        } else {
            body = Map.of(
                    "model", model,
                    "stream", false,
                    "messages", List.of(Map.of("role", "user", "content", prompt))
            );
        }

        String response = client.post()
                .bodyValue(body)
                .retrieve()
                .bodyToMono(String.class)
                .block();

        if (response == null || response.isEmpty()) {
            throw new RuntimeException("AI 返回空响应");
        }

        String content = extractContentFromResponse(response, provider);
        if (content == null || content.isEmpty()) {
            throw new RuntimeException("AI 响应中没有有效内容");
        }

        // 清理 markdown 代码块标记
        String cleaned = content.replaceAll("```json\n?", "").replaceAll("\n?```", "").trim();
        return cleaned;
    }

    private String extractContentFromResponse(String responseJson, String provider) {
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
            log.error("提取 AI 响应内容失败", e);
            return null;
        }
    }

    // ==================== 结果集转换 ====================

    /**
     * 从 SQL 中提取主表名（简单正则，匹配 FROM 后的第一个表名）
     */
    private String extractMainTableFromSql(String sql) {
        if (sql == null || sql.trim().isEmpty()) return null;
        Pattern pattern = Pattern.compile("FROM\\s+([a-zA-Z0-9_]+)", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(sql);
        if (matcher.find()) {
            return matcher.group(1);
        }
        return null;
    }

    /**
     * 将结果集中的字典编码字段替换为中文标签
     * @param data 原始结果集
     * @param tableName 表名
     * @return 转换后的结果集
     */
    private List<Map<String, Object>> convertEnumValues(List<Map<String, Object>> data, String tableName) {
        if (data == null || data.isEmpty()) return data;

        Map<String, Map<String, String>> enumMapping = metadataSchemaCache.getEnumMappingForTable(tableName);
        if (enumMapping == null || enumMapping.isEmpty()) {
            log.debug("表 {} 无字典映射，跳过编码转换", tableName);
            return data;
        }

        List<Map<String, Object>> converted = new ArrayList<>();
        for (Map<String, Object> row : data) {
            Map<String, Object> newRow = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String colName = entry.getKey();
                Object value = entry.getValue();
                Map<String, String> fieldEnum = enumMapping.get(colName);
                if (fieldEnum != null && value != null) {
                    String strValue = value.toString();
                    String label = fieldEnum.get(strValue);
                    if (label != null) {
                        newRow.put(colName, label);
                        log.trace("字段 {}.{} 编码 {} 转换为 {}", tableName, colName, strValue, label);
                    } else {
                        newRow.put(colName, value);
                    }
                } else {
                    newRow.put(colName, value);
                }
            }
            converted.add(newRow);
        }
        log.info("[NL2SQL] 字典转换完成，表: {}, 共 {} 行，转换字段: {}", tableName, data.size(), enumMapping.keySet());
        return converted;
    }

    /**
     * 将结果集中的字段名（key）替换为中文名称（从 metadataSchemaCache 获取）
     * @param data 原始数据列表（已经过字典值转换）
     * @param tableName 表名
     * @return 字段名已中文化的列表
     */
    private List<Map<String, Object>> convertFieldNamesToChinese(List<Map<String, Object>> data, String tableName) {
        if (data == null || data.isEmpty() || tableName == null) {
            return data;
        }
        Set<String> originalColumns = data.get(0).keySet();
        log.debug("【列名中文化】原始列名: {}", originalColumns);

        Map<String, String> chineseNameMap = new HashMap<>();
        for (String col : originalColumns) {
            // 尝试获取中文名
            String chinese = metadataSchemaCache.getFieldChineseName(tableName, col);
            if (chinese != null && !chinese.isEmpty()) {
                chineseNameMap.put(col, chinese);
                log.debug("【列名中文化】字段 {} 的中文名: {}", col, chinese);
            } else {
                log.debug("【列名中文化】字段 {} 没有中文映射，保持原样", col);
                chineseNameMap.put(col, col);
            }
        }
        if (chineseNameMap.isEmpty()) {
            return data;
        }
        List<Map<String, Object>> converted = new ArrayList<>();
        for (Map<String, Object> row : data) {
            Map<String, Object> newRow = new LinkedHashMap<>();
            for (Map.Entry<String, Object> entry : row.entrySet()) {
                String newKey = chineseNameMap.getOrDefault(entry.getKey(), entry.getKey());
                newRow.put(newKey, entry.getValue());
            }
            converted.add(newRow);
        }
        log.info("[NL2SQL] 字段名中文化完成，表: {}, 映射: {}", tableName, chineseNameMap);
        return converted;
    }

    // ==================== ECharts 配置生成 ====================

    private Map<String, Object> buildEChartsOption(List<Map<String, Object>> data, String chartType,
                                                   String xAxisColumn, String yAxisColumn) {
        List<String> xAxisData = new ArrayList<>();
        List<Number> seriesData = new ArrayList<>();

        for (Map<String, Object> row : data) {
            Object xVal = row.get(xAxisColumn);
            xAxisData.add(xVal != null ? xVal.toString() : "");
            Object yVal = row.get(yAxisColumn);
            if (yVal instanceof Number) {
                seriesData.add((Number) yVal);
            } else {
                try {
                    if (yVal != null) seriesData.add(Double.parseDouble(yVal.toString()));
                    else seriesData.add(0);
                } catch (NumberFormatException e) {
                    seriesData.add(0);
                }
            }
        }

        return Map.of(
                "tooltip", Map.of("trigger", "axis"),
                "xAxis", Map.of("type", "category", "data", xAxisData),
                "yAxis", Map.of("type", "value"),
                "series", List.of(Map.of("name", yAxisColumn, "type", chartType, "data", seriesData))
        );
    }
}
