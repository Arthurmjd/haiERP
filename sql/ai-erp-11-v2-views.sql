-- v2.0 read models. Run after 09 to replace the prototype definitions.
SET NAMES utf8mb4;

CREATE OR REPLACE VIEW v_mrp_demand AS
SELECT m.material_id, m.material_code, m.material_name, m.category,
       CASE m.category WHEN 1 THEN '面料' ELSE '辅料' END AS category_name,
       m.unit, m.ref_price,
       ROUND(COALESCE(d.qty, 0), 3) AS demand_qty,
       ROUND(COALESCE(stock.qty, 0), 3) AS stock_qty,
       ROUND(COALESCE(transit.qty, 0), 3) AS on_way_qty,
       ROUND(COALESCE(stock.qty, 0) + COALESCE(transit.qty, 0), 3) AS available_qty,
       ROUND(COALESCE(d.qty, 0) - COALESCE(stock.qty, 0) - COALESCE(transit.qty, 0), 3) AS net_gap,
       ROUND(GREATEST(COALESCE(d.qty, 0) - COALESCE(stock.qty, 0) - COALESCE(transit.qty, 0), 0), 3) AS gap_qty,
       CEIL(GREATEST(COALESCE(d.qty, 0) - COALESCE(stock.qty, 0) - COALESCE(transit.qty, 0), 0)) AS suggest_qty,
       ROUND(CEIL(GREATEST(COALESCE(d.qty, 0) - COALESCE(stock.qty, 0) - COALESCE(transit.qty, 0), 0))
             * COALESCE(price.unit_price, m.ref_price), 2) AS est_amount,
       IF(COALESCE(d.qty, 0) > COALESCE(stock.qty, 0) + COALESCE(transit.qty, 0), 1, 0) AS shortage_flag
FROM md_material m
LEFT JOIN v_material_last_price price ON price.material_id = m.material_id
LEFT JOIN (
  SELECT material_id, SUM(qty) AS qty FROM (
    SELECT wm.material_id, GREATEST(wm.plan_qty - wm.issued_qty, 0) AS qty
    FROM pr_work_order_material wm
    JOIN pr_work_order w ON w.wo_id = wm.wo_id AND w.del_flag = '0' AND w.status <> 9
    JOIN sd_order o ON o.order_id = w.order_id
    WHERE o.del_flag = '0'
      AND (o.status IN (1,2,3) OR (o.status IN (4,5) AND o.full_ship_date > CURDATE()))
    UNION ALL
    SELECT b.material_id, oi.qty * b.qty * (1 + b.loss_rate / 100)
    FROM sd_order o
    JOIN sd_order_item oi ON oi.order_id = o.order_id
    JOIN md_bom b ON b.style_id = oi.style_id
    WHERE o.del_flag = '0'
      AND (o.status IN (1,2,3) OR (o.status IN (4,5) AND o.full_ship_date > CURDATE()))
      AND NOT EXISTS (SELECT 1 FROM pr_work_order w
                      WHERE w.order_id = o.order_id AND w.style_id = oi.style_id
                        AND w.del_flag = '0' AND w.status <> 9)
  ) raw_demand GROUP BY material_id
) d ON d.material_id = m.material_id
LEFT JOIN (
  SELECT item_id AS material_id, SUM(qty) AS qty
  FROM st_inventory WHERE item_type = 1 GROUP BY item_id
) stock ON stock.material_id = m.material_id
LEFT JOIN (
  SELECT pi.material_id, SUM(GREATEST(pi.qty - COALESCE(received.qty, 0), 0)) AS qty
  FROM pu_purchase_order_item pi
  JOIN pu_purchase_order po ON po.po_id = pi.po_id
  LEFT JOIN (
    SELECT source_line_id, SUM(qty) AS qty
    FROM st_stock_txn
    WHERE biz_type = 1 AND txn_type = 1 AND source_type = 'pu_order'
    GROUP BY source_line_id
  ) received ON received.source_line_id = pi.item_id
  WHERE po.del_flag = '0' AND po.status IN (1,2)
  GROUP BY pi.material_id
) transit ON transit.material_id = m.material_id
WHERE m.status = 1;

