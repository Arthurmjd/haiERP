-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 采购模块（Purchase）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- -----------------------------------------------------------------------------
-- 本模块含 2 张表 + 1 个视图：
--   pu_purchase_order       采购单（主表）
--   pu_purchase_order_item  采购明细
--   v_material_last_price   物料最近采购价视图（供成本核算取价）
-- -----------------------------------------------------------------------------
-- 设计约定：
--   1) 不做独立「请购单」表：需求由 BOM×订单−库存 生成「采购建议」（视图/报表）。
--   2) 采购明细带可选 source_order_id，记录「这批料给哪张订单买的」。
--   3) 到货收货归「库存模块」处理（入库单）；采购单只用 status 记到货进度。
--   4) MVP 不做分批到货明细行，用采购单状态体现。
-- -----------------------------------------------------------------------------
-- 审计字段规范（对齐底座 RuoYi BaseEntity，v1.2 起）：
--   · 主表：create_by + create_time + update_by + update_time + remark + **del_flag**；
--   · 明细表：只记 create_by + create_time。
-- -----------------------------------------------------------------------------
-- v1.1 修订（2026-10-14，P0 修复 A4）：
--   · pu_purchase_order_item 增复合索引 idx_material_item(material_id, item_id)；
--   · 新增视图 v_material_last_price：材料 → 最近一次「已到货」采购单价（无则回退 ref_price）。
-- v1.2 修订（2026-10-14，P1 修复 B2）：补齐审计字段 + 单据逻辑删除。
-- v1.3 修订（2026-10-14，P1 修复 B4）：
--   · pu_purchase_order 增 actual_arrival_date（实际到货日）——只有 status 无法度量
--     采购周期（下单→到货）与采购准交率（到货 vs 承诺），这是「快速反应」的核心指标；
--   · 顺带修正种子的 order_date / expect_date：原种子让 4 张已到货采购单的
--     order_date 与入库日期同一天（采购周期恒为 0 天，指标失去意义），
--     现按「现货面料 2~7 天」重排，并保证 order_date ≤ 到货日
--     且 ≥ 其 source_order_id 指向的订单下单日（不让采购早于订单存在）；
--   · v_material_last_price 由 DROP VIEW + CREATE VIEW 改为 CREATE OR REPLACE VIEW，
--     避免重跑本模块时把依赖它的汇总视图（v_mrp_demand）连带打坏；
--     同时补上 del_flag='0' 过滤（底座无 @TableLogic，逻辑删除行必须手写过滤）。
-- -----------------------------------------------------------------------------
-- MRP 简化公式：需求量 = Σ(订单数量 × BOM单件用量 × (1+损耗率)) − 库存 − 在途
-- 依赖：md_supplier / md_material（主数据）、sd_order（销售）
-- 编写日期：2026-10-13　修订：2026-10-14
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS pu_purchase_order_item;
DROP TABLE IF EXISTS pu_purchase_order;

