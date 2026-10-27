本 ERP 项目的全新本地演示库请按 ../开发启动说明.md 导入：
platform-schema.sql -> init_all.sql -> erp-permissions.sql -> ai-erp-13-seed-rich.sql
-> ai-erp-14-boss-permissions.sql -> p4-rbac-completion.sql -> platform-demo-users.sql
-> system-permissions.sql -> self_check.sql（29 项应全 PASS）。
注意：system-permissions.sql 必须在 platform-demo-users.sql 之后执行，
      它会补齐部门/岗位、把八个账号挂到部门、给 admin 发全部系统权限；
      顺序反了自检第 22、24 项会 FAIL。
      p4-rbac-completion.sql 必须在 self_check.sql 之前执行，
      它按岗位下发按钮权限（财务资金域、销售报价新建与转单、计划员主数据、
      生产一键生成工单、人事批量生成工资单）；漏跑会让财务账号打不开财务页。
已有演示库只需增量执行四个幂等补丁：
p1-finance-align-boss.sql（补厂长权限 + 账户流水账实对齐）
p2-demo-data-consistency.sql（单据合计=明细、工单数量=色码计划、库存期初补流水、状态回填）
p3-chain-integrity.sql（出货-库存-应收-收款-资金全链条对账：幽灵出货单退回待发货、
                         无出货支撑的应收删除、应收金额/账期按代码口径重算、订单状态对齐工单、
                         留一条报价转单痕迹）
p4-rbac-completion.sql（按岗位补全 34 个无人拥有的按钮权限，财务可打开财务页并登记收付款）

可选的历史演示数据（不进 init_all，按需单独执行）：
ai-erp-16-delivered-orders.sql 12 张「已交付」订单全链路（一次性，重复执行会撞单号唯一键）
ai-erp-15-salary-history.sql  2026-01~09 历史工资单（幂等）
注意：16 会生成报工记录（hr_piece_wage），必须在 15 之前执行；
      15 按当期计件重算工资单，跑在 16 之后自检第 19 项才是 PASS。
顺序：ai-erp-16 -> ai-erp-15 -> p3 -> self_check.sql
以下为旧 myFactoryAI 参考系统的说明；不要把 myfactoryai-free.sql 或 clear_all_demo_data.sql
作为本 ERP 项目的初始化或增量迁移脚本。

******************************************************************************************************
*                                                                                                                                            *
*                                                             部署向导，玩耍愉快                                                  *
*                                                                                                                                            *
*                                                               factoryopen.net                                                     *
*                                                                                                                                            *
******************************************************************************************************


myfactoryai-free.sql
************************************
demo数据库的完整sql，包括
1. 所有表、系统数据和业务数据，同时适用于git开放源码版和官网无限制部署包；
2. 较为完整的demo数据，几乎所有界面打开后，均有足够数据支持该界面功能的完整体验；
3. duochuli账号登录，可体验到班组作业的完整界面（其他账号无数据操作，看不到界面效果）。
4. 默认密码factoryopen


clear_all_demo_data.sql
************************************
1. 清除所有demo数据；
2. 适合于正式上线的业务数据初始化；
3. 可按关注主题来清除数据。
