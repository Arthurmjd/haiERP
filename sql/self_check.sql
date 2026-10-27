-- AI-ERP v2.0 disposable-demo verification. Every row must be PASS.
-- Run from the repository root after init_all.sql, against the same schema.
SET NAMES utf8mb4;

SELECT '01 application tables = 35' AS check_item,
       IF(COUNT(*) = 35, 'PASS', CONCAT('FAIL: ', COUNT(*))) AS result
FROM information_schema.tables
WHERE table_schema = DATABASE() AND table_type = 'BASE TABLE'
  AND table_name REGEXP '^(md_|hr_|sd_|pu_|pr_|st_|sh_|fn_|biz_)'
UNION ALL
SELECT '02 expected views = 4', IF(COUNT(*) = 4, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM information_schema.views
WHERE table_schema = DATABASE()
  AND table_name IN ('v_material_last_price','v_mrp_demand','v_style_cost','v_order_ontime')
UNION ALL
SELECT '03 stock matches ledger both ways', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM (
  SELECT i.inv_id
  FROM st_inventory i
  LEFT JOIN (
    SELECT item_type,item_id,batch_no,warehouse_id,
           SUM(IF(txn_type = 1, qty, -qty)) AS net_qty
    FROM st_stock_txn GROUP BY item_type,item_id,batch_no,warehouse_id
  ) t ON t.item_type=i.item_type AND t.item_id=i.item_id
     AND t.batch_no=i.batch_no AND t.warehouse_id=i.warehouse_id
  WHERE ABS(i.qty-COALESCE(t.net_qty,0)) > 0.0001
  UNION ALL
  SELECT -t.item_id
  FROM (
    SELECT item_type,item_id,batch_no,warehouse_id
    FROM st_stock_txn GROUP BY item_type,item_id,batch_no,warehouse_id
  ) t
  LEFT JOIN st_inventory i ON i.item_type=t.item_type AND i.item_id=t.item_id
    AND i.batch_no=t.batch_no AND i.warehouse_id=t.warehouse_id
  WHERE i.inv_id IS NULL
) bad
UNION ALL
SELECT '04 business ledger source lines', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM st_stock_txn WHERE biz_type IN (1,2,3,4,7) AND source_line_id IS NULL
UNION ALL
SELECT '05 frozen SKU plan and completion', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM (
  SELECT w.wo_id
  FROM pr_work_order w
  LEFT JOIN (SELECT wo_id, SUM(plan_qty) plan_qty, SUM(completed_qty) completed_qty
             FROM pr_work_order_sku GROUP BY wo_id) s ON s.wo_id=w.wo_id
  LEFT JOIN (SELECT source_id wo_id, SUM(qty) completed_qty
             FROM st_stock_txn WHERE biz_type=3 GROUP BY source_id) t ON t.wo_id=w.wo_id
  WHERE w.qty<>COALESCE(s.plan_qty,0)
     OR COALESCE(s.completed_qty,0)<>COALESCE(t.completed_qty,0)
) bad
UNION ALL
SELECT '06 report totals match process progress', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM (
  SELECT wp.id
  FROM pr_work_order_process wp
  LEFT JOIN (SELECT wo_process_id, SUM(good_qty) good_qty, SUM(bad_qty) bad_qty
             FROM pr_process_report GROUP BY wo_process_id) r ON r.wo_process_id=wp.id
  WHERE wp.done_qty<>COALESCE(r.good_qty,0) OR wp.bad_qty<>COALESCE(r.bad_qty,0)
) bad
UNION ALL
SELECT '07 report SKU belongs to work order', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM pr_process_report r
LEFT JOIN pr_work_order_sku s ON s.wo_id=r.wo_id AND s.sku_id=r.sku_id
LEFT JOIN pr_work_order_process wp ON wp.id=r.wo_process_id AND wp.wo_id=r.wo_id
WHERE s.id IS NULL OR wp.id IS NULL
UNION ALL
SELECT '08 issue totals match material ledger', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM pr_work_order_material m
LEFT JOIN (SELECT source_line_id, SUM(qty) qty FROM st_stock_txn
           WHERE biz_type=2 GROUP BY source_line_id) t ON t.source_line_id=m.id
WHERE ABS(m.issued_qty-COALESCE(t.qty,0))>0.0001
UNION ALL
SELECT '09 MRP frozen demand and partial receipt',
       IF((SELECT demand_qty FROM v_mrp_demand WHERE material_id=2)=145.200
          AND (SELECT demand_qty FROM v_mrp_demand WHERE material_id=7)=39.000
          AND (SELECT on_way_qty FROM v_mrp_demand WHERE material_id=6)=140.000
          AND (SELECT qty FROM st_stock_txn WHERE source_type='pu_order' AND source_id=6)=40.000,
          'PASS','FAIL')
UNION ALL
SELECT '10 cost headers equal frozen lines', IF(COUNT(*)=0,'PASS',CONCAT('FAIL: ',COUNT(*)))
FROM fn_cost_settlement s
LEFT JOIN (SELECT settle_id,
    SUM(IF(line_type=1,amount,0)) material_cost,
    SUM(IF(line_type=2,amount,0)) labor_cost,
    SUM(IF(line_type=3,amount,0)) overhead_cost,
    SUM(amount) total_cost
  FROM fn_cost_detail GROUP BY settle_id) d ON d.settle_id=s.settle_id
WHERE s.material_cost<>COALESCE(d.material_cost,0)
   OR s.labor_cost<>COALESCE(d.labor_cost,0)
   OR s.overhead_cost<>COALESCE(d.overhead_cost,0)
   OR s.total_cost<>COALESCE(d.total_cost,0)
UNION ALL
SELECT '11 cost lines retain their own price', IF(COUNT(*)=0,'PASS',CONCAT('FAIL: ',COUNT(*)))
FROM fn_cost_detail WHERE amount<>ROUND(qty*unit_price,2)
UNION ALL
SELECT '12 style view confirmed-only',
       IF((SELECT COALESCE(SUM(total_cost),0) FROM v_style_cost)=8894.65
          AND (SELECT COALESCE(SUM(total_qty),0) FROM v_style_cost)=1560
          AND (SELECT COALESCE(SUM(settle_cnt),0) FROM v_style_cost)=2,
          'PASS','FAIL')
UNION ALL
SELECT '13 future shipment excluded today',
       IF((CURDATE()<'2026-10-21' AND (SELECT full_ship_date FROM v_order_ontime WHERE order_id=1) IS NULL
           OR CURDATE()>='2026-10-21' AND (SELECT full_ship_date FROM v_order_ontime WHERE order_id=1)='2026-10-21')
          AND (CURDATE()<'2026-10-10' AND (SELECT full_ship_date FROM v_order_ontime WHERE order_id=3) IS NULL
           OR CURDATE()>='2026-10-10' AND (SELECT full_ship_date FROM v_order_ontime WHERE order_id=3)='2026-10-10'),
          'PASS','FAIL')
UNION ALL
SELECT '14 partial then complete shipment fixture',
       IF((SELECT COALESCE(SUM(si.qty),0) FROM sh_shipment sh
           JOIN sh_shipment_item si ON si.shipment_id=sh.shipment_id
           WHERE sh.order_id=1 AND sh.ship_date<='2026-10-20')=30
          AND (SELECT COALESCE(SUM(si.qty),0) FROM sh_shipment sh
           JOIN sh_shipment_item si ON si.shipment_id=sh.shipment_id
           WHERE sh.order_id=1 AND sh.ship_date<='2026-10-21')=360
          AND (SELECT full_ship_date FROM sd_order WHERE order_id=1)='2026-10-21',
          'PASS','FAIL')
UNION ALL
SELECT '15 quotation uses confirmed settlement', IF(COUNT(*)=0,'PASS',CONCAT('FAIL: ',COUNT(*)))
FROM sd_quotation_item qi
LEFT JOIN fn_cost_settlement s ON s.settle_id=qi.source_settle_id
WHERE qi.cost_src=1 AND (s.settle_id IS NULL OR s.status<>1 OR s.del_flag<>'0'
                         OR qi.cost_ref<>s.unit_cost)
UNION ALL
SELECT '16 return by SKU and batch', IF(COUNT(*)=0,'PASS',CONCAT('FAIL: ',COUNT(*)))
FROM sh_return_item i
JOIN sh_return r ON r.return_id=i.return_id AND r.del_flag='0' AND r.status=1
LEFT JOIN (SELECT source_line_id,item_id,batch_no,SUM(qty) qty
           FROM st_stock_txn WHERE biz_type=7 AND txn_type=1
           GROUP BY source_line_id,item_id,batch_no) t
  ON t.source_line_id=i.item_id AND t.item_id=i.sku_id AND t.batch_no=i.batch_no
WHERE (r.is_restock=1 AND i.qty<>COALESCE(t.qty,0))
   OR (r.is_restock=0 AND COALESCE(t.qty,0)<>0)
UNION ALL
SELECT '17 wage report linkage unique', IF(COUNT(*)=0,'PASS',CONCAT('FAIL: ',COUNT(*)))
FROM hr_piece_wage w
LEFT JOIN pr_process_report r ON r.report_id=w.report_id
WHERE w.report_id IS NOT NULL
  AND (r.report_id IS NULL OR r.emp_id<>w.emp_id OR r.good_qty<>w.qty OR r.data_origin<>0)
UNION ALL
SELECT '18 outsourced work has no employee wages', IF(COUNT(*)=0,'PASS',CONCAT('FAIL: ',COUNT(*)))
FROM hr_piece_wage pw
JOIN pr_work_order_process wp ON wp.wo_id=pw.work_order_id AND wp.process_id=pw.process_id
WHERE wp.is_outsourced=1
UNION ALL
SELECT '19 piece wages match salary slips', IF(COUNT(*)=0,'PASS',CONCAT('FAIL: ',COUNT(*)))
FROM hr_salary_slip sl
LEFT JOIN (SELECT emp_id,wage_period,SUM(amount) amount FROM hr_piece_wage
           GROUP BY emp_id,wage_period) pw
  ON pw.emp_id=sl.emp_id AND pw.wage_period=sl.wage_period
WHERE sl.piece_amount<>COALESCE(pw.amount,0)
UNION ALL
SELECT '20 seed data present',
       IF((SELECT COUNT(*) FROM pr_work_order_sku)>0
          AND (SELECT COUNT(*) FROM pr_work_order_material)>0
          AND (SELECT COUNT(*) FROM pr_process_report)>0
          AND (SELECT COUNT(*) FROM fn_cost_detail)>0,
          'PASS','FAIL')
UNION ALL
SELECT '21 system management permissions seeded',
       IF((SELECT COUNT(*) FROM sys_menu WHERE menu_id BETWEEN 90200 AND 90299) >= 50,
          'PASS', CONCAT('FAIL: ', (SELECT COUNT(*) FROM sys_menu WHERE menu_id BETWEEN 90200 AND 90299)))
UNION ALL
SELECT '22 every demo user has a department',
       IF((SELECT COUNT(*) FROM sys_user WHERE user_id BETWEEN 1 AND 8 AND dept_id IS NULL) = 0,
          'PASS', CONCAT('FAIL: ', (SELECT COUNT(*) FROM sys_user WHERE user_id BETWEEN 1 AND 8 AND dept_id IS NULL)))
UNION ALL
SELECT '23 departments and posts initialized',
       IF((SELECT COUNT(*) FROM sys_dept WHERE dept_id BETWEEN 100 AND 106) = 7
          AND (SELECT COUNT(*) FROM sys_post WHERE post_id BETWEEN 1 AND 7) = 7,
          'PASS', CONCAT('FAIL: ', (SELECT COUNT(*) FROM sys_dept WHERE dept_id BETWEEN 100 AND 106),
                              '/', (SELECT COUNT(*) FROM sys_post WHERE post_id BETWEEN 1 AND 7)))
UNION ALL
SELECT '24 admin holds all system permissions',
       IF((SELECT COUNT(*) FROM sys_role_menu rm JOIN sys_role r ON r.role_id = rm.role_id
           WHERE r.role_key = 'admin' AND rm.menu_id BETWEEN 90200 AND 90299)
          = (SELECT COUNT(*) FROM sys_menu WHERE menu_id BETWEEN 90200 AND 90299),
          'PASS', 'FAIL');
