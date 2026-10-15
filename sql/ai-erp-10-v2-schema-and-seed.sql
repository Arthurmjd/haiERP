-- v2.0 fresh-demo initialization only; run after modules 01-08, before 09.
-- Existing business databases require a reviewed, data-preserving migration.
SET NAMES utf8mb4;

ALTER TABLE sd_order ADD COLUMN full_ship_date DATE NULL COMMENT 'All ordered SKU quantities shipped';
ALTER TABLE pr_work_order ADD UNIQUE KEY uk_order_style (order_id, style_id);
ALTER TABLE hr_piece_wage ADD COLUMN report_id BIGINT NULL COMMENT 'NULL for imported legacy wages',
  ADD UNIQUE KEY uk_report_id (report_id);
ALTER TABLE st_stock_txn ADD COLUMN source_line_id BIGINT NULL COMMENT 'Source document line',
  ADD COLUMN request_key VARCHAR(80) NULL COMMENT 'Idempotent action key',
  ADD KEY idx_source_line (source_type, source_id, source_line_id),
  ADD KEY idx_request_key (request_key);
ALTER TABLE sh_return_item ADD COLUMN batch_no VARCHAR(30) NOT NULL DEFAULT '' COMMENT 'Returned batch';
ALTER TABLE sh_shipment_item ADD UNIQUE KEY uk_shipment_sku_batch (shipment_id, sku_id, batch_no);
ALTER TABLE sd_quotation ADD COLUMN request_key VARCHAR(80) NULL COMMENT 'Idempotent source-settlement request',
  ADD UNIQUE KEY uk_quote_request_key (request_key);
ALTER TABLE pu_purchase_order ADD COLUMN suggestion_request_key VARCHAR(80) NULL COMMENT 'Idempotent MRP generation request',
  ADD UNIQUE KEY uk_suggestion_request_key (suggestion_request_key);
ALTER TABLE st_inventory ADD CONSTRAINT chk_inventory_nonnegative CHECK (qty >= 0);
ALTER TABLE st_stock_txn ADD CONSTRAINT chk_txn_positive CHECK (qty > 0);

DROP TABLE IF EXISTS biz_action_request;
DROP TABLE IF EXISTS fn_cost_detail;
DROP TABLE IF EXISTS pr_process_report;
DROP TABLE IF EXISTS pr_work_order_material;
DROP TABLE IF EXISTS pr_work_order_sku;

