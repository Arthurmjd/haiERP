package net.factoryopen.myfactoryai.ai.nl2sql;

import java.util.List;
import java.util.Map;

/**
 * 元数据提供者接口
 * 用于 NL2SQL 获取数据库表结构描述
 */
public interface MetadataProvider {

    /**
     * 获取所有表的结构描述（用于拼接到 Prompt 中）
     * @return 格式化的表结构字符串
     */
    String getSchemaDescription();

    /**
     * 获取结构化的字段元数据（可选实现）
     * @return 表名 -> 字段元数据列表
     */
    Map<String, List<FieldMeta>> getTableFieldsMap();

    class FieldMeta {
        private String fieldName;
        private String fieldType;
        private String comment;

        public FieldMeta() {}
        public FieldMeta(String fieldName, String fieldType, String comment) {
            this.fieldName = fieldName;
            this.fieldType = fieldType;
            this.comment = comment;
        }
        public String getFieldName() { return fieldName; }
        public void setFieldName(String fieldName) { this.fieldName = fieldName; }
        public String getFieldType() { return fieldType; }
        public void setFieldType(String fieldType) { this.fieldType = fieldType; }
        public String getComment() { return comment; }
        public void setComment(String comment) { this.comment = comment; }
    }
}