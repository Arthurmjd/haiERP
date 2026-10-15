-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 生产模块（Production）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- -----------------------------------------------------------------------------
-- 本模块含 2 张表：
--   pr_work_order           生产工单（主表）
--   pr_work_order_process   生产工序（进度明细，由工艺路线展开）
-- -----------------------------------------------------------------------------
-- 设计约定：
--   1) 工单粒度：一个订单的一个款式 = 一个工单。
--   2) 工单创建时按款式工艺路线（md_style_process）复制出工序行，并**快照件单价**。
--   3) 外发：在工序行加 is_outsourced 标记 + outsource_price（外发加工单价），不建独立外发单。
--   4) 领料不建独立表，走库存模块的出库流水（stock_txn）。
--   5) 记录实际开/完工日期，用于准交率统计。
-- -----------------------------------------------------------------------------
-- v1.2 修订（2026-10-14，P1 修复 B1 / B2）：
--   · B1：pr_work_order_process 新增 unit_price（工单创建时从 md_style_process 快照，
--         后续调价不回溯污染历史人工成本）与 outsource_price（外发加工单价，
--         外发是按件付加工费给外协厂，单价与内部计件价不同）；
--   · B2：pr_work_order 补 create_by/update_by + del_flag（单据表逻辑删除）；
--         pr_work_order_process 补 create_by（明细/流水表只记创建者）。
-- -----------------------------------------------------------------------------
-- 下游联动：done_qty → hr_piece_wage（计件工资）；bad_qty → 合格率（替代质检）
-- 依赖：sd_order（销售）、md_style / md_process（主数据）
-- 编写日期：2026-10-13　修订：2026-10-14
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS pr_work_order_process;
DROP TABLE IF EXISTS pr_work_order;

