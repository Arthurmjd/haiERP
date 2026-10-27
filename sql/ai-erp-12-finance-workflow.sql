-- =============================================================================
-- AI-ERP P0：资金域 + 协作流（待办/审批/期间）+ 库存预留
-- 适用：已有 hai_erp_demo 增量升级；也可在 init_all.sql 之后全新库执行。
-- 新增表使用 IF NOT EXISTS，新增列用存储过程判断，可重复执行。
-- =============================================================================
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

CREATE TABLE IF NOT EXISTS fn_account (
  account_id      BIGINT        NOT NULL AUTO_INCREMENT COMMENT '账户ID',
  account_no      VARCHAR(30)   NOT NULL                COMMENT '账户编码',
  account_name    VARCHAR(50)   NOT NULL                COMMENT '账户名称',
  account_type    TINYINT       NOT NULL DEFAULT 1      COMMENT '类型：1现金 2银行 3电子账户',
  opening_balance DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '期初余额',
  current_balance DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '当前余额',
  status          TINYINT       NOT NULL DEFAULT 1      COMMENT '状态：1启用 0停用',
  remark          VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  create_by       VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time     DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_by       VARCHAR(64)   DEFAULT ''              COMMENT '更新者',
  update_time     DATETIME      DEFAULT NULL            COMMENT '更新时间',
  PRIMARY KEY (account_id),
  UNIQUE KEY uk_account_no (account_no)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资金账户';

CREATE TABLE IF NOT EXISTS fn_cash_txn (
  txn_id            BIGINT        NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  txn_no            VARCHAR(30)   NOT NULL                COMMENT '流水号',
  account_id        BIGINT        NOT NULL                COMMENT '资金账户',
  direction         TINYINT       NOT NULL                COMMENT '方向：1收 2付',
  amount            DECIMAL(14,2) NOT NULL                COMMENT '金额',
  biz_type          TINYINT       NOT NULL                COMMENT '业务：1销售收款 2采购付款 3工资发放 4退款 5其他收 6其他付',
  counterparty_type TINYINT       DEFAULT NULL            COMMENT '对方类型：1客户 2供应商 3员工',
  counterparty_id   BIGINT        DEFAULT NULL            COMMENT '对方ID',
  source_type       VARCHAR(40)   DEFAULT NULL            COMMENT '来源单据类型',
  source_id         BIGINT        DEFAULT NULL            COMMENT '来源单据ID',
  txn_date          DATE          NOT NULL                COMMENT '发生日期',
  request_key       VARCHAR(80)   DEFAULT NULL            COMMENT '幂等键',
  remark            VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  create_by         VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time       DATETIME      DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (txn_id),
  UNIQUE KEY uk_cash_txn_no (txn_no),
  UNIQUE KEY uk_cash_request (request_key),
  KEY idx_cash_account_date (account_id, txn_date),
  KEY idx_cash_biz (biz_type, source_type, source_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='资金收支流水';

CREATE TABLE IF NOT EXISTS fn_receivable (
  ar_id           BIGINT        NOT NULL AUTO_INCREMENT COMMENT '应收ID',
  ar_no           VARCHAR(30)   NOT NULL                COMMENT '应收单号',
  customer_id     BIGINT        NOT NULL                COMMENT '客户ID',
  order_id        BIGINT        DEFAULT NULL            COMMENT '销售订单ID',
  shipment_id     BIGINT        DEFAULT NULL            COMMENT '出货单ID',
  amount          DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '应收金额',
  received_amount DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '已收金额',
  balance         DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '未收余额',
  due_date        DATE          DEFAULT NULL            COMMENT '到期日',
  status          TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0未收 1部分 2已收 9作废',
  request_key     VARCHAR(80)   NOT NULL                COMMENT '幂等键',
  create_by       VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time     DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_time     DATETIME      DEFAULT NULL            COMMENT '更新时间',
  PRIMARY KEY (ar_id),
  UNIQUE KEY uk_ar_no (ar_no),
  UNIQUE KEY uk_ar_request (request_key),
  KEY idx_ar_customer (customer_id, status),
  KEY idx_ar_due (due_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应收账款';

CREATE TABLE IF NOT EXISTS fn_payable (
  ap_id        BIGINT        NOT NULL AUTO_INCREMENT COMMENT '应付ID',
  ap_no        VARCHAR(30)   NOT NULL                COMMENT '应付单号',
  supplier_id  BIGINT        NOT NULL                COMMENT '供应商ID',
  po_id        BIGINT        DEFAULT NULL            COMMENT '采购单ID',
  amount       DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '应付金额',
  paid_amount  DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '已付金额',
  balance      DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '未付余额',
  due_date     DATE          DEFAULT NULL            COMMENT '到期日',
  status       TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0未付 1部分 2已付 9作废',
  request_key  VARCHAR(80)   NOT NULL                COMMENT '幂等键',
  create_by    VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time  DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_time  DATETIME      DEFAULT NULL            COMMENT '更新时间',
  PRIMARY KEY (ap_id),
  UNIQUE KEY uk_ap_no (ap_no),
  UNIQUE KEY uk_ap_request (request_key),
  KEY idx_ap_supplier (supplier_id, status),
  KEY idx_ap_due (due_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='应付账款';

CREATE TABLE IF NOT EXISTS fn_payment (
  payment_id        BIGINT        NOT NULL AUTO_INCREMENT COMMENT '收付款ID',
  payment_no        VARCHAR(30)   NOT NULL                COMMENT '单号',
  direction         TINYINT       NOT NULL                COMMENT '方向：1收款 2付款',
  biz_type          TINYINT       NOT NULL DEFAULT 5      COMMENT '业务：1销售收款 2采购付款 3工资发放 4退款 5其他收 6其他付',
  account_id        BIGINT        NOT NULL                COMMENT '资金账户',
  counterparty_type TINYINT       DEFAULT NULL            COMMENT '对方类型：1客户 2供应商 3员工',
  counterparty_id   BIGINT        DEFAULT NULL            COMMENT '对方ID',
  amount            DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '金额',
  pay_date          DATE          NOT NULL                COMMENT '收付款日期',
  status            TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0草稿 1已确认 9作废',
  request_key       VARCHAR(80)   DEFAULT NULL            COMMENT '草稿幂等键',
  remark            VARCHAR(255)  DEFAULT NULL            COMMENT '备注',
  create_by         VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time       DATETIME      DEFAULT NULL            COMMENT '创建时间',
  confirm_by        VARCHAR(64)   DEFAULT ''              COMMENT '确认人',
  confirm_time      DATETIME      DEFAULT NULL            COMMENT '确认时间',
  PRIMARY KEY (payment_id),
  UNIQUE KEY uk_payment_no (payment_no),
  UNIQUE KEY uk_payment_request (request_key),
  KEY idx_payment_account (account_id, pay_date),
  KEY idx_payment_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收付款单';

CREATE TABLE IF NOT EXISTS fn_payment_line (
  line_id     BIGINT        NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  payment_id  BIGINT        NOT NULL                COMMENT '收付款ID',
  target_type TINYINT       NOT NULL                COMMENT '核销对象：1应收 2应付',
  target_id   BIGINT        NOT NULL                COMMENT '应收/应付ID',
  amount      DECIMAL(14,2) NOT NULL                COMMENT '核销金额',
  PRIMARY KEY (line_id),
  KEY idx_payment_line (payment_id),
  KEY idx_payment_target (target_type, target_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='收付款核销明细';

CREATE TABLE IF NOT EXISTS biz_todo (
  todo_id       BIGINT       NOT NULL AUTO_INCREMENT COMMENT '待办ID',
  todo_type     VARCHAR(30)  NOT NULL                COMMENT '类型：approval/receive/issue/report/ship/collect/pay/payroll/plan/close',
  target_type   VARCHAR(40)  NOT NULL                COMMENT '目标单据类型',
  target_id     BIGINT       NOT NULL                COMMENT '目标单据ID',
  assignee_role VARCHAR(30)  NOT NULL                COMMENT '处理角色',
  title         VARCHAR(120) NOT NULL                COMMENT '标题',
  path          VARCHAR(160) DEFAULT NULL            COMMENT '前端跳转路径',
  due_date      DATE         DEFAULT NULL            COMMENT '截止日期',
  status        TINYINT      NOT NULL DEFAULT 0      COMMENT '状态：0待办 1已办 2取消',
  create_time   DATETIME     DEFAULT NULL            COMMENT '创建时间',
  done_by       VARCHAR(64)  DEFAULT ''              COMMENT '处理人',
  done_time     DATETIME     DEFAULT NULL            COMMENT '处理时间',
  PRIMARY KEY (todo_id),
  UNIQUE KEY uk_todo_target (todo_type, target_type, target_id, assignee_role),
  KEY idx_todo_role (assignee_role, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='业务待办';

CREATE TABLE IF NOT EXISTS biz_approval (
  approval_id   BIGINT        NOT NULL AUTO_INCREMENT COMMENT '审批ID',
  doc_type      VARCHAR(40)   NOT NULL                COMMENT '单据类型',
  doc_id        BIGINT        NOT NULL                COMMENT '单据ID',
  approval_type VARCHAR(30)   NOT NULL                COMMENT '审批类型',
  amount        DECIMAL(14,2) NOT NULL DEFAULT 0.00   COMMENT '涉及金额',
  applicant     VARCHAR(64)   DEFAULT ''              COMMENT '申请人',
  approver      VARCHAR(64)   DEFAULT NULL            COMMENT '审批人',
  status        TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0待审 1通过 2驳回',
  comment       VARCHAR(255)  DEFAULT NULL            COMMENT '审批意见',
  create_time   DATETIME      DEFAULT NULL            COMMENT '申请时间',
  approve_time  DATETIME      DEFAULT NULL            COMMENT '审批时间',
  PRIMARY KEY (approval_id),
  KEY idx_approval_doc (doc_type, doc_id),
  KEY idx_approval_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='统一审批记录';

CREATE TABLE IF NOT EXISTS biz_period (
  period        CHAR(7)     NOT NULL                COMMENT '会计期间 yyyy-MM',
  status        TINYINT     NOT NULL DEFAULT 0      COMMENT '状态：0开启 1关闭',
  closed_by     VARCHAR(64) DEFAULT ''              COMMENT '结账人',
  closed_time   DATETIME    DEFAULT NULL            COMMENT '结账时间',
  reopened_by   VARCHAR(64) DEFAULT ''              COMMENT '反结账人',
  reopened_time DATETIME    DEFAULT NULL            COMMENT '反结账时间',
  PRIMARY KEY (period)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='财务期间';

CREATE TABLE IF NOT EXISTS hr_payroll_config (
  config_key   VARCHAR(50)   NOT NULL COMMENT '参数键',
  config_value DECIMAL(12,4) NOT NULL DEFAULT 0 COMMENT '参数值',
  remark       VARCHAR(255)  DEFAULT NULL COMMENT '说明',
  PRIMARY KEY (config_key)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工资计算参数';

CREATE TABLE IF NOT EXISTS st_reservation (
  reservation_id BIGINT        NOT NULL AUTO_INCREMENT COMMENT '预留ID',
  item_type      TINYINT       NOT NULL                COMMENT '对象类型：1物料 2成品',
  item_id        BIGINT        NOT NULL                COMMENT '对象ID',
  source_type    VARCHAR(40)   NOT NULL                COMMENT '来源类型',
  source_id      BIGINT        NOT NULL                COMMENT '来源单据ID',
  reserved_qty   DECIMAL(14,3) NOT NULL DEFAULT 0.000  COMMENT '预留数量',
  status         TINYINT       NOT NULL DEFAULT 0      COMMENT '状态：0有效 1已释放',
  create_by      VARCHAR(64)   DEFAULT ''              COMMENT '创建者',
  create_time    DATETIME      DEFAULT NULL            COMMENT '创建时间',
  update_time    DATETIME      DEFAULT NULL            COMMENT '更新时间',
  PRIMARY KEY (reservation_id),
  UNIQUE KEY uk_reservation_source (source_type, source_id, item_type, item_id),
  KEY idx_reservation_item (item_type, item_id, status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='库存预留';

DROP PROCEDURE IF EXISTS erp_add_column;
DELIMITER //
CREATE PROCEDURE erp_add_column(IN tbl VARCHAR(64), IN col VARCHAR(64), IN ddl VARCHAR(255))
BEGIN
  IF NOT EXISTS (
    SELECT 1 FROM information_schema.COLUMNS
    WHERE TABLE_SCHEMA = DATABASE() AND TABLE_NAME = tbl AND COLUMN_NAME = col
  ) THEN
    SET @ddl = CONCAT('ALTER TABLE `', tbl, '` ADD COLUMN `', col, '` ', ddl);
    PREPARE stmt FROM @ddl;
    EXECUTE stmt;
    DEALLOCATE PREPARE stmt;
  END IF;
END //
DELIMITER ;

CALL erp_add_column('md_customer','credit_limit','DECIMAL(14,2) NOT NULL DEFAULT 0.00');
CALL erp_add_column('sd_order','quote_id','BIGINT DEFAULT NULL');
CALL erp_add_column('pu_purchase_order','approval_status','TINYINT NOT NULL DEFAULT 0');
CALL erp_add_column('pu_purchase_order','approved_by','VARCHAR(64) DEFAULT NULL');
CALL erp_add_column('pu_purchase_order','approved_time','DATETIME DEFAULT NULL');
CALL erp_add_column('pu_purchase_order','approval_comment','VARCHAR(255) DEFAULT NULL');
CALL erp_add_column('hr_salary_slip','paid_time','DATETIME DEFAULT NULL');
CALL erp_add_column('hr_salary_slip','paid_by','VARCHAR(64) DEFAULT NULL');
CALL erp_add_column('pr_work_order_material','returned_qty','DECIMAL(14,3) NOT NULL DEFAULT 0.000');

DROP PROCEDURE IF EXISTS erp_add_column;

CREATE OR REPLACE VIEW v_cash_balance AS
SELECT a.account_id, a.account_no, a.account_name, a.account_type,
       a.opening_balance, a.current_balance, a.status,
       a.opening_balance + COALESCE(SUM(CASE WHEN t.direction = 1 THEN t.amount ELSE -t.amount END), 0) AS computed_balance
FROM fn_account a
LEFT JOIN fn_cash_txn t ON t.account_id = a.account_id
GROUP BY a.account_id, a.account_no, a.account_name, a.account_type,
         a.opening_balance, a.current_balance, a.status;

CREATE OR REPLACE VIEW v_ar_aging AS
SELECT ar.ar_id, ar.ar_no, ar.customer_id, c.customer_name, ar.order_id, ar.shipment_id,
       ar.amount, ar.received_amount, ar.balance, ar.due_date, ar.status,
       DATEDIFF(CURDATE(), ar.due_date) AS overdue_days,
       CASE WHEN ar.status = 2 THEN '已收'
            WHEN ar.status = 9 THEN '作废'
            WHEN ar.due_date IS NOT NULL AND ar.due_date < CURDATE() THEN '逾期'
            ELSE '未到期' END AS aging_bucket
FROM fn_receivable ar
JOIN md_customer c ON c.customer_id = ar.customer_id;

CREATE OR REPLACE VIEW v_ap_aging AS
SELECT ap.ap_id, ap.ap_no, ap.supplier_id, s.supplier_name, ap.po_id,
       ap.amount, ap.paid_amount, ap.balance, ap.due_date, ap.status,
       DATEDIFF(CURDATE(), ap.due_date) AS overdue_days,
       CASE WHEN ap.status = 2 THEN '已付'
            WHEN ap.status = 9 THEN '作废'
            WHEN ap.due_date IS NOT NULL AND ap.due_date < CURDATE() THEN '逾期'
            ELSE '未到期' END AS aging_bucket
FROM fn_payable ap
JOIN md_supplier s ON s.supplier_id = ap.supplier_id;

CREATE OR REPLACE VIEW v_labor_variance AS
SELECT w.wo_id, w.wo_no, w.order_id, w.style_id,
       COALESCE(std.amount, 0) AS standard_labor,
       COALESCE(act.amount, 0) AS actual_piece,
       COALESCE(act.amount, 0) - COALESCE(std.amount, 0) AS variance
FROM pr_work_order w
LEFT JOIN (
  SELECT s.order_id, s.style_id, SUM(d.amount) AS amount
  FROM fn_cost_settlement s
  JOIN fn_cost_detail d ON d.settle_id = s.settle_id AND d.line_type = 2
  WHERE s.status = 1 AND s.del_flag = '0'
  GROUP BY s.order_id, s.style_id
) std ON std.order_id = w.order_id AND std.style_id = w.style_id
LEFT JOIN (
  SELECT work_order_id, SUM(amount) AS amount
  FROM hr_piece_wage GROUP BY work_order_id
) act ON act.work_order_id = w.wo_id
WHERE w.del_flag = '0';

CREATE OR REPLACE VIEW v_cash_flow_monthly AS
SELECT DATE_FORMAT(txn_date, '%Y-%m') AS period,
       SUM(CASE WHEN direction = 1 THEN amount ELSE 0 END) AS inflow,
       SUM(CASE WHEN direction = 2 THEN amount ELSE 0 END) AS outflow,
       SUM(CASE WHEN direction = 1 THEN amount ELSE -amount END) AS net_flow
FROM fn_cash_txn
GROUP BY DATE_FORMAT(txn_date, '%Y-%m');

INSERT INTO fn_account (account_id, account_no, account_name, account_type, opening_balance, current_balance, status, create_by, create_time)
VALUES
  (1, 'ACC-CASH', '现金账户', 1, 10000.00, 10000.00, 1, 'admin', NOW()),
  (2, 'ACC-BANK', '银行账户', 2, 1000000.00, 1000000.00, 1, 'admin', NOW())
ON DUPLICATE KEY UPDATE account_name = VALUES(account_name), status = VALUES(status);

INSERT INTO hr_payroll_config (config_key, config_value, remark)
VALUES
  ('si_personal', 10.5000, '社保个人承担比例(%)'),
  ('fund_personal', 5.0000, '公积金个人承担比例(%)'),
  ('si_company', 27.0000, '社保公司承担比例(%)'),
  ('fund_company', 5.0000, '公积金公司承担比例(%)'),
  ('tax_threshold', 5000.0000, '个税起征点(元/月)'),
  ('tax_rate', 3.0000, '简化个税比例(%)')
ON DUPLICATE KEY UPDATE remark = VALUES(remark);

INSERT INTO sys_menu
  (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache,
   menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
  (90110, '待办中心', 90000, 1, 'todos', 'erp/todos', 1, 0, 'C', '0', '0', 'workflow:todo:list', 'bell', 'admin', NOW(), ''),
  (90111, '审批中心', 90000, 2, 'approvals', 'erp/approvals', 1, 0, 'C', '0', '0', 'workflow:approval:list', 'stamp', 'admin', NOW(), ''),
  (90112, '资金总览', 90000, 26, 'finance/overview', 'erp/finance', 1, 0, 'C', '0', '0', 'fn:account:list', 'money', 'admin', NOW(), ''),
  (90113, '应收管理', 90000, 27, 'finance/receivables', 'erp/finance', 1, 0, 'C', '0', '0', 'fn:receivable:list', 'document', 'admin', NOW(), ''),
  (90114, '应付管理', 90000, 28, 'finance/payables', 'erp/finance', 1, 0, 'C', '0', '0', 'fn:payable:list', 'document', 'admin', NOW(), ''),
  (90115, '收付款', 90000, 29, 'finance/payments', 'erp/finance', 1, 0, 'C', '0', '0', 'fn:payment:list', 'credit-card', 'admin', NOW(), ''),
  (90116, '资金流水', 90000, 30, 'finance/cash', 'erp/finance', 1, 0, 'C', '0', '0', 'fn:cashTxn:list', 'list', 'admin', NOW(), ''),
  (90117, '新增账户', 90112, 1, '#', NULL, 1, 0, 'F', '0', '0', 'fn:account:add', '#', 'admin', NOW(), ''),
  (90118, '编辑账户', 90112, 2, '#', NULL, 1, 0, 'F', '0', '0', 'fn:account:edit', '#', 'admin', NOW(), ''),
  (90119, '登记收付款', 90115, 1, '#', NULL, 1, 0, 'F', '0', '0', 'fn:payment:add', '#', 'admin', NOW(), ''),
  (90120, '确认收付款', 90115, 2, '#', NULL, 1, 0, 'F', '0', '0', 'fn:payment:confirm', '#', 'admin', NOW(), ''),
  (90121, '应收查询', 90113, 1, '#', NULL, 1, 0, 'F', '0', '0', 'fn:receivable:query', '#', 'admin', NOW(), ''),
  (90122, '应付查询', 90114, 1, '#', NULL, 1, 0, 'F', '0', '0', 'fn:payable:query', '#', 'admin', NOW(), ''),
  (90123, '工资发放', 90024, 3, '#', NULL, 1, 0, 'F', '0', '0', 'fn:payroll:pay', '#', 'admin', NOW(), ''),
  (90124, '期间结账', 90112, 3, '#', NULL, 1, 0, 'F', '0', '0', 'fn:period:close', '#', 'admin', NOW(), ''),
  (90125, '反结账', 90112, 4, '#', NULL, 1, 0, 'F', '0', '0', 'fn:period:reopen', '#', 'admin', NOW(), ''),
  (90126, '采购审批', 90008, 8, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:approve', '#', 'admin', NOW(), ''),
  (90127, '采购驳回', 90008, 9, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:reject', '#', 'admin', NOW(), ''),
  (90128, '新建报价', 90025, 6, '#', NULL, 1, 0, 'F', '0', '0', 'sd:quotation:add', '#', 'admin', NOW(), ''),
  (90129, '编辑报价', 90025, 7, '#', NULL, 1, 0, 'F', '0', '0', 'sd:quotation:edit', '#', 'admin', NOW(), ''),
  (90130, '一键生成工单', 90026, 8, '#', NULL, 1, 0, 'F', '0', '0', 'pr:workOrder:generate', '#', 'admin', NOW(), ''),
  (90131, '退料', 90026, 9, '#', NULL, 1, 0, 'F', '0', '0', 'pr:workOrder:return', '#', 'admin', NOW(), ''),
  (90132, '批量生成工资单', 90024, 4, '#', NULL, 1, 0, 'F', '0', '0', 'hr:salarySlip:generateBatch', '#', 'admin', NOW(), ''),
  (90133, '工资单查询', 90024, 5, '#', NULL, 1, 0, 'F', '0', '0', 'hr:salarySlip:query', '#', 'admin', NOW(), ''),
  (90134, '期间查询', 90112, 5, '#', NULL, 1, 0, 'F', '0', '0', 'fn:period:list', '#', 'admin', NOW(), ''),
  (90135, '报价转订单', 90025, 8, '#', NULL, 1, 0, 'F', '0', '0', 'sd:quotation:convert', '#', 'admin', NOW(), '')
ON DUPLICATE KEY UPDATE menu_name = VALUES(menu_name), parent_id = VALUES(parent_id),
  order_num = VALUES(order_num), path = VALUES(path), component = VALUES(component),
  menu_type = VALUES(menu_type), status = VALUES(status), perms = VALUES(perms);

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r JOIN sys_menu m ON m.menu_id BETWEEN 90110 AND 90135
WHERE (r.role_key = 'finance' AND m.menu_id IN (90110,90111,90112,90113,90114,90115,90116,90117,90118,90119,90120,90121,90122,90123,90124,90133,90134))
   OR (r.role_key = 'boss' AND m.menu_id IN (90110,90111,90112,90113,90114,90115,90116,90121,90122,90125,90126,90127,90134))
   OR (r.role_key = 'hr' AND m.menu_id IN (90110,90132,90133))
   OR (r.role_key = 'planner' AND m.menu_id IN (90110,90130))
   OR (r.role_key = 'production' AND m.menu_id IN (90110,90131))
   OR (r.role_key = 'wh' AND m.menu_id IN (90110))
   OR (r.role_key = 'sales' AND m.menu_id IN (90110,90128,90129,90135))
   OR (r.role_key = 'salesAssistant' AND m.menu_id IN (90110,90128,90129,90135));

SET FOREIGN_KEY_CHECKS = 1;
