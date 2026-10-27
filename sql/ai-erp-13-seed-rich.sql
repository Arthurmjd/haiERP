-- =============================================================================
-- AI-ERP 充实演示数据：在官方种子基础上补充主数据与多状态业务单据
-- 幂等设计：主数据按编码判重、业务单据按单号判重，可重复执行。
-- 适用：已执行 init_all.sql + erp-permissions.sql 的 hai_erp 库。
-- =============================================================================
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- -----------------------------------------------------------------------------
-- 1. 主数据补充：客户 / 供应商 / 物料
-- -----------------------------------------------------------------------------
INSERT INTO md_customer (customer_code, customer_name, contact, phone, address, credit_days, credit_limit, status, create_by, create_time)
SELECT t.code, t.name, t.contact, t.phone, t.addr, t.cdays, t.climit, t.st, 'admin', NOW() FROM (
SELECT 'C004' code, '峰行户外用品公司' name, '周经理' contact, '13911110004' phone, '广州市天河区体育西路 12 号' addr, 30 cdays, 200000.00 climit, 1 st UNION ALL
SELECT 'C005', '蓝湾商贸有限公司', '吴经理', '13911110005', '杭州市余杭区文一西路 88 号', 45, 150000.00, 1 UNION ALL
SELECT 'C006', '锐步青少年体育', '郑经理', '13911110006', '成都市高新区天府二街 66 号', 30, 120000.00, 1 UNION ALL
SELECT 'C007', '飞扬运动连锁', '冯经理', '13911110007', '武汉市洪山区珞喻路 100 号', 60, 300000.00, 1 UNION ALL
SELECT 'C008', '途乐运动电商', '蒋经理', '13911110008', '南京市建邺区江东中路 369 号', 15, 80000.00, 1 UNION ALL
SELECT 'C009', '恒凯百货集团', '沈经理', '13911110009', '青岛市市南区香港中路 7 号', 60, 500000.00, 1 UNION ALL
SELECT 'C010', '优品严选电商', '韩经理', '13911110010', '合肥市蜀山区长江西路 299 号', 15, 60000.00, 1) t
WHERE NOT EXISTS (SELECT 1 FROM md_customer WHERE customer_code = t.code);

INSERT INTO md_supplier (supplier_code, supplier_name, contact, phone, address, credit_days, status, create_by, create_time)
SELECT t.code, t.name, t.contact, t.phone, t.addr, t.cdays, t.st, 'admin', NOW() FROM (
SELECT 'S004' code, '联达印染有限公司' name, '马经理' contact, '13822220004' phone, '绍兴市柯桥区滨海工业区' addr, 30 cdays, 1 st UNION ALL
SELECT 'S005', '瑞纺纱线厂', '宋经理', '13822220005', '南通市通州区川姜镇', 30, 1 UNION ALL
SELECT 'S006', '宏达包装制品厂', '唐经理', '13822220006', '东莞市寮步镇石步村', 15, 1 UNION ALL
SELECT 'S007', '佳诚辅料有限公司', '曹经理', '13822220007', '义乌市义亭镇工业区', 30, 1 UNION ALL
SELECT 'S008', '汇丰物流供应链', '潘经理', '13822220008', '深圳市宝安区西乡街道', 15, 1) t
WHERE NOT EXISTS (SELECT 1 FROM md_supplier WHERE supplier_code = t.code);

INSERT INTO md_material (material_code, material_name, category, spec, unit, ref_price, status, create_by, create_time)
SELECT t.code, t.name, t.cat, t.spec, t.unit, t.price, t.st, 'admin', NOW() FROM (
SELECT 'M009' code, '网眼透气面料' name, 1 cat, '160g/m²' spec, '米' unit, 15.50 price, 1 st UNION ALL
SELECT 'M010', '摇粒绒面料', 1, '280g/m²', '米', 19.00, 1 UNION ALL
SELECT 'M011', '四面弹力面料', 1, '200g/m²', '米', 24.00, 1 UNION ALL
SELECT 'M012', '防泼水涂层布', 1, '150D', '米', 28.00, 1 UNION ALL
SELECT 'M013', '涤纶缝纫线', 2, '402 号', '卷', 2.50, 1 UNION ALL
SELECT 'M014', '树脂拉链', 2, '5# 黑色', '条', 1.80, 1 UNION ALL
SELECT 'M015', '织唛标签', 2, '50×20mm', '个', 0.25, 1) t
WHERE NOT EXISTS (SELECT 1 FROM md_material WHERE material_code = t.code);

