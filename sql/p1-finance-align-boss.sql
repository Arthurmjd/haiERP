-- =============================================================================
-- P1 补丁：对「已经初始化过」的演示库做两件事（幂等，可重复执行）
--   1) 补厂长（boss）审批中心 / 待办中心 / 采购审批 / 财务只读权限
--   2) 补 ACC-003 / ACC-004 的资金流水，使「期初 + 流水」与账面余额对齐
--
-- 全新库不需要单独跑本文件：
--   权限已在 erp-permissions.sql + ai-erp-14-boss-permissions.sql 里，
--   流水已在 ai-erp-13-seed-rich.sql 里。
-- =============================================================================
SET NAMES utf8mb4;

-- -----------------------------------------------------------------------------
-- 1) 厂长权限补齐
-- -----------------------------------------------------------------------------
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.menu_id IN (90110, 90111, 90112, 90113, 90114, 90115, 90116, 90121, 90122, 90126, 90127, 90134,
                                 90018, 90019, 90020, 90021)
WHERE r.role_key = 'boss' AND r.del_flag = '0';

-- -----------------------------------------------------------------------------
-- 1.5 工序可报工员工：补建缺失的表 + 补权限点
--      报工逻辑会查 md_process_worker，但脚本里从未建过这张表，
--      导致内部报工直接 500（ERROR 1146）。
-- -----------------------------------------------------------------------------
CREATE TABLE IF NOT EXISTS md_process_worker (
  process_id    BIGINT       NOT NULL                COMMENT '工序ID',
  emp_id        BIGINT       NOT NULL                COMMENT '员工ID',
  create_by     VARCHAR(64)  DEFAULT ''              COMMENT '创建者',
  create_time   DATETIME     DEFAULT NULL            COMMENT '创建时间',
  PRIMARY KEY (process_id, emp_id),
  KEY idx_pw_emp (emp_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='工序可报工员工';

INSERT INTO sys_menu
  (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache,
   menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
  (90136, '工序配置员工', 90018, 4, '#', NULL, 1, 0, 'F', '0', '0', 'md:process:assign', '#', 'admin', NOW(), '工序可报工员工')
ON DUPLICATE KEY UPDATE menu_name=VALUES(menu_name), parent_id=VALUES(parent_id),
  order_num=VALUES(order_num), perms=VALUES(perms);

INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r JOIN sys_menu m ON m.menu_id = 90136
WHERE r.role_key IN ('production','mc');

-- -----------------------------------------------------------------------------
-- 2) ACC-003 / ACC-004 资金流水补齐（账实对齐）
--    ACC-003：期初 500000 → 当前 426300，净流出 73700
--    ACC-004：期初 0      → 当前 58200 ，净流入 58200
-- -----------------------------------------------------------------------------
INSERT INTO fn_cash_txn
  (txn_no, account_id, direction, amount, biz_type, counterparty_type, counterparty_id,
   source_type, source_id, txn_date, request_key, remark, create_by, create_time)
SELECT 'CT-SEED-ACC003-01', a.account_id, 2, 38700.00, 2, 2, NULL, NULL, NULL,
       DATE('2026-09-18'), 'seed-acc003-out-01', '采购付款（首批面辅料）', 'finance', NOW()
FROM fn_account a
WHERE a.account_no = 'ACC-003'
  AND NOT EXISTS (SELECT 1 FROM fn_cash_txn x WHERE x.txn_no = 'CT-SEED-ACC003-01');

INSERT INTO fn_cash_txn
  (txn_no, account_id, direction, amount, biz_type, counterparty_type, counterparty_id,
   source_type, source_id, txn_date, request_key, remark, create_by, create_time)
SELECT 'CT-SEED-ACC003-02', a.account_id, 2, 35000.00, 3, 3, NULL, NULL, NULL,
       DATE('2026-10-08'), 'seed-acc003-out-02', '工资发放（9 月）', 'finance', NOW()
FROM fn_account a
WHERE a.account_no = 'ACC-003'
  AND NOT EXISTS (SELECT 1 FROM fn_cash_txn x WHERE x.txn_no = 'CT-SEED-ACC003-02');

INSERT INTO fn_cash_txn
  (txn_no, account_id, direction, amount, biz_type, counterparty_type, counterparty_id,
   source_type, source_id, txn_date, request_key, remark, create_by, create_time)
SELECT 'CT-SEED-ACC004-01', a.account_id, 1, 58200.00, 1, 1, NULL, NULL, NULL,
       DATE('2026-10-12'), 'seed-acc004-in-01', '客户回款（微信）', 'finance', NOW()
FROM fn_account a
WHERE a.account_no = 'ACC-004'
  AND NOT EXISTS (SELECT 1 FROM fn_cash_txn x WHERE x.txn_no = 'CT-SEED-ACC004-01');

-- -----------------------------------------------------------------------------
-- 3) 自检：账面余额 vs 流水推算（两者应完全相等）
-- -----------------------------------------------------------------------------
SELECT a.account_no,
       a.current_balance                                        AS book_balance,
       a.opening_balance
         + COALESCE(SUM(CASE WHEN t.direction = 1 THEN t.amount ELSE -t.amount END), 0) AS computed_balance,
       IF(a.current_balance = a.opening_balance
            + COALESCE(SUM(CASE WHEN t.direction = 1 THEN t.amount ELSE -t.amount END), 0),
          'PASS', 'FAIL')                                       AS result
FROM fn_account a
LEFT JOIN fn_cash_txn t ON t.account_id = a.account_id
GROUP BY a.account_id, a.account_no, a.current_balance, a.opening_balance
ORDER BY a.account_id;
