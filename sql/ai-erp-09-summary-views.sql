-- =============================================================================
-- AI 驱动精益 ERP 系统 —— 汇总视图（Summary Views）建表脚本
-- 数据库：MySQL 8.x        业务域：运动服装代工厂（OEM）· 工厂内部 ERP
-- -----------------------------------------------------------------------------
-- 本脚本含 3 个视图（P1 修复 B5）：
--   v_mrp_demand     MRP 缺料：物料需求 vs 库存 + 在途 → 缺口与建议采购量
--   v_style_cost     款式成本：按款式汇总材料/人工/制造/收入/毛利与成本结构
--   v_order_ontime   订单准交：每张订单的承诺交期 vs 实际出货，准交/延期/逾期
--   另：v_material_last_price（物料最近采购价）在 04 采购模块中定义，本脚本依赖它。
-- -----------------------------------------------------------------------------
-- 为什么要做成视图（而不是留给后端写 SQL）：
--   1) 这三个问题是「快速反应」的核心 KPI（需求分析 FR-DB-1：准交率/毛利率/库存周转），
--      报表、看板、AI 问答三处都要用，固化一次避免口径漂移；
--   2) 视图即「口径文档」：把公式写进 DDL，评审与接手成本最低；
--   3) 对 NL2SQL 友好 —— 模型只需知道 `v_mrp_demand` 是「哪个料要补多少」，
--      不必现场拼 4 张表的 JOIN 与损耗率公式，显著降低幻觉。
-- -----------------------------------------------------------------------------
-- 通用约定（重要，底座无 MyBatis-Plus 的 @TableLogic，逻辑删除必须手写过滤）：
--   · 所有视图内引用「业务单据表」处一律带 del_flag = '0'；
--   · 一律使用 CREATE OR REPLACE VIEW —— 保证可重复执行，且不会连带打坏依赖它的视图；
--   · 金额/数量统一 ROUND 到 2/3 位，避免 DECIMAL 隐式进位导致对不上账；
--   · 除法一律套 NULLIF(分母,0)，杜绝除零与非法 NULL 传播。
-- -----------------------------------------------------------------------------
-- 执行顺序：必须最后执行（依赖 01~08 全部模块）。
-- 编写日期：2026-10-14
-- =============================================================================

SET NAMES utf8mb4;

-- -----------------------------------------------------------------------------
-- 1. v_mrp_demand —— MRP 缺料
--    需求来源：**已确认及以后**且未交付的订单（status 1已确认/2生产中/3已完工），
--              待确认（status 0）订单不驱动采购；已出货/已完成/已取消（4/5/9）不再产生需求。
--    需求量  = Σ[ 订单明细数量 × BOM单件用量 × (1 + 损耗率/100) ]
--    供给    = 库存（st_inventory 中 item_type=1 的全部批次）
--            + 在途（采购单 status 0待下单/1已下单/2部分到货）
--    缺口    = MAX(需求量 − 供给, 0)
--    建议采购 = 缺口向上取整（面料按整米、辅料按整个下单）
--    预估金额 = **建议采购量** × 参考单价（不是缺口×单价，否则与建议采购量口径打架）
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_mrp_demand AS
SELECT
  g.material_id,
  g.material_code,
  g.material_name,
  g.category,
  g.category_name,
  g.unit,
  g.ref_price,
  g.demand_qty,
  g.stock_qty,
  g.on_way_qty,
  g.available_qty,
  ROUND(g.demand_qty - g.available_qty, 3)                             AS net_gap,
  ROUND(GREATEST(g.demand_qty - g.available_qty, 0), 3)                AS gap_qty,
  CASE WHEN g.demand_qty - g.available_qty > 0
       THEN CEIL(g.demand_qty - g.available_qty) ELSE 0 END            AS suggest_qty,
  ROUND((CASE WHEN g.demand_qty - g.available_qty > 0
              THEN CEIL(g.demand_qty - g.available_qty) ELSE 0 END) * g.ref_price, 2) AS est_amount,
  CASE WHEN g.demand_qty - g.available_qty > 0 THEN 1 ELSE 0 END       AS shortage_flag