-- -----------------------------------------------------------------------------
-- 2. 员工补充（缝制/裁剪/质检计件工 + 职员若干）
-- -----------------------------------------------------------------------------
INSERT INTO hr_employee (emp_no, name, gender, phone, dept_id, position, emp_type, hire_date, base_salary, si_base, status, create_by, create_time)
SELECT t.no, t.name, t.gender, t.phone, t.dept, t.pos, t.etype, t.hire, t.base, t.si, t.st, 'admin', NOW() FROM (
SELECT 'E017' no, '罗建平' name, 0 gender, '13733330017' phone, 2 dept, '缝制工' pos, 1 etype, '2022-03-15' hire, 3200.00 base, 4000.00 si, 1 st UNION ALL
SELECT 'E018', '高秀英', 1, '13733330018', 2, '缝制工', 1, '2022-06-01', 3200.00, 4000.00, 1 UNION ALL
SELECT 'E019', '林国栋', 0, '13733330019', 1, '裁剪工', 1, '2021-09-10', 3500.00, 4500.00, 1 UNION ALL
SELECT 'E020', '谢丽华', 1, '13733330020', 1, '裁剪工', 1, '2023-02-20', 3300.00, 4000.00, 1 UNION ALL
SELECT 'E021', '黄志强', 0, '13733330021', 3, '质检员', 1, '2022-11-05', 3800.00, 4500.00, 1 UNION ALL
SELECT 'E022', '宋晓芳', 1, '13733330022', 3, '质检员', 1, '2023-05-12', 3600.00, 4200.00, 1 UNION ALL
SELECT 'E023', '唐建军', 0, '13733330023', 2, '缝制组长', 1, '2020-07-08', 4500.00, 5000.00, 1 UNION ALL
SELECT 'E024', '康丽娟', 1, '13733330024', 2, '缝制工', 1, '2023-08-01', 3000.00, 3500.00, 1 UNION ALL
SELECT 'E025', '贺明辉', 0, '13733330025', 4, '包装工', 1, '2023-10-15', 3000.00, 3500.00, 1 UNION ALL
SELECT 'E026', '邹雅琴', 1, '13733330026', 4, '包装工', 1, '2024-01-02', 3000.00, 3500.00, 1 UNION ALL
SELECT 'E027', '雷小军', 0, '13733330027', 2, '缝制工', 1, '2024-03-18', 3000.00, 3500.00, 1 UNION ALL
SELECT 'E028', '龚雪梅', 1, '13733330028', 2, '缝制工', 1, '2024-05-06', 3000.00, 3500.00, 1 UNION ALL
SELECT 'E029', '程国华', 0, '13733330029', 6, '财务专员', 2, '2021-04-19', 5000.00, 5000.00, 1 UNION ALL
SELECT 'E030', '苏婉婷', 1, '13733330030', 7, '人事专员', 2, '2022-09-25', 4800.00, 4800.00, 1) t
WHERE NOT EXISTS (SELECT 1 FROM hr_employee WHERE emp_no = t.no);

-- -----------------------------------------------------------------------------
-- 3. 订单补充：20 单覆盖全状态（0草稿/1确认/2生产/3完工/4出货/5完成/9取消）
--    引用现有款式 1-5 与新客户 4-10；日期相对今天保持"新鲜"。
-- -----------------------------------------------------------------------------
INSERT INTO sd_order (order_no, customer_id, order_date, delivery_date, status, total_amount, create_by, create_time)
SELECT * FROM (SELECT 'SO2026101' no, 4 cust, DATE_SUB(CURDATE(), INTERVAL 25 DAY) odate, DATE_ADD(CURDATE(), INTERVAL 5 DAY) ddate, 1 st, 46800.00 amt, 'sales' cb, NOW() ct UNION ALL
SELECT 'SO2026102', 5, DATE_SUB(CURDATE(), INTERVAL 22 DAY), DATE_ADD(CURDATE(), INTERVAL 12 DAY), 1, 32500.00, 'sales', NOW() UNION ALL
SELECT 'SO2026103', 6, DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_ADD(CURDATE(), INTERVAL 18 DAY), 2, 58200.00, 'sales', NOW() UNION ALL
SELECT 'SO2026104', 7, DATE_SUB(CURDATE(), INTERVAL 18 DAY), DATE_ADD(CURDATE(), INTERVAL 20 DAY), 2, 71400.00, 'sales', NOW() UNION ALL
SELECT 'SO2026105', 8, DATE_SUB(CURDATE(), INTERVAL 16 DAY), DATE_ADD(CURDATE(), INTERVAL 8 DAY), 2, 26800.00, 'sales', NOW() UNION ALL
SELECT 'SO2026106', 9, DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_ADD(CURDATE(), INTERVAL 25 DAY), 1, 93600.00, 'sales', NOW() UNION ALL
SELECT 'SO2026107', 10, DATE_SUB(CURDATE(), INTERVAL 12 DAY), DATE_ADD(CURDATE(), INTERVAL 3 DAY), 2, 41500.00, 'sales', NOW() UNION ALL
SELECT 'SO2026108', 4, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_SUB(CURDATE(), INTERVAL 2 DAY), 3, 38900.00, 'sales', NOW() UNION ALL
SELECT 'SO2026109', 5, DATE_SUB(CURDATE(), INTERVAL 9 DAY), DATE_SUB(CURDATE(), INTERVAL 1 DAY), 3, 52300.00, 'sales', NOW() UNION ALL
SELECT 'SO2026110', 7, DATE_SUB(CURDATE(), INTERVAL 35 DAY), DATE_SUB(CURDATE(), INTERVAL 6 DAY), 4, 86400.00, 'sales', NOW() UNION ALL
SELECT 'SO2026111', 9, DATE_SUB(CURDATE(), INTERVAL 40 DAY), DATE_SUB(CURDATE(), INTERVAL 10 DAY), 5, 67800.00, 'sales', NOW() UNION ALL
SELECT 'SO2026112', 6, DATE_SUB(CURDATE(), INTERVAL 45 DAY), DATE_SUB(CURDATE(), INTERVAL 15 DAY), 5, 44900.00, 'sales', NOW() UNION ALL
SELECT 'SO2026113', 8, DATE_SUB(CURDATE(), INTERVAL 42 DAY), DATE_SUB(CURDATE(), INTERVAL 12 DAY), 4, 33600.00, 'sales', NOW() UNION ALL
SELECT 'SO2026114', 10, DATE_SUB(CURDATE(), INTERVAL 6 DAY), DATE_ADD(CURDATE(), INTERVAL 30 DAY), 0, 28700.00, 'sales', NOW() UNION ALL
SELECT 'SO2026115', 4, DATE_SUB(CURDATE(), INTERVAL 3 DAY), DATE_ADD(CURDATE(), INTERVAL 35 DAY), 0, 35200.00, 'sales', NOW() UNION ALL
SELECT 'SO2026116', 5, DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_ADD(CURDATE(), INTERVAL 2 DAY), 2, 19800.00, 'sales', NOW() UNION ALL
SELECT 'SO2026117', 6, DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_SUB(CURDATE(), INTERVAL 8 DAY), 5, 52600.00, 'sales', NOW() UNION ALL
SELECT 'SO2026118', 7, DATE_SUB(CURDATE(), INTERVAL 8 DAY), NULL, 9, 0.00, 'sales', NOW() UNION ALL
SELECT 'SO2026119', 9, DATE_SUB(CURDATE(), INTERVAL 4 DAY), NULL, 9, 0.00, 'sales', NOW() UNION ALL
SELECT 'SO2026120', 10, DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 40 DAY), 1, 61300.00, 'sales', NOW()) t
WHERE NOT EXISTS (SELECT 1 FROM sd_order WHERE order_no = t.no);

