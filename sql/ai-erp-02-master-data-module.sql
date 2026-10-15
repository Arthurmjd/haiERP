-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 主数据模块（Master Data）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- 产品线：上衣 / 裤子 / 袜子（不含鞋子）
-- -----------------------------------------------------------------------------
-- 本模块含 11 张表：
--   品类 / 产品侧：md_category 品类, md_style 款式, md_color 颜色, md_size 尺码, md_sku
--   用料侧：       md_material 面料辅料, md_bom 款式级用料
--   工艺侧：       md_process 工序, md_style_process 款式工艺(含件单价)
--   交易对手：     md_customer 客户, md_supplier 供应商
-- -----------------------------------------------------------------------------
-- 设计约定：
--   1) SKU 按需生成：只在需要下单/排产时生成，不预置「款式×全部色码」组合。
--   2) 面料与辅料同表，用 category 区分（1面料 2辅料）。
--   3) BOM 带损耗率 loss_rate：实际领料 = qty × (1 + loss_rate)（精益关键）。
--   4) 品类用字典表 md_category；性别/季节为枚举/字符串。
--   5) 尺码带 size_group 分组（服装码 / 袜码），避免不同体系混用。
--   6) 件单价挂在「款式 × 工序」上（md_style_process），不挂员工、不挂款式整体。
-- -----------------------------------------------------------------------------
-- 审计字段规范（对齐底座 RuoYi BaseEntity，v1.2 起）：
--   · 主数据表统一补齐 create_by + create_time + update_by + update_time + remark 五件套；
--   · **不设 del_flag**：主数据被历史单据引用后不可物理删除，删除语义统一走已有的
--     status（1启用 / 0停用），这样既保留历史可查，又避免与唯一键（uk_style_no 等）冲突。
--   · create_by / update_by = varchar(64) 存用户名，由 BaseEntity.refreshCreatingStamp() 自动填充。
-- -----------------------------------------------------------------------------
-- v1.1 修订（2026-10-14，P0 修复 A3）：
--   · SKU 由「仅款式1的4个」补齐为「款式1~5 共 17 个」，打通长裤/袜子/背心/紧身裤成品链路；
--   · 款式扩到 5 个、物料扩到 8 个、颜色扩到 7 个、客户/供应商各 3 个，BOM/工艺同步补齐。
-- v1.2 修订（2026-10-14，P1 修复 B2）：补齐审计字段（见上方「审计字段规范」）。
-- 编写日期：2026-10-13　修订：2026-10-14
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 先删子表，再删主表，保证脚本可重复执行
DROP TABLE IF EXISTS md_bom;
DROP TABLE IF EXISTS md_style_process;
DROP TABLE IF EXISTS md_sku;
DROP TABLE IF EXISTS md_style;
DROP TABLE IF EXISTS md_color;
DROP TABLE IF EXISTS md_size;
DROP TABLE IF EXISTS md_category;
DROP TABLE IF EXISTS md_material;
DROP TABLE IF EXISTS md_process;
DROP TABLE IF EXISTS md_customer;
DROP TABLE IF EXISTS md_supplier;

