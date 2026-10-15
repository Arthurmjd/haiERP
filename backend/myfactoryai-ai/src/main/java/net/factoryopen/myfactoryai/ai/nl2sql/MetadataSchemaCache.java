package net.factoryopen.myfactoryai.ai.nl2sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

/**
 * 表结构元数据缓存管理器
 * <p>
 * 从以下三部分数据源读取并合并：
 * 1. INFORMATION_SCHEMA（基础表/字段定义）
 * 2. gen_table_column（代码生成器业务说明、字典类型）
 * 3. sys_dict_type + sys_dict_data（字典映射）
 * <p>
 * 对外提供：
 * - 完整的表结构描述字符串 {@link #getSchemaDescription()}
 * - 字段枚举映射（编码->标签）查询 {@link #getEnumMappingForTable(String)}
 * - 字段中文名称查询 {@link #getFieldChineseName(String, String)}
 * <p>
 * 支持定时刷新和手动刷新。
 */
@Component
public class MetadataSchemaCache implements InitializingBean, DisposableBean {

    private static final Logger log = LoggerFactory.getLogger(MetadataSchemaCache.class);

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Value("${ai.nl2sql.metadata.cache.refresh-interval-minutes:120}")
    private long refreshIntervalMinutes;

    private ScheduledExecutorService scheduler;
    private final AtomicReference<String> cachedMetadata = new AtomicReference<>("");
    // 枚举映射缓存：表名 -> 字段名 -> (编码 -> 标签)
    private final AtomicReference<Map<String, Map<String, Map<String, String>>>> enumMappingCache =
            new AtomicReference<>(new HashMap<>());
    // 字段中文名缓存：表名 -> 字段名 -> 中文名称（纯净，不含枚举描述）
    private final AtomicReference<Map<String, Map<String, String>>> fieldChineseNameCache =
            new AtomicReference<>(new HashMap<>());

    // ==================== 数据结构定义 ====================

    private static class FieldInfo {
        String columnName;
        String dataType;
        boolean isNullable;
        String columnComment;          // 来自 INFORMATION_SCHEMA
        String businessComment;        // 来自 gen_table_column.column_comment
        String dictType;               // 字典类型（来自 gen_table_column.dict_type）

        FieldInfo(String columnName, String dataType, boolean isNullable, String columnComment) {
            this.columnName = columnName;
            this.dataType = dataType;
            this.isNullable = isNullable;
            this.columnComment = columnComment;
            this.businessComment = "";
            this.dictType = null;
        }

        String getDisplayComment() {
            if (businessComment != null && !businessComment.isEmpty()) return businessComment;
            if (columnComment != null && !columnComment.isEmpty()) return columnComment;
            return "";
        }
    }

    private static class TableInfo {
        String tableName;
        String tableComment;
        List<FieldInfo> fields = new ArrayList<>();

        TableInfo(String tableName, String tableComment) {
            this.tableName = tableName;
            this.tableComment = tableComment;
        }
    }

    private static class DictItem {
        String label;
        String value;
        DictItem(String label, String value) {
            this.label = label;
            this.value = value;
        }
    }

    // ==================== 生命周期管理 ====================

    @Override
    public void afterPropertiesSet() {
        log.info("【MetadataSchemaCache】初始化，刷新间隔 {} 分钟", refreshIntervalMinutes);
        refreshCache();
        scheduler = Executors.newSingleThreadScheduledExecutor();
        scheduler.scheduleAtFixedRate(this::refreshCache, refreshIntervalMinutes, refreshIntervalMinutes, TimeUnit.MINUTES);
        log.info("【MetadataSchemaCache】定时任务已启动");
    }

    @Override
    public void destroy() {
        if (scheduler != null && !scheduler.isShutdown()) {
            scheduler.shutdown();
            log.info("【MetadataSchemaCache】定时任务已关闭");
        }
    }

    /**
     * 对外提供表结构描述（格式与原有版本兼容）
     */
    public String getSchemaDescription() {
        String metadata = cachedMetadata.get();
        if (metadata == null || metadata.trim().isEmpty()) {
            log.warn("【MetadataSchemaCache】缓存为空，请检查数据库连接或配置。");
            return "";
        }
        log.debug("提供元数据，长度 {} 字符", metadata.length());
        return metadata;
    }

