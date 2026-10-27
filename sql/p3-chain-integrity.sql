-- =============================================================================
-- p3-chain-integrity.sql
-- 演示数据「单据链路」补齐：出货 → 库存 → 应收 → 收款 → 资金 全链条对账
--
-- 背景：早期种子（ai-erp-13-seed-rich.sql）里有一批「只有单头」的数据：
--   · 出货单已经是「已发货 / 已签收」，但从来没有出库流水，成品批号也从未入过库；
--   · 应收单硬编码 shipment_id = 1..5，实际出货单主键是 4..8，指到了别人的单子上；
--   · 应收金额是手填的，和「出货数量 × 订单单价」对不上（如订单 ¥7,200，应收 ¥58,200）；
--   · 订单写着「生产中 / 已完工」，但没有对应工单；
--   · SO2026120 只有金额、没有明细。
--
-- 处理原则：能追溯的就补链接、能重算的就按代码口径重算；追不出来的（凭空开的票、
-- 没有生产支撑的出货）退回一个说得通的状态，而不是硬造生产数据。
--
-- 幂等：全部条件更新 + NOT EXISTS，可重复执行。
-- =============================================================================
SET NAMES utf8mb4;

-- -----------------------------------------------------------------------------
-- 1. 幽灵出货单
--    SH2026101 ~ SH2026104：成品从未入库、也没有出库流水，却挂「已发货/已签收」
--      → 退回「待发货」，清空发货时间与物流信息（货还在仓库，等安排）
--    SH2026105：所属订单 SO2026112 连工单都没有
--      → 整单删除（连带明细、退货单）
-- -----------------------------------------------------------------------------
UPDATE sh_shipment
SET status = 0, ship_date = NULL, logistics_company = NULL, logistics_no = NULL,
    update_by = 'admin', update_time = NOW()
WHERE shipment_no IN ('SH2026101','SH2026102','SH2026103','SH2026104')
  AND status IN (1,2);

DELETE si FROM sh_shipment_item si
JOIN sh_shipment sh ON sh.shipment_id = si.shipment_id
WHERE sh.shipment_no = 'SH2026105';

DELETE sr FROM sh_return sr
JOIN sh_shipment sh ON sh.shipment_id = sr.shipment_id
WHERE sh.shipment_no = 'SH2026105';

DELETE sh FROM sh_shipment sh WHERE sh.shipment_no = 'SH2026105';

-- -----------------------------------------------------------------------------
-- 2. 客户字段对齐：出货单 / 应收单的客户必须等于订单客户
-- -----------------------------------------------------------------------------
UPDATE sh_shipment s
JOIN sd_order o ON o.order_id = s.order_id
SET s.customer_id = o.customer_id, s.update_time = NOW()
WHERE s.order_id IS NOT NULL AND s.customer_id <> o.customer_id;

-- -----------------------------------------------------------------------------
-- 3. 应收单与出货单对账
-- -----------------------------------------------------------------------------
-- 3.1 应收缺出货单时，回填本订单最近一张「已发货/已签收」的出货单
UPDATE fn_receivable r
SET r.shipment_id = (
        SELECT MAX(s.shipment_id) FROM sh_shipment s
        WHERE s.order_id = r.order_id AND s.status IN (1,2) AND s.del_flag = '0')
WHERE r.shipment_id IS NULL AND r.order_id IS NOT NULL
  AND EXISTS (SELECT 1 FROM sh_shipment s
              WHERE s.order_id = r.order_id AND s.status IN (1,2) AND s.del_flag = '0');

-- 3.2 仍然没有有效出货单支撑的应收 = 凭空开票 → 删除（连带收款凭证与资金流水）
DROP TEMPORARY TABLE IF EXISTS tmp_bad_ar;
CREATE TEMPORARY TABLE tmp_bad_ar (ar_id BIGINT PRIMARY KEY, payment_id BIGINT NULL);

INSERT INTO tmp_bad_ar (ar_id)
SELECT r.ar_id
FROM fn_receivable r
LEFT JOIN sh_shipment s ON s.shipment_id = r.shipment_id
WHERE r.shipment_id IS NULL
   OR s.shipment_id IS NULL
   OR s.status NOT IN (1,2)
   OR (r.order_id IS NOT NULL AND s.order_id <> r.order_id);

UPDATE tmp_bad_ar g
SET payment_id = (SELECT MIN(l.payment_id) FROM fn_payment_line l
                  WHERE l.target_type = 1 AND l.target_id = g.ar_id);

