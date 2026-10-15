-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 人事模块（HR）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- -----------------------------------------------------------------------------
-- 本模块含 4 张表：
--   hr_department   部门（车间 + 职能部门）
--   hr_employee     员工档案（生产 / 职能两类）
--   hr_piece_wage   计件工资明细（由生产工单工序数据生成）
--   hr_salary_slip  月度工资单（应发 - 扣款 = 实发；含公司用工成本）
-- -----------------------------------------------------------------------------
-- 设计约定：
--   1) 员工分两类：1=生产(计件) / 2=职能(固定薪)，统一由 hr_salary_slip 承载；
--      职能岗 piece_amount 恒为 0。
--   2) 计件单价挂在「工序」上，员工表不放单价；hr_piece_wage.unit_price 存快照，
--      防止后续调价篡改历史工资。
--   3) 社保个人/公司比例建议放系统参数（sys_config）动态配置，不写死；
--      个税 MVP 简化处理（字段保留，可手填或按固定税率估算）。
--   4) 人工成本 hr_salary_slip.company_cost 后续汇总进成本核算（cost_settlement）。
-- -----------------------------------------------------------------------------
-- 审计字段规范（对齐底座 RuoYi BaseEntity，v1.2 起）：
--   · 主数据 / 组织表：create_by + create_time + update_by + update_time + remark；
--   · 明细 / 流水表：只记 create_by + create_time（从属主表，不做独立更新/删除）。
--   · create_by / update_by 为 varchar(64) 存**用户名**，由框架 BaseEntity
--     .refreshCreatingStamp() 从 SecurityUtils.getUsername() 自动填充。
--   · 组织 / 员工表不设 del_flag：删除语义走已有的 status（启用/停用、在职/离职），
--     避免与「主数据唯一键」冲突（详见设计文档 §5.3）。
-- -----------------------------------------------------------------------------
-- v1.1 修订（2026-10-14，P0 修复 A1）：
--   · hr_piece_wage 新增 style_id（款式ID），支持「按款式 × 工序」统计计件；
--   · 修正种子中 process_id（原错误值 201 不存在于 md_process，现改为 1/2/3）；
--   · 计件明细 / 工资单 / 员工 种子数据大幅扩充（16 员工、2026-09 与 2026-10 两期）。
-- v1.2 修订（2026-10-14，P1 修复 B2）：补齐审计字段（见上方「审计字段规范」）。
-- 编写日期：2026-10-13　修订：2026-10-14
-- =============================================================================

SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- 先删子表，再删主表，保证脚本可重复执行
DROP TABLE IF EXISTS hr_salary_slip;
DROP TABLE IF EXISTS hr_piece_wage;
DROP TABLE IF EXISTS hr_employee;
DROP TABLE IF EXISTS hr_department;