    /**
     * 获取指定表的所有字段的枚举映射
     * @param tableName 表名
     * @return Map<字段名, Map<编码, 标签>>
     */
    public Map<String, Map<String, String>> getEnumMappingForTable(String tableName) {
        Map<String, Map<String, Map<String, String>>> cache = enumMappingCache.get();
        if (cache == null) return Collections.emptyMap();
        return cache.getOrDefault(tableName, Collections.emptyMap());
    }

    /**
     * 获取指定表、指定字段的中文名称（纯净，不含枚举描述）
     * @param tableName 表名
     * @param columnName 字段名
     * @return 中文名称，若不存在则返回 null
     */
    public String getFieldChineseName(String tableName, String columnName) {
        Map<String, Map<String, String>> cache = fieldChineseNameCache.get();
        if (cache == null) return null;
        Map<String, String> tableMap = cache.get(tableName);
        if (tableMap == null) return null;
        return tableMap.get(columnName);
    }

    /**
     * 手动刷新缓存
     */
    public void manualRefresh() {
        log.info("手动刷新缓存");
        refreshCache();
    }

    // ==================== 核心刷新逻辑 ====================

    private void refreshCache() {
        log.info("【MetadataSchemaCache】开始刷新表结构元数据...");
        long start = System.currentTimeMillis();

        try {
            // 1. 设置较大的 GROUP_CONCAT 长度（避免字段列表被截断）
            try {
                jdbcTemplate.execute("SET SESSION group_concat_max_len = 50000");
                log.debug("已设置 group_concat_max_len = 50000");
            } catch (Exception e) {
                log.warn("设置 group_concat_max_len 失败，字段列表可能被截断", e);
            }

            String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
            log.debug("当前数据库: {}", dbName);

            // ---------- 第一步：从 INFORMATION_SCHEMA 读取基础表/字段 ----------
            Map<String, TableInfo> tables = loadBaseSchema(dbName);
            if (tables.isEmpty()) {
                log.error("【MetadataSchemaCache】未读取到任何表，缓存保持旧值");
                return;
            }
            log.info("【MetadataSchemaCache】读取基础表结构：{} 张表", tables.size());
            if (log.isDebugEnabled()) {
                log.debug("表名列表：{}", tables.keySet());
            }

            // ---------- 第二步：从 gen_table_column 读取业务说明和字典类型 ----------
            loadGenTableColumnInfo(tables);
            log.info("【MetadataSchemaCache】合并 gen_table_column 中的业务说明");

            // ---------- 第三步：从 sys_dict_type + sys_dict_data 读取字典映射 ----------
            Map<String, List<DictItem>> dictMap = loadDictData();
            log.info("【MetadataSchemaCache】加载字典类型 {} 个", dictMap.size());

            // ---------- 第四步：为字段关联字典，生成描述并构建枚举映射缓存 ----------
            Map<String, Map<String, Map<String, String>>> newEnumMapping = new HashMap<>();
            enrichFieldsWithDict(tables, dictMap, newEnumMapping);
            log.info("【MetadataSchemaCache】已为字段关联字典项");

            // ---------- 第五步：构建字段中文名缓存 ----------
            buildFieldChineseNameCache(tables);
            log.info("【MetadataSchemaCache】字段中文名缓存构建完成，共 {} 张表", fieldChineseNameCache.get().size());

            // ---------- 第六步：生成最终输出字符串 ----------
            String newMetadata = buildSchemaDescription(tables);
            cachedMetadata.set(newMetadata);
            enumMappingCache.set(newEnumMapping);

            // ---------- 第七步：控制台打印枚举映射详情（DEBUG级别） ----------
            if (log.isDebugEnabled()) {
                printEnumMappings(newEnumMapping);
                log.debug("字段中文名示例: {}", fieldChineseNameCache.get());
            }

            long duration = System.currentTimeMillis() - start;
            log.info("【MetadataSchemaCache】刷新完成，耗时 {} ms，元数据总长度 {} 字符", duration, newMetadata.length());
            if (log.isDebugEnabled()) {
                log.debug("元数据详情:\n{}", newMetadata);
            }
        } catch (Exception e) {
            log.error("【MetadataSchemaCache】刷新失败，缓存保持旧值", e);
        }
    }

    // ==================== 辅助方法 ====================

