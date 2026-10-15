-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 库存模块（Inventory）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- -----------------------------------------------------------------------------
-- 本模块含 2 张表：
--   st_inventory    库存结存（物料 + 成品同表，用 item_type 区分）
--   st_stock_txn    出入库流水（只增不改，库存的真相来源）
-- -----------------------------------------------------------------------------
-- 设计约定：
--   1) 物料与成品同表，用 item_type 区分（1物料 2成品SKU）。
--   2) 保留结存表（可由流水汇总，也可物化存一份，读性能好）。
--   3) 单仓库：warehouse_id 预留（MVP 固定 1）。
--   4) 统一批次字段 batch_no；物料按批次管理，成品可选。
--   5) 安全库存 safe_stock 先留字段，MVP 不做深化预警。
-- -----------------------------------------------------------------------------
-- 四类进出（由单据驱动，人不手改）：
--   采购入库 / 生产领料 / 完工入库 / 销售出库（+ 盘盈盘亏 / 退料）
-- 数据一致性：st_inventory 的结存数量 == Σ(该 item+batch 的 st_stock_txn)
-- -----------------------------------------------------------------------------
-- 审计字段规范（v1.2 起）：
--   · 本模块属「流水 / 派生表」，只记 create_by（谁触发的），不加 del_flag；
--   · 流水只增不改，删除主单不删流水，保证批次追溯可回放；
--   · st_inventory.update_time 由库存重算任务维护，非用户操作，故不设 update_by。
-- -----------------------------------------------------------------------------
-- 依赖：md_material / md_sku（主数据）
-- 编写日期：2026-10-14　修订：2026-10-14（流水扩充至 38 条 / 结存 16 行 + 审计字段）
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS st_stock_txn;
DROP TABLE IF EXISTS st_inventory;

