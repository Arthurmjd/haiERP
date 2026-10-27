-- =============================================================================
-- p4-rbac-completion.sql
-- 业务权限补全：让每个业务动作都至少有一个「业务角色」能执行
--
-- 背景：erp-permissions.sql 是按 menu_id 批量授权的，后来陆续新增的按钮权限
--       （90104~90135）只补进了厂长，忘了分给实际执行它的岗位，结果出现两类问题：
--   ① 财务角色拿不到 fn:account:list，连 /finance 财务页都打不开（直接 403）；
--   ② 34 个按钮权限没有任何非 admin 角色拥有：
--      收款/付款登记与确认、账户新增、期间结账、工资发放、
--      新建报价/编辑报价/报价转订单、一键生成工单、退料、
--      主数据的新增/编辑/删除（分类、颜色、尺码、工序…）。
--
-- 处理原则：
--   · 谁执行这个动作，就授给谁（财务付钱、销售报价、计划维护主数据、生产排工单、人事做工资）；
--   · 厂长保持「只读 + 采购审批」，不因为补权限而被加上写权限；
--   · monitor:operlog:remove 属于运维清理，只留给 admin，列入白名单。
--
-- 幂等：INSERT IGNORE + 按 perms 查菜单，可重复执行。
-- =============================================================================
SET NAMES utf8mb4;

-- -----------------------------------------------------------------------------
-- 1. 财务专员：资金域全流程 + 会计期间 + 工资发放
--    没有这一段，finance 账号打开「财务」菜单会直接 403。
-- -----------------------------------------------------------------------------
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.perms IN (
    'fn:account:list', 'fn:account:add', 'fn:account:edit',
    'fn:cashTxn:list',
    'fn:receivable:list', 'fn:receivable:query',
    'fn:payable:list', 'fn:payable:query',
    'fn:payment:list', 'fn:payment:add', 'fn:payment:confirm',
    'fn:period:list', 'fn:period:close', 'fn:period:reopen',
    'fn:payroll:pay')
WHERE r.role_key = 'finance' AND r.del_flag = '0';

-- -----------------------------------------------------------------------------
-- 2. 销售：报价的新建 / 编辑 / 转订单；客户删除
--    原来只有「接受 / 失效 / 删除 / 由结算生成」，等于销售自己开不了报价单。
-- -----------------------------------------------------------------------------
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.perms IN (
    'sd:quotation:add', 'sd:quotation:edit', 'sd:quotation:convert',
    'md:customer:remove')
WHERE r.role_key = 'sales' AND r.del_flag = '0';

-- -----------------------------------------------------------------------------
-- 3. 计划员：主数据（分类 / 颜色 / 尺码 / 款式 / 物料 / 工序 / 供应商 / 色码）维护
--    计划员是基础档案的归属岗位，原来只有「新增 + 删除」，没有编辑。
-- -----------------------------------------------------------------------------
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.perms IN (
    'md:category:add', 'md:category:edit', 'md:category:remove',
    'md:color:add', 'md:color:edit', 'md:color:remove',
    'md:size:add', 'md:size:edit', 'md:size:remove',
    'md:style:edit', 'md:material:edit', 'md:supplier:edit', 'md:sku:edit',
    'md:process:add', 'md:process:edit', 'md:process:remove')
WHERE r.role_key = 'planner' AND r.del_flag = '0';

-- -----------------------------------------------------------------------------
-- 4. 生产主管：一键生成工单、工序退料
-- -----------------------------------------------------------------------------
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.perms IN ('pr:workOrder:generate', 'pr:workOrder:return')
WHERE r.role_key = 'production' AND r.del_flag = '0';

-- -----------------------------------------------------------------------------
-- 5. 人事：批量生成工资单、工资单查询
-- -----------------------------------------------------------------------------
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r
JOIN sys_menu m ON m.perms IN ('hr:salarySlip:generateBatch', 'hr:salarySlip:query')
WHERE r.role_key = 'hr' AND r.del_flag = '0';

-- -----------------------------------------------------------------------------
-- 6. 自检：以下每一项都必须是 PASS
-- -----------------------------------------------------------------------------
SELECT 'P4-01 财务角色可打开财务页(fn:account:list)' AS check_item, COUNT(*) AS bad,
       IF(COUNT(*) = 1, 'PASS', 'FAIL') AS result
FROM sys_role r JOIN sys_role_menu rm ON rm.role_id = r.role_id
JOIN sys_menu m ON m.menu_id = rm.menu_id
WHERE r.role_key = 'finance' AND m.perms = 'fn:account:list'
UNION ALL
SELECT 'P4-02 财务角色可登记/确认收付款', COUNT(*), IF(COUNT(*) = 2, 'PASS', 'FAIL')
FROM sys_role r JOIN sys_role_menu rm ON rm.role_id = r.role_id
JOIN sys_menu m ON m.menu_id = rm.menu_id
WHERE r.role_key = 'finance' AND m.perms IN ('fn:payment:add', 'fn:payment:confirm')
UNION ALL
SELECT 'P4-03 销售可新建报价并转订单', COUNT(*), IF(COUNT(*) = 3, 'PASS', 'FAIL')
FROM sys_role r JOIN sys_role_menu rm ON rm.role_id = r.role_id
JOIN sys_menu m ON m.menu_id = rm.menu_id
WHERE r.role_key = 'sales' AND m.perms IN ('sd:quotation:add','sd:quotation:edit','sd:quotation:convert')
UNION ALL
SELECT 'P4-04 生产主管可一键生成工单', COUNT(*), IF(COUNT(*) = 1, 'PASS', 'FAIL')
FROM sys_role r JOIN sys_role_menu rm ON rm.role_id = r.role_id
JOIN sys_menu m ON m.menu_id = rm.menu_id
WHERE r.role_key = 'production' AND m.perms = 'pr:workOrder:generate'
UNION ALL
SELECT 'P4-05 人事可批量生成工资单', COUNT(*), IF(COUNT(*) = 1, 'PASS', 'FAIL')
FROM sys_role r JOIN sys_role_menu rm ON rm.role_id = r.role_id
JOIN sys_menu m ON m.menu_id = rm.menu_id
WHERE r.role_key = 'hr' AND m.perms = 'hr:salarySlip:generateBatch'
UNION ALL
-- 除运维专用项外，不应再有「没人能用」的按钮权限
SELECT 'P4-06 无遗留孤儿按钮权限', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
    SELECT m.perms
    FROM sys_menu m
    LEFT JOIN sys_role_menu rm ON rm.menu_id = m.menu_id
    LEFT JOIN sys_role r ON r.role_id = rm.role_id AND r.role_key <> 'admin'
    WHERE m.menu_id BETWEEN 90000 AND 90199 AND m.menu_type = 'F'
      AND m.perms <> 'monitor:operlog:remove'
    GROUP BY m.menu_id, m.perms
    HAVING COUNT(r.role_id) = 0
) orphan
UNION ALL
-- 厂长不应因为补权限而拿到资金写权限（保持只读）
SELECT 'P4-07 厂长资金权限保持只读', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM sys_role r JOIN sys_role_menu rm ON rm.role_id = r.role_id
JOIN sys_menu m ON m.menu_id = rm.menu_id
WHERE r.role_key = 'boss'
  AND m.perms IN ('fn:payment:add','fn:payment:confirm','fn:account:add','fn:account:edit',
                  'fn:period:close','fn:period:reopen','fn:payroll:pay');
