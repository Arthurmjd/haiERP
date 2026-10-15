-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 成本核算模块（Cost Settlement）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- -----------------------------------------------------------------------------
-- 本模块含 1 张表：
--   fn_cost_settlement   订单级成本结算单
-- -----------------------------------------------------------------------------
-- 设计约定：
--   1) 核算粒度：**订单 × 款式**级（一张订单的一个款式 = 一条结算单）；
--      唯一键为 (order_id, style_id)，支持「一单多款」拆分结算（v1.1 修复 A2）。
--   2) 单表存储：MVP 不做「成本明细子表」，四类成本各存一个汇总金额字段。
--   3) 材料单价：取「该物料最近一次采购价」（视图 v_material_last_price），无记录回退 ref_price。
--   4) 制造费用：按 (材料 + 人工) × overhead_rate(%) 计提，MVP 可手填/调比例。
--   5) 人工成本：按工序计件，单价取**工单工序的快照价**（pr_work_order_process.unit_price，
--      工单创建时已从 md_style_process 冻结，v1.2 起不再实时回查工艺表）。
--   6) 结算单可重算（试算 status=0），确认后 status=1 不再自动改，供成本反哺报价。
-- -----------------------------------------------------------------------------
-- 成本口径（本模块的核心公式，务必理解）：
--   材料成本 material_cost = Σ[ 订单数量 × BOM单件用量 × (1 + 损耗率/100) × 材料单价 ]
--   人工成本 labor_cost    = Σ[ 工序完成数 × 取价 ]，
--                            取价 = IF(is_outsourced=1 AND outsource_price>0,
--                                     outsource_price, unit_price)   ← 来自工单工序快照
--   制造费用 overhead_cost = (材料成本 + 人工成本) × overhead_rate / 100
--   总成本   total_cost    = 材料 + 人工 + 制造费用
--   单件成本 unit_cost     = 总成本 / 数量
--   毛利     gross_profit  = 收入(订单金额) − 总成本
--   毛利率   gross_margin  = 毛利 / 收入 × 100
-- -----------------------------------------------------------------------------
-- 反哺报价闭环：本表 unit_cost → sd_quotation_item.cost_ref（source_settle_id 指向本表）
-- 依赖：sd_order（销售）、md_bom / md_material（主数据）、v_material_last_price（采购取价）、
--       pr_work_order_process（工序完成数 + 件单价快照）
-- -----------------------------------------------------------------------------
-- 审计字段规范（v1.2 起）：结算单属「单据表」→ create_by + update_by + update_time
--   + remark + **del_flag**（逻辑删除；已确认结算单不建议删除，仅作废标记）。
-- 编写日期：2026-10-14　修订：2026-10-14（B1 工价快照 / B2 审计字段，种子 5 张）
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS fn_cost_settlement;

-- -----------------------------------------------------------------------------
-- 1. 订单级成本结算单
-- -----------------------------------------------------------------------------
CREATE TABLE fn_cost_settlement (
  settle_id      BIGINT        NOT NULL AUTO_INCREMENT COMMENT '结算单ID',
  settle_no      VARCHAR(30)   NOT NULL                COMMENT '结算单号',
  order_id       BIGINT        NOT NULL                COMMENT '订单ID（sd_order）',
  style_id       BIGINT        NOT NULL                COMMENT '款式ID（核算粒度 = 订单 × 款式）',
  qty            INT           NOT NULL DEFAULT 0      COMMENT '核算数量',
  material_cost  DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '材料成本',
  labor_cost     DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '人工成本（计件，单价取工单工序快照）',
  overhead_rate  DECIMAL(5,2)  NOT NULL DEFAULT 0.00   COMMENT '制造费用计提比例(%)',
  overhead_cost  DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '制造费用',
  total_cost     DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '总成本 = 材料 + 人工 + 制造费用',
  unit_cost      DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '单件成本 = 总成本 / 数量',
  revenue        DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '收入（订单金额）',
  gross_profit   DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '毛利 = 收入 − 总成本',
  gross_margin   DECIMAL(6,2)  NOT NULL DEFAULT 0.00   COMMENT '毛利率(%) = 毛利 / 收入 × 100',
  status         TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0试算 1已确认',
  calc_time      DATETIME      DEFAULT NULL            COMMENT '核算时间',
  create_by      VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time    DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by      VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time    DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark         VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  del_flag       CHAR(1)       NOT NULL DEFAULT '0'    COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (settle_id),
  UNIQUE KEY uk_settle_no (settle_no),
  UNIQUE KEY uk_order_style (order_id, style_id),
  KEY idx_style (style_id),
  KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='订单级成本结算单';

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 示例数据（SQL 种子，数据与前面 7 个模块对齐，可 SQL 复算核对）
--   材料取价：v_material_last_price（= 最近采购价，回退 md_material.ref_price）
--   人工取价：分两种情况 ——
--     ① 已排产订单（有 pr_work_order）：Σ[工序计划量 × IF(is_outsourced AND
--        outsource_price>0, outsource_price, unit_price)]，单价为工单创建时的快照；
--     ② 未排产订单（无工单，如 SO2026004）：无工序可依，按款式工艺路线
--        md_style_process.unit_price × 数量 的理论成本试算（报价前预估用）。
--   口径：均为「理论成本」（计划量 × 标准/外发工价），故在生产中的订单为试算；
--         实际成本应在工单完工后按 done_qty 重算。
-- =============================================================================

-- -----------------------------------------------------------------------------
-- 结算单 1：订单 SO2026001（T恤 360 件，收入 12600）—— 已确认
--   材料：布 360×0.800×1.05×12.50 = 3780.00
--         织唛 360×1.000×1.00×0.15 =   54.00
--         缝纫线 360×0.020×1.02×8.00 = 58.752 ≈ 58.75
--         → 材料合计 3892.75
--   人工：360 × (裁剪1.20 + 缝制3.50 + 整烫0.60 = 5.30) = 1908.00
--   制造费用：(3892.75 + 1908.00) × 8% = 464.06
--   总成本：3892.75 + 1908.00 + 464.06 = 6264.81      单件成本：6264.81 / 360 = 17.40
--   毛利：12600.00 − 6264.81 = 6335.19                毛利率：50.28%
-- -----------------------------------------------------------------------------
INSERT INTO fn_cost_settlement
(settle_no, order_id, style_id, qty, material_cost, labor_cost, overhead_rate, overhead_cost,
 total_cost, unit_cost, revenue, gross_profit, gross_margin, status, calc_time, create_by, create_time, remark) VALUES
('CS2026001', 1, 1, 360, 3892.75, 1908.00, 8.00, 464.06,
 6264.81, 17.40, 12600.00, 6335.19, 50.28, 1, NOW(), 'admin', NOW(), 'T恤订单结算（已确认，反哺报价 QT2026003）');

-- -----------------------------------------------------------------------------
-- 结算单 2：订单 SO2026002（长裤 350 条，收入 19250）—— 试算（生产中，缝制外发）
--   材料：布 350×1.200×1.06×18.00 = 8013.60 / 拉链 350×1.000×1.01×1.20 = 424.20
--         缝纫线 350×0.030×1.02×8.00 = 85.68    → 材料合计 8523.48
--   人工：350 × (裁剪1.50 + 缝制【外发6.00】+ 整烫0.80 = 8.30) = 2905.00
--         ※ 缝制工序 is_outsourced=1，按外发加工单价 6.00 计（内部参考价 4.80）
--   制造费用：(8523.48 + 2905.00) × 8% = 914.28
--   总成本：12342.76      单件成本：35.27      毛利：6907.24      毛利率：35.88%
-- -----------------------------------------------------------------------------
INSERT INTO fn_cost_settlement
(settle_no, order_id, style_id, qty, material_cost, labor_cost, overhead_rate, overhead_cost,
 total_cost, unit_cost, revenue, gross_profit, gross_margin, status, calc_time, create_by, create_time, remark) VALUES
('CS2026002', 2, 2, 350, 8523.48, 2905.00, 8.00, 914.28,
 12342.76, 35.27, 19250.00, 6907.24, 35.88, 0, NOW(), 'admin', NOW(), '长裤订单试算（缝制外发，按外发单价计）');

-- -----------------------------------------------------------------------------
-- 结算单 3：订单 SO2026003（袜子 1200 双，收入 7200）—— 已确认
--   材料：布 1200×0.080×1.04×18.00 = 1797.12 / 缝纫线 1200×0.010×1.02×8.00 = 97.92
--         → 材料合计 1895.04
--   人工：1200 × (裁剪0.10 + 缝制0.35 = 0.45) = 540.00
--   制造费用：(1895.04 + 540.00) × 8% = 194.80
--   总成本：2629.84      单件成本：2.19      毛利：4570.16      毛利率：63.47%
-- -----------------------------------------------------------------------------
INSERT INTO fn_cost_settlement
(settle_no, order_id, style_id, qty, material_cost, labor_cost, overhead_rate, overhead_cost,
 total_cost, unit_cost, revenue, gross_profit, gross_margin, status, calc_time, create_by, create_time, remark) VALUES
('CS2026003', 3, 3, 1200, 1895.04, 540.00, 8.00, 194.80,
 2629.84, 2.19, 7200.00, 4570.16, 63.47, 1, NOW(), 'admin', NOW(), '袜子订单结算（已确认，反哺报价 QT2026004）');

-- -----------------------------------------------------------------------------
-- 结算单 4：订单 SO2026004（背心 200 件，收入 5600）—— 试算（待确认，未排产）
--   注意：该订单尚未排产（无 pr_work_order），人工成本**无工单工序可依**，
--         故按款式工艺路线（md_style_process）的理论工价试算，作为报价前成本预估。
--   材料：速干锦纶 200×0.600×1.05×22.00 = 2772.00 / 织唛 200×1.000×1.00×0.15 = 30.00
--         缝纫线 200×0.015×1.02×8.00 = 24.48    → 材料合计 2826.48
--   人工：200 × (1.00 + 2.80 + 0.50 = 4.30) = 860.00
--   制造费用：(2826.48 + 860.00) × 8% = 294.92
--   总成本：3981.40      单件成本：19.91      毛利：1618.60      毛利率：28.90%
-- -----------------------------------------------------------------------------
INSERT INTO fn_cost_settlement
(settle_no, order_id, style_id, qty, material_cost, labor_cost, overhead_rate, overhead_cost,
 total_cost, unit_cost, revenue, gross_profit, gross_margin, status, calc_time, create_by, create_time, remark) VALUES
('CS2026004', 4, 4, 200, 2826.48, 860.00, 8.00, 294.92,
 3981.40, 19.91, 5600.00, 1618.60, 28.90, 0, NOW(), 'admin', NOW(), '背心订单试算（未排产，按工艺路线理论成本）');

-- -----------------------------------------------------------------------------
-- 结算单 5：订单 SO2026005（紧身裤 150 条，收入 6750）—— 试算（生产中）
--   材料：氨纶布 150×1.000×1.06×26.00 = 4134.00 / 洗水标 150×1.000×1.00×0.10 = 15.00
--         缝纫线 150×0.025×1.02×8.00 = 30.60    → 材料合计 4179.60
--   人工：150 × (1.40 + 4.20 + 0.70 = 6.30) = 945.00
--   制造费用：(4179.60 + 945.00) × 8% = 409.97
--   总成本：5534.57      单件成本：36.90      毛利：1215.43      毛利率：18.01%
-- -----------------------------------------------------------------------------
INSERT INTO fn_cost_settlement
(settle_no, order_id, style_id, qty, material_cost, labor_cost, overhead_rate, overhead_cost,
 total_cost, unit_cost, revenue, gross_profit, gross_margin, status, calc_time, create_by, create_time, remark) VALUES
('CS2026005', 5, 5, 150, 4179.60, 945.00, 8.00, 409.97,
 5534.57, 36.90, 6750.00, 1215.43, 18.01, 0, NOW(), 'admin', NOW(), '紧身裤订单试算（生产中）');

-- =============================================================================
-- 附 1：成本反哺报价 —— 参考 SQL
--   建议最低报价 = 单件成本 × (1 + 目标毛利率)；也可直接回读已确认结算单
-- -----------------------------------------------------------------------------
-- SELECT c.style_id,
--        c.unit_cost,
--        ROUND(c.unit_cost * 1.20, 2) AS suggest_price_20pct,
--        c.gross_margin              AS actual_margin
-- FROM fn_cost_settlement c
-- WHERE c.status = 1
-- ORDER BY c.style_id;
-- =============================================================================

-- =============================================================================
-- 附 2：成本明细复算 SQL（用视图取价 + 工序件单价快照，验证种子材料/人工成本）
-- -----------------------------------------------------------------------------
-- -- 材料
-- SELECT oi.style_id,
--        ROUND(SUM(oi.qty * b.qty * (1 + b.loss_rate/100) * p.unit_price), 2) AS material_cost
-- FROM sd_order_item oi
-- JOIN md_bom b                ON b.style_id = oi.style_id
-- JOIN v_material_last_price p ON p.material_id = b.material_id
-- WHERE oi.order_id = 1 AND oi.style_id = 1
-- GROUP BY oi.style_id;
--
-- -- 人工（按工单工序快照价）
-- SELECT w.order_id, w.style_id,
--        ROUND(SUM(w.qty * IF(wp.is_outsourced = 1 AND wp.outsource_price > 0,
--                             wp.outsource_price, wp.unit_price)), 2) AS labor_cost
-- FROM pr_work_order w
-- JOIN pr_work_order_process wp ON wp.wo_id = w.wo_id
-- GROUP BY w.order_id, w.style_id;
-- =============================================================================

-- =============================================================================
-- 结束
-- =============================================================================