-- 订单明细：按款式×颜色×尺码组合（引用 SKU 表中已存在的组合保证一致性）
INSERT INTO sd_order_item (order_id, style_id, color_id, size_id, qty, unit_price, amount, create_by, create_time)
SELECT o.order_id, s.style_id, s.color_id, s.size_id,
       CASE WHEN o.order_no IN ('SO2026103','SO2026104','SO2026106') THEN 400 WHEN o.order_no IN ('SO2026110','SO2026111') THEN 500 ELSE 200 END qty,
       CASE s.style_id WHEN 1 THEN 89.00 WHEN 2 THEN 129.00 WHEN 3 THEN 29.00 WHEN 4 THEN 79.00 ELSE 99.00 END uprice,
       CASE s.style_id WHEN 1 THEN 89.00 WHEN 2 THEN 129.00 WHEN 3 THEN 29.00 WHEN 4 THEN 79.00 ELSE 99.00 END
         * CASE WHEN o.order_no IN ('SO2026103','SO2026104','SO2026106') THEN 400 WHEN o.order_no IN ('SO2026110','SO2026111') THEN 500 ELSE 200 END,
       'sales', NOW()
FROM sd_order o
JOIN md_sku s ON s.sku_id IN (1, 3, 6, 10, 13, 16)
WHERE o.order_no BETWEEN 'SO2026101' AND 'SO2026120'
  AND o.status NOT IN (9)
  AND NOT EXISTS (SELECT 1 FROM sd_order_item i WHERE i.order_id = o.order_id);

-- 已发齐订单回填 full_ship_date
UPDATE sd_order SET full_ship_date = DATE_SUB(CURDATE(), INTERVAL 5 DAY) WHERE order_no IN ('SO2026111', 'SO2026112') AND full_ship_date IS NULL;
UPDATE sd_order SET full_ship_date = DATE_SUB(CURDATE(), INTERVAL 9 DAY) WHERE order_no = 'SO2026117' AND full_ship_date IS NULL;

-- -----------------------------------------------------------------------------
-- 4. 采购单补充：14 单多状态（0草稿/1已确认/2部分到货/3全部到货/9取消）
-- -----------------------------------------------------------------------------
INSERT INTO pu_purchase_order (po_no, supplier_id, order_date, expect_date, actual_arrival_date, status, total_amount, create_by, create_time, approval_status)
SELECT * FROM (SELECT 'PO2026101' no, 1 sup, DATE_SUB(CURDATE(), INTERVAL 12 DAY) odate, DATE_ADD(CURDATE(), INTERVAL 8 DAY) edate, NULL adate, 0 st, 28600.00 amt, 'planner' cb, NOW() ct, 0 ap UNION ALL
SELECT 'PO2026102', 2, DATE_SUB(CURDATE(), INTERVAL 10 DAY), DATE_ADD(CURDATE(), INTERVAL 6 DAY), NULL, 0, 15400.00, 'planner', NOW(), 0 UNION ALL
SELECT 'PO2026103', 4, DATE_SUB(CURDATE(), INTERVAL 20 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY), NULL, 1, 45200.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026104', 5, DATE_SUB(CURDATE(), INTERVAL 18 DAY), DATE_SUB(CURDATE(), INTERVAL 1 DAY), NULL, 1, 32800.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026105', 1, DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_ADD(CURDATE(), INTERVAL 2 DAY), NULL, 1, 21900.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026106', 7, DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_SUB(CURDATE(), INTERVAL 4 DAY), DATE_SUB(CURDATE(), INTERVAL 4 DAY), 2, 38700.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026107', 2, DATE_SUB(CURDATE(), INTERVAL 25 DAY), DATE_SUB(CURDATE(), INTERVAL 10 DAY), NULL, 2, 27300.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026108', 4, DATE_SUB(CURDATE(), INTERVAL 30 DAY), DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_SUB(CURDATE(), INTERVAL 16 DAY), 3, 56100.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026109', 5, DATE_SUB(CURDATE(), INTERVAL 28 DAY), DATE_SUB(CURDATE(), INTERVAL 13 DAY), DATE_SUB(CURDATE(), INTERVAL 14 DAY), 3, 43400.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026110', 8, DATE_SUB(CURDATE(), INTERVAL 26 DAY), DATE_SUB(CURDATE(), INTERVAL 11 DAY), DATE_SUB(CURDATE(), INTERVAL 12 DAY), 3, 19200.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026111', 6, DATE_SUB(CURDATE(), INTERVAL 22 DAY), DATE_SUB(CURDATE(), INTERVAL 7 DAY), DATE_SUB(CURDATE(), INTERVAL 7 DAY), 3, 8600.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026112', 7, DATE_SUB(CURDATE(), INTERVAL 8 DAY), DATE_ADD(CURDATE(), INTERVAL 10 DAY), NULL, 1, 24800.00, 'planner', NOW(), 1 UNION ALL
SELECT 'PO2026113', 1, DATE_SUB(CURDATE(), INTERVAL 6 DAY), NULL, NULL, 9, 0.00, 'planner', NOW(), 0 UNION ALL
SELECT 'PO2026114', 6, DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_ADD(CURDATE(), INTERVAL 15 DAY), NULL, 0, 11700.00, 'planner', NOW(), 0) t
WHERE NOT EXISTS (SELECT 1 FROM pu_purchase_order WHERE po_no = t.no);

