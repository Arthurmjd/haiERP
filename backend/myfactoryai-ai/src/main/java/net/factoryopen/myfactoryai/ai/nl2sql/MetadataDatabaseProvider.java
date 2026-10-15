package net.factoryopen.myfactoryai.ai.nl2sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 数据库元数据提供者（动态召回模式）
 * 从缓存中获取表结构描述，避免每次查询数据库。
 * 配置项：ai.nl2sql.metadata.provider=database
 */
@Component
@ConditionalOnProperty(name = "ai.nl2sql.metadata.provider", havingValue = "database", matchIfMissing = true)
public class MetadataDatabaseProvider implements MetadataProvider {

    private static final Logger log = LoggerFactory.getLogger(MetadataDatabaseProvider.class);

    @Autowired
    private MetadataSchemaCache metadataCache;

    @Override
    public String getSchemaDescription() {
        log.debug("【MetadataDatabaseProvider】从缓存获取元数据");
        return metadataCache.getSchemaDescription();
    }

    @Override
    public Map<String, List<FieldMeta>> getTableFieldsMap() {
        // 当前未使用结构化字段映射，返回空
        return Map.of();
    }
}