DELETE t FROM fn_cash_txn t
JOIN tmp_bad_ar g ON t.source_type = 'fn_payment' AND t.source_id = g.payment_id;

DELETE l FROM fn_payment_line l
JOIN tmp_bad_ar g ON l.target_type = 1 AND l.target_id = g.ar_id;

DELETE p FROM fn_payment p
WHERE p.payment_no LIKE 'PM-SEED-%'
  AND NOT EXISTS (SELECT 1 FROM fn_payment_line l WHERE l.payment_id = p.payment_id);

DELETE r FROM fn_receivable r JOIN tmp_bad_ar g ON g.ar_id = r.ar_id;

DELETE t FROM biz_todo t
JOIN tmp_bad_ar g ON t.target_type = 'fn_receivable' AND t.target_id = g.ar_id;

DROP TEMPORARY TABLE tmp_bad_ar;

-- 3.3 应收金额 / 账期按代码口径重算
--     金额   = Σ(出货数量 × 该订单同款色码的单价)        —— ErpFinanceMapper.shipmentAmount
--     到期日 = 发货日 + 客户信用天数                      —— ErpFinanceService.createReceivable
--     客户   = 订单客户
UPDATE fn_receivable r
JOIN sd_order o ON o.order_id = r.order_id
JOIN sh_shipment sh ON sh.shipment_id = r.shipment_id
JOIN md_customer c ON c.customer_id = o.customer_id
JOIN (
    SELECT si.shipment_id, SUM(si.qty * oi.unit_price) AS total
    FROM sh_shipment_item si
    JOIN sh_shipment s2 ON s2.shipment_id = si.shipment_id
    JOIN md_sku sku ON sku.sku_id = si.sku_id
    JOIN sd_order_item oi ON oi.order_id = s2.order_id
         AND oi.style_id = sku.style_id AND oi.color_id = sku.color_id
         AND oi.size_id = sku.size_id
    GROUP BY si.shipment_id
) amt ON amt.shipment_id = r.shipment_id
SET r.customer_id     = o.customer_id,
    r.amount          = amt.total,
    r.received_amount = LEAST(r.received_amount, amt.total),
    r.balance         = amt.total - LEAST(r.received_amount, amt.total),
    r.status          = CASE WHEN amt.total - LEAST(r.received_amount, amt.total) <= 0.005 THEN 2
                             WHEN r.received_amount > 0 THEN 1 ELSE 0 END,
    r.due_date        = DATE_ADD(sh.ship_date, INTERVAL COALESCE(c.credit_days, 30) DAY),
    r.update_time     = NOW()
WHERE amt.total > 0
  AND (ABS(r.amount - amt.total) > 0.005
       OR r.customer_id <> o.customer_id
       OR r.due_date <> DATE_ADD(sh.ship_date, INTERVAL COALESCE(c.credit_days, 30) DAY));

-- -----------------------------------------------------------------------------
-- 4. 账龄演示：老单已收清，最近三笔改成未收 / 部分收款
--    （真实工厂就是这样：早的款回来了，近的还在催，账龄才有分档）
-- -----------------------------------------------------------------------------
UPDATE fn_receivable SET received_amount = 0     WHERE ar_no IN ('AR2026D09','AR2026D11') AND received_amount <> 0;
UPDATE fn_receivable SET received_amount = 14000 WHERE ar_no = 'AR2026D12' AND received_amount <> 14000;
UPDATE fn_receivable SET received_amount = 20000 WHERE ar_no = 'AR2026D07' AND received_amount <> 20000;

-- -----------------------------------------------------------------------------
-- 5. 收款凭证与应收、资金流水三者对齐
-- -----------------------------------------------------------------------------
-- 5.1 核销明细金额 = 应收实收金额
UPDATE fn_payment_line l
JOIN fn_payment p ON p.payment_id = l.payment_id
JOIN fn_receivable r ON r.ar_id = l.target_id
SET l.amount = r.received_amount
WHERE p.payment_no LIKE 'PM-SEED-%' AND l.target_type = 1
  AND ABS(l.amount - r.received_amount) > 0.005;

-- 5.2 应收余额 / 状态重算
UPDATE fn_receivable
SET balance = amount - received_amount,
    status  = CASE WHEN amount - received_amount <= 0.005 THEN 2
                   WHEN received_amount > 0 THEN 1 ELSE 0 END,
    update_time = NOW()
WHERE ABS(balance - (amount - received_amount)) > 0.005
   OR status <> CASE WHEN amount - received_amount <= 0.005 THEN 2
                     WHEN received_amount > 0 THEN 1 ELSE 0 END;

