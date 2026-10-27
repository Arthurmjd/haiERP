-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 销售模块（Sales）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- 产品线：上衣 / 裤子 / 袜子
-- -----------------------------------------------------------------------------
-- 本模块含 4 张表：
--   sd_order           销售订单（主表）
--   sd_order_item      订单明细（子表 = 尺码矩阵，一行 = 一个色码组合）
--   sd_quotation       报价单（主表，款式级）
--   sd_quotation_item  报价明细（含成本参考 cost_ref，承接「成本反哺报价」）
-- -----------------------------------------------------------------------------
-- 设计约定：
--   1) 订单 = 主表 + 明细子表；尺码矩阵落子表，杜绝主表列膨胀。
--   2) 单价放明细行（最灵活）；明细唯一键 (order_id, style_id, color_id, size_id)。
--   3) 报价单独立成表，成交后转订单；报价为款式级（不细分到色码）。
--   4) 本期不做打样，故订单无 need_sample 字段。
--   5) 订单数据由 SQL 种子直接入库（演示）。
-- -----------------------------------------------------------------------------
-- 审计字段规范（对齐底座 RuoYi BaseEntity，v1.2 起）：
--   · 主表（订单/报价）：create_by + create_time + update_by + update_time + remark + **del_flag**；
--   · 明细子表：只记 create_by + create_time（随主单整体增删）。
--   · del_flag char(1) '0'存在 / '2'删除；单据号不重复使用，故唯一键不受逻辑删除影响。
-- -----------------------------------------------------------------------------
-- v1.1 修订（2026-10-14，P0 修复 A5）：
--   · sd_quotation_item 新增 cost_src（参考成本来源）+ source_settle_id（来源结算单）；
--   · 口径统一：cost_ref 优先取「该款式最近一次成本结算的 unit_cost」，无历史时用 BOM 预估；
--   · 种子补 2 张「反哺报价」单（QT2026003/QT2026004），cost_ref 直接来自结算单，闭环可追溯。
-- v1.2 修订（2026-10-14，P1 修复 B2）：补齐审计字段 + 单据逻辑删除。
-- v1.3 修订（2026-10-14，P1 修复 B5 配套）：调整种子交期 —— 原种子 5 张订单全部准交，
--   会让 v_order_ontime 的准交率恒为 100%、指标失去演示与校验价值；现改为
--   SO1 延期 3 天、SO2 逾期未交付、SO3 提前 5 天，覆盖三类状态（交期字段无外键，改动无涟漪）。
-- 依赖：md_customer / md_style / md_color / md_size（主数据模块）
-- 编写日期：2026-10-13　修订：2026-10-14
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 先删子表，再删主表，保证脚本可重复执行
DROP TABLE IF EXISTS sd_order_item;
DROP TABLE IF EXISTS sd_order;
DROP TABLE IF EXISTS sd_quotation_item;
DROP TABLE IF EXISTS sd_quotation;