    private Map<String, TableInfo> loadBaseSchema(String dbName) {
        String sql = """
                SELECT 
                    t.TABLE_NAME,
                    IFNULL(t.TABLE_COMMENT, '') AS TABLE_COMMENT,
                    c.COLUMN_NAME,
                    c.COLUMN_TYPE,
                    c.IS_NULLABLE,
                    IFNULL(c.COLUMN_COMMENT, '') AS COLUMN_COMMENT
                FROM INFORMATION_SCHEMA.TABLES t
                JOIN INFORMATION_SCHEMA.COLUMNS c 
                    ON t.TABLE_NAME = c.TABLE_NAME AND t.TABLE_SCHEMA = c.TABLE_SCHEMA
                WHERE t.TABLE_SCHEMA = ?
                ORDER BY t.TABLE_NAME, c.ORDINAL_POSITION
                """;
        List<Map<String, Object>> rows = jdbcTemplate.queryForList(sql, dbName);
        Map<String, TableInfo> tables = new LinkedHashMap<>();

        for (Map<String, Object> row : rows) {
            String tableName = (String) row.get("TABLE_NAME");
            String tableComment = (String) row.get("TABLE_COMMENT");
            String columnName = (String) row.get("COLUMN_NAME");
            String colType = (String) row.get("COLUMN_TYPE");
            String isNullable = (String) row.get("IS_NULLABLE");
            String colComment = (String) row.get("COLUMN_COMMENT");

            TableInfo table = tables.get(tableName);
            if (table == null) {
                table = new TableInfo(tableName, tableComment);
                tables.put(tableName, table);
            }
            FieldInfo field = new FieldInfo(columnName, colType, "YES".equalsIgnoreCase(isNullable), colComment);
            table.fields.add(field);
        }
        log.info("加载 INFORMATION_SCHEMA 完成，共 {} 张表", tables.size());
        return tables;
    }

    private void loadGenTableColumnInfo(Map<String, TableInfo> tables) {
        if (!tableExists("gen_table_column")) {
            log.warn("表 gen_table_column 不存在，跳过加载业务说明");
            return;
        }

        String sql = """
                SELECT 
                    table_name,
                    column_name,
                    IFNULL(column_comment, '') AS column_comment,
                    IFNULL(dict_type, '') AS dict_type
                FROM gen_table_column
                """;
        List<Map<String, Object>> rows;
        try {
            rows = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            log.warn("查询 gen_table_column 失败，可能表结构不匹配，跳过", e);
            return;
        }

        int updated = 0;
        for (Map<String, Object> row : rows) {
            String tableName = (String) row.get("table_name");
            String columnName = (String) row.get("column_name");
            String businessComment = (String) row.get("column_comment");
            String dictType = (String) row.get("dict_type");

            TableInfo table = tables.get(tableName);
            if (table != null) {
                for (FieldInfo field : table.fields) {
                    if (field.columnName.equals(columnName)) {
                        if (businessComment != null && !businessComment.isEmpty()) {
                            field.businessComment = businessComment;
                        }
                        if (dictType != null && !dictType.isEmpty()) {
                            field.dictType = dictType;
                        }
                        updated++;
                        break;
                    }
                }
            }
        }
        log.info("从 gen_table_column 更新了 {} 个字段的业务说明/字典类型", updated);
    }

    private Map<String, List<DictItem>> loadDictData() {
        Map<String, List<DictItem>> dictMap = new HashMap<>();
        if (!tableExists("sys_dict_type") || !tableExists("sys_dict_data")) {
            log.warn("sys_dict_type 或 sys_dict_data 表不存在，字典功能不可用");
            return dictMap;
        }

        String sql = """
                SELECT 
                    dt.dict_type,
                    dd.dict_label,
                    dd.dict_value
                FROM sys_dict_type dt
                JOIN sys_dict_data dd ON dt.dict_type = dd.dict_type
                WHERE dt.status = '0' AND dd.status = '0'
                ORDER BY dt.dict_type, dd.dict_sort
                """;
        List<Map<String, Object>> rows;
        try {
            rows = jdbcTemplate.queryForList(sql);
        } catch (Exception e) {
            log.warn("查询字典表失败，可能表结构不匹配，跳过", e);
            return dictMap;
        }

        for (Map<String, Object> row : rows) {
            String dictType = (String) row.get("dict_type");
            String dictLabel = (String) row.get("dict_label");
            String dictValue = (String) row.get("dict_value");
            dictMap.computeIfAbsent(dictType, k -> new ArrayList<>())
                    .add(new DictItem(dictLabel, dictValue));
        }
        log.info("加载字典数据完成，共 {} 个字典类型", dictMap.size());
        return dictMap;
    }