-- -----------------------------------------------------------------------------
-- 1. 采购单（主表）
-- -----------------------------------------------------------------------------
CREATE TABLE pu_purchase_order (
  po_id                BIGINT        NOT NULL AUTO_INCREMENT COMMENT '采购单ID',
  po_no                VARCHAR(30)   NOT NULL                COMMENT '采购单号',
  supplier_id          BIGINT        NOT NULL                COMMENT '供应商ID（md_supplier）',
  order_date           DATE          DEFAULT NULL            COMMENT '下单日期',
  expect_date          DATE          DEFAULT NULL            COMMENT '预计到货日期（供应商承诺）',
  actual_arrival_date  DATE          DEFAULT NULL            COMMENT '实际到货日期（全部到货时回填，用于采购周期与准交率）',
  status               TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0待下单 1已下单 2部分到货 3已到货 9已取消',
  total_amount         DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '采购总额',
  buyer_user_id        BIGINT        DEFAULT NULL            COMMENT '采购员（关联登录账号）',
  create_by            VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time          DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by            VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time          DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark               VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  del_flag             CHAR(1)       NOT NULL DEFAULT '0'    COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (po_id),
  UNIQUE KEY uk_po_no (po_no),
  KEY idx_supplier (supplier_id),
  KEY idx_status (status),
  KEY idx_expect (expect_date),
  KEY idx_arrival (actual_arrival_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购单主表';

-- -----------------------------------------------------------------------------
-- 2. 采购明细
-- -----------------------------------------------------------------------------
CREATE TABLE pu_purchase_order_item (
  item_id         BIGINT        NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  po_id           BIGINT        NOT NULL                COMMENT '采购单ID',
  material_id     BIGINT        NOT NULL                COMMENT '物料ID（md_material）',
  qty             DECIMAL(12,3) NOT NULL DEFAULT 0.000  COMMENT '采购数量',
  unit_price      DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '单价',
  amount          DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '金额 = 数量 × 单价',
  source_order_id BIGINT        DEFAULT NULL            COMMENT '来源订单ID（可空，便于跟单）',
  create_by       VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time     DATETIME      DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (item_id),
  KEY idx_po (po_id),
  KEY idx_material (material_id),
  KEY idx_material_item (material_id, item_id),
  KEY idx_source_order (source_order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='采购单明细';

-- -----------------------------------------------------------------------------
-- 3. 视图：物料最近采购价（成本核算取价入口）
--    口径：取该物料「最近一次已到货/部分到货」采购单的单价；无记录则回退 md_material.ref_price
--    用 CREATE OR REPLACE：v_mrp_demand 等汇总视图依赖本视图，不能 DROP 重建。
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_material_last_price AS
SELECT
  m.material_id,
  m.material_code,
  m.material_name,
  m.unit,
  COALESCE(last.unit_price, m.ref_price)                                 AS unit_price,
  last.po_date                                                           AS last_po_date,
  CASE WHEN last.unit_price IS NULL THEN 0 ELSE 1 END                    AS price_src  -- 0=参考价 1=最近采购价
FROM md_material m
LEFT JOIN (
  SELECT i.material_id,
         i.unit_price,
         p.order_date AS po_date,
         ROW_NUMBER() OVER (PARTITION BY i.material_id ORDER BY p.order_date DESC, i.item_id DESC) AS rn
  FROM pu_purchase_order_item i
  JOIN pu_purchase_order p ON p.po_id = i.po_id
  WHERE p.status IN (2, 3)
    AND p.del_flag = '0'
) last ON last.material_id = m.material_id AND last.rn = 1;

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 示例数据（SQL 种子）
-- =============================================================================

-- 采购单（5 张：4 张已到货 + 1 张在途）
--   采购周期 = actual_arrival_date − order_date：PO1 2天 / PO2 3天 / PO3 7天 / PO4 4天（均值 4 天，现货面料）
--   采购准交 = actual_arrival_date ≤ expect_date：PO1✓ PO2✗(晚1天) PO3✓ PO4✓ → 3/4 = 75%
--   约束：order_date ≤ actual_arrival_date，且 ≥ 其明细 source_order_id 指向订单的下单日
--         （不让采购早于订单存在）；PO5 在途未到货，actual_arrival_date 留空。
INSERT INTO pu_purchase_order (po_id, po_no, supplier_id, order_date, expect_date, actual_arrival_date, status, total_amount, buyer_user_id, create_by, create_time, remark) VALUES
(1, 'PO2026001', 1, '2026-09-26', '2026-09-29', '2026-09-28', 3, 11200.00, 9, 'admin', NOW(), 'T恤网眼布/长裤弹力棉，提前 1 天到货'),
(2, 'PO2026002', 2, '2026-09-25', '2026-09-27', '2026-09-28', 3,   970.00, 9, 'admin', NOW(), '拉链/织唛/缝纫线，比承诺晚 1 天到货'),
(3, 'PO2026003', 1, '2026-09-11', '2026-09-19', '2026-09-18', 3,  5750.00, 9, 'admin', NOW(), '袜子弹力棉/背心速干锦纶，提前 1 天到货'),
(4, 'PO2026004', 3, '2026-09-28', '2026-10-03', '2026-10-02', 3,  4700.00, 9, 'admin', NOW(), '紧身裤氨纶布/洗水标备货（SO2026005 尚未下单，明细 source_order_id 留空）'),
(5, 'PO2026005', 1, '2026-10-12', '2026-10-26', NULL,         1,  1760.00, 9, 'admin', NOW(), '背心速干锦纶补货，在途未到货');

INSERT INTO pu_purchase_order_item (po_id, material_id, qty, unit_price, amount, source_order_id, create_by, create_time) VALUES
-- PO1 面料：T恤网眼布（为 SO2026001）+ 长裤弹力棉（为 SO2026002）
(1, 1, 320.000, 12.50, 4000.00, 1, 'admin', NOW()),
(1, 2, 400.000, 18.00, 7200.00, 2, 'admin', NOW()),
-- PO2 辅料：拉链（为 SO2026002）+ 织唛（为 SO2026001）+ 缝纫线（通用备料）
(2, 3, 400.000,  1.20,  480.00, 2, 'admin', NOW()),
(2, 4, 600.000,  0.15,   90.00, 1, 'admin', NOW()),
(2, 5,  50.000,  8.00,  400.00, NULL, 'admin', NOW()),
-- PO3 面料：袜子弹力棉（为 SO2026003）+ 背心速干锦纶（备货，SO2026004 尚未下单）
(3, 2, 150.000, 17.80, 2670.00, 3, 'admin', NOW()),
(3, 6, 140.000, 22.00, 3080.00, NULL, 'admin', NOW()),
-- PO4 面料/辅料：紧身裤氨纶布 + 洗水标（备货，SO2026005 尚未下单）
(4, 7, 180.000, 26.00, 4680.00, NULL, 'admin', NOW()),
(4, 8, 200.000,  0.10,   20.00, NULL, 'admin', NOW()),
-- PO5 面料（在途）：背心补货
(5, 6,  80.000, 22.00, 1760.00, NULL, 'admin', NOW());

-- 视图校验（应得到：m1=12.50 / m2=18.00 / m3=1.20 / m4=0.15 / m5=8.00 / m6=22.00 / m7=26.00 / m8=0.10）
-- SELECT * FROM v_material_last_price;

-- =============================================================================
-- 结束
-- =============================================================================