INSERT INTO pu_purchase_order_item (po_id, material_id, qty, unit_price, amount, create_by, create_time)
SELECT p.po_id, m.material_id,
       CASE m.material_id WHEN 1 THEN 1500 WHEN 2 THEN 800 WHEN 6 THEN 600 WHEN 7 THEN 400 WHEN 9 THEN 900 WHEN 10 THEN 500 WHEN 11 THEN 700 WHEN 12 THEN 300 ELSE 2000 END qty,
       m.ref_price,
       CASE m.material_id WHEN 1 THEN 1500 WHEN 2 THEN 800 WHEN 6 THEN 600 WHEN 7 THEN 400 WHEN 9 THEN 900 WHEN 10 THEN 500 WHEN 11 THEN 700 WHEN 12 THEN 300 ELSE 2000 END * m.ref_price,
       'planner', NOW()
FROM pu_purchase_order p
JOIN md_material m ON m.material_id IN (1, 2, 6, 7, 9, 10, 11, 12, 13)
WHERE p.po_no BETWEEN 'PO2026101' AND 'PO2026114'
  AND p.status NOT IN (9)
  AND NOT EXISTS (SELECT 1 FROM pu_purchase_order_item i WHERE i.po_id = p.po_id);

-- -----------------------------------------------------------------------------
-- 5. 工单补充：8 单（0待下达/1生产中/2已完工）
-- -----------------------------------------------------------------------------
INSERT INTO pr_work_order (wo_no, order_id, style_id, qty, plan_start_date, plan_end_date, actual_start_date, actual_end_date, status, create_by, create_time)
SELECT * FROM (SELECT 'WO2026101' no, 8 o, 1 s, 300 qty, DATE_SUB(CURDATE(), INTERVAL 6 DAY) ps, DATE_ADD(CURDATE(), INTERVAL 4 DAY) pe, DATE_SUB(CURDATE(), INTERVAL 5 DAY) asx, NULL aex, 1 st, 'planner' cb, NOW() ct UNION ALL
SELECT 'WO2026102', 9, 2, 350, DATE_SUB(CURDATE(), INTERVAL 5 DAY), DATE_ADD(CURDATE(), INTERVAL 7 DAY), DATE_SUB(CURDATE(), INTERVAL 4 DAY), NULL, 1, 'planner', NOW() UNION ALL
SELECT 'WO2026103', 10, 1, 400, DATE_SUB(CURDATE(), INTERVAL 12 DAY), DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_SUB(CURDATE(), INTERVAL 11 DAY), DATE_SUB(CURDATE(), INTERVAL 3 DAY), 2, 'planner', NOW() UNION ALL
SELECT 'WO2026104', 11, 3, 500, DATE_SUB(CURDATE(), INTERVAL 15 DAY), DATE_SUB(CURDATE(), INTERVAL 6 DAY), DATE_SUB(CURDATE(), INTERVAL 14 DAY), DATE_SUB(CURDATE(), INTERVAL 7 DAY), 2, 'planner', NOW() UNION ALL
SELECT 'WO2026105', 12, 4, 250, DATE_SUB(CURDATE(), INTERVAL 18 DAY), DATE_SUB(CURDATE(), INTERVAL 9 DAY), DATE_SUB(CURDATE(), INTERVAL 17 DAY), DATE_SUB(CURDATE(), INTERVAL 10 DAY), 2, 'planner', NOW() UNION ALL
SELECT 'WO2026106', 13, 5, 300, DATE_SUB(CURDATE(), INTERVAL 3 DAY), DATE_ADD(CURDATE(), INTERVAL 9 DAY), NULL, NULL, 0, 'planner', NOW() UNION ALL
SELECT 'WO2026107', 16, 1, 150, DATE_SUB(CURDATE(), INTERVAL 2 DAY), DATE_ADD(CURDATE(), INTERVAL 12 DAY), NULL, NULL, 0, 'planner', NOW() UNION ALL
SELECT 'WO2026108', 3, 2, 280, DATE_SUB(CURDATE(), INTERVAL 9 DAY), DATE_ADD(CURDATE(), INTERVAL 1 DAY), DATE_SUB(CURDATE(), INTERVAL 8 DAY), NULL, 1, 'planner', NOW()) t
WHERE NOT EXISTS (SELECT 1 FROM pr_work_order WHERE wo_no = t.no);

INSERT INTO pr_work_order_sku (wo_id, sku_id, plan_qty, create_by, create_time)
SELECT w.wo_id, s.sku_id, ROUND(w.qty * 0.4), 'planner', NOW()
FROM pr_work_order w
JOIN md_sku s ON s.style_id = w.style_id AND s.sku_id IN (1, 3, 6, 10, 13, 16)
WHERE w.wo_no BETWEEN 'WO2026101' AND 'WO2026108'
  AND NOT EXISTS (SELECT 1 FROM pr_work_order_sku x WHERE x.wo_id = w.wo_id);

