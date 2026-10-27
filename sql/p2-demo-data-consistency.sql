-- =============================================================================
-- P2 补丁：演示数据一致性收口（幂等，可重复执行）
--
-- 背景：ai-erp-13-seed-rich.sql 里的单据头「合计金额」是写死的，明细却是按
--       款式单价 × 固定数量生成的，两者从根上就不相等；库存批次有期初数量
--       但没有任何出入库流水；工单头的数量与色码计划合计也不一致。
--       结果：明细页金额对不上、库存结存与流水对不上、self_check 03/05 报错。
--
-- 本补丁做四件事：
--   1. 订单 / 采购 / 报价 三类单据：抬头合计 = 明细合计（并补齐缺失的报价明细）
--   2. 工单：抬头数量 = 色码计划合计
--   3. 库存：为期初有量但无流水的批次补一条「期初建账」入库流水
--   4. 自检：打印修复结果
--
-- 全新库：本文件的内容已并入 ai-erp-13-seed-rich.sql 结尾，不必单独执行。
-- =============================================================================
SET NAMES utf8mb4;

-- -----------------------------------------------------------------------------
-- 1.1 报价单：补齐缺失的款式明细（原来依赖「同客户存在已完结订单」才会插入，
--     条件不满足时报价单是空明细，详情页看起来像坏掉了）
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

-- -----------------------------------------------------------------------------
-- 1.2 三类单据：抬头合计按明细重算（只改不一致的，幂等）
-- -----------------------------------------------------------------------------
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

-- -----------------------------------------------------------------------------
-- 1.3 报价「已转单」状态收口：存在订单的报价应为 status=3（已转单）
--     业务规则：一张报价最多转一张订单；订单即使后续作废，报价也不再回到「已接受」，
--     需要重新报价。这里只做「1 -> 3」的补写，不反向回退。
-- -----------------------------------------------------------------------------
UPDATE sd_quotation q
JOIN (SELECT DISTINCT quote_id FROM sd_order WHERE quote_id IS NOT NULL) t ON t.quote_id = q.quote_id
SET q.status = 3, q.update_time = NOW()
WHERE q.status = 1 AND q.del_flag = '0';

-- -----------------------------------------------------------------------------
-- 1.4 单据状态回填：状态必须能被下游单据支撑（只降不升，避免凭空造进度）
--   · 采购 status=3(已到货) 但入库量 < 订购量 → 回落到 2(部分到货) / 1(已下单)
--   · 工单 status=2(已完工) 但色码完工数 < 计划数   → 回落到 1(生产中)
--   · 订单 status=4/5(已发齐/已完成) 但出货量 < 订购量 → 按实际出货回落 4/3/2
-- -----------------------------------------------------------------------------
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

-- -----------------------------------------------------------------------------
-- 2. 工单：抬头数量 = 色码计划合计（建单时就是这么写的，种子写歪了）
-- -----------------------------------------------------------------------------
UPDATE pr_work_order w
JOIN (SELECT wo_id, SUM(plan_qty) qty FROM pr_work_order_sku GROUP BY wo_id) t ON t.wo_id = w.wo_id
SET w.qty = t.qty, w.update_time = NOW()
WHERE t.qty > 0 AND w.qty <> t.qty;

-- -----------------------------------------------------------------------------
-- 3. 库存期初建账：库存有数但没有对应流水时，补一条「盘盈」入库流水，
--    使「期初 + 流水」= 结存（biz_type=5 盘盈，不进业务单据校验范围）
-- -----------------------------------------------------------------------------
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

-- -----------------------------------------------------------------------------
-- 4. 自检：以下四项都应为 0 / 0 行
-- -----------------------------------------------------------------------------
SELECT 'order total vs items' AS check_item,
       COUNT(*) AS bad,
       IF(COUNT(*) = 0, 'PASS', 'FAIL') AS result