-- -----------------------------------------------------------------------------
-- 1. 部门表：车间（生产）与职能部门统一管理
-- -----------------------------------------------------------------------------
CREATE TABLE hr_department (
  dept_id      BIGINT        NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  dept_name    VARCHAR(50)   NOT NULL                COMMENT '部门名称',
  dept_type    TINYINT       NOT NULL DEFAULT 1       COMMENT '部门类型：1生产 2职能',
  parent_id    BIGINT        NOT NULL DEFAULT 0       COMMENT '上级部门ID（0为顶级）',
  sort         INT           NOT NULL DEFAULT 0       COMMENT '显示顺序',
  status       TINYINT       NOT NULL DEFAULT 1       COMMENT '状态：1正常 0停用',
  create_by    VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by    VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time  DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark       VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (dept_id),
  UNIQUE KEY uk_dept_name (dept_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='部门表';

-- -----------------------------------------------------------------------------
-- 2. 员工档案表
-- -----------------------------------------------------------------------------
CREATE TABLE hr_employee (
  emp_id       BIGINT        NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  emp_no       VARCHAR(20)   NOT NULL                COMMENT '工号',
  name         VARCHAR(50)   NOT NULL                COMMENT '姓名',
  gender       TINYINT       NOT NULL DEFAULT 0       COMMENT '性别：0女 1男',
  phone        VARCHAR(20)   DEFAULT NULL             COMMENT '联系电话',
  dept_id      BIGINT        DEFAULT NULL             COMMENT '部门ID',
  position     VARCHAR(50)   DEFAULT NULL             COMMENT '岗位/工种',
  emp_type     TINYINT       NOT NULL DEFAULT 1       COMMENT '员工类型：1生产(计件) 2职能(固定薪)',
  hire_date    DATE          DEFAULT NULL             COMMENT '入职日期',
  base_salary  DECIMAL(10,2) NOT NULL DEFAULT 0.00    COMMENT '月基本工资标准',
  si_base      DECIMAL(10,2) NOT NULL DEFAULT 0.00    COMMENT '社保缴费基数',
  user_id      BIGINT        DEFAULT NULL             COMMENT '关联登录账号ID（车间工人可为空）',
  status       TINYINT       NOT NULL DEFAULT 1       COMMENT '状态：1在职 0离职',
  create_by    VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by    VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time  DATETIME      DEFAULT NULL            COMMENT '更新时间',
  remark       VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  PRIMARY KEY (emp_id),
  UNIQUE KEY uk_emp_no (emp_no),
  KEY idx_dept (dept_id),
  KEY idx_user (user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='员工档案表';

-- -----------------------------------------------------------------------------
-- 3. 计件工资明细表（数据源：生产工单工序的完成件数 × 工序件单价）
-- -----------------------------------------------------------------------------
CREATE TABLE hr_piece_wage (
  id            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
  emp_id        BIGINT        NOT NULL                COMMENT '员工ID',
  work_order_id BIGINT        DEFAULT NULL            COMMENT '生产工单ID',
  style_id      BIGINT        DEFAULT NULL            COMMENT '款式ID（冗余，便于按款式×工序统计）',
  process_id    BIGINT        NOT NULL                COMMENT '工序ID（关联 md_process）',
  qty           INT           NOT NULL DEFAULT 0      COMMENT '完成件数',
  unit_price    DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '件单价（快照，取自 pr_work_order_process.unit_price）',
  amount        DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '金额 = 件数 × 单价',
  wage_period   CHAR(7)       NOT NULL                COMMENT '工资期（yyyy-MM，如 2026-10）',
  status        TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0待发 1已发',
  create_by     VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time   DATETIME      DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (id),
  KEY idx_emp_period (emp_id, wage_period),
  KEY idx_wo (work_order_id),
  KEY idx_style_process (style_id, process_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='计件工资明细表';

-- -----------------------------------------------------------------------------
-- 4. 月度工资单表（每人每月一条；构成见下）
--    应发合计 gross_pay   = 基本 + 补贴 + 计件 + 加班
--    扣款合计              = 社保(个人) + 公积金(个人) + 个税 + 其他
--    实发工资 net_pay      = gross_pay - 扣款合计
--    公司用工成本 cost     = gross_pay + 社保(公司) + 公积金(公司)
-- -----------------------------------------------------------------------------
CREATE TABLE hr_salary_slip (
  id               BIGINT        NOT NULL AUTO_INCREMENT COMMENT '主键',
  emp_id           BIGINT        NOT NULL                COMMENT '员工ID',
  wage_period      CHAR(7)       NOT NULL                COMMENT '工资期（yyyy-MM）',
  base_salary      DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '基本工资',
  allowance        DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '补贴合计（餐补/全勤/交通等）',
  piece_amount     DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '计件工资（汇总自 hr_piece_wage）',
  overtime_pay     DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '加班费',
  gross_pay        DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '应发合计',
  si_personal      DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '社保（个人承担）',
  fund_personal    DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '公积金（个人承担）',
  tax              DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '个税',
  other_deduction  DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '其他扣款',
  net_pay          DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '实发工资',
  si_company       DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '社保（公司承担）',
  fund_company     DECIMAL(10,2) NOT NULL DEFAULT 0.00   COMMENT '公积金（公司承担）',
  company_cost     DECIMAL(12,2) NOT NULL DEFAULT 0.00   COMMENT '公司用工成本',
  status           TINYINT       NOT NULL DEFAULT 0       COMMENT '状态：0待发 1已发',
  create_by        VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time      DATETIME      DEFAULT NULL             COMMENT '创建时间',
  PRIMARY KEY (id),
  UNIQUE KEY uk_emp_period (emp_id, wage_period),
  KEY idx_period (wage_period)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='月度工资单表';

SET FOREIGN_KEY_CHECKS = 1;

-- =============================================================================
-- 示例数据（SQL 种子）
--   比例口径：社保个人 10.5% / 公积金个人 5% / 社保公司 27% / 公积金公司 5%
--   create_by 统一记 'admin'（种子由系统初始化导入）
-- =============================================================================

-- 部门（生产 3 + 职能 6）
INSERT INTO hr_department (dept_id, dept_name, dept_type, parent_id, sort, status, create_by, create_time, remark) VALUES
(1, '裁剪车间', 1, 0, 1, 1, 'admin', NOW(), '生产 - 裁剪'),
(2, '缝制车间', 1, 0, 2, 1, 'admin', NOW(), '生产 - 缝制'),
(3, '整烫车间', 1, 0, 3, 1, 'admin', NOW(), '生产 - 整烫'),
(4, '采购部',   2, 0, 4, 1, 'admin', NOW(), '职能 - 采购'),
(5, '销售部',   2, 0, 5, 1, 'admin', NOW(), '职能 - 销售'),
(6, '财务部',   2, 0, 6, 1, 'admin', NOW(), '职能 - 财务'),
(7, '人事部',   2, 0, 7, 1, 'admin', NOW(), '职能 - 人事'),
(8, '仓储部',   2, 0, 8, 1, 'admin', NOW(), '职能 - 仓储'),
(9, '厂办',     2, 0, 9, 1, 'admin', NOW(), '职能 - 厂办/计划');

-- 员工（生产 8 人 + 职能 8 人）
INSERT INTO hr_employee (emp_id, emp_no, name, gender, phone, dept_id, position, emp_type, hire_date, base_salary, si_base, user_id, status, create_by, create_time) VALUES
(1,  'E001', '张伟', 1, '13800000001', 2, '缝制工',   1, '2023-03-01',  2200.00,  4000.00, NULL, 1, 'admin', NOW()),
(2,  'E002', '李娜', 0, '13800000002', 2, '缝制工',   1, '2023-06-15',  2200.00,  4000.00, NULL, 1, 'admin', NOW()),
(3,  'E003', '王强', 1, '13800000003', 1, '裁剪工',   1, '2022-09-10',  2300.00,  4200.00, NULL, 1, 'admin', NOW()),
(4,  'E004', '赵敏', 0, '13800000004', 3, '整烫工',   1, '2024-01-05',  2100.00,  3800.00, NULL, 1, 'admin', NOW()),
(5,  'E005', '陈静', 0, '13800000005', 6, '会计',     2, '2021-07-01',  6500.00,  8000.00, 5,    1, 'admin', NOW()),
(6,  'E006', '刘洋', 1, '13800000006', 5, '销售代表', 2, '2022-05-20',  5000.00,  6500.00, 6,    1, 'admin', NOW()),
(7,  'E007', '孙丽', 0, '13800000007', 7, '人事专员', 2, '2023-02-01',  5500.00,  7000.00, 7,    1, 'admin', NOW()),
(8,  'E008', '周涛', 1, '13800000008', 9, '厂长',     2, '2020-01-01', 15000.00, 18000.00, 8,    1, 'admin', NOW()),
(9,  'E009', '吴迪', 1, '13800000009', 1, '裁剪工',   1, '2023-08-01',  2300.00,  4200.00, NULL, 1, 'admin', NOW()),
(10, 'E010', '郑爽', 0, '13800000010', 2, '缝制工',   1, '2024-03-01',  2200.00,  4000.00, NULL, 1, 'admin', NOW()),
(11, 'E011', '冯磊', 1, '13800000011', 2, '缝制工',   1, '2024-05-10',  2200.00,  4000.00, NULL, 1, 'admin', NOW()),
(12, 'E012', '何静', 0, '13800000012', 3, '整烫工',   1, '2024-06-20',  2100.00,  3800.00, NULL, 1, 'admin', NOW()),
(13, 'E013', '许峰', 1, '13800000013', 4, '采购专员', 2, '2022-11-01',  5200.00,  6800.00, 9,    1, 'admin', NOW()),
(14, 'E014', '邓丽', 0, '13800000014', 5, '销售代表', 2, '2023-04-15',  5000.00,  6500.00, 10,   1, 'admin', NOW()),
(15, 'E015', '曹阳', 1, '13800000015', 8, '仓管员',   2, '2023-09-01',  4800.00,  6200.00, 11,   1, 'admin', NOW()),
(16, 'E016', '韩雪', 0, '13800000016', 9, '计划跟单', 2, '2022-02-20',  5800.00,  7200.00, 12,   1, 'admin', NOW());

-- 计件工资明细（process_id 关联 md_process：1裁剪 2缝制 3整烫）
--   2026-09（订单3 袜子生产，已发） / 2026-10（订单1/2/5 生产，待发）
INSERT INTO hr_piece_wage (emp_id, work_order_id, style_id, process_id, qty, unit_price, amount, wage_period, status, create_by, create_time) VALUES
-- —— 2026-09 ——
(3,  3, 3, 1, 1200, 0.10, 120.00, '2026-09', 1, 'admin', NOW()),
(1,  3, 3, 2,  600, 0.35, 210.00, '2026-09', 1, 'admin', NOW()),
(2,  3, 3, 2,  600, 0.35, 210.00, '2026-09', 1, 'admin', NOW()),
(11, 3, 3, 2,  400, 0.35, 140.00, '2026-09', 1, 'admin', NOW()),
(12, 3, 3, 2,  300, 0.35, 105.00, '2026-09', 1, 'admin', NOW()),
-- —— 2026-10 ——
(9,  1, 1, 1,  360, 1.20, 432.00, '2026-10', 0, 'admin', NOW()),
(1,  1, 1, 2,  180, 3.50, 630.00, '2026-10', 0, 'admin', NOW()),
(2,  1, 1, 2,  180, 3.50, 630.00, '2026-10', 0, 'admin', NOW()),
(4,  1, 1, 3,  360, 0.60, 216.00, '2026-10', 0, 'admin', NOW()),
(3,  2, 2, 1,  350, 1.50, 525.00, '2026-10', 0, 'admin', NOW()),
(10, 2, 2, 2,  130, 4.80, 624.00, '2026-10', 0, 'admin', NOW()),
(11, 2, 2, 2,   70, 4.80, 336.00, '2026-10', 0, 'admin', NOW()),
(9,  4, 5, 1,  150, 1.40, 210.00, '2026-10', 0, 'admin', NOW()),
(10, 4, 5, 2,   80, 4.20, 336.00, '2026-10', 0, 'admin', NOW()),
(12, 4, 5, 3,   50, 0.70,  35.00, '2026-10', 0, 'admin', NOW());

-- 月度工资单
--   2026-09（已发，status=1）：生产 3 人 + 职能 2 人
INSERT INTO hr_salary_slip
(emp_id, wage_period, base_salary, allowance, piece_amount, overtime_pay, gross_pay,
 si_personal, fund_personal, tax, other_deduction, net_pay, si_company, fund_company, company_cost, status, create_by, create_time) VALUES
-- 张伟：应发 2200+300+210+100=2810；实发 2810-420-200=2190；公司成本 2810+1080+200=4090
(1,  '2026-09',  2200.00, 300.00,  210.00, 100.00,  2810.00,  420.00, 200.00,  0.00, 0.00,  2190.00, 1080.00, 200.00,  4090.00, 1, 'admin', NOW()),
-- 李娜：应发 2790；实发 2170；公司成本 4070
(2,  '2026-09',  2200.00, 300.00,  210.00,  80.00,  2790.00,  420.00, 200.00,  0.00, 0.00,  2170.00, 1080.00, 200.00,  4070.00, 1, 'admin', NOW()),
-- 王强：应发 2720；实发 2069；公司成本 4064
(3,  '2026-09',  2300.00, 300.00,  120.00,   0.00,  2720.00,  441.00, 210.00,  0.00, 0.00,  2069.00, 1134.00, 210.00,  4064.00, 1, 'admin', NOW()),
-- 陈静（职能）：应发 7000；实发 5760；公司成本 9560
(5,  '2026-09',  6500.00, 500.00,    0.00,   0.00,  7000.00,  840.00, 400.00,  0.00, 0.00,  5760.00, 2160.00, 400.00,  9560.00, 1, 'admin', NOW()),
-- 周涛（厂长）：应发 15500；实发 12478.70；公司成本 21260
(8,  '2026-09', 15000.00, 500.00,    0.00,   0.00, 15500.00, 1890.00, 900.00, 231.30, 0.00, 12478.70, 4860.00, 900.00, 21260.00, 1, 'admin', NOW());

--   2026-10（待发，status=0）：生产 8 人 + 职能 7 人
INSERT INTO hr_salary_slip
(emp_id, wage_period, base_salary, allowance, piece_amount, overtime_pay, gross_pay,
 si_personal, fund_personal, tax, other_deduction, net_pay, si_company, fund_company, company_cost, status, create_by, create_time) VALUES
-- 生产岗
(1,  '2026-10',  2200.00, 300.00,  630.00, 200.00,  3330.00,  420.00, 200.00,   0.00, 0.00,  2710.00, 1080.00, 200.00,  4610.00, 0, 'admin', NOW()),
(2,  '2026-10',  2200.00, 300.00,  630.00, 180.00,  3310.00,  420.00, 200.00,   0.00, 0.00,  2690.00, 1080.00, 200.00,  4590.00, 0, 'admin', NOW()),
(3,  '2026-10',  2300.00, 300.00,  525.00, 150.00,  3275.00,  441.00, 210.00,   0.00, 0.00,  2624.00, 1134.00, 210.00,  4619.00, 0, 'admin', NOW()),
(4,  '2026-10',  2100.00, 300.00,  216.00,   0.00,  2616.00,  399.00, 190.00,   0.00, 0.00,  2027.00, 1026.00, 190.00,  3832.00, 0, 'admin', NOW()),
(9,  '2026-10',  2300.00, 300.00,  642.00, 180.00,  3422.00,  441.00, 210.00,   0.00, 0.00,  2771.00, 1134.00, 210.00,  4766.00, 0, 'admin', NOW()),
(10, '2026-10',  2200.00, 300.00,  960.00, 220.00,  3680.00,  420.00, 200.00,   0.00, 0.00,  3060.00, 1080.00, 200.00,  4960.00, 0, 'admin', NOW()),
(11, '2026-10',  2200.00, 300.00,  336.00, 100.00,  2936.00,  420.00, 200.00,   0.00, 0.00,  2316.00, 1080.00, 200.00,  4216.00, 0, 'admin', NOW()),
(12, '2026-10',  2100.00, 300.00,   35.00,   0.00,  2435.00,  399.00, 190.00,   0.00, 0.00,  1846.00, 1026.00, 190.00,  3651.00, 0, 'admin', NOW()),
-- 职能岗
(5,  '2026-10',  6500.00, 500.00,    0.00,   0.00,  7000.00,  840.00, 400.00,   0.00, 0.00,  5760.00, 2160.00, 400.00,  9560.00, 0, 'admin', NOW()),
(6,  '2026-10',  5000.00, 500.00,    0.00,   0.00,  5500.00,  682.50, 325.00,   0.00, 0.00,  4492.50, 1755.00, 325.00,  7580.00, 0, 'admin', NOW()),
(7,  '2026-10',  5500.00, 500.00,    0.00,   0.00,  6000.00,  735.00, 350.00,   0.00, 0.00,  4915.00, 1890.00, 350.00,  8240.00, 0, 'admin', NOW()),
(8,  '2026-10', 15000.00, 500.00,    0.00,   0.00, 15500.00, 1890.00, 900.00, 231.30, 0.00, 12478.70, 4860.00, 900.00, 21260.00, 0, 'admin', NOW()),
(13, '2026-10',  5200.00, 500.00,    0.00,   0.00,  5700.00,  714.00, 340.00,   0.00, 0.00,  4646.00, 1836.00, 340.00,  7876.00, 0, 'admin', NOW()),
(15, '2026-10',  4800.00, 500.00,    0.00,   0.00,  5300.00,  651.00, 310.00,   0.00, 0.00,  4339.00, 1674.00, 310.00,  7284.00, 0, 'admin', NOW()),
(16, '2026-10',  5800.00, 500.00,    0.00,   0.00,  6300.00,  756.00, 360.00,   0.00, 0.00,  5184.00, 1944.00, 360.00,  8604.00, 0, 'admin', NOW());

-- =============================================================================
-- 结束
-- =============================================================================
