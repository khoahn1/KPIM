CREATE DATABASE  IF NOT EXISTS `stm_project` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `stm_project`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: stm_project
-- ------------------------------------------------------
-- Server version	5.6.23-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `functions`
--

DROP TABLE IF EXISTS `functions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `functions` (
  `id` varchar(50) NOT NULL,
  `function_name` varchar(100) NOT NULL,
  `privilege_id` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`privilege_id`),
  KEY `fk_functions_privilege_idx` (`privilege_id`),
  CONSTRAINT `fk_functions_privileges` FOREIGN KEY (`privilege_id`) REFERENCES `privileges` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functions`
--

LOCK TABLES `functions` WRITE;
/*!40000 ALTER TABLE `functions` DISABLE KEYS */;
INSERT INTO `functions` VALUES ('FU00000001','Branch','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000002','ExpensesOther','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000003','PosParameter','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000004','PrintTemplate','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000005','SmsEmailTemplate','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000006','Surcharge','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000007','User','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000008','AuditTrail','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000010','DashBoard','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000011','Customer','PR00000002','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000012','CustomerPointAdjustment','PR00000002','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000013','CustomerAdjustment','PR00000002','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),('FU00000014','SupplierAdjustment','PR00000002','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin');
/*!40000 ALTER TABLE `functions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operations`
--

DROP TABLE IF EXISTS `operations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operations` (
  `id` varchar(50) NOT NULL,
  `ops_name` varchar(100) NOT NULL,
  `function_id` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`function_id`),
  KEY `fk_operations_functions1_idx` (`function_id`),
  CONSTRAINT `fk_operations_functions1` FOREIGN KEY (`function_id`) REFERENCES `functions` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operations`
--

LOCK TABLES `operations` WRITE;
/*!40000 ALTER TABLE `operations` DISABLE KEYS */;
INSERT INTO `operations` VALUES ('OP00000001','Branch_Read','FU00000001','2019-02-18 09:15:13','2019-02-18 09:15:13','admin','admin'),('OP00000002','Branch_Create','FU00000001','2019-02-18 09:15:13','2019-02-18 09:15:13','admin','admin'),('OP00000003','Branch_Update','FU00000001','2019-02-18 09:15:13','2019-02-18 09:15:13','admin','admin'),('OP00000004','Branch_Delete','FU00000001','2019-02-18 09:15:13','2019-02-18 09:15:13','admin','admin'),('OP00000005','ExpensesOther_Read','FU00000002','2019-02-19 02:17:33','2019-02-19 02:17:33','admin','admin'),('OP00000006','ExpensesOther_Create','FU00000002','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000007','ExpensesOther_Update','FU00000002','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000008','ExpensesOther_Delete','FU00000002','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000009','PosParameter_Update','FU00000003','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000010','PrintTemplate_Read','FU00000004','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000011','PrintTemplate_Update','FU00000004','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000012','SmsEmailTemplate_Read','FU00000005','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000013','SmsEmailTemplate_Create','FU00000005','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000014','SmsEmailTemplate_Update','FU00000005','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000015','SmsEmailTemplate_Delete','FU00000005','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000016','SmsEmailTemplate_SendSMS','FU00000005','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000017','SmsEmailTemplate_SendEmail','FU00000005','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000018','SmsEmailTemplate_SendZalo','FU00000005','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000019','Surcharge_Read','FU00000006','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000020','Surcharge_Create','FU00000006','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000021','Surcharge_Update','FU00000006','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000022','Surcharge_Delete','FU00000006','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000023','user-read','FU00000007','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000024','user-create','FU00000007','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000025','user-update','FU00000007','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000026','user-delete','FU00000007','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000027','AuditTrail_Read','FU00000008','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000028','DashBoard_Read','FU00000009','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000029','Manufacturing_Read','FU00000010','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000030','Manufacturing_Create','FU00000010','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000031','Manufacturing_Update','FU00000010','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000032','Manufacturing_Delete','FU00000010','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000033','Manufacturing_Export','FU00000010','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin');
/*!40000 ALTER TABLE `operations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `privileges`
--

DROP TABLE IF EXISTS `privileges`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `privileges` (
  `id` varchar(50) NOT NULL,
  `privilege_name` varchar(100) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `privileges`
--

LOCK TABLES `privileges` WRITE;
/*!40000 ALTER TABLE `privileges` DISABLE KEYS */;
INSERT INTO `privileges` VALUES ('PR00000001','system','2019-02-18 09:14:35','2019-02-18 09:14:35','admin','admin'),('PR00000002','partner','2019-02-18 09:14:35','2019-02-18 09:14:35','admin','admin'),('PR00000003','report','2019-02-18 09:14:35','2019-02-18 09:14:35','admin','admin'),('PR00000004','trade','2019-02-18 09:14:35','2019-02-18 09:14:35','admin','admin'),('PR00000005','good','2019-02-18 09:14:35','2019-02-18 09:14:35','admin','admin');
/*!40000 ALTER TABLE `privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_authority`
--

DROP TABLE IF EXISTS `role_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_authority` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(50) NOT NULL,
  `operation_id` varchar(50) NOT NULL,
  `authority` int(11) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`role_id`,`operation_id`),
  KEY `fk_role_authority_roles1_idx` (`role_id`),
  KEY `fk_role_authority_operations1_idx` (`operation_id`),
  CONSTRAINT `fk_role_authority_operations1` FOREIGN KEY (`operation_id`) REFERENCES `operations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_role_authority_roles1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_authority`
--

LOCK TABLES `role_authority` WRITE;
/*!40000 ALTER TABLE `role_authority` DISABLE KEYS */;
INSERT INTO `role_authority` VALUES (1,'RL00000001','OP00000001',1,'2019-02-18 09:17:11','2019-02-18 09:17:11','admin','admin'),(2,'RL00000002','OP00000001',1,'2019-02-18 09:17:11','2019-02-18 09:17:11','admin','admin'),(3,'RL00000003','OP00000001',1,'2019-02-18 09:17:11','2019-02-18 09:17:11','admin','admin'),(4,'RL00000002','OP00000002',1,'2019-02-18 09:17:11',NULL,'admin',NULL),(5,'RL00000002','OP00000003',1,'2019-02-18 09:17:11',NULL,'admin',NULL),(6,'RL00000002','OP00000004',1,'2019-02-18 09:17:11',NULL,'admin',NULL);
/*!40000 ALTER TABLE `role_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `roles` (
  `id` varchar(50) NOT NULL,
  `role_name` varchar(100) NOT NULL,
  `description` varchar(500) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES ('RL00000001','admin','admin','2019-02-18 09:16:50','2019-02-18 09:16:50','admin','admin'),('RL00000002','sale','sale','2019-02-18 09:16:50','2019-02-18 09:16:50','admin','admin'),('RL00000003','user','user','2019-02-18 09:16:50','2019-02-18 09:16:50','admin','admin');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `token`
--

DROP TABLE IF EXISTS `token`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `token` (
  `series` varchar(50) NOT NULL,
  `value` varchar(50) DEFAULT NULL,
  `date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `ip_address` varchar(50) DEFAULT NULL,
  `user_agent` varchar(200) DEFAULT NULL,
  `user_login` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `token`
--

LOCK TABLES `token` WRITE;
/*!40000 ALTER TABLE `token` DISABLE KEYS */;
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_authority` (
  `id` bigint(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `operation_id` varchar(50) NOT NULL,
  `authority` int(11) NOT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`user_id`,`operation_id`),
  KEY `fk_user_authority_users1_idx` (`user_id`),
  KEY `fk_user_authority_operations1_idx` (`operation_id`),
  CONSTRAINT `fk_user_authority_operations1` FOREIGN KEY (`operation_id`) REFERENCES `operations` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_user_authority_users1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
INSERT INTO `user_authority` VALUES (23,'US00000001','OP00000001',1,'2019-02-27 02:56:17',NULL,'khoahn1',NULL),(24,'US00000002','OP00000001',1,'2019-02-27 02:56:48',NULL,'khoahn1',NULL),(25,'US00000002','OP00000002',1,'2019-02-27 02:56:48',NULL,'khoahn1',NULL),(26,'US00000002','OP00000003',1,'2019-02-27 02:56:48',NULL,'khoahn1',NULL),(27,'US00000002','OP00000004',1,'2019-02-27 02:56:48',NULL,'khoahn1',NULL),(27,'US00000007','OP00000001',1,'2019-02-27 03:22:31',NULL,'khoahn1',NULL),(28,'US00000004','OP00000001',1,'2019-02-27 02:58:39',NULL,'khoahn1',NULL),(28,'US00000007','OP00000002',1,'2019-02-27 03:22:31',NULL,'khoahn1',NULL),(29,'US00000004','OP00000002',1,'2019-02-27 02:58:39',NULL,'khoahn1',NULL),(29,'US00000007','OP00000003',1,'2019-02-27 03:22:31',NULL,'khoahn1',NULL),(30,'US00000004','OP00000003',1,'2019-02-27 02:58:39',NULL,'khoahn1',NULL),(30,'US00000007','OP00000004',1,'2019-02-27 03:22:31',NULL,'khoahn1',NULL),(31,'US00000004','OP00000004',1,'2019-02-27 02:58:39',NULL,'khoahn1',NULL),(32,'US00000001','OP00000023',1,'2019-02-27 02:58:39',NULL,'khoahn1',NULL),(33,'US00000001','OP00000024',1,'2019-02-27 02:58:39',NULL,'khoahn1',NULL),(34,'US00000001','OP00000025',1,'2019-02-27 02:58:39',NULL,'khoahn1',NULL);
/*!40000 ALTER TABLE `user_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` varchar(50) NOT NULL,
  `username` varchar(50) NOT NULL,
  `full_name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `language` int(11) DEFAULT NULL,
  `avatar` varchar(500) DEFAULT NULL,
  `password` varchar(50) NOT NULL,
  `birthday` date DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `gender` int(11) DEFAULT NULL,
  `marital_status` int(11) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NULL DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  `role_id` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `fk_users_roles1_idx` (`role_id`),
  CONSTRAINT `fk_users_roles1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('US00000001','khoahn1','khoahn1','khoahn1@gmail.com','khoahn1','0989842323',1,'upload/img/profile-pics/khoahn1.png','khoahn1','2019-02-06',1,1,1,'2019-02-18 09:14:13','2019-02-27 02:56:17','admin','khoahn1','RL00000001'),('US00000002','khoahn2','khoahn2','khoahn2@gmail.com','khoahn2','0989842323',1,'upload/img/profile-pics/khoahn2.png','khoahn2','2019-02-01',1,1,1,'2019-02-18 09:14:13','2019-02-27 02:56:48','admin','khoahn1','RL00000002'),('US00000003','khoahn3','khoahn3','khoahn3','khoahn3','0989842323',1,'resources/img/profile-pics/avatar-default-icon.png','khoahn3',NULL,1,1,1,'2019-02-18 09:14:13','2019-02-18 09:14:13','admin','admin','RL00000002'),('US00000004','khoahn4','khoahn4','khoahn4@gmail.com','khoahn4','0989842323',1,'upload/img/profile-pics/khoahn4.jpg','khoahn4','2019-02-01',1,1,1,'2019-02-18 09:14:13','2019-02-27 02:58:39','admin','khoahn1','RL00000002'),('US00000005','khoahn5','khoahn5','khoahn5','khoahn5','0989842323',1,'resources/img/profile-pics/avatar-default-icon.png','khoahn5',NULL,1,1,1,'2019-02-18 09:14:13','2019-02-18 09:14:13','admin','admin','RL00000002'),('US00000006','khoahn6','khoahn6','khoahn6','khoahn6','0989842323',1,'resources/img/profile-pics/avatar-default-icon.png','khoahn6',NULL,1,1,1,'2019-02-18 09:14:13','2019-02-18 09:14:13','admin','admin','RL00000002'),('US00000007','khoahn11','','','','',NULL,'resources/img/profile-pics/avatar-default-icon.png','khoahn1',NULL,0,0,0,'2019-02-27 03:22:31',NULL,'khoahn1',NULL,'RL00000002');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'stm_project'
--

--
-- Dumping routines for database 'stm_project'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-02-27 13:27:49
