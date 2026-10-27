-- =============================================================================
-- 2026-01 ~ 2026-09 历史工资单（幂等）
-- 口径与服务端 ErpSalaryService 一致：应发 = 基本 + 计件 + 津贴 + 加班；
-- 个税 = max(0, 应发-社保-公积金-5000) * 3%；实发 = 应发 - 社保 - 公积金 - 个税 - 其他扣款。
-- 计件部分取 hr_piece_wage 实际汇总，保证 self_check 19「计件与工资单一致」。
-- 已存在该月工资单则不重复插入；草稿/已确认单按当前计件重算（已发放不动）。
-- 可重复执行。
-- =============================================================================
SET NAMES utf8mb4;

-- 2026-01 ~ 2026-09 历史工资单：按员工基数 + 固定津贴 + 实际计件汇总，服务端同一套公式
INSERT INTO hr_salary_slip
  (emp_id,wage_period,base_salary,allowance,piece_amount,overtime_pay,
   gross_pay,si_personal,fund_personal,tax,other_deduction,net_pay,
   si_company,fund_company,company_cost,status,create_by,create_time)
SELECT e.emp_id, p.period, e.base_salary, 300.00, COALESCE(w.amt,0), 0,
       e.base_salary + 300 + COALESCE(w.amt,0),
       ROUND(e.si_base*0.105,2), ROUND(e.si_base*0.05,2),
       ROUND(GREATEST(e.base_salary + 300 + COALESCE(w.amt,0)
                      - ROUND(e.si_base*0.105,2) - ROUND(e.si_base*0.05,2) - 5000, 0)*0.03,2),
       0,
       ROUND(e.base_salary + 300 + COALESCE(w.amt,0)
             - ROUND(e.si_base*0.105,2) - ROUND(e.si_base*0.05,2)
             - ROUND(GREATEST(e.base_salary + 300 + COALESCE(w.amt,0)
                      - ROUND(e.si_base*0.105,2) - ROUND(e.si_base*0.05,2) - 5000, 0)*0.03,2),2),
       ROUND(e.si_base*0.27,2), ROUND(e.si_base*0.05,2),
       ROUND(e.base_salary + 300 + COALESCE(w.amt,0)
             + ROUND(e.si_base*0.27,2) + ROUND(e.si_base*0.05,2),2),
       1,'admin',NOW()
FROM hr_employee e
CROSS JOIN (
  SELECT '2026-01' AS period UNION ALL SELECT '2026-02' UNION ALL SELECT '2026-03'
  UNION ALL SELECT '2026-04' UNION ALL SELECT '2026-05' UNION ALL SELECT '2026-06'
  UNION ALL SELECT '2026-07' UNION ALL SELECT '2026-08' UNION ALL SELECT '2026-09'
) p
LEFT JOIN (SELECT emp_id,wage_period,SUM(amount) amt FROM hr_piece_wage GROUP BY emp_id,wage_period) w
       ON w.emp_id = e.emp_id AND w.wage_period = p.period
WHERE e.emp_type = 1 AND e.status = 1
  AND NOT EXISTS (SELECT 1 FROM hr_salary_slip s WHERE s.emp_id = e.emp_id AND s.wage_period = p.period);

-- 已存在但计件已变化的草稿/已确认工资单，按当前计件重算
UPDATE hr_salary_slip s
JOIN (SELECT emp_id,wage_period,SUM(amount) amt FROM hr_piece_wage GROUP BY emp_id,wage_period) w
  ON w.emp_id = s.emp_id AND w.wage_period = s.wage_period
JOIN hr_employee e ON e.emp_id = s.emp_id
SET s.piece_amount  = w.amt,
    s.gross_pay     = s.base_salary + w.amt + s.allowance + s.overtime_pay,
    s.si_personal   = ROUND(e.si_base*0.105,2),
    s.fund_personal = ROUND(e.si_base*0.05,2),
    s.tax           = ROUND(GREATEST(s.base_salary + w.amt + s.allowance + s.overtime_pay
                            - ROUND(e.si_base*0.105,2) - ROUND(e.si_base*0.05,2) - 5000, 0)*0.03,2),
    s.net_pay       = ROUND(s.base_salary + w.amt + s.allowance + s.overtime_pay
                            - ROUND(e.si_base*0.105,2) - ROUND(e.si_base*0.05,2)
                            - ROUND(GREATEST(s.base_salary + w.amt + s.allowance + s.overtime_pay
                              - ROUND(e.si_base*0.105,2) - ROUND(e.si_base*0.05,2) - 5000, 0)*0.03,2)
                            - s.other_deduction, 2),
    s.si_company    = ROUND(e.si_base*0.27,2),
    s.fund_company  = ROUND(e.si_base*0.05,2),
    s.company_cost  = ROUND(s.base_salary + w.amt + s.allowance + s.overtime_pay
                            + ROUND(e.si_base*0.27,2) + ROUND(e.si_base*0.05,2),2)
WHERE s.wage_period BETWEEN '2026-01' AND '2026-09' AND s.status <> 2
  AND ABS(s.piece_amount - w.amt) > 0.005;

SELECT wage_period, COUNT(*) cnt, SUM(net_pay) total_net FROM hr_salary_slip GROUP BY wage_period ORDER BY wage_period;
