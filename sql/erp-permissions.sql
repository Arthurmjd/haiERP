-- Run after the RuoYi platform sys_* tables exist. Safe to rerun in a demo database.
SET NAMES utf8mb4;

INSERT INTO sys_menu
  (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache,
   menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
  (90000, '海岚 ERP', 0, 20, 'erp', NULL, 1, 0, 'M', '0', '0', '', 'dashboard', 'admin', NOW(), 'ERP 权限目录'),
  (90001, '工作台', 90000, 1, 'dashboard', 'erp/dashboard', 1, 0, 'C', '0', '0', 'dashboard:overview:list', 'dashboard', 'admin', NOW(), ''),
  (90002, '销售订单', 90000, 2, 'orders', 'erp/orders', 1, 0, 'C', '0', '0', 'sd:order:list', 'list', 'admin', NOW(), ''),
  (90003, '订单详情', 90002, 1, '#', NULL, 1, 0, 'F', '0', '0', 'sd:order:query', '#', 'admin', NOW(), ''),
  (90004, '新建订单', 90002, 2, '#', NULL, 1, 0, 'F', '0', '0', 'sd:order:add', '#', 'admin', NOW(), ''),
  (90005, '确认订单', 90002, 3, '#', NULL, 1, 0, 'F', '0', '0', 'sd:order:confirm', '#', 'admin', NOW(), ''),
  (90006, '缺料建议', 90000, 3, 'mrp', 'erp/mrp', 1, 0, 'C', '0', '0', 'pu:suggestion:list', 'chart', 'admin', NOW(), ''),
  (90007, '库存结存', 90000, 4, 'inventory', 'erp/inventory', 1, 0, 'C', '0', '0', 'st:inventory:list', 'box', 'admin', NOW(), ''),
  (90008, '采购订单', 90000, 5, 'purchases', 'erp/purchases', 1, 0, 'C', '0', '0', 'pu:purchaseOrder:list', 'shopping', 'admin', NOW(), ''),
  (90009, '采购详情', 90008, 1, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:query', '#', 'admin', NOW(), ''),
  (90010, '新建采购', 90008, 2, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:add', '#', 'admin', NOW(), ''),
  (90011, '确认采购', 90008, 3, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:confirm', '#', 'admin', NOW(), ''),
  (90012, '采购收货', 90008, 4, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:receive', '#', 'admin', NOW(), ''),
  (90013, '款式', 90000, 6, 'styles', NULL, 1, 0, 'C', '0', '0', 'md:style:list', '#', 'admin', NOW(), ''),
  (90014, 'SKU', 90000, 7, 'skus', NULL, 1, 0, 'C', '0', '0', 'md:sku:list', '#', 'admin', NOW(), ''),
  (90015, '物料', 90000, 8, 'materials', NULL, 1, 0, 'C', '0', '0', 'md:material:list', '#', 'admin', NOW(), ''),
  (90016, '客户', 90000, 9, 'customers', NULL, 1, 0, 'C', '0', '0', 'md:customer:list', '#', 'admin', NOW(), ''),
  (90017, '供应商', 90000, 10, 'suppliers', NULL, 1, 0, 'C', '0', '0', 'md:supplier:list', '#', 'admin', NOW(), ''),
  (90018, '工序', 90000, 11, 'processes', NULL, 1, 0, 'C', '0', '0', 'md:process:list', '#', 'admin', NOW(), ''),
  (90019, '颜色', 90000, 12, 'colors', NULL, 1, 0, 'C', '0', '0', 'md:color:list', '#', 'admin', NOW(), ''),
  (90020, '尺码', 90000, 13, 'sizes', NULL, 1, 0, 'C', '0', '0', 'md:size:list', '#', 'admin', NOW(), ''),
  (90021, '品类', 90000, 14, 'categories', NULL, 1, 0, 'C', '0', '0', 'md:category:list', '#', 'admin', NOW(), ''),
  (90022, '员工', 90000, 15, 'employees', NULL, 1, 0, 'C', '0', '0', 'hr:employee:list', '#', 'admin', NOW(), ''),
  (90023, '计件工资', 90000, 16, 'pieceWage', NULL, 1, 0, 'C', '0', '0', 'hr:pieceWage:list', '#', 'admin', NOW(), ''),
  (90024, '工资单', 90000, 17, 'salarySlip', NULL, 1, 0, 'C', '0', '0', 'hr:salarySlip:list', '#', 'admin', NOW(), ''),
  (90025, '报价单', 90000, 18, 'quotations', NULL, 1, 0, 'C', '0', '0', 'sd:quotation:list', '#', 'admin', NOW(), ''),
  (90026, '生产工单', 90000, 19, 'workOrders', NULL, 1, 0, 'C', '0', '0', 'pr:workOrder:list', '#', 'admin', NOW(), ''),
  (90027, '工单详情', 90026, 1, '#', NULL, 1, 0, 'F', '0', '0', 'pr:workOrder:query', '#', 'admin', NOW(), ''),
  (90028, '新建工单', 90026, 2, '#', NULL, 1, 0, 'F', '0', '0', 'pr:workOrder:add', '#', 'admin', NOW(), ''),
  (90029, '领料开工', 90026, 3, '#', NULL, 1, 0, 'F', '0', '0', 'pr:workOrder:issue', '#', 'admin', NOW(), ''),
  (90030, '报工', 90026, 4, '#', NULL, 1, 0, 'F', '0', '0', 'pr:process:report', '#', 'admin', NOW(), ''),
  (90031, '完工入库', 90026, 5, '#', NULL, 1, 0, 'F', '0', '0', 'pr:workOrder:finish', '#', 'admin', NOW(), ''),
  (90032, '库存流水', 90000, 20, 'transactions', NULL, 1, 0, 'C', '0', '0', 'st:txn:list', '#', 'admin', NOW(), ''),
  (90033, '出货单', 90000, 21, 'shipments', NULL, 1, 0, 'C', '0', '0', 'sh:shipment:list', '#', 'admin', NOW(), ''),
  (90034, '退货单', 90000, 22, 'returns', NULL, 1, 0, 'C', '0', '0', 'sh:return:list', '#', 'admin', NOW(), ''),
  (90035, '标准成本', 90000, 23, 'settlements', NULL, 1, 0, 'C', '0', '0', 'fn:settlement:list', '#', 'admin', NOW(), ''),
  (90036, '交付准时率', 90000, 24, 'ontime', NULL, 1, 0, 'C', '0', '0', 'dashboard:ontime:list', '#', 'admin', NOW(), ''),
  (90037, '款式成本', 90000, 25, 'styleCost', NULL, 1, 0, 'C', '0', '0', 'dashboard:styleCost:list', '#', 'admin', NOW(), ''),
  (90038, '出货详情', 90033, 1, '#', NULL, 1, 0, 'F', '0', '0', 'sh:shipment:query', '#', 'admin', NOW(), ''),
  (90039, '新建出货单', 90033, 2, '#', NULL, 1, 0, 'F', '0', '0', 'sh:shipment:add', '#', 'admin', NOW(), ''),
  (90040, '确认发货', 90033, 3, '#', NULL, 1, 0, 'F', '0', '0', 'sh:shipment:ship', '#', 'admin', NOW(), ''),
  (90041, '登记签收', 90033, 4, '#', NULL, 1, 0, 'F', '0', '0', 'sh:shipment:sign', '#', 'admin', NOW(), ''),
  (90042, '退货详情', 90034, 1, '#', NULL, 1, 0, 'F', '0', '0', 'sh:return:query', '#', 'admin', NOW(), ''),
  (90043, '登记退货', 90034, 2, '#', NULL, 1, 0, 'F', '0', '0', 'sh:return:add', '#', 'admin', NOW(), ''),
  (90044, '处理退货', 90034, 3, '#', NULL, 1, 0, 'F', '0', '0', 'sh:return:handle', '#', 'admin', NOW(), ''),
  (90045, '成本详情', 90035, 1, '#', NULL, 1, 0, 'F', '0', '0', 'fn:settlement:query', '#', 'admin', NOW(), ''),
  (90046, '成本试算', 90035, 2, '#', NULL, 1, 0, 'F', '0', '0', 'fn:settlement:calc', '#', 'admin', NOW(), ''),
  (90047, '确认结算', 90035, 3, '#', NULL, 1, 0, 'F', '0', '0', 'fn:settlement:confirm', '#', 'admin', NOW(), ''),
  (90048, '生成工资单', 90024, 1, '#', NULL, 1, 0, 'F', '0', '0', 'hr:salarySlip:generate', '#', 'admin', NOW(), ''),
  (90049, '确认工资单', 90024, 2, '#', NULL, 1, 0, 'F', '0', '0', 'hr:salarySlip:confirm', '#', 'admin', NOW(), ''),
  (90050, '报价详情', 90025, 1, '#', NULL, 1, 0, 'F', '0', '0', 'sd:quotation:query', '#', 'admin', NOW(), ''),
  (90051, '结算生成报价', 90025, 2, '#', NULL, 1, 0, 'F', '0', '0', 'sd:quotation:fromSettlement', '#', 'admin', NOW(), ''),
  (90052, '新增品类', 90021, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:category:add', '#', 'admin', NOW(), ''),
  (90053, '编辑品类', 90021, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:category:edit', '#', 'admin', NOW(), ''),
  (90054, '新增颜色', 90019, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:color:add', '#', 'admin', NOW(), ''),
  (90055, '编辑颜色', 90019, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:color:edit', '#', 'admin', NOW(), ''),
  (90056, '新增尺码', 90020, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:size:add', '#', 'admin', NOW(), ''),
  (90057, '编辑尺码', 90020, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:size:edit', '#', 'admin', NOW(), ''),
  (90058, '新增款式', 90013, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:style:add', '#', 'admin', NOW(), ''),
  (90059, '编辑款式', 90013, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:style:edit', '#', 'admin', NOW(), ''),
  (90060, '新增物料', 90015, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:material:add', '#', 'admin', NOW(), ''),
  (90061, '编辑物料', 90015, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:material:edit', '#', 'admin', NOW(), ''),
  (90062, '新增工序', 90018, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:process:add', '#', 'admin', NOW(), ''),
  (90063, '编辑工序', 90018, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:process:edit', '#', 'admin', NOW(), ''),
  (90064, '新增客户', 90016, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:customer:add', '#', 'admin', NOW(), ''),
  (90065, '编辑客户', 90016, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:customer:edit', '#', 'admin', NOW(), ''),
  (90066, '新增供应商', 90017, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:supplier:add', '#', 'admin', NOW(), ''),
  (90067, '编辑供应商', 90017, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:supplier:edit', '#', 'admin', NOW(), ''),
  (90068, '生成 SKU', 90014, 1, '#', NULL, 1, 0, 'F', '0', '0', 'md:sku:generate', '#', 'admin', NOW(), ''),
  (90069, '编辑 SKU', 90014, 2, '#', NULL, 1, 0, 'F', '0', '0', 'md:sku:edit', '#', 'admin', NOW(), ''),
  (90070, '业务预警', 90001, 1, '#', NULL, 1, 0, 'F', '0', '0', 'dashboard:alerts:list', '#', 'admin', NOW(), ''),
  (90071, '预警分析', 90001, 2, '#', NULL, 1, 0, 'F', '0', '0', 'dashboard:alerts:explain', '#', 'admin', NOW(), ''),
  (90072, '库存盘点调整', 90007, 1, '#', NULL, 1, 0, 'F', '0', '0', 'st:txn:adjust', '#', 'admin', NOW(), ''),
  (90073, '缺料生成采购', 90006, 1, '#', NULL, 1, 0, 'F', '0', '0', 'pu:suggestion:generate', '#', 'admin', NOW(), ''),
  (90074, '接受报价', 90025, 3, '#', NULL, 1, 0, 'F', '0', '0', 'sd:quotation:accept', '#', 'admin', NOW(), ''),
  (90075, '报价失效', 90025, 4, '#', NULL, 1, 0, 'F', '0', '0', 'sd:quotation:expire', '#', 'admin', NOW(), ''),
  (90076, '新增员工', 90022, 1, '#', NULL, 1, 0, 'F', '0', '0', 'hr:employee:add', '#', 'admin', NOW(), ''),
  (90077, '编辑员工', 90022, 2, '#', NULL, 1, 0, 'F', '0', '0', 'hr:employee:edit', '#', 'admin', NOW(), ''),
  (90078, '看板缺料汇总', 90001, 3, '#', NULL, 1, 0, 'F', '0', '0', 'dashboard:mrp:list', '#', 'admin', NOW(), '')
ON DUPLICATE KEY UPDATE menu_name=VALUES(menu_name), parent_id=VALUES(parent_id),
  order_num=VALUES(order_num), path=VALUES(path), component=VALUES(component),
  menu_type=VALUES(menu_type), status=VALUES(status), perms=VALUES(perms);

-- The historical platform seed does not contain every ERP role in the v2 plan.
INSERT INTO sys_role (role_name,role_key,role_sort,data_scope,status,del_flag,create_by,create_time)
SELECT planned.role_name,planned.role_key,planned.role_sort,'1','0','0','admin',NOW()
FROM (
  SELECT '销售' AS role_name,'sales' AS role_key,20 AS role_sort UNION ALL
  SELECT '财务','finance',21 UNION ALL
  SELECT '计划员' AS role_name,'planner' AS role_key,30 AS role_sort UNION ALL
  SELECT '生产主管','production',31 UNION ALL
  SELECT '仓管员','wh',32 UNION ALL
  SELECT '人事员','hr',33 UNION ALL
  SELECT '厂长','boss',34
) planned
WHERE NOT EXISTS (SELECT 1 FROM sys_role r WHERE r.role_key=planned.role_key AND r.del_flag='0');

-- Admin has all permissions in RuoYi; these grants cover existing non-admin roles.
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r JOIN sys_menu m ON m.menu_id BETWEEN 90000 AND 90078
WHERE r.role_key = 'supervisor'
   OR (r.role_key IN ('sales', 'salesAssistant') AND m.menu_id IN (90000,90001,90002,90003,90004,90005,90016,90025,90033,90034,90036,90038,90039,90040,90041,90042,90043,90044,90050,90051,90064,90065,90070,90071,90074,90075))
   OR (r.role_key = 'hr' AND m.menu_id IN (90000,90001,90022,90023,90024,90048,90049,90076,90077))
   OR (r.role_key = 'buyer' AND m.menu_id IN (90000,90001,90006,90008,90009,90010,90011,90012,90015,90017,90070,90071,90073))
   OR (r.role_key = 'mc' AND m.menu_id IN (90000,90001,90007,90013,90014,90015,90018,90019,90020,90021,90026,90027,90028,90029,90030,90031,90032,90070,90071,90072))
   OR (r.role_key = 'dataVisitor' AND m.menu_id IN (90000,90001,90002,90003,90006,90007,90008,90009,90013,90014,90015,90016,90017,90018,90019,90020,90021,90026,90027,90032,90033,90034,90036,90038,90042,90070,90071))
   OR (r.role_key = 'planner' AND m.menu_id IN (90000,90001,90002,90003,90006,90008,90009,90010,90011,90012,90013,90014,90015,90017,90026,90027,90028,90033,90034,90070,90071,90073,90078))
   OR (r.role_key = 'production' AND m.menu_id IN (90000,90001,90002,90003,90007,90013,90014,90015,90018,90026,90027,90028,90029,90030,90031,90032,90070,90071))
   OR (r.role_key = 'wh' AND m.menu_id IN (90000,90001,90007,90008,90009,90012,90026,90027,90029,90031,90032,90033,90034,90038,90039,90040,90041,90042,90043,90044,90070,90071,90072))
   OR (r.role_key = 'finance' AND m.menu_id IN (90000,90001,90002,90003,90025,90033,90034,90035,90036,90037,90038,90042,90045,90046,90047,90050,90051,90070,90071))
   OR (r.role_key = 'boss' AND m.menu_id IN (90000,90001,90002,90003,90006,90007,90008,90009,90013,90014,90015,90016,90017,90025,90026,90027,90032,90033,90034,90035,90036,90037,90038,90042,90045,90050,90070,90071,90078));

-- Remove legacy salary grants if this seed was already applied before role workspaces.
DELETE rm FROM sys_role_menu rm JOIN sys_role r ON r.role_id = rm.role_id
WHERE r.role_key = 'boss' AND rm.menu_id IN (90022,90023,90024);

-- 90079 起：删除（remove）与作废（cancel）。此前全系统没有任何删除权限，
-- 导致主数据、单据只能增改不能删。作废只给状态机里有 9（已取消）的单据。
INSERT INTO sys_menu
  (menu_id, menu_name, parent_id, order_num, path, component, is_frame, is_cache,
   menu_type, visible, status, perms, icon, create_by, create_time, remark)
VALUES
  (90079, '删除品类', 90021, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:category:remove', '#', 'admin', NOW(), ''),
  (90080, '删除员工', 90022, 3, '#', NULL, 1, 0, 'F', '0', '0', 'hr:employee:remove', '#', 'admin', NOW(), ''),
  (90081, '删除订单', 90002, 4, '#', NULL, 1, 0, 'F', '0', '0', 'sd:order:remove', '#', 'admin', NOW(), ''),
  (90082, '作废订单', 90002, 5, '#', NULL, 1, 0, 'F', '0', '0', 'sd:order:cancel', '#', 'admin', NOW(), ''),
  (90083, '删除采购单', 90008, 5, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:remove', '#', 'admin', NOW(), ''),
  (90084, '作废采购单', 90008, 6, '#', NULL, 1, 0, 'F', '0', '0', 'pu:purchaseOrder:cancel', '#', 'admin', NOW(), ''),
  (90085, '删除颜色', 90019, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:color:remove', '#', 'admin', NOW(), ''),
  (90086, '删除尺码', 90020, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:size:remove', '#', 'admin', NOW(), ''),
  (90087, '删除工单', 90026, 6, '#', NULL, 1, 0, 'F', '0', '0', 'pr:workOrder:remove', '#', 'admin', NOW(), ''),
  (90088, '作废工单', 90026, 7, '#', NULL, 1, 0, 'F', '0', '0', 'pr:workOrder:cancel', '#', 'admin', NOW(), ''),
  (90089, '删除出货单', 90033, 5, '#', NULL, 1, 0, 'F', '0', '0', 'sh:shipment:remove', '#', 'admin', NOW(), ''),
  (90090, '作废出货单', 90033, 6, '#', NULL, 1, 0, 'F', '0', '0', 'sh:shipment:cancel', '#', 'admin', NOW(), ''),
  (90091, '删除退货单', 90034, 4, '#', NULL, 1, 0, 'F', '0', '0', 'sh:return:remove', '#', 'admin', NOW(), ''),
  (90092, '作废退货单', 90034, 5, '#', NULL, 1, 0, 'F', '0', '0', 'sh:return:cancel', '#', 'admin', NOW(), ''),
  (90093, '删除款式', 90013, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:style:remove', '#', 'admin', NOW(), ''),
  (90094, '删除 SKU', 90014, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:sku:remove', '#', 'admin', NOW(), ''),
  (90095, '删除物料', 90015, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:material:remove', '#', 'admin', NOW(), ''),
  (90096, '删除客户', 90016, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:customer:remove', '#', 'admin', NOW(), ''),
  (90097, '删除供应商', 90017, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:supplier:remove', '#', 'admin', NOW(), ''),
  (90098, '删除工序', 90018, 3, '#', NULL, 1, 0, 'F', '0', '0', 'md:process:remove', '#', 'admin', NOW(), ''),
  (90099, '删除报价单', 90025, 5, '#', NULL, 1, 0, 'F', '0', '0', 'sd:quotation:remove', '#', 'admin', NOW(), ''),
  (90100, '删除成本单', 90035, 4, '#', NULL, 1, 0, 'F', '0', '0', 'fn:settlement:remove', '#', 'admin', NOW(), '')
ON DUPLICATE KEY UPDATE menu_name=VALUES(menu_name), parent_id=VALUES(parent_id),
  order_num=VALUES(order_num), perms=VALUES(perms);

-- 各岗位只拿到自己业务范围内的删除/作废权。admin 在 RuoYi 里本身拥有全部权限。
INSERT IGNORE INTO sys_role_menu (role_id, menu_id)
SELECT r.role_id, m.menu_id
FROM sys_role r JOIN sys_menu m ON m.menu_id BETWEEN 90079 AND 90100
WHERE (r.role_key IN ('sales','salesAssistant') AND m.menu_id IN (90081,90082,90099))
   OR (r.role_key = 'planner' AND m.menu_id IN (90083,90084,90087,90088))
   OR (r.role_key = 'production' AND m.menu_id IN (90087,90088))
   OR (r.role_key = 'wh' AND m.menu_id IN (90089,90090,90091,90092))
   OR (r.role_key = 'hr' AND m.menu_id IN (90080))
   OR (r.role_key = 'finance' AND m.menu_id IN (90100))
   OR (r.role_key = 'mc' AND m.menu_id IN (90079,90085,90086,90093,90094,90095,90096,90097,90098));