INSERT INTO pr_work_order_material (wo_id, material_id, unit, unit_qty, loss_rate, plan_qty, create_by, create_time)
SELECT w.wo_id, m.material_id, m.unit, 1.2, 3.00, ROUND(1.2 * w.qty * 1.03, 3), 'planner', NOW()
FROM pr_work_order w JOIN md_material m ON m.material_id IN (1, 2)
WHERE w.wo_no BETWEEN 'WO2026101' AND 'WO2026108'
  AND NOT EXISTS (SELECT 1 FROM pr_work_order_material x WHERE x.wo_id = w.wo_id);

INSERT INTO pr_work_order_process (wo_id, process_id, seq, plan_qty, unit_price, create_by, create_time)
SELECT w.wo_id, pr.process_id, pr.sort, w.qty, 0.80, 'planner', NOW()
FROM pr_work_order w JOIN md_process pr ON pr.status = 1
WHERE w.wo_no BETWEEN 'WO2026101' AND 'WO2026108'
  AND NOT EXISTS (SELECT 1 FROM pr_work_order_process x WHERE x.wo_id = w.wo_id);

-- -----------------------------------------------------------------------------
-- 6. 出货单补充：7 单
--    全部建成「0 待发货」。原因：种子里这几张订单没有对应的报工/入库记录，
--    若直接写「已发货 / 已签收」就变成没有出库流水的幽灵出货单，
--    「库存 - 出库流水」会对不上。待发货状态下它们是仓库里真实待发的任务。
-- -----------------------------------------------------------------------------
INSERT INTO sh_shipment (shipment_no, order_id, customer_id, ship_date, logistics_company, logistics_no, status, create_by, create_time)
SELECT * FROM (SELECT 'SH2026101' no, 10 o, 7 c, NULL sd, NULL lc, NULL ln, 0 st, 'wh' cb, NOW() ct UNION ALL
SELECT 'SH2026102', 11, 9, NULL, NULL, NULL, 0, 'wh', NOW() UNION ALL
SELECT 'SH2026103', 12, 6, NULL, NULL, NULL, 0, 'wh', NOW() UNION ALL
SELECT 'SH2026104', 13, 8, NULL, NULL, NULL, 0, 'wh', NOW() UNION ALL
SELECT 'SH2026105', 17, 6, NULL, NULL, NULL, 0, 'wh', NOW() UNION ALL
SELECT 'SH2026106', 8, 4, NULL, NULL, NULL, 0, 'wh', NOW() UNION ALL
SELECT 'SH2026107', 9, 5, NULL, NULL, NULL, 0, 'wh', NOW()) t
WHERE NOT EXISTS (SELECT 1 FROM sh_shipment WHERE shipment_no = t.no);

-- -----------------------------------------------------------------------------
-- 7. 库存补充：新物料多批次 + 成品库存
-- -----------------------------------------------------------------------------
INSERT INTO st_inventory (item_type, item_id, batch_no, warehouse_id, qty, safe_stock, create_by, create_time)
SELECT 1, m.material_id, CONCAT('B26-', LPAD(m.material_id, 2, '0'), '-1'), 1, 600, 100, 'wh', NOW()
FROM md_material m
WHERE m.material_id BETWEEN 9 AND 15
  AND NOT EXISTS (SELECT 1 FROM st_inventory i WHERE i.item_type = 1 AND i.item_id = m.material_id AND i.batch_no = CONCAT('B26-', LPAD(m.material_id, 2, '0'), '-1'));

INSERT INTO st_inventory (item_type, item_id, batch_no, warehouse_id, qty, safe_stock, create_by, create_time)
SELECT 1, m.material_id, CONCAT('B26-', LPAD(m.material_id, 2, '0'), '-2'), 1, 350, 100, 'wh', NOW()
FROM md_material m
WHERE m.material_id IN (1, 2, 6, 7, 9, 10, 11)
  AND NOT EXISTS (SELECT 1 FROM st_inventory i WHERE i.item_type = 1 AND i.item_id = m.material_id AND i.batch_no = CONCAT('B26-', LPAD(m.material_id, 2, '0'), '-2'));

INSERT INTO st_inventory (item_type, item_id, batch_no, warehouse_id, qty, safe_stock, create_by, create_time)
SELECT 2, s.sku_id, CONCAT('FG26-', LPAD(s.sku_id, 3, '0'), '-A'), 2, 180, 30, 'wh', NOW()
FROM md_sku s
WHERE s.sku_id IN (1, 3, 6, 10, 13, 16, 2, 4, 11, 14, 17)
  AND NOT EXISTS (SELECT 1 FROM st_inventory i WHERE i.item_type = 2 AND i.item_id = s.sku_id AND i.batch_no = CONCAT('FG26-', LPAD(s.sku_id, 3, '0'), '-A'));

INSERT INTO st_inventory (item_type, item_id, batch_no, warehouse_id, qty, safe_stock, create_by, create_time)
SELECT 2, s.sku_id, CONCAT('FG26-', LPAD(s.sku_id, 3, '0'), '-B'), 2, 90, 30, 'wh', NOW()
FROM md_sku s
WHERE s.sku_id IN (1, 6, 10, 16)
  AND NOT EXISTS (SELECT 1 FROM st_inventory i WHERE i.item_type = 2 AND i.item_id = s.sku_id AND i.batch_no = CONCAT('FG26-', LPAD(s.sku_id, 3, '0'), '-B'));