CREATE OR REPLACE VIEW v_style_cost AS
SELECT st.style_id, st.style_no, st.style_name, cat.category_name, st.unit,
       COUNT(DISTINCT s.order_id) AS order_cnt, COUNT(s.settle_id) AS settle_cnt,
       COALESCE(SUM(s.qty), 0) AS total_qty,
       ROUND(COALESCE(SUM(s.material_cost), 0), 2) AS material_cost,
       ROUND(COALESCE(SUM(s.labor_cost), 0), 2) AS labor_cost,
       ROUND(COALESCE(SUM(s.overhead_cost), 0), 2) AS overhead_cost,
       ROUND(COALESCE(SUM(s.total_cost), 0), 2) AS total_cost,
       ROUND(SUM(s.total_cost) / NULLIF(SUM(s.qty), 0), 2) AS avg_unit_cost,
       ROUND(COALESCE(SUM(s.revenue), 0), 2) AS total_revenue,
       ROUND(COALESCE(SUM(s.gross_profit), 0), 2) AS total_gross_profit,
       ROUND(100 * SUM(s.gross_profit) / NULLIF(SUM(s.revenue), 0), 2) AS gross_margin,
       ROUND(100 * SUM(s.material_cost) / NULLIF(SUM(s.total_cost), 0), 2) AS material_ratio,
       ROUND(100 * SUM(s.labor_cost) / NULLIF(SUM(s.total_cost), 0), 2) AS labor_ratio,
       ROUND(100 * SUM(s.overhead_cost) / NULLIF(SUM(s.total_cost), 0), 2) AS overhead_ratio
FROM md_style st
LEFT JOIN md_category cat ON cat.category_id = st.category_id
LEFT JOIN fn_cost_settlement s ON s.style_id = st.style_id AND s.del_flag = '0' AND s.status = 1
GROUP BY st.style_id, st.style_no, st.style_name, cat.category_name, st.unit;

-- Each SKU is fulfilled on the first shipment date its running quantity reaches its ordered quantity.
-- CURDATE excludes future fixture shipments; the API must implement the same rule for an explicit asOfDate.
CREATE OR REPLACE VIEW v_order_ontime AS
SELECT o.order_id, o.order_no, o.customer_id, c.customer_name,
       o.order_date, o.delivery_date, o.status AS order_status,
       CASE o.status WHEN 0 THEN '待确认' WHEN 1 THEN '已确认' WHEN 2 THEN '生产中'
         WHEN 3 THEN '已完工' WHEN 4 THEN '已出货' WHEN 5 THEN '已完成' ELSE '已取消' END AS order_status_name,
       ship.first_ship_date, ship.last_ship_date, complete.full_ship_date,
       CASE WHEN complete.full_ship_date IS NOT NULL
            THEN DATEDIFF(complete.full_ship_date, o.delivery_date)
            ELSE DATEDIFF(CURDATE(), o.delivery_date) END AS delay_days,
       CASE WHEN complete.full_ship_date IS NULL THEN -1
            WHEN complete.full_ship_date <= o.delivery_date THEN 1 ELSE 0 END AS ontime_flag,
       CASE WHEN complete.full_ship_date IS NULL AND o.delivery_date < CURDATE() THEN '逾期未发齐'
            WHEN complete.full_ship_date IS NULL THEN '未发齐'
            WHEN complete.full_ship_date <= o.delivery_date THEN '准时发齐'
            ELSE '延期发齐' END AS delivery_state
FROM sd_order o
JOIN md_customer c ON c.customer_id = o.customer_id
LEFT JOIN (
  SELECT order_id, MIN(ship_date) AS first_ship_date, MAX(ship_date) AS last_ship_date
  FROM sh_shipment
  WHERE del_flag = '0' AND status IN (1,2) AND ship_date <= CURDATE()
  GROUP BY order_id
) ship ON ship.order_id = o.order_id
LEFT JOIN (
  SELECT order_lines.order_id,
         IF(COUNT(*) = COUNT(order_lines.sku_full_date), MAX(order_lines.sku_full_date), NULL) AS full_ship_date
  FROM (
    SELECT oi.order_id, oi.item_id,
           MIN(IF(sent.running_qty >= oi.qty, sent.ship_date, NULL)) AS sku_full_date
    FROM sd_order_item oi
    JOIN md_sku sku ON sku.style_id = oi.style_id AND sku.color_id = oi.color_id AND sku.size_id = oi.size_id
    LEFT JOIN (
      SELECT sh.order_id, si.sku_id, sh.ship_date,
             SUM(si.qty) OVER (PARTITION BY sh.order_id, si.sku_id
                               ORDER BY sh.ship_date, sh.shipment_id, si.item_id) AS running_qty
      FROM sh_shipment sh
      JOIN sh_shipment_item si ON si.shipment_id = sh.shipment_id
      WHERE sh.del_flag = '0' AND sh.status IN (1,2) AND sh.ship_date <= CURDATE()
    ) sent ON sent.order_id = oi.order_id AND sent.sku_id = sku.sku_id
    GROUP BY oi.order_id, oi.item_id
  ) order_lines
  GROUP BY order_lines.order_id
) complete ON complete.order_id = o.order_id
WHERE o.del_flag = '0' AND o.status <> 9;