-- -----------------------------------------------------------------------------
-- 1. 销售订单（主表）
-- -----------------------------------------------------------------------------
CREATE TABLE sd_order (
  order_id      BIGINT        NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  order_no      VARCHAR(30)   NOT NULL                COMMENT '订单号',
  customer_id   BIGINT        NOT NULL                COMMENT '客户ID（md_customer）',
  order_date    DATE          DEFAULT NULL            COMMENT '下单日期',
  delivery_date DATE          DEFAULT NULL            COMMENT '要求交期',
  status        TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0待确认 1已确认 2生产中 3已完工 4已出货 5已完成 9已取消',
  total_amount  DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '订单总额',
  quote_id      BIGINT        DEFAULT NULL            COMMENT '来源报价ID（sd_quotation.quote_id，成交转订单）',
  sales_user_id BIGINT        DEFAULT NULL            COMMENT '销售员（关联登录账号）',
  create_by     VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time   DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by     VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time   DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark        VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  del_flag      CHAR(1)       NOT NULL DEFAULT '0'    COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (order_id),
  UNIQUE KEY uk_order_no (order_no),
  KEY idx_customer (customer_id),
  KEY idx_quote (quote_id),
  KEY idx_status (status),
  KEY idx_delivery (delivery_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单主表';

-- -----------------------------------------------------------------------------
-- 2. 订单明细（子表 = 尺码矩阵，一行 = 一个款式×颜色×尺码）
-- -----------------------------------------------------------------------------
CREATE TABLE sd_order_item (
  item_id     BIGINT        NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  order_id    BIGINT        NOT NULL                COMMENT '订单ID',
  style_id    BIGINT        NOT NULL                COMMENT '款式ID',
  color_id    BIGINT        NOT NULL                COMMENT '颜色ID',
  size_id     BIGINT        NOT NULL                COMMENT '尺码ID',
  qty         INT           NOT NULL DEFAULT 0      COMMENT '数量',
  unit_price  DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '单价',
  amount      DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '金额 = 数量 × 单价',
  create_by   VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time DATETIME      DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (item_id),
  UNIQUE KEY uk_order_style_color_size (order_id, style_id, color_id, size_id),
  KEY idx_order (order_id),
  KEY idx_style (style_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='销售订单明细（尺码矩阵）';

-- -----------------------------------------------------------------------------
-- 3. 报价单（主表，款式级）
-- -----------------------------------------------------------------------------
CREATE TABLE sd_quotation (
  quote_id      BIGINT        NOT NULL AUTO_INCREMENT COMMENT '报价ID',
  quote_no      VARCHAR(30)   NOT NULL                COMMENT '报价单号',
  customer_id   BIGINT        NOT NULL                COMMENT '客户ID',
  quote_date    DATE          DEFAULT NULL            COMMENT '报价日期',
  status        TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0待确认 1已接受 2已失效 3已转单',
  total_amount  DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '报价总额',
  sales_user_id BIGINT        DEFAULT NULL            COMMENT '销售员',
  create_by     VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time   DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by     VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time   DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark        VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  del_flag      CHAR(1)       NOT NULL DEFAULT '0'    COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (quote_id),
  UNIQUE KEY uk_quote_no (quote_no),
  KEY idx_customer (customer_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报价单主表';

-- -----------------------------------------------------------------------------
-- 4. 报价明细（款式级；cost_ref 承接「成本反哺报价」）
--    cost_src：0手工录入 1历史结算（来源 source_settle_id） 2BOM预估
-- -----------------------------------------------------------------------------
CREATE TABLE sd_quotation_item (
  quote_item_id    BIGINT        NOT NULL AUTO_INCREMENT COMMENT '报价明细ID',
  quote_id         BIGINT        NOT NULL                COMMENT '报价ID',
  style_id         BIGINT        NOT NULL                COMMENT '款式ID',
  qty              INT           NOT NULL DEFAULT 0      COMMENT '数量',
  unit_price       DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '报价单价',
  cost_ref         DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '参考成本（成本反哺报价）',
  cost_src         TINYINT       NOT NULL DEFAULT 0      COMMENT '参考成本来源：0手工 1历史结算 2BOM预估',
  source_settle_id BIGINT        DEFAULT NULL            COMMENT '来源成本结算单ID（cost_src=1 时）',
  amount           DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '金额 = 数量 × 单价',
  create_by        VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time      DATETIME      DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (quote_item_id),
  KEY idx_quote (quote_id),
  KEY idx_style (style_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='报价单明细（款式级）';

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 示例数据（SQL 种子）
-- =============================================================================

-- 报价单（4 张：前 2 张为首次报价[BOM预估]，后 2 张为成本反哺报价[引用结算单]）
INSERT INTO sd_quotation (quote_id, quote_no, customer_id, quote_date, status, total_amount, sales_user_id, create_by, create_time, remark) VALUES
(1, 'QT2026001', 1, '2026-09-15', 1, 12600.00, 6,  'admin', NOW(), 'T恤首次报价'),
(2, 'QT2026002', 3, '2026-09-05', 1,  7200.00, 14, 'admin', NOW(), '袜子首次报价'),
(3, 'QT2026003', 1, '2026-10-15', 0, 13600.00, 6,  'admin', NOW(), 'T恤补单报价（参照 CS2026001 结算成本）'),
(4, 'QT2026004', 3, '2026-10-15', 0,  8700.00, 14, 'admin', NOW(), '袜子补单报价（参照 CS2026003 结算成本）');

INSERT INTO sd_quotation_item (quote_id, style_id, qty, unit_price, cost_ref, cost_src, source_settle_id, amount, create_by, create_time) VALUES
(1, 1,  360, 35.00, 17.40, 2, NULL, 12600.00, 'admin', NOW()),
(2, 3, 1200,  6.00,  2.19, 2, NULL,  7200.00, 'admin', NOW()),
(3, 1,  400, 34.00, 17.40, 1, 1,    13600.00, 'admin', NOW()),
(4, 3, 1500,  5.80,  2.19, 1, 3,     8700.00, 'admin', NOW());

-- 销售订单（5 张，覆盖 5 个款式与 3 个客户）
--   交期口径（为 B5 的 v_order_ontime 准交率视图服务，故意留出准时/延期/逾期三类样本）：
--     SO1 交期 10-18，实际 10-21 出货 → 延期 3 天
--     SO2 交期 10-13，缝制外发导致计划完工 10-26 晚于交期 → 已逾期未交付
--     SO3 交期 10-15，实际 10-10 出货 → 提前 5 天（准时）
--     SO4/SO5 交期在未来，待交付
INSERT INTO sd_order (order_id, order_no, customer_id, order_date, delivery_date, status, total_amount, sales_user_id, create_by, create_time, remark) VALUES
(1, 'SO2026001', 1, '2026-09-20', '2026-10-18', 5, 12600.00, 6,  'admin', NOW(), '交期 10-18，实际 10-21 出货，延期 3 天'),
(2, 'SO2026002', 2, '2026-09-25', '2026-10-13', 2, 19250.00, 14, 'admin', NOW(), '交期偏紧：缝制工序外发，计划完工 10-26 已晚于客户交期'),
(3, 'SO2026003', 3, '2026-09-10', '2026-10-15', 5,  7200.00, 14, 'admin', NOW(), '交期 10-15，实际 10-10 出货，提前 5 天'),
(4, 'SO2026004', 1, '2026-10-10', '2026-11-20', 0,  5600.00, 6,  'admin', NOW(), '待客户确认，尚未排产'),
(5, 'SO2026005', 2, '2026-10-05', '2026-11-10', 2,  6750.00, 14, 'admin', NOW(), NULL);

INSERT INTO sd_order_item (order_id, style_id, color_id, size_id, qty, unit_price, amount, create_by, create_time) VALUES
-- 订单1：T恤 360 件（黑/白 × M/L）
(1, 1, 2, 2, 100, 35.00, 3500.00, 'admin', NOW()),
(1, 1, 2, 3,  80, 35.00, 2800.00, 'admin', NOW()),
(1, 1, 3, 2, 120, 35.00, 4200.00, 'admin', NOW()),
(1, 1, 3, 3,  60, 35.00, 2100.00, 'admin', NOW()),
-- 订单2：长裤 350 条（黑 × M/L）
(2, 2, 2, 2, 200, 55.00, 11000.00, 'admin', NOW()),
(2, 2, 2, 3, 150, 55.00,  8250.00, 'admin', NOW()),
-- 订单3：袜子 1200 双（黑/白/灰 × 均码）
(3, 3, 2, 6, 500, 6.00, 3000.00, 'admin', NOW()),
(3, 3, 3, 6, 400, 6.00, 2400.00, 'admin', NOW()),
(3, 3, 5, 6, 300, 6.00, 1800.00, 'admin', NOW()),
-- 订单4：背心 200 件（白 × S/M）
(4, 4, 3, 1,  80, 28.00, 2240.00, 'admin', NOW()),
(4, 4, 3, 2, 120, 28.00, 3360.00, 'admin', NOW()),
-- 订单5：紧身裤 150 条（黑 × S/M）
(5, 5, 2, 1,  90, 45.00, 4050.00, 'admin', NOW()),
(5, 5, 2, 2,  60, 45.00, 2700.00, 'admin', NOW());

-- =============================================================================
-- 结束
-- =============================================================================
