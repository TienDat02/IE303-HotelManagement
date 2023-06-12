-- MySQL dump 10.13  Distrib 8.0.33, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hotelmanagement
-- ------------------------------------------------------
-- Server version	8.0.33

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `idAccount` int NOT NULL,
  `Employee_ID` bigint NOT NULL,
  `account_name` varchar(45) NOT NULL,
  `account_password` varchar(45) NOT NULL,
  `isAdmin` enum('1','0') DEFAULT NULL,
  PRIMARY KEY (`idAccount`),
  UNIQUE KEY `account_nam_UNIQUE` (`account_name`),
  KEY `account-employee_idx` (`Employee_ID`),
  CONSTRAINT `account_employee` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,20520437,'tiendat','admin','1');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bill`
--

DROP TABLE IF EXISTS `bill`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bill` (
  `Bill_ID` int NOT NULL AUTO_INCREMENT,
  `Guest_ID` bigint DEFAULT NULL,
  `Bill_Date` date DEFAULT NULL,
  `Bill_Time` time DEFAULT NULL,
  `Total_Cost` decimal(14,2) DEFAULT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`Bill_ID`),
  KEY `bill_Guest_idx` (`Guest_ID`),
  CONSTRAINT `bill_Guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (18,123456789012,'2023-05-10','18:45:26',1250000.00,20520437),(19,123456789012,'2023-04-10','20:33:19',1333333.40,20520437),(20,123456789101,'2023-06-10','20:37:00',1866666.80,20520437),(21,234501928374,'2023-06-10','21:08:15',2250000.00,20520437),(22,789543021867,'2023-06-10','22:31:56',1916666.50,20520437),(23,135792468111,'2023-06-10','22:37:14',57441668.00,20520437),(24,109876543210,'2023-06-10','22:46:30',16791666.00,20520437),(25,759032816094,'2023-06-10','23:44:09',687500.00,20520437),(26,123456789012,'2023-06-11','07:16:13',4050000.00,20520437),(27,123456789000,'2023-06-11','10:21:14',9987500.00,20520437),(28,987659876598,'2023-06-11','10:25:45',5147500.00,20520437),(29,234501928374,'2023-06-11','10:26:21',362500.00,20520437),(30,987659876598,'2023-06-11','11:59:36',3001666.80,20520437),(31,123456789012,'2023-06-11','12:07:14',4940000.00,20520437),(32,987654321012,'2023-06-11','22:50:23',8666666.00,20520437);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `bookings`
--

DROP TABLE IF EXISTS `bookings`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bookings` (
  `name` varchar(45) NOT NULL,
  `email` varchar(45) DEFAULT NULL,
  `checkin` datetime DEFAULT NULL,
  `checkout` datetime DEFAULT NULL,
  `adults` int DEFAULT NULL,
  `children` int DEFAULT NULL,
  `room` int DEFAULT NULL,
  `special_requests` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bookings`
--

LOCK TABLES `bookings` WRITE;
/*!40000 ALTER TABLE `bookings` DISABLE KEYS */;
INSERT INTO `bookings` VALUES ('Group 1','20520437@gm.uit.edu.vn',NULL,NULL,0,0,0,NULL),('Trần Tiến Đạt','20520437@gm.uit.edu.vn',NULL,NULL,0,0,0,NULL);
/*!40000 ALTER TABLE `bookings` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkin`
--

DROP TABLE IF EXISTS `checkin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkin` (
  `Checkin_ID` bigint NOT NULL AUTO_INCREMENT,
  `Guest_ID` bigint NOT NULL,
  `Room_ID` int NOT NULL,
  `Checkin_Date` datetime NOT NULL,
  `Expected_Checkout_Date` datetime DEFAULT NULL,
  `Number_OfGuest` tinyint NOT NULL,
  `Employee_ID` bigint NOT NULL,
  PRIMARY KEY (`Checkin_ID`),
  KEY `checkin-employee_idx` (`Employee_ID`),
  KEY `checkin-room_idx` (`Room_ID`),
  KEY `checkin-guest_idx` (`Guest_ID`),
  CONSTRAINT `checkin-employee` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`),
  CONSTRAINT `checkin-guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`),
  CONSTRAINT `checkin-room` FOREIGN KEY (`Room_ID`) REFERENCES `room` (`Room_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=118 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkin`
--

LOCK TABLES `checkin` WRITE;
/*!40000 ALTER TABLE `checkin` DISABLE KEYS */;
INSERT INTO `checkin` VALUES (77,123456789101,102,'2023-06-11 12:30:00','2023-06-14 12:30:00',1,20520437),(96,759032816094,403,'2023-06-10 12:30:00','2023-06-19 12:30:00',1,20520437),(104,321098765432,304,'2023-06-09 12:30:00','2023-06-11 03:30:00',1,20520437),(106,405681392751,405,'2023-06-11 12:30:00','2023-06-20 12:30:00',1,20520437),(107,405681392751,406,'2023-06-11 12:30:00','2023-06-20 12:30:00',2,20520437),(108,405681392751,501,'2023-06-11 12:30:00','2023-06-20 12:30:00',3,20520437),(109,405681392751,502,'2023-06-11 12:30:00','2023-06-20 12:30:00',1,20520437),(110,405681392751,503,'2023-06-11 12:30:00','2023-06-20 12:30:00',3,20520437),(112,987659876598,506,'2023-06-09 12:30:00','2023-06-11 11:30:00',2,20520437),(113,123456789000,602,'2023-06-09 12:30:00','2023-06-11 12:30:00',1,20520437),(114,123456789012,101,'2023-06-11 12:30:00','2023-06-11 20:30:00',2,20520437);
/*!40000 ALTER TABLE `checkin` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkin_history`
--

DROP TABLE IF EXISTS `checkin_history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkin_history` (
  `Checkin_ID` bigint NOT NULL,
  `Guest_ID` bigint DEFAULT NULL,
  `Room_ID` smallint DEFAULT NULL,
  `Checkin_Date` datetime DEFAULT NULL,
  `Expected_Checkout_Date` datetime DEFAULT NULL,
  `Number_OfGuest` tinyint DEFAULT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`Checkin_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkin_history`
--

LOCK TABLES `checkin_history` WRITE;
/*!40000 ALTER TABLE `checkin_history` DISABLE KEYS */;
INSERT INTO `checkin_history` VALUES (71,123456789012,101,'2023-05-08 12:30:00','2023-06-08 12:30:00',2,20520437),(72,123456789012,102,'2023-05-08 12:30:00','2023-06-08 12:30:00',1,20520437),(73,123456789012,101,'2023-06-09 12:30:00','2023-06-11 12:30:00',2,20520437),(74,123456789012,101,'2023-06-09 12:30:00','2023-06-11 12:30:00',2,20520437),(75,123456789101,102,'2023-06-09 12:30:00','2023-06-10 12:30:00',2,20520437),(76,234501928374,103,'2023-06-09 12:30:00','2023-06-10 12:30:00',2,20520437),(78,987654321012,201,'2023-06-10 12:30:00','2023-06-11 12:30:00',2,20520437),(79,987654321012,202,'2023-06-10 12:30:00','2023-06-11 12:30:00',2,20520437),(80,987654321012,301,'2023-06-10 12:30:00','2023-06-11 12:30:00',2,20520437),(81,987654321012,302,'2023-06-10 12:30:00','2023-06-11 12:30:00',2,20520437),(82,987659876598,406,'2023-06-10 12:30:00','2023-06-22 12:30:00',1,20520437),(83,987659876598,501,'2023-06-10 12:30:00','2023-06-22 12:30:00',2,20520437),(84,987659876598,502,'2023-06-10 12:30:00','2023-06-22 12:30:00',3,20520437),(85,987659876598,505,'2023-06-10 12:30:00','2023-06-22 12:30:00',4,20520437),(86,789543021867,404,'2023-06-10 12:30:00','2023-06-10 12:30:00',2,20520437),(87,789543021867,405,'2023-06-10 12:30:00','2023-06-10 12:30:00',2,20520437),(88,789543021867,503,'2023-06-10 12:30:00','2023-06-10 12:30:00',3,20520437),(89,135792468111,403,'2023-06-01 12:30:00','2023-06-11 12:30:00',1,20520437),(90,135792468111,404,'2023-06-01 12:30:00','2023-06-10 12:30:00',2,20520437),(91,135792468111,405,'2023-06-01 12:30:00','2023-06-10 12:30:00',1,20520437),(92,135792468111,503,'2023-06-01 12:30:00','2023-06-10 12:30:00',2,20520437),(93,109876543210,405,'2023-06-05 12:30:00','2023-06-10 12:30:00',2,20520437),(94,109876543210,504,'2023-06-05 12:30:00','2023-06-10 12:30:00',2,20520437),(95,759032816094,403,'2023-06-10 12:30:00','2023-06-11 12:30:00',1,20520437),(97,234501928374,404,'2023-06-11 07:09:00','2023-06-13 12:30:00',2,20520437),(98,123456789012,101,'2023-06-10 12:30:00','2023-06-15 12:30:00',1,20520437),(99,123456789012,103,'2023-06-10 12:30:00','2023-06-15 12:30:00',1,20520437),(100,123456789012,106,'2023-06-10 12:30:00','2023-06-15 12:30:00',1,20520437),(101,123456789012,305,'2023-06-10 12:30:00','2023-06-15 12:30:00',1,20520437),(105,123456789000,204,'2023-06-05 12:30:00','2023-06-11 12:30:00',1,20520437),(111,987659876598,505,'2023-06-09 12:30:00','2023-06-11 11:30:00',2,20520437),(115,123456789012,404,'2023-06-10 12:30:00','2023-06-11 12:00:00',2,20520437),(116,123456789012,504,'2023-06-10 12:30:00','2023-06-11 12:00:00',2,20520437),(117,123456789012,505,'2023-06-10 12:30:00','2023-06-11 12:00:00',2,20520437);
/*!40000 ALTER TABLE `checkin_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkout`
--

DROP TABLE IF EXISTS `checkout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkout` (
  `Checkout_ID` bigint NOT NULL AUTO_INCREMENT,
  `Guest_ID` bigint NOT NULL,
  `Room_ID` smallint DEFAULT NULL,
  `Checkout_Time` time NOT NULL,
  `Checkout_Date` date DEFAULT NULL,
  `Total_Hours` int DEFAULT NULL,
  `Service_Total` decimal(10,2) DEFAULT NULL,
  `Room_Total` decimal(12,2) DEFAULT NULL,
  `Total` decimal(12,2) DEFAULT NULL,
  `Employee_ID` bigint NOT NULL,
  `Over_Checkout` int DEFAULT NULL,
  PRIMARY KEY (`Checkout_ID`),
  KEY `checkout-guest_idx` (`Guest_ID`),
  KEY `checkout-employee_idx` (`Employee_ID`),
  CONSTRAINT `checkout-employee` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`),
  CONSTRAINT `checkout-guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=144 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout`
--

LOCK TABLES `checkout` WRITE;
/*!40000 ALTER TABLE `checkout` DISABLE KEYS */;
INSERT INTO `checkout` VALUES (85,123456789012,101,'20:43:23','2023-05-08',752,0.00,31333334.00,31333334.00,20520437,8),(86,123456789012,102,'20:43:23','2023-04-08',752,0.00,43866668.00,43866668.00,20520437,8),(112,123456789012,101,'18:45:26','2023-06-10',30,0.00,1250000.00,1250000.00,20520437,0),(113,123456789012,101,'20:33:19','2023-06-10',32,0.00,1333333.40,1333333.40,20520437,0),(114,123456789101,102,'20:37:00','2023-06-10',32,0.00,1866666.80,1866666.80,20520437,8),(115,234501928374,103,'21:08:15','2023-06-10',32,250000.00,2000000.00,2250000.00,20520437,8),(116,789543021867,404,'22:31:56','2023-06-10',10,0.00,708333.30,708333.30,20520437,10),(117,789543021867,405,'22:31:56','2023-06-10',10,0.00,583333.30,583333.30,20520437,10),(118,789543021867,503,'22:31:56','2023-06-10',10,0.00,625000.00,625000.00,20520437,10),(119,135792468111,403,'22:37:14','2023-06-10',226,0.00,14125000.00,14125000.00,20520437,0),(120,135792468111,404,'22:37:14','2023-06-10',226,0.00,16008334.00,16008334.00,20520437,10),(121,135792468111,405,'22:37:14','2023-06-10',226,0.00,13183334.00,13183334.00,20520437,10),(122,135792468111,503,'22:37:14','2023-06-10',226,0.00,14125000.00,14125000.00,20520437,10),(123,109876543210,405,'22:46:30','2023-06-10',130,0.00,7583333.00,7583333.00,20520437,10),(124,109876543210,504,'22:46:30','2023-06-10',130,0.00,9208333.00,9208333.00,20520437,10),(125,759032816094,403,'23:44:09','2023-06-10',11,0.00,687500.00,687500.00,20520437,0),(126,123456789012,101,'07:16:13','2023-06-11',18,0.00,750000.00,750000.00,20520437,0),(127,123456789012,103,'07:16:13','2023-06-11',18,0.00,1125000.00,1125000.00,20520437,0),(128,123456789012,106,'07:16:13','2023-06-11',18,0.00,1125000.00,1125000.00,20520437,0),(129,123456789012,305,'07:16:13','2023-06-11',18,0.00,1050000.00,1050000.00,20520437,0),(130,123456789000,204,'10:21:14','2023-06-11',141,0.00,9987500.00,9987500.00,20520437,0),(131,987659876598,406,'10:25:45','2023-06-11',21,330000.00,1312500.00,1642500.00,20520437,0),(132,987659876598,501,'10:25:45','2023-06-11',21,180000.00,875000.00,1055000.00,20520437,0),(133,987659876598,502,'10:25:45','2023-06-11',21,0.00,1225000.00,1225000.00,20520437,0),(134,987659876598,505,'10:25:45','2023-06-11',21,0.00,1225000.00,1225000.00,20520437,0),(135,234501928374,404,'10:26:21','2023-06-11',3,150000.00,212500.00,362500.00,20520437,0),(136,987659876598,505,'11:59:36','2023-06-11',47,260000.00,2741666.80,3001666.80,20520437,0),(137,123456789012,404,'12:07:14','2023-06-11',23,60000.00,1629166.60,1689166.60,20520437,0),(138,123456789012,504,'12:07:14','2023-06-11',23,280000.00,1629166.60,1909166.60,20520437,0),(139,123456789012,505,'12:07:14','2023-06-11',23,0.00,1341666.60,1341666.60,20520437,0),(140,987654321012,201,'22:50:23','2023-06-11',34,200000.00,1700000.00,1900000.00,20520437,10),(141,987654321012,202,'22:50:23','2023-06-11',34,0.00,2266666.50,2266666.50,20520437,10),(142,987654321012,301,'22:50:23','2023-06-11',34,350000.00,1416666.60,1766666.60,20520437,10),(143,987654321012,302,'22:50:23','2023-06-11',34,750000.00,1983333.20,2733333.20,20520437,10);
/*!40000 ALTER TABLE `checkout` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `coupon`
--

DROP TABLE IF EXISTS `coupon`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `coupon` (
  `Coupon_ID` varchar(45) NOT NULL,
  `Guest_ID` bigint DEFAULT NULL,
  `Discount_Percentage` tinyint DEFAULT NULL,
  `Coupon_Status` enum('Đã sử dụng','Khả dụng') DEFAULT NULL,
  PRIMARY KEY (`Coupon_ID`),
  KEY `coupon_guest_idx` (`Guest_ID`),
  CONSTRAINT `coupon_guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES ('123456789000FirstCheckin',123456789000,20,'Khả dụng'),('759032816094FirstCheckin',759032816094,20,'Khả dụng');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Temporary view structure for view `doanhthu`
--

DROP TABLE IF EXISTS `doanhthu`;
/*!50001 DROP VIEW IF EXISTS `doanhthu`*/;
SET @saved_cs_client     = @@character_set_client;
/*!50503 SET character_set_client = utf8mb4 */;
/*!50001 CREATE VIEW `doanhthu` AS SELECT 
 1 AS `Bill_ID`,
 1 AS `Guest_Name`,
 1 AS `Room_ID`,
 1 AS `Bill_Date`,
 1 AS `TienPhong`,
 1 AS `TienDichVu`,
 1 AS `Total_Cost`*/;
SET character_set_client = @saved_cs_client;

--
-- Table structure for table `employee`
--

DROP TABLE IF EXISTS `employee`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `employee` (
  `Employee_ID` bigint NOT NULL,
  `Employee_Name` varchar(45) NOT NULL,
  `Employee_DateofBirth` date DEFAULT NULL,
  `Employee_Gender` enum('Nam','Nữ','Khác') DEFAULT NULL,
  `Employee_Address` varchar(100) NOT NULL,
  `Employee_Phone` varchar(10) NOT NULL,
  `Employee_Email` varchar(45) DEFAULT NULL,
  `Employee_CCCD` varchar(12) DEFAULT NULL,
  `Employee_Position` varchar(45) NOT NULL,
  `Employee_Salary` decimal(13,3) NOT NULL,
  `Employee_BeginDate` date DEFAULT NULL,
  `Employee_Status` enum('Có mặt','Vắng') DEFAULT NULL,
  PRIMARY KEY (`Employee_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `employee`
--

LOCK TABLES `employee` WRITE;
/*!40000 ALTER TABLE `employee` DISABLE KEYS */;
INSERT INTO `employee` VALUES (20520319,'Võ Lục Thanh Trà','2002-01-01','Nam','Linh Trung, Thủ Đức','0912345678','thanhtra@example.com','234567890123','Quản lý',15000000.000,'2022-01-01','Có mặt'),(20520437,'Trần Tiến Đạt','2002-02-11','Nam','Linh Trung, Thu Duc','0328950020','20520437@gm.uit.edu.vn','012345678910','Quản lý',15000.000,'2024-04-23','Vắng'),(20520469,'Nguyễn Đức Duy','2002-01-01','Nam','Linh Trung, Thủ Đức','0345678912','ducduy@example.com','345678901234','Quản lý',15000000.000,'2022-01-01','Có mặt'),(20520510,'Đặng Thái Hòa','2002-01-01','Nam','Linh Trung, Thủ Đức','0985678912','thaihoa@example.com','456789012345','Quản lý',15000000.000,'2022-01-01','Có mặt'),(20520511,'Nguyễn Minh Hậu','2002-01-01','Nam','Linh Chiểu, Thủ Đức','0987546713','minhhau@example.com','12312144354','Nhân viên',10000000.000,'2022-01-01','Có mặt'),(20520512,'Trần Thanh Trúc','1998-05-15','Nữ','Bình Thạnh, TP.HCM','0978456123','thanhtruc@example.com','678901234567','Nhân viên',8500000.000,'2022-03-01','Có mặt'),(20520513,'Lê Văn An','1995-09-10','Nam','Quận 1, TP.HCM','0905123456','vanan@example.com','789012345678','Nhân viên',9000000.000,'2022-02-15','Có mặt'),(20520514,'Phạm Thị Bích Ngọc','1997-11-20','Nữ','Thủ Đức, TP.HCM','0987123456','bichngoc@example.com','890123456789','Nhân viên',7500000.000,'2022-04-01','Có mặt'),(20520515,'Nguyễn Thị Thu Hương','1993-12-25','Nữ','Gò Vấp, TP.HCM','0967123456','thuhuong@example.com','901234567890','Nhân viên',8000000.000,'2022-05-01','Có mặt'),(20520517,'Nguyễn Thị Anh','1994-08-18','Nữ','Quận 10, TP.HCM','0918123456','nguyenthanh@example.com','012345678901','Hướng dẫn viên',7000000.000,'2022-06-01','Vắng'),(20520518,'Trần Văn Đức','1996-03-05','Nam','Quận 5, TP.HCM','0935123456','vanduc@example.com','123456789012','Hướng dẫn viên',9500000.000,'2022-07-01','Vắng'),(20520519,'Ngô Thị Mỹ Linh','1999-07-12','Nữ','Quận 3, TP.HCM','0989123456','mylinh@example.com','234567890123','Nhân viên',8200000.000,'2022-08-01','Có mặt'),(20520520,'Phan Văn Tài','1991-02-28','Nam','Quận 7, TP.HCM','0978123456','vantai@example.com','345678901234','Nhân viên',8800000.000,'2022-09-01','Có mặt'),(20520521,'Trần Minh Quân','1994-06-12','Nam','Quận 2, TP.HCM','0912345678','minhquan@example.com','456789012345','Nhân viên',7800000.000,'2022-10-01','Có mặt'),(20520522,'Lê Thị Kim Anh','1997-09-28','Nữ','Bình Tân, TP.HCM','0923456789','kimanhh@example.com','567890123456','Nhân viên',7200000.000,'2022-11-01','Có mặt'),(20520523,'Hoàng Văn Đạt','1993-03-20','Nam','Quận 8, TP.HCM','0934567890','dat@example.com','678901234567','Nhân viên',8400000.000,'2022-12-01','Có mặt'),(20520524,'Nguyễn Thị Trang','1998-07-05','Nữ','Quận 4, TP.HCM','0945678901','trangnguyen@example.com','789012345678','Nhân viên',7600000.000,'2023-01-01','Có mặt'),(20520525,'Trần Minh Trí','1992-11-15','Nam','Quận 6, TP.HCM','0956789012','minhtri@example.com','890123456789','Nhân viên',9200000.000,'2023-02-01','Có mặt'),(20520526,'Nguyễn Thị Lan','1996-04-25','Nữ','Quận 9, TP.HCM','0967890123','lannguyen@example.com','901234567890','Nhân viên',8900000.000,'2023-03-01','Có mặt'),(20520527,'Trần Văn Hưng','1995-02-10','Nam','Quận 11, TP.HCM','0978901234','vanhung@example.com','012345678901','Nhân viên',8300000.000,'2023-04-01','Có mặt'),(20520528,'Lê Thị Quỳnh','1999-11-12','Nữ','Quận 12, TP.HCM','0989012345','quynhle@example.com','123456789012','Nhân viên',7700000.000,'2023-05-01','Có mặt'),(20520529,'Phạm Văn Tuấn','1993-09-18','Nam','Quận 10, TP.HCM','0990123456','vantuan@example.com','234567890123','Nhân viên',9100000.000,'2023-06-01','Có mặt'),(20520530,'Nguyễn Thị Hồng','1997-12-22','Nữ','Quận 7, TP.HCM','0912345678','hongnguyen@example.com','345678901234','Nhân viên',8400000.000,'2023-07-01','Có mặt');
/*!40000 ALTER TABLE `employee` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guest`
--

DROP TABLE IF EXISTS `guest`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guest` (
  `Guest_ID` bigint NOT NULL,
  `Guest_Name` varchar(45) NOT NULL,
  `Number_Of_Checkin` int DEFAULT NULL,
  `Guest_Tier` enum('Mới','Bạc','Vàng','Kim cương') DEFAULT NULL,
  `Guest_BirthYear` year DEFAULT NULL,
  `Guest_Phone` varchar(15) NOT NULL,
  `Guest_Notes` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Guest_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest`
--

LOCK TABLES `guest` WRITE;
/*!40000 ALTER TABLE `guest` DISABLE KEYS */;
INSERT INTO `guest` VALUES (109876543210,'Karen Adams',3,'Kim cương',1983,'0987654321','Needs a room with a balcony'),(123450987654,'Christopher Lee',3,'Mới',1988,'0912873456','Requires a vegetarian meal'),(123456789000,'John Does',1,'Bạc',2000,'0323456789',''),(123456789012,'John Doe',9,'Bạc',1990,'0924184088','Likes extra pillows'),(123456789101,'Brian Wilson',5,'Mới',1989,'0912345678','Requires an early check-in'),(135792468111,'Michael Jackson',2,'Kim cương',1987,'0987654321','yehee'),(162749503871,'Amelia Miller',4,'Mới',1981,'0912345678','Requires a room with a working desk'),(165098374512,'Emma Miller',4,'Mới',1981,'0912347650','Requires a room with a working desk'),(209384756109,'Matthew Turner',5,'Mới',1979,'0987561039','Needs an extra bed'),(210987654321,'Laura Davis',4,'Mới',1980,'0987123456','Allergic to scented products'),(234501928374,'Matthew Turner',4,'Mới',1994,'0912876543','Requires extra towels'),(234567890123,'Robert Brown',2,'Mới',1992,'0912876543','Requests a wake-up call'),(273598106741,'Ava Walker',2,'Mới',1991,'0912345678','Requires a quiet room'),(298457610923,'Emily Thompson',1,'Mới',1995,'0912874596','Requests a non-allergenic pillow'),(321098765432,'Elizabeth Turner',6,'Mới',1979,'0987654321','Needs an extra bed'),(345601928374,'Jennifer Phillips',1,'Mới',1990,'0987123456','Has a special dietary restriction'),(345678901234,'Jessica Anderson',1,'Mới',1989,'0987654321','Requires a crib for a baby'),(395817264019,'Henry White',2,'Mới',1990,'0987654321','Prefers a room with a city view'),(405681392751,'Sophia Thompson',2,'Mới',1995,'0912876534','Requests a non-allergenic pillow'),(415630827941,'Ethan Smith',1,'Mới',1988,'0987123456','Has a specific room preference'),(456781234567,'Ryan Martinez',1,'Mới',1993,'0912347890','Requests a late check-out'),(456789012345,'Michael Johnson',1,'Mới',1995,'0987654321','Prefers a room with a view'),(543210987654,'Sarah Miller',1,'Mới',1988,'0987123456','Has a specific room preference'),(560943817256,'Michelle Anderson',3,'Mới',1989,'0912384756','Requires an early check-in'),(561093827456,'Olivia Walker',2,'Mới',1991,'0912347609','Requires a quiet room'),(567890123456,'Emily Davis',5,'Mới',1978,'0987123456','Needs an extra blanket'),(567890192837,'Rebecca Baker',5,'Mới',1977,'0987123654','Needs a room with a mini-fridge'),(621098347510,'William Turner',5,'Mới',1979,'0987123456','Needs an extra bed'),(678901234567,'Daniel Wilson',4,'Mới',1983,'0912345678','Has a late check-in'),(678919283746,'Kevin Rodriguez',4,'Mới',1982,'0912876543','Requires a room with a king-size bed'),(759032816094,'Daniel Smith',2,'Mới',1988,'0987123094','Has a specific room preference'),(765092384561,'Adam Wilson',2,'Mới',1983,'0987650192','Needs a room with a balcony'),(789012345678,'Sarah Williams',4,'Mới',1980,'0976543210','Allergic to feathers'),(789065432109,'Stephanie Clark',4,'Mới',1981,'0987123456','Allergic to seafood'),(789543021867,'James Adams',5,'Mới',1980,'0987123456','Allergic to scented products'),(830917562401,'Isabella Davis',3,'Mới',1989,'0912305846','Requires an early check-in'),(876543210987,'Jonathan Roberts',3,'Mới',1987,'0912876543','Requires a laptop-friendly workspace'),(890123456789,'David Taylor',3,'Mới',1987,'0912765432','Has a pet allergy'),(890198273645,'Andrew Hill',3,'Mới',1986,'0912345678','Requires a room with a view'),(901234567890,'Amanda Thompson',2,'Mới',1991,'0987654321','Prefers a room on a higher floor'),(901287364509,'Michelle Thompson',2,'Mới',1992,'0987123654','Prefers a room on the ground floor'),(908723146501,'Mia Roberts',3,'Mới',1987,'0912876543','Requires a laptop-friendly workspace'),(935126480971,'Jackson White',2,'Mới',1990,'0987650142','Prefers a room with a city view'),(938561092374,'Ryan Adams',4,'Mới',1980,'0987120956','Allergic to scented products'),(956140823791,'Benjamin Wilson',2,'Mới',1983,'0987654321','Needs a room with a balcony'),(983741650921,'Sophia Roberts',3,'Mới',1987,'0912874095','Requires a laptop-friendly workspace'),(987654321012,'Christopher Walker',3,'Mới',1991,'0912345678','Requires a quiet room'),(987654321098,'Jane Smith',2,'Mới',1985,'0912345678','Requires a non-smoking room'),(987659876598,'Melissa Harris',4,'Bạc',1984,'0987123654','Needs a room with a bathtub'),(999999999999,'Trần Tiến Đạt',0,'Mới',2000,'0924184088','abc');
/*!40000 ALTER TABLE `guest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `log`
--

DROP TABLE IF EXISTS `log`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `log` (
  `Log_Number` bigint NOT NULL AUTO_INCREMENT,
  `Log_Time` datetime DEFAULT NULL,
  `Log_Index` varchar(45) DEFAULT NULL,
  `Log_Status` varchar(45) DEFAULT NULL,
  `Log_Detail` varchar(500) DEFAULT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`Log_Number`),
  KEY `log_employee_idx` (`Employee_ID`),
  CONSTRAINT `log_employee` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (8,'2023-06-10 18:44:35','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 101',20520437),(9,'2023-06-10 19:51:21','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 101',20520437),(10,'2023-06-10 20:35:24','Nhận phòng','Thành công','Checkin cho khách hàng 123456789101 vào phòng 102',20520437),(11,'2023-06-10 20:41:45','Nhận phòng','Thành công','Checkin cho khách hàng 234501928374 vào phòng 103',20520437),(12,'2023-06-10 20:51:46','Nhận phòng','Thành công','Checkin cho khách hàng 123456789101 vào phòng 102',20520437),(13,'2023-06-10 20:54:44','Nhận phòng','Thành công','Checkin cho khách hàng 987654321012 vào phòng 201',20520437),(14,'2023-06-10 20:54:44','Nhận phòng','Thành công','Checkin cho khách hàng 987654321012 vào phòng 202',20520437),(15,'2023-06-10 20:54:44','Nhận phòng','Thành công','Checkin cho khách hàng 987654321012 vào phòng 301',20520437),(16,'2023-06-10 20:54:44','Nhận phòng','Thành công','Checkin cho khách hàng 987654321012 vào phòng 302',20520437),(17,'2023-06-10 21:20:40','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 123456789012, phòng 101',20520437),(18,'2023-06-10 21:21:08','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 123456789012, phòng 304',20520437),(19,'2023-06-10 22:02:39','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 123456789012, phòng 103',20520437),(20,'2023-06-10 22:03:39','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 123456789012, phòng 101',20520437),(21,'2023-06-10 22:06:55','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 123456789012, phòng 101',20520437),(22,'2023-06-10 22:10:11','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 123456789012, phòng 101',20520437),(23,'2023-06-10 22:18:13','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 123456789012, phòng javafx.scene.control.TableColumn@2e59e3e2',20520437),(24,'2023-06-10 22:19:37','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 543210987654, phòng javafx.scene.control.TableColumn@368634fb',20520437),(25,'2023-06-10 22:29:06','Nhận phòng','Thành công','Checkin cho khách hàng 987659876598 vào phòng 406',20520437),(26,'2023-06-10 22:29:06','Nhận phòng','Thành công','Checkin cho khách hàng 987659876598 vào phòng  501',20520437),(27,'2023-06-10 22:29:06','Nhận phòng','Thành công','Checkin cho khách hàng 987659876598 vào phòng  502',20520437),(28,'2023-06-10 22:29:06','Nhận phòng','Thành công','Checkin cho khách hàng 987659876598 vào phòng  505',20520437),(29,'2023-06-10 22:30:08','Nhận phòng','Thành công','Checkin cho khách hàng 789543021867 vào phòng 404',20520437),(30,'2023-06-10 22:30:08','Nhận phòng','Thành công','Checkin cho khách hàng 789543021867 vào phòng 405',20520437),(31,'2023-06-10 22:30:08','Nhận phòng','Thành công','Checkin cho khách hàng 789543021867 vào phòng 503',20520437),(32,'2023-06-10 22:34:24','Thêm khách hàng','Thành công','Thêm khách hàng có CCCD 135792468111 vào cơ sở dữ liệu.',20520437),(33,'2023-06-10 22:34:39','Sửa khách hàng','Thành công','Sửa thông khách hàng có CCCD 135792468111 trong cơ sở dữ liệu.',20520437),(34,'2023-06-10 22:35:33','Nhận phòng','Thành công','Checkin cho khách hàng 135792468111 vào phòng 403',20520437),(35,'2023-06-10 22:35:33','Nhận phòng','Thành công','Checkin cho khách hàng 135792468111 vào phòng  404',20520437),(36,'2023-06-10 22:35:33','Nhận phòng','Thành công','Checkin cho khách hàng 135792468111 vào phòng  405',20520437),(37,'2023-06-10 22:35:33','Nhận phòng','Thành công','Checkin cho khách hàng 135792468111 vào phòng  503',20520437),(38,'2023-06-10 22:45:18','Nhận phòng','Thành công','Checkin cho khách hàng 109876543210 vào phòng 405',20520437),(39,'2023-06-10 22:45:18','Nhận phòng','Thành công','Checkin cho khách hàng 109876543210 vào phòng 504',20520437),(40,'2023-06-10 23:43:33','Sửa khách hàng','Thành công','Sửa thông khách hàng có CCCD 759032816094 trong cơ sở dữ liệu.',20520437),(41,'2023-06-10 23:44:02','Nhận phòng','Thành công','Checkin cho khách hàng 759032816094 vào phòng 403',20520437),(42,'2023-06-10 23:45:24','Nhận phòng','Thành công','Checkin cho khách hàng 759032816094 vào phòng 403',20520437),(43,'2023-06-11 07:09:57','Nhận phòng','Thành công','Checkin cho khách hàng 234501928374 vào phòng 404',20520437),(44,'2023-06-11 07:14:24','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 101',20520437),(45,'2023-06-11 07:14:24','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 103',20520437),(46,'2023-06-11 07:14:24','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 106',20520437),(47,'2023-06-11 07:14:24','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 305',20520437),(48,'2023-06-11 07:47:27','Nhận phòng','Thành công','Checkin cho khách hàng 123456789000 vào phòng 304',20520437),(49,'2023-06-11 07:51:33','Xóa khách hàng','Thành công','Thêm khách hàng có CCCD 123456789000 vào cơ sở dữ liệu.',20520437),(50,'2023-06-11 07:54:07','Nhận phòng','Thành công','Checkin cho khách hàng 321098765432 vào phòng 304',20520437),(51,'2023-06-11 10:19:48','Nhận phòng','Thành công','Checkin cho khách hàng 123456789000 vào phòng 204',20520437),(52,'2023-06-11 10:28:10','Sửa khách hàng','Thành công','Sửa thông khách hàng có CCCD 123456789000 trong cơ sở dữ liệu.',20520437),(53,'2023-06-11 10:29:13','Nhận phòng','Thành công','Checkin cho khách hàng 405681392751 vào phòng 405',20520437),(54,'2023-06-11 10:29:13','Nhận phòng','Thành công','Checkin cho khách hàng 405681392751 vào phòng 406',20520437),(55,'2023-06-11 10:29:13','Nhận phòng','Thành công','Checkin cho khách hàng 405681392751 vào phòng 501',20520437),(56,'2023-06-11 10:29:13','Nhận phòng','Thành công','Checkin cho khách hàng 405681392751 vào phòng 502',20520437),(57,'2023-06-11 10:29:13','Nhận phòng','Thành công','Checkin cho khách hàng 405681392751 vào phòng 503',20520437),(58,'2023-06-11 10:38:07','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 123456789000, phòng javafx.scene.control.TableColumn@5c102575',20520437),(59,'2023-06-11 10:44:53','Nhận phòng','Thành công','Checkin cho khách hàng 987659876598 vào phòng 505',20520437),(60,'2023-06-11 10:44:53','Nhận phòng','Thành công','Checkin cho khách hàng 987659876598 vào phòng  506',20520437),(61,'2023-06-11 10:59:41','Nhận phòng','Thành công','Checkin cho khách hàng 123456789000 vào phòng 602',20520437),(62,'2023-06-11 11:38:31','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 101',20520437),(63,'2023-06-11 11:42:44','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 273598106741, phòng javafx.scene.control.TableColumn@6cd91895',20520437),(64,'2023-06-11 12:04:51','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 404',20520437),(65,'2023-06-11 12:04:51','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng  504',20520437),(66,'2023-06-11 12:04:51','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng  505',20520437),(69,'2023-06-11 22:50:12','Hủy đặt phòng','Thành công','Đã hủy đặt phòng cho khách hàng 273598106741, phòng javafx.scene.control.TableColumn@c32f29c',20520437);
/*!40000 ALTER TABLE `log` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `Reservation_ID` bigint NOT NULL AUTO_INCREMENT,
  `Guest_ID` bigint DEFAULT NULL,
  `Reserve_Date` datetime DEFAULT NULL,
  `Room_ID` int DEFAULT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  `Expected_Checkin_Date` datetime DEFAULT NULL,
  `Number_ofGuest` int DEFAULT NULL,
  `Expected_Checkout_Date` datetime DEFAULT NULL,
  `Reservation_Status` enum('Quá hạn','Hủy','Đặt trước','Thành công') DEFAULT NULL,
  PRIMARY KEY (`Reservation_ID`),
  KEY `reservation-guest_idx` (`Guest_ID`),
  KEY `reservation_room_idx` (`Room_ID`),
  CONSTRAINT `reservation_guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`),
  CONSTRAINT `reservation_room` FOREIGN KEY (`Room_ID`) REFERENCES `room` (`Room_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (58,123456789012,'2023-06-10 20:50:18',101,20520437,'2023-06-14 12:30:00',2,'2023-06-16 12:30:00','Thành công'),(59,298457610923,'2023-06-10 20:50:56',105,20520437,'2023-06-16 12:30:00',2,'2023-06-17 12:30:00','Đặt trước'),(60,543210987654,'2023-06-10 20:52:23',106,20520437,'2023-06-10 12:30:00',2,'2023-06-13 12:30:00','Hủy'),(61,395817264019,'2023-06-10 20:53:14',702,20520437,'2023-06-11 12:30:00',2,'2023-06-13 12:30:00','Quá hạn'),(62,345678901234,'2023-06-10 20:53:56',801,20520437,'2023-06-13 12:30:00',1,'2023-06-15 12:30:00','Đặt trước'),(63,345678901234,'2023-06-10 20:53:56',802,20520437,'2023-06-13 12:30:00',2,'2023-06-15 12:30:00','Đặt trước'),(64,345678901234,'2023-06-01 20:53:56',901,20520437,'2023-06-09 12:30:00',2,'2023-06-15 12:30:00','Quá hạn'),(65,273598106741,'2023-06-01 21:15:42',205,20520437,'2023-06-09 12:30:00',2,'2023-06-12 12:30:00','Hủy'),(66,273598106741,'2023-06-01 21:15:42',206,20520437,'2023-06-09 12:30:00',3,'2023-06-12 12:30:00','Hủy'),(68,456789012345,'2023-06-10 22:26:21',401,20520437,'2023-06-10 12:30:00',2,'2023-06-14 12:30:00','Quá hạn'),(69,123456789012,'2023-06-10 22:27:05',101,20520437,'2023-06-12 12:30:00',1,'2023-06-15 12:30:00','Thành công'),(70,123456789012,'2023-06-10 22:27:05',103,20520437,'2023-06-12 12:30:00',1,'2023-06-15 12:30:00','Thành công'),(71,123456789012,'2023-06-10 22:27:05',106,20520437,'2023-06-12 12:30:00',1,'2023-06-15 12:30:00','Thành công'),(72,123456789012,'2023-06-10 22:27:05',305,20520437,'2023-06-12 12:30:00',1,'2023-06-15 12:30:00','Thành công'),(73,456789012345,'2023-06-10 22:28:02',402,20520437,'2023-06-10 12:30:00',2,'2023-06-14 12:30:00','Quá hạn'),(74,123456789012,'2023-06-11 07:19:52',101,20520437,'2023-06-12 12:30:00',2,'2023-06-14 12:30:00','Thành công'),(76,123456789000,'2023-06-11 10:30:11',505,20520437,'2023-06-12 12:30:00',1,'2023-06-14 12:30:00','Thành công'),(77,999999999999,'2023-06-11 12:39:00',303,20520437,'2023-06-12 12:33:00',2,'2023-06-13 12:33:00','Hủy'),(78,999999999999,'2023-06-11 12:54:19',704,20520437,'2023-06-15 12:50:00',2,'2023-06-17 12:30:00','Đặt trước');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `Room_ID` int NOT NULL,
  `Room_Type` varchar(45) NOT NULL,
  `Room_Price` decimal(10,2) NOT NULL,
  `Room_Status` enum('Trống','Đang thuê','Chờ trả','Đang sửa chữa','Đặt trước','Quá hạn') NOT NULL,
  `Room_Floor` tinyint NOT NULL,
  PRIMARY KEY (`Room_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (101,'Deluxe Suite - Đơn',1000000.00,'Chờ trả',1),(102,'Executive Room - Đơn',1400000.00,'Đang thuê',1),(103,'Family Suite - Ba',1500000.00,'Trống',1),(104,'Honeymoon Suite - Đôi',1700000.00,'Trống',1),(105,'Superior Room - Đơn',1400000.00,'Đặt trước',1),(106,'Poolside Cabana - Đôi',1500000.00,'Trống',1),(201,'Deluxe Suite - Đôi',1200000.00,'Trống',2),(202,'Executive Room - Đôi',1600000.00,'Trống',2),(204,'Honeymoon Suite - Đôi',1700000.00,'Trống',2),(205,'Superior Room - Đơn',1400000.00,'Trống',2),(206,'Poolside Cabana - Đôi',1500000.00,'Trống',2),(301,'Deluxe Suite - Đơn',1000000.00,'Trống',3),(302,'Executive Room - Đơn',1400000.00,'Trống',3),(303,'Family Suite - Ba',1500000.00,'Trống',3),(304,'Honeymoon Suite - Đôi',1700000.00,'Chờ trả',3),(305,'Superior Room - Đơn',1400000.00,'Trống',3),(306,'Poolside Cabana - Đôi',1500000.00,'Trống',3),(401,'Deluxe Suite - Đôi',1200000.00,'Quá hạn',4),(402,'Executive Room - Đôi',1600000.00,'Quá hạn',4),(403,'Family Suite - Ba',1500000.00,'Đang thuê',4),(404,'Honeymoon Suite - Đôi',1700000.00,'Trống',4),(405,'Superior Room - Đơn',1400000.00,'Đang thuê',4),(406,'Poolside Cabana - Đôi',1500000.00,'Đang thuê',4),(501,'Deluxe Suite - Đơn',1000000.00,'Đang thuê',5),(502,'Executive Room - Đơn',1400000.00,'Đang thuê',5),(503,'Family Suite - Ba',1500000.00,'Đang thuê',5),(504,'Honeymoon Suite - Đôi',1700000.00,'Trống',5),(505,'Superior Room - Đơn',1400000.00,'Trống',5),(506,'Poolside Cabana - Đôi',1500000.00,'Chờ trả',5),(601,'Deluxe Suite - Đôi',1200000.00,'Trống',6),(602,'Executive Room - Đôi',1600000.00,'Chờ trả',6),(603,'Family Suite - Ba',1500000.00,'Trống',6),(604,'Honeymoon Suite - Đôi',1700000.00,'Trống',6),(605,'Superior Room - Đơn',1400000.00,'Trống',6),(606,'Poolside Cabana - Đôi',1500000.00,'Trống',6),(701,'Deluxe Suite - Đơn',1000000.00,'Trống',7),(702,'Executive Room - Đơn',1400000.00,'Quá hạn',7),(703,'Family Suite - Ba',1500000.00,'Trống',7),(704,'Honeymoon Suite - Đôi',1700000.00,'Đặt trước',7),(705,'Superior Room - Đơn',1400000.00,'Trống',7),(706,'Poolside Cabana - Đôi',1500000.00,'Trống',7),(801,'Deluxe Suite - Đôi',1200000.00,'Đặt trước',8),(802,'Executive Room - Đôi',1600000.00,'Đặt trước',8),(803,'Family Suite - Ba',1500000.00,'Trống',8),(804,'Honeymoon Suite - Đôi',1700000.00,'Trống',8),(805,'Superior Room - Đơn',1400000.00,'Trống',8),(806,'Poolside Cabana - Đôi',1500000.00,'Trống',8),(901,'Deluxe Suite - Đơn',1000000.00,'Quá hạn',9),(902,'Executive Room - Đơn',1400000.00,'Trống',9),(903,'Family Suite - Ba',1500000.00,'Trống',9),(904,'Honeymoon Suite - Đôi',1700000.00,'Trống',9),(905,'Superior Room - Đơn',1400000.00,'Trống',9),(906,'Poolside Cabana - Đôi',1500000.00,'Trống',9),(1001,'Deluxe Suite - Đôi',1200000.00,'Trống',10),(1002,'Executive Room - Đôi',1600000.00,'Trống',10),(1003,'Family Suite - Ba',1500000.00,'Trống',10),(1004,'Honeymoon Suite - Đôi',1700000.00,'Trống',10),(1005,'Superior Room - Đơn',1400000.00,'Trống',10),(1006,'Poolside Cabana - Đôi',1500000.00,'Trống',10);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_service`
--

DROP TABLE IF EXISTS `room_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_service` (
  `Room_Service_ID` bigint NOT NULL AUTO_INCREMENT,
  `Room_ID` int NOT NULL,
  `Service_ID` int NOT NULL,
  `Service_Date` date NOT NULL,
  `Service_Time` time NOT NULL,
  `Service_Quantity` smallint NOT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  `Note` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Room_Service_ID`),
  KEY `Service-RoomService_idx` (`Service_ID`),
  KEY `rs_room_idx` (`Room_ID`),
  CONSTRAINT `rs_room` FOREIGN KEY (`Room_ID`) REFERENCES `room` (`Room_ID`),
  CONSTRAINT `rs_service` FOREIGN KEY (`Service_ID`) REFERENCES `service` (`Service_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=70 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_service`
--

LOCK TABLES `room_service` WRITE;
/*!40000 ALTER TABLE `room_service` DISABLE KEYS */;
INSERT INTO `room_service` VALUES (46,102,7,'2023-06-10','12:30:00',2,20520437,''),(52,102,11,'2023-06-11','12:30:00',1,20520437,''),(62,506,23,'2023-06-11','12:30:00',2,20520437,''),(63,506,24,'2023-06-11','12:30:00',2,20520437,''),(65,403,19,'2023-06-11','12:30:00',1,20520437,'');
/*!40000 ALTER TABLE `room_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `Service_ID` int NOT NULL AUTO_INCREMENT,
  `Service_Name` varchar(45) NOT NULL,
  `Service_Price` decimal(10,2) NOT NULL,
  `Service_Description` varchar(200) DEFAULT NULL,
  `Service_Status` enum('Khả dụng','Bận') NOT NULL,
  PRIMARY KEY (`Service_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Đơn vị: Ngàn';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (6,'Dịch vụ đưa đón sân bay',250000.00,'Đưa khách từ sân bay đến khách sạn và ngược lại','Khả dụng'),(7,'Dịch vụ thuê xe',350000.00,'Cho thuê xe ô tô hoặc xe máy','Khả dụng'),(8,'Dịch vụ giặt là',100000.00,'Giặt là và là ủi quần áo','Khả dụng'),(9,'Dịch vụ hướng dẫn du lịch',200000.00,'Hướng dẫn viên du lịch đi kèm','Bận'),(10,'Dịch vụ phòng họp',400000.00,'Cho thuê phòng họp và trang thiết bị','Khả dụng'),(11,'Dịch vụ spa',300000.00,'Trị liệu spa và mát-xa','Khả dụng'),(12,'Dịch vụ nhà hàng',150000.00,'Dịch vụ ăn uống tại nhà hàng','Khả dụng'),(13,'Dịch vụ đặt tour',180000.00,'Đặt tour du lịch và vé tham quan','Khả dụng'),(14,'Dịch vụ y tế',500000.00,'Dịch vụ y tế và chăm sóc sức khỏe','Khả dụng'),(15,'Dịch vụ wifi',50000.00,'Cung cấp internet wifi trong khách sạn','Khả dụng'),(16,'Buffet sáng',150000.00,'Dịch vụ buffet sáng với đa dạng món ăn','Khả dụng'),(17,'Phục vụ phòng',80000.00,'Dịch vụ phục vụ thức ăn và đồ uống trong phòng','Khả dụng'),(18,'Quầy bar',100000.00,'Dịch vụ phục vụ thức uống tại quầy bar','Khả dụng'),(19,'Dịch vụ đồ uống',50000.00,'Dịch vụ phục vụ đồ uống trong khuôn viên khách sạn','Khả dụng'),(20,'Dịch vụ phòng ăn',120000.00,'Dịch vụ phục vụ bữa ăn tại phòng ăn','Khả dụng'),(21,'Thực đơn đặc biệt',90000.00,'Thực đơn đặc biệt dành cho khách hàng','Khả dụng'),(22,'Dịch vụ hẹn giờ',50000.00,'Dịch vụ hẹn giờ phục vụ đồ uống','Khả dụng'),(23,'Trà chiều',60000.00,'Dịch vụ phục vụ trà chiều','Khả dụng'),(24,'Dịch vụ đồ ăn nhanh',70000.00,'Dịch vụ phục vụ đồ ăn nhanh','Khả dụng'),(25,'Dịch vụ đồ ăn tự chọn',80000.00,'Dịch vụ phục vụ đồ ăn tự chọn theo yêu cầu','Khả dụng');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tiers`
--

DROP TABLE IF EXISTS `tiers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tiers` (
  `ID` int NOT NULL AUTO_INCREMENT,
  `Tier` varchar(45) DEFAULT NULL,
  `Discount` int DEFAULT NULL,
  `Value_Condition` decimal(12,3) DEFAULT NULL,
  `Nearest_Checkouts` int DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `Tiers-Guest_idx` (`Tier`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tiers`
--

LOCK TABLES `tiers` WRITE;
/*!40000 ALTER TABLE `tiers` DISABLE KEYS */;
INSERT INTO `tiers` VALUES (1,'Kim cương',15,15000000.000,5),(2,'Vàng',10,10000000.000,3),(3,'Bạc',5,5000000.000,2),(4,'Mới',0,0.000,0);
/*!40000 ALTER TABLE `tiers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `used_service`
--

DROP TABLE IF EXISTS `used_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `used_service` (
  `Used_Service_ID` int NOT NULL,
  `Room_ID` smallint DEFAULT NULL,
  `Service_ID` int DEFAULT NULL,
  `Service_Date` date DEFAULT NULL,
  `Service_Time` time DEFAULT NULL,
  `Service_Quantity` smallint DEFAULT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  `Note` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Used_Service_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `used_service`
--

LOCK TABLES `used_service` WRITE;
/*!40000 ALTER TABLE `used_service` DISABLE KEYS */;
INSERT INTO `used_service` VALUES (45,103,6,'2023-06-10','12:30:00',1,20520437,''),(47,302,7,'2023-06-10','12:30:00',1,20520437,''),(48,301,7,'2023-06-10','12:30:00',1,20520437,''),(53,302,11,'2023-06-11','12:30:00',1,20520437,''),(54,404,12,'2023-06-11','12:30:00',1,20520437,''),(55,406,12,'2023-06-11','12:30:00',1,20520437,''),(56,406,13,'2023-06-11','12:30:00',1,20520437,''),(57,501,13,'2023-06-11','12:30:00',1,20520437,''),(58,201,15,'2023-06-11','12:30:00',1,20520437,''),(59,201,12,'2023-06-11','12:30:00',1,20520437,''),(60,302,18,'2023-06-11','12:30:00',1,20520437,''),(61,505,23,'2023-06-11','12:30:00',2,20520437,''),(64,505,24,'2023-06-11','12:30:00',2,20520437,''),(66,404,23,'2023-06-11','12:30:00',1,20520437,''),(67,504,23,'2023-06-11','12:30:00',1,20520437,''),(68,504,24,'2023-06-11','12:30:00',1,20520437,''),(69,504,16,'2023-06-11','12:30:00',1,20520437,'');
/*!40000 ALTER TABLE `used_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Final view structure for view `doanhthu`
--

/*!50001 DROP VIEW IF EXISTS `doanhthu`*/;
/*!50001 SET @saved_cs_client          = @@character_set_client */;
/*!50001 SET @saved_cs_results         = @@character_set_results */;
/*!50001 SET @saved_col_connection     = @@collation_connection */;
/*!50001 SET character_set_client      = utf8mb4 */;
/*!50001 SET character_set_results     = utf8mb4 */;
/*!50001 SET collation_connection      = utf8mb4_0900_ai_ci */;
/*!50001 CREATE ALGORITHM=UNDEFINED */
/*!50013 DEFINER=`root`@`localhost` SQL SECURITY DEFINER */
/*!50001 VIEW `doanhthu` AS select `b`.`Bill_ID` AS `Bill_ID`,`g`.`Guest_Name` AS `Guest_Name`,`r`.`Room_ID` AS `Room_ID`,`b`.`Bill_Date` AS `Bill_Date`,(`r`.`Room_Price` * `c`.`Total_Hours`) AS `TienPhong`,sum((`rs`.`Service_Quantity` * `s`.`Service_Price`)) AS `TienDichVu`,`b`.`Total_Cost` AS `Total_Cost` from (((((`bill` `b` join `guest` `g` on((`b`.`Guest_ID` = `g`.`Guest_ID`))) join `checkout` `c` on((`c`.`Guest_ID` = `g`.`Guest_ID`))) join `room` `r` on((`r`.`Room_ID` = `c`.`Room_ID`))) join `room_service` `rs` on((`r`.`Room_ID` = `rs`.`Room_ID`))) join `service` `s` on((`rs`.`Service_ID` = `s`.`Service_ID`))) group by `b`.`Bill_ID`,`g`.`Guest_Name`,`r`.`Room_ID`,`b`.`Bill_Date`,`TienPhong`,`b`.`Total_Cost` */;
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

-- Dump completed on 2023-06-12  8:09:46
