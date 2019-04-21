CREATE DATABASE  IF NOT EXISTS `angularjs_project1` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `angularjs_project1`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: angularjs_project1
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
-- Table structure for table `master_authority`
--

DROP TABLE IF EXISTS `master_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `authority_name` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(50) NOT NULL DEFAULT 'admin',
  `updated_by` varchar(50) DEFAULT 'admin',
  PRIMARY KEY (`id`),
  UNIQUE KEY `authority_name` (`authority_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master_authority`
--

LOCK TABLES `master_authority` WRITE;
/*!40000 ALTER TABLE `master_authority` DISABLE KEYS */;
INSERT INTO `master_authority` VALUES (1,'admin','2018-02-17 15:54:19','2018-02-17 15:54:19','admin','admin'),(2,'user','2018-02-17 15:54:19','2018-02-17 15:54:19','admin','admin');
/*!40000 ALTER TABLE `master_authority` ENABLE KEYS */;
UNLOCK TABLES;

DROP TABLE IF EXISTS `master_project_type_of_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_project_type_of_work` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type_of_work_name` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(50) NOT NULL DEFAULT 'admin',
  `updated_by` varchar(50) DEFAULT 'admin',
  PRIMARY KEY (`id`),
  UNIQUE KEY `type_of_work_name` (`type_of_work_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master_project_type_of_work`
--

LOCK TABLES `master_project_type_of_work` WRITE;
/*!40000 ALTER TABLE `master_project_type_of_work` DISABLE KEYS */;
INSERT INTO `master_project_type_of_work` VALUES (1,'admin','2018-02-17 15:54:19','2018-02-17 15:54:19','admin','admin'),(2,'user','2018-02-17 15:54:19','2018-02-17 15:54:19','admin','admin');
/*!40000 ALTER TABLE `master_project_type_of_work` ENABLE KEYS */;
UNLOCK TABLES;
--
-- Table structure for table `master_gender`
--

DROP TABLE IF EXISTS `master_gender`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_gender` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `gender_name` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(50) NOT NULL DEFAULT 'admin',
  `updated_by` varchar(50) DEFAULT 'admin',
  PRIMARY KEY (`id`),
  UNIQUE KEY `gender_name` (`gender_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master_gender`
--

LOCK TABLES `master_gender` WRITE;
/*!40000 ALTER TABLE `master_gender` DISABLE KEYS */;
INSERT INTO `master_gender` VALUES (1,'male','2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin'),(2,'female','2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin');
/*!40000 ALTER TABLE `master_gender` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `master_status`
--

DROP TABLE IF EXISTS `master_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `master_status` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(50) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(50) NOT NULL DEFAULT 'admin',
  `updated_by` varchar(50) DEFAULT 'admin',
  PRIMARY KEY (`id`),
  UNIQUE KEY `status_name` (`status_name`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `master_status`
--

LOCK TABLES `master_status` WRITE;
/*!40000 ALTER TABLE `master_status` DISABLE KEYS */;
INSERT INTO `master_status` VALUES (1,'active','2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin'),(2,'inactive','2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin');
/*!40000 ALTER TABLE `master_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `project_code` varchar(100) NOT NULL,
  `project_name` varchar(100) NOT NULL,
  `details` text,
  `status` bigint(20) DEFAULT '1',
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(50) NOT NULL DEFAULT 'admin',
  `updated_by` varchar(50) DEFAULT 'admin',
  PRIMARY KEY (`id`),
  UNIQUE KEY `project_code` (`project_code`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
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
INSERT INTO `token` VALUES ('2J4DuLS96o1v0jPMNHFD8g==','nrTpZf93FslEG0BVWasU0Q==','2018-05-19 05:37:36','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('7OswPcJkhvQijm+7fg7c4A==','V9DiQDy9pT1eumaGCr6D+w==','2018-05-23 13:33:22','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('cK4V+3ZQShjWY7zGpHGQFg==','fQ48eR7Or/4BejwYcw15qw==','2018-05-22 15:41:08','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('GbxNIyklAPPbC83IlZH9BQ==','N/gI/DQpeTUCBTSLXI7n4A==','2018-05-23 13:32:53','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('gyCvxkD5rcJt8WzQj+IpVg==','jsDdFdm9IuA6stJLtWl59Q==','2018-05-22 14:20:04','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('Hz0SqG498UR4lPKNPn5Akw==','vfpzPGZ4iD6b8I1aCCdizQ==','2018-05-23 13:23:01','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('NIBTNlmIWDFXFYlhUdZeaw==','qiMKc29Nnooo/jyfAzDF+g==','2018-05-19 05:37:36','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('nPFyaiyJuyGQYqwkOzIlWw==','/oDd+FZquSXpH9WbpD4YfA==','2018-05-19 05:37:36','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('nUic/7Gv0fkvI/Pq/oqWaw==','Iq9Cy6H/ERHfM/Vpu/6W4g==','2018-05-19 05:37:36','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('pS0IH/eobkPklyXAS2hPQA==','agofFkeCXpdvdvZLcl3+Eg==','2018-05-19 17:24:54','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('QnQWZhVYqb5cHATI5xj9lQ==','ovuqp8MT1XNhRtKiWW5VVQ==','2018-05-22 14:14:22','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('Qr9ILSuryw0l04OQ/oDb0w==','AwcQjwEXJqo8fyQSg4Go7w==','2018-05-23 13:17:27','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('qVr3h7O0w9KZeHJ9/pPXBw==','iXbQXN/gRJnGXAsFVr+17A==','2018-05-19 15:02:56','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('rD9bnwjbpFvzgAalPXRWTg==','G774M7s4cW/kbbK6Qymh2w==','2018-05-19 15:59:37','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('uf7E4ifrZ0KMBql6L8zU8w==','7el5T5RhO5yvkFU8nnBc8w==','2018-05-19 05:37:36','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('wzMy3XhaPlHhRrD++QxTKg==','1o5EcBhc6JmITpGIkq30WQ==','2018-05-19 15:00:12','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('x66X3XQ3paw/lP5NojtTGA==','s7/8K8c5P40dXCkbVw7bbw==','2018-05-22 15:44:26','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve'),('XwCAOH9eUgLqgk852cUQ8w==','vcT686+HAWeqJTJG/jTb1w==','2018-05-19 05:37:36','0:0:0:0:0:0:0:1','Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/66.0.3359.181 Safari/537.36','steve');
/*!40000 ALTER TABLE `token` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) DEFAULT NULL,
  `e_mail` varchar(50) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `language` char(2) DEFAULT NULL,
  `id_picture` int(11) DEFAULT NULL,
  `username` varchar(50) NOT NULL,
  `password` varchar(50) NOT NULL,
  `birthday` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `status` bigint(20) DEFAULT '1',
  `gender` bigint(20) DEFAULT NULL,
  `marital_status` bigint(20) DEFAULT NULL,
  `employment_status` bigint(20) DEFAULT NULL,
  `job_title` bigint(20) DEFAULT NULL,
  `address` varchar(100) DEFAULT NULL,
  `home_phone` varchar(50) DEFAULT NULL,
  `mobile_phone` varchar(50) DEFAULT NULL,
  `work_email` varchar(100) DEFAULT NULL,
  `private_email` varchar(100) DEFAULT NULL,
  `joined_date` timestamp NULL DEFAULT NULL,
  `department` bigint(20) DEFAULT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(50) NOT NULL DEFAULT 'admin',
  `updated_by` varchar(50) DEFAULT 'admin',
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Steve JOBS','steve.jobs@apple.com','0033 1 23 45 67 89','en',NULL,'steve','steve','2018-05-22 14:21:27',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin'),(2,'Bill GATES','bill.gates@microsoft.com','0033 1 23 45 67 89','fr',NULL,'bill','bill','2018-02-17 15:54:20',1,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin'),(3,'Mark ZUCKERBERG','mark.zuckerberg@facebook.com','0033 1 23 45 67 89','en',NULL,'mark','zuckerberg','2018-02-17 15:54:20',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin'),(5,'Huynh Ngoc Khoa','khoahn1@gmail.com',NULL,NULL,NULL,'khoahn1','abc123','1992-07-15 13:41:36',NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,NULL,'2018-05-23 13:41:50','2018-05-23 13:41:50','steve','steve');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users_authority`
--

DROP TABLE IF EXISTS `users_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` bigint(20) NOT NULL,
  `authority_id` bigint(20) NOT NULL,
  `created_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `created_by` varchar(50) NOT NULL DEFAULT 'admin',
  `updated_by` varchar(50) DEFAULT 'admin',
  PRIMARY KEY (`id`,`user_id`),
  KEY `FK_USERS` (`user_id`),
  KEY `FK_USERS_AUTHORITY` (`authority_id`),
  CONSTRAINT `FK_USERS` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `FK_USERS_AUTHORITY` FOREIGN KEY (`authority_id`) REFERENCES `users_authority` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users_authority`
--

LOCK TABLES `users_authority` WRITE;
/*!40000 ALTER TABLE `users_authority` DISABLE KEYS */;
INSERT INTO `users_authority` VALUES (1,1,1,'2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin'),(2,2,2,'2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin'),(3,3,1,'2018-02-17 15:54:20','2018-02-17 15:54:20','admin','admin'),(12,5,1,'2018-05-23 13:41:49','2018-05-23 13:41:49','admin','admin');
/*!40000 ALTER TABLE `users_authority` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'angularjs_project1'
--

--
-- Dumping routines for database 'angularjs_project1'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2018-07-06 13:50:35