-- -----------------------------------------------------------------------------
-- 7.1 出货单明细：SKU 必须来自本订单明细、批号必须取自真实成品库存、数量不超过库存
--     （原来硬编码 CONCAT('FG','2610','-1')，库存里从来没有这个批号，
--       真点「发货」会报「出货超过订单未发数量」或「成品库存不足」）
-- -----------------------------------------------------------------------------
INSERT INTO sh_shipment_item (shipment_id, sku_id, batch_no, qty, create_by, create_time)
SELECT sh.shipment_id, x.sku_id, x.batch_no, x.qty, 'wh', NOW()
FROM sh_shipment sh
JOIN (
  SELECT oi.order_id, sku.sku_id, i.batch_no, LEAST(oi.qty, 100, i.qty) AS qty,
         ROW_NUMBER() OVER (PARTITION BY oi.order_id ORDER BY sku.sku_id) AS rn
  FROM sd_order_item oi
  JOIN md_sku sku ON sku.style_id = oi.style_id AND sku.color_id = oi.color_id
       AND sku.size_id = oi.size_id
  JOIN st_inventory i ON i.item_type = 2 AND i.item_id = sku.sku_id AND i.warehouse_id = 2
       AND i.batch_no = CONCAT('FG26-', LPAD(sku.sku_id, 3, '0'), '-A')
  WHERE i.qty > 0 AND oi.qty > 0
) x ON x.order_id = sh.order_id AND x.rn = 1 + MOD(sh.order_id, 3)
WHERE sh.shipment_no BETWEEN 'SH2026101' AND 'SH2026107'
  AND NOT EXISTS (SELECT 1 FROM sh_shipment_item y WHERE y.shipment_id = sh.shipment_id);

-- -----------------------------------------------------------------------------
-- 8. 报价单补充：4 单（0草稿/1已成交/2已过期）
-- -----------------------------------------------------------------------------
INSERT INTO sd_quotation (quote_no, customer_id, quote_date, status, total_amount, create_by, create_time, request_key)
SELECT * FROM (SELECT 'QT2026101' no, 4 c, DATE_SUB(CURDATE(), INTERVAL 20 DAY) qd, 1 st, 39800.00 amt, 'sales' cb, NOW() ct, 'seed-qt-101' rk UNION ALL
SELECT 'QT2026102', 5, DATE_SUB(CURDATE(), INTERVAL 15 DAY), 1, 27600.00, 'sales', NOW(), 'seed-qt-102' UNION ALL
SELECT 'QT2026103', 8, DATE_SUB(CURDATE(), INTERVAL 40 DAY), 2, 31200.00, 'sales', NOW(), 'seed-qt-103' UNION ALL
SELECT 'QT2026104', 10, DATE_SUB(CURDATE(), INTERVAL 2 DAY), 0, 46500.00, 'sales', NOW(), 'seed-qt-104') t
WHERE NOT EXISTS (SELECT 1 FROM sd_quotation WHERE quote_no = t.no);

INSERT INTO sd_quotation_item (quote_id, style_id, qty, unit_price, cost_ref, cost_src, source_settle_id, amount, create_by, create_time)
SELECT q.quote_id, si.style_id, si.qty, si.unit_price, 0, 0, NULL, si.amount, 'sales', NOW()
FROM sd_quotation q JOIN sd_order_item si ON si.order_id = (SELECT MIN(order_id) FROM sd_order WHERE customer_id = q.customer_id AND status = 5)
WHERE q.quote_no BETWEEN 'QT2026101' AND 'QT2026104'
  AND NOT EXISTS (SELECT 1 FROM sd_quotation_item x WHERE x.quote_id = q.quote_id);

-- -----------------------------------------------------------------------------
-- 9. 应收补充：由「真正发出去的出货单」生成
--    口径与代码保持一致（ErpFinanceService.createReceivable）：
--      金额   = Σ(出货数量 × 该订单同款色码单价)   —— ErpFinanceMapper.shipmentAmount
--      到期日 = 发货日 + 客户信用天数
--      客户   = 订单客户
--    不给待发货的出货单开票，也不手填金额，避免「订单 ¥7,200、应收 ¥58,200」这种对不上的数据。
-- -----------------------------------------------------------------------------
INSERT INTO fn_receivable (ar_no, customer_id, order_id, shipment_id, amount, received_amount, balance, due_date, status, request_key, create_by, create_time)
SELECT CONCAT('AR-SEED-', s.shipment_id), s.customer_id, s.order_id, s.shipment_id,
       amt.total, 0.00, amt.total,
       DATE_ADD(s.ship_date, INTERVAL COALESCE(c.credit_days, 30) DAY), 0,
       CONCAT('seed-ar-ship-', s.shipment_id), 'finance', NOW()
FROM sh_shipment s
JOIN sd_order o ON o.order_id = s.order_id
JOIN md_customer c ON c.customer_id = o.customer_id
JOIN (
  SELECT si.shipment_id, SUM(si.qty * oi.unit_price) AS total
  FROM sh_shipment_item si
  JOIN sh_shipment s2 ON s2.shipment_id = si.shipment_id
  JOIN md_sku sku ON sku.sku_id = si.sku_id
  JOIN sd_order_item oi ON oi.order_id = s2.order_id
       AND oi.style_id = sku.style_id AND oi.color_id = sku.color_id AND oi.size_id = sku.size_id
  GROUP BY si.shipment_id
) amt ON amt.shipment_id = s.shipment_id
WHERE s.status IN (1, 2) AND s.ship_date IS NOT NULL AND s.del_flag = '0'
  AND amt.total > 0
  AND NOT EXISTS (SELECT 1 FROM fn_receivable r WHERE r.shipment_id = s.shipment_id);

