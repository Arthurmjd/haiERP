package net.factoryopen.myfactoryai.ai.nl2sql;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * 硬编码元数据提供者（精确可控模式）
 * 配置项：ai.nl2sql.metadata.provider=hardcoded
 * 优势：不依赖数据库，避免数据库结构变化导致 AI 生成 SQL 偏差。
 */
@Component
@ConditionalOnProperty(name = "ai.nl2sql.metadata.provider", havingValue = "hardcoded")
public class MetadataHardcodedProvider implements MetadataProvider {

    private static final Logger log = LoggerFactory.getLogger(MetadataHardcodedProvider.class);

    @Override
    public String getSchemaDescription() {
        log.info("【MetadataHardcodedProvider】使用硬编码表结构元数据");
        // 以下硬编码内容请根据实际业务表结构填写
        return """              
            表名: sup_purchasing_plan (采购计划)
            字段: id (ID, bigint(20), NOT NULL),
                 bill_no (采购单号, varchar(30), NOT NULL),
                 status (状态, smallint(6), NOT NULL),
                 material_id (物料ID, bigint(20), NOT NULL),
                 material_code (物料编码, varchar(30), NOT NULL),
                 material_name (物料名称, varchar(50)),
                 material_spec (物料规格, varchar(50)),
                 draw_no (图号, varchar(50)),
                 unit_id (单位, bigint(20), NOT NULL),
                 quantity (订单数量, decimal(12,4), NOT NULL),
                 price (单价, decimal(12,4), NOT NULL),
                 amount (金额, decimal(12,4), NOT NULL),
                 require_day (需求日期, date),
                 priority (优先级, smallint(6)),
                 pr_id (采购申请ID, bigint(20)),
                 pr_item_id (单项序号, bigint(20)),
                 purchasing_type (采购类型, smallint(4), NOT NULL),
                 customer_id (客户ID, bigint(20)),
                 customer_name (客户名称, varchar(255)),
                 price_sale (销售价, decimal(12,4)),
                 source_bill_id (来源单ID, bigint(20)),
                 source_bill_no (来源单号, varchar(30)),
                 consume_type (消耗类型, smallint(4)),
                 enable_must (必配料, char(1)),
                 enable_replace (可替代, char(1)),
                 remark (备注, varchar(255)),
                 create_by (创建者, varchar(64)),
                 create_time (创建时间, datetime),
                 update_by (更新者, varchar(64)),
                 update_time (更新时间, datetime)
            业务说明: 采购计划
            
            表名: sup_supplier (供应商表)
            字段: id (ID, bigint(20), NOT NULL),
                 supplier_name (供应商名称, varchar(255), NOT NULL),
                 supplier_code (供应商编码, varchar(30)),
                 full_name (全名, varchar(255)),
                 material_enabled (物料供应否, char(1)),
                 ordor_os_enabled (计划外协否, char(1)),
                 process_os_enabled (工序委外否, char(1)),
                 logistic_enabled (物流配运否, char(1)),
                 contact (联系人, varchar(30)),
                 phone (电话, varchar(30)),
                 address (地址, varchar(255)),
                 status (在用否, smallint(2)),
                 remark (备注, varchar(255)),
                 create_by (创建者, varchar(64)),
                 create_time (创建时间, datetime),
                 update_by (更新者, varchar(64)),
                 update_time (更新时间, datetime)
            业务说明: 供应商表
            
            表名: sup_supplier_address (供应商地址表)
            字段: id (ID, bigint(20), NOT NULL),
                 supplier_id (供应商ID, bigint(20), NOT NULL),
                 address (地址, varchar(100)),
                 contact (联系人, varchar(230)),
                 phone (手机, varchar(20)),
                 Longitude (经度, bigint(20)),
                 latitude (纬度, bigint(20)),
                 remark (备注, varchar(255)),
                 create_by (创建者, varchar(64)),
                 create_time (创建时间, datetime),
                 update_by (更新者, varchar(64)),
                 update_time (更新时间, datetime)
            业务说明: 供应商地址表
            
            表名: sup_supplier_bank (供应商账号表)
            字段: id (ID, bigint(20), NOT NULL),
                 supplier_id (供应商ID, bigint(20), NOT NULL),
                 account_no (账号, varchar(50)),
                 bank_name (开户行, varchar(100)),
                 owner_name (开户人, varchar(100)),
                 remark (备注, varchar(255)),
                 create_by (创建者, varchar(64)),
                 create_time (创建时间, datetime),
                 update_by (更新者, varchar(64)),
                 update_time (更新时间, datetime)
            业务说明: 供应商账号表
            
            表名: sup_supplier_contact (供应商人员表)
            字段: id (ID, bigint(20), NOT NULL),
                 supplier_id (供应商ID, bigint(20), NOT NULL),
                 contact (联系人, varchar(230)),
                 cellphone1 (手机1, varchar(20)),
                 cellphone2 (手机2, varchar(20)),
                 wechat (微信, varchar(20)),
                 email (电邮, varchar(20)),
                 telephone (电话, varchar(20)),
                 remark (备注, varchar(255)),
                 create_by (创建者, varchar(64)),
                 create_time (创建时间, datetime),
                 update_by (更新者, varchar(64)),
                 update_time (更新时间, datetime)
            业务说明: 供应商人员表
            
            表名: sup_supplier_quota (供应商配额)
            字段: id (ID, bigint(20), NOT NULL),
                 material_id (物料ID, bigint(20), NOT NULL),
                 material_name (物料名称, varchar(50)),
                 material_spec (物料规格, varchar(50)),
                 priority_seq (优先顺位, smallint(4), NOT NULL),
                 supplier_id (供应商ID, bigint(20), NOT NULL),
                 quota_percent (配额%, decimal(12,4), NOT NULL),
                 remark (备注, varchar(255)),
                 create_by (创建者, varchar(64)),
                 create_time (创建时间, datetime),
                 update_by (更新者, varchar(64)),
                 update_time (更新时间, datetime)
            业务说明: 供应商配额
            """;
    }

    @Override
    public Map<String, List<FieldMeta>> getTableFieldsMap() {
        return Map.of();
    }
}