-- -----------------------------------------------------------------------------
-- 1. 生产工单（主表）
-- -----------------------------------------------------------------------------
CREATE TABLE pr_work_order (
  wo_id             BIGINT       NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  wo_no             VARCHAR(30)  NOT NULL                COMMENT '工单号',
  order_id          BIGINT       DEFAULT NULL            COMMENT '来源订单ID（sd_order）',
  style_id          BIGINT       NOT NULL                COMMENT '款式ID',
  qty               INT          NOT NULL DEFAULT 0      COMMENT '计划生产数量',
  plan_start_date   DATE         DEFAULT NULL            COMMENT '计划开工日期',
  plan_end_date     DATE         DEFAULT NULL            COMMENT '计划完工日期',
  actual_start_date DATE         DEFAULT NULL            COMMENT '实际开工日期',
  actual_end_date   DATE         DEFAULT NULL            COMMENT '实际完工日期',
  status            TINYINT      NOT NULL DEFAULT 0      COMMENT '状态：0待开工 1生产中 2已完工 9已取消',
  create_by         VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time       DATETIME     DEFAULT NULL            COMMENT '创建时间',
  update_by         VARCHAR(64)  DEFAULT ''              COMMENT '更新者',
  update_time       DATETIME     DEFAULT NULL            COMMENT '更新时间',
  remark            VARCHAR(255) DEFAULT NULL            COMMENT '备注',
  del_flag          CHAR(1)      NOT NULL DEFAULT '0'    COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (wo_id),
  UNIQUE KEY uk_wo_no (wo_no),
  KEY idx_order (order_id),
  KEY idx_style (style_id),
  KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产工单主表';

-- -----------------------------------------------------------------------------
-- 2. 生产工序（进度明细）
--    单价说明：unit_price = 内部件单价（创建工单时快照自 md_style_process）；
--              outsource_price = 外发加工单价（仅 is_outsourced=1 时有效，否则为 0）。
--    成本取价：IF(is_outsourced=1 AND outsource_price>0, outsource_price, unit_price)
-- -----------------------------------------------------------------------------
CREATE TABLE pr_work_order_process (
  id              BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
  wo_id           BIGINT        NOT NULL                COMMENT '工单ID',
  process_id      BIGINT        NOT NULL                COMMENT '工序ID（md_process）',
  seq             INT           NOT NULL DEFAULT 0      COMMENT '工序顺序',
  plan_qty        INT           NOT NULL DEFAULT 0      COMMENT '计划数量',
  done_qty        INT           NOT NULL DEFAULT 0      COMMENT '完成数量',
  bad_qty         INT           NOT NULL DEFAULT 0      COMMENT '不良数量',
  is_outsourced   TINYINT       NOT NULL DEFAULT 0      COMMENT '是否外发：0否 1是',
  unit_price      DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '内部件单价（快照，取自 md_style_process）',
  outsource_price DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '外发加工单价（is_outsourced=1 时有效）',
  status          TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0待开工 1进行中 2已完成',
  create_by       VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time     DATETIME      DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_wo_process (wo_id, process_id),
  KEY idx_wo (wo_id),
  KEY idx_process (process_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='生产工序进度';

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 示例数据（SQL 种子）
-- =============================================================================

-- 工单 1：订单1 T恤 360 件 —— 已完工
INSERT INTO pr_work_order (wo_id, wo_no, order_id, style_id, qty, plan_start_date, plan_end_date, actual_start_date, actual_end_date, status, create_by, create_time) VALUES
(1, 'WO2026001', 1, 1,  360, '2026-10-13', '2026-10-22', '2026-10-13', '2026-10-20', 2, 'admin', NOW());
INSERT INTO pr_work_order_process (wo_id, process_id, seq, plan_qty, done_qty, bad_qty, is_outsourced, unit_price, outsource_price, status, create_by) VALUES
(1, 1, 1,  360,  360,  4, 0, 1.20, 0.00, 2, 'admin'),
(1, 2, 2,  360,  360,  3, 0, 3.50, 0.00, 2, 'admin'),
(1, 3, 3,  360,  360,  2, 0, 0.60, 0.00, 2, 'admin');

-- 工单 2：订单2 长裤 350 条 —— 生产中（缝制外发，外发单价 6.00）
INSERT INTO pr_work_order (wo_id, wo_no, order_id, style_id, qty, plan_start_date, plan_end_date, actual_start_date, actual_end_date, status, create_by, create_time) VALUES
(2, 'WO2026002', 2, 2,  350, '2026-10-14', '2026-10-26', '2026-10-14', NULL, 1, 'admin', NOW());
INSERT INTO pr_work_order_process (wo_id, process_id, seq, plan_qty, done_qty, bad_qty, is_outsourced, unit_price, outsource_price, status, create_by) VALUES
(2, 1, 1,  350,  350,  3, 0, 1.50, 0.00, 2, 'admin'),
(2, 2, 2,  350,  200,  2, 1, 4.80, 6.00, 1, 'admin'),
(2, 3, 3,  350,    0,  0, 0, 0.80, 0.00, 0, 'admin');

-- 工单 3：订单3 袜子 1200 双 —— 已完工（仅裁剪/缝制两道工序）
INSERT INTO pr_work_order (wo_id, wo_no, order_id, style_id, qty, plan_start_date, plan_end_date, actual_start_date, actual_end_date, status, create_by, create_time) VALUES
(3, 'WO2026003', 3, 3, 1200, '2026-09-20', '2026-10-05', '2026-09-20', '2026-10-04', 2, 'admin', NOW());
INSERT INTO pr_work_order_process (wo_id, process_id, seq, plan_qty, done_qty, bad_qty, is_outsourced, unit_price, outsource_price, status, create_by) VALUES
(3, 1, 1, 1200, 1200, 10, 0, 0.10, 0.00, 2, 'admin'),
(3, 2, 2, 1200, 1200,  8, 0, 0.35, 0.00, 2, 'admin');

-- 工单 4：订单5 紧身裤 150 条 —— 生产中
INSERT INTO pr_work_order (wo_id, wo_no, order_id, style_id, qty, plan_start_date, plan_end_date, actual_start_date, actual_end_date, status, create_by, create_time) VALUES
(4, 'WO2026004', 5, 5,  150, '2026-10-08', '2026-10-25', '2026-10-08', NULL, 1, 'admin', NOW());
INSERT INTO pr_work_order_process (wo_id, process_id, seq, plan_qty, done_qty, bad_qty, is_outsourced, unit_price, outsource_price, status, create_by) VALUES
(4, 1, 1,  150,  150,  2, 0, 1.40, 0.00, 2, 'admin'),
(4, 2, 2,  150,   80,  1, 0, 4.20, 0.00, 1, 'admin'),
(4, 3, 3,  150,    0,  0, 0, 0.70, 0.00, 0, 'admin');

-- =============================================================================
-- 结束
-- =============================================================================
