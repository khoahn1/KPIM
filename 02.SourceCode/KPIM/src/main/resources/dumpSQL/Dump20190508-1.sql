CREATE DATABASE  IF NOT EXISTS `kpim` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `kpim`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win32 (x86)
--
-- Host: localhost    Database: kpim
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
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `companies` (
  `id` varchar(50) NOT NULL,
  `company_code` varchar(100) NOT NULL,
  `company_name` varchar(500) NOT NULL,
  `tax_code` varchar(50) DEFAULT NULL,
  `address` varchar(500) DEFAULT NULL,
  `province` varchar(100) DEFAULT NULL,
  `district` varchar(100) DEFAULT NULL,
  `phone` varchar(50) DEFAULT NULL,
  `status` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES ('1','FPT','FPT',NULL,NULL,NULL,NULL,NULL,1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `components`
--

DROP TABLE IF EXISTS `components`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `components` (
  `id` varchar(50) NOT NULL,
  `component_name` varchar(500) NOT NULL,
  `project_id` varchar(50) NOT NULL,
  `status` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`project_id`),
  KEY `components_projects` (`project_id`),
  CONSTRAINT `components_projects` FOREIGN KEY (`project_id`) REFERENCES `projects` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `components`
--

LOCK TABLES `components` WRITE;
/*!40000 ALTER TABLE `components` DISABLE KEYS */;
INSERT INTO `components` VALUES ('1','Project Management','1',1,'2019-05-06 09:31:52','2019-05-06 09:31:52','admin','admin'),('2','Project Meeting','1',1,'2019-05-06 09:31:52','2019-05-06 09:31:52','admin','admin'),('3','Project Record','1',1,'2019-05-06 09:31:52','2019-05-06 09:31:52','admin','admin'),('4','Training Record','1',1,'2019-05-06 09:31:52','2019-05-06 09:31:52','admin','admin'),('5','IRC','1',1,'2019-05-06 09:31:52','2019-05-06 09:31:52','admin','admin'),('6','FAIMS_WD','1',1,'2019-05-06 09:31:52','2019-05-06 09:31:52','admin','admin');
/*!40000 ALTER TABLE `components` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `departments`
--

DROP TABLE IF EXISTS `departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `departments` (
  `id` varchar(50) NOT NULL,
  `department_code` varchar(100) NOT NULL,
  `department_name` varchar(500) DEFAULT NULL,
  `parent_department_id` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`parent_department_id`),
  KEY `departments_parent_departments` (`parent_department_id`),
  CONSTRAINT `departments_parent_departments` FOREIGN KEY (`parent_department_id`) REFERENCES `parent_departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `departments`
--

LOCK TABLES `departments` WRITE;
/*!40000 ALTER TABLE `departments` DISABLE KEYS */;
INSERT INTO `departments` VALUES ('1','DepartmentCode1','DepartmentName1','1',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `departments` ENABLE KEYS */;
UNLOCK TABLES;

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
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`privilege_id`),
  KEY `functions_privileges` (`privilege_id`),
  CONSTRAINT `functions_privileges` FOREIGN KEY (`privilege_id`) REFERENCES `privileges` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `functions`
--

LOCK TABLES `functions` WRITE;
/*!40000 ALTER TABLE `functions` DISABLE KEYS */;
INSERT INTO `functions` VALUES ('FU00000001','User','PR00000001','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin');
/*!40000 ALTER TABLE `functions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `genders`
--

DROP TABLE IF EXISTS `genders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `genders` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `gender_name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `genders`
--

LOCK TABLES `genders` WRITE;
/*!40000 ALTER TABLE `genders` DISABLE KEYS */;
INSERT INTO `genders` VALUES (1,'male','male',NULL,NULL,NULL,NULL),(2,'female','female',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `genders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `languages`
--

DROP TABLE IF EXISTS `languages`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `languages` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `language_code` varchar(5) NOT NULL,
  `language_name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `languages`
--

LOCK TABLES `languages` WRITE;
/*!40000 ALTER TABLE `languages` DISABLE KEYS */;
INSERT INTO `languages` VALUES (1,'en','english','english',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `languages` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `marital_status`
--

DROP TABLE IF EXISTS `marital_status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `marital_status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `marital_status_name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `marital_status`
--

LOCK TABLES `marital_status` WRITE;
/*!40000 ALTER TABLE `marital_status` DISABLE KEYS */;
INSERT INTO `marital_status` VALUES (1,'alone','alone',NULL,NULL,NULL,NULL),(2,'married','married',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `marital_status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `operations`
--

DROP TABLE IF EXISTS `operations`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `operations` (
  `id` varchar(50) NOT NULL,
  `function_id` varchar(50) NOT NULL,
  `ops_name` varchar(100) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`function_id`),
  KEY `operations_functions` (`function_id`),
  CONSTRAINT `operations_functions` FOREIGN KEY (`function_id`) REFERENCES `functions` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `operations`
--

LOCK TABLES `operations` WRITE;
/*!40000 ALTER TABLE `operations` DISABLE KEYS */;
INSERT INTO `operations` VALUES ('OP00000001','FU00000001','user-read','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000002','FU00000001','user-create','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000003','FU00000001','user-update','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000004','FU00000001','user-delete','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000005','FU00000001','user-import','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin'),('OP00000006','FU00000001','user-export','2019-02-18 02:15:13','2019-02-18 02:15:13','admin','admin');
/*!40000 ALTER TABLE `operations` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `parent_departments`
--

DROP TABLE IF EXISTS `parent_departments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `parent_departments` (
  `id` varchar(50) NOT NULL,
  `parent_department_code` varchar(100) NOT NULL,
  `parent_department_name` varchar(500) DEFAULT NULL,
  `company_id` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`company_id`),
  KEY `parent_departments_copanies` (`company_id`),
  CONSTRAINT `parent_departments_copanies` FOREIGN KEY (`company_id`) REFERENCES `companies` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `parent_departments`
--

LOCK TABLES `parent_departments` WRITE;
/*!40000 ALTER TABLE `parent_departments` DISABLE KEYS */;
INSERT INTO `parent_departments` VALUES ('1','ParentCode','ParentName','1',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `parent_departments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phases`
--

DROP TABLE IF EXISTS `phases`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phases` (
  `id` int(11) NOT NULL,
  `phase_code` varchar(50) DEFAULT NULL,
  `phase_name` varchar(100) NOT NULL,
  `unit_id` int(11) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `phases_units_idx` (`unit_id`),
  CONSTRAINT `phases_units` FOREIGN KEY (`unit_id`) REFERENCES `units` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phases`
--

LOCK TABLES `phases` WRITE;
/*!40000 ALTER TABLE `phases` DISABLE KEYS */;
INSERT INTO `phases` VALUES (1,'BD','basic design',1,'basic design',NULL,NULL,NULL,NULL),(2,'DD','detail design',1,'detail design',NULL,NULL,NULL,NULL),(3,'CD','coding',2,'coding',NULL,NULL,NULL,NULL),(4,'UTC','create UT case',3,'create UT case',NULL,NULL,NULL,NULL),(5,'UTR','run UT',3,'run UT',NULL,NULL,NULL,NULL),(6,'ITC','create IT case',3,'create IT case',NULL,NULL,NULL,NULL),(7,'ITR','run IT',3,'run IT',NULL,NULL,NULL,NULL),(8,'Study requirement','Study requirement',1,'Study requirement',NULL,NULL,NULL,NULL),(9,'Investigate','Investigate',1,'Investigate',NULL,NULL,NULL,NULL),(10,'Other','Other',NULL,'Other',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `phases` ENABLE KEYS */;
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
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
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
INSERT INTO `privileges` VALUES ('PR00000001','system','2019-02-18 09:14:35','2019-02-18 09:14:35','admin','admin'),('PR00000002','partner','2019-02-18 09:14:35','2019-02-18 09:14:35','admin','admin');
/*!40000 ALTER TABLE `privileges` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `products` (
  `id` varchar(50) NOT NULL,
  `product_name` varchar(500) NOT NULL,
  `component_id` varchar(50) NOT NULL,
  `scope_id` int(11) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`component_id`),
  KEY `products_components` (`component_id`),
  KEY `products_scope` (`scope_id`),
  CONSTRAINT `fk_products_scope1` FOREIGN KEY (`scope_id`) REFERENCES `scope` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `products_components` FOREIGN KEY (`component_id`) REFERENCES `components` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES ('1','Meeting','5',6,1,NULL,NULL,NULL,NULL),('2','YOKOTENKAI','6',1,1,NULL,NULL,NULL,NULL),('3','PH0020142-0107','6',1,1,NULL,NULL,NULL,NULL),('4','CR','6',1,1,NULL,NULL,NULL,NULL),('5','FixBugIT','6',1,1,NULL,NULL,NULL,NULL),('6','CR','5',1,1,NULL,NULL,NULL,NULL),('7','Training_JP','4',6,1,NULL,NULL,NULL,NULL),('8','Training_Technical','4',6,1,NULL,NULL,NULL,NULL),('9','Management','1',6,1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `project_user`
--

DROP TABLE IF EXISTS `project_user`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `project_user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `users_id` varchar(50) NOT NULL,
  `projects_id` varchar(50) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`users_id`,`projects_id`),
  KEY `fk_project_user_projects1_idx` (`projects_id`),
  KEY `fk_project_user_users1_idx` (`users_id`),
  CONSTRAINT `fk_project_user_projects1` FOREIGN KEY (`projects_id`) REFERENCES `projects` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_project_user_users1` FOREIGN KEY (`users_id`) REFERENCES `users` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `project_user`
--

LOCK TABLES `project_user` WRITE;
/*!40000 ALTER TABLE `project_user` DISABLE KEYS */;
/*!40000 ALTER TABLE `project_user` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `projects`
--

DROP TABLE IF EXISTS `projects`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `projects` (
  `id` varchar(50) NOT NULL,
  `project_code` varchar(100) NOT NULL,
  `project_name` varchar(500) DEFAULT NULL,
  `department_id` varchar(50) NOT NULL,
  `status` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`department_id`),
  KEY `projects_departments` (`department_id`),
  CONSTRAINT `projects_departments` FOREIGN KEY (`department_id`) REFERENCES `departments` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `projects`
--

LOCK TABLES `projects` WRITE;
/*!40000 ALTER TABLE `projects` DISABLE KEYS */;
INSERT INTO `projects` VALUES ('1','KINYU3','KINYU3','1',1,NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `projects` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `role_authority`
--

DROP TABLE IF EXISTS `role_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `role_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `role_id` varchar(50) NOT NULL,
  `operation_id` varchar(100) NOT NULL,
  `authority` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`role_id`,`operation_id`),
  KEY `role_authority_roles` (`role_id`),
  KEY `role_authority_operations` (`operation_id`),
  CONSTRAINT `role_authority_operations` FOREIGN KEY (`operation_id`) REFERENCES `operations` (`id`),
  CONSTRAINT `role_authority_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `role_authority`
--

LOCK TABLES `role_authority` WRITE;
/*!40000 ALTER TABLE `role_authority` DISABLE KEYS */;
INSERT INTO `role_authority` VALUES (1,'RL00000001','OP00000001',1,'2019-04-22 11:34:25','2019-04-22 11:34:25',NULL,NULL),(2,'RL00000001','OP00000002',1,'2019-04-22 11:34:25','2019-04-22 11:34:25',NULL,NULL),(3,'RL00000001','OP00000003',1,'2019-04-22 11:34:25','2019-04-22 11:34:25',NULL,NULL),(4,'RL00000001','OP00000004',1,'2019-04-22 11:34:25','2019-04-22 11:34:25',NULL,NULL),(5,'RL00000001','OP00000005',1,'2019-04-22 11:34:25','2019-04-22 11:34:25',NULL,NULL);
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
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
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
INSERT INTO `roles` VALUES ('RL00000001','admin','admin','2019-02-18 09:14:49','2019-04-22 11:34:25','admin',NULL);
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `scope`
--

DROP TABLE IF EXISTS `scope`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `scope` (
  `id` int(11) NOT NULL,
  `scope_name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `scope`
--

LOCK TABLES `scope` WRITE;
/*!40000 ALTER TABLE `scope` DISABLE KEYS */;
INSERT INTO `scope` VALUES (1,'Coding','Coding','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),(2,'UT','Unit Test','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),(3,'IT','Intergration Test','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),(4,'ST','Sytem Test','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),(5,'Design','Design','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin'),(6,'Other','Other','2019-02-18 09:14:49','2019-02-18 09:14:49','admin','admin');
/*!40000 ALTER TABLE `scope` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `status`
--

DROP TABLE IF EXISTS `status`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `status` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `status_name` varchar(100) NOT NULL,
  `status_type` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `status`
--

LOCK TABLES `status` WRITE;
/*!40000 ALTER TABLE `status` DISABLE KEYS */;
INSERT INTO `status` VALUES (1,'active','','active',NULL,NULL,NULL,NULL),(2,'inactive','','inactive',NULL,NULL,NULL,NULL),(3,'lock','','lock',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `status` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tasks`
--

DROP TABLE IF EXISTS `tasks`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tasks` (
  `id` varchar(50) NOT NULL,
  `task_code` varchar(50) DEFAULT NULL,
  `product_id` varchar(50) NOT NULL,
  `phase_id` varchar(50) NOT NULL,
  `user_id` varchar(50) NOT NULL,
  `status_id` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`product_id`),
  KEY `tasks_products` (`product_id`),
  CONSTRAINT `tasks_products` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tasks`
--

LOCK TABLES `tasks` WRITE;
/*!40000 ALTER TABLE `tasks` DISABLE KEYS */;
/*!40000 ALTER TABLE `tasks` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `type_of_work`
--

DROP TABLE IF EXISTS `type_of_work`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `type_of_work` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `type_of_work`
--

LOCK TABLES `type_of_work` WRITE;
/*!40000 ALTER TABLE `type_of_work` DISABLE KEYS */;
INSERT INTO `type_of_work` VALUES (1,'Create','Create','2019-04-23 16:27:06','2019-04-23 16:27:06',NULL,NULL),(2,'Review','Review','2019-04-23 16:27:22','2019-04-23 16:27:50',NULL,NULL),(3,'Test','Test','2019-04-23 16:27:58','2019-04-23 16:27:58',NULL,NULL),(4,'Correct','Correct',NULL,NULL,NULL,NULL),(5,'Study','Study',NULL,NULL,NULL,NULL),(6,'Translate','Translate',NULL,NULL,NULL,NULL);
/*!40000 ALTER TABLE `type_of_work` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `units`
--

DROP TABLE IF EXISTS `units`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `units` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `unit_name` varchar(100) NOT NULL,
  `description` varchar(500) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `units`
--

LOCK TABLES `units` WRITE;
/*!40000 ALTER TABLE `units` DISABLE KEYS */;
INSERT INTO `units` VALUES (1,'Pages','Pages',NULL,NULL,NULL,NULL),(2,'Steps','Steps',NULL,NULL,NULL,NULL),(3,'Cases','Cases','2019-04-23 16:53:38','2019-04-23 16:53:38',NULL,NULL);
/*!40000 ALTER TABLE `units` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_authority`
--

DROP TABLE IF EXISTS `user_authority`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_authority` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(50) NOT NULL,
  `operation_id` varchar(50) NOT NULL,
  `authority` int(11) NOT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`user_id`,`operation_id`),
  KEY `user_authority_roles` (`user_id`),
  KEY `user_authority_operations` (`operation_id`),
  CONSTRAINT `user_authority_operations` FOREIGN KEY (`operation_id`) REFERENCES `operations` (`id`),
  CONSTRAINT `user_authority_roles` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=132 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_authority`
--

LOCK TABLES `user_authority` WRITE;
/*!40000 ALTER TABLE `user_authority` DISABLE KEYS */;
INSERT INTO `user_authority` VALUES (77,'US00000001','OP00000001',1,'2019-05-02 12:37:15','2019-05-02 12:37:15',NULL,NULL),(78,'US00000001','OP00000002',1,'2019-05-02 12:37:15','2019-05-02 12:37:15',NULL,NULL),(79,'US00000001','OP00000003',1,'2019-05-02 12:37:15','2019-05-02 12:37:15',NULL,NULL),(80,'US00000001','OP00000004',1,'2019-05-02 12:37:15','2019-05-02 12:37:15',NULL,NULL),(81,'US00000001','OP00000005',1,'2019-05-02 12:37:15','2019-05-02 12:37:15',NULL,NULL),(127,'US00000002','OP00000001',1,'2019-05-07 17:01:13','2019-05-07 17:01:13',NULL,NULL),(128,'US00000002','OP00000002',1,'2019-05-07 17:01:13','2019-05-07 17:01:13',NULL,NULL),(129,'US00000002','OP00000003',1,'2019-05-07 17:01:13','2019-05-07 17:01:13',NULL,NULL),(130,'US00000002','OP00000004',1,'2019-05-07 17:01:13','2019-05-07 17:01:13',NULL,NULL),(131,'US00000002','OP00000005',1,'2019-05-07 17:01:13','2019-05-07 17:01:13',NULL,NULL);
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
  `avatar` varchar(500) DEFAULT NULL,
  `password` varchar(150) NOT NULL,
  `birthday` date DEFAULT NULL,
  `language_id` int(11) DEFAULT NULL,
  `gender_id` int(11) DEFAULT NULL,
  `marital_status_id` int(11) DEFAULT NULL,
  `role_id` varchar(50) NOT NULL,
  `status_id` int(11) DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`),
  KEY `users_roles` (`role_id`),
  KEY `fk_users_gender1_idx` (`gender_id`),
  KEY `fk_users_status1_idx` (`status_id`),
  KEY `fk_users_marital_status1_idx` (`marital_status_id`),
  KEY `fk_users_language1_idx` (`language_id`),
  CONSTRAINT `fk_users_gender1` FOREIGN KEY (`gender_id`) REFERENCES `genders` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_language1` FOREIGN KEY (`language_id`) REFERENCES `languages` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_marital_status1` FOREIGN KEY (`marital_status_id`) REFERENCES `marital_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_users_status1` FOREIGN KEY (`status_id`) REFERENCES `status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `users_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES ('US00000001','admin','admin123123','khoahn.fpt@gmail.com','daklak','0989842323','upload/img/profile-pics/admin.jpg','$2a$10$EblZqNptyYvcLm/VwDCVAuBjzZOI7khzdyGPBr08PpIi0na624b8.',NULL,NULL,1,1,'RL00000001',1,'2019-02-18 09:14:49','2019-05-02 12:37:15','admin',NULL),('US00000002','khoahn2','khoahn2','','khoahn2','123456','upload/img/profile-pics/khoahn2_07-05-2019_05-01-12-893.jpg','abc125',NULL,NULL,2,2,'RL00000001',NULL,'2019-04-30 09:45:14','2019-05-07 17:01:13','admin',NULL);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `worklogs`
--

DROP TABLE IF EXISTS `worklogs`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `worklogs` (
  `id` varchar(50) NOT NULL,
  `task_id` varchar(50) NOT NULL,
  `anken` varchar(500) DEFAULT NULL,
  `screen` varchar(500) DEFAULT NULL,
  `type_of_work_id` int(11) DEFAULT NULL,
  `plan_effort` double DEFAULT NULL,
  `acctual_effort` double DEFAULT NULL,
  `quantity` double DEFAULT NULL,
  `unit_id` varchar(50) DEFAULT NULL,
  `description` varchar(500) DEFAULT NULL,
  `status` int(11) DEFAULT NULL,
  `issue` varchar(500) DEFAULT NULL,
  `notes` varchar(500) DEFAULT NULL,
  `log_date` date DEFAULT NULL,
  `created_date` datetime DEFAULT NULL,
  `updated_date` datetime DEFAULT NULL,
  `created_by` varchar(50) DEFAULT NULL,
  `updated_by` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id`,`task_id`),
  KEY `worklogs_tasks` (`task_id`),
  CONSTRAINT `worklogs_tasks` FOREIGN KEY (`task_id`) REFERENCES `tasks` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `worklogs`
--

LOCK TABLES `worklogs` WRITE;
/*!40000 ALTER TABLE `worklogs` DISABLE KEYS */;
/*!40000 ALTER TABLE `worklogs` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'kpim'
--

--
-- Dumping routines for database 'kpim'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2019-05-08 14:21:49
