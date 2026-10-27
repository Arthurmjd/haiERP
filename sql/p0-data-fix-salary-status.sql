-- =============================================================================
-- 一次性数据修正：工资单旧状态语义迁移（P0）
--
-- 背景：旧语义 hr_salary_slip.status=1 表示“已发”，新语义
--       0 草稿 / 1 已确认 / 2 已发放。需要把历史 status=1 且未记录 paid_time
--       的单据迁到 status=2，避免被新逻辑当成“已确认未发放”。
--
-- 执行次数：仅执行一次。重复执行会把新语义下“已确认未发放”的单据误标为已发放，
--           执行后请不要再跑。
-- 执行时机：在 ai-erp-12-finance-workflow.sql 之后执行一次。
-- =============================================================================

SET NAMES utf8mb4;

UPDATE hr_salary_slip
SET status = 2,
    paid_time = IFNULL(paid_time, NOW())
WHERE status = 1
  AND paid_time IS NULL;