FROM (
  SELECT
    m.material_id,
    m.material_code,
    m.material_name,
    m.category,
    CASE m.category WHEN 1 THEN '面料' ELSE '辅料' END                 AS category_name,
    m.unit,
    m.ref_price,
    ROUND(COALESCE(d.demand_qty, 0), 3)                                AS demand_qty,
    ROUND(COALESCE(s.stock_qty , 0), 3)                                AS stock_qty,
    ROUND(COALESCE(w.on_way_qty, 0), 3)                                AS on_way_qty,
    ROUND(COALESCE(s.stock_qty, 0) + COALESCE(w.on_way_qty, 0), 3)     AS available_qty
  FROM md_material m
  LEFT JOIN (
    SELECT b.material_id,
           SUM(oi.qty * b.qty * (1 + b.loss_rate / 100)) AS demand_qty
    FROM sd_order o
    JOIN sd_order_item oi ON oi.order_id = o.order_id
    JOIN md_bom b         ON b.style_id  = oi.style_id
    WHERE o.del_flag = '0'
      AND o.status IN (1, 2, 3)
    GROUP BY b.material_id
  ) d ON d.material_id = m.material_id
  LEFT JOIN (
    SELECT item_id, SUM(qty) AS stock_qty
    FROM st_inventory
    WHERE item_type = 1
    GROUP BY item_id
  ) s ON s.item_id = m.material_id
  LEFT JOIN (
    SELECT i.material_id, SUM(i.qty) AS on_way_qty
    FROM pu_purchase_order_item i
    JOIN pu_purchase_order p ON p.po_id = i.po_id
    WHERE p.del_flag = '0'
      AND p.status IN (0, 1, 2)
    GROUP BY i.material_id
  ) w ON w.material_id = m.material_id
  WHERE m.status = 1
) g;

-- -----------------------------------------------------------------------------
-- 2. v_style_cost —— 款式成本汇总
--    来源：fn_cost_settlement（订单 × 款式级结算单，已确认/试算都计入）
--    口径：avg_unit_cost = Σ总成本 / Σ数量（加权），gross_margin 同样按金额加权，
--          不是各批次毛利率的简单平均 —— 这是评审时最容易被算错的一处。
--    以 md_style 为主表 LEFT JOIN：**没有结算记录的新款式也会出现在结果里**（金额为 0），
--    方便「报价前先看历史成本」的场景；若只看已结算款式，加 WHERE settle_cnt > 0。
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_style_cost AS
SELECT
  st.style_id,
  st.style_no,
  st.style_name,
  cat.category_name,
  st.unit,
  COUNT(DISTINCT s.order_id)                                                  AS order_cnt,
  COUNT(s.settle_id)                                                          AS settle_cnt,
  COALESCE(SUM(s.qty), 0)                                                     AS total_qty,
  ROUND(COALESCE(SUM(s.material_cost), 0), 2)                                 AS material_cost,
  ROUND(COALESCE(SUM(s.labor_cost), 0), 2)                                    AS labor_cost,
  ROUND(COALESCE(SUM(s.overhead_cost), 0), 2)                                 AS overhead_cost,
  ROUND(COALESCE(SUM(s.total_cost), 0), 2)                                    AS total_cost,
  ROUND(COALESCE(SUM(s.total_cost), 0) / NULLIF(SUM(s.qty), 0), 2)            AS avg_unit_cost,
  ROUND(COALESCE(SUM(s.revenue), 0), 2)                                       AS total_revenue,
  ROUND(COALESCE(SUM(s.gross_profit), 0), 2)                                  AS total_gross_profit,
  ROUND(100 * COALESCE(SUM(s.gross_profit), 0) / NULLIF(SUM(s.revenue), 0), 2)      AS gross_margin,
  ROUND(100 * COALESCE(SUM(s.material_cost), 0) / NULLIF(SUM(s.total_cost), 0), 2)  AS material_ratio,
  ROUND(100 * COALESCE(SUM(s.labor_cost), 0) / NULLIF(SUM(s.total_cost), 0), 2)     AS labor_ratio,
  ROUND(100 * COALESCE(SUM(s.overhead_cost), 0) / NULLIF(SUM(s.total_cost), 0), 2)  AS overhead_ratio
FROM md_style st
LEFT JOIN md_category cat ON cat.category_id = st.category_id
LEFT JOIN fn_cost_settlement s
       ON s.style_id = st.style_id
      AND s.del_flag = '0'
GROUP BY st.style_id, st.style_no, st.style_name, cat.category_name, st.unit;

