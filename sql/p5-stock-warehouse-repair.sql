-- ============================================================================
-- p5：修掉「发货说成品库存不足」留下的两类历史脏数据（可重复执行）
--
-- 背景：ErpStockMapper 早期把 warehouse_id=1 写死在扣减 SQL 里，而成品库存分布在
-- 2 号仓；同时库存流水的 warehouse_id 走表默认值 1，扣减却可能发生在 2 号仓。
-- 代码已改（按实际有货的仓库逐仓扣、流水记录真实仓库），本脚本负责把已有数据摆平。
--
-- 执行顺序：在 self_check.sql 之前执行；只需执一次，重复执行无副作用。
-- ============================================================================
SET NAMES utf8mb4;

-- ---------------------------------------------------------------------------
-- 1. 出库流水记错仓库：流水挂在 A 仓，结存却在唯一的 B 仓
--    只在「A 仓没有任何结存行、且该批号仅 B 仓有结存」时搬动，避免误改。
-- ---------------------------------------------------------------------------
UPDATE st_stock_txn t
JOIN (
    SELECT t2.txn_id, MIN(i.warehouse_id) AS warehouse_id
    FROM st_stock_txn t2
    JOIN st_inventory i
      ON i.item_type = t2.item_type AND i.item_id = t2.item_id AND i.batch_no = t2.batch_no
    WHERE t2.txn_type = 2
      AND NOT EXISTS (
          SELECT 1 FROM st_inventory x
          WHERE x.item_type = t2.item_type AND x.item_id = t2.item_id
            AND x.batch_no = t2.batch_no AND x.warehouse_id = t2.warehouse_id)
    GROUP BY t2.txn_id
    HAVING COUNT(DISTINCT i.warehouse_id) = 1
) fix ON fix.txn_id = t.txn_id
SET t.warehouse_id = fix.warehouse_id;

-- ---------------------------------------------------------------------------
-- 2. 完工入库流水缺 source_line_id：回填到 pr_work_order_sku 的明细行
--    （新代码已直接写这一列，这里只补历史行）
-- ---------------------------------------------------------------------------
UPDATE st_stock_txn t
JOIN pr_work_order_sku s ON s.wo_id = t.source_id AND s.sku_id = t.item_id
SET t.source_line_id = s.id
WHERE t.biz_type = 3 AND t.source_type = 'pr_wo' AND t.source_line_id IS NULL;

-- ---------------------------------------------------------------------------
-- 3. 自检
-- ---------------------------------------------------------------------------
SELECT 'P5-01 stock matches ledger both ways', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM (
    SELECT i.inv_id
    FROM st_inventory i
    LEFT JOIN (SELECT item_type, item_id, batch_no, warehouse_id,
                      SUM(IF(txn_type = 1, qty, -qty)) AS net_qty
               FROM st_stock_txn GROUP BY item_type, item_id, batch_no, warehouse_id) t
           ON t.item_type = i.item_type AND t.item_id = i.item_id
          AND t.batch_no = i.batch_no AND t.warehouse_id = i.warehouse_id
    WHERE ABS(i.qty - COALESCE(t.net_qty, 0)) > 0.0001
    UNION ALL
    SELECT -t.item_id
    FROM (SELECT item_type, item_id, batch_no, warehouse_id
          FROM st_stock_txn GROUP BY item_type, item_id, batch_no, warehouse_id) t
    LEFT JOIN st_inventory i ON i.item_type = t.item_type AND i.item_id = t.item_id
          AND i.batch_no = t.batch_no AND i.warehouse_id = t.warehouse_id
    WHERE i.inv_id IS NULL
) bad
UNION ALL
SELECT 'P5-02 ledger rows trace to document lines', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM st_stock_txn WHERE biz_type IN (1, 2, 3, 4, 7) AND source_line_id IS NULL
UNION ALL
SELECT 'P5-03 no outbound txn in empty warehouse', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM st_stock_txn t
WHERE t.txn_type = 2
  AND NOT EXISTS (SELECT 1 FROM st_inventory i
                  WHERE i.item_type = t.item_type AND i.item_id = t.item_id
                    AND i.batch_no = t.batch_no AND i.warehouse_id = t.warehouse_id)
  AND EXISTS (SELECT 1 FROM st_inventory i2
              WHERE i2.item_type = t.item_type AND i2.item_id = t.item_id AND i2.batch_no = t.batch_no)
UNION ALL
SELECT 'P5-04 finished-goods stock not negative', IF(COUNT(*) = 0, 'PASS', CONCAT('FAIL: ', COUNT(*)))
FROM st_inventory WHERE item_type = 2 AND qty < 0;
