-- AI-ERP v2.0: disposable demo schema initialization, MySQL 8.x.
-- This script DROPS business tables. Never run against an existing business database.
-- Create a separate schema first, then run from the repository root:
--   mysql -u USER -p DEMO_SCHEMA < sql/init_all.sql
-- Afterward: mysql -u USER -p DEMO_SCHEMA < sql/self_check.sql
-- myfactoryai-free.sql and clear_all_demo_data.sql belong to a reference system.

SOURCE sql/ai-erp-02-master-data-module.sql;
SOURCE sql/ai-erp-01-hr-module.sql;
SOURCE sql/ai-erp-03-sales-module.sql;
SOURCE sql/ai-erp-04-purchase-module.sql;
SOURCE sql/ai-erp-05-production-module.sql;
SOURCE sql/ai-erp-06-inventory-module.sql;
SOURCE sql/ai-erp-07-shipment-module.sql;
SOURCE sql/ai-erp-08-cost-module.sql;
SOURCE sql/ai-erp-10-v2-schema-and-seed.sql;
SOURCE sql/ai-erp-09-summary-views.sql;
SOURCE sql/ai-erp-11-v2-views.sql;
