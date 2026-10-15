-- Local disposable demo database only. Import after erp-permissions.sql.
-- All eight accounts below use the temporary password Demo@2026!.
SET NAMES utf8mb4;

INSERT INTO sys_role
  (role_id, role_name, role_key, role_sort, data_scope, status, del_flag, create_by, create_time)
VALUES (1, '管理员', 'admin', 1, '1', '0', '0', 'bootstrap', NOW());

INSERT INTO sys_config
  (config_id, config_name, config_key, config_value, config_type, create_by, create_time)
VALUES
  (1, '演示库验证码', 'sys.account.captchaEnabled', 'false', 'Y', 'bootstrap', NOW()),
  (2, '登录黑名单', 'sys.login.blackIPList', '', 'Y', 'bootstrap', NOW());

INSERT INTO sys_user
  (user_id, user_name, nick_name, user_type, password, status, del_flag, create_by, create_time)
VALUES
  (1, 'admin', '管理员', '00', '$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52', '0', '0', 'bootstrap', NOW()),
  (2, 'sales', '销售', '00', '$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52', '0', '0', 'bootstrap', NOW()),
  (3, 'planner', '计划员', '00', '$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52', '0', '0', 'bootstrap', NOW()),
  (4, 'production', '生产主管', '00', '$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52', '0', '0', 'bootstrap', NOW()),
  (5, 'wh', '仓管员', '00', '$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52', '0', '0', 'bootstrap', NOW()),
  (6, 'finance', '财务', '00', '$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52', '0', '0', 'bootstrap', NOW()),
  (7, 'hr', '人事', '00', '$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52', '0', '0', 'bootstrap', NOW()),
  (8, 'boss', '厂长', '00', '$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52', '0', '0', 'bootstrap', NOW());

INSERT INTO sys_user_role (user_id, role_id)
SELECT u.user_id, r.role_id FROM sys_user u
JOIN sys_role r ON r.role_key = u.user_name
WHERE u.user_name IN ('admin','sales','planner','production','wh','finance','hr','boss');