-- 5.3 核销金额归零的收款凭证 → 未收款就不该有收款单
DELETE l FROM fn_payment_line l
JOIN fn_payment p ON p.payment_id = l.payment_id
WHERE p.payment_no LIKE 'PM-SEED-%' AND l.target_type = 1 AND l.amount <= 0;

DELETE t FROM fn_cash_txn t
WHERE t.source_type = 'fn_payment' AND t.source_id IS NOT NULL
  AND NOT EXISTS (SELECT 1 FROM fn_payment_line l WHERE l.payment_id = t.source_id);

DELETE p FROM fn_payment p
WHERE p.payment_no LIKE 'PM-SEED-%'
  AND NOT EXISTS (SELECT 1 FROM fn_payment_line l WHERE l.payment_id = p.payment_id);

-- 5.4 收款单金额 = 其核销明细合计；资金流水金额 = 收款单金额
UPDATE fn_payment p
JOIN (SELECT payment_id, SUM(amount) amt FROM fn_payment_line GROUP BY payment_id) t
  ON t.payment_id = p.payment_id
SET p.amount = t.amt
WHERE p.payment_no LIKE 'PM-SEED-%' AND ABS(p.amount - t.amt) > 0.005;

UPDATE fn_cash_txn t
JOIN fn_payment p ON t.source_type = 'fn_payment' AND t.source_id = p.payment_id
SET t.amount = p.amount
WHERE t.txn_no LIKE 'CT-SEED-%' AND ABS(t.amount - p.amount) > 0.005;

-- -----------------------------------------------------------------------------
-- 6. 订单状态与工单对齐（状态必须能被下游单据支撑，只降不升）
-- -----------------------------------------------------------------------------
-- 6.1 生产 / 完工，却没有任何工单 → 回到「已确认」，等排产
UPDATE sd_order o
LEFT JOIN pr_work_order w ON w.order_id = o.order_id
SET o.status = 1, o.update_time = NOW()
WHERE o.status IN (2,3) AND w.wo_id IS NULL AND o.del_flag = '0';

-- 6.2 工单已在产，订单却还停在「已确认」→ 订单进入生产中
UPDATE sd_order o
JOIN pr_work_order w ON w.order_id = o.order_id
SET o.status = 2, o.update_time = NOW()
WHERE o.status = 1 AND w.status IN (1,2) AND o.del_flag = '0';

-- 6.3 订单已开工，工单却还是草稿 → 工单推进到生产中
UPDATE pr_work_order w
JOIN sd_order o ON o.order_id = w.order_id
SET w.status = 1, w.update_time = NOW()
WHERE w.status = 0 AND o.status IN (2,3);

-- 6.4 订单写「已完工」，工单却没完工 → 订单回落到生产中
UPDATE sd_order o
JOIN pr_work_order w ON w.order_id = o.order_id
SET o.status = 2, o.update_time = NOW()
WHERE o.status = 3 AND w.status <> 2 AND o.del_flag = '0';

-- -----------------------------------------------------------------------------
-- 7. 待发货出货单必须「真能发得出去」
--    原种子的明细是写死的：批号 FG2610-1 库存里根本不存在，SKU 也和订单明细对不上。
--    真点「发货」时会连续撞三个错：出货超过订单未发数量 / 成品库存不足 / 状态不允许。
--    这里按订单自己的明细重建：SKU 取自订单行、批号取自真实成品批次、数量不超过库存。
-- -----------------------------------------------------------------------------
DELETE si FROM sh_shipment_item si
JOIN sh_shipment sh ON sh.shipment_id = si.shipment_id
WHERE sh.status = 0 AND sh.del_flag = '0'
  AND sh.shipment_no BETWEEN 'SH2026101' AND 'SH2026107';

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
WHERE sh.status = 0 AND sh.del_flag = '0'
  AND sh.shipment_no BETWEEN 'SH2026101' AND 'SH2026107';

-- -----------------------------------------------------------------------------
-- 7.1 报价 → 订单：留下一条真实的「已转单」痕迹
--     种子里没有任何订单引用报价，所以 sd_quotation 里永远没有 status=3，
--     报价详情页的「已转单」状态、以及「不能重复转单」的校验都没法演示。
--     把历史订单 SO2026001 关联到同客户同金额的 QT2026001，并把报价置为已转单。
-- -----------------------------------------------------------------------------
UPDATE sd_order o
JOIN sd_quotation q ON q.quote_no = 'QT2026001' AND q.del_flag = '0'
SET o.quote_id = q.quote_id, o.update_time = NOW()
WHERE o.order_no = 'SO2026001' AND o.del_flag = '0'
  AND o.quote_id IS NULL
  AND o.customer_id = q.customer_id
  AND ABS(o.total_amount - q.total_amount) <= 0.005;