FROM (
  SELECT o.order_id
  FROM sd_order o
  JOIN (SELECT order_id, SUM(amount) amt FROM sd_order_item GROUP BY order_id) t ON t.order_id = o.order_id
  WHERE ABS(o.total_amount - t.amt) > 0.005
) a
UNION ALL
SELECT 'purchase total vs items', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
  SELECT p.po_id
  FROM pu_purchase_order p
  JOIN (SELECT po_id, SUM(amount) amt FROM pu_purchase_order_item GROUP BY po_id) t ON t.po_id = p.po_id
  WHERE ABS(p.total_amount - t.amt) > 0.005
) b
UNION ALL
SELECT 'quotation total vs items', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
  SELECT q.quote_id
  FROM sd_quotation q
  JOIN (SELECT quote_id, SUM(amount) amt FROM sd_quotation_item GROUP BY quote_id) t ON t.quote_id = q.quote_id
  WHERE ABS(q.total_amount - t.amt) > 0.005
) c
UNION ALL
SELECT 'work order qty vs sku plan', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
  SELECT w.wo_id
  FROM pr_work_order w
  JOIN (SELECT wo_id, SUM(plan_qty) qty FROM pr_work_order_sku GROUP BY wo_id) t ON t.wo_id = w.wo_id
  WHERE t.qty > 0 AND w.qty <> t.qty
) d
UNION ALL
SELECT 'stock vs ledger', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
  SELECT i.inv_id
  FROM st_inventory i
  LEFT JOIN (
    SELECT item_type, item_id, batch_no, warehouse_id,
           SUM(CASE WHEN txn_type = 1 THEN qty ELSE -qty END) net_qty
    FROM st_stock_txn GROUP BY item_type, item_id, batch_no, warehouse_id
  ) t ON t.item_type = i.item_type AND t.item_id = i.item_id
     AND t.batch_no = i.batch_no AND t.warehouse_id = i.warehouse_id
  WHERE ABS(i.qty - COALESCE(t.net_qty, 0)) > 0.0001
) e
UNION ALL
SELECT 'quotation converted flag', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
  SELECT q.quote_id
  FROM sd_quotation q
  LEFT JOIN sd_order o ON o.quote_id = q.quote_id
  WHERE (o.order_id IS NOT NULL AND q.status <> 3)
     OR (q.status = 3 AND o.order_id IS NULL)
) f
UNION ALL
SELECT 'purchase arrived status', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
  SELECT p.po_id
  FROM pu_purchase_order p
  LEFT JOIN (SELECT source_id AS po_id, SUM(qty) qty FROM st_stock_txn WHERE source_type='pu_order' GROUP BY source_id) t ON t.po_id=p.po_id
  JOIN (SELECT po_id, SUM(qty) ord FROM pu_purchase_order_item GROUP BY po_id) i ON i.po_id=p.po_id
  WHERE p.status = 3 AND COALESCE(t.qty,0) < i.ord
) g
UNION ALL
SELECT 'work order finished status', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
  SELECT w.wo_id
  FROM pr_work_order w
  JOIN (SELECT wo_id, SUM(plan_qty) plan_qty, SUM(completed_qty) done FROM pr_work_order_sku GROUP BY wo_id) s ON s.wo_id=w.wo_id
  WHERE w.status = 2 AND s.done < s.plan_qty
) h
UNION ALL
SELECT 'order shipped status', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM (
  SELECT o.order_id
  FROM sd_order o
  JOIN (SELECT order_id, SUM(qty) ord FROM sd_order_item GROUP BY order_id) i ON i.order_id=o.order_id
  LEFT JOIN (SELECT sh.order_id, SUM(si.qty) qty FROM sh_shipment sh
             JOIN sh_shipment_item si ON si.shipment_id=sh.shipment_id
             WHERE sh.status IN (1,2) GROUP BY sh.order_id) sh ON sh.order_id=o.order_id
  WHERE o.status IN (4,5) AND COALESCE(sh.qty,0) < i.ord
) j;
