-- MySQL dump 10.13  Distrib 8.4.6, for Win64 (x86_64)
--
-- Host: localhost    Database: hai_erp_demo
-- ------------------------------------------------------
-- Server version	8.4.6

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `biz_action_request`
--

DROP TABLE IF EXISTS `biz_action_request`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `biz_action_request` (
  `request_key` varchar(80) NOT NULL,
  `action_type` varchar(40) NOT NULL,
  `target_type` varchar(40) NOT NULL,
  `target_id` bigint DEFAULT NULL,
  `payload_hash` char(64) NOT NULL,
  `status` tinyint NOT NULL COMMENT '0 processing, 1 succeeded, 2 failed',
  `result_json` json DEFAULT NULL,
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `update_time` datetime DEFAULT NULL,
  PRIMARY KEY (`request_key`),
  KEY `idx_target` (`target_type`,`target_id`),
  CONSTRAINT `chk_action_status` CHECK ((`status` in (0,1,2)))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Cross-module action idempotency';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `biz_action_request`
--

LOCK TABLES `biz_action_request` WRITE;
/*!40000 ALTER TABLE `biz_action_request` DISABLE KEYS */;
/*!40000 ALTER TABLE `biz_action_request` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fn_cost_detail`
--

DROP TABLE IF EXISTS `fn_cost_detail`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fn_cost_detail` (
  `detail_id` bigint NOT NULL AUTO_INCREMENT,
  `settle_id` bigint NOT NULL,
  `line_type` tinyint NOT NULL COMMENT '1 material, 2 process, 3 overhead',
  `source_id` bigint NOT NULL COMMENT 'Overhead uses 0',
  `qty` decimal(14,3) NOT NULL,
  `unit_price` decimal(12,4) NOT NULL,
  `amount` decimal(12,2) NOT NULL,
  `price_source` varchar(40) NOT NULL,
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`detail_id`),
  UNIQUE KEY `uk_settle_line` (`settle_id`,`line_type`,`source_id`),
  CONSTRAINT `chk_cost_detail` CHECK (((`qty` >= 0) and (`unit_price` >= 0) and (`amount` >= 0)))
) ENGINE=InnoDB AUTO_INCREMENT=36 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Frozen standard-cost lines';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fn_cost_detail`
--

LOCK TABLES `fn_cost_detail` WRITE;
/*!40000 ALTER TABLE `fn_cost_detail` DISABLE KEYS */;
INSERT INTO `fn_cost_detail` VALUES (1,1,1,1,302.400,12.5000,3780.00,'purchase_snapshot','seed','2026-10-15 15:07:58'),(2,1,1,4,360.000,0.1500,54.00,'purchase_snapshot','seed','2026-10-15 15:07:58'),(3,1,1,5,7.344,8.0000,58.75,'purchase_snapshot','seed','2026-10-15 15:07:58'),(4,2,1,2,445.200,18.0000,8013.60,'purchase_snapshot','seed','2026-10-15 15:07:58'),(5,2,1,3,353.500,1.2000,424.20,'purchase_snapshot','seed','2026-10-15 15:07:58'),(6,2,1,5,10.710,8.0000,85.68,'purchase_snapshot','seed','2026-10-15 15:07:58'),(7,3,1,2,99.840,18.0000,1797.12,'purchase_snapshot','seed','2026-10-15 15:07:58'),(8,3,1,5,12.240,8.0000,97.92,'purchase_snapshot','seed','2026-10-15 15:07:58'),(9,5,1,5,3.825,8.0000,30.60,'purchase_snapshot','seed','2026-10-15 15:07:58'),(10,5,1,7,159.000,26.0000,4134.00,'purchase_snapshot','seed','2026-10-15 15:07:58'),(11,5,1,8,150.000,0.1000,15.00,'purchase_snapshot','seed','2026-10-15 15:07:58'),(12,4,1,4,200.000,0.1500,30.00,'purchase_snapshot','seed','2026-10-15 15:07:58'),(13,4,1,5,3.060,8.0000,24.48,'purchase_snapshot','seed','2026-10-15 15:07:58'),(14,4,1,6,126.000,22.0000,2772.00,'purchase_snapshot','seed','2026-10-15 15:07:58'),(16,1,2,1,360.000,1.2000,432.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(17,1,2,2,360.000,3.5000,1260.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(18,1,2,3,360.000,0.6000,216.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(19,2,2,4,350.000,1.5000,525.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(20,2,2,5,350.000,6.0000,2100.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(21,2,2,6,350.000,0.8000,280.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(22,3,2,7,1200.000,0.1000,120.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(23,3,2,8,1200.000,0.3500,420.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(24,5,2,9,150.000,1.4000,210.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(25,5,2,10,150.000,4.2000,630.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(26,5,2,11,150.000,0.7000,105.00,'work_order_snapshot','seed','2026-10-15 15:07:58'),(27,4,2,9,200.000,1.0000,200.00,'route_estimate','seed','2026-10-15 15:07:58'),(28,4,2,10,200.000,2.8000,560.00,'route_estimate','seed','2026-10-15 15:07:58'),(29,4,2,11,200.000,0.5000,100.00,'route_estimate','seed','2026-10-15 15:07:58'),(31,1,3,0,1.000,464.0600,464.06,'frozen_overhead_rate','seed','2026-10-15 15:07:58'),(32,2,3,0,1.000,914.2800,914.28,'frozen_overhead_rate','seed','2026-10-15 15:07:58'),(33,3,3,0,1.000,194.8000,194.80,'frozen_overhead_rate','seed','2026-10-15 15:07:58'),(34,4,3,0,1.000,294.9200,294.92,'frozen_overhead_rate','seed','2026-10-15 15:07:58'),(35,5,3,0,1.000,409.9700,409.97,'frozen_overhead_rate','seed','2026-10-15 15:07:58');
/*!40000 ALTER TABLE `fn_cost_detail` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `fn_cost_settlement`
--

DROP TABLE IF EXISTS `fn_cost_settlement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `fn_cost_settlement` (
  `settle_id` bigint NOT NULL AUTO_INCREMENT COMMENT '结算单ID',
  `settle_no` varchar(30) NOT NULL COMMENT '结算单号',
  `order_id` bigint NOT NULL COMMENT '订单ID（sd_order）',
  `style_id` bigint NOT NULL COMMENT '款式ID（核算粒度 = 订单 × 款式）',
  `qty` int NOT NULL DEFAULT '0' COMMENT '核算数量',
  `material_cost` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '材料成本',
  `labor_cost` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '人工成本（计件，单价取工单工序快照）',
  `overhead_rate` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '制造费用计提比例(%)',
  `overhead_cost` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '制造费用',
  `total_cost` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '总成本 = 材料 + 人工 + 制造费用',
  `unit_cost` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单件成本 = 总成本 / 数量',
  `revenue` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '收入（订单金额）',
  `gross_profit` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '毛利 = 收入 − 总成本',
  `gross_margin` decimal(6,2) NOT NULL DEFAULT '0.00' COMMENT '毛利率(%) = 毛利 / 收入 × 100',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0试算 1已确认',
  `calc_time` datetime DEFAULT NULL COMMENT '核算时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`settle_id`),
  UNIQUE KEY `uk_settle_no` (`settle_no`),
  UNIQUE KEY `uk_order_style` (`order_id`,`style_id`),
  KEY `idx_style` (`style_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='订单级成本结算单';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `fn_cost_settlement`
--

LOCK TABLES `fn_cost_settlement` WRITE;
/*!40000 ALTER TABLE `fn_cost_settlement` DISABLE KEYS */;
INSERT INTO `fn_cost_settlement` VALUES (1,'CS2026001',1,1,360,3892.75,1908.00,8.00,464.06,6264.81,17.40,12600.00,6335.19,50.28,1,'2026-10-15 15:07:57','admin','2026-10-15 15:07:57','',NULL,'T恤订单结算（已确认，反哺报价 QT2026003）','0'),(2,'CS2026002',2,2,350,8523.48,2905.00,8.00,914.28,12342.76,35.27,19250.00,6907.24,35.88,0,'2026-10-15 15:07:57','admin','2026-10-15 15:07:57','',NULL,'长裤订单试算（缝制外发，按外发单价计）','0'),(3,'CS2026003',3,3,1200,1895.04,540.00,8.00,194.80,2629.84,2.19,7200.00,4570.16,63.47,1,'2026-10-15 15:07:57','admin','2026-10-15 15:07:57','',NULL,'袜子订单结算（已确认，反哺报价 QT2026004）','0'),(4,'CS2026004',4,4,200,2826.48,860.00,8.00,294.92,3981.40,19.91,5600.00,1618.60,28.90,0,'2026-10-15 15:07:57','admin','2026-10-15 15:07:57','',NULL,'背心订单试算（未排产，按工艺路线理论成本）','0'),(5,'CS2026005',5,5,150,4179.60,945.00,8.00,409.97,5534.57,36.90,6750.00,1215.43,18.01,0,'2026-10-15 15:07:57','admin','2026-10-15 15:07:57','',NULL,'紧身裤订单试算（生产中）','0');
/*!40000 ALTER TABLE `fn_cost_settlement` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_department`
--

DROP TABLE IF EXISTS `hr_department`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hr_department` (
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门ID',
  `dept_name` varchar(50) NOT NULL COMMENT '部门名称',
  `dept_type` tinyint NOT NULL DEFAULT '1' COMMENT '部门类型：1生产 2职能',
  `parent_id` bigint NOT NULL DEFAULT '0' COMMENT '上级部门ID（0为顶级）',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1正常 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dept_id`),
  UNIQUE KEY `uk_dept_name` (`dept_name`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_department`
--

LOCK TABLES `hr_department` WRITE;
/*!40000 ALTER TABLE `hr_department` DISABLE KEYS */;
INSERT INTO `hr_department` VALUES (1,'裁剪车间',1,0,1,1,'admin','2026-10-15 15:07:55','',NULL,'生产 - 裁剪'),(2,'缝制车间',1,0,2,1,'admin','2026-10-15 15:07:55','',NULL,'生产 - 缝制'),(3,'整烫车间',1,0,3,1,'admin','2026-10-15 15:07:55','',NULL,'生产 - 整烫'),(4,'采购部',2,0,4,1,'admin','2026-10-15 15:07:55','',NULL,'职能 - 采购'),(5,'销售部',2,0,5,1,'admin','2026-10-15 15:07:55','',NULL,'职能 - 销售'),(6,'财务部',2,0,6,1,'admin','2026-10-15 15:07:55','',NULL,'职能 - 财务'),(7,'人事部',2,0,7,1,'admin','2026-10-15 15:07:55','',NULL,'职能 - 人事'),(8,'仓储部',2,0,8,1,'admin','2026-10-15 15:07:55','',NULL,'职能 - 仓储'),(9,'厂办',2,0,9,1,'admin','2026-10-15 15:07:55','',NULL,'职能 - 厂办/计划');
/*!40000 ALTER TABLE `hr_department` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_employee`
--

DROP TABLE IF EXISTS `hr_employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hr_employee` (
  `emp_id` bigint NOT NULL AUTO_INCREMENT COMMENT '员工ID',
  `emp_no` varchar(20) NOT NULL COMMENT '工号',
  `name` varchar(50) NOT NULL COMMENT '姓名',
  `gender` tinyint NOT NULL DEFAULT '0' COMMENT '性别：0女 1男',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `position` varchar(50) DEFAULT NULL COMMENT '岗位/工种',
  `emp_type` tinyint NOT NULL DEFAULT '1' COMMENT '员工类型：1生产(计件) 2职能(固定薪)',
  `hire_date` date DEFAULT NULL COMMENT '入职日期',
  `base_salary` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '月基本工资标准',
  `si_base` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '社保缴费基数',
  `user_id` bigint DEFAULT NULL COMMENT '关联登录账号ID（车间工人可为空）',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1在职 0离职',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`emp_id`),
  UNIQUE KEY `uk_emp_no` (`emp_no`),
  KEY `idx_dept` (`dept_id`),
  KEY `idx_user` (`user_id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='员工档案表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_employee`
--

LOCK TABLES `hr_employee` WRITE;
/*!40000 ALTER TABLE `hr_employee` DISABLE KEYS */;
INSERT INTO `hr_employee` VALUES (1,'E001','张伟',1,'13800000001',2,'缝制工',1,'2023-03-01',2200.00,4000.00,NULL,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'E002','李娜',0,'13800000002',2,'缝制工',1,'2023-06-15',2200.00,4000.00,NULL,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'E003','王强',1,'13800000003',1,'裁剪工',1,'2022-09-10',2300.00,4200.00,NULL,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(4,'E004','赵敏',0,'13800000004',3,'整烫工',1,'2024-01-05',2100.00,3800.00,NULL,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(5,'E005','陈静',0,'13800000005',6,'会计',2,'2021-07-01',6500.00,8000.00,5,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(6,'E006','刘洋',1,'13800000006',5,'销售代表',2,'2022-05-20',5000.00,6500.00,6,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(7,'E007','孙丽',0,'13800000007',7,'人事专员',2,'2023-02-01',5500.00,7000.00,7,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(8,'E008','周涛',1,'13800000008',9,'厂长',2,'2020-01-01',15000.00,18000.00,8,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(9,'E009','吴迪',1,'13800000009',1,'裁剪工',1,'2023-08-01',2300.00,4200.00,NULL,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(10,'E010','郑爽',0,'13800000010',2,'缝制工',1,'2024-03-01',2200.00,4000.00,NULL,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(11,'E011','冯磊',1,'13800000011',2,'缝制工',1,'2024-05-10',2200.00,4000.00,NULL,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(12,'E012','何静',0,'13800000012',3,'整烫工',1,'2024-06-20',2100.00,3800.00,NULL,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(13,'E013','许峰',1,'13800000013',4,'采购专员',2,'2022-11-01',5200.00,6800.00,9,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(14,'E014','邓丽',0,'13800000014',5,'销售代表',2,'2023-04-15',5000.00,6500.00,10,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(15,'E015','曹阳',1,'13800000015',8,'仓管员',2,'2023-09-01',4800.00,6200.00,11,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(16,'E016','韩雪',0,'13800000016',9,'计划跟单',2,'2022-02-20',5800.00,7200.00,12,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `hr_employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_piece_wage`
--

DROP TABLE IF EXISTS `hr_piece_wage`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hr_piece_wage` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` bigint NOT NULL COMMENT '员工ID',
  `work_order_id` bigint DEFAULT NULL COMMENT '生产工单ID',
  `style_id` bigint DEFAULT NULL COMMENT '款式ID（冗余，便于按款式×工序统计）',
  `process_id` bigint NOT NULL COMMENT '工序ID（关联 md_process）',
  `qty` int NOT NULL DEFAULT '0' COMMENT '完成件数',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '件单价（快照，取自 pr_work_order_process.unit_price）',
  `amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '金额 = 件数 × 单价',
  `wage_period` char(7) NOT NULL COMMENT '工资期（yyyy-MM，如 2026-10）',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待发 1已发',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `report_id` bigint DEFAULT NULL COMMENT 'NULL for imported legacy wages',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_report_id` (`report_id`),
  KEY `idx_emp_period` (`emp_id`,`wage_period`),
  KEY `idx_wo` (`work_order_id`),
  KEY `idx_style_process` (`style_id`,`process_id`)
) ENGINE=InnoDB AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='计件工资明细表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_piece_wage`
--

LOCK TABLES `hr_piece_wage` WRITE;
/*!40000 ALTER TABLE `hr_piece_wage` DISABLE KEYS */;
INSERT INTO `hr_piece_wage` VALUES (1,3,3,3,1,1200,0.10,120.00,'2026-09',1,'admin','2026-10-15 15:07:55',NULL),(2,1,3,3,2,600,0.35,210.00,'2026-09',1,'admin','2026-10-15 15:07:55',NULL),(3,2,3,3,2,600,0.35,210.00,'2026-09',1,'admin','2026-10-15 15:07:55',NULL),(4,11,3,3,2,400,0.35,140.00,'2026-09',1,'admin','2026-10-15 15:07:55',NULL),(5,12,3,3,2,300,0.35,105.00,'2026-09',1,'admin','2026-10-15 15:07:55',NULL),(6,9,1,1,1,360,1.20,432.00,'2026-10',0,'admin','2026-10-15 15:07:55',NULL),(7,1,1,1,2,180,3.50,630.00,'2026-10',0,'admin','2026-10-15 15:07:55',NULL),(8,2,1,1,2,180,3.50,630.00,'2026-10',0,'admin','2026-10-15 15:07:55',NULL),(9,4,1,1,3,360,0.60,216.00,'2026-10',0,'admin','2026-10-15 15:07:55',NULL),(10,3,2,2,1,350,1.50,525.00,'2026-10',0,'admin','2026-10-15 15:07:55',NULL),(13,9,4,5,1,150,1.40,210.00,'2026-10',0,'admin','2026-10-15 15:07:55',NULL),(14,10,4,5,2,80,4.20,336.00,'2026-10',0,'admin','2026-10-15 15:07:55',NULL),(15,12,4,5,3,50,0.70,35.00,'2026-10',0,'admin','2026-10-15 15:07:55',NULL);
/*!40000 ALTER TABLE `hr_piece_wage` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `hr_salary_slip`
--

DROP TABLE IF EXISTS `hr_salary_slip`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `hr_salary_slip` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `emp_id` bigint NOT NULL COMMENT '员工ID',
  `wage_period` char(7) NOT NULL COMMENT '工资期（yyyy-MM）',
  `base_salary` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '基本工资',
  `allowance` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '补贴合计（餐补/全勤/交通等）',
  `piece_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '计件工资（汇总自 hr_piece_wage）',
  `overtime_pay` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '加班费',
  `gross_pay` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '应发合计',
  `si_personal` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '社保（个人承担）',
  `fund_personal` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '公积金（个人承担）',
  `tax` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '个税',
  `other_deduction` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '其他扣款',
  `net_pay` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '实发工资',
  `si_company` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '社保（公司承担）',
  `fund_company` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '公积金（公司承担）',
  `company_cost` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '公司用工成本',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待发 1已发',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_emp_period` (`emp_id`,`wage_period`),
  KEY `idx_period` (`wage_period`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='月度工资单表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `hr_salary_slip`
--

LOCK TABLES `hr_salary_slip` WRITE;
/*!40000 ALTER TABLE `hr_salary_slip` DISABLE KEYS */;
INSERT INTO `hr_salary_slip` VALUES (1,1,'2026-09',2200.00,300.00,210.00,100.00,2810.00,420.00,200.00,0.00,0.00,2190.00,1080.00,200.00,4090.00,1,'admin','2026-10-15 15:07:55'),(2,2,'2026-09',2200.00,300.00,210.00,80.00,2790.00,420.00,200.00,0.00,0.00,2170.00,1080.00,200.00,4070.00,1,'admin','2026-10-15 15:07:55'),(3,3,'2026-09',2300.00,300.00,120.00,0.00,2720.00,441.00,210.00,0.00,0.00,2069.00,1134.00,210.00,4064.00,1,'admin','2026-10-15 15:07:55'),(4,5,'2026-09',6500.00,500.00,0.00,0.00,7000.00,840.00,400.00,0.00,0.00,5760.00,2160.00,400.00,9560.00,1,'admin','2026-10-15 15:07:55'),(5,8,'2026-09',15000.00,500.00,0.00,0.00,15500.00,1890.00,900.00,231.30,0.00,12478.70,4860.00,900.00,21260.00,1,'admin','2026-10-15 15:07:55'),(6,1,'2026-10',2200.00,300.00,630.00,200.00,3330.00,420.00,200.00,0.00,0.00,2710.00,1080.00,200.00,4610.00,0,'admin','2026-10-15 15:07:55'),(7,2,'2026-10',2200.00,300.00,630.00,180.00,3310.00,420.00,200.00,0.00,0.00,2690.00,1080.00,200.00,4590.00,0,'admin','2026-10-15 15:07:55'),(8,3,'2026-10',2300.00,300.00,525.00,150.00,3275.00,441.00,210.00,0.00,0.00,2624.00,1134.00,210.00,4619.00,0,'admin','2026-10-15 15:07:55'),(9,4,'2026-10',2100.00,300.00,216.00,0.00,2616.00,399.00,190.00,0.00,0.00,2027.00,1026.00,190.00,3832.00,0,'admin','2026-10-15 15:07:55'),(10,9,'2026-10',2300.00,300.00,642.00,180.00,3422.00,441.00,210.00,0.00,0.00,2771.00,1134.00,210.00,4766.00,0,'admin','2026-10-15 15:07:55'),(11,10,'2026-10',2200.00,300.00,336.00,220.00,3056.00,420.00,200.00,0.00,0.00,2436.00,1080.00,200.00,4336.00,0,'admin','2026-10-15 15:07:55'),(12,11,'2026-10',2200.00,300.00,0.00,100.00,2600.00,420.00,200.00,0.00,0.00,1980.00,1080.00,200.00,3880.00,0,'admin','2026-10-15 15:07:55'),(13,12,'2026-10',2100.00,300.00,35.00,0.00,2435.00,399.00,190.00,0.00,0.00,1846.00,1026.00,190.00,3651.00,0,'admin','2026-10-15 15:07:55'),(14,5,'2026-10',6500.00,500.00,0.00,0.00,7000.00,840.00,400.00,0.00,0.00,5760.00,2160.00,400.00,9560.00,0,'admin','2026-10-15 15:07:55'),(15,6,'2026-10',5000.00,500.00,0.00,0.00,5500.00,682.50,325.00,0.00,0.00,4492.50,1755.00,325.00,7580.00,0,'admin','2026-10-15 15:07:55'),(16,7,'2026-10',5500.00,500.00,0.00,0.00,6000.00,735.00,350.00,0.00,0.00,4915.00,1890.00,350.00,8240.00,0,'admin','2026-10-15 15:07:55'),(17,8,'2026-10',15000.00,500.00,0.00,0.00,15500.00,1890.00,900.00,231.30,0.00,12478.70,4860.00,900.00,21260.00,0,'admin','2026-10-15 15:07:55'),(18,13,'2026-10',5200.00,500.00,0.00,0.00,5700.00,714.00,340.00,0.00,0.00,4646.00,1836.00,340.00,7876.00,0,'admin','2026-10-15 15:07:55'),(19,15,'2026-10',4800.00,500.00,0.00,0.00,5300.00,651.00,310.00,0.00,0.00,4339.00,1674.00,310.00,7284.00,0,'admin','2026-10-15 15:07:55'),(20,16,'2026-10',5800.00,500.00,0.00,0.00,6300.00,756.00,360.00,0.00,0.00,5184.00,1944.00,360.00,8604.00,0,'admin','2026-10-15 15:07:55');
/*!40000 ALTER TABLE `hr_salary_slip` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_bom`
--

DROP TABLE IF EXISTS `md_bom`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_bom` (
  `bom_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'BOM ID',
  `style_id` bigint NOT NULL COMMENT '款式ID',
  `material_id` bigint NOT NULL COMMENT '物料ID',
  `qty` decimal(10,3) NOT NULL DEFAULT '0.000' COMMENT '单件用量',
  `loss_rate` decimal(5,2) NOT NULL DEFAULT '0.00' COMMENT '损耗率(%)，实际领料=qty*(1+loss_rate/100)',
  `unit` varchar(10) DEFAULT NULL COMMENT '单位',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`bom_id`),
  UNIQUE KEY `uk_style_material` (`style_id`,`material_id`),
  KEY `idx_style` (`style_id`),
  KEY `idx_material` (`material_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='BOM（款式级用料）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_bom`
--

LOCK TABLES `md_bom` WRITE;
/*!40000 ALTER TABLE `md_bom` DISABLE KEYS */;
INSERT INTO `md_bom` VALUES (1,1,1,0.800,5.00,'米','admin','2026-10-15 15:07:55','',NULL,NULL),(2,1,4,1.000,0.00,'个','admin','2026-10-15 15:07:55','',NULL,NULL),(3,1,5,0.020,2.00,'卷','admin','2026-10-15 15:07:55','',NULL,NULL),(4,2,2,1.200,6.00,'米','admin','2026-10-15 15:07:55','',NULL,NULL),(5,2,3,1.000,1.00,'条','admin','2026-10-15 15:07:55','',NULL,NULL),(6,2,5,0.030,2.00,'卷','admin','2026-10-15 15:07:55','',NULL,NULL),(7,3,2,0.080,4.00,'米','admin','2026-10-15 15:07:55','',NULL,NULL),(8,3,5,0.010,2.00,'卷','admin','2026-10-15 15:07:55','',NULL,NULL),(9,4,6,0.600,5.00,'米','admin','2026-10-15 15:07:55','',NULL,NULL),(10,4,4,1.000,0.00,'个','admin','2026-10-15 15:07:55','',NULL,NULL),(11,4,5,0.015,2.00,'卷','admin','2026-10-15 15:07:55','',NULL,NULL),(12,5,7,1.000,6.00,'米','admin','2026-10-15 15:07:55','',NULL,NULL),(13,5,8,1.000,0.00,'个','admin','2026-10-15 15:07:55','',NULL,NULL),(14,5,5,0.025,2.00,'卷','admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_bom` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_category`
--

DROP TABLE IF EXISTS `md_category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_category` (
  `category_id` bigint NOT NULL AUTO_INCREMENT COMMENT '品类ID',
  `category_name` varchar(50) NOT NULL COMMENT '品类名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`category_id`),
  UNIQUE KEY `uk_category_name` (`category_name`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='品类字典';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_category`
--

LOCK TABLES `md_category` WRITE;
/*!40000 ALTER TABLE `md_category` DISABLE KEYS */;
INSERT INTO `md_category` VALUES (1,'上衣',1,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'裤子',2,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'袜子',3,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_category` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_color`
--

DROP TABLE IF EXISTS `md_color`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_color` (
  `color_id` bigint NOT NULL AUTO_INCREMENT COMMENT '颜色ID',
  `color_code` varchar(20) NOT NULL COMMENT '颜色编码',
  `color_name` varchar(50) NOT NULL COMMENT '颜色名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`color_id`),
  UNIQUE KEY `uk_color_code` (`color_code`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='颜色字典';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_color`
--

LOCK TABLES `md_color` WRITE;
/*!40000 ALTER TABLE `md_color` DISABLE KEYS */;
INSERT INTO `md_color` VALUES (1,'RED','红色',1,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'BLK','黑色',2,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'WHT','白色',3,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(4,'NVY','藏青',4,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(5,'GRY','灰色',5,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(6,'KHA','卡其',6,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(7,'ARMY','军绿',7,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_color` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_customer`
--

DROP TABLE IF EXISTS `md_customer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_customer` (
  `customer_id` bigint NOT NULL AUTO_INCREMENT COMMENT '客户ID',
  `customer_code` varchar(30) NOT NULL COMMENT '客户编码',
  `customer_name` varchar(100) NOT NULL COMMENT '客户名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `credit_days` int NOT NULL DEFAULT '0' COMMENT '账期(天)',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`customer_id`),
  UNIQUE KEY `uk_customer_code` (`customer_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='客户表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_customer`
--

LOCK TABLES `md_customer` WRITE;
/*!40000 ALTER TABLE `md_customer` DISABLE KEYS */;
INSERT INTO `md_customer` VALUES (1,'C001','星辰体育用品有限公司','李经理','13900000001','浙江省宁波市',30,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'C002','华东服饰批发中心','王老板','13900000002','江苏省苏州市',15,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'C003','悦动电商旗舰店','赵主管','13900000003','广东省广州市',7,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_customer` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_material`
--

DROP TABLE IF EXISTS `md_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_material` (
  `material_id` bigint NOT NULL AUTO_INCREMENT COMMENT '物料ID',
  `material_code` varchar(30) NOT NULL COMMENT '物料编码',
  `material_name` varchar(100) NOT NULL COMMENT '物料名称',
  `category` tinyint NOT NULL DEFAULT '1' COMMENT '物料类别：1面料 2辅料',
  `spec` varchar(100) DEFAULT NULL COMMENT '规格',
  `unit` varchar(10) NOT NULL DEFAULT '米' COMMENT '单位：米/个/卷/条',
  `ref_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '参考单价',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`material_id`),
  UNIQUE KEY `uk_material_code` (`material_code`),
  KEY `idx_category` (`category`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='面料/辅料表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_material`
--

LOCK TABLES `md_material` WRITE;
/*!40000 ALTER TABLE `md_material` DISABLE KEYS */;
INSERT INTO `md_material` VALUES (1,'M001','涤纶网眼布',1,'150g/㎡','米',12.50,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'M002','弹力棉针织布',1,'180g/㎡','米',18.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'M003','树脂拉链',2,'30cm','条',1.20,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(4,'M004','织唛主标',2,'3×5cm','个',0.15,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(5,'M005','涤棉缝纫线',2,'40/2','卷',8.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(6,'M006','速干锦纶布',1,'160g/㎡','米',22.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(7,'M007','氨纶弹力布',1,'220g/㎡','米',26.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(8,'M008','洗水标',2,'2×4cm','个',0.10,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_process`
--

DROP TABLE IF EXISTS `md_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_process` (
  `process_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工序ID',
  `process_code` varchar(20) NOT NULL COMMENT '工序编码',
  `process_name` varchar(50) NOT NULL COMMENT '工序名称',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`process_id`),
  UNIQUE KEY `uk_process_code` (`process_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='工序字典';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_process`
--

LOCK TABLES `md_process` WRITE;
/*!40000 ALTER TABLE `md_process` DISABLE KEYS */;
INSERT INTO `md_process` VALUES (1,'CUT','裁剪',1,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'SEW','缝制',2,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'IRON','整烫',3,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_size`
--

DROP TABLE IF EXISTS `md_size`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_size` (
  `size_id` bigint NOT NULL AUTO_INCREMENT COMMENT '尺码ID',
  `size_code` varchar(20) NOT NULL COMMENT '尺码编码',
  `size_name` varchar(50) NOT NULL COMMENT '尺码名称',
  `size_group` varchar(20) NOT NULL DEFAULT '服装码' COMMENT '尺码组：服装码/袜码',
  `sort` int NOT NULL DEFAULT '0' COMMENT '显示顺序',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`size_id`),
  UNIQUE KEY `uk_group_code` (`size_group`,`size_code`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='尺码字典';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_size`
--

LOCK TABLES `md_size` WRITE;
/*!40000 ALTER TABLE `md_size` DISABLE KEYS */;
INSERT INTO `md_size` VALUES (1,'S','S','服装码',1,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'M','M','服装码',2,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'L','L','服装码',3,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(4,'XL','XL','服装码',4,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(5,'XXL','XXL','服装码',5,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(6,'F','均码','袜码',1,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_size` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_sku`
--

DROP TABLE IF EXISTS `md_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_sku` (
  `sku_id` bigint NOT NULL AUTO_INCREMENT COMMENT 'SKU ID',
  `sku_code` varchar(50) NOT NULL COMMENT 'SKU 编码',
  `style_id` bigint NOT NULL COMMENT '款式ID',
  `color_id` bigint NOT NULL COMMENT '颜色ID',
  `size_id` bigint NOT NULL COMMENT '尺码ID',
  `barcode` varchar(50) DEFAULT NULL COMMENT '条码',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`sku_id`),
  UNIQUE KEY `uk_sku_code` (`sku_code`),
  UNIQUE KEY `uk_style_color_size` (`style_id`,`color_id`,`size_id`),
  KEY `idx_style` (`style_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='SKU 表（按需生成）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_sku`
--

LOCK TABLES `md_sku` WRITE;
/*!40000 ALTER TABLE `md_sku` DISABLE KEYS */;
INSERT INTO `md_sku` VALUES (1,'ST2026001-BLK-M',1,2,2,'6901000000001',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'ST2026001-BLK-L',1,2,3,'6901000000002',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'ST2026001-WHT-M',1,3,2,'6901000000003',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(4,'ST2026001-WHT-L',1,3,3,'6901000000004',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(5,'ST2026001-NVY-L',1,4,3,'6901000000005',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(6,'ST2026002-BLK-M',2,2,2,'6901000000011',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(7,'ST2026002-BLK-L',2,2,3,'6901000000012',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(8,'ST2026002-BLK-XL',2,2,4,'6901000000013',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(9,'ST2026002-GRY-L',2,5,3,'6901000000014',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(10,'ST2026003-BLK-F',3,2,6,'6901000000021',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(11,'ST2026003-WHT-F',3,3,6,'6901000000022',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(12,'ST2026003-GRY-F',3,5,6,'6901000000023',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(13,'ST2026004-WHT-S',4,3,1,'6901000000031',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(14,'ST2026004-WHT-M',4,3,2,'6901000000032',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(15,'ST2026004-RED-M',4,1,2,'6901000000033',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(16,'ST2026005-BLK-S',5,2,1,'6901000000041',1,'admin','2026-10-15 15:07:55','',NULL,NULL),(17,'ST2026005-BLK-M',5,2,2,'6901000000042',1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_style`
--

DROP TABLE IF EXISTS `md_style`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_style` (
  `style_id` bigint NOT NULL AUTO_INCREMENT COMMENT '款式ID',
  `style_no` varchar(30) NOT NULL COMMENT '款号',
  `style_name` varchar(100) NOT NULL COMMENT '款式名称',
  `category_id` bigint DEFAULT NULL COMMENT '品类ID（md_category）',
  `gender` tinyint NOT NULL DEFAULT '2' COMMENT '性别：0女 1男 2中性',
  `season` varchar(20) DEFAULT NULL COMMENT '季节，如 2026夏',
  `unit` varchar(10) NOT NULL DEFAULT '件' COMMENT '单位：件/条/双',
  `base_cost` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '基准成本（参考）',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`style_id`),
  UNIQUE KEY `uk_style_no` (`style_no`),
  KEY `idx_category` (`category_id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='款式表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_style`
--

LOCK TABLES `md_style` WRITE;
/*!40000 ALTER TABLE `md_style` DISABLE KEYS */;
INSERT INTO `md_style` VALUES (1,'ST2026001','男款运动短袖T恤',1,1,'2026夏','件',35.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'ST2026002','男款运动束脚长裤',2,1,'2026夏','条',55.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'ST2026003','专业运动短袜',3,2,'2026夏','双',6.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(4,'ST2026004','女款速干运动背心',1,0,'2026夏','件',28.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(5,'ST2026005','女款高弹紧身裤',2,0,'2026秋','条',45.00,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_style` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_style_process`
--

DROP TABLE IF EXISTS `md_style_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_style_process` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `style_id` bigint NOT NULL COMMENT '款式ID',
  `process_id` bigint NOT NULL COMMENT '工序ID',
  `seq` int NOT NULL DEFAULT '0' COMMENT '工序顺序',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '件单价',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_style_process` (`style_id`,`process_id`),
  KEY `idx_style` (`style_id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='款式工艺（含件单价）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_style_process`
--

LOCK TABLES `md_style_process` WRITE;
/*!40000 ALTER TABLE `md_style_process` DISABLE KEYS */;
INSERT INTO `md_style_process` VALUES (1,1,1,1,1.20,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,1,2,2,3.50,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,1,3,3,0.60,'admin','2026-10-15 15:07:55','',NULL,NULL),(4,2,1,1,1.50,'admin','2026-10-15 15:07:55','',NULL,NULL),(5,2,2,2,4.80,'admin','2026-10-15 15:07:55','',NULL,NULL),(6,2,3,3,0.80,'admin','2026-10-15 15:07:55','',NULL,NULL),(7,3,1,1,0.10,'admin','2026-10-15 15:07:55','',NULL,NULL),(8,3,2,2,0.35,'admin','2026-10-15 15:07:55','',NULL,NULL),(9,4,1,1,1.00,'admin','2026-10-15 15:07:55','',NULL,NULL),(10,4,2,2,2.80,'admin','2026-10-15 15:07:55','',NULL,NULL),(11,4,3,3,0.50,'admin','2026-10-15 15:07:55','',NULL,NULL),(12,5,1,1,1.40,'admin','2026-10-15 15:07:55','',NULL,NULL),(13,5,2,2,4.20,'admin','2026-10-15 15:07:55','',NULL,NULL),(14,5,3,3,0.70,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_style_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `md_supplier`
--

DROP TABLE IF EXISTS `md_supplier`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `md_supplier` (
  `supplier_id` bigint NOT NULL AUTO_INCREMENT COMMENT '供应商ID',
  `supplier_code` varchar(30) NOT NULL COMMENT '供应商编码',
  `supplier_name` varchar(100) NOT NULL COMMENT '供应商名称',
  `contact` varchar(50) DEFAULT NULL COMMENT '联系人',
  `phone` varchar(20) DEFAULT NULL COMMENT '联系电话',
  `address` varchar(200) DEFAULT NULL COMMENT '地址',
  `credit_days` int NOT NULL DEFAULT '0' COMMENT '账期(天)',
  `status` tinyint NOT NULL DEFAULT '1' COMMENT '状态：1启用 0停用',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`supplier_id`),
  UNIQUE KEY `uk_supplier_code` (`supplier_code`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='供应商表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `md_supplier`
--

LOCK TABLES `md_supplier` WRITE;
/*!40000 ALTER TABLE `md_supplier` DISABLE KEYS */;
INSERT INTO `md_supplier` VALUES (1,'S001','恒达面料织造厂','张厂长','13700000001','广东省佛山市',30,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(2,'S002','益丰辅料有限公司','刘经理','13700000002','福建省泉州市',15,1,'admin','2026-10-15 15:07:55','',NULL,NULL),(3,'S003','广盛新材料有限公司','陈总','13700000003','浙江省绍兴市',30,1,'admin','2026-10-15 15:07:55','',NULL,NULL);
/*!40000 ALTER TABLE `md_supplier` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pr_process_report`
--

DROP TABLE IF EXISTS `pr_process_report`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pr_process_report` (
  `report_id` bigint NOT NULL AUTO_INCREMENT,
  `request_key` varchar(80) NOT NULL,
  `wo_process_id` bigint NOT NULL,
  `wo_id` bigint NOT NULL,
  `sku_id` bigint NOT NULL,
  `emp_id` bigint DEFAULT NULL COMMENT 'NULL only for imported aggregates or outsourced work',
  `is_outsourced` tinyint NOT NULL DEFAULT '0' COMMENT '1 outsourced, no employee wage',
  `good_qty` int NOT NULL DEFAULT '0',
  `bad_qty` int NOT NULL DEFAULT '0',
  `report_time` datetime NOT NULL,
  `data_origin` tinyint NOT NULL DEFAULT '0' COMMENT '0 normal, 1 historical aggregate',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`report_id`),
  UNIQUE KEY `uk_request_key` (`request_key`),
  KEY `idx_process_sku` (`wo_process_id`,`sku_id`),
  KEY `idx_wo_sku` (`wo_id`,`sku_id`),
  CONSTRAINT `chk_report_employee` CHECK (((`data_origin` = 1) or (`is_outsourced` = 1) or (`emp_id` is not null))),
  CONSTRAINT `chk_report_origin` CHECK (((`data_origin` in (0,1)) and (`is_outsourced` in (0,1)))),
  CONSTRAINT `chk_report_qty` CHECK (((`good_qty` >= 0) and (`bad_qty` >= 0) and ((`good_qty` + `bad_qty`) > 0)))
) ENGINE=InnoDB AUTO_INCREMENT=27 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Append-only SKU process reports';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pr_process_report`
--

LOCK TABLES `pr_process_report` WRITE;
/*!40000 ALTER TABLE `pr_process_report` DISABLE KEYS */;
INSERT INTO `pr_process_report` VALUES (1,'seed-report-1-1',1,1,1,NULL,0,100,3,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(2,'seed-report-1-2',1,1,2,NULL,0,80,0,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(3,'seed-report-1-3',1,1,3,NULL,0,120,1,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(4,'seed-report-1-4',1,1,4,NULL,0,60,0,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(5,'seed-report-2-1',2,1,1,NULL,0,100,2,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(6,'seed-report-2-2',2,1,2,NULL,0,80,0,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(7,'seed-report-2-3',2,1,3,NULL,0,120,1,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(8,'seed-report-2-4',2,1,4,NULL,0,60,0,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(9,'seed-report-3-1',3,1,1,NULL,0,100,2,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(10,'seed-report-3-2',3,1,2,NULL,0,80,0,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(11,'seed-report-3-3',3,1,3,NULL,0,120,0,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(12,'seed-report-3-4',3,1,4,NULL,0,60,0,'2026-10-20 00:00:00',1,'seed','2026-10-15 15:07:58'),(13,'seed-report-4-6',4,2,6,NULL,0,200,2,'2026-10-14 00:00:00',1,'seed','2026-10-15 15:07:58'),(14,'seed-report-4-7',4,2,7,NULL,0,150,1,'2026-10-14 00:00:00',1,'seed','2026-10-15 15:07:58'),(15,'seed-report-5-6',5,2,6,NULL,1,115,2,'2026-10-14 00:00:00',1,'seed','2026-10-15 15:07:58'),(16,'seed-report-5-7',5,2,7,NULL,1,85,0,'2026-10-14 00:00:00',1,'seed','2026-10-15 15:07:58'),(17,'seed-report-7-10',7,3,10,NULL,0,500,5,'2026-10-04 00:00:00',1,'seed','2026-10-15 15:07:58'),(18,'seed-report-7-11',7,3,11,NULL,0,400,3,'2026-10-04 00:00:00',1,'seed','2026-10-15 15:07:58'),(19,'seed-report-7-12',7,3,12,NULL,0,300,2,'2026-10-04 00:00:00',1,'seed','2026-10-15 15:07:58'),(20,'seed-report-8-10',8,3,10,NULL,0,500,4,'2026-10-04 00:00:00',1,'seed','2026-10-15 15:07:58'),(21,'seed-report-8-11',8,3,11,NULL,0,400,2,'2026-10-04 00:00:00',1,'seed','2026-10-15 15:07:58'),(22,'seed-report-8-12',8,3,12,NULL,0,300,2,'2026-10-04 00:00:00',1,'seed','2026-10-15 15:07:58'),(23,'seed-report-9-16',9,4,16,NULL,0,90,2,'2026-10-08 00:00:00',1,'seed','2026-10-15 15:07:58'),(24,'seed-report-9-17',9,4,17,NULL,0,60,0,'2026-10-08 00:00:00',1,'seed','2026-10-15 15:07:58'),(25,'seed-report-10-16',10,4,16,NULL,0,48,1,'2026-10-08 00:00:00',1,'seed','2026-10-15 15:07:58'),(26,'seed-report-10-17',10,4,17,NULL,0,32,0,'2026-10-08 00:00:00',1,'seed','2026-10-15 15:07:58');
/*!40000 ALTER TABLE `pr_process_report` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pr_work_order`
--

DROP TABLE IF EXISTS `pr_work_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pr_work_order` (
  `wo_id` bigint NOT NULL AUTO_INCREMENT COMMENT '工单ID',
  `wo_no` varchar(30) NOT NULL COMMENT '工单号',
  `order_id` bigint DEFAULT NULL COMMENT '来源订单ID（sd_order）',
  `style_id` bigint NOT NULL COMMENT '款式ID',
  `qty` int NOT NULL DEFAULT '0' COMMENT '计划生产数量',
  `plan_start_date` date DEFAULT NULL COMMENT '计划开工日期',
  `plan_end_date` date DEFAULT NULL COMMENT '计划完工日期',
  `actual_start_date` date DEFAULT NULL COMMENT '实际开工日期',
  `actual_end_date` date DEFAULT NULL COMMENT '实际完工日期',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待开工 1生产中 2已完工 9已取消',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`wo_id`),
  UNIQUE KEY `uk_wo_no` (`wo_no`),
  UNIQUE KEY `uk_order_style` (`order_id`,`style_id`),
  KEY `idx_order` (`order_id`),
  KEY `idx_style` (`style_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产工单主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pr_work_order`
--

LOCK TABLES `pr_work_order` WRITE;
/*!40000 ALTER TABLE `pr_work_order` DISABLE KEYS */;
INSERT INTO `pr_work_order` VALUES (1,'WO2026001',1,1,360,'2026-10-13','2026-10-22','2026-10-13','2026-10-20',2,'admin','2026-10-15 15:07:56','',NULL,NULL,'0'),(2,'WO2026002',2,2,350,'2026-10-14','2026-10-26','2026-10-14',NULL,1,'admin','2026-10-15 15:07:56','',NULL,NULL,'0'),(3,'WO2026003',3,3,1200,'2026-09-20','2026-10-05','2026-09-20','2026-10-04',2,'admin','2026-10-15 15:07:56','',NULL,NULL,'0'),(4,'WO2026004',5,5,150,'2026-10-08','2026-10-25','2026-10-08',NULL,1,'admin','2026-10-15 15:07:56','',NULL,NULL,'0');
/*!40000 ALTER TABLE `pr_work_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pr_work_order_material`
--

DROP TABLE IF EXISTS `pr_work_order_material`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pr_work_order_material` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `wo_id` bigint NOT NULL,
  `material_id` bigint NOT NULL,
  `unit` varchar(10) NOT NULL,
  `unit_qty` decimal(10,3) NOT NULL,
  `loss_rate` decimal(5,2) NOT NULL DEFAULT '0.00',
  `plan_qty` decimal(14,3) NOT NULL,
  `issued_qty` decimal(14,3) NOT NULL DEFAULT '0.000',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wo_material` (`wo_id`,`material_id`),
  KEY `idx_material` (`material_id`),
  CONSTRAINT `chk_wo_material_qty` CHECK (((`unit_qty` > 0) and (`plan_qty` >= 0) and (`issued_qty` >= 0)))
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Frozen work-order BOM and issue totals';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pr_work_order_material`
--

LOCK TABLES `pr_work_order_material` WRITE;
/*!40000 ALTER TABLE `pr_work_order_material` DISABLE KEYS */;
INSERT INTO `pr_work_order_material` VALUES (1,1,1,'米',0.800,5.00,302.400,302.400,'seed','2026-10-15 15:07:58'),(2,1,4,'个',1.000,0.00,360.000,360.000,'seed','2026-10-15 15:07:58'),(3,1,5,'卷',0.020,2.00,7.344,7.344,'seed','2026-10-15 15:07:58'),(4,2,2,'米',1.200,6.00,445.200,300.000,'seed','2026-10-15 15:07:58'),(5,2,3,'条',1.000,1.00,353.500,250.000,'seed','2026-10-15 15:07:58'),(6,2,5,'卷',0.030,2.00,10.710,7.000,'seed','2026-10-15 15:07:58'),(7,3,2,'米',0.080,4.00,99.840,99.840,'seed','2026-10-15 15:07:58'),(8,3,5,'卷',0.010,2.00,12.240,12.240,'seed','2026-10-15 15:07:58'),(9,4,5,'卷',0.025,2.00,3.825,3.000,'seed','2026-10-15 15:07:58'),(10,4,7,'米',1.000,6.00,159.000,120.000,'seed','2026-10-15 15:07:58'),(11,4,8,'个',1.000,0.00,150.000,100.000,'seed','2026-10-15 15:07:58');
/*!40000 ALTER TABLE `pr_work_order_material` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pr_work_order_process`
--

DROP TABLE IF EXISTS `pr_work_order_process`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pr_work_order_process` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT '主键',
  `wo_id` bigint NOT NULL COMMENT '工单ID',
  `process_id` bigint NOT NULL COMMENT '工序ID（md_process）',
  `seq` int NOT NULL DEFAULT '0' COMMENT '工序顺序',
  `plan_qty` int NOT NULL DEFAULT '0' COMMENT '计划数量',
  `done_qty` int NOT NULL DEFAULT '0' COMMENT '完成数量',
  `bad_qty` int NOT NULL DEFAULT '0' COMMENT '不良数量',
  `is_outsourced` tinyint NOT NULL DEFAULT '0' COMMENT '是否外发：0否 1是',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '内部件单价（快照，取自 md_style_process）',
  `outsource_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '外发加工单价（is_outsourced=1 时有效）',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待开工 1进行中 2已完成',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wo_process` (`wo_id`,`process_id`),
  KEY `idx_wo` (`wo_id`),
  KEY `idx_process` (`process_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='生产工序进度';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pr_work_order_process`
--

LOCK TABLES `pr_work_order_process` WRITE;
/*!40000 ALTER TABLE `pr_work_order_process` DISABLE KEYS */;
INSERT INTO `pr_work_order_process` VALUES (1,1,1,1,360,360,4,0,1.20,0.00,2,'admin',NULL),(2,1,2,2,360,360,3,0,3.50,0.00,2,'admin',NULL),(3,1,3,3,360,360,2,0,0.60,0.00,2,'admin',NULL),(4,2,1,1,350,350,3,0,1.50,0.00,2,'admin',NULL),(5,2,2,2,350,200,2,1,4.80,6.00,1,'admin',NULL),(6,2,3,3,350,0,0,0,0.80,0.00,0,'admin',NULL),(7,3,1,1,1200,1200,10,0,0.10,0.00,2,'admin',NULL),(8,3,2,2,1200,1200,8,0,0.35,0.00,2,'admin',NULL),(9,4,1,1,150,150,2,0,1.40,0.00,2,'admin',NULL),(10,4,2,2,150,80,1,0,4.20,0.00,1,'admin',NULL),(11,4,3,3,150,0,0,0,0.70,0.00,0,'admin',NULL);
/*!40000 ALTER TABLE `pr_work_order_process` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pr_work_order_sku`
--

DROP TABLE IF EXISTS `pr_work_order_sku`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pr_work_order_sku` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `wo_id` bigint NOT NULL,
  `sku_id` bigint NOT NULL,
  `plan_qty` int NOT NULL,
  `completed_qty` int NOT NULL DEFAULT '0',
  `create_by` varchar(64) DEFAULT '',
  `create_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uk_wo_sku` (`wo_id`,`sku_id`),
  KEY `idx_sku` (`sku_id`),
  CONSTRAINT `chk_wo_sku_qty` CHECK (((`plan_qty` > 0) and (`completed_qty` >= 0) and (`completed_qty` <= `plan_qty`)))
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Frozen work-order SKU plan';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pr_work_order_sku`
--

LOCK TABLES `pr_work_order_sku` WRITE;
/*!40000 ALTER TABLE `pr_work_order_sku` DISABLE KEYS */;
INSERT INTO `pr_work_order_sku` VALUES (1,1,1,100,100,'seed','2026-10-15 15:07:58'),(2,1,2,80,80,'seed','2026-10-15 15:07:58'),(3,1,3,120,120,'seed','2026-10-15 15:07:58'),(4,1,4,60,60,'seed','2026-10-15 15:07:58'),(5,2,6,200,0,'seed','2026-10-15 15:07:58'),(6,2,7,150,0,'seed','2026-10-15 15:07:58'),(7,3,10,500,500,'seed','2026-10-15 15:07:58'),(8,3,11,400,400,'seed','2026-10-15 15:07:58'),(9,3,12,300,300,'seed','2026-10-15 15:07:58'),(10,4,16,90,0,'seed','2026-10-15 15:07:58'),(11,4,17,60,0,'seed','2026-10-15 15:07:58');
/*!40000 ALTER TABLE `pr_work_order_sku` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pu_purchase_order`
--

DROP TABLE IF EXISTS `pu_purchase_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pu_purchase_order` (
  `po_id` bigint NOT NULL AUTO_INCREMENT COMMENT '采购单ID',
  `po_no` varchar(30) NOT NULL COMMENT '采购单号',
  `supplier_id` bigint NOT NULL COMMENT '供应商ID（md_supplier）',
  `order_date` date DEFAULT NULL COMMENT '下单日期',
  `expect_date` date DEFAULT NULL COMMENT '预计到货日期（供应商承诺）',
  `actual_arrival_date` date DEFAULT NULL COMMENT '实际到货日期（全部到货时回填，用于采购周期与准交率）',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待下单 1已下单 2部分到货 3已到货 9已取消',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '采购总额',
  `buyer_user_id` bigint DEFAULT NULL COMMENT '采购员（关联登录账号）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `suggestion_request_key` varchar(80) DEFAULT NULL COMMENT 'Idempotent MRP generation request',
  PRIMARY KEY (`po_id`),
  UNIQUE KEY `uk_po_no` (`po_no`),
  UNIQUE KEY `uk_suggestion_request_key` (`suggestion_request_key`),
  KEY `idx_supplier` (`supplier_id`),
  KEY `idx_status` (`status`),
  KEY `idx_expect` (`expect_date`),
  KEY `idx_arrival` (`actual_arrival_date`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购单主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pu_purchase_order`
--

LOCK TABLES `pu_purchase_order` WRITE;
/*!40000 ALTER TABLE `pu_purchase_order` DISABLE KEYS */;
INSERT INTO `pu_purchase_order` VALUES (1,'PO2026001',1,'2026-09-26','2026-09-29','2026-09-28',3,11200.00,9,'admin','2026-10-15 15:07:56','',NULL,'T恤网眼布/长裤弹力棉，提前 1 天到货','0',NULL),(2,'PO2026002',2,'2026-09-25','2026-09-27','2026-09-28',3,970.00,9,'admin','2026-10-15 15:07:56','',NULL,'拉链/织唛/缝纫线，比承诺晚 1 天到货','0',NULL),(3,'PO2026003',1,'2026-09-11','2026-09-19','2026-09-18',3,5750.00,9,'admin','2026-10-15 15:07:56','',NULL,'袜子弹力棉/背心速干锦纶，提前 1 天到货','0',NULL),(4,'PO2026004',3,'2026-09-28','2026-10-03','2026-10-02',3,4700.00,9,'admin','2026-10-15 15:07:56','',NULL,'紧身裤氨纶布/洗水标备货（SO2026005 尚未下单，明细 source_order_id 留空）','0',NULL),(5,'PO2026005',1,'2026-10-12','2026-10-26',NULL,1,1760.00,9,'admin','2026-10-15 15:07:56','',NULL,'背心速干锦纶补货，在途未到货','0',NULL),(6,'PO2026006',1,'2026-10-12','2026-10-25',NULL,2,2200.00,9,'seed','2026-10-15 15:07:58','',NULL,NULL,'0',NULL);
/*!40000 ALTER TABLE `pu_purchase_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `pu_purchase_order_item`
--

DROP TABLE IF EXISTS `pu_purchase_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `pu_purchase_order_item` (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `po_id` bigint NOT NULL COMMENT '采购单ID',
  `material_id` bigint NOT NULL COMMENT '物料ID（md_material）',
  `qty` decimal(12,3) NOT NULL DEFAULT '0.000' COMMENT '采购数量',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '金额 = 数量 × 单价',
  `source_order_id` bigint DEFAULT NULL COMMENT '来源订单ID（可空，便于跟单）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`item_id`),
  KEY `idx_po` (`po_id`),
  KEY `idx_material` (`material_id`),
  KEY `idx_material_item` (`material_id`,`item_id`),
  KEY `idx_source_order` (`source_order_id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='采购单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `pu_purchase_order_item`
--

LOCK TABLES `pu_purchase_order_item` WRITE;
/*!40000 ALTER TABLE `pu_purchase_order_item` DISABLE KEYS */;
INSERT INTO `pu_purchase_order_item` VALUES (1,1,1,320.000,12.50,4000.00,1,'admin','2026-10-15 15:07:56'),(2,1,2,400.000,18.00,7200.00,2,'admin','2026-10-15 15:07:56'),(3,2,3,400.000,1.20,480.00,2,'admin','2026-10-15 15:07:56'),(4,2,4,600.000,0.15,90.00,1,'admin','2026-10-15 15:07:56'),(5,2,5,50.000,8.00,400.00,NULL,'admin','2026-10-15 15:07:56'),(6,3,2,150.000,17.80,2670.00,3,'admin','2026-10-15 15:07:56'),(7,3,6,140.000,22.00,3080.00,NULL,'admin','2026-10-15 15:07:56'),(8,4,7,180.000,26.00,4680.00,NULL,'admin','2026-10-15 15:07:56'),(9,4,8,200.000,0.10,20.00,NULL,'admin','2026-10-15 15:07:56'),(10,5,6,80.000,22.00,1760.00,NULL,'admin','2026-10-15 15:07:56'),(11,6,6,100.000,22.00,2200.00,NULL,'seed','2026-10-15 15:07:58');
/*!40000 ALTER TABLE `pu_purchase_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_order`
--

DROP TABLE IF EXISTS `sd_order`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sd_order` (
  `order_id` bigint NOT NULL AUTO_INCREMENT COMMENT '订单ID',
  `order_no` varchar(30) NOT NULL COMMENT '订单号',
  `customer_id` bigint NOT NULL COMMENT '客户ID（md_customer）',
  `order_date` date DEFAULT NULL COMMENT '下单日期',
  `delivery_date` date DEFAULT NULL COMMENT '要求交期',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待确认 1已确认 2生产中 3已完工 4已出货 5已完成 9已取消',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '订单总额',
  `sales_user_id` bigint DEFAULT NULL COMMENT '销售员（关联登录账号）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `full_ship_date` date DEFAULT NULL COMMENT 'All ordered SKU quantities shipped',
  PRIMARY KEY (`order_id`),
  UNIQUE KEY `uk_order_no` (`order_no`),
  KEY `idx_customer` (`customer_id`),
  KEY `idx_status` (`status`),
  KEY `idx_delivery` (`delivery_date`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_order`
--

LOCK TABLES `sd_order` WRITE;
/*!40000 ALTER TABLE `sd_order` DISABLE KEYS */;
INSERT INTO `sd_order` VALUES (1,'SO2026001',1,'2026-09-20','2026-10-18',5,12600.00,6,'admin','2026-10-15 15:07:56','',NULL,'交期 10-18，实际 10-21 出货，延期 3 天','0','2026-10-21'),(2,'SO2026002',2,'2026-09-25','2026-10-13',2,19250.00,14,'admin','2026-10-15 15:07:56','',NULL,'交期偏紧：缝制工序外发，计划完工 10-26 已晚于客户交期','0',NULL),(3,'SO2026003',3,'2026-09-10','2026-10-15',5,7200.00,14,'admin','2026-10-15 15:07:56','',NULL,'交期 10-15，实际 10-10 出货，提前 5 天','0','2026-10-10'),(4,'SO2026004',1,'2026-10-10','2026-11-20',0,5600.00,6,'admin','2026-10-15 15:07:56','',NULL,'待客户确认，尚未排产','0',NULL),(5,'SO2026005',2,'2026-10-05','2026-11-10',2,6750.00,14,'admin','2026-10-15 15:07:56','',NULL,NULL,'0',NULL);
/*!40000 ALTER TABLE `sd_order` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_order_item`
--

DROP TABLE IF EXISTS `sd_order_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sd_order_item` (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `order_id` bigint NOT NULL COMMENT '订单ID',
  `style_id` bigint NOT NULL COMMENT '款式ID',
  `color_id` bigint NOT NULL COMMENT '颜色ID',
  `size_id` bigint NOT NULL COMMENT '尺码ID',
  `qty` int NOT NULL DEFAULT '0' COMMENT '数量',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '单价',
  `amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '金额 = 数量 × 单价',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `uk_order_style_color_size` (`order_id`,`style_id`,`color_id`,`size_id`),
  KEY `idx_order` (`order_id`),
  KEY `idx_style` (`style_id`)
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='销售订单明细（尺码矩阵）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_order_item`
--

LOCK TABLES `sd_order_item` WRITE;
/*!40000 ALTER TABLE `sd_order_item` DISABLE KEYS */;
INSERT INTO `sd_order_item` VALUES (1,1,1,2,2,100,35.00,3500.00,'admin','2026-10-15 15:07:56'),(2,1,1,2,3,80,35.00,2800.00,'admin','2026-10-15 15:07:56'),(3,1,1,3,2,120,35.00,4200.00,'admin','2026-10-15 15:07:56'),(4,1,1,3,3,60,35.00,2100.00,'admin','2026-10-15 15:07:56'),(5,2,2,2,2,200,55.00,11000.00,'admin','2026-10-15 15:07:56'),(6,2,2,2,3,150,55.00,8250.00,'admin','2026-10-15 15:07:56'),(7,3,3,2,6,500,6.00,3000.00,'admin','2026-10-15 15:07:56'),(8,3,3,3,6,400,6.00,2400.00,'admin','2026-10-15 15:07:56'),(9,3,3,5,6,300,6.00,1800.00,'admin','2026-10-15 15:07:56'),(10,4,4,3,1,80,28.00,2240.00,'admin','2026-10-15 15:07:56'),(11,4,4,3,2,120,28.00,3360.00,'admin','2026-10-15 15:07:56'),(12,5,5,2,1,90,45.00,4050.00,'admin','2026-10-15 15:07:56'),(13,5,5,2,2,60,45.00,2700.00,'admin','2026-10-15 15:07:56');
/*!40000 ALTER TABLE `sd_order_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_quotation`
--

DROP TABLE IF EXISTS `sd_quotation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sd_quotation` (
  `quote_id` bigint NOT NULL AUTO_INCREMENT COMMENT '报价ID',
  `quote_no` varchar(30) NOT NULL COMMENT '报价单号',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `quote_date` date DEFAULT NULL COMMENT '报价日期',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待确认 1已接受 2已失效',
  `total_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '报价总额',
  `sales_user_id` bigint DEFAULT NULL COMMENT '销售员',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `request_key` varchar(80) DEFAULT NULL COMMENT 'Idempotent source-settlement request',
  PRIMARY KEY (`quote_id`),
  UNIQUE KEY `uk_quote_no` (`quote_no`),
  UNIQUE KEY `uk_quote_request_key` (`request_key`),
  KEY `idx_customer` (`customer_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='报价单主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_quotation`
--

LOCK TABLES `sd_quotation` WRITE;
/*!40000 ALTER TABLE `sd_quotation` DISABLE KEYS */;
INSERT INTO `sd_quotation` VALUES (1,'QT2026001',1,'2026-09-15',1,12600.00,6,'admin','2026-10-15 15:07:56','',NULL,'T恤首次报价','0',NULL),(2,'QT2026002',3,'2026-09-05',1,7200.00,14,'admin','2026-10-15 15:07:56','',NULL,'袜子首次报价','0',NULL),(3,'QT2026003',1,'2026-10-15',0,13600.00,6,'admin','2026-10-15 15:07:56','',NULL,'T恤补单报价（参照 CS2026001 结算成本）','0',NULL),(4,'QT2026004',3,'2026-10-15',0,8700.00,14,'admin','2026-10-15 15:07:56','',NULL,'袜子补单报价（参照 CS2026003 结算成本）','0',NULL);
/*!40000 ALTER TABLE `sd_quotation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sd_quotation_item`
--

DROP TABLE IF EXISTS `sd_quotation_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sd_quotation_item` (
  `quote_item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '报价明细ID',
  `quote_id` bigint NOT NULL COMMENT '报价ID',
  `style_id` bigint NOT NULL COMMENT '款式ID',
  `qty` int NOT NULL DEFAULT '0' COMMENT '数量',
  `unit_price` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '报价单价',
  `cost_ref` decimal(10,2) NOT NULL DEFAULT '0.00' COMMENT '参考成本（成本反哺报价）',
  `cost_src` tinyint NOT NULL DEFAULT '0' COMMENT '参考成本来源：0手工 1历史结算 2BOM预估',
  `source_settle_id` bigint DEFAULT NULL COMMENT '来源成本结算单ID（cost_src=1 时）',
  `amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '金额 = 数量 × 单价',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`quote_item_id`),
  KEY `idx_quote` (`quote_id`),
  KEY `idx_style` (`style_id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='报价单明细（款式级）';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sd_quotation_item`
--

LOCK TABLES `sd_quotation_item` WRITE;
/*!40000 ALTER TABLE `sd_quotation_item` DISABLE KEYS */;
INSERT INTO `sd_quotation_item` VALUES (1,1,1,360,35.00,17.40,2,NULL,12600.00,'admin','2026-10-15 15:07:56'),(2,2,3,1200,6.00,2.19,2,NULL,7200.00,'admin','2026-10-15 15:07:56'),(3,3,1,400,34.00,17.40,1,1,13600.00,'admin','2026-10-15 15:07:56'),(4,4,3,1500,5.80,2.19,1,3,8700.00,'admin','2026-10-15 15:07:56');
/*!40000 ALTER TABLE `sd_quotation_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sh_return`
--

DROP TABLE IF EXISTS `sh_return`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sh_return` (
  `return_id` bigint NOT NULL AUTO_INCREMENT COMMENT '退货单ID',
  `return_no` varchar(30) NOT NULL COMMENT '退货单号',
  `order_id` bigint DEFAULT NULL COMMENT '原订单ID',
  `shipment_id` bigint DEFAULT NULL COMMENT '关联出货单ID',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `return_date` date DEFAULT NULL COMMENT '退货日期',
  `reason` varchar(255) DEFAULT NULL COMMENT '退货原因',
  `return_type` tinyint NOT NULL DEFAULT '1' COMMENT '退货类型：1退货入库 2换货 3报废',
  `is_restock` tinyint NOT NULL DEFAULT '1' COMMENT '是否回库：1回库 0不回库（报废/就地处理）',
  `refund_amount` decimal(12,2) NOT NULL DEFAULT '0.00' COMMENT '退款金额（换货可为 0）',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待处理 1已处理 9已取消',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`return_id`),
  UNIQUE KEY `uk_return_no` (`return_no`),
  KEY `idx_order` (`order_id`),
  KEY `idx_customer` (`customer_id`),
  KEY `idx_return_type` (`return_type`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='退货单主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_return`
--

LOCK TABLES `sh_return` WRITE;
/*!40000 ALTER TABLE `sh_return` DISABLE KEYS */;
INSERT INTO `sh_return` VALUES (1,'RT2026001',1,1,1,'2026-10-23','部分尺码偏小，客户要求退货',1,1,175.00,1,'admin','2026-10-15 15:07:57','',NULL,'退货入库，按订单单价全额退款','0'),(2,'RT2026002',3,2,3,'2026-10-12','颜色与样品不符',2,1,0.00,1,'admin','2026-10-15 15:07:57','',NULL,'换货：重发同款，不退款','0'),(3,'RT2026003',1,1,1,'2026-10-24','水洗后印花开裂，客户拒收',3,0,105.00,1,'admin','2026-10-15 15:07:57','',NULL,'货物未回厂，就地报废；按订单单价认赔，不产生入库流水','0');
/*!40000 ALTER TABLE `sh_return` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sh_return_item`
--

DROP TABLE IF EXISTS `sh_return_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sh_return_item` (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `return_id` bigint NOT NULL COMMENT '退货单ID',
  `sku_id` bigint NOT NULL COMMENT '成品SKU ID',
  `qty` int NOT NULL DEFAULT '0' COMMENT '退货数量',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `batch_no` varchar(30) NOT NULL DEFAULT '' COMMENT 'Returned batch',
  PRIMARY KEY (`item_id`),
  KEY `idx_return` (`return_id`),
  KEY `idx_sku` (`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='退货单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_return_item`
--

LOCK TABLES `sh_return_item` WRITE;
/*!40000 ALTER TABLE `sh_return_item` DISABLE KEYS */;
INSERT INTO `sh_return_item` VALUES (1,1,1,5,'admin','2026-10-15 15:07:57','C20261020'),(2,2,10,20,'admin','2026-10-15 15:07:57','C20261004'),(3,3,1,3,'admin','2026-10-15 15:07:57','C20261020');
/*!40000 ALTER TABLE `sh_return_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sh_shipment`
--

DROP TABLE IF EXISTS `sh_shipment`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sh_shipment` (
  `shipment_id` bigint NOT NULL AUTO_INCREMENT COMMENT '出货单ID',
  `shipment_no` varchar(30) NOT NULL COMMENT '出货单号',
  `order_id` bigint DEFAULT NULL COMMENT '来源订单ID（sd_order）',
  `customer_id` bigint NOT NULL COMMENT '客户ID',
  `ship_date` date DEFAULT NULL COMMENT '出货日期',
  `logistics_company` varchar(50) DEFAULT NULL COMMENT '物流公司',
  `logistics_no` varchar(50) DEFAULT NULL COMMENT '物流单号',
  `status` tinyint NOT NULL DEFAULT '0' COMMENT '状态：0待发货 1已发货 2已签收 9已取消',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `del_flag` char(1) NOT NULL DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  PRIMARY KEY (`shipment_id`),
  UNIQUE KEY `uk_shipment_no` (`shipment_no`),
  KEY `idx_order` (`order_id`),
  KEY `idx_customer` (`customer_id`),
  KEY `idx_status` (`status`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出货单主表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_shipment`
--

LOCK TABLES `sh_shipment` WRITE;
/*!40000 ALTER TABLE `sh_shipment` DISABLE KEYS */;
INSERT INTO `sh_shipment` VALUES (1,'SH2026001',1,1,'2026-10-21','顺丰速运','SF1234567890',2,'admin','2026-10-15 15:07:57','',NULL,NULL,'0'),(2,'SH2026002',3,3,'2026-10-10','中通快递','ZT9988776655',2,'admin','2026-10-15 15:07:57','',NULL,NULL,'0'),(3,'SH2026003',1,1,'2026-10-20',NULL,NULL,1,'seed','2026-10-15 15:07:58','',NULL,NULL,'0');
/*!40000 ALTER TABLE `sh_shipment` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sh_shipment_item`
--

DROP TABLE IF EXISTS `sh_shipment_item`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sh_shipment_item` (
  `item_id` bigint NOT NULL AUTO_INCREMENT COMMENT '明细ID',
  `shipment_id` bigint NOT NULL COMMENT '出货单ID',
  `sku_id` bigint NOT NULL COMMENT '成品SKU ID',
  `batch_no` varchar(30) NOT NULL DEFAULT '' COMMENT '批次号',
  `qty` int NOT NULL DEFAULT '0' COMMENT '出货数量',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`item_id`),
  UNIQUE KEY `uk_shipment_sku_batch` (`shipment_id`,`sku_id`,`batch_no`),
  KEY `idx_shipment` (`shipment_id`),
  KEY `idx_sku` (`sku_id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出货单明细';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sh_shipment_item`
--

LOCK TABLES `sh_shipment_item` WRITE;
/*!40000 ALTER TABLE `sh_shipment_item` DISABLE KEYS */;
INSERT INTO `sh_shipment_item` VALUES (1,1,1,'C20261020',70,'admin','2026-10-15 15:07:57'),(2,1,2,'C20261020',80,'admin','2026-10-15 15:07:57'),(3,1,3,'C20261020',120,'admin','2026-10-15 15:07:57'),(4,1,4,'C20261020',60,'admin','2026-10-15 15:07:57'),(5,2,10,'C20261004',500,'admin','2026-10-15 15:07:57'),(6,2,11,'C20261004',400,'admin','2026-10-15 15:07:57'),(7,2,12,'C20261004',300,'admin','2026-10-15 15:07:57'),(8,3,1,'C20261020',30,'seed','2026-10-15 15:07:58');
/*!40000 ALTER TABLE `sh_shipment_item` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `st_inventory`
--

DROP TABLE IF EXISTS `st_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `st_inventory` (
  `inv_id` bigint NOT NULL AUTO_INCREMENT COMMENT '结存ID',
  `item_type` tinyint NOT NULL COMMENT '库存对象类型：1物料 2成品SKU',
  `item_id` bigint NOT NULL COMMENT '对象ID（material_id 或 sku_id）',
  `batch_no` varchar(30) NOT NULL DEFAULT '' COMMENT '批次号（物料必管，成品可选）',
  `warehouse_id` bigint NOT NULL DEFAULT '1' COMMENT '仓库ID（MVP 固定 1）',
  `qty` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '结存数量',
  `safe_stock` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '安全库存（预留）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间（库存重算维护）',
  PRIMARY KEY (`inv_id`),
  UNIQUE KEY `uk_item_batch_wh` (`item_type`,`item_id`,`batch_no`,`warehouse_id`),
  KEY `idx_item` (`item_type`,`item_id`),
  CONSTRAINT `chk_inventory_nonnegative` CHECK ((`qty` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='库存结存';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `st_inventory`
--

LOCK TABLES `st_inventory` WRITE;
/*!40000 ALTER TABLE `st_inventory` DISABLE KEYS */;
INSERT INTO `st_inventory` VALUES (1,1,1,'F20260928',1,17.600,50.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(2,1,2,'F20260918',1,50.160,0.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(3,1,2,'F20260928',1,100.000,150.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(4,1,3,'',1,145.000,100.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(5,1,4,'',1,240.000,100.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(6,1,5,'',1,21.416,10.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(7,1,6,'F20260918',1,140.000,50.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(8,1,7,'',1,60.000,40.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(9,1,8,'',1,100.000,50.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(10,2,1,'C20261020',1,5.000,20.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(11,2,2,'C20261020',1,0.000,0.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(12,2,3,'C20261020',1,0.000,0.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(13,2,4,'C20261020',1,0.000,0.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(14,2,10,'C20261004',1,20.000,100.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(15,2,11,'C20261004',1,0.000,0.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(16,2,12,'C20261004',1,0.000,0.000,'admin','2026-10-15 15:07:56','2026-10-15 15:07:56'),(17,1,6,'F20261013',1,40.000,0.000,'seed','2026-10-15 15:07:58','2026-10-15 15:07:58');
/*!40000 ALTER TABLE `st_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `st_stock_txn`
--

DROP TABLE IF EXISTS `st_stock_txn`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `st_stock_txn` (
  `txn_id` bigint NOT NULL AUTO_INCREMENT COMMENT '流水ID',
  `txn_no` varchar(30) NOT NULL COMMENT '流水号',
  `txn_type` tinyint NOT NULL COMMENT '方向：1入库 2出库',
  `biz_type` tinyint NOT NULL COMMENT '业务类型：1采购入库 2生产领料 3完工入库 4销售出库 5盘盈 6盘亏 7退料',
  `item_type` tinyint NOT NULL COMMENT '库存对象类型：1物料 2成品SKU',
  `item_id` bigint NOT NULL COMMENT '对象ID',
  `batch_no` varchar(30) NOT NULL DEFAULT '' COMMENT '批次号',
  `qty` decimal(14,3) NOT NULL DEFAULT '0.000' COMMENT '数量（正数，方向看 txn_type）',
  `warehouse_id` bigint NOT NULL DEFAULT '1' COMMENT '仓库ID',
  `source_type` varchar(20) DEFAULT NULL COMMENT '来源单据类型：pu_order/pr_wo/sd_order/sh_return/stock_check',
  `source_id` bigint DEFAULT NULL COMMENT '来源单据ID',
  `txn_time` datetime DEFAULT NULL COMMENT '发生时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `source_line_id` bigint DEFAULT NULL COMMENT 'Source document line',
  `request_key` varchar(80) DEFAULT NULL COMMENT 'Idempotent action key',
  PRIMARY KEY (`txn_id`),
  UNIQUE KEY `uk_txn_no` (`txn_no`),
  KEY `idx_item` (`item_type`,`item_id`),
  KEY `idx_time` (`txn_time`),
  KEY `idx_source` (`source_type`,`source_id`),
  KEY `idx_source_line` (`source_type`,`source_id`,`source_line_id`),
  KEY `idx_request_key` (`request_key`),
  CONSTRAINT `chk_txn_positive` CHECK ((`qty` > 0))
) ENGINE=InnoDB AUTO_INCREMENT=41 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='出入库流水';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `st_stock_txn`
--

LOCK TABLES `st_stock_txn` WRITE;
/*!40000 ALTER TABLE `st_stock_txn` DISABLE KEYS */;
INSERT INTO `st_stock_txn` VALUES (1,'TXN2026091801',1,1,1,2,'F20260918',150.000,1,'pu_order',3,'2026-09-18 09:00:00','admin','2026-10-15 15:07:56','袜子/长裤弹力棉入库',6,NULL),(2,'TXN2026091802',1,1,1,6,'F20260918',140.000,1,'pu_order',3,'2026-09-18 09:05:00','admin','2026-10-15 15:07:56','背心速干锦纶入库',7,NULL),(3,'TXN2026092801',1,1,1,1,'F20260928',320.000,1,'pu_order',1,'2026-09-28 09:00:00','admin','2026-10-15 15:07:56','T恤网眼布入库',1,NULL),(4,'TXN2026092802',1,1,1,2,'F20260928',400.000,1,'pu_order',1,'2026-09-28 09:03:00','admin','2026-10-15 15:07:56','长裤弹力棉入库',2,NULL),(5,'TXN2026092803',1,1,1,3,'',400.000,1,'pu_order',2,'2026-09-28 10:00:00','admin','2026-10-15 15:07:56','树脂拉链入库',3,NULL),(6,'TXN2026092804',1,1,1,4,'',600.000,1,'pu_order',2,'2026-09-28 10:02:00','admin','2026-10-15 15:07:56','织唛主标入库',4,NULL),(7,'TXN2026092805',1,1,1,5,'',50.000,1,'pu_order',2,'2026-09-28 10:05:00','admin','2026-10-15 15:07:56','缝纫线入库',5,NULL),(8,'TXN2026100201',1,1,1,7,'',180.000,1,'pu_order',4,'2026-10-02 11:00:00','admin','2026-10-15 15:07:56','氨纶弹力布入库',8,NULL),(9,'TXN2026100202',1,1,1,8,'',200.000,1,'pu_order',4,'2026-10-02 11:02:00','admin','2026-10-15 15:07:56','洗水标入库',9,NULL),(10,'TXN2026092101',2,2,1,2,'F20260918',99.840,1,'pr_wo',3,'2026-09-21 08:30:00','admin','2026-10-15 15:07:56','WO3 袜子领料-弹力棉',7,NULL),(11,'TXN2026092102',2,2,1,5,'',12.240,1,'pr_wo',3,'2026-09-21 08:32:00','admin','2026-10-15 15:07:56','WO3 袜子领料-缝纫线',8,NULL),(12,'TXN2026100901',2,2,1,7,'',120.000,1,'pr_wo',4,'2026-10-09 08:30:00','admin','2026-10-15 15:07:56','WO4 紧身裤领料-氨纶布',10,NULL),(13,'TXN2026100902',2,2,1,8,'',100.000,1,'pr_wo',4,'2026-10-09 08:32:00','admin','2026-10-15 15:07:56','WO4 紧身裤领料-洗水标',11,NULL),(14,'TXN2026101301',2,2,1,1,'F20260928',302.400,1,'pr_wo',1,'2026-10-13 08:30:00','admin','2026-10-15 15:07:56','WO1 T恤领料-网眼布',1,NULL),(15,'TXN2026101302',2,2,1,4,'',360.000,1,'pr_wo',1,'2026-10-13 08:32:00','admin','2026-10-15 15:07:56','WO1 T恤领料-织唛',2,NULL),(16,'TXN2026101303',2,2,1,5,'',7.344,1,'pr_wo',1,'2026-10-13 08:34:00','admin','2026-10-15 15:07:56','WO1 T恤领料-缝纫线',3,NULL),(17,'TXN2026101304',2,2,1,5,'',3.000,1,'pr_wo',4,'2026-10-13 09:00:00','admin','2026-10-15 15:07:56','WO4 紧身裤领料-缝纫线',9,NULL),(18,'TXN2026101501',2,2,1,2,'F20260928',300.000,1,'pr_wo',2,'2026-10-15 08:30:00','admin','2026-10-15 15:07:56','WO2 长裤领料-弹力棉',4,NULL),(19,'TXN2026101502',2,2,1,3,'',250.000,1,'pr_wo',2,'2026-10-15 08:32:00','admin','2026-10-15 15:07:56','WO2 长裤领料-拉链',5,NULL),(20,'TXN2026101503',2,2,1,5,'',7.000,1,'pr_wo',2,'2026-10-15 08:34:00','admin','2026-10-15 15:07:56','WO2 长裤领料-缝纫线',6,NULL),(21,'TXN2026100401',1,3,2,10,'C20261004',500.000,1,'pr_wo',3,'2026-10-04 17:00:00','admin','2026-10-15 15:07:56','袜子完工入库-BLK',7,NULL),(22,'TXN2026100402',1,3,2,11,'C20261004',400.000,1,'pr_wo',3,'2026-10-04 17:01:00','admin','2026-10-15 15:07:56','袜子完工入库-WHT',8,NULL),(23,'TXN2026100403',1,3,2,12,'C20261004',300.000,1,'pr_wo',3,'2026-10-04 17:02:00','admin','2026-10-15 15:07:56','袜子完工入库-GRY',9,NULL),(24,'TXN2026102001',1,3,2,1,'C20261020',100.000,1,'pr_wo',1,'2026-10-20 16:00:00','admin','2026-10-15 15:07:56','T恤完工入库-BLK-M',1,NULL),(25,'TXN2026102002',1,3,2,2,'C20261020',80.000,1,'pr_wo',1,'2026-10-20 16:01:00','admin','2026-10-15 15:07:56','T恤完工入库-BLK-L',2,NULL),(26,'TXN2026102003',1,3,2,3,'C20261020',120.000,1,'pr_wo',1,'2026-10-20 16:02:00','admin','2026-10-15 15:07:56','T恤完工入库-WHT-M',3,NULL),(27,'TXN2026102004',1,3,2,4,'C20261020',60.000,1,'pr_wo',1,'2026-10-20 16:03:00','admin','2026-10-15 15:07:56','T恤完工入库-WHT-L',4,NULL),(28,'TXN2026101001',2,4,2,10,'C20261004',500.000,1,'sh_shipment',2,'2026-10-10 10:00:00','admin','2026-10-15 15:07:56','SO3 袜子出库-BLK',5,NULL),(29,'TXN2026101002',2,4,2,11,'C20261004',400.000,1,'sh_shipment',2,'2026-10-10 10:01:00','admin','2026-10-15 15:07:56','SO3 袜子出库-WHT',6,NULL),(30,'TXN2026101003',2,4,2,12,'C20261004',300.000,1,'sh_shipment',2,'2026-10-10 10:02:00','admin','2026-10-15 15:07:56','SO3 袜子出库-GRY',7,NULL),(31,'TXN2026102101',2,4,2,1,'C20261020',70.000,1,'sh_shipment',1,'2026-10-21 09:00:00','admin','2026-10-15 15:07:56','SO1 T恤出库-BLK-M',1,NULL),(32,'TXN2026102102',2,4,2,2,'C20261020',80.000,1,'sh_shipment',1,'2026-10-21 09:01:00','admin','2026-10-15 15:07:56','SO1 T恤出库-BLK-L',2,NULL),(33,'TXN2026102103',2,4,2,3,'C20261020',120.000,1,'sh_shipment',1,'2026-10-21 09:02:00','admin','2026-10-15 15:07:56','SO1 T恤出库-WHT-M',3,NULL),(34,'TXN2026102104',2,4,2,4,'C20261020',60.000,1,'sh_shipment',1,'2026-10-21 09:03:00','admin','2026-10-15 15:07:56','SO1 T恤出库-WHT-L',4,NULL),(35,'TXN2026101201',1,7,2,10,'C20261004',20.000,1,'sh_return',2,'2026-10-12 15:00:00','admin','2026-10-15 15:07:56','袜子退货入库-颜色不符',2,NULL),(36,'TXN2026102301',1,7,2,1,'C20261020',5.000,1,'sh_return',1,'2026-10-23 15:00:00','admin','2026-10-15 15:07:56','T恤退货入库-尺码偏小',1,NULL),(37,'TXN2026101401',2,6,1,3,'',5.000,1,'stock_check',NULL,'2026-10-14 18:00:00','admin','2026-10-15 15:07:56','拉链盘亏',NULL,NULL),(38,'TXN2026101402',1,5,1,5,'',1.000,1,'stock_check',NULL,'2026-10-14 18:01:00','admin','2026-10-15 15:07:56','缝纫线盘盈',NULL,NULL),(39,'TXN2026101305',1,1,1,6,'F20261013',40.000,1,'pu_order',6,'2026-10-13 10:00:00','seed','2026-10-15 15:07:58',NULL,11,NULL),(40,'TXN2026102005',2,4,2,1,'C20261020',30.000,1,'sh_shipment',3,'2026-10-20 17:30:00','seed','2026-10-15 15:07:58',NULL,8,NULL);
/*!40000 ALTER TABLE `st_stock_txn` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_bill_frozen`
--

DROP TABLE IF EXISTS `sys_bill_frozen`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_bill_frozen` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `class_name` varchar(50) NOT NULL COMMENT '类名',
  `object_id` bigint NOT NULL COMMENT '对象ID',
  `frozen_status` smallint NOT NULL COMMENT '冻结时状态',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=COMPACT COMMENT='表单对象冻结表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_bill_frozen`
--

LOCK TABLES `sys_bill_frozen` WRITE;
/*!40000 ALTER TABLE `sys_bill_frozen` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_bill_frozen` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_billcode_rule`
--

DROP TABLE IF EXISTS `sys_billcode_rule`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_billcode_rule` (
  `id` bigint NOT NULL AUTO_INCREMENT COMMENT 'ID',
  `bill_code` varchar(30) NOT NULL COMMENT '单码',
  `bill_name` varchar(30) NOT NULL COMMENT '单名',
  `disable_rule` char(1) NOT NULL DEFAULT '0' COMMENT '启用规则否',
  `disable_code` char(1) NOT NULL DEFAULT '0' COMMENT '启用单码否',
  `disable_prefix` char(1) NOT NULL DEFAULT '1' COMMENT '启用前缀否',
  `prefix_chars` varchar(6) DEFAULT NULL COMMENT '前缀字符',
  `disable_period` char(1) NOT NULL DEFAULT '0' COMMENT '启用时期否',
  `period_pattern` varchar(10) NOT NULL DEFAULT 'YYMMDD' COMMENT '时期样式',
  `disable_number` char(1) NOT NULL DEFAULT '0' COMMENT '启用数字否',
  `number_size` smallint NOT NULL DEFAULT '4' COMMENT '数字位数',
  `last_date` varchar(10) NOT NULL COMMENT '最后日期',
  `next_number` smallint DEFAULT '1' COMMENT '后数',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=821 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='单据规则表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_billcode_rule`
--

LOCK TABLES `sys_billcode_rule` WRITE;
/*!40000 ALTER TABLE `sys_billcode_rule` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_billcode_rule` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_config`
--

DROP TABLE IF EXISTS `sys_config`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_config` (
  `config_id` int NOT NULL AUTO_INCREMENT COMMENT '参数主键',
  `config_name` varchar(100) DEFAULT '' COMMENT '参数名称',
  `config_key` varchar(100) DEFAULT '' COMMENT '参数键名',
  `config_value` varchar(500) DEFAULT '' COMMENT '参数键值',
  `config_type` char(1) DEFAULT 'N' COMMENT '系统内置（Y是 N否）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`config_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=101 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='参数配置表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_config`
--

LOCK TABLES `sys_config` WRITE;
/*!40000 ALTER TABLE `sys_config` DISABLE KEYS */;
INSERT INTO `sys_config` VALUES (1,'演示库验证码','sys.account.captchaEnabled','false','Y','bootstrap','2026-10-15 15:07:59','',NULL,NULL),(2,'登录黑名单','sys.login.blackIPList','','Y','bootstrap','2026-10-15 15:07:59','',NULL,NULL);
/*!40000 ALTER TABLE `sys_config` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dept`
--

DROP TABLE IF EXISTS `sys_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dept` (
  `comp_id` bigint NOT NULL DEFAULT '2' COMMENT '公司id',
  `dept_id` bigint NOT NULL AUTO_INCREMENT COMMENT '部门id',
  `dept_type` int DEFAULT NULL COMMENT '部门类型',
  `parent_id` bigint DEFAULT '0' COMMENT '父部门id',
  `ancestors` varchar(50) DEFAULT '' COMMENT '祖级列表',
  `dept_name` varchar(30) DEFAULT '' COMMENT '部门名称',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `leader` varchar(20) DEFAULT NULL COMMENT '负责人',
  `phone` varchar(11) DEFAULT NULL COMMENT '联系电话',
  `email` varchar(50) DEFAULT NULL COMMENT '邮箱',
  `status` char(1) DEFAULT '0' COMMENT '部门状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `xleft` smallint DEFAULT NULL COMMENT 'x坐标',
  `ytop` smallint DEFAULT NULL COMMENT 'y坐标',
  `rheight` smallint DEFAULT NULL COMMENT '高',
  `rwidth` smallint DEFAULT NULL COMMENT '宽',
  PRIMARY KEY (`dept_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=250 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='部门表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dept`
--

LOCK TABLES `sys_dept` WRITE;
/*!40000 ALTER TABLE `sys_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_data`
--

DROP TABLE IF EXISTS `sys_dict_data`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_data` (
  `dict_code` bigint NOT NULL AUTO_INCREMENT COMMENT '字典编码',
  `dict_sort` int DEFAULT '0' COMMENT '字典排序',
  `dict_label` varchar(100) DEFAULT '' COMMENT '字典标签',
  `dict_value` varchar(100) DEFAULT '' COMMENT '字典键值',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `css_class` varchar(100) DEFAULT NULL COMMENT '样式属性（其他样式扩展）',
  `list_class` varchar(100) DEFAULT NULL COMMENT '表格回显样式',
  `is_default` char(1) DEFAULT 'N' COMMENT '是否默认（Y是 N否）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_code`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1094 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='字典数据表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_data`
--

LOCK TABLES `sys_dict_data` WRITE;
/*!40000 ALTER TABLE `sys_dict_data` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict_data` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_dict_type`
--

DROP TABLE IF EXISTS `sys_dict_type`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_dict_type` (
  `dict_id` bigint NOT NULL AUTO_INCREMENT COMMENT '字典主键',
  `dict_name` varchar(100) DEFAULT '' COMMENT '字典名称',
  `dict_type` varchar(100) DEFAULT '' COMMENT '字典类型',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`dict_id`) USING BTREE,
  UNIQUE KEY `dict_type` (`dict_type`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=140 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='字典类型表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_dict_type`
--

LOCK TABLES `sys_dict_type` WRITE;
/*!40000 ALTER TABLE `sys_dict_type` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_dict_type` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job`
--

DROP TABLE IF EXISTS `sys_job`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job` (
  `job_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务ID',
  `job_name` varchar(64) NOT NULL DEFAULT '' COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL DEFAULT 'DEFAULT' COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `cron_expression` varchar(255) DEFAULT '' COMMENT 'cron执行表达式',
  `misfire_policy` varchar(20) DEFAULT '3' COMMENT '计划执行错误策略（1立即执行 2执行一次 3放弃执行）',
  `concurrent` char(1) DEFAULT '1' COMMENT '是否并发执行（0允许 1禁止）',
  `status` char(1) DEFAULT '0' COMMENT '状态（0正常 1暂停）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注信息',
  PRIMARY KEY (`job_id`,`job_name`,`job_group`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='定时任务调度表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job`
--

LOCK TABLES `sys_job` WRITE;
/*!40000 ALTER TABLE `sys_job` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_job_log`
--

DROP TABLE IF EXISTS `sys_job_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_job_log` (
  `job_log_id` bigint NOT NULL AUTO_INCREMENT COMMENT '任务日志ID',
  `job_name` varchar(64) NOT NULL COMMENT '任务名称',
  `job_group` varchar(64) NOT NULL COMMENT '任务组名',
  `invoke_target` varchar(500) NOT NULL COMMENT '调用目标字符串',
  `job_message` varchar(500) DEFAULT NULL COMMENT '日志信息',
  `status` char(1) DEFAULT '0' COMMENT '执行状态（0正常 1失败）',
  `exception_info` varchar(2000) DEFAULT '' COMMENT '异常信息',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`job_log_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='定时任务调度日志表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_job_log`
--

LOCK TABLES `sys_job_log` WRITE;
/*!40000 ALTER TABLE `sys_job_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_job_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_logininfor`
--

DROP TABLE IF EXISTS `sys_logininfor`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_logininfor` (
  `info_id` bigint NOT NULL AUTO_INCREMENT COMMENT '访问ID',
  `user_name` varchar(50) DEFAULT '' COMMENT '用户账号',
  `ipaddr` varchar(128) DEFAULT '' COMMENT '登录IP地址',
  `login_location` varchar(255) DEFAULT '' COMMENT '登录地点',
  `browser` varchar(50) DEFAULT '' COMMENT '浏览器类型',
  `os` varchar(50) DEFAULT '' COMMENT '操作系统',
  `status` char(1) DEFAULT '0' COMMENT '登录状态（0成功 1失败）',
  `msg` varchar(255) DEFAULT '' COMMENT '提示消息',
  `login_time` datetime DEFAULT NULL COMMENT '访问时间',
  PRIMARY KEY (`info_id`) USING BTREE,
  KEY `idx_sys_logininfor_s` (`status`) USING BTREE,
  KEY `idx_sys_logininfor_lt` (`login_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=1328 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='系统访问记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_logininfor`
--

LOCK TABLES `sys_logininfor` WRITE;
/*!40000 ALTER TABLE `sys_logininfor` DISABLE KEYS */;
INSERT INTO `sys_logininfor` VALUES (1319,'admin','127.0.0.1','内网IP','Chrome 15','Windows 10','1','用户不存在/密码错误','2026-10-15 15:13:30'),(1320,'admin','127.0.0.1','内网IP','Chrome 15','Windows 10','1','用户不存在/密码错误','2026-10-15 15:16:56'),(1321,'admin','127.0.0.1','内网IP','Chrome 15','Windows 10','1','用户不存在/密码错误','2026-10-15 15:16:58'),(1322,'admin','127.0.0.1','内网IP','Chrome 15','Windows 10','1','用户不存在/密码错误','2026-10-15 15:16:58'),(1323,'admin','127.0.0.1','内网IP','Chrome 15','Windows 10','0','登录成功','2026-10-15 15:17:22'),(1324,'hr','127.0.0.1','内网IP','Chrome 15','Windows 10','0','登录成功','2026-10-15 15:19:14'),(1325,'admin','127.0.0.1','内网IP','Mozilla','Windows 10','0','登录成功','2026-10-15 15:20:34'),(1326,'admin','127.0.0.1','内网IP','Chrome 15','Windows 10','0','登录成功','2026-10-15 15:33:49'),(1327,'hr','127.0.0.1','内网IP','Chrome 15','Windows 10','0','登录成功','2026-10-15 15:34:28');
/*!40000 ALTER TABLE `sys_logininfor` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_menu`
--

DROP TABLE IF EXISTS `sys_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_menu` (
  `menu_id` bigint NOT NULL AUTO_INCREMENT COMMENT '菜单ID',
  `menu_name` varchar(50) NOT NULL COMMENT '菜单名称',
  `parent_id` bigint DEFAULT '0' COMMENT '父菜单ID',
  `order_num` int DEFAULT '0' COMMENT '显示顺序',
  `path` varchar(200) DEFAULT '' COMMENT '路由地址',
  `component` varchar(255) DEFAULT NULL COMMENT '组件路径',
  `query` varchar(255) DEFAULT NULL COMMENT '路由参数',
  `is_frame` int DEFAULT '1' COMMENT '是否为外链（0是 1否）',
  `is_cache` int DEFAULT '0' COMMENT '是否缓存（0缓存 1不缓存）',
  `menu_type` char(1) DEFAULT '' COMMENT '菜单类型（M目录 C菜单 F按钮）',
  `visible` char(1) DEFAULT '0' COMMENT '菜单状态（0显示 1隐藏）',
  `status` char(1) DEFAULT '0' COMMENT '菜单状态（0正常 1停用）',
  `perms` text COMMENT '权限标识',
  `icon` varchar(100) DEFAULT '#' COMMENT '菜单图标',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT '' COMMENT '备注',
  `source_table_id` int DEFAULT NULL COMMENT '原始表id',
  PRIMARY KEY (`menu_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=90079 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='菜单权限表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_menu`
--

LOCK TABLES `sys_menu` WRITE;
/*!40000 ALTER TABLE `sys_menu` DISABLE KEYS */;
INSERT INTO `sys_menu` VALUES (90000,'海岚 ERP',0,20,'erp',NULL,NULL,1,0,'M','0','0','','dashboard','admin','2026-10-15 15:07:58','',NULL,'ERP 权限目录',NULL),(90001,'工作台',90000,1,'dashboard','erp/dashboard',NULL,1,0,'C','0','0','dashboard:overview:list','dashboard','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90002,'销售订单',90000,2,'orders','erp/orders',NULL,1,0,'C','0','0','sd:order:list','list','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90003,'订单详情',90002,1,'#',NULL,NULL,1,0,'F','0','0','sd:order:query','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90004,'新建订单',90002,2,'#',NULL,NULL,1,0,'F','0','0','sd:order:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90005,'确认订单',90002,3,'#',NULL,NULL,1,0,'F','0','0','sd:order:confirm','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90006,'缺料建议',90000,3,'mrp','erp/mrp',NULL,1,0,'C','0','0','pu:suggestion:list','chart','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90007,'库存结存',90000,4,'inventory','erp/inventory',NULL,1,0,'C','0','0','st:inventory:list','box','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90008,'采购订单',90000,5,'purchases','erp/purchases',NULL,1,0,'C','0','0','pu:purchaseOrder:list','shopping','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90009,'采购详情',90008,1,'#',NULL,NULL,1,0,'F','0','0','pu:purchaseOrder:query','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90010,'新建采购',90008,2,'#',NULL,NULL,1,0,'F','0','0','pu:purchaseOrder:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90011,'确认采购',90008,3,'#',NULL,NULL,1,0,'F','0','0','pu:purchaseOrder:confirm','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90012,'采购收货',90008,4,'#',NULL,NULL,1,0,'F','0','0','pu:purchaseOrder:receive','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90013,'款式',90000,6,'styles',NULL,NULL,1,0,'C','0','0','md:style:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90014,'SKU',90000,7,'skus',NULL,NULL,1,0,'C','0','0','md:sku:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90015,'物料',90000,8,'materials',NULL,NULL,1,0,'C','0','0','md:material:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90016,'客户',90000,9,'customers',NULL,NULL,1,0,'C','0','0','md:customer:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90017,'供应商',90000,10,'suppliers',NULL,NULL,1,0,'C','0','0','md:supplier:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90018,'工序',90000,11,'processes',NULL,NULL,1,0,'C','0','0','md:process:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90019,'颜色',90000,12,'colors',NULL,NULL,1,0,'C','0','0','md:color:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90020,'尺码',90000,13,'sizes',NULL,NULL,1,0,'C','0','0','md:size:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90021,'品类',90000,14,'categories',NULL,NULL,1,0,'C','0','0','md:category:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90022,'员工',90000,15,'employees',NULL,NULL,1,0,'C','0','0','hr:employee:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90023,'计件工资',90000,16,'pieceWage',NULL,NULL,1,0,'C','0','0','hr:pieceWage:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90024,'工资单',90000,17,'salarySlip',NULL,NULL,1,0,'C','0','0','hr:salarySlip:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90025,'报价单',90000,18,'quotations',NULL,NULL,1,0,'C','0','0','sd:quotation:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90026,'生产工单',90000,19,'workOrders',NULL,NULL,1,0,'C','0','0','pr:workOrder:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90027,'工单详情',90026,1,'#',NULL,NULL,1,0,'F','0','0','pr:workOrder:query','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90028,'新建工单',90026,2,'#',NULL,NULL,1,0,'F','0','0','pr:workOrder:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90029,'领料开工',90026,3,'#',NULL,NULL,1,0,'F','0','0','pr:workOrder:issue','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90030,'报工',90026,4,'#',NULL,NULL,1,0,'F','0','0','pr:process:report','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90031,'完工入库',90026,5,'#',NULL,NULL,1,0,'F','0','0','pr:workOrder:finish','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90032,'库存流水',90000,20,'transactions',NULL,NULL,1,0,'C','0','0','st:txn:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90033,'出货单',90000,21,'shipments',NULL,NULL,1,0,'C','0','0','sh:shipment:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90034,'退货单',90000,22,'returns',NULL,NULL,1,0,'C','0','0','sh:return:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90035,'标准成本',90000,23,'settlements',NULL,NULL,1,0,'C','0','0','fn:settlement:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90036,'交付准时率',90000,24,'ontime',NULL,NULL,1,0,'C','0','0','dashboard:ontime:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90037,'款式成本',90000,25,'styleCost',NULL,NULL,1,0,'C','0','0','dashboard:styleCost:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90038,'出货详情',90033,1,'#',NULL,NULL,1,0,'F','0','0','sh:shipment:query','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90039,'新建出货单',90033,2,'#',NULL,NULL,1,0,'F','0','0','sh:shipment:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90040,'确认发货',90033,3,'#',NULL,NULL,1,0,'F','0','0','sh:shipment:ship','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90041,'登记签收',90033,4,'#',NULL,NULL,1,0,'F','0','0','sh:shipment:sign','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90042,'退货详情',90034,1,'#',NULL,NULL,1,0,'F','0','0','sh:return:query','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90043,'登记退货',90034,2,'#',NULL,NULL,1,0,'F','0','0','sh:return:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90044,'处理退货',90034,3,'#',NULL,NULL,1,0,'F','0','0','sh:return:handle','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90045,'成本详情',90035,1,'#',NULL,NULL,1,0,'F','0','0','fn:settlement:query','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90046,'成本试算',90035,2,'#',NULL,NULL,1,0,'F','0','0','fn:settlement:calc','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90047,'确认结算',90035,3,'#',NULL,NULL,1,0,'F','0','0','fn:settlement:confirm','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90048,'生成工资单',90024,1,'#',NULL,NULL,1,0,'F','0','0','hr:salarySlip:generate','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90049,'确认工资单',90024,2,'#',NULL,NULL,1,0,'F','0','0','hr:salarySlip:confirm','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90050,'报价详情',90025,1,'#',NULL,NULL,1,0,'F','0','0','sd:quotation:query','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90051,'结算生成报价',90025,2,'#',NULL,NULL,1,0,'F','0','0','sd:quotation:fromSettlement','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90052,'新增品类',90021,1,'#',NULL,NULL,1,0,'F','0','0','md:category:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90053,'编辑品类',90021,2,'#',NULL,NULL,1,0,'F','0','0','md:category:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90054,'新增颜色',90019,1,'#',NULL,NULL,1,0,'F','0','0','md:color:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90055,'编辑颜色',90019,2,'#',NULL,NULL,1,0,'F','0','0','md:color:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90056,'新增尺码',90020,1,'#',NULL,NULL,1,0,'F','0','0','md:size:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90057,'编辑尺码',90020,2,'#',NULL,NULL,1,0,'F','0','0','md:size:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90058,'新增款式',90013,1,'#',NULL,NULL,1,0,'F','0','0','md:style:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90059,'编辑款式',90013,2,'#',NULL,NULL,1,0,'F','0','0','md:style:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90060,'新增物料',90015,1,'#',NULL,NULL,1,0,'F','0','0','md:material:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90061,'编辑物料',90015,2,'#',NULL,NULL,1,0,'F','0','0','md:material:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90062,'新增工序',90018,1,'#',NULL,NULL,1,0,'F','0','0','md:process:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90063,'编辑工序',90018,2,'#',NULL,NULL,1,0,'F','0','0','md:process:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90064,'新增客户',90016,1,'#',NULL,NULL,1,0,'F','0','0','md:customer:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90065,'编辑客户',90016,2,'#',NULL,NULL,1,0,'F','0','0','md:customer:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90066,'新增供应商',90017,1,'#',NULL,NULL,1,0,'F','0','0','md:supplier:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90067,'编辑供应商',90017,2,'#',NULL,NULL,1,0,'F','0','0','md:supplier:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90068,'生成 SKU',90014,1,'#',NULL,NULL,1,0,'F','0','0','md:sku:generate','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90069,'编辑 SKU',90014,2,'#',NULL,NULL,1,0,'F','0','0','md:sku:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90070,'业务预警',90001,1,'#',NULL,NULL,1,0,'F','0','0','dashboard:alerts:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90071,'预警分析',90001,2,'#',NULL,NULL,1,0,'F','0','0','dashboard:alerts:explain','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90072,'库存盘点调整',90007,1,'#',NULL,NULL,1,0,'F','0','0','st:txn:adjust','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90073,'缺料生成采购',90006,1,'#',NULL,NULL,1,0,'F','0','0','pu:suggestion:generate','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90074,'接受报价',90025,3,'#',NULL,NULL,1,0,'F','0','0','sd:quotation:accept','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90075,'报价失效',90025,4,'#',NULL,NULL,1,0,'F','0','0','sd:quotation:expire','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90076,'新增员工',90022,1,'#',NULL,NULL,1,0,'F','0','0','hr:employee:add','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90077,'编辑员工',90022,2,'#',NULL,NULL,1,0,'F','0','0','hr:employee:edit','#','admin','2026-10-15 15:07:58','',NULL,'',NULL),(90078,'看板缺料汇总',90001,3,'#',NULL,NULL,1,0,'F','0','0','dashboard:mrp:list','#','admin','2026-10-15 15:07:58','',NULL,'',NULL);
/*!40000 ALTER TABLE `sys_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_notice`
--

DROP TABLE IF EXISTS `sys_notice`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_notice` (
  `notice_id` int NOT NULL AUTO_INCREMENT COMMENT '公告ID',
  `notice_title` varchar(50) NOT NULL COMMENT '公告标题',
  `notice_type` char(1) NOT NULL COMMENT '公告类型（1通知 2公告）',
  `notice_content` longblob COMMENT '公告内容',
  `status` char(1) DEFAULT '0' COMMENT '公告状态（0正常 1关闭）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(255) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`notice_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='通知公告表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_notice`
--

LOCK TABLES `sys_notice` WRITE;
/*!40000 ALTER TABLE `sys_notice` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_notice` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_oper_log`
--

DROP TABLE IF EXISTS `sys_oper_log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_oper_log` (
  `oper_id` bigint NOT NULL AUTO_INCREMENT COMMENT '日志主键',
  `title` varchar(50) DEFAULT '' COMMENT '模块标题',
  `business_type` int DEFAULT '0' COMMENT '业务类型（0其它 1新增 2修改 3删除）',
  `method` varchar(200) DEFAULT '' COMMENT '方法名称',
  `request_method` varchar(10) DEFAULT '' COMMENT '请求方式',
  `operator_type` int DEFAULT '0' COMMENT '操作类别（0其它 1后台用户 2手机端用户）',
  `oper_name` varchar(50) DEFAULT '' COMMENT '操作人员',
  `dept_name` varchar(50) DEFAULT '' COMMENT '部门名称',
  `oper_url` varchar(255) DEFAULT '' COMMENT '请求URL',
  `oper_ip` varchar(128) DEFAULT '' COMMENT '主机地址',
  `oper_location` varchar(255) DEFAULT '' COMMENT '操作地点',
  `oper_param` varchar(2000) DEFAULT '' COMMENT '请求参数',
  `json_result` varchar(2000) DEFAULT '' COMMENT '返回参数',
  `status` int DEFAULT '0' COMMENT '操作状态（0正常 1异常）',
  `error_msg` varchar(2000) DEFAULT '' COMMENT '错误消息',
  `oper_time` datetime DEFAULT NULL COMMENT '操作时间',
  `cost_time` bigint DEFAULT '0' COMMENT '消耗时间',
  PRIMARY KEY (`oper_id`) USING BTREE,
  KEY `idx_sys_oper_log_bt` (`business_type`) USING BTREE,
  KEY `idx_sys_oper_log_s` (`status`) USING BTREE,
  KEY `idx_sys_oper_log_ot` (`oper_time`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=4859 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='操作日志记录';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_oper_log`
--

LOCK TABLES `sys_oper_log` WRITE;
/*!40000 ALTER TABLE `sys_oper_log` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_oper_log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_post`
--

DROP TABLE IF EXISTS `sys_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_post` (
  `post_id` bigint NOT NULL AUTO_INCREMENT COMMENT '岗位ID',
  `post_code` varchar(64) NOT NULL COMMENT '岗位编码',
  `post_name` varchar(50) NOT NULL COMMENT '岗位名称',
  `post_sort` int NOT NULL COMMENT '显示顺序',
  `status` char(1) NOT NULL COMMENT '状态（0正常 1停用）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`post_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='岗位信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_post`
--

LOCK TABLES `sys_post` WRITE;
/*!40000 ALTER TABLE `sys_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role`
--

DROP TABLE IF EXISTS `sys_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role` (
  `role_id` bigint NOT NULL AUTO_INCREMENT COMMENT '角色ID',
  `role_name` varchar(30) NOT NULL COMMENT '角色名称',
  `role_key` varchar(100) NOT NULL COMMENT '角色权限字符串',
  `role_sort` int NOT NULL COMMENT '显示顺序',
  `data_scope` char(1) DEFAULT '1' COMMENT '数据范围（1：全部数据权限 2：自定数据权限 3：本部门数据权限 4：本部门及以下数据权限）',
  `menu_check_strictly` tinyint(1) DEFAULT '1' COMMENT '菜单树选择项是否关联显示',
  `dept_check_strictly` tinyint(1) DEFAULT '1' COMMENT '部门树选择项是否关联显示',
  `status` char(1) NOT NULL COMMENT '角色状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`role_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=29 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role`
--

LOCK TABLES `sys_role` WRITE;
/*!40000 ALTER TABLE `sys_role` DISABLE KEYS */;
INSERT INTO `sys_role` VALUES (1,'管理员','admin',1,'1',1,1,'0','0','bootstrap','2026-10-15 15:07:59','',NULL,NULL),(22,'销售','sales',20,'1',1,1,'0','0','admin','2026-10-15 15:07:58','',NULL,NULL),(23,'财务','finance',21,'1',1,1,'0','0','admin','2026-10-15 15:07:58','',NULL,NULL),(24,'计划员','planner',30,'1',1,1,'0','0','admin','2026-10-15 15:07:58','',NULL,NULL),(25,'生产主管','production',31,'1',1,1,'0','0','admin','2026-10-15 15:07:58','',NULL,NULL),(26,'仓管员','wh',32,'1',1,1,'0','0','admin','2026-10-15 15:07:58','',NULL,NULL),(27,'人事员','hr',33,'1',1,1,'0','0','admin','2026-10-15 15:07:58','',NULL,NULL),(28,'厂长','boss',34,'1',1,1,'0','0','admin','2026-10-15 15:07:58','',NULL,NULL);
/*!40000 ALTER TABLE `sys_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_dept`
--

DROP TABLE IF EXISTS `sys_role_dept`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_dept` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `dept_id` bigint NOT NULL COMMENT '部门ID',
  PRIMARY KEY (`role_id`,`dept_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色和部门关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_dept`
--

LOCK TABLES `sys_role_dept` WRITE;
/*!40000 ALTER TABLE `sys_role_dept` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_role_dept` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_role_menu`
--

DROP TABLE IF EXISTS `sys_role_menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_role_menu` (
  `role_id` bigint NOT NULL COMMENT '角色ID',
  `menu_id` bigint NOT NULL COMMENT '菜单ID',
  PRIMARY KEY (`role_id`,`menu_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='角色和菜单关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_role_menu`
--

LOCK TABLES `sys_role_menu` WRITE;
/*!40000 ALTER TABLE `sys_role_menu` DISABLE KEYS */;
INSERT INTO `sys_role_menu` VALUES (22,90000),(22,90001),(22,90002),(22,90003),(22,90004),(22,90005),(22,90016),(22,90025),(22,90033),(22,90034),(22,90036),(22,90038),(22,90039),(22,90040),(22,90041),(22,90042),(22,90043),(22,90044),(22,90050),(22,90051),(22,90064),(22,90065),(22,90070),(22,90071),(22,90074),(22,90075),(23,90000),(23,90001),(23,90002),(23,90003),(23,90025),(23,90033),(23,90034),(23,90035),(23,90036),(23,90037),(23,90038),(23,90042),(23,90045),(23,90046),(23,90047),(23,90050),(23,90051),(23,90070),(23,90071),(24,90000),(24,90001),(24,90002),(24,90003),(24,90006),(24,90008),(24,90009),(24,90010),(24,90011),(24,90012),(24,90013),(24,90014),(24,90015),(24,90017),(24,90026),(24,90027),(24,90028),(24,90033),(24,90034),(24,90070),(24,90071),(24,90073),(24,90078),(25,90000),(25,90001),(25,90002),(25,90003),(25,90007),(25,90013),(25,90014),(25,90015),(25,90018),(25,90026),(25,90027),(25,90028),(25,90029),(25,90030),(25,90031),(25,90032),(25,90070),(25,90071),(26,90000),(26,90001),(26,90007),(26,90008),(26,90009),(26,90012),(26,90026),(26,90027),(26,90029),(26,90031),(26,90032),(26,90033),(26,90034),(26,90038),(26,90039),(26,90040),(26,90041),(26,90042),(26,90043),(26,90044),(26,90070),(26,90071),(26,90072),(27,90000),(27,90001),(27,90022),(27,90023),(27,90024),(27,90048),(27,90049),(27,90076),(27,90077),(28,90000),(28,90001),(28,90002),(28,90003),(28,90006),(28,90007),(28,90008),(28,90009),(28,90013),(28,90014),(28,90015),(28,90016),(28,90017),(28,90025),(28,90026),(28,90027),(28,90032),(28,90033),(28,90034),(28,90035),(28,90036),(28,90037),(28,90038),(28,90042),(28,90045),(28,90050),(28,90070),(28,90071),(28,90078);
/*!40000 ALTER TABLE `sys_role_menu` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user`
--

DROP TABLE IF EXISTS `sys_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user` (
  `user_id` bigint NOT NULL AUTO_INCREMENT COMMENT '用户ID',
  `comp_id` bigint DEFAULT NULL COMMENT '公司id',
  `user_name` varchar(30) NOT NULL COMMENT '用户账号',
  `nick_name` varchar(30) NOT NULL COMMENT '用户昵称',
  `dept_id` bigint DEFAULT NULL COMMENT '部门ID',
  `line_id` bigint DEFAULT NULL COMMENT '产线ID',
  `group_id` bigint DEFAULT NULL COMMENT '班组ID',
  `user_type` varchar(2) DEFAULT '00' COMMENT '用户类型（00系统用户）',
  `email` varchar(50) DEFAULT '' COMMENT '用户邮箱',
  `phonenumber` varchar(11) DEFAULT '' COMMENT '手机号码',
  `sex` char(1) DEFAULT '0' COMMENT '用户性别（0男 1女 2未知）',
  `avatar` varchar(100) DEFAULT '' COMMENT '头像地址',
  `password` varchar(100) DEFAULT '' COMMENT '密码',
  `status` char(1) DEFAULT '0' COMMENT '帐号状态（0正常 1停用）',
  `del_flag` char(1) DEFAULT '0' COMMENT '删除标志（0代表存在 2代表删除）',
  `login_ip` varchar(128) DEFAULT '' COMMENT '最后登录IP',
  `login_date` datetime DEFAULT NULL COMMENT '最后登录时间',
  `create_by` varchar(64) DEFAULT '' COMMENT '创建者',
  `create_time` datetime DEFAULT NULL COMMENT '创建时间',
  `update_by` varchar(64) DEFAULT '' COMMENT '更新者',
  `update_time` datetime DEFAULT NULL COMMENT '更新时间',
  `remark` varchar(500) DEFAULT NULL COMMENT '备注',
  PRIMARY KEY (`user_id`) USING BTREE
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户信息表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user`
--

LOCK TABLES `sys_user` WRITE;
/*!40000 ALTER TABLE `sys_user` DISABLE KEYS */;
INSERT INTO `sys_user` VALUES (1,NULL,'admin','管理员',NULL,NULL,NULL,'00','','','0','','$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52','0','0','127.0.0.1','2026-10-15 15:33:49','bootstrap','2026-10-15 15:07:59','','2026-10-15 15:33:49',NULL),(2,NULL,'sales','销售',NULL,NULL,NULL,'00','','','0','','$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52','0','0','',NULL,'bootstrap','2026-10-15 15:07:59','',NULL,NULL),(3,NULL,'planner','计划员',NULL,NULL,NULL,'00','','','0','','$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52','0','0','',NULL,'bootstrap','2026-10-15 15:07:59','',NULL,NULL),(4,NULL,'production','生产主管',NULL,NULL,NULL,'00','','','0','','$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52','0','0','',NULL,'bootstrap','2026-10-15 15:07:59','',NULL,NULL),(5,NULL,'wh','仓管员',NULL,NULL,NULL,'00','','','0','','$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52','0','0','',NULL,'bootstrap','2026-10-15 15:07:59','',NULL,NULL),(6,NULL,'finance','财务',NULL,NULL,NULL,'00','','','0','','$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52','0','0','',NULL,'bootstrap','2026-10-15 15:07:59','',NULL,NULL),(7,NULL,'hr','人事',NULL,NULL,NULL,'00','','','0','','$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52','0','0','127.0.0.1','2026-10-15 15:34:28','bootstrap','2026-10-15 15:07:59','','2026-10-15 15:34:28',NULL),(8,NULL,'boss','厂长',NULL,NULL,NULL,'00','','','0','','$2a$10$h.ZyWtEl/Sa1YwJ98N9tJ./1b.3kV0uXtCDFSSw8Jr3FKRxo6Jj52','0','0','',NULL,'bootstrap','2026-10-15 15:07:59','',NULL,NULL);
/*!40000 ALTER TABLE `sys_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_post`
--

DROP TABLE IF EXISTS `sys_user_post`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_post` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `post_id` bigint NOT NULL COMMENT '岗位ID',
  PRIMARY KEY (`user_id`,`post_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户与岗位关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_post`
--

LOCK TABLES `sys_user_post` WRITE;
/*!40000 ALTER TABLE `sys_user_post` DISABLE KEYS */;
/*!40000 ALTER TABLE `sys_user_post` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `sys_user_role`
--

DROP TABLE IF EXISTS `sys_user_role`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `sys_user_role` (
  `user_id` bigint NOT NULL COMMENT '用户ID',
  `role_id` bigint NOT NULL COMMENT '角色ID',
  PRIMARY KEY (`user_id`,`role_id`) USING BTREE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci ROW_FORMAT=DYNAMIC COMMENT='用户和角色关联表';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `sys_user_role`
--

LOCK TABLES `sys_user_role` WRITE;
/*!40000 ALTER TABLE `sys_user_role` DISABLE KEYS */;
INSERT INTO `sys_user_role` VALUES (1,1),(2,22),(3,24),(4,25),(5,26),(6,23),(7,27),(8,28);
/*!40000 ALTER TABLE `sys_user_role` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `v_material_last_price`
--

DROP TABLE IF EXISTS `v_material_last_price`;
/*!50001 DROP VIEW IF EXISTS `v_material_last_price`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_material_last_price` AS SELECT 
 1 AS `material_id`,
 1 AS `material_code`,
 1 AS `material_name`,
 1 AS `unit`,
 1 AS `unit_price`,
 1 AS `last_po_date`,
 1 AS `price_src`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_mrp_demand`
--

DROP TABLE IF EXISTS `v_mrp_demand`;
/*!50001 DROP VIEW IF EXISTS `v_mrp_demand`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_mrp_demand` AS SELECT 
 1 AS `material_id`,
 1 AS `material_code`,
 1 AS `material_name`,
 1 AS `category`,
 1 AS `category_name`,
 1 AS `unit`,
 1 AS `ref_price`,
 1 AS `demand_qty`,
 1 AS `stock_qty`,
 1 AS `on_way_qty`,
 1 AS `available_qty`,
 1 AS `net_gap`,
 1 AS `gap_qty`,
 1 AS `suggest_qty`,
 1 AS `est_amount`,
 1 AS `shortage_flag`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_order_ontime`
--

DROP TABLE IF EXISTS `v_order_ontime`;
/*!50001 DROP VIEW IF EXISTS `v_order_ontime`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_order_ontime` AS SELECT 
 1 AS `order_id`,
 1 AS `order_no`,
 1 AS `customer_id`,
 1 AS `customer_name`,
 1 AS `order_date`,
 1 AS `delivery_date`,
 1 AS `order_status`,
 1 AS `order_status_name`,
 1 AS `first_ship_date`,
 1 AS `last_ship_date`,
 1 AS `full_ship_date`,
 1 AS `delay_days`,
 1 AS `ontime_flag`,
 1 AS `delivery_state`*/;
SET character_set_client = @saved_cs_client;

--
-- Temporary view structure for view `v_style_cost`
--

DROP TABLE IF EXISTS `v_style_cost`;
/*!50001 DROP VIEW IF EXISTS `v_style_cost`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `v_style_cost` AS SELECT 
 1 AS `style_id`,
 1 AS `style_no`,
 1 AS `style_name`,
 1 AS `category_name`,
 1 AS `unit`,
 1 AS `order_cnt`,
 1 AS `settle_cnt`,
 1 AS `total_qty`,
 1 AS `material_cost`,
 1 AS `labor_cost`,
 1 AS `overhead_cost`,
 1 AS `total_cost`,
 1 AS `avg_unit_cost`,
 1 AS `total_revenue`,
 1 AS `total_gross_profit`,
 1 AS `gross_margin`,
 1 AS `material_ratio`,
 1 AS `labor_ratio`,
 1 AS `overhead_ratio`*/;
SET character_set_client = @saved_cs_client;

--
-- Dumping routines for database 'hai_erp_demo'
--

--
-- Final view structure for view `v_material_last_price`
--

/*!50001 DROP VIEW IF EXISTS `v_material_last_price`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_material_last_price` AS select `m`.`material_id` AS `material_id`,`m`.`material_code` AS `material_code`,`m`.`material_name` AS `material_name`,`m`.`unit` AS `unit`,coalesce(`last`.`unit_price`,`m`.`ref_price`) AS `unit_price`,`last`.`po_date` AS `last_po_date`,(case when (`last`.`unit_price` is null) then 0 else 1 end) AS `price_src` from (`md_material` `m` left join (select `i`.`material_id` AS `material_id`,`i`.`unit_price` AS `unit_price`,`p`.`order_date` AS `po_date`,row_number() OVER (PARTITION BY `i`.`material_id` ORDER BY `p`.`order_date` desc,`i`.`item_id` desc )  AS `rn` from (`pu_purchase_order_item` `i` join `pu_purchase_order` `p` on((`p`.`po_id` = `i`.`po_id`))) where ((`p`.`status` in (2,3)) and (`p`.`del_flag` = '0'))) `last` on(((`last`.`material_id` = `m`.`material_id`) and (`last`.`rn` = 1)))) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_mrp_demand`
--

/*!50001 DROP VIEW IF EXISTS `v_mrp_demand`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_mrp_demand` AS select `m`.`material_id` AS `material_id`,`m`.`material_code` AS `material_code`,`m`.`material_name` AS `material_name`,`m`.`category` AS `category`,(case `m`.`category` when 1 then '面料' else '辅料' end) AS `category_name`,`m`.`unit` AS `unit`,`m`.`ref_price` AS `ref_price`,round(coalesce(`d`.`qty`,0),3) AS `demand_qty`,round(coalesce(`stock`.`qty`,0),3) AS `stock_qty`,round(coalesce(`transit`.`qty`,0),3) AS `on_way_qty`,round((coalesce(`stock`.`qty`,0) + coalesce(`transit`.`qty`,0)),3) AS `available_qty`,round(((coalesce(`d`.`qty`,0) - coalesce(`stock`.`qty`,0)) - coalesce(`transit`.`qty`,0)),3) AS `net_gap`,round(greatest(((coalesce(`d`.`qty`,0) - coalesce(`stock`.`qty`,0)) - coalesce(`transit`.`qty`,0)),0),3) AS `gap_qty`,ceiling(greatest(((coalesce(`d`.`qty`,0) - coalesce(`stock`.`qty`,0)) - coalesce(`transit`.`qty`,0)),0)) AS `suggest_qty`,round((ceiling(greatest(((coalesce(`d`.`qty`,0) - coalesce(`stock`.`qty`,0)) - coalesce(`transit`.`qty`,0)),0)) * coalesce(`price`.`unit_price`,`m`.`ref_price`)),2) AS `est_amount`,if((coalesce(`d`.`qty`,0) > (coalesce(`stock`.`qty`,0) + coalesce(`transit`.`qty`,0))),1,0) AS `shortage_flag` from ((((`md_material` `m` left join `v_material_last_price` `price` on((`price`.`material_id` = `m`.`material_id`))) left join (select `raw_demand`.`material_id` AS `material_id`,sum(`raw_demand`.`qty`) AS `qty` from (select `wm`.`material_id` AS `material_id`,greatest((`wm`.`plan_qty` - `wm`.`issued_qty`),0) AS `qty` from ((`pr_work_order_material` `wm` join `pr_work_order` `w` on(((`w`.`wo_id` = `wm`.`wo_id`) and (`w`.`del_flag` = '0') and (`w`.`status` <> 9)))) join `sd_order` `o` on((`o`.`order_id` = `w`.`order_id`))) where ((`o`.`del_flag` = '0') and ((`o`.`status` in (1,2,3)) or ((`o`.`status` in (4,5)) and (`o`.`full_ship_date` > curdate())))) union all select `b`.`material_id` AS `material_id`,((`oi`.`qty` * `b`.`qty`) * (1 + (`b`.`loss_rate` / 100))) AS `oi.qty * b.qty * (1 + b.loss_rate / 100)` from ((`sd_order` `o` join `sd_order_item` `oi` on((`oi`.`order_id` = `o`.`order_id`))) join `md_bom` `b` on((`b`.`style_id` = `oi`.`style_id`))) where ((`o`.`del_flag` = '0') and ((`o`.`status` in (1,2,3)) or ((`o`.`status` in (4,5)) and (`o`.`full_ship_date` > curdate()))) and exists(select 1 from `pr_work_order` `w` where ((`w`.`order_id` = `o`.`order_id`) and (`w`.`style_id` = `oi`.`style_id`) and (`w`.`del_flag` = '0') and (`w`.`status` <> 9))) is false)) `raw_demand` group by `raw_demand`.`material_id`) `d` on((`d`.`material_id` = `m`.`material_id`))) left join (select `st_inventory`.`item_id` AS `material_id`,sum(`st_inventory`.`qty`) AS `qty` from `st_inventory` where (`st_inventory`.`item_type` = 1) group by `st_inventory`.`item_id`) `stock` on((`stock`.`material_id` = `m`.`material_id`))) left join (select `pi`.`material_id` AS `material_id`,sum(greatest((`pi`.`qty` - coalesce(`received`.`qty`,0)),0)) AS `qty` from ((`pu_purchase_order_item` `pi` join `pu_purchase_order` `po` on((`po`.`po_id` = `pi`.`po_id`))) left join (select `st_stock_txn`.`source_line_id` AS `source_line_id`,sum(`st_stock_txn`.`qty`) AS `qty` from `st_stock_txn` where ((`st_stock_txn`.`biz_type` = 1) and (`st_stock_txn`.`txn_type` = 1) and (`st_stock_txn`.`source_type` = 'pu_order')) group by `st_stock_txn`.`source_line_id`) `received` on((`received`.`source_line_id` = `pi`.`item_id`))) where ((`po`.`del_flag` = '0') and (`po`.`status` in (1,2))) group by `pi`.`material_id`) `transit` on((`transit`.`material_id` = `m`.`material_id`))) where (`m`.`status` = 1) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_order_ontime`
--

/*!50001 DROP VIEW IF EXISTS `v_order_ontime`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_order_ontime` AS select `o`.`order_id` AS `order_id`,`o`.`order_no` AS `order_no`,`o`.`customer_id` AS `customer_id`,`c`.`customer_name` AS `customer_name`,`o`.`order_date` AS `order_date`,`o`.`delivery_date` AS `delivery_date`,`o`.`status` AS `order_status`,(case `o`.`status` when 0 then '待确认' when 1 then '已确认' when 2 then '生产中' when 3 then '已完工' when 4 then '已出货' when 5 then '已完成' else '已取消' end) AS `order_status_name`,`ship`.`first_ship_date` AS `first_ship_date`,`ship`.`last_ship_date` AS `last_ship_date`,`complete`.`full_ship_date` AS `full_ship_date`,(case when (`complete`.`full_ship_date` is not null) then (to_days(`complete`.`full_ship_date`) - to_days(`o`.`delivery_date`)) else (to_days(curdate()) - to_days(`o`.`delivery_date`)) end) AS `delay_days`,(case when (`complete`.`full_ship_date` is null) then -(1) when (`complete`.`full_ship_date` <= `o`.`delivery_date`) then 1 else 0 end) AS `ontime_flag`,(case when ((`complete`.`full_ship_date` is null) and (`o`.`delivery_date` < curdate())) then '逾期未发齐' when (`complete`.`full_ship_date` is null) then '未发齐' when (`complete`.`full_ship_date` <= `o`.`delivery_date`) then '准时发齐' else '延期发齐' end) AS `delivery_state` from (((`sd_order` `o` join `md_customer` `c` on((`c`.`customer_id` = `o`.`customer_id`))) left join (select `sh_shipment`.`order_id` AS `order_id`,min(`sh_shipment`.`ship_date`) AS `first_ship_date`,max(`sh_shipment`.`ship_date`) AS `last_ship_date` from `sh_shipment` where ((`sh_shipment`.`del_flag` = '0') and (`sh_shipment`.`status` in (1,2)) and (`sh_shipment`.`ship_date` <= curdate())) group by `sh_shipment`.`order_id`) `ship` on((`ship`.`order_id` = `o`.`order_id`))) left join (select `order_lines`.`order_id` AS `order_id`,if((count(0) = count(`order_lines`.`sku_full_date`)),max(`order_lines`.`sku_full_date`),NULL) AS `full_ship_date` from (select `oi`.`order_id` AS `order_id`,`oi`.`item_id` AS `item_id`,min(if((`sent`.`running_qty` >= `oi`.`qty`),`sent`.`ship_date`,NULL)) AS `sku_full_date` from ((`sd_order_item` `oi` join `md_sku` `sku` on(((`sku`.`style_id` = `oi`.`style_id`) and (`sku`.`color_id` = `oi`.`color_id`) and (`sku`.`size_id` = `oi`.`size_id`)))) left join (select `sh`.`order_id` AS `order_id`,`si`.`sku_id` AS `sku_id`,`sh`.`ship_date` AS `ship_date`,sum(`si`.`qty`) OVER (PARTITION BY `sh`.`order_id`,`si`.`sku_id` ORDER BY `sh`.`ship_date`,`sh`.`shipment_id`,`si`.`item_id` )  AS `running_qty` from (`sh_shipment` `sh` join `sh_shipment_item` `si` on((`si`.`shipment_id` = `sh`.`shipment_id`))) where ((`sh`.`del_flag` = '0') and (`sh`.`status` in (1,2)) and (`sh`.`ship_date` <= curdate()))) `sent` on(((`sent`.`order_id` = `oi`.`order_id`) and (`sent`.`sku_id` = `sku`.`sku_id`)))) group by `oi`.`order_id`,`oi`.`item_id`) `order_lines` group by `order_lines`.`order_id`) `complete` on((`complete`.`order_id` = `o`.`order_id`))) where ((`o`.`del_flag` = '0') and (`o`.`status` <> 9)) */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;

--
-- Final view structure for view `v_style_cost`
--

/*!50001 DROP VIEW IF EXISTS `v_style_cost`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `v_style_cost` AS select `st`.`style_id` AS `style_id`,`st`.`style_no` AS `style_no`,`st`.`style_name` AS `style_name`,`cat`.`category_name` AS `category_name`,`st`.`unit` AS `unit`,count(distinct `s`.`order_id`) AS `order_cnt`,count(`s`.`settle_id`) AS `settle_cnt`,coalesce(sum(`s`.`qty`),0) AS `total_qty`,round(coalesce(sum(`s`.`material_cost`),0),2) AS `material_cost`,round(coalesce(sum(`s`.`labor_cost`),0),2) AS `labor_cost`,round(coalesce(sum(`s`.`overhead_cost`),0),2) AS `overhead_cost`,round(coalesce(sum(`s`.`total_cost`),0),2) AS `total_cost`,round((sum(`s`.`total_cost`) / nullif(sum(`s`.`qty`),0)),2) AS `avg_unit_cost`,round(coalesce(sum(`s`.`revenue`),0),2) AS `total_revenue`,round(coalesce(sum(`s`.`gross_profit`),0),2) AS `total_gross_profit`,round(((100 * sum(`s`.`gross_profit`)) / nullif(sum(`s`.`revenue`),0)),2) AS `gross_margin`,round(((100 * sum(`s`.`material_cost`)) / nullif(sum(`s`.`total_cost`),0)),2) AS `material_ratio`,round(((100 * sum(`s`.`labor_cost`)) / nullif(sum(`s`.`total_cost`),0)),2) AS `labor_ratio`,round(((100 * sum(`s`.`overhead_cost`)) / nullif(sum(`s`.`total_cost`),0)),2) AS `overhead_ratio` from ((`md_style` `st` left join `md_category` `cat` on((`cat`.`category_id` = `st`.`category_id`))) left join `fn_cost_settlement` `s` on(((`s`.`style_id` = `st`.`style_id`) and (`s`.`del_flag` = '0') and (`s`.`status` = 1)))) group by `st`.`style_id`,`st`.`style_no`,`st`.`style_name`,`cat`.`category_name`,`st`.`unit` */;
/*!50001 SET character_set_client      = @saved_cs_client */;
/*!50001 SET character_set_results     = @saved_cs_results */;
/*!50001 SET collation_connection      = @saved_col_connection */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-10-19  8:28:33