-- -----------------------------------------------------------------------------
-- 1. 品类字典（上衣 / 裤子 / 袜子）
-- -----------------------------------------------------------------------------
CREATE TABLE md_category (
  category_id    BIGINT       NOT NULL AUTO_INCREMENT COMMENT '品类ID',
  category_name  VARCHAR(50)  NOT NULL                COMMENT '品类名称',
  sort           INT          NOT NULL DEFAULT 0      COMMENT '显示顺序',
  status         TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1启用 0停用',
  create_by      VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time    DATETIME     DEFAULT NULL            COMMENT '创建时间',
  update_by      VARCHAR(64)  DEFAULT ''              COMMENT '更新者',
  update_time    DATETIME     DEFAULT NULL            COMMENT '更新时间',
  remark         VARCHAR(255) DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (category_id),
  UNIQUE KEY uk_category_name (category_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='品类字典';

-- -----------------------------------------------------------------------------
-- 2. 颜色字典
-- -----------------------------------------------------------------------------
CREATE TABLE md_color (
  color_id     BIGINT       NOT NULL AUTO_INCREMENT COMMENT '颜色ID',
  color_code   VARCHAR(20)  NOT NULL                COMMENT '颜色编码',
  color_name   VARCHAR(50)  NOT NULL                COMMENT '颜色名称',
  sort         INT          NOT NULL DEFAULT 0      COMMENT '显示顺序',
  status       TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1启用 0停用',
  create_by    VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME     DEFAULT NULL            COMMENT '创建时间',
  update_by    VARCHAR(64)  DEFAULT ''              COMMENT '更新者',
  update_time  DATETIME     DEFAULT NULL            COMMENT '更新时间',
  remark       VARCHAR(255) DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (color_id),
  UNIQUE KEY uk_color_code (color_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='颜色字典';

-- -----------------------------------------------------------------------------
-- 3. 尺码字典（带分组：服装码 / 袜码）
-- -----------------------------------------------------------------------------
CREATE TABLE md_size (
  size_id      BIGINT       NOT NULL AUTO_INCREMENT COMMENT '尺码ID',
  size_code    VARCHAR(20)  NOT NULL                COMMENT '尺码编码',
  size_name    VARCHAR(50)  NOT NULL                COMMENT '尺码名称',
  size_group   VARCHAR(20)  NOT NULL DEFAULT '服装码' COMMENT '尺码组：服装码/袜码',
  sort         INT          NOT NULL DEFAULT 0      COMMENT '显示顺序',
  status       TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1启用 0停用',
  create_by    VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME     DEFAULT NULL            COMMENT '创建时间',
  update_by    VARCHAR(64)  DEFAULT ''              COMMENT '更新者',
  update_time  DATETIME     DEFAULT NULL            COMMENT '更新时间',
  remark       VARCHAR(255) DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (size_id),
  UNIQUE KEY uk_group_code (size_group, size_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='尺码字典';

-- -----------------------------------------------------------------------------
-- 4. 款式（设计单元，不分色码）
-- -----------------------------------------------------------------------------
CREATE TABLE md_style (
  style_id     BIGINT        NOT NULL AUTO_INCREMENT COMMENT '款式ID',
  style_no     VARCHAR(30)   NOT NULL                COMMENT '款号',
  style_name   VARCHAR(100)  NOT NULL                COMMENT '款式名称',
  category_id  BIGINT        DEFAULT NULL            COMMENT '品类ID（md_category）',
  gender       TINYINT       NOT NULL DEFAULT 2       COMMENT '性别：0女 1男 2中性',
  season       VARCHAR(20)   DEFAULT NULL            COMMENT '季节，如 2026夏',
  unit         VARCHAR(10)   NOT NULL DEFAULT '件'    COMMENT '单位：件/条/双',
  base_cost    DECIMAL(10,2) NOT NULL DEFAULT 0.00    COMMENT '基准成本（参考）',
  status       TINYINT       NOT NULL DEFAULT 1       COMMENT '状态：1启用 0停用',
  create_by    VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by    VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time  DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark       VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (style_id),
  UNIQUE KEY uk_style_no (style_no),
  KEY idx_category (category_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='款式表';

-- -----------------------------------------------------------------------------
-- 5. SKU（款式 × 颜色 × 尺码；按需生成）
-- -----------------------------------------------------------------------------
CREATE TABLE md_sku (
  sku_id       BIGINT       NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
  sku_code     VARCHAR(50)  NOT NULL                COMMENT 'SKU 编码',
  style_id     BIGINT       NOT NULL                COMMENT '款式ID',
  color_id     BIGINT       NOT NULL                COMMENT '颜色ID',
  size_id      BIGINT       NOT NULL                COMMENT '尺码ID',
  barcode      VARCHAR(50)  DEFAULT NULL            COMMENT '条码',
  status       TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1启用 0停用',
  create_by    VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME     DEFAULT NULL            COMMENT '创建时间',
  update_by    VARCHAR(64)  DEFAULT ''              COMMENT '更新者',
  update_time  DATETIME     DEFAULT NULL            COMMENT '更新时间',
  remark       VARCHAR(255) DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (sku_id),
  UNIQUE KEY uk_sku_code (sku_code),
  UNIQUE KEY uk_style_color_size (style_id, color_id, size_id),
  KEY idx_style (style_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='SKU 表（按需生成）';

-- -----------------------------------------------------------------------------
-- 6. 面料 / 辅料（同表用 category 区分）
-- -----------------------------------------------------------------------------
CREATE TABLE md_material (
  material_id    BIGINT        NOT NULL AUTO_INCREMENT COMMENT '物料ID',
  material_code  VARCHAR(30)   NOT NULL                COMMENT '物料编码',
  material_name  VARCHAR(100)  NOT NULL                COMMENT '物料名称',
  category       TINYINT       NOT NULL DEFAULT 1       COMMENT '物料类别：1面料 2辅料',
  spec           VARCHAR(100)  DEFAULT NULL            COMMENT '规格',
  unit           VARCHAR(10)   NOT NULL DEFAULT '米'    COMMENT '单位：米/个/卷/条',
  ref_price      DECIMAL(10,2) NOT NULL DEFAULT 0.00    COMMENT '参考单价',
  status         TINYINT       NOT NULL DEFAULT 1       COMMENT '状态：1启用 0停用',
  create_by      VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time    DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by      VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time    DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark         VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (material_id),
  UNIQUE KEY uk_material_code (material_code),
  KEY idx_category (category)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='面料/辅料表';

-- -----------------------------------------------------------------------------
-- 7. BOM（款式级用料；带损耗率）
-- -----------------------------------------------------------------------------
CREATE TABLE md_bom (
  bom_id       BIGINT        NOT NULL AUTO_INCREMENT COMMENT 'BOM ID',
  style_id     BIGINT        NOT NULL                COMMENT '款式ID',
  material_id  BIGINT        NOT NULL                COMMENT '物料ID',
  qty          DECIMAL(10,3) NOT NULL DEFAULT 0.000  COMMENT '单件用量',
  loss_rate    DECIMAL(5,2)  NOT NULL DEFAULT 0.00   COMMENT '损耗率(%)，实际领料=qty*(1+loss_rate/100)',
  unit         VARCHAR(10)   DEFAULT NULL            COMMENT '单位',
  create_by    VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by    VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time  DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark       VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (bom_id),
  UNIQUE KEY uk_style_material (style_id, material_id),
  KEY idx_style (style_id),
  KEY idx_material (material_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='BOM（款式级用料）';

-- -----------------------------------------------------------------------------
-- 8. 工序字典（裁剪 / 缝制 / 整烫）
-- -----------------------------------------------------------------------------
CREATE TABLE md_process (
  process_id    BIGINT       NOT NULL AUTO_INCREMENT COMMENT '工序ID',
  process_code  VARCHAR(20)  NOT NULL                COMMENT '工序编码',
  process_name  VARCHAR(50)  NOT NULL                COMMENT '工序名称',
  sort          INT          NOT NULL DEFAULT 0      COMMENT '显示顺序',
  status        TINYINT      NOT NULL DEFAULT 1      COMMENT '状态：1启用 0停用',
  create_by     VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time   DATETIME     DEFAULT NULL            COMMENT '创建时间',
  update_by     VARCHAR(64)  DEFAULT ''              COMMENT '更新者',
  update_time   DATETIME     DEFAULT NULL            COMMENT '更新时间',
  remark        VARCHAR(255) DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (process_id),
  UNIQUE KEY uk_process_code (process_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序字典';

-- -----------------------------------------------------------------------------
-- 9. 款式工艺（款式 × 工序，含件单价 —— 计件工资的单价来源）
-- -----------------------------------------------------------------------------
CREATE TABLE md_style_process (
  id           BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
  style_id     BIGINT        NOT NULL                COMMENT '款式ID',
  process_id   BIGINT        NOT NULL                COMMENT '工序ID',
  seq          INT           NOT NULL DEFAULT 0      COMMENT '工序顺序',
  unit_price   DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '件单价',
  create_by    VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by    VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time  DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark       VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (id),
  UNIQUE KEY uk_style_process (style_id, process_id),
  KEY idx_style (style_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='款式工艺（含件单价）';

-- -----------------------------------------------------------------------------
-- 10. 客户
-- -----------------------------------------------------------------------------
CREATE TABLE md_customer (
  customer_id    BIGINT        NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  customer_code  VARCHAR(30)   NOT NULL                COMMENT '客户编码',
  customer_name  VARCHAR(100)  NOT NULL                COMMENT '客户名称',
  contact        VARCHAR(50)   DEFAULT NULL            COMMENT '联系人',
  phone          VARCHAR(20)   DEFAULT NULL            COMMENT '联系电话',
  address        VARCHAR(200)  DEFAULT NULL            COMMENT '地址',
  credit_days    INT           NOT NULL DEFAULT 0      COMMENT '账期(天)',
  status         TINYINT       NOT NULL DEFAULT 1      COMMENT '状态：1启用 0停用',
  create_by      VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time    DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by      VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time    DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark         VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (customer_id),
  UNIQUE KEY uk_customer_code (customer_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='客户表';

-- -----------------------------------------------------------------------------
-- 11. 供应商
-- -----------------------------------------------------------------------------
CREATE TABLE md_supplier (
  supplier_id    BIGINT        NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  supplier_code  VARCHAR(30)   NOT NULL                COMMENT '供应商编码',
  supplier_name  VARCHAR(100)  NOT NULL                COMMENT '供应商名称',
  contact        VARCHAR(50)   DEFAULT NULL            COMMENT '联系人',
  phone          VARCHAR(20)   DEFAULT NULL            COMMENT '联系电话',
  address        VARCHAR(200)  DEFAULT NULL            COMMENT '地址',
  credit_days    INT           NOT NULL DEFAULT 0      COMMENT '账期(天)',
  status         TINYINT       NOT NULL DEFAULT 1      COMMENT '状态：1启用 0停用',
  create_by      VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time    DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by      VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time    DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark         VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (supplier_id),
  UNIQUE KEY uk_supplier_code (supplier_code)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='供应商表';

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 示例数据（SQL 种子）
--   create_by 统一记 'admin'（种子由系统初始化导入）
-- =============================================================================

-- 品类（不含鞋子）
INSERT INTO md_category (category_id, category_name, sort, status, create_by, create_time) VALUES
(1, '上衣', 1, 1, 'admin', NOW()),
(2, '裤子', 2, 1, 'admin', NOW()),
(3, '袜子', 3, 1, 'admin', NOW());

-- 颜色
INSERT INTO md_color (color_id, color_code, color_name, sort, status, create_by, create_time) VALUES
(1, 'RED',   '红色', 1, 1, 'admin', NOW()),
(2, 'BLK',   '黑色', 2, 1, 'admin', NOW()),
(3, 'WHT',   '白色', 3, 1, 'admin', NOW()),
(4, 'NVY',   '藏青', 4, 1, 'admin', NOW()),
(5, 'GRY',   '灰色', 5, 1, 'admin', NOW()),
(6, 'KHA',   '卡其', 6, 1, 'admin', NOW()),
(7, 'ARMY',  '军绿', 7, 1, 'admin', NOW());

-- 尺码（服装码 S-XXL；袜码 F 均码）
INSERT INTO md_size (size_id, size_code, size_name, size_group, sort, status, create_by, create_time) VALUES
(1, 'S',   'S',   '服装码', 1, 1, 'admin', NOW()),
(2, 'M',   'M',   '服装码', 2, 1, 'admin', NOW()),
(3, 'L',   'L',   '服装码', 3, 1, 'admin', NOW()),
(4, 'XL',  'XL',  '服装码', 4, 1, 'admin', NOW()),
(5, 'XXL', 'XXL', '服装码', 5, 1, 'admin', NOW()),
(6, 'F',   '均码', '袜码',  1, 1, 'admin', NOW());

-- 款式（5 个）
INSERT INTO md_style (style_id, style_no, style_name, category_id, gender, season, unit, base_cost, status, create_by, create_time) VALUES
(1, 'ST2026001', '男款运动短袖T恤', 1, 1, '2026夏', '件', 35.00, 1, 'admin', NOW()),
(2, 'ST2026002', '男款运动束脚长裤', 2, 1, '2026夏', '条', 55.00, 1, 'admin', NOW()),
(3, 'ST2026003', '专业运动短袜',     3, 2, '2026夏', '双',  6.00, 1, 'admin', NOW()),
(4, 'ST2026004', '女款速干运动背心', 1, 0, '2026夏', '件', 28.00, 1, 'admin', NOW()),
(5, 'ST2026005', '女款高弹紧身裤',   2, 0, '2026秋', '条', 45.00, 1, 'admin', NOW());

-- SKU（按需生成：17 个，覆盖 5 个款式）
INSERT INTO md_sku (sku_id, sku_code, style_id, color_id, size_id, barcode, status, create_by, create_time) VALUES
(1,  'ST2026001-BLK-M',  1, 2, 2, '6901000000001', 1, 'admin', NOW()),
(2,  'ST2026001-BLK-L',  1, 2, 3, '6901000000002', 1, 'admin', NOW()),
(3,  'ST2026001-WHT-M',  1, 3, 2, '6901000000003', 1, 'admin', NOW()),
(4,  'ST2026001-WHT-L',  1, 3, 3, '6901000000004', 1, 'admin', NOW()),
(5,  'ST2026001-NVY-L',  1, 4, 3, '6901000000005', 1, 'admin', NOW()),
(6,  'ST2026002-BLK-M',  2, 2, 2, '6901000000011', 1, 'admin', NOW()),
(7,  'ST2026002-BLK-L',  2, 2, 3, '6901000000012', 1, 'admin', NOW()),
(8,  'ST2026002-BLK-XL', 2, 2, 4, '6901000000013', 1, 'admin', NOW()),
(9,  'ST2026002-GRY-L',  2, 5, 3, '6901000000014', 1, 'admin', NOW()),
(10, 'ST2026003-BLK-F',  3, 2, 6, '6901000000021', 1, 'admin', NOW()),
(11, 'ST2026003-WHT-F',  3, 3, 6, '6901000000022', 1, 'admin', NOW()),
(12, 'ST2026003-GRY-F',  3, 5, 6, '6901000000023', 1, 'admin', NOW()),
(13, 'ST2026004-WHT-S',  4, 3, 1, '6901000000031', 1, 'admin', NOW()),
(14, 'ST2026004-WHT-M',  4, 3, 2, '6901000000032', 1, 'admin', NOW()),
(15, 'ST2026004-RED-M',  4, 1, 2, '6901000000033', 1, 'admin', NOW()),
(16, 'ST2026005-BLK-S',  5, 2, 1, '6901000000041', 1, 'admin', NOW()),
(17, 'ST2026005-BLK-M',  5, 2, 2, '6901000000042', 1, 'admin', NOW());

-- 面料 / 辅料（8 个）
INSERT INTO md_material (material_id, material_code, material_name, category, spec, unit, ref_price, status, create_by, create_time) VALUES
(1, 'M001', '涤纶网眼布',   1, '150g/㎡', '米', 12.50, 1, 'admin', NOW()),
(2, 'M002', '弹力棉针织布', 1, '180g/㎡', '米', 18.00, 1, 'admin', NOW()),
(3, 'M003', '树脂拉链',     2, '30cm',    '条',  1.20, 1, 'admin', NOW()),
(4, 'M004', '织唛主标',     2, '3×5cm',   '个',  0.15, 1, 'admin', NOW()),
(5, 'M005', '涤棉缝纫线',   2, '40/2',    '卷',  8.00, 1, 'admin', NOW()),
(6, 'M006', '速干锦纶布',   1, '160g/㎡', '米', 22.00, 1, 'admin', NOW()),
(7, 'M007', '氨纶弹力布',   1, '220g/㎡', '米', 26.00, 1, 'admin', NOW()),
(8, 'M008', '洗水标',       2, '2×4cm',   '个',  0.10, 1, 'admin', NOW());

-- BOM（5 个款式的用料，带损耗率）
INSERT INTO md_bom (style_id, material_id, qty, loss_rate, unit, create_by, create_time) VALUES
-- 款式1 T恤
(1, 1, 0.800, 5.00, '米', 'admin', NOW()),
(1, 4, 1.000, 0.00, '个', 'admin', NOW()),
(1, 5, 0.020, 2.00, '卷', 'admin', NOW()),
-- 款式2 长裤
(2, 2, 1.200, 6.00, '米', 'admin', NOW()),
(2, 3, 1.000, 1.00, '条', 'admin', NOW()),
(2, 5, 0.030, 2.00, '卷', 'admin', NOW()),
-- 款式3 袜子
(3, 2, 0.080, 4.00, '米', 'admin', NOW()),
(3, 5, 0.010, 2.00, '卷', 'admin', NOW()),
-- 款式4 背心
(4, 6, 0.600, 5.00, '米', 'admin', NOW()),
(4, 4, 1.000, 0.00, '个', 'admin', NOW()),
(4, 5, 0.015, 2.00, '卷', 'admin', NOW()),
-- 款式5 紧身裤
(5, 7, 1.000, 6.00, '米', 'admin', NOW()),
(5, 8, 1.000, 0.00, '个', 'admin', NOW()),
(5, 5, 0.025, 2.00, '卷', 'admin', NOW());

-- 工序字典
INSERT INTO md_process (process_id, process_code, process_name, sort, status, create_by, create_time) VALUES
(1, 'CUT',   '裁剪', 1, 1, 'admin', NOW()),
(2, 'SEW',   '缝制', 2, 1, 'admin', NOW()),
(3, 'IRON',  '整烫', 3, 1, 'admin', NOW());

-- 款式工艺（含件单价 —— 计件工资单价来源）
INSERT INTO md_style_process (style_id, process_id, seq, unit_price, create_by, create_time) VALUES
(1, 1, 1, 1.20, 'admin', NOW()), (1, 2, 2, 3.50, 'admin', NOW()), (1, 3, 3, 0.60, 'admin', NOW()),
(2, 1, 1, 1.50, 'admin', NOW()), (2, 2, 2, 4.80, 'admin', NOW()), (2, 3, 3, 0.80, 'admin', NOW()),
(3, 1, 1, 0.10, 'admin', NOW()), (3, 2, 2, 0.35, 'admin', NOW()),
(4, 1, 1, 1.00, 'admin', NOW()), (4, 2, 2, 2.80, 'admin', NOW()), (4, 3, 3, 0.50, 'admin', NOW()),
(5, 1, 1, 1.40, 'admin', NOW()), (5, 2, 2, 4.20, 'admin', NOW()), (5, 3, 3, 0.70, 'admin', NOW());

-- 客户
INSERT INTO md_customer (customer_id, customer_code, customer_name, contact, phone, address, credit_days, status, create_by, create_time) VALUES
(1, 'C001', '星辰体育用品有限公司', '李经理', '13900000001', '浙江省宁波市', 30, 1, 'admin', NOW()),
(2, 'C002', '华东服饰批发中心',     '王老板', '13900000002', '江苏省苏州市', 15, 1, 'admin', NOW()),
(3, 'C003', '悦动电商旗舰店',       '赵主管', '13900000003', '广东省广州市', 7,  1, 'admin', NOW());

-- 供应商
INSERT INTO md_supplier (supplier_id, supplier_code, supplier_name, contact, phone, address, credit_days, status, create_by, create_time) VALUES
(1, 'S001', '恒达面料织造厂',   '张厂长', '13700000001', '广东省佛山市', 30, 1, 'admin', NOW()),
(2, 'S002', '益丰辅料有限公司', '刘经理', '13700000002', '福建省泉州市', 15, 1, 'admin', NOW()),
(3, 'S003', '广盛新材料有限公司', '陈总', '13700000003', '浙江省绍兴市', 30, 1, 'admin', NOW());

-- =============================================================================
-- 结束
-- =============================================================================