    private void enrichFieldsWithDict(Map<String, TableInfo> tables, Map<String, List<DictItem>> dictMap,
                                      Map<String, Map<String, Map<String, String>>> enumMapping) {
        if (dictMap.isEmpty()) return;

        for (TableInfo table : tables.values()) {
            Map<String, Map<String, String>> fieldEnumMap = new HashMap<>();
            for (FieldInfo field : table.fields) {
                if (field.dictType != null && dictMap.containsKey(field.dictType)) {
                    List<DictItem> items = dictMap.get(field.dictType);
                    Map<String, String> enumItemMap = new LinkedHashMap<>();
                    StringBuilder sb = new StringBuilder("可选值: ");
                    for (int i = 0; i < items.size(); i++) {
                        if (i > 0) sb.append(", ");
                        sb.append(items.get(i).value).append("=").append(items.get(i).label);
                        enumItemMap.put(items.get(i).value, items.get(i).label);
                    }
                    // 将字典描述附加到业务说明后面（分号隔开）
                    if (field.businessComment != null && !field.businessComment.isEmpty()) {
                        field.businessComment = field.businessComment + "；" + sb.toString();
                    } else {
                        field.businessComment = sb.toString();
                    }
                    fieldEnumMap.put(field.columnName, enumItemMap);
                }
            }
            if (!fieldEnumMap.isEmpty()) {
                enumMapping.put(table.tableName, fieldEnumMap);
            }
        }
    }

    /**
     * 构建字段中文名缓存（纯净名称，不含“可选值”部分）
     */
    private void buildFieldChineseNameCache(Map<String, TableInfo> tables) {
        Map<String, Map<String, String>> newCache = new HashMap<>();
        for (Map.Entry<String, TableInfo> entry : tables.entrySet()) {
            String tableName = entry.getKey();
            Map<String, String> fieldMap = new HashMap<>();
            for (FieldInfo field : entry.getValue().fields) {
                String rawComment = field.getDisplayComment();
                String pureChinese = rawComment;
                // 去除可能附加的“可选值：...”部分（以分号分隔）
                if (rawComment != null && rawComment.contains("；")) {
                    pureChinese = rawComment.split("；")[0];
                }
                // 如果最终为空，则使用原字段名作为降级
                if (pureChinese == null || pureChinese.trim().isEmpty()) {
                    pureChinese = field.columnName;
                }
                fieldMap.put(field.columnName, pureChinese);
            }
            newCache.put(tableName, fieldMap);
        }
        fieldChineseNameCache.set(newCache);
        log.info("【MetadataSchemaCache】构建字段中文名缓存完成，共 {} 张表", newCache.size());
    }

    private String buildSchemaDescription(Map<String, TableInfo> tables) {
        StringBuilder sb = new StringBuilder();
        for (TableInfo table : tables.values()) {
            sb.append("表名: ").append(table.tableName);
            if (table.tableComment != null && !table.tableComment.isEmpty()) {
                sb.append(" (").append(table.tableComment).append(")");
            }
            sb.append("\n字段: \n");
            for (FieldInfo field : table.fields) {
                sb.append("    ").append(field.columnName);
                String comment = field.getDisplayComment();
                if (!comment.isEmpty()) {
                    sb.append(" (").append(comment).append(")");
                }
                sb.append(" (").append(field.dataType);
                if (!field.isNullable) {
                    sb.append(", NOT NULL");
                }
                sb.append(")\n");
            }
            sb.append("业务说明: ").append(table.tableComment != null ? table.tableComment : "").append("\n\n");
        }
        return sb.toString().trim();
    }

    private boolean tableExists(String tableName) {
        try {
            String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
            Integer count = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM INFORMATION_SCHEMA.TABLES WHERE TABLE_SCHEMA = ? AND TABLE_NAME = ?",
                    Integer.class, dbName, tableName);
            return count != null && count > 0;
        } catch (Exception e) {
            log.debug("检查表 {} 是否存在时出错", tableName, e);
            return false;
        }
    }

    private void printEnumMappings(Map<String, Map<String, Map<String, String>>> mapping) {
        log.debug("========== 字段枚举映射详情 ==========");
        for (Map.Entry<String, Map<String, Map<String, String>>> tableEntry : mapping.entrySet()) {
            String tableName = tableEntry.getKey();
            for (Map.Entry<String, Map<String, String>> fieldEntry : tableEntry.getValue().entrySet()) {
                String fieldName = fieldEntry.getKey();
                Map<String, String> enumMap = fieldEntry.getValue();
                log.debug("表: {}, 字段: {}, 枚举项: {}", tableName, fieldName, enumMap);
            }
        }
        log.debug("======================================");
    }
}