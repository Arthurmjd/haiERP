-- ============================================================
-- 海岚 ERP · 系统管理权限与组织架构初始化
-- ------------------------------------------------------------
-- 用途：补齐 RuoYi 底座自带的 system:* 权限项，让系统管理页面
--      （用户 / 角色 / 菜单 / 部门 / 岗位）的后端 @PreAuthorize
--      能真正拦截未授权请求，同时给前端提供一致的权限字符串。
--
-- 菜单 ID 段：90200 - 90299（90000-90104 已被 ERP 业务占用）
-- 执行前提：sys_menu / sys_role / sys_user / sys_dept / sys_post 表已存在
-- 幂等：可重复执行
-- ============================================================

SET NAMES utf8mb4;

-- ------------------------------------------------------------
-- 1. 系统管理菜单树
--    90200 系统管理（目录 M）
--      90201 用户管理（C）  90210 岗位管理（C）
--      90202 角色管理（C）  90211 字典管理（C）
--      90203 菜单管理（C）  90212 参数配置（C）
--      90204 部门管理（C）  90213 单号规则（C）
--      90205 通知公告（C）
-- ------------------------------------------------------------
INSERT INTO sys_menu
  (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache,
   menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
  (90200, '系统管理', 0, 30, 'system', NULL, 1, 0, 'M', '0', '0', '', 'system', 'admin', NOW(), '系统管理目录'),

  (90201, '用户管理', 90200, 1, 'user', 'system/user', 1, 0, 'C', '0', '0', 'system:user:list', 'user', 'admin', NOW(), '用户管理菜单'),
  (90202, '角色管理', 90200, 2, 'role', 'system/role', 1, 0, 'C', '0', '0', 'system:role:list', 'peoples', 'admin', NOW(), '角色管理菜单'),
  (90203, '菜单管理', 90200, 3, 'menu', 'system/menu', 1, 0, 'C', '0', '0', 'system:menu:list', 'tree-table', 'admin', NOW(), '菜单管理菜单'),
  (90204, '部门管理', 90200, 4, 'dept', 'system/dept', 1, 0, 'C', '0', '0', 'system:dept:list', 'tree', 'admin', NOW(), '部门管理菜单'),
  (90205, '通知公告', 90200, 5, 'notice', 'system/notice', 1, 0, 'C', '0', '0', 'system:notice:list', 'bell', 'admin', NOW(), '通知公告菜单'),
  (90210, '岗位管理', 90200, 6, 'post', 'system/post', 1, 0, 'C', '0', '0', 'system:post:list', 'post', 'admin', NOW(), '岗位管理菜单'),
  (90211, '字典管理', 90200, 7, 'dict', 'system/dict', 1, 0, 'C', '0', '0', 'system:dict:list', 'dict', 'admin', NOW(), '字典管理菜单'),
  (90212, '参数配置', 90200, 8, 'config', 'system/config', 1, 0, 'C', '0', '0', 'system:config:list', 'edit', 'admin', NOW(), '参数配置菜单'),
  (90213, '单号规则', 90200, 9, 'billcodeRule', 'system/billcodeRule', 1, 0, 'C', '0', '0', 'sys:billcodeRule:list', 'editPen', 'admin', NOW(), '单号规则菜单'),

  (90206, '用户查询', 90201, 1, '#', NULL, 1, 0, 'F', '0', '0', 'system:user:query', '#', 'admin', NOW(), ''),
  (90207, '用户新增', 90201, 2, '#', NULL, 1, 0, 'F', '0', '0', 'system:user:add', '#', 'admin', NOW(), ''),
  (90208, '用户修改', 90201, 3, '#', NULL, 1, 0, 'F', '0', '0', 'system:user:edit', '#', 'admin', NOW(), ''),
  (90209, '用户删除', 90201, 4, '#', NULL, 1, 0, 'F', '0', '0', 'system:user:remove', '#', 'admin', NOW(), ''),
  (90230, '重置密码', 90201, 5, '#', NULL, 1, 0, 'F', '0', '0', 'system:user:resetPwd', '#', 'admin', NOW(), ''),
  (90231, '用户导入', 90201, 6, '#', NULL, 1, 0, 'F', '0', '0', 'system:user:import', '#', 'admin', NOW(), ''),
  (90232, '用户导出', 90201, 7, '#', NULL, 1, 0, 'F', '0', '0', 'system:user:export', '#', 'admin', NOW(), ''),

  (90214, '角色查询', 90202, 1, '#', NULL, 1, 0, 'F', '0', '0', 'system:role:query', '#', 'admin', NOW(), ''),
  (90215, '角色新增', 90202, 2, '#', NULL, 1, 0, 'F', '0', '0', 'system:role:add', '#', 'admin', NOW(), ''),
  (90216, '角色修改', 90202, 3, '#', NULL, 1, 0, 'F', '0', '0', 'system:role:edit', '#', 'admin', NOW(), ''),
  (90217, '角色删除', 90202, 4, '#', NULL, 1, 0, 'F', '0', '0', 'system:role:remove', '#', 'admin', NOW(), ''),
  (90218, '角色导出', 90202, 5, '#', NULL, 1, 0, 'F', '0', '0', 'system:role:export', '#', 'admin', NOW(), ''),

  (90219, '菜单查询', 90203, 1, '#', NULL, 1, 0, 'F', '0', '0', 'system:menu:query', '#', 'admin', NOW(), ''),
  (90220, '菜单新增', 90203, 2, '#', NULL, 1, 0, 'F', '0', '0', 'system:menu:add', '#', 'admin', NOW(), ''),
  (90221, '菜单修改', 90203, 3, '#', NULL, 1, 0, 'F', '0', '0', 'system:menu:edit', '#', 'admin', NOW(), ''),
  (90222, '菜单删除', 90203, 4, '#', NULL, 1, 0, 'F', '0', '0', 'system:menu:remove', '#', 'admin', NOW(), ''),

  (90223, '部门查询', 90204, 1, '#', NULL, 1, 0, 'F', '0', '0', 'system:dept:query', '#', 'admin', NOW(), ''),
  (90224, '部门新增', 90204, 2, '#', NULL, 1, 0, 'F', '0', '0', 'system:dept:add', '#', 'admin', NOW(), ''),
  (90225, '部门修改', 90204, 3, '#', NULL, 1, 0, 'F', '0', '0', 'system:dept:edit', '#', 'admin', NOW(), ''),
  (90226, '部门删除', 90204, 4, '#', NULL, 1, 0, 'F', '0', '0', 'system:dept:remove', '#', 'admin', NOW(), ''),

  (90227, '公告查询', 90205, 1, '#', NULL, 1, 0, 'F', '0', '0', 'system:notice:query', '#', 'admin', NOW(), ''),
  (90228, '公告新增', 90205, 2, '#', NULL, 1, 0, 'F', '0', '0', 'system:notice:add', '#', 'admin', NOW(), ''),
  (90229, '公告修改', 90205, 3, '#', NULL, 1, 0, 'F', '0', '0', 'system:notice:edit', '#', 'admin', NOW(), ''),
  (90233, '公告删除', 90205, 4, '#', NULL, 1, 0, 'F', '0', '0', 'system:notice:remove', '#', 'admin', NOW(), ''),

  (90234, '岗位查询', 90210, 1, '#', NULL, 1, 0, 'F', '0', '0', 'system:post:query', '#', 'admin', NOW(), ''),
  (90235, '岗位新增', 90210, 2, '#', NULL, 1, 0, 'F', '0', '0', 'system:post:add', '#', 'admin', NOW(), ''),
  (90236, '岗位修改', 90210, 3, '#', NULL, 1, 0, 'F', '0', '0', 'system:post:edit', '#', 'admin', NOW(), ''),
  (90237, '岗位删除', 90210, 4, '#', NULL, 1, 0, 'F', '0', '0', 'system:post:remove', '#', 'admin', NOW(), ''),
  (90238, '岗位导出', 90210, 5, '#', NULL, 1, 0, 'F', '0', '0', 'system:post:export', '#', 'admin', NOW(), ''),

  (90239, '字典查询', 90211, 1, '#', NULL, 1, 0, 'F', '0', '0', 'system:dict:query', '#', 'admin', NOW(), ''),
  (90240, '字典新增', 90211, 2, '#', NULL, 1, 0, 'F', '0', '0', 'system:dict:add', '#', 'admin', NOW(), ''),
  (90241, '字典修改', 90211, 3, '#', NULL, 1, 0, 'F', '0', '0', 'system:dict:edit', '#', 'admin', NOW(), ''),
  (90242, '字典删除', 90211, 4, '#', NULL, 1, 0, 'F', '0', '0', 'system:dict:remove', '#', 'admin', NOW(), ''),
  (90243, '字典导出', 90211, 5, '#', NULL, 1, 0, 'F', '0', '0', 'system:dict:export', '#', 'admin', NOW(), ''),

  (90244, '参数查询', 90212, 1, '#', NULL, 1, 0, 'F', '0', '0', 'system:config:query', '#', 'admin', NOW(), ''),
  (90245, '参数新增', 90212, 2, '#', NULL, 1, 0, 'F', '0', '0', 'system:config:add', '#', 'admin', NOW(), ''),
  (90246, '参数修改', 90212, 3, '#', NULL, 1, 0, 'F', '0', '0', 'system:config:edit', '#', 'admin', NOW(), ''),
  (90247, '参数删除', 90212, 4, '#', NULL, 1, 0, 'F', '0', '0', 'system:config:remove', '#', 'admin', NOW(), ''),
  (90248, '参数导出', 90212, 5, '#', NULL, 1, 0, 'F', '0', '0', 'system:config:export', '#', 'admin', NOW(), ''),

  (90249, '单号规则查询', 90213, 1, '#', NULL, 1, 0, 'F', '0', '0', 'sys:billcodeRule:query', '#', 'admin', NOW(), ''),
  (90250, '单号规则新增', 90213, 2, '#', NULL, 1, 0, 'F', '0', '0', 'sys:billcodeRule:add', '#', 'admin', NOW(), ''),
  (90251, '单号规则修改', 90213, 3, '#', NULL, 1, 0, 'F', '0', '0', 'sys:billcodeRule:edit', '#', 'admin', NOW(), ''),
  (90252, '单号规则删除', 90213, 4, '#', NULL, 1, 0, 'F', '0', '0', 'sys:billcodeRule:remove', '#', 'admin', NOW(), ''),
  (90253, '单号规则导出', 90213, 5, '#', NULL, 1, 0, 'F', '0', '0', 'sys:billcodeRule:export', '#', 'admin', NOW(), '')
ON DUPLICATE KEY UPDATE menu_name=VALUES(menu_name), parent_id=VALUES(parent_id),
  order_num=VALUES(order_num), path=VALUES(path), component=VALUES(component),
  menu_type=VALUES(menu_type), perms=VALUES(perms), icon=VALUES(icon);

-- ------------------------------------------------------------
-- 2. 授权：管理员拿全部系统管理权限
--    RuoYi 中 admin（role_id=1）本身拥有 *:*:*，此处仍显式落库，
--    便于换用普通角色做系统管理的只读授权。
-- ------------------------------------------------------------
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r JOIN sys_menu m ON m.menu_id BETWEEN 90200 AND 90299
WHERE r.role_key = 'admin';

-- ------------------------------------------------------------
-- 3. 组织架构：服装代工厂的部门与岗位
--    部门层级：海岚服饰 → 业务部门
-- ------------------------------------------------------------
INSERT INTO sys_dept
  (dept_id, dept_name, parent_id, ancestors, order_num, leader, status, del_flag, create_by, create_time)
VALUES
  (100, '海岚服饰', 0, '0', 1, '管理员', '0', '0', 'admin', NOW()),
  (101, '销售部', 100, '0,100', 1, '销售', '0', '0', 'admin', NOW()),
  (102, '计划部', 100, '0,100', 2, '计划员', '0', '0', 'admin', NOW()),
  (103, '生产部', 100, '0,100', 3, '生产主管', '0', '0', 'admin', NOW()),
  (104, '仓储部', 100, '0,100', 4, '仓管员', '0', '0', 'admin', NOW()),
  (105, '财务部', 100, '0,100', 5, '财务', '0', '0', 'admin', NOW()),
  (106, '人事部', 100, '0,100', 6, '人事', '0', '0', 'admin', NOW())
ON DUPLICATE KEY UPDATE dept_name=VALUES(dept_name), parent_id=VALUES(parent_id),
  ancestors=VALUES(ancestors), order_num=VALUES(order_num), leader=VALUES(leader),
  status=VALUES(status);

INSERT INTO sys_post
  (post_id, post_code, post_name, post_sort, status, create_by, create_time, remark)
VALUES
  (1, 'sales', '销售专员', 1, '0', 'admin', NOW(), '对接客户、报价跟单'),
  (2, 'planner', '计划专员', 2, '0', 'admin', NOW(), '物料计划、采购跟单'),
  (3, 'production', '生产主管', 3, '0', 'admin', NOW(), '工单下达与现场管理'),
  (4, 'worker', '车间工人', 4, '0', 'admin', NOW(), '领料、报工'),
  (5, 'warehouse', '仓管员', 5, '0', 'admin', NOW(), '收货、盘点、发货'),
  (6, 'finance', '成本会计', 6, '0', 'admin', NOW(), '标准成本与结算'),
  (7, 'hr', '人事专员', 7, '0', 'admin', NOW(), '员工档案与计件工资')
ON DUPLICATE KEY UPDATE post_code=VALUES(post_code), post_name=VALUES(post_name),
  post_sort=VALUES(post_sort), status=VALUES(status), remark=VALUES(remark);

-- ------------------------------------------------------------
-- 4. 把现有业务账号挂到对应部门（dept_id 为空时才有必要写）
-- ------------------------------------------------------------
UPDATE sys_user SET dept_id = 101 WHERE user_name = 'sales'   AND (dept_id IS NULL OR dept_id = 0);
UPDATE sys_user SET dept_id = 102 WHERE user_name = 'planner' AND (dept_id IS NULL OR dept_id = 0);
UPDATE sys_user SET dept_id = 103 WHERE user_name = 'production' AND (dept_id IS NULL OR dept_id = 0);
UPDATE sys_user SET dept_id = 104 WHERE user_name = 'wh'      AND (dept_id IS NULL OR dept_id = 0);
UPDATE sys_user SET dept_id = 105 WHERE user_name = 'finance' AND (dept_id IS NULL OR dept_id = 0);
UPDATE sys_user SET dept_id = 106 WHERE user_name = 'hr'      AND (dept_id IS NULL OR dept_id = 0);
UPDATE sys_user SET dept_id = 100 WHERE user_name = 'admin'   AND (dept_id IS NULL OR dept_id = 0);
UPDATE sys_user SET dept_id = 100 WHERE user_name = 'boss'    AND (dept_id IS NULL OR dept_id = 0);