-- -----------------------------------------------------------------------------
-- 10. 待办 / 审批补充
-- -----------------------------------------------------------------------------
INSERT INTO biz_todo (todo_type, target_type, target_id, assignee_role, title, path, due_date, status, create_time)
SELECT * FROM (SELECT 'receive' ttype, 'pu_purchase_order' tt, 10 tid, 'wh' ar, '采购单 PO2026106 已到货，请安排收货' title, '/purchases/10' path, DATE_ADD(CURDATE(), INTERVAL 2 DAY) dd, 0 st, NOW() ct UNION ALL
SELECT 'receive', 'pu_purchase_order', 11, 'wh', '采购单 PO2026107 部分到货，请补录收货', '/purchases/11', DATE_ADD(CURDATE(), INTERVAL 3 DAY), 0, NOW() UNION ALL
SELECT 'approval', 'pu_purchase_order', 12, 'boss', '采购单 PO2026112 待审批（¥24,800）', '/approvals', DATE_ADD(CURDATE(), INTERVAL 1 DAY), 0, NOW() UNION ALL
SELECT 'collect', 'fn_receivable', (SELECT MIN(ar_id) FROM fn_receivable), 'finance', '应收单待催收，请跟进', '/finance/receivables', DATE_SUB(CURDATE(), INTERVAL 3 DAY), 0, NOW() UNION ALL
SELECT 'issue', 'pr_wo', 6, 'production', '工单 WO2026106 待下达领料', '/production/workOrders', DATE_ADD(CURDATE(), INTERVAL 4 DAY), 0, NOW() UNION ALL
SELECT 'issue', 'pr_wo', 7, 'production', '工单 WO2026107 待下达领料', '/production/workOrders', DATE_ADD(CURDATE(), INTERVAL 6 DAY), 0, NOW() UNION ALL
SELECT 'ship', 'sh_shipment', 6, 'wh', '出货单 SH2026106 待发货', '/shipping/shipments', DATE_ADD(CURDATE(), INTERVAL 2 DAY), 0, NOW()) t
WHERE NOT EXISTS (SELECT 1 FROM biz_todo x WHERE x.todo_type = t.ttype AND x.target_id = t.tid AND x.title = t.title);

INSERT INTO biz_approval (doc_type, doc_id, approval_type, amount, applicant, approver, status, comment, create_time)
SELECT * FROM (SELECT 'pu_purchase_order' dtype, 12 did, 'purchase' atype, 24800.00 amt, 'planner' appl, NULL appr, 0 st, NULL cmt, NOW() ct UNION ALL
SELECT 'pu_purchase_order', 13, 'purchase', 0.00, 'planner', 'boss', 2, '取消单无需审批', NOW() UNION ALL
SELECT 'pu_purchase_order', 10, 'purchase', 38700.00, 'planner', 'boss', 1, '同意，按计划执行', NOW() UNION ALL
SELECT 'pu_purchase_order', 11, 'purchase', 27300.00, 'planner', 'boss', 1, '同意', NOW()) t
WHERE NOT EXISTS (SELECT 1 FROM biz_approval x WHERE x.doc_type = t.dtype AND x.doc_id = t.did AND x.approval_type = t.atype);

-- -----------------------------------------------------------------------------
-- 11. 资金账户与流水补充
-- -----------------------------------------------------------------------------
INSERT INTO fn_account (account_no, account_name, account_type, opening_balance, current_balance, status, create_by, create_time)
SELECT * FROM (SELECT 'ACC-003' no, '招商银行基本户' name, 2 type, 500000.00 ob, 426300.00 cb, 1 st, 'finance' byx, NOW() ct UNION ALL
SELECT 'ACC-004', '微信商户账户', 3, 0.00, 58200.00, 1, 'finance', NOW()) t
WHERE NOT EXISTS (SELECT 1 FROM fn_account WHERE account_no = t.no);

-- -----------------------------------------------------------------------------
-- 11.1 账实对齐：给 ACC-003 / ACC-004 补资金流水
-- 说明：这两个账户在上面只写了「期初 / 当前」余额，没有流水，
--       导致 v_cash_balance 用「期初 + 流水」推算时与账面 current_balance 对不上。
--   ACC-003 招商银行基本户：期初 500000，当前 426300 → 净流出 73700
--   ACC-004 微信商户账户  ：期初 0，     当前 58200  → 净流入 58200
-- 幂等：按 txn_no 去重，可重复执行。
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
-- 12. 一致性收口
-- 上面的单据头「合计」是写死的、明细是按单价×固定数量生成的，两者不相等；
-- 库存批次有期初数量但没有流水；工单头数量与色码计划合计也不一致。
-- 这里统一按明细重算，并为期初库存补一条建账流水，保证：
--   抬头合计 = 明细合计、工单数量 = 色码计划合计、库存结存 = 期初 + 流水。
-- 与 sql/p2-demo-data-consistency.sql 内容一致，幂等。
-- -----------------------------------------------------------------------------
INSERT INTO sd_quotation_item
  (quote_id, style_id, qty, unit_price, cost_ref, cost_src, source_settle_id, amount, create_by, create_time)
SELECT q.quote_id, s.style_id, 200,
       CASE s.style_id WHEN 1 THEN 89.00 WHEN 2 THEN 129.00 WHEN 3 THEN 29.00 WHEN 4 THEN 79.00 ELSE 99.00 END,
       0, 0, NULL,
       200 * CASE s.style_id WHEN 1 THEN 89.00 WHEN 2 THEN 129.00 WHEN 3 THEN 29.00 WHEN 4 THEN 79.00 ELSE 99.00 END,
       'sales', NOW()
FROM sd_quotation q
JOIN (SELECT DISTINCT style_id FROM md_sku WHERE sku_id IN (1, 3, 6, 10, 13, 16)) s
WHERE q.quote_no BETWEEN 'QT2026101' AND 'QT2026104'
  AND NOT EXISTS (SELECT 1 FROM sd_quotation_item i WHERE i.quote_id = q.quote_id);