UPDATE sd_quotation q
JOIN sd_order o ON o.quote_id = q.quote_id AND o.order_no = 'SO2026001' AND o.del_flag = '0'
SET q.status = 3, q.update_time = NOW()
WHERE q.quote_no = 'QT2026001' AND q.status = 1;

-- -----------------------------------------------------------------------------
-- 8. 订单明细与金额
-- -----------------------------------------------------------------------------
-- 8.1 已确认却没有任何明细的订单（种子里 SO2026120 只有金额）→ 按标准 SKU 组合补明细
INSERT INTO sd_order_item (order_id, style_id, color_id, size_id, qty, unit_price, amount, create_by, create_time)
SELECT o.order_id, s.style_id, s.color_id, s.size_id, 200,
       p.uprice, 200 * p.uprice, 'sales', NOW()
FROM sd_order o
JOIN md_sku s ON s.sku_id IN (1, 3, 6, 10, 13, 16)
JOIN (SELECT 1 AS style_id, 89.00 AS uprice UNION ALL SELECT 2, 129.00 UNION ALL
      SELECT 3, 29.00 UNION ALL SELECT 4, 79.00 UNION ALL SELECT 5, 99.00) p
  ON p.style_id = s.style_id
WHERE o.order_no = 'SO2026120' AND o.del_flag = '0'
  AND NOT EXISTS (SELECT 1 FROM sd_order_item i WHERE i.order_id = o.order_id);

-- 8.2 订单金额 = 明细合计
UPDATE sd_order o
JOIN (SELECT order_id, SUM(amount) amt FROM sd_order_item GROUP BY order_id) t
  ON t.order_id = o.order_id
SET o.total_amount = t.amt, o.update_time = NOW()
WHERE ABS(o.total_amount - t.amt) > 0.005;

-- -----------------------------------------------------------------------------
-- 9. 资金账户余额 = 期初 + 全部流水（上面删/改了流水，必须重算）
-- -----------------------------------------------------------------------------
UPDATE fn_account a
LEFT JOIN (
    SELECT account_id, SUM(CASE WHEN direction = 1 THEN amount ELSE -amount END) AS net
    FROM fn_cash_txn GROUP BY account_id
) t ON t.account_id = a.account_id
SET a.current_balance = a.opening_balance + COALESCE(t.net, 0),
    a.update_time = NOW();

-- -----------------------------------------------------------------------------
-- 10. 自检：以下每一项都必须是 PASS
-- -----------------------------------------------------------------------------
SELECT 'P3-01 已发货出货单必须有出库流水' AS check_item, COUNT(*) AS bad,
       IF(COUNT(*) = 0, 'PASS', 'FAIL') AS result
FROM sh_shipment s
WHERE s.status IN (1,2) AND s.del_flag = '0'
  AND NOT EXISTS (SELECT 1 FROM st_stock_txn t
                  WHERE t.biz_type = 4 AND t.source_type = 'sh_shipment' AND t.source_id = s.shipment_id)
UNION ALL
SELECT 'P3-02 应收必须挂本订单的已发货出货单', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM fn_receivable r
LEFT JOIN sh_shipment s ON s.shipment_id = r.shipment_id
WHERE r.order_id IS NOT NULL
  AND (r.shipment_id IS NULL OR s.shipment_id IS NULL OR s.status NOT IN (1,2) OR s.order_id <> r.order_id)
