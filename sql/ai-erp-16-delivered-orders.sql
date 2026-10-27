-- =============================================================================
-- 「已交付」演示订单：订单→工单→领料→报工/计件→完工入库→出货→应收 全链路
-- 12 张订单，分布在 2026-01 ~ 2026-09，全部 status=5 已完成、出货已签收、应收已结清。
-- 数据互相咬合，满足 self_check 03/04/05/06/07/08/09/13/19。
--
-- 注意：本文件引用了当前演示库的 SKU/物料/工序 ID，属于一次性灌数脚本，
--       重复执行会因单号（SO2026D** / WO2026D** / SH2026D** / AR2026D**）唯一键冲突而失败。
--       重建库后如需再灌，请确认主数据 ID 与本文件一致。
-- =============================================================================
SET NAMES utf8mb4;
SET @dummy := 0;
-- ===== SO2026D01 2026-01 style=1 qty/sku=100 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D01',1,'2026-01-05','2026-01-20',5,17800.0,'sales',NOW(),'2026-01-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,1,(SELECT color_id FROM md_sku WHERE sku_id=1),(SELECT size_id FROM md_sku WHERE sku_id=1),100,89.00,8900.00,'sales',NOW()),
(@o,1,(SELECT color_id FROM md_sku WHERE sku_id=2),(SELECT size_id FROM md_sku WHERE sku_id=2),100,89.00,8900.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D01',@o,1,200,'2026-01-06','2026-01-18','2026-01-06','2026-01-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,1,100,100,'admin',NOW()),
(@w,2,100,100,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,1,'米',0.800,5.00,168.000,168.000,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,1,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0001',1,5,1,1,'SUP',168.000,1,'stock_check',NULL,NULL,'2026-01-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+168.000, update_time=NOW() WHERE item_type=1 AND item_id=1 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0002',2,2,1,1,'SUP',168.000,1,'pr_wo',@w,@m1,'2026-01-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-168.000, update_time=NOW() WHERE item_type=1 AND item_id=1 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,4,'个',1.000,0.00,200.000,200.000,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,4,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0003',1,5,1,4,'SUP',200.000,1,'stock_check',NULL,NULL,'2026-01-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+200.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0004',2,2,1,4,'SUP',200.000,1,'pr_wo',@w,@m2,'2026-01-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-200.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.020,2.00,4.080,4.080,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0005',1,5,1,5,'SUP',4.080,1,'stock_check',NULL,NULL,'2026-01-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+4.080, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0006',2,2,1,5,'SUP',4.080,1,'pr_wo',@w,@m3,'2026-01-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-4.080, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,200,200,0,0,1.20,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D01-1-1',@wp1,@w,1,1,0,100,0,'2026-01-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(1,1,1,@w,100,1.20,120.00,'2026-01',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D01-1-2',@wp1,@w,2,2,0,100,0,'2026-01-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(2,1,1,@w,100,1.20,120.00,'2026-01',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,200,200,0,0,3.50,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D01-2-1',@wp2,@w,1,3,0,100,0,'2026-01-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(3,2,1,@w,100,3.50,350.00,'2026-01',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D01-2-2',@wp2,@w,2,4,0,100,0,'2026-01-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(4,2,1,@w,100,3.50,350.00,'2026-01',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,200,200,0,0,0.60,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D01-3-1',@wp3,@w,1,9,0,100,0,'2026-01-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(9,3,1,@w,100,0.60,60.00,'2026-01',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D01-3-2',@wp3,@w,2,10,0,100,0,'2026-01-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(10,3,1,@w,100,0.60,60.00,'2026-01',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,1,'FG-D01-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0007',1,3,2,1,'FG-D01-1',100,1,'pr_wo',@w,@ws1,'2026-01-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+100, update_time=NOW() WHERE item_type=2 AND item_id=1 AND batch_no='FG-D01-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,2,'FG-D01-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0008',1,3,2,2,'FG-D01-2',100,1,'pr_wo',@w,@ws2,'2026-01-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+100, update_time=NOW() WHERE item_type=2 AND item_id=2 AND batch_no='FG-D01-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D01',@o,1,'2026-01-22','顺丰速运','SF00000001',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,1,'FG-D01-1',100,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0009',2,4,2,1,'FG-D01-1',100,1,'sh_shipment',@sh,@si,'2026-01-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-100, update_time=NOW() WHERE item_type=2 AND item_id=1 AND batch_no='FG-D01-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,2,'FG-D01-2',100,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0010',2,4,2,2,'FG-D01-2',100,1,'sh_shipment',@sh,@si,'2026-01-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-100, update_time=NOW() WHERE item_type=2 AND item_id=2 AND batch_no='FG-D01-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D01',1,@o,@sh,17800.0,17800.0,0,'2026-01-20',2,'seed-ar-D01','finance',NOW());
-- ===== SO2026D02 2026-02 style=2 qty/sku=120 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D02',2,'2026-02-05','2026-02-20',5,30960.0,'sales',NOW(),'2026-02-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,2,(SELECT color_id FROM md_sku WHERE sku_id=6),(SELECT size_id FROM md_sku WHERE sku_id=6),120,129.00,15480.00,'sales',NOW()),
(@o,2,(SELECT color_id FROM md_sku WHERE sku_id=7),(SELECT size_id FROM md_sku WHERE sku_id=7),120,129.00,15480.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D02',@o,2,240,'2026-02-06','2026-02-18','2026-02-06','2026-02-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,6,120,120,'admin',NOW()),
(@w,7,120,120,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,2,'米',1.200,6.00,305.280,305.280,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,2,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0011',1,5,1,2,'SUP',305.280,1,'stock_check',NULL,NULL,'2026-02-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+305.280, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0012',2,2,1,2,'SUP',305.280,1,'pr_wo',@w,@m1,'2026-02-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-305.280, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,3,'条',1.000,1.00,242.400,242.400,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,3,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0013',1,5,1,3,'SUP',242.400,1,'stock_check',NULL,NULL,'2026-02-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+242.400, update_time=NOW() WHERE item_type=1 AND item_id=3 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0014',2,2,1,3,'SUP',242.400,1,'pr_wo',@w,@m2,'2026-02-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-242.400, update_time=NOW() WHERE item_type=1 AND item_id=3 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.030,2.00,7.344,7.344,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0015',1,5,1,5,'SUP',7.344,1,'stock_check',NULL,NULL,'2026-02-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+7.344, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0016',2,2,1,5,'SUP',7.344,1,'pr_wo',@w,@m3,'2026-02-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-7.344, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,240,240,0,0,1.50,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D02-1-1',@wp1,@w,6,11,0,120,0,'2026-02-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(11,1,2,@w,120,1.50,180.00,'2026-02',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D02-1-2',@wp1,@w,7,12,0,120,0,'2026-02-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(12,1,2,@w,120,1.50,180.00,'2026-02',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,240,240,0,0,4.80,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D02-2-1',@wp2,@w,6,17,0,120,0,'2026-02-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(17,2,2,@w,120,4.80,576.00,'2026-02',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D02-2-2',@wp2,@w,7,18,0,120,0,'2026-02-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(18,2,2,@w,120,4.80,576.00,'2026-02',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,240,240,0,0,0.80,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D02-3-1',@wp3,@w,6,19,0,120,0,'2026-02-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(19,3,2,@w,120,0.80,96.00,'2026-02',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D02-3-2',@wp3,@w,7,20,0,120,0,'2026-02-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(20,3,2,@w,120,0.80,96.00,'2026-02',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,6,'FG-D02-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0017',1,3,2,6,'FG-D02-1',120,1,'pr_wo',@w,@ws1,'2026-02-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+120, update_time=NOW() WHERE item_type=2 AND item_id=6 AND batch_no='FG-D02-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,7,'FG-D02-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0018',1,3,2,7,'FG-D02-2',120,1,'pr_wo',@w,@ws2,'2026-02-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+120, update_time=NOW() WHERE item_type=2 AND item_id=7 AND batch_no='FG-D02-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D02',@o,2,'2026-02-22','顺丰速运','SF00000002',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,6,'FG-D02-1',120,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0019',2,4,2,6,'FG-D02-1',120,1,'sh_shipment',@sh,@si,'2026-02-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-120, update_time=NOW() WHERE item_type=2 AND item_id=6 AND batch_no='FG-D02-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,7,'FG-D02-2',120,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0020',2,4,2,7,'FG-D02-2',120,1,'sh_shipment',@sh,@si,'2026-02-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-120, update_time=NOW() WHERE item_type=2 AND item_id=7 AND batch_no='FG-D02-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D02',2,@o,@sh,30960.0,30960.0,0,'2026-02-20',2,'seed-ar-D02','finance',NOW());
-- ===== SO2026D03 2026-03 style=3 qty/sku=150 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D03',3,'2026-03-05','2026-03-20',5,8700.0,'sales',NOW(),'2026-03-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,3,(SELECT color_id FROM md_sku WHERE sku_id=10),(SELECT size_id FROM md_sku WHERE sku_id=10),150,29.00,4350.00,'sales',NOW()),
(@o,3,(SELECT color_id FROM md_sku WHERE sku_id=11),(SELECT size_id FROM md_sku WHERE sku_id=11),150,29.00,4350.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D03',@o,3,300,'2026-03-06','2026-03-18','2026-03-06','2026-03-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,10,150,150,'admin',NOW()),
(@w,11,150,150,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,2,'米',0.080,4.00,24.960,24.960,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,2,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0021',1,5,1,2,'SUP',24.960,1,'stock_check',NULL,NULL,'2026-03-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+24.960, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0022',2,2,1,2,'SUP',24.960,1,'pr_wo',@w,@m1,'2026-03-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-24.960, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.010,2.00,3.060,3.060,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0023',1,5,1,5,'SUP',3.060,1,'stock_check',NULL,NULL,'2026-03-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+3.060, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0024',2,2,1,5,'SUP',3.060,1,'pr_wo',@w,@m2,'2026-03-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-3.060, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,300,300,0,0,0.10,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D03-1-1',@wp1,@w,10,21,0,150,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(21,1,3,@w,150,0.10,15.00,'2026-03',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D03-1-2',@wp1,@w,11,22,0,150,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(22,1,3,@w,150,0.10,15.00,'2026-03',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,300,300,0,0,0.35,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D03-2-1',@wp2,@w,10,23,0,150,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(23,2,3,@w,150,0.35,52.50,'2026-03',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D03-2-2',@wp2,@w,11,24,0,150,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(24,2,3,@w,150,0.35,52.50,'2026-03',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,10,'FG-D03-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0025',1,3,2,10,'FG-D03-1',150,1,'pr_wo',@w,@ws1,'2026-03-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+150, update_time=NOW() WHERE item_type=2 AND item_id=10 AND batch_no='FG-D03-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,11,'FG-D03-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0026',1,3,2,11,'FG-D03-2',150,1,'pr_wo',@w,@ws2,'2026-03-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+150, update_time=NOW() WHERE item_type=2 AND item_id=11 AND batch_no='FG-D03-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D03',@o,3,'2026-03-22','顺丰速运','SF00000003',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,10,'FG-D03-1',150,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0027',2,4,2,10,'FG-D03-1',150,1,'sh_shipment',@sh,@si,'2026-03-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-150, update_time=NOW() WHERE item_type=2 AND item_id=10 AND batch_no='FG-D03-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,11,'FG-D03-2',150,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0028',2,4,2,11,'FG-D03-2',150,1,'sh_shipment',@sh,@si,'2026-03-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-150, update_time=NOW() WHERE item_type=2 AND item_id=11 AND batch_no='FG-D03-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D03',3,@o,@sh,8700.0,8700.0,0,'2026-03-20',2,'seed-ar-D03','finance',NOW());
-- ===== SO2026D04 2026-04 style=4 qty/sku=100 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D04',4,'2026-04-05','2026-04-20',5,15800.0,'sales',NOW(),'2026-04-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,4,(SELECT color_id FROM md_sku WHERE sku_id=13),(SELECT size_id FROM md_sku WHERE sku_id=13),100,79.00,7900.00,'sales',NOW()),
(@o,4,(SELECT color_id FROM md_sku WHERE sku_id=14),(SELECT size_id FROM md_sku WHERE sku_id=14),100,79.00,7900.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D04',@o,4,200,'2026-04-06','2026-04-18','2026-04-06','2026-04-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,13,100,100,'admin',NOW()),
(@w,14,100,100,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,6,'米',0.600,5.00,126.000,126.000,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,6,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0029',1,5,1,6,'SUP',126.000,1,'stock_check',NULL,NULL,'2026-04-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+126.000, update_time=NOW() WHERE item_type=1 AND item_id=6 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0030',2,2,1,6,'SUP',126.000,1,'pr_wo',@w,@m1,'2026-04-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-126.000, update_time=NOW() WHERE item_type=1 AND item_id=6 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,4,'个',1.000,0.00,200.000,200.000,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,4,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0031',1,5,1,4,'SUP',200.000,1,'stock_check',NULL,NULL,'2026-04-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+200.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0032',2,2,1,4,'SUP',200.000,1,'pr_wo',@w,@m2,'2026-04-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-200.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.015,2.00,3.060,3.060,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0033',1,5,1,5,'SUP',3.060,1,'stock_check',NULL,NULL,'2026-04-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+3.060, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0034',2,2,1,5,'SUP',3.060,1,'pr_wo',@w,@m3,'2026-04-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-3.060, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,200,200,0,0,1.00,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D04-1-1',@wp1,@w,13,25,0,100,0,'2026-04-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(25,1,4,@w,100,1.00,100.00,'2026-04',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D04-1-2',@wp1,@w,14,26,0,100,0,'2026-04-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(26,1,4,@w,100,1.00,100.00,'2026-04',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,200,200,0,0,2.80,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D04-2-1',@wp2,@w,13,27,0,100,0,'2026-04-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(27,2,4,@w,100,2.80,280.00,'2026-04',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D04-2-2',@wp2,@w,14,28,0,100,0,'2026-04-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(28,2,4,@w,100,2.80,280.00,'2026-04',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,200,200,0,0,0.50,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D04-3-1',@wp3,@w,13,1,0,100,0,'2026-04-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(1,3,4,@w,100,0.50,50.00,'2026-04',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D04-3-2',@wp3,@w,14,2,0,100,0,'2026-04-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(2,3,4,@w,100,0.50,50.00,'2026-04',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,13,'FG-D04-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0035',1,3,2,13,'FG-D04-1',100,1,'pr_wo',@w,@ws1,'2026-04-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+100, update_time=NOW() WHERE item_type=2 AND item_id=13 AND batch_no='FG-D04-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,14,'FG-D04-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0036',1,3,2,14,'FG-D04-2',100,1,'pr_wo',@w,@ws2,'2026-04-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+100, update_time=NOW() WHERE item_type=2 AND item_id=14 AND batch_no='FG-D04-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D04',@o,4,'2026-04-22','顺丰速运','SF00000004',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,13,'FG-D04-1',100,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0037',2,4,2,13,'FG-D04-1',100,1,'sh_shipment',@sh,@si,'2026-04-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-100, update_time=NOW() WHERE item_type=2 AND item_id=13 AND batch_no='FG-D04-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,14,'FG-D04-2',100,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0038',2,4,2,14,'FG-D04-2',100,1,'sh_shipment',@sh,@si,'2026-04-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-100, update_time=NOW() WHERE item_type=2 AND item_id=14 AND batch_no='FG-D04-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D04',4,@o,@sh,15800.0,15800.0,0,'2026-04-20',2,'seed-ar-D04','finance',NOW());
-- ===== SO2026D05 2026-05 style=5 qty/sku=180 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D05',5,'2026-05-05','2026-05-20',5,35640.0,'sales',NOW(),'2026-05-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,5,(SELECT color_id FROM md_sku WHERE sku_id=16),(SELECT size_id FROM md_sku WHERE sku_id=16),180,99.00,17820.00,'sales',NOW()),
(@o,5,(SELECT color_id FROM md_sku WHERE sku_id=17),(SELECT size_id FROM md_sku WHERE sku_id=17),180,99.00,17820.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D05',@o,5,360,'2026-05-06','2026-05-18','2026-05-06','2026-05-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,16,180,180,'admin',NOW()),
(@w,17,180,180,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,7,'米',1.000,6.00,381.600,381.600,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,7,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0039',1,5,1,7,'SUP',381.600,1,'stock_check',NULL,NULL,'2026-05-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+381.600, update_time=NOW() WHERE item_type=1 AND item_id=7 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0040',2,2,1,7,'SUP',381.600,1,'pr_wo',@w,@m1,'2026-05-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-381.600, update_time=NOW() WHERE item_type=1 AND item_id=7 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,8,'个',1.000,0.00,360.000,360.000,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,8,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0041',1,5,1,8,'SUP',360.000,1,'stock_check',NULL,NULL,'2026-05-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+360.000, update_time=NOW() WHERE item_type=1 AND item_id=8 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0042',2,2,1,8,'SUP',360.000,1,'pr_wo',@w,@m2,'2026-05-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-360.000, update_time=NOW() WHERE item_type=1 AND item_id=8 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.025,2.00,9.180,9.180,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0043',1,5,1,5,'SUP',9.180,1,'stock_check',NULL,NULL,'2026-05-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+9.180, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0044',2,2,1,5,'SUP',9.180,1,'pr_wo',@w,@m3,'2026-05-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-9.180, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,360,360,0,0,1.40,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D05-1-1',@wp1,@w,16,3,0,180,0,'2026-05-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(3,1,5,@w,180,1.40,252.00,'2026-05',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D05-1-2',@wp1,@w,17,4,0,180,0,'2026-05-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(4,1,5,@w,180,1.40,252.00,'2026-05',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,360,360,0,0,4.20,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D05-2-1',@wp2,@w,16,9,0,180,0,'2026-05-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(9,2,5,@w,180,4.20,756.00,'2026-05',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D05-2-2',@wp2,@w,17,10,0,180,0,'2026-05-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(10,2,5,@w,180,4.20,756.00,'2026-05',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,360,360,0,0,0.70,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D05-3-1',@wp3,@w,16,11,0,180,0,'2026-05-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(11,3,5,@w,180,0.70,126.00,'2026-05',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D05-3-2',@wp3,@w,17,12,0,180,0,'2026-05-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(12,3,5,@w,180,0.70,126.00,'2026-05',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,16,'FG-D05-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0045',1,3,2,16,'FG-D05-1',180,1,'pr_wo',@w,@ws1,'2026-05-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+180, update_time=NOW() WHERE item_type=2 AND item_id=16 AND batch_no='FG-D05-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,17,'FG-D05-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0046',1,3,2,17,'FG-D05-2',180,1,'pr_wo',@w,@ws2,'2026-05-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+180, update_time=NOW() WHERE item_type=2 AND item_id=17 AND batch_no='FG-D05-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D05',@o,5,'2026-05-22','顺丰速运','SF00000005',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,16,'FG-D05-1',180,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0047',2,4,2,16,'FG-D05-1',180,1,'sh_shipment',@sh,@si,'2026-05-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-180, update_time=NOW() WHERE item_type=2 AND item_id=16 AND batch_no='FG-D05-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,17,'FG-D05-2',180,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0048',2,4,2,17,'FG-D05-2',180,1,'sh_shipment',@sh,@si,'2026-05-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-180, update_time=NOW() WHERE item_type=2 AND item_id=17 AND batch_no='FG-D05-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D05',5,@o,@sh,35640.0,35640.0,0,'2026-05-20',2,'seed-ar-D05','finance',NOW());
-- ===== SO2026D06 2026-06 style=1 qty/sku=120 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D06',6,'2026-06-05','2026-06-20',5,21360.0,'sales',NOW(),'2026-06-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,1,(SELECT color_id FROM md_sku WHERE sku_id=1),(SELECT size_id FROM md_sku WHERE sku_id=1),120,89.00,10680.00,'sales',NOW()),
(@o,1,(SELECT color_id FROM md_sku WHERE sku_id=2),(SELECT size_id FROM md_sku WHERE sku_id=2),120,89.00,10680.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D06',@o,1,240,'2026-06-06','2026-06-18','2026-06-06','2026-06-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,1,120,120,'admin',NOW()),
(@w,2,120,120,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,1,'米',0.800,5.00,201.600,201.600,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,1,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0049',1,5,1,1,'SUP',201.600,1,'stock_check',NULL,NULL,'2026-06-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+201.600, update_time=NOW() WHERE item_type=1 AND item_id=1 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0050',2,2,1,1,'SUP',201.600,1,'pr_wo',@w,@m1,'2026-06-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-201.600, update_time=NOW() WHERE item_type=1 AND item_id=1 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,4,'个',1.000,0.00,240.000,240.000,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,4,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0051',1,5,1,4,'SUP',240.000,1,'stock_check',NULL,NULL,'2026-06-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+240.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0052',2,2,1,4,'SUP',240.000,1,'pr_wo',@w,@m2,'2026-06-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-240.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.020,2.00,4.896,4.896,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0053',1,5,1,5,'SUP',4.896,1,'stock_check',NULL,NULL,'2026-06-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+4.896, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0054',2,2,1,5,'SUP',4.896,1,'pr_wo',@w,@m3,'2026-06-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-4.896, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,240,240,0,0,1.20,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D06-1-1',@wp1,@w,1,17,0,120,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(17,1,1,@w,120,1.20,144.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D06-1-2',@wp1,@w,2,18,0,120,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(18,1,1,@w,120,1.20,144.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,240,240,0,0,3.50,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D06-2-1',@wp2,@w,1,19,0,120,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(19,2,1,@w,120,3.50,420.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D06-2-2',@wp2,@w,2,20,0,120,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(20,2,1,@w,120,3.50,420.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,240,240,0,0,0.60,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D06-3-1',@wp3,@w,1,21,0,120,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(21,3,1,@w,120,0.60,72.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D06-3-2',@wp3,@w,2,22,0,120,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(22,3,1,@w,120,0.60,72.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,1,'FG-D06-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0055',1,3,2,1,'FG-D06-1',120,1,'pr_wo',@w,@ws1,'2026-06-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+120, update_time=NOW() WHERE item_type=2 AND item_id=1 AND batch_no='FG-D06-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,2,'FG-D06-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0056',1,3,2,2,'FG-D06-2',120,1,'pr_wo',@w,@ws2,'2026-06-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+120, update_time=NOW() WHERE item_type=2 AND item_id=2 AND batch_no='FG-D06-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D06',@o,6,'2026-06-22','顺丰速运','SF00000006',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,1,'FG-D06-1',120,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0057',2,4,2,1,'FG-D06-1',120,1,'sh_shipment',@sh,@si,'2026-06-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-120, update_time=NOW() WHERE item_type=2 AND item_id=1 AND batch_no='FG-D06-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,2,'FG-D06-2',120,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0058',2,4,2,2,'FG-D06-2',120,1,'sh_shipment',@sh,@si,'2026-06-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-120, update_time=NOW() WHERE item_type=2 AND item_id=2 AND batch_no='FG-D06-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D06',6,@o,@sh,21360.0,21360.0,0,'2026-06-20',2,'seed-ar-D06','finance',NOW());
-- ===== SO2026D07 2026-07 style=2 qty/sku=150 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D07',7,'2026-07-05','2026-07-20',5,38700.0,'sales',NOW(),'2026-07-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,2,(SELECT color_id FROM md_sku WHERE sku_id=6),(SELECT size_id FROM md_sku WHERE sku_id=6),150,129.00,19350.00,'sales',NOW()),
(@o,2,(SELECT color_id FROM md_sku WHERE sku_id=7),(SELECT size_id FROM md_sku WHERE sku_id=7),150,129.00,19350.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D07',@o,2,300,'2026-07-06','2026-07-18','2026-07-06','2026-07-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,6,150,150,'admin',NOW()),
(@w,7,150,150,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,2,'米',1.200,6.00,381.600,381.600,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,2,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0059',1,5,1,2,'SUP',381.600,1,'stock_check',NULL,NULL,'2026-07-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+381.600, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0060',2,2,1,2,'SUP',381.600,1,'pr_wo',@w,@m1,'2026-07-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-381.600, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,3,'条',1.000,1.00,303.000,303.000,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,3,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0061',1,5,1,3,'SUP',303.000,1,'stock_check',NULL,NULL,'2026-07-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+303.000, update_time=NOW() WHERE item_type=1 AND item_id=3 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0062',2,2,1,3,'SUP',303.000,1,'pr_wo',@w,@m2,'2026-07-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-303.000, update_time=NOW() WHERE item_type=1 AND item_id=3 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.030,2.00,9.180,9.180,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0063',1,5,1,5,'SUP',9.180,1,'stock_check',NULL,NULL,'2026-07-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+9.180, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0064',2,2,1,5,'SUP',9.180,1,'pr_wo',@w,@m3,'2026-07-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-9.180, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,300,300,0,0,1.50,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D07-1-1',@wp1,@w,6,23,0,150,0,'2026-07-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(23,1,2,@w,150,1.50,225.00,'2026-07',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D07-1-2',@wp1,@w,7,24,0,150,0,'2026-07-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(24,1,2,@w,150,1.50,225.00,'2026-07',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,300,300,0,0,4.80,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D07-2-1',@wp2,@w,6,25,0,150,0,'2026-07-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(25,2,2,@w,150,4.80,720.00,'2026-07',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D07-2-2',@wp2,@w,7,26,0,150,0,'2026-07-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(26,2,2,@w,150,4.80,720.00,'2026-07',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,300,300,0,0,0.80,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D07-3-1',@wp3,@w,6,27,0,150,0,'2026-07-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(27,3,2,@w,150,0.80,120.00,'2026-07',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D07-3-2',@wp3,@w,7,28,0,150,0,'2026-07-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(28,3,2,@w,150,0.80,120.00,'2026-07',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,6,'FG-D07-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0065',1,3,2,6,'FG-D07-1',150,1,'pr_wo',@w,@ws1,'2026-07-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+150, update_time=NOW() WHERE item_type=2 AND item_id=6 AND batch_no='FG-D07-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,7,'FG-D07-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0066',1,3,2,7,'FG-D07-2',150,1,'pr_wo',@w,@ws2,'2026-07-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+150, update_time=NOW() WHERE item_type=2 AND item_id=7 AND batch_no='FG-D07-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D07',@o,7,'2026-07-22','顺丰速运','SF00000007',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,6,'FG-D07-1',150,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0067',2,4,2,6,'FG-D07-1',150,1,'sh_shipment',@sh,@si,'2026-07-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-150, update_time=NOW() WHERE item_type=2 AND item_id=6 AND batch_no='FG-D07-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,7,'FG-D07-2',150,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0068',2,4,2,7,'FG-D07-2',150,1,'sh_shipment',@sh,@si,'2026-07-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-150, update_time=NOW() WHERE item_type=2 AND item_id=7 AND batch_no='FG-D07-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D07',7,@o,@sh,38700.0,38700.0,0,'2026-07-20',2,'seed-ar-D07','finance',NOW());
-- ===== SO2026D08 2026-08 style=3 qty/sku=200 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D08',1,'2026-08-05','2026-08-20',5,11600.0,'sales',NOW(),'2026-08-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,3,(SELECT color_id FROM md_sku WHERE sku_id=10),(SELECT size_id FROM md_sku WHERE sku_id=10),200,29.00,5800.00,'sales',NOW()),
(@o,3,(SELECT color_id FROM md_sku WHERE sku_id=11),(SELECT size_id FROM md_sku WHERE sku_id=11),200,29.00,5800.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D08',@o,3,400,'2026-08-06','2026-08-18','2026-08-06','2026-08-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,10,200,200,'admin',NOW()),
(@w,11,200,200,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,2,'米',0.080,4.00,33.280,33.280,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,2,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0069',1,5,1,2,'SUP',33.280,1,'stock_check',NULL,NULL,'2026-08-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+33.280, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0070',2,2,1,2,'SUP',33.280,1,'pr_wo',@w,@m1,'2026-08-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-33.280, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.010,2.00,4.080,4.080,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0071',1,5,1,5,'SUP',4.080,1,'stock_check',NULL,NULL,'2026-08-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+4.080, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0072',2,2,1,5,'SUP',4.080,1,'pr_wo',@w,@m2,'2026-08-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-4.080, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,400,400,0,0,0.10,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D08-1-1',@wp1,@w,10,1,0,200,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(1,1,3,@w,200,0.10,20.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D08-1-2',@wp1,@w,11,2,0,200,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(2,1,3,@w,200,0.10,20.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,400,400,0,0,0.35,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D08-2-1',@wp2,@w,10,3,0,200,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(3,2,3,@w,200,0.35,70.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D08-2-2',@wp2,@w,11,4,0,200,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(4,2,3,@w,200,0.35,70.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,10,'FG-D08-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0073',1,3,2,10,'FG-D08-1',200,1,'pr_wo',@w,@ws1,'2026-08-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+200, update_time=NOW() WHERE item_type=2 AND item_id=10 AND batch_no='FG-D08-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,11,'FG-D08-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0074',1,3,2,11,'FG-D08-2',200,1,'pr_wo',@w,@ws2,'2026-08-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+200, update_time=NOW() WHERE item_type=2 AND item_id=11 AND batch_no='FG-D08-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D08',@o,1,'2026-08-22','顺丰速运','SF00000008',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,10,'FG-D08-1',200,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0075',2,4,2,10,'FG-D08-1',200,1,'sh_shipment',@sh,@si,'2026-08-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-200, update_time=NOW() WHERE item_type=2 AND item_id=10 AND batch_no='FG-D08-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,11,'FG-D08-2',200,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0076',2,4,2,11,'FG-D08-2',200,1,'sh_shipment',@sh,@si,'2026-08-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-200, update_time=NOW() WHERE item_type=2 AND item_id=11 AND batch_no='FG-D08-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D08',1,@o,@sh,11600.0,11600.0,0,'2026-08-20',2,'seed-ar-D08','finance',NOW());
-- ===== SO2026D09 2026-09 style=4 qty/sku=100 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D09',2,'2026-09-05','2026-09-20',5,15800.0,'sales',NOW(),'2026-09-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,4,(SELECT color_id FROM md_sku WHERE sku_id=13),(SELECT size_id FROM md_sku WHERE sku_id=13),100,79.00,7900.00,'sales',NOW()),
(@o,4,(SELECT color_id FROM md_sku WHERE sku_id=14),(SELECT size_id FROM md_sku WHERE sku_id=14),100,79.00,7900.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D09',@o,4,200,'2026-09-06','2026-09-18','2026-09-06','2026-09-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,13,100,100,'admin',NOW()),
(@w,14,100,100,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,6,'米',0.600,5.00,126.000,126.000,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,6,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0077',1,5,1,6,'SUP',126.000,1,'stock_check',NULL,NULL,'2026-09-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+126.000, update_time=NOW() WHERE item_type=1 AND item_id=6 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0078',2,2,1,6,'SUP',126.000,1,'pr_wo',@w,@m1,'2026-09-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-126.000, update_time=NOW() WHERE item_type=1 AND item_id=6 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,4,'个',1.000,0.00,200.000,200.000,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,4,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0079',1,5,1,4,'SUP',200.000,1,'stock_check',NULL,NULL,'2026-09-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+200.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0080',2,2,1,4,'SUP',200.000,1,'pr_wo',@w,@m2,'2026-09-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-200.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.015,2.00,3.060,3.060,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0081',1,5,1,5,'SUP',3.060,1,'stock_check',NULL,NULL,'2026-09-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+3.060, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0082',2,2,1,5,'SUP',3.060,1,'pr_wo',@w,@m3,'2026-09-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-3.060, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,200,200,0,0,1.00,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D09-1-1',@wp1,@w,13,9,0,100,0,'2026-09-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(9,1,4,@w,100,1.00,100.00,'2026-09',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D09-1-2',@wp1,@w,14,10,0,100,0,'2026-09-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(10,1,4,@w,100,1.00,100.00,'2026-09',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,200,200,0,0,2.80,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D09-2-1',@wp2,@w,13,11,0,100,0,'2026-09-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(11,2,4,@w,100,2.80,280.00,'2026-09',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D09-2-2',@wp2,@w,14,12,0,100,0,'2026-09-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(12,2,4,@w,100,2.80,280.00,'2026-09',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,200,200,0,0,0.50,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D09-3-1',@wp3,@w,13,17,0,100,0,'2026-09-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(17,3,4,@w,100,0.50,50.00,'2026-09',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D09-3-2',@wp3,@w,14,18,0,100,0,'2026-09-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(18,3,4,@w,100,0.50,50.00,'2026-09',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,13,'FG-D09-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0083',1,3,2,13,'FG-D09-1',100,1,'pr_wo',@w,@ws1,'2026-09-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+100, update_time=NOW() WHERE item_type=2 AND item_id=13 AND batch_no='FG-D09-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,14,'FG-D09-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0084',1,3,2,14,'FG-D09-2',100,1,'pr_wo',@w,@ws2,'2026-09-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+100, update_time=NOW() WHERE item_type=2 AND item_id=14 AND batch_no='FG-D09-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D09',@o,2,'2026-09-22','顺丰速运','SF00000009',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,13,'FG-D09-1',100,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0085',2,4,2,13,'FG-D09-1',100,1,'sh_shipment',@sh,@si,'2026-09-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-100, update_time=NOW() WHERE item_type=2 AND item_id=13 AND batch_no='FG-D09-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,14,'FG-D09-2',100,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0086',2,4,2,14,'FG-D09-2',100,1,'sh_shipment',@sh,@si,'2026-09-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-100, update_time=NOW() WHERE item_type=2 AND item_id=14 AND batch_no='FG-D09-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D09',2,@o,@sh,15800.0,15800.0,0,'2026-09-20',2,'seed-ar-D09','finance',NOW());
-- ===== SO2026D10 2026-03 style=5 qty/sku=80 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D10',3,'2026-03-05','2026-03-20',5,15840.0,'sales',NOW(),'2026-03-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,5,(SELECT color_id FROM md_sku WHERE sku_id=16),(SELECT size_id FROM md_sku WHERE sku_id=16),80,99.00,7920.00,'sales',NOW()),
(@o,5,(SELECT color_id FROM md_sku WHERE sku_id=17),(SELECT size_id FROM md_sku WHERE sku_id=17),80,99.00,7920.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D10',@o,5,160,'2026-03-06','2026-03-18','2026-03-06','2026-03-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,16,80,80,'admin',NOW()),
(@w,17,80,80,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,7,'米',1.000,6.00,169.600,169.600,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,7,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0087',1,5,1,7,'SUP',169.600,1,'stock_check',NULL,NULL,'2026-03-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+169.600, update_time=NOW() WHERE item_type=1 AND item_id=7 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0088',2,2,1,7,'SUP',169.600,1,'pr_wo',@w,@m1,'2026-03-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-169.600, update_time=NOW() WHERE item_type=1 AND item_id=7 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,8,'个',1.000,0.00,160.000,160.000,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,8,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0089',1,5,1,8,'SUP',160.000,1,'stock_check',NULL,NULL,'2026-03-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+160.000, update_time=NOW() WHERE item_type=1 AND item_id=8 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0090',2,2,1,8,'SUP',160.000,1,'pr_wo',@w,@m2,'2026-03-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-160.000, update_time=NOW() WHERE item_type=1 AND item_id=8 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.025,2.00,4.080,4.080,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0091',1,5,1,5,'SUP',4.080,1,'stock_check',NULL,NULL,'2026-03-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+4.080, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0092',2,2,1,5,'SUP',4.080,1,'pr_wo',@w,@m3,'2026-03-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-4.080, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,160,160,0,0,1.40,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D10-1-1',@wp1,@w,16,19,0,80,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(19,1,5,@w,80,1.40,112.00,'2026-03',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D10-1-2',@wp1,@w,17,20,0,80,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(20,1,5,@w,80,1.40,112.00,'2026-03',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,160,160,0,0,4.20,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D10-2-1',@wp2,@w,16,21,0,80,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(21,2,5,@w,80,4.20,336.00,'2026-03',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D10-2-2',@wp2,@w,17,22,0,80,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(22,2,5,@w,80,4.20,336.00,'2026-03',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,160,160,0,0,0.70,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D10-3-1',@wp3,@w,16,23,0,80,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(23,3,5,@w,80,0.70,56.00,'2026-03',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D10-3-2',@wp3,@w,17,24,0,80,0,'2026-03-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(24,3,5,@w,80,0.70,56.00,'2026-03',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,16,'FG-D10-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0093',1,3,2,16,'FG-D10-1',80,1,'pr_wo',@w,@ws1,'2026-03-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+80, update_time=NOW() WHERE item_type=2 AND item_id=16 AND batch_no='FG-D10-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,17,'FG-D10-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0094',1,3,2,17,'FG-D10-2',80,1,'pr_wo',@w,@ws2,'2026-03-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+80, update_time=NOW() WHERE item_type=2 AND item_id=17 AND batch_no='FG-D10-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D10',@o,3,'2026-03-22','顺丰速运','SF00000010',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,16,'FG-D10-1',80,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0095',2,4,2,16,'FG-D10-1',80,1,'sh_shipment',@sh,@si,'2026-03-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-80, update_time=NOW() WHERE item_type=2 AND item_id=16 AND batch_no='FG-D10-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,17,'FG-D10-2',80,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0096',2,4,2,17,'FG-D10-2',80,1,'sh_shipment',@sh,@si,'2026-03-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-80, update_time=NOW() WHERE item_type=2 AND item_id=17 AND batch_no='FG-D10-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D10',3,@o,@sh,15840.0,15840.0,0,'2026-03-20',2,'seed-ar-D10','finance',NOW());
-- ===== SO2026D11 2026-06 style=1 qty/sku=90 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D11',4,'2026-06-05','2026-06-20',5,16020.0,'sales',NOW(),'2026-06-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,1,(SELECT color_id FROM md_sku WHERE sku_id=1),(SELECT size_id FROM md_sku WHERE sku_id=1),90,89.00,8010.00,'sales',NOW()),
(@o,1,(SELECT color_id FROM md_sku WHERE sku_id=2),(SELECT size_id FROM md_sku WHERE sku_id=2),90,89.00,8010.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D11',@o,1,180,'2026-06-06','2026-06-18','2026-06-06','2026-06-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,1,90,90,'admin',NOW()),
(@w,2,90,90,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,1,'米',0.800,5.00,151.200,151.200,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,1,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0097',1,5,1,1,'SUP',151.200,1,'stock_check',NULL,NULL,'2026-06-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+151.200, update_time=NOW() WHERE item_type=1 AND item_id=1 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0098',2,2,1,1,'SUP',151.200,1,'pr_wo',@w,@m1,'2026-06-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-151.200, update_time=NOW() WHERE item_type=1 AND item_id=1 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,4,'个',1.000,0.00,180.000,180.000,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,4,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0099',1,5,1,4,'SUP',180.000,1,'stock_check',NULL,NULL,'2026-06-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+180.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0100',2,2,1,4,'SUP',180.000,1,'pr_wo',@w,@m2,'2026-06-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-180.000, update_time=NOW() WHERE item_type=1 AND item_id=4 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.020,2.00,3.672,3.672,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0101',1,5,1,5,'SUP',3.672,1,'stock_check',NULL,NULL,'2026-06-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+3.672, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0102',2,2,1,5,'SUP',3.672,1,'pr_wo',@w,@m3,'2026-06-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-3.672, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,180,180,0,0,1.20,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D11-1-1',@wp1,@w,1,25,0,90,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(25,1,1,@w,90,1.20,108.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D11-1-2',@wp1,@w,2,26,0,90,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(26,1,1,@w,90,1.20,108.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,180,180,0,0,3.50,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D11-2-1',@wp2,@w,1,27,0,90,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(27,2,1,@w,90,3.50,315.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D11-2-2',@wp2,@w,2,28,0,90,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(28,2,1,@w,90,3.50,315.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,180,180,0,0,0.60,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D11-3-1',@wp3,@w,1,1,0,90,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(1,3,1,@w,90,0.60,54.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D11-3-2',@wp3,@w,2,2,0,90,0,'2026-06-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(2,3,1,@w,90,0.60,54.00,'2026-06',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,1,'FG-D11-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0103',1,3,2,1,'FG-D11-1',90,1,'pr_wo',@w,@ws1,'2026-06-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+90, update_time=NOW() WHERE item_type=2 AND item_id=1 AND batch_no='FG-D11-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,2,'FG-D11-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0104',1,3,2,2,'FG-D11-2',90,1,'pr_wo',@w,@ws2,'2026-06-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+90, update_time=NOW() WHERE item_type=2 AND item_id=2 AND batch_no='FG-D11-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D11',@o,4,'2026-06-22','顺丰速运','SF00000011',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,1,'FG-D11-1',90,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0105',2,4,2,1,'FG-D11-1',90,1,'sh_shipment',@sh,@si,'2026-06-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-90, update_time=NOW() WHERE item_type=2 AND item_id=1 AND batch_no='FG-D11-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,2,'FG-D11-2',90,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0106',2,4,2,2,'FG-D11-2',90,1,'sh_shipment',@sh,@si,'2026-06-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-90, update_time=NOW() WHERE item_type=2 AND item_id=2 AND batch_no='FG-D11-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D11',4,@o,@sh,16020.0,16020.0,0,'2026-06-20',2,'seed-ar-D11','finance',NOW());
-- ===== SO2026D12 2026-08 style=2 qty/sku=110 =====
INSERT INTO sd_order(order_no,customer_id,order_date,delivery_date,status,total_amount,create_by,create_time,full_ship_date)
VALUES('SO2026D12',5,'2026-08-05','2026-08-20',5,28380.0,'sales',NOW(),'2026-08-22');
SET @o := LAST_INSERT_ID();
INSERT INTO sd_order_item(order_id,style_id,color_id,size_id,qty,unit_price,amount,create_by,create_time) VALUES
(@o,2,(SELECT color_id FROM md_sku WHERE sku_id=6),(SELECT size_id FROM md_sku WHERE sku_id=6),110,129.00,14190.00,'sales',NOW()),
(@o,2,(SELECT color_id FROM md_sku WHERE sku_id=7),(SELECT size_id FROM md_sku WHERE sku_id=7),110,129.00,14190.00,'sales',NOW());
INSERT INTO pr_work_order(wo_no,order_id,style_id,qty,plan_start_date,plan_end_date,actual_start_date,actual_end_date,status,create_by,create_time)
VALUES('WO2026D12',@o,2,220,'2026-08-06','2026-08-18','2026-08-06','2026-08-18',2,'admin',NOW());
SET @w := LAST_INSERT_ID();
INSERT INTO pr_work_order_sku(wo_id,sku_id,plan_qty,completed_qty,create_by,create_time) VALUES
(@w,6,110,110,'admin',NOW()),
(@w,7,110,110,'admin',NOW());
SET @ws1 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id LIMIT 1);
SET @ws2 := (SELECT id FROM pr_work_order_sku WHERE wo_id=@w ORDER BY id DESC LIMIT 1);
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,2,'米',1.200,6.00,279.840,279.840,'admin',NOW(),0);
SET @m1 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,2,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0107',1,5,1,2,'SUP',279.840,1,'stock_check',NULL,NULL,'2026-08-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+279.840, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0108',2,2,1,2,'SUP',279.840,1,'pr_wo',@w,@m1,'2026-08-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-279.840, update_time=NOW() WHERE item_type=1 AND item_id=2 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,3,'条',1.000,1.00,222.200,222.200,'admin',NOW(),0);
SET @m2 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,3,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0109',1,5,1,3,'SUP',222.200,1,'stock_check',NULL,NULL,'2026-08-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+222.200, update_time=NOW() WHERE item_type=1 AND item_id=3 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0110',2,2,1,3,'SUP',222.200,1,'pr_wo',@w,@m2,'2026-08-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-222.200, update_time=NOW() WHERE item_type=1 AND item_id=3 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_material(wo_id,material_id,unit,unit_qty,loss_rate,plan_qty,issued_qty,create_by,create_time,returned_qty)
VALUES(@w,5,'卷',0.030,2.00,6.732,6.732,'admin',NOW(),0);
SET @m3 := LAST_INSERT_ID();
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(1,5,'SUP',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0111',1,5,1,5,'SUP',6.732,1,'stock_check',NULL,NULL,'2026-08-06 09:00:00','补货备料','admin',NOW());
UPDATE st_inventory SET qty=qty+6.732, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0112',2,2,1,5,'SUP',6.732,1,'pr_wo',@w,@m3,'2026-08-07 09:00:00','生产领料','admin',NOW());
UPDATE st_inventory SET qty=qty-6.732, update_time=NOW() WHERE item_type=1 AND item_id=5 AND batch_no='SUP' AND warehouse_id=1;
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,1,1,220,220,0,0,1.50,0,2,'admin',NOW());
SET @wp1 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D12-1-1',@wp1,@w,6,3,0,110,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(3,1,2,@w,110,1.50,165.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D12-1-2',@wp1,@w,7,4,0,110,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(4,1,2,@w,110,1.50,165.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,2,2,220,220,0,0,4.80,0,2,'admin',NOW());
SET @wp2 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D12-2-1',@wp2,@w,6,9,0,110,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(9,2,2,@w,110,4.80,528.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D12-2-2',@wp2,@w,7,10,0,110,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(10,2,2,@w,110,4.80,528.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO pr_work_order_process(wo_id,process_id,seq,plan_qty,done_qty,bad_qty,is_outsourced,unit_price,outsource_price,status,create_by,create_time)
VALUES(@w,3,3,220,220,0,0,0.80,0,2,'admin',NOW());
SET @wp3 := LAST_INSERT_ID();
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D12-3-1',@wp3,@w,6,11,0,110,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(11,3,2,@w,110,0.80,88.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO pr_process_report(request_key,wo_process_id,wo_id,sku_id,emp_id,is_outsourced,good_qty,bad_qty,report_time,data_origin,create_by,create_time)
VALUES('R-D12-3-2',@wp3,@w,7,12,0,110,0,'2026-08-12 10:00:00',0,'admin',NOW());
SET @r := LAST_INSERT_ID();
INSERT INTO hr_piece_wage(emp_id,process_id,style_id,work_order_id,qty,unit_price,amount,wage_period,report_id,status,create_by,create_time)
VALUES(12,3,2,@w,110,0.80,88.00,'2026-08',@r,1,'admin',NOW());
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,6,'FG-D12-1',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0113',1,3,2,6,'FG-D12-1',110,1,'pr_wo',@w,@ws1,'2026-08-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+110, update_time=NOW() WHERE item_type=2 AND item_id=6 AND batch_no='FG-D12-1' AND warehouse_id=1;
INSERT INTO st_inventory(item_type,item_id,batch_no,warehouse_id,qty,create_by,create_time,update_time)
VALUES(2,7,'FG-D12-2',1,0,'admin',NOW(),NOW()) ON DUPLICATE KEY UPDATE qty=qty;
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0114',1,3,2,7,'FG-D12-2',110,1,'pr_wo',@w,@ws2,'2026-08-18 16:00:00','完工入库','admin',NOW());
UPDATE st_inventory SET qty=qty+110, update_time=NOW() WHERE item_type=2 AND item_id=7 AND batch_no='FG-D12-2' AND warehouse_id=1;
INSERT INTO sh_shipment(shipment_no,order_id,customer_id,ship_date,logistics_company,logistics_no,status,create_by,create_time)
VALUES('SH2026D12',@o,5,'2026-08-22','顺丰速运','SF00000012',2,'wh',NOW());
SET @sh := LAST_INSERT_ID();
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,6,'FG-D12-1',110,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0115',2,4,2,6,'FG-D12-1',110,1,'sh_shipment',@sh,@si,'2026-08-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-110, update_time=NOW() WHERE item_type=2 AND item_id=6 AND batch_no='FG-D12-1' AND warehouse_id=1;
INSERT INTO sh_shipment_item(shipment_id,sku_id,batch_no,qty,create_by,create_time)
VALUES(@sh,7,'FG-D12-2',110,'wh',NOW());
SET @si := LAST_INSERT_ID();
INSERT INTO st_stock_txn(txn_no,txn_type,biz_type,item_type,item_id,batch_no,qty,warehouse_id,source_type,source_id,source_line_id,txn_time,remark,create_by,create_time)
VALUES('TXSEED0116',2,4,2,7,'FG-D12-2',110,1,'sh_shipment',@sh,@si,'2026-08-22 10:00:00','销售出库','wh',NOW());
UPDATE st_inventory SET qty=qty-110, update_time=NOW() WHERE item_type=2 AND item_id=7 AND batch_no='FG-D12-2' AND warehouse_id=1;
INSERT INTO fn_receivable(ar_no,customer_id,order_id,shipment_id,amount,received_amount,balance,due_date,status,request_key,create_by,create_time)
VALUES('AR2026D12',5,@o,@sh,28380.0,28380.0,0,'2026-08-20',2,'seed-ar-D12','finance',NOW());