UPDATE sd_order o
JOIN (SELECT order_id, SUM(amount) amt FROM sd_order_item GROUP BY order_id) t ON t.order_id = o.order_id
SET o.total_amount = t.amt, o.update_time = NOW()
WHERE ABS(o.total_amount - t.amt) > 0.005;

UPDATE pu_purchase_order p
JOIN (SELECT po_id, SUM(amount) amt FROM pu_purchase_order_item GROUP BY po_id) t ON t.po_id = p.po_id
SET p.total_amount = t.amt, p.update_time = NOW()
WHERE ABS(p.total_amount - t.amt) > 0.005;

UPDATE sd_quotation q
JOIN (SELECT quote_id, SUM(amount) amt FROM sd_quotation_item GROUP BY quote_id) t ON t.quote_id = q.quote_id
SET q.total_amount = t.amt, q.update_time = NOW()
WHERE ABS(q.total_amount - t.amt) > 0.005;

UPDATE pr_work_order w
JOIN (SELECT wo_id, SUM(plan_qty) qty FROM pr_work_order_sku GROUP BY wo_id) t ON t.wo_id = w.wo_id
SET w.qty = t.qty, w.update_time = NOW()
WHERE t.qty > 0 AND w.qty <> t.qty;

INSERT INTO st_stock_txn
  (txn_no, txn_type, biz_type, item_type, item_id, batch_no, qty,
   warehouse_id, source_type, source_id, txn_time, request_key, remark, create_by, create_time)
SELECT CONCAT('OPEN-', x.item_type, '-', x.item_id, '-', x.batch_no),
       1, 5, x.item_type, x.item_id, x.batch_no, x.diff,
       x.warehouse_id, 'stock_check', NULL, NOW(),
       CONCAT('open-', x.item_type, '-', x.item_id, '-', x.batch_no),
       '期初建账（种子库存补流水）', 'admin', NOW()
FROM (
  SELECT i.item_type, i.item_id, i.batch_no, i.warehouse_id,
         ROUND(i.qty - COALESCE((
           SELECT SUM(CASE WHEN t.txn_type = 1 THEN t.qty ELSE -t.qty END)
           FROM st_stock_txn t
           WHERE t.item_type = i.item_type AND t.item_id = i.item_id
             AND t.batch_no = i.batch_no AND t.warehouse_id = i.warehouse_id), 0), 3) AS diff
  FROM st_inventory i
) x
WHERE x.diff > 0
  AND NOT EXISTS (
    SELECT 1 FROM st_stock_txn y
    WHERE y.request_key = CONCAT('open-', x.item_type, '-', x.item_id, '-', x.batch_no));

-- 12.1 单据状态必须被下游单据支撑（种子把状态写高了，只降不升）
UPDATE pu_purchase_order p
LEFT JOIN (SELECT source_id AS po_id, SUM(qty) qty FROM st_stock_txn WHERE source_type='pu_order' GROUP BY source_id) t
       ON t.po_id = p.po_id
JOIN (SELECT po_id, SUM(qty) ord FROM pu_purchase_order_item GROUP BY po_id) i
  ON i.po_id = p.po_id
SET p.status = IF(COALESCE(t.qty,0) > 0, 2, 1), p.update_time = NOW()
WHERE p.status = 3 AND COALESCE(t.qty,0) < i.ord;

UPDATE pr_work_order w
JOIN (SELECT wo_id, SUM(plan_qty) plan_qty, SUM(completed_qty) done
      FROM pr_work_order_sku GROUP BY wo_id) s ON s.wo_id = w.wo_id
SET w.status = 1, w.update_time = NOW()
WHERE w.status = 2 AND s.done < s.plan_qty;

UPDATE sd_order o
JOIN (SELECT order_id, SUM(qty) ord FROM sd_order_item GROUP BY order_id) i ON i.order_id = o.order_id
LEFT JOIN (SELECT sh.order_id, SUM(si.qty) qty
           FROM sh_shipment sh JOIN sh_shipment_item si ON si.shipment_id = sh.shipment_id
           WHERE sh.status IN (1,2) GROUP BY sh.order_id) sh ON sh.order_id = o.order_id
SET o.status = CASE WHEN COALESCE(sh.qty,0) >= i.ord THEN 4
                    WHEN COALESCE(sh.qty,0) > 0 THEN 3
                    ELSE 2 END,
    o.full_ship_date = NULL,
    o.update_time = NOW()
WHERE o.status IN (4,5) AND COALESCE(sh.qty,0) < i.ord;

-- 12.2 订单状态必须能被工单支撑（没有工单就不能是「生产中 / 已完工」）
--   ① 没有工单却写着生产/完工 → 回到「已确认」，等排产
UPDATE sd_order o
LEFT JOIN pr_work_order w ON w.order_id = o.order_id
SET o.status = 1, o.update_time = NOW()
WHERE o.status IN (2,3) AND w.wo_id IS NULL;

--   ② 工单已在产，订单却还停在「已确认」→ 订单进入生产中
UPDATE sd_order o
JOIN pr_work_order w ON w.order_id = o.order_id
SET o.status = 2, o.update_time = NOW()
WHERE o.status = 1 AND w.status IN (1,2);

--   ③ 订单已开工，工单却还是草稿 → 工单推进到生产中
UPDATE pr_work_order w
JOIN sd_order o ON o.order_id = w.order_id
SET w.status = 1, w.update_time = NOW()
WHERE w.status = 0 AND o.status IN (2,3);

--   ④ 订单写「已完工」，工单却没完工 → 订单回落到生产中
UPDATE sd_order o
JOIN pr_work_order w ON w.order_id = o.order_id
SET o.status = 2, o.update_time = NOW()
WHERE o.status = 3 AND w.status <> 2;

SET FOREIGN_KEY_CHECKS = 1;