-- -----------------------------------------------------------------------------
-- 1. 库存结存
-- -----------------------------------------------------------------------------
CREATE TABLE st_inventory (
  inv_id       BIGINT        NOT NULL AUTO_INCREMENT COMMENT '结存ID',
  item_type    TINYINT       NOT NULL                COMMENT '库存对象类型：1物料 2成品SKU',
  item_id      BIGINT        NOT NULL                COMMENT '对象ID（material_id 或 sku_id）',
  batch_no     VARCHAR(30)   NOT NULL DEFAULT ''     COMMENT '批次号（物料必管，成品可选）',
  warehouse_id BIGINT        NOT NULL DEFAULT 1      COMMENT '仓库ID（MVP 固定 1）',
  qty          DECIMAL(14,3) NOT NULL DEFAULT 0.000  COMMENT '结存数量',
  safe_stock   DECIMAL(14,3) NOT NULL DEFAULT 0.000  COMMENT '安全库存（预留）',
  create_by    VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_time  DATETIME      DEFAULT NULL            COMMENT '更新时间（库存重算维护）',
  PRIMARY KEY (inv_id),
  UNIQUE KEY uk_item_batch_wh (item_type, item_id, batch_no, warehouse_id),
  KEY idx_item (item_type, item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存结存';

-- -----------------------------------------------------------------------------
-- 2. 出入库流水（只增不改）
-- -----------------------------------------------------------------------------
CREATE TABLE st_stock_txn (
  txn_id       BIGINT        NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  txn_no       VARCHAR(30)   NOT NULL                COMMENT '流水号',
  txn_type     TINYINT       NOT NULL                COMMENT '方向：1入库 2出库',
  biz_type     TINYINT       NOT NULL                COMMENT '业务类型：1采购入库 2生产领料 3完工入库 4销售出库 5盘盈 6盘亏 7退料',
  item_type    TINYINT       NOT NULL                COMMENT '库存对象类型：1物料 2成品SKU',
  item_id      BIGINT        NOT NULL                COMMENT '对象ID',
  batch_no     VARCHAR(30)   NOT NULL DEFAULT ''     COMMENT '批次号',
  qty          DECIMAL(14,3) NOT NULL DEFAULT 0.000  COMMENT '数量（正数，方向看 txn_type）',
  warehouse_id BIGINT        NOT NULL DEFAULT 1      COMMENT '仓库ID',
  source_type  VARCHAR(20)   DEFAULT NULL            COMMENT '来源单据类型：pu_order/pr_wo/sd_order/sh_return/stock_check',
  source_id    BIGINT        DEFAULT NULL            COMMENT '来源单据ID',
  txn_time     DATETIME      DEFAULT NULL            COMMENT '发生时间',
  create_by    VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME      DEFAULT NULL            COMMENT '创建时间',
  remark       VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (txn_id),
  UNIQUE KEY uk_txn_no (txn_no),
  KEY idx_item (item_type, item_id),
  KEY idx_time (txn_time),
  KEY idx_source (source_type, source_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出入库流水';

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 示例数据（SQL 种子）  create_by 统一记 'admin'
-- =============================================================================

-- 出入库流水（38 条，按时间顺序）
INSERT INTO st_stock_txn (txn_no, txn_type, biz_type, item_type, item_id, batch_no, qty, source_type, source_id, txn_time, create_by, create_time, remark) VALUES
-- 采购入库（PO3 / PO1 / PO2 / PO4）
('TXN2026091801', 1, 1, 1, 2, 'F20260918', 150.000, 'pu_order', 3, '2026-09-18 09:00:00', 'admin', NOW(), '袜子/长裤弹力棉入库'),
('TXN2026091802', 1, 1, 1, 6, 'F20260918', 140.000, 'pu_order', 3, '2026-09-18 09:05:00', 'admin', NOW(), '背心速干锦纶入库'),
('TXN2026092801', 1, 1, 1, 1, 'F20260928', 320.000, 'pu_order', 1, '2026-09-28 09:00:00', 'admin', NOW(), 'T恤网眼布入库'),
('TXN2026092802', 1, 1, 1, 2, 'F20260928', 400.000, 'pu_order', 1, '2026-09-28 09:03:00', 'admin', NOW(), '长裤弹力棉入库'),
('TXN2026092803', 1, 1, 1, 3, '',          400.000, 'pu_order', 2, '2026-09-28 10:00:00', 'admin', NOW(), '树脂拉链入库'),
('TXN2026092804', 1, 1, 1, 4, '',          600.000, 'pu_order', 2, '2026-09-28 10:02:00', 'admin', NOW(), '织唛主标入库'),
('TXN2026092805', 1, 1, 1, 5, '',           50.000, 'pu_order', 2, '2026-09-28 10:05:00', 'admin', NOW(), '缝纫线入库'),
('TXN2026100201', 1, 1, 1, 7, '',          180.000, 'pu_order', 4, '2026-10-02 11:00:00', 'admin', NOW(), '氨纶弹力布入库'),
('TXN2026100202', 1, 1, 1, 8, '',          200.000, 'pu_order', 4, '2026-10-02 11:02:00', 'admin', NOW(), '洗水标入库'),
-- 生产领料（WO3 / WO4 / WO1 / WO2）
('TXN2026092101', 2, 2, 1, 2, 'F20260918',  99.840, 'pr_wo', 3, '2026-09-21 08:30:00', 'admin', NOW(), 'WO3 袜子领料-弹力棉'),
('TXN2026092102', 2, 2, 1, 5, '',           12.240, 'pr_wo', 3, '2026-09-21 08:32:00', 'admin', NOW(), 'WO3 袜子领料-缝纫线'),
('TXN2026100901', 2, 2, 1, 7, '',          120.000, 'pr_wo', 4, '2026-10-09 08:30:00', 'admin', NOW(), 'WO4 紧身裤领料-氨纶布'),
('TXN2026100902', 2, 2, 1, 8, '',          100.000, 'pr_wo', 4, '2026-10-09 08:32:00', 'admin', NOW(), 'WO4 紧身裤领料-洗水标'),
('TXN2026101301', 2, 2, 1, 1, 'F20260928', 302.400, 'pr_wo', 1, '2026-10-13 08:30:00', 'admin', NOW(), 'WO1 T恤领料-网眼布'),
('TXN2026101302', 2, 2, 1, 4, '',          360.000, 'pr_wo', 1, '2026-10-13 08:32:00', 'admin', NOW(), 'WO1 T恤领料-织唛'),
('TXN2026101303', 2, 2, 1, 5, '',            7.344, 'pr_wo', 1, '2026-10-13 08:34:00', 'admin', NOW(), 'WO1 T恤领料-缝纫线'),
('TXN2026101304', 2, 2, 1, 5, '',            3.000, 'pr_wo', 4, '2026-10-13 09:00:00', 'admin', NOW(), 'WO4 紧身裤领料-缝纫线'),
('TXN2026101501', 2, 2, 1, 2, 'F20260928', 300.000, 'pr_wo', 2, '2026-10-15 08:30:00', 'admin', NOW(), 'WO2 长裤领料-弹力棉'),
('TXN2026101502', 2, 2, 1, 3, '',          250.000, 'pr_wo', 2, '2026-10-15 08:32:00', 'admin', NOW(), 'WO2 长裤领料-拉链'),
('TXN2026101503', 2, 2, 1, 5, '',            7.000, 'pr_wo', 2, '2026-10-15 08:34:00', 'admin', NOW(), 'WO2 长裤领料-缝纫线'),
-- 完工入库（WO3 / WO1）
('TXN2026100401', 1, 3, 2, 10, 'C20261004', 500.000, 'pr_wo', 3, '2026-10-04 17:00:00', 'admin', NOW(), '袜子完工入库-BLK'),
('TXN2026100402', 1, 3, 2, 11, 'C20261004', 400.000, 'pr_wo', 3, '2026-10-04 17:01:00', 'admin', NOW(), '袜子完工入库-WHT'),
('TXN2026100403', 1, 3, 2, 12, 'C20261004', 300.000, 'pr_wo', 3, '2026-10-04 17:02:00', 'admin', NOW(), '袜子完工入库-GRY'),
('TXN2026102001', 1, 3, 2, 1,  'C20261020', 100.000, 'pr_wo', 1, '2026-10-20 16:00:00', 'admin', NOW(), 'T恤完工入库-BLK-M'),
('TXN2026102002', 1, 3, 2, 2,  'C20261020',  80.000, 'pr_wo', 1, '2026-10-20 16:01:00', 'admin', NOW(), 'T恤完工入库-BLK-L'),
('TXN2026102003', 1, 3, 2, 3,  'C20261020', 120.000, 'pr_wo', 1, '2026-10-20 16:02:00', 'admin', NOW(), 'T恤完工入库-WHT-M'),
('TXN2026102004', 1, 3, 2, 4,  'C20261020',  60.000, 'pr_wo', 1, '2026-10-20 16:03:00', 'admin', NOW(), 'T恤完工入库-WHT-L'),
-- 销售出库（SO3 / SO1）
('TXN2026101001', 2, 4, 2, 10, 'C20261004', 500.000, 'sd_order', 3, '2026-10-10 10:00:00', 'admin', NOW(), 'SO3 袜子出库-BLK'),
('TXN2026101002', 2, 4, 2, 11, 'C20261004', 400.000, 'sd_order', 3, '2026-10-10 10:01:00', 'admin', NOW(), 'SO3 袜子出库-WHT'),
('TXN2026101003', 2, 4, 2, 12, 'C20261004', 300.000, 'sd_order', 3, '2026-10-10 10:02:00', 'admin', NOW(), 'SO3 袜子出库-GRY'),
('TXN2026102101', 2, 4, 2, 1,  'C20261020', 100.000, 'sd_order', 1, '2026-10-21 09:00:00', 'admin', NOW(), 'SO1 T恤出库-BLK-M'),
('TXN2026102102', 2, 4, 2, 2,  'C20261020',  80.000, 'sd_order', 1, '2026-10-21 09:01:00', 'admin', NOW(), 'SO1 T恤出库-BLK-L'),
('TXN2026102103', 2, 4, 2, 3,  'C20261020', 120.000, 'sd_order', 1, '2026-10-21 09:02:00', 'admin', NOW(), 'SO1 T恤出库-WHT-M'),
('TXN2026102104', 2, 4, 2, 4,  'C20261020',  60.000, 'sd_order', 1, '2026-10-21 09:03:00', 'admin', NOW(), 'SO1 T恤出库-WHT-L'),
-- 退货入库（客户退回）
('TXN2026101201', 1, 7, 2, 10, 'C20261004',  20.000, 'sh_return', 2, '2026-10-12 15:00:00', 'admin', NOW(), '袜子退货入库-颜色不符'),
('TXN2026102301', 1, 7, 2, 1,  'C20261020',   5.000, 'sh_return', 1, '2026-10-23 15:00:00', 'admin', NOW(), 'T恤退货入库-尺码偏小'),
-- 盘点（盘盈/盘亏）
('TXN2026101401', 2, 6, 1, 3, '',             5.000, 'stock_check', NULL, '2026-10-14 18:00:00', 'admin', NOW(), '拉链盘亏'),
('TXN2026101402', 1, 5, 1, 5, '',             1.000, 'stock_check', NULL, '2026-10-14 18:01:00', 'admin', NOW(), '缝纫线盘盈');

-- 库存结存（严格等于上列流水的汇总：结存 == Σ入 − Σ出）
INSERT INTO st_inventory (item_type, item_id, batch_no, warehouse_id, qty, safe_stock, create_by, create_time, update_time) VALUES
-- 物料
(1, 1, 'F20260928', 1,  17.600,  50.000, 'admin', NOW(), NOW()),   -- 涤纶网眼布 320-302.4
(1, 2, 'F20260918', 1,  50.160,   0.000, 'admin', NOW(), NOW()),   -- 弹力棉针织布 150-99.84
(1, 2, 'F20260928', 1, 100.000, 150.000, 'admin', NOW(), NOW()),   -- 弹力棉针织布 400-300
(1, 3, '',          1, 145.000, 100.000, 'admin', NOW(), NOW()),   -- 树脂拉链 400-250-5
(1, 4, '',          1, 240.000, 100.000, 'admin', NOW(), NOW()),   -- 织唛主标 600-360
(1, 5, '',          1,  21.416,  10.000, 'admin', NOW(), NOW()),   -- 涤棉缝纫线 50-12.24-7.344-3-7+1
(1, 6, 'F20260918', 1, 140.000,  50.000, 'admin', NOW(), NOW()),   -- 速干锦纶布 140
(1, 7, '',          1,  60.000,  40.000, 'admin', NOW(), NOW()),   -- 氨纶弹力布 180-120
(1, 8, '',          1, 100.000,  50.000, 'admin', NOW(), NOW()),   -- 洗水标 200-100
-- 成品 SKU
(2, 1,  'C20261020', 1,   5.000, 20.000, 'admin', NOW(), NOW()),   -- ST2026001-BLK-M 100-100+5
(2, 2,  'C20261020', 1,   0.000,  0.000, 'admin', NOW(), NOW()),   -- ST2026001-BLK-L 80-80
(2, 3,  'C20261020', 1,   0.000,  0.000, 'admin', NOW(), NOW()),   -- ST2026001-WHT-M 120-120
(2, 4,  'C20261020', 1,   0.000,  0.000, 'admin', NOW(), NOW()),   -- ST2026001-WHT-L 60-60
(2, 10, 'C20261004', 1,  20.000,100.000, 'admin', NOW(), NOW()),   -- ST2026003-BLK-F 500-500+20
(2, 11, 'C20261004', 1,   0.000,  0.000, 'admin', NOW(), NOW()),   -- ST2026003-WHT-F 400-400
(2, 12, 'C20261004', 1,   0.000,  0.000, 'admin', NOW(), NOW());   -- ST2026003-GRY-F 300-300

-- =============================================================================
-- 结束
-- =============================================================================