-- -----------------------------------------------------------------------------
-- 3. v_order_ontime —— 订单准交
--    实际出货日 = 该订单**全部有效出货单**（status 1已发货/2已签收）中最晚的 ship_date，
--                 支持分批出货（只看最后一批是否赶上交期）。
--    delay_days（一个字段读两种状态，正数=晚）：
--      · 已出货 → 实际最后出货日 − 承诺交期（正=延期天数，负=提前天数）
--      · 未出货 → 今天 − 承诺交期（正=已逾期天数，负=距交期还有几天）
--    ontime_flag：1 准时交付 / 0 延期交付 / −1 未交付
--    delivery_state：准时交付 / 延期交付 / 逾期未交付 / 待交付
--    准交率（KPI）= SUM(ontime_flag=1) / SUM(ontime_flag<>-1)，见文末示例查询。
-- -----------------------------------------------------------------------------
CREATE OR REPLACE VIEW v_order_ontime AS
SELECT
  o.order_id,
  o.order_no,
  o.customer_id,
  c.customer_name,
  o.order_date,
  o.delivery_date,
  o.status                                                                    AS order_status,
  CASE o.status
    WHEN 0 THEN '待确认' WHEN 1 THEN '已确认' WHEN 2 THEN '生产中'
    WHEN 3 THEN '已完工' WHEN 4 THEN '已出货' WHEN 5 THEN '已完成'
    ELSE '已取消' END                                                          AS order_status_name,
  s.first_ship_date,
  s.last_ship_date,
  CASE WHEN s.last_ship_date IS NOT NULL
       THEN DATEDIFF(s.last_ship_date, o.delivery_date)
       ELSE DATEDIFF(CURDATE(), o.delivery_date) END                          AS delay_days,
  CASE WHEN s.last_ship_date IS NULL THEN -1
       WHEN s.last_ship_date <= o.delivery_date THEN 1
       ELSE 0 END                                                            AS ontime_flag,
  CASE WHEN s.last_ship_date IS NULL AND o.delivery_date < CURDATE() THEN '逾期未交付'
       WHEN s.last_ship_date IS NULL THEN '待交付'
       WHEN s.last_ship_date <= o.delivery_date THEN '准时交付'
       ELSE '延期交付' END                                                     AS delivery_state
FROM sd_order o
JOIN md_customer c ON c.customer_id = o.customer_id
LEFT JOIN (
  SELECT order_id,
         MIN(ship_date) AS first_ship_date,
         MAX(ship_date) AS last_ship_date
  FROM sh_shipment
  WHERE del_flag = '0'
    AND status IN (1, 2)
  GROUP BY order_id
) s ON s.order_id = o.order_id
WHERE o.del_flag = '0';

-- =============================================================================
-- 附：三个视图的示例查询（供报表/看板/AI 取数参考）
-- -----------------------------------------------------------------------------
-- -- (1) 缺料清单（MRP 要点：只看向上取整后的建议采购量）
-- SELECT material_code, material_name, unit, demand_qty, available_qty,
--        gap_qty, suggest_qty, est_amount
-- FROM v_mrp_demand WHERE shortage_flag = 1 ORDER BY est_amount DESC;
--
-- -- (2) 款式毛利排行（成本结构占比一并给出，便于定位「材料贵还是工费贵」）
-- SELECT style_no, style_name, total_qty, avg_unit_cost, total_revenue,
--        gross_margin, material_ratio, labor_ratio
-- FROM v_style_cost ORDER BY gross_margin DESC;
--
-- -- (3) 订单准交率 KPI（整体 + 按客户）
-- SELECT ROUND(100 * SUM(ontime_flag = 1) / NULLIF(SUM(ontime_flag <> -1), 0), 1) AS ontime_rate_pct,
--        SUM(ontime_flag = 1) AS ontime_cnt,
--        SUM(ontime_flag = 0) AS late_cnt,
--        SUM(ontime_flag = -1) AS pending_cnt
-- FROM v_order_ontime;
--
-- SELECT customer_name,
--        SUM(ontime_flag = 1) AS ontime_cnt,
--        SUM(ontime_flag = 0) AS late_cnt,
--        SUM(ontime_flag = -1) AS pending_cnt
-- FROM v_order_ontime GROUP BY customer_name;
--
-- -- (4) 要延期/已逾期的订单（对应需求「哪张订单要延期了？」）
-- SELECT order_no, customer_name, delivery_date, last_ship_date, delay_days, delivery_state
-- FROM v_order_ontime
-- WHERE delivery_state IN ('延期交付', '逾期未交付')
-- ORDER BY delay_days DESC;
-- =============================================================================

-- =============================================================================
-- 结束
-- =============================================================================
