-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 出货模块（Shipment）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- -----------------------------------------------------------------------------
-- 本模块含 4 张表：
--   sh_shipment        出货单（主表）
--   sh_shipment_item   出货明细（SKU·批次）
--   sh_return          退货单（主表）
--   sh_return_item     退货明细
-- -----------------------------------------------------------------------------
-- 设计约定：
--   1) 出货明细挂 SKU（面向客户），不挂工单。
--   2) 出货单独立，一个订单可开多张（支持分批发货）。
--   3) 退货单独立建表，承接退货/滞销回流（BR4），也让「退货率」有数据。
--   4) 物流只留「物流公司 + 物流单号」两字段。
-- -----------------------------------------------------------------------------
-- 审计字段规范（v1.2 起）：
--   · 主表（出货单/退货单）：create_by + update_by + update_time + remark + **del_flag**；
--   · 明细表：只记 create_by + create_time。
-- -----------------------------------------------------------------------------
-- 联动：出货 → 驱动销售出库（st_stock_txn.biz_type=4）→ 库存减；退货 → 回流（biz_type=7）
-- 依赖：sd_order（销售）、md_sku（主数据）、st_inventory（库存）
-- -----------------------------------------------------------------------------
-- v1.3 修订（2026-10-14，P1 修复 B3）：
--   sh_return 增 return_type(1退货入库 2换货 3报废) / is_restock(结果是否回库)
--   / refund_amount(退款金额)。原表只记「原因」，无法回答「退回来的货去哪了、
--   赔了多少钱」；退货率、质量损失、销售折让都需要这三个字段。
--   口径约定（与库存联动，已被自检校验）：
--     · is_restock=1 → 必有 biz_type=7 的入库流水，且流水数量 == 退货明细数量；
--     · is_restock=0（报废/就地处理）→ 不得产生入库流水，货物不回厂。
--     · refund_amount：退货入库按「明细数量 × 订单单价」退款；换货为 0（只换不退）；
--       报废为客户认赔金额。退款不冲减 fn_cost_settlement（成本结算针对已完工批次），
--       仅在报表层作为销售折让体现。
-- 编写日期：2026-10-14　修订：2026-10-14（种子扩充至 2 出货单 / 3 退货单 + B3 字段）
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

DROP TABLE IF EXISTS sh_shipment_item;
DROP TABLE IF EXISTS sh_shipment;
DROP TABLE IF EXISTS sh_return_item;
DROP TABLE IF EXISTS sh_return;

-- -----------------------------------------------------------------------------
-- 1. 出货单（主表）
-- -----------------------------------------------------------------------------
CREATE TABLE sh_shipment (
  shipment_id       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '出货单ID',
  shipment_no       VARCHAR(30)  NOT NULL                COMMENT '出货单号',
  order_id          BIGINT       DEFAULT NULL            COMMENT '来源订单ID（sd_order）',
  customer_id       BIGINT       NOT NULL                COMMENT '客户ID',
  ship_date         DATE         DEFAULT NULL            COMMENT '出货日期',
  logistics_company VARCHAR(50)  DEFAULT NULL            COMMENT '物流公司',
  logistics_no      VARCHAR(50)  DEFAULT NULL            COMMENT '物流单号',
  status            TINYINT      NOT NULL DEFAULT 0      COMMENT '状态：0待发货 1已发货 2已签收 9已取消',
  create_by         VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time       DATETIME     DEFAULT NULL            COMMENT '创建时间',
  update_by         VARCHAR(64)  DEFAULT ''              COMMENT '更新者',
  update_time       DATETIME     DEFAULT NULL            COMMENT '更新时间',
  remark            VARCHAR(255) DEFAULT NULL            COMMENT '备注',
  del_flag          CHAR(1)      NOT NULL DEFAULT '0'    COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (shipment_id),
  UNIQUE KEY uk_shipment_no (shipment_no),
  KEY idx_order (order_id),
  KEY idx_customer (customer_id),
  KEY idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出货单主表';

-- -----------------------------------------------------------------------------
-- 2. 出货明细
-- -----------------------------------------------------------------------------
CREATE TABLE sh_shipment_item (
  item_id     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  shipment_id BIGINT       NOT NULL                COMMENT '出货单ID',
  sku_id      BIGINT       NOT NULL                COMMENT '成品SKU ID',
  batch_no    VARCHAR(30)  NOT NULL DEFAULT ''     COMMENT '批次号',
  qty         INT          NOT NULL DEFAULT 0      COMMENT '出货数量',
  create_by   VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time DATETIME     DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (item_id),
  KEY idx_shipment (shipment_id),
  KEY idx_sku (sku_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='出货单明细';

-- -----------------------------------------------------------------------------
-- 3. 退货单（主表）
--    return_type：1退货入库（货退回、款退客户） 2换货（货退回、重发，不退款）
--                 3报废（货不回厂，就地处理，可为 0 元或认赔）
--    is_restock ：1回库 0不回库。决定是否生成 biz_type=7 的入库流水。
-- -----------------------------------------------------------------------------
CREATE TABLE sh_return (
  return_id     BIGINT        NOT NULL AUTO_INCREMENT COMMENT '退货单ID',
  return_no     VARCHAR(30)   NOT NULL                COMMENT '退货单号',
  order_id      BIGINT        DEFAULT NULL            COMMENT '原订单ID',
  shipment_id   BIGINT        DEFAULT NULL            COMMENT '关联出货单ID',
  customer_id   BIGINT        NOT NULL                COMMENT '客户ID',
  return_date   DATE          DEFAULT NULL            COMMENT '退货日期',
  reason        VARCHAR(255)  DEFAULT NULL            COMMENT '退货原因',
  return_type   TINYINT       NOT NULL DEFAULT 1      COMMENT '退货类型：1退货入库 2换货 3报废',
  is_restock    TINYINT       NOT NULL DEFAULT 1      COMMENT '是否回库：1回库 0不回库（报废/就地处理）',
  refund_amount DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '退款金额（换货可为 0）',
  status        TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0待处理 1已处理 9已取消',
  create_by     VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time   DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by     VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time   DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark        VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  del_flag      CHAR(1)       NOT NULL DEFAULT '0'    COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (return_id),
  UNIQUE KEY uk_return_no (return_no),
  KEY idx_order (order_id),
  KEY idx_customer (customer_id),
  KEY idx_return_type (return_type)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退货单主表';

-- -----------------------------------------------------------------------------
-- 4. 退货明细
-- -----------------------------------------------------------------------------
CREATE TABLE sh_return_item (
  item_id     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  return_id   BIGINT       NOT NULL                COMMENT '退货单ID',
  sku_id      BIGINT       NOT NULL                COMMENT '成品SKU ID',
  qty         INT          NOT NULL DEFAULT 0      COMMENT '退货数量',
  create_by   VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time DATETIME     DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (item_id),
  KEY idx_return (return_id),
  KEY idx_sku (sku_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='退货单明细';

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 示例数据（SQL 种子）  create_by 统一记 'admin'
-- =============================================================================

-- 出货单（2 张，均已签收）
INSERT INTO sh_shipment (shipment_id, shipment_no, order_id, customer_id, ship_date, logistics_company, logistics_no, status, create_by, create_time) VALUES
(1, 'SH2026001', 1, 1, '2026-10-21', '顺丰速运', 'SF1234567890', 2, 'admin', NOW()),
(2, 'SH2026002', 3, 3, '2026-10-10', '中通快递', 'ZT9988776655', 2, 'admin', NOW());

INSERT INTO sh_shipment_item (shipment_id, sku_id, batch_no, qty, create_by, create_time) VALUES
-- 出货单1：订单1 T恤 360 件（4 个 SKU）
(1, 1,  'C20261020', 100, 'admin', NOW()),
(1, 2,  'C20261020',  80, 'admin', NOW()),
(1, 3,  'C20261020', 120, 'admin', NOW()),
(1, 4,  'C20261020',  60, 'admin', NOW()),
-- 出货单2：订单3 袜子 1200 双（3 个 SKU）
(2, 10, 'C20261004', 500, 'admin', NOW()),
(2, 11, 'C20261004', 400, 'admin', NOW()),
(2, 12, 'C20261004', 300, 'admin', NOW());

-- 退货单（3 张，覆盖三种退货类型；均已处理）
--   RT1 退货入库：5 件 × 订单单价 35.00 = 175.00，货回库（对应入库流水 TXN2026102301）
--   RT2 换货    ：20 双，只换不退 → 退款 0.00，货回库（对应入库流水 TXN2026101201）
--   RT3 报废    ：3 件，货物未回厂、就地报废 → is_restock=0，无入库流水；按订单单价认赔 105.00
INSERT INTO sh_return (return_id, return_no, order_id, shipment_id, customer_id, return_date, reason, return_type, is_restock, refund_amount, status, create_by, create_time, remark) VALUES
(1, 'RT2026001', 1, 1, 1, '2026-10-23', '部分尺码偏小，客户要求退货',       1, 1, 175.00, 1, 'admin', NOW(), '退货入库，按订单单价全额退款'),
(2, 'RT2026002', 3, 2, 3, '2026-10-12', '颜色与样品不符',                   2, 1,   0.00, 1, 'admin', NOW(), '换货：重发同款，不退款'),
(3, 'RT2026003', 1, 1, 1, '2026-10-24', '水洗后印花开裂，客户拒收',         3, 0, 105.00, 1, 'admin', NOW(), '货物未回厂，就地报废；按订单单价认赔，不产生入库流水');

INSERT INTO sh_return_item (return_id, sku_id, qty, create_by, create_time) VALUES
(1, 1,   5, 'admin', NOW()),
(2, 10, 20, 'admin', NOW()),
(3, 1,   3, 'admin', NOW());

-- =============================================================================
-- 结束
-- =============================================================================