UNION ALL
SELECT 'P3-03 应收金额 = 出货数量 x 订单单价', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM fn_receivable r
JOIN (
    SELECT si.shipment_id, SUM(si.qty * oi.unit_price) AS total
    FROM sh_shipment_item si
    JOIN sh_shipment s2 ON s2.shipment_id = si.shipment_id
    JOIN md_sku sku ON sku.sku_id = si.sku_id
    JOIN sd_order_item oi ON oi.order_id = s2.order_id
         AND oi.style_id = sku.style_id AND oi.color_id = sku.color_id AND oi.size_id = sku.size_id
    GROUP BY si.shipment_id
) amt ON amt.shipment_id = r.shipment_id
WHERE ABS(r.amount - amt.total) > 0.005
UNION ALL
SELECT 'P3-04 应收到期日 = 发货日 + 客户账期', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM fn_receivable r
JOIN sd_order o ON o.order_id = r.order_id
JOIN sh_shipment sh ON sh.shipment_id = r.shipment_id
JOIN md_customer c ON c.customer_id = o.customer_id
WHERE r.due_date <> DATE_ADD(sh.ship_date, INTERVAL COALESCE(c.credit_days, 30) DAY)
UNION ALL
SELECT 'P3-05 应收余额 = 金额 - 实收', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM fn_receivable WHERE ABS(balance - (amount - received_amount)) > 0.005
UNION ALL
SELECT 'P3-06 有实收就必须有收款凭证', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM fn_receivable r
WHERE r.received_amount > 0
  AND NOT EXISTS (SELECT 1 FROM fn_payment_line l JOIN fn_payment p ON p.payment_id = l.payment_id
                  WHERE l.target_type = 1 AND l.target_id = r.ar_id AND p.status = 1)
UNION ALL
SELECT 'P3-07 收款凭证金额 = 核销合计', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM fn_payment p
JOIN (SELECT payment_id, SUM(amount) amt FROM fn_payment_line GROUP BY payment_id) t
  ON t.payment_id = p.payment_id
WHERE ABS(p.amount - t.amt) > 0.005
UNION ALL
SELECT 'P3-08 生产/完工订单必须有工单', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM sd_order o
LEFT JOIN pr_work_order w ON w.order_id = o.order_id
WHERE o.status IN (2,3) AND w.wo_id IS NULL AND o.del_flag = '0'
UNION ALL
SELECT 'P3-09 订单金额 = 明细合计', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM sd_order o
JOIN (SELECT order_id, SUM(amount) amt FROM sd_order_item GROUP BY order_id) t
  ON t.order_id = o.order_id
WHERE o.del_flag = '0' AND ABS(o.total_amount - t.amt) > 0.005
UNION ALL
SELECT 'P3-10 有金额订单必须有明细', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM sd_order o
WHERE o.del_flag = '0' AND o.total_amount > 0
  AND NOT EXISTS (SELECT 1 FROM sd_order_item i WHERE i.order_id = o.order_id)
UNION ALL
SELECT 'P3-11 账户账面 = 期初 + 流水', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM fn_account a
LEFT JOIN (SELECT account_id, SUM(CASE WHEN direction = 1 THEN amount ELSE -amount END) net
           FROM fn_cash_txn GROUP BY account_id) t ON t.account_id = a.account_id
WHERE ABS(a.current_balance - (a.opening_balance + COALESCE(t.net, 0))) > 0.005
UNION ALL
SELECT 'P3-12 账户余额不得为负', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM fn_account WHERE current_balance < 0
UNION ALL
SELECT 'P3-13 待发货出货单明细属于本订单', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM sh_shipment sh
JOIN sh_shipment_item si ON si.shipment_id = sh.shipment_id
LEFT JOIN sd_order_item oi ON oi.order_id = sh.order_id
     AND oi.style_id = (SELECT sku.style_id FROM md_sku sku WHERE sku.sku_id = si.sku_id)
     AND oi.color_id = (SELECT sku.color_id FROM md_sku sku WHERE sku.sku_id = si.sku_id)
     AND oi.size_id  = (SELECT sku.size_id  FROM md_sku sku WHERE sku.sku_id = si.sku_id)
WHERE sh.status = 0 AND sh.del_flag = '0' AND oi.item_id IS NULL
UNION ALL
SELECT 'P3-14 待发货出货单库存够发', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM sh_shipment sh
JOIN sh_shipment_item si ON si.shipment_id = sh.shipment_id
LEFT JOIN st_inventory i ON i.item_type = 2 AND i.item_id = si.sku_id
     AND i.batch_no = si.batch_no AND i.warehouse_id = 2
WHERE sh.status = 0 AND sh.del_flag = '0'
  AND COALESCE(i.qty, 0) < si.qty
UNION ALL
SELECT 'P3-15 报价已转单状态与订单一致', COUNT(*), IF(COUNT(*) = 0, 'PASS', 'FAIL')
FROM sd_quotation q
LEFT JOIN sd_order o ON o.quote_id = q.quote_id AND o.status <> 9 AND o.del_flag = '0'
WHERE q.del_flag = '0'
  AND ((o.order_id IS NOT NULL AND q.status <> 3) OR (q.status = 3 AND o.order_id IS NULL));