CREATE TABLE pr_work_order_sku (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  wo_id BIGINT NOT NULL,
  sku_id BIGINT NOT NULL,
  plan_qty INT NOT NULL,
  completed_qty INT NOT NULL DEFAULT 0,
  create_by VARCHAR(64) DEFAULT '',
  create_time DATETIME DEFAULT NULL,
  UNIQUE KEY uk_wo_sku (wo_id, sku_id),
  KEY idx_sku (sku_id),
  CONSTRAINT chk_wo_sku_qty CHECK (plan_qty > 0 AND completed_qty >= 0 AND completed_qty <= plan_qty)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Frozen work-order SKU plan';

CREATE TABLE pr_work_order_material (
  id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  wo_id BIGINT NOT NULL,
  material_id BIGINT NOT NULL,
  unit VARCHAR(10) NOT NULL,
  unit_qty DECIMAL(10,3) NOT NULL,
  loss_rate DECIMAL(5,2) NOT NULL DEFAULT 0,
  plan_qty DECIMAL(14,3) NOT NULL,
  issued_qty DECIMAL(14,3) NOT NULL DEFAULT 0,
  create_by VARCHAR(64) DEFAULT '',
  create_time DATETIME DEFAULT NULL,
  UNIQUE KEY uk_wo_material (wo_id, material_id),
  KEY idx_material (material_id),
  CONSTRAINT chk_wo_material_qty CHECK (unit_qty > 0 AND plan_qty >= 0 AND issued_qty >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Frozen work-order BOM and issue totals';

CREATE TABLE pr_process_report (
  report_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  request_key VARCHAR(80) NOT NULL,
  wo_process_id BIGINT NOT NULL,
  wo_id BIGINT NOT NULL,
  sku_id BIGINT NOT NULL,
  emp_id BIGINT NULL COMMENT 'NULL only for imported aggregates or outsourced work',
  is_outsourced TINYINT NOT NULL DEFAULT 0 COMMENT '1 outsourced, no employee wage',
  good_qty INT NOT NULL DEFAULT 0,
  bad_qty INT NOT NULL DEFAULT 0,
  report_time DATETIME NOT NULL,
  data_origin TINYINT NOT NULL DEFAULT 0 COMMENT '0 normal, 1 historical aggregate',
  create_by VARCHAR(64) DEFAULT '',
  create_time DATETIME DEFAULT NULL,
  UNIQUE KEY uk_request_key (request_key),
  KEY idx_process_sku (wo_process_id, sku_id),
  KEY idx_wo_sku (wo_id, sku_id),
  CONSTRAINT chk_report_qty CHECK (good_qty >= 0 AND bad_qty >= 0 AND good_qty + bad_qty > 0),
  CONSTRAINT chk_report_employee CHECK (data_origin = 1 OR is_outsourced = 1 OR emp_id IS NOT NULL),
  CONSTRAINT chk_report_origin CHECK (data_origin IN (0,1) AND is_outsourced IN (0,1))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Append-only SKU process reports';

CREATE TABLE fn_cost_detail (
  detail_id BIGINT NOT NULL AUTO_INCREMENT PRIMARY KEY,
  settle_id BIGINT NOT NULL,
  line_type TINYINT NOT NULL COMMENT '1 material, 2 process, 3 overhead',
  source_id BIGINT NOT NULL COMMENT 'Overhead uses 0',
  qty DECIMAL(14,3) NOT NULL,
  unit_price DECIMAL(12,4) NOT NULL,
  amount DECIMAL(12,2) NOT NULL,
  price_source VARCHAR(40) NOT NULL,
  create_by VARCHAR(64) DEFAULT '',
  create_time DATETIME DEFAULT NULL,
  UNIQUE KEY uk_settle_line (settle_id, line_type, source_id),
  CONSTRAINT chk_cost_detail CHECK (qty >= 0 AND unit_price >= 0 AND amount >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Frozen standard-cost lines';

CREATE TABLE biz_action_request (
  request_key VARCHAR(80) NOT NULL PRIMARY KEY,
  action_type VARCHAR(40) NOT NULL,
  target_type VARCHAR(40) NOT NULL,
  target_id BIGINT NULL,
  payload_hash CHAR(64) NOT NULL,
  status TINYINT NOT NULL COMMENT '0 processing, 1 succeeded, 2 failed',
  result_json JSON NULL,
  create_by VARCHAR(64) DEFAULT '',
  create_time DATETIME NOT NULL DEFAULT CURRENT_TIMESTAMP,
  update_time DATETIME DEFAULT NULL,
  KEY idx_target (target_type, target_id),
  CONSTRAINT chk_action_status CHECK (status IN (0,1,2))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='Cross-module action idempotency';

-- Partial receipt: 40 of 100 units arrived; 60 remain on the way.
INSERT INTO pu_purchase_order
  (po_id, po_no, supplier_id, order_date, expect_date, status, total_amount, buyer_user_id, create_by, create_time)
VALUES (6, 'PO2026006', 1, '2026-10-12', '2026-10-25', 2, 2200.00, 9, 'seed', NOW());
INSERT INTO pu_purchase_order_item
  (item_id, po_id, material_id, qty, unit_price, amount, create_by, create_time)
VALUES (11, 6, 6, 100.000, 22.00, 2200.00, 'seed', NOW());
INSERT INTO st_stock_txn
  (txn_no, txn_type, biz_type, item_type, item_id, batch_no, qty,
   source_type, source_id, txn_time, create_by, create_time)
VALUES ('TXN2026101305', 1, 1, 1, 6, 'F20261013', 40.000,
        'pu_order', 6, '2026-10-13 10:00:00', 'seed', NOW());
INSERT INTO st_inventory
  (item_type, item_id, batch_no, warehouse_id, qty, create_by, create_time, update_time)
VALUES (1, 6, 'F20261013', 1, 40.000, 'seed', NOW(), NOW());

-- Partial shipment, then final shipment on the next day. Net stock is unchanged.
INSERT INTO sh_shipment
  (shipment_id, shipment_no, order_id, customer_id, ship_date, status, create_by, create_time)
VALUES (3, 'SH2026003', 1, 1, '2026-10-20', 1, 'seed', NOW());
INSERT INTO sh_shipment_item
  (item_id, shipment_id, sku_id, batch_no, qty, create_by, create_time)
VALUES (8, 3, 1, 'C20261020', 30, 'seed', NOW());
UPDATE sh_shipment_item SET qty = 70 WHERE item_id = 1;
UPDATE st_stock_txn SET qty = 70 WHERE txn_no = 'TXN2026102101';
INSERT INTO st_stock_txn
  (txn_no, txn_type, biz_type, item_type, item_id, batch_no, qty,
   source_type, source_id, txn_time, create_by, create_time)
VALUES ('TXN2026102005', 2, 4, 2, 1, 'C20261020', 30,
        'sd_order', 1, '2026-10-20 17:30:00', 'seed', NOW());

-- Freeze the current order matrix and BOM. New writes do this in one transaction.
INSERT INTO pr_work_order_sku (wo_id, sku_id, plan_qty, completed_qty, create_by, create_time)
SELECT w.wo_id, sku.sku_id, oi.qty, COALESCE(done.qty, 0), 'seed', NOW()
FROM pr_work_order w
JOIN sd_order_item oi ON oi.order_id = w.order_id AND oi.style_id = w.style_id
JOIN md_sku sku ON sku.style_id = oi.style_id AND sku.color_id = oi.color_id AND sku.size_id = oi.size_id
LEFT JOIN (SELECT source_id AS wo_id, item_id AS sku_id, SUM(qty) AS qty
           FROM st_stock_txn WHERE biz_type = 3 GROUP BY source_id, item_id) done
  ON done.wo_id = w.wo_id AND done.sku_id = sku.sku_id;

INSERT INTO pr_work_order_material
  (wo_id, material_id, unit, unit_qty, loss_rate, plan_qty, issued_qty, create_by, create_time)
SELECT w.wo_id, b.material_id, b.unit, b.qty, b.loss_rate,
       ROUND(w.qty * b.qty * (1 + b.loss_rate / 100), 3), COALESCE(issued.qty, 0), 'seed', NOW()
FROM pr_work_order w
JOIN md_bom b ON b.style_id = w.style_id
LEFT JOIN (SELECT source_id AS wo_id, item_id AS material_id, SUM(qty) AS qty
           FROM st_stock_txn WHERE biz_type = 2 GROUP BY source_id, item_id) issued
  ON issued.wo_id = w.wo_id AND issued.material_id = b.material_id;

-- Historical progress had totals only. Backfill is marked and never mints wages.
INSERT INTO pr_process_report
  (request_key, wo_process_id, wo_id, sku_id, emp_id, is_outsourced, good_qty, bad_qty,
   report_time, data_origin, create_by, create_time)
SELECT CONCAT('seed-report-', x.process_row_id, '-', x.sku_id), x.process_row_id,
       x.wo_id, x.sku_id, NULL, x.is_outsourced,
       x.base_good + IF(x.rn = 1, x.total_good - x.allocated_good, 0),
       x.base_bad + IF(x.rn = 1, x.total_bad - x.allocated_bad, 0),
       COALESCE(x.actual_end_date, x.actual_start_date, '2026-10-01'), 1, 'seed', NOW()
FROM (
  SELECT wp.id AS process_row_id, wp.wo_id, ws.sku_id, wp.is_outsourced, wp.done_qty AS total_good,
         wp.bad_qty AS total_bad, w.actual_end_date, w.actual_start_date,
         FLOOR(wp.done_qty * ws.plan_qty / w.qty) AS base_good,
         FLOOR(wp.bad_qty * ws.plan_qty / w.qty) AS base_bad,
         ROW_NUMBER() OVER (PARTITION BY wp.id ORDER BY ws.sku_id) AS rn,
         SUM(FLOOR(wp.done_qty * ws.plan_qty / w.qty)) OVER (PARTITION BY wp.id) AS allocated_good,
         SUM(FLOOR(wp.bad_qty * ws.plan_qty / w.qty)) OVER (PARTITION BY wp.id) AS allocated_bad
  FROM pr_work_order_process wp
  JOIN pr_work_order w ON w.wo_id = wp.wo_id
  JOIN pr_work_order_sku ws ON ws.wo_id = w.wo_id
  WHERE wp.done_qty + wp.bad_qty > 0
) x
WHERE x.base_good + x.base_bad > 0 OR x.rn = 1;

UPDATE st_stock_txn t JOIN pu_purchase_order_item i
  ON t.biz_type = 1 AND t.source_type = 'pu_order' AND i.po_id = t.source_id AND i.material_id = t.item_id
SET t.source_line_id = i.item_id;
UPDATE st_stock_txn t JOIN pr_work_order_material m
  ON t.biz_type = 2 AND t.source_type = 'pr_wo' AND m.wo_id = t.source_id AND m.material_id = t.item_id
SET t.source_line_id = m.id;
UPDATE st_stock_txn t JOIN pr_work_order_sku s
  ON t.biz_type = 3 AND t.source_type = 'pr_wo' AND s.wo_id = t.source_id AND s.sku_id = t.item_id
SET t.source_line_id = s.id;
UPDATE st_stock_txn t JOIN sh_shipment sh ON t.biz_type = 4 AND t.source_type = 'sd_order'
  AND sh.order_id = t.source_id AND sh.ship_date = DATE(t.txn_time)
JOIN sh_shipment_item i ON i.shipment_id = sh.shipment_id AND i.sku_id = t.item_id AND i.batch_no = t.batch_no
SET t.source_type = 'sh_shipment', t.source_id = sh.shipment_id, t.source_line_id = i.item_id;
UPDATE sh_return_item i JOIN sh_return r ON r.return_id = i.return_id
JOIN sh_shipment_item si ON si.shipment_id = r.shipment_id AND si.sku_id = i.sku_id
SET i.batch_no = si.batch_no;
UPDATE st_stock_txn t JOIN sh_return_item i
  ON t.biz_type = 7 AND t.source_type = 'sh_return' AND i.return_id = t.source_id
  AND i.sku_id = t.item_id AND i.batch_no = t.batch_no
SET t.source_line_id = i.item_id;

-- These dated seed documents extend beyond 2026-10-14. As-of reports filter events.
UPDATE sd_order o JOIN (
  SELECT sh.order_id, MAX(sh.ship_date) AS full_date
  FROM sh_shipment sh WHERE sh.del_flag = '0' AND sh.status IN (1,2)
  GROUP BY sh.order_id
) d ON d.order_id = o.order_id
SET o.full_ship_date = d.full_date WHERE o.status IN (4,5);

INSERT INTO fn_cost_detail
  (settle_id, line_type, source_id, qty, unit_price, amount, price_source, create_by, create_time)
SELECT s.settle_id, 1, m.material_id, m.plan_qty, p.unit_price,
       ROUND(m.plan_qty * p.unit_price, 2),
       IF(p.price_src = 1, 'purchase_snapshot', 'reference_snapshot'), 'seed', NOW()
FROM fn_cost_settlement s
JOIN pr_work_order w ON w.order_id = s.order_id AND w.style_id = s.style_id
JOIN pr_work_order_material m ON m.wo_id = w.wo_id
JOIN v_material_last_price p ON p.material_id = m.material_id
UNION ALL
SELECT s.settle_id, 1, b.material_id,
       ROUND(s.qty * b.qty * (1 + b.loss_rate / 100), 3), p.unit_price,
       ROUND(s.qty * b.qty * (1 + b.loss_rate / 100) * p.unit_price, 2),
       IF(p.price_src = 1, 'purchase_snapshot', 'reference_snapshot'), 'seed', NOW()
FROM fn_cost_settlement s
JOIN md_bom b ON b.style_id = s.style_id
JOIN v_material_last_price p ON p.material_id = b.material_id
WHERE NOT EXISTS (SELECT 1 FROM pr_work_order w WHERE w.order_id = s.order_id AND w.style_id = s.style_id);

INSERT INTO fn_cost_detail
  (settle_id, line_type, source_id, qty, unit_price, amount, price_source, create_by, create_time)
SELECT s.settle_id, 2, wp.id, wp.plan_qty,
       IF(wp.is_outsourced = 1, wp.outsource_price, wp.unit_price),
       ROUND(wp.plan_qty * IF(wp.is_outsourced = 1, wp.outsource_price, wp.unit_price), 2),
       'work_order_snapshot', 'seed', NOW()
FROM fn_cost_settlement s
JOIN pr_work_order w ON w.order_id = s.order_id AND w.style_id = s.style_id
JOIN pr_work_order_process wp ON wp.wo_id = w.wo_id
UNION ALL
SELECT s.settle_id, 2, sp.id, s.qty, sp.unit_price,
       ROUND(s.qty * sp.unit_price, 2), 'route_estimate', 'seed', NOW()
FROM fn_cost_settlement s
JOIN md_style_process sp ON sp.style_id = s.style_id
WHERE NOT EXISTS (SELECT 1 FROM pr_work_order w WHERE w.order_id = s.order_id AND w.style_id = s.style_id);

INSERT INTO fn_cost_detail
  (settle_id, line_type, source_id, qty, unit_price, amount, price_source, create_by, create_time)
SELECT settle_id, 3, 0, 1, overhead_cost, overhead_cost, 'frozen_overhead_rate', 'seed', NOW()
FROM fn_cost_settlement;

-- Prototype payroll treated outsourced sewing as employee piecework. Remove those
-- two legacy rows and recalculate the affected October slips from remaining work.
DELETE pw FROM hr_piece_wage pw
JOIN pr_work_order_process wp
  ON wp.wo_id = pw.work_order_id AND wp.process_id = pw.process_id
WHERE wp.is_outsourced = 1;
UPDATE hr_salary_slip sl
LEFT JOIN (SELECT emp_id, wage_period, SUM(amount) AS piece_amount
           FROM hr_piece_wage GROUP BY emp_id, wage_period) pw
  ON pw.emp_id = sl.emp_id AND pw.wage_period = sl.wage_period
SET sl.piece_amount = COALESCE(pw.piece_amount, 0);
UPDATE hr_salary_slip
SET gross_pay = base_salary + allowance + piece_amount + overtime_pay;
UPDATE hr_salary_slip
SET net_pay = gross_pay - si_personal - fund_personal - tax - other_deduction,
    company_cost = gross_pay + si_company + fund_company;
