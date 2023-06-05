-- MySQL dump 10.13  Distrib 8.0.32, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: hotelmanagement
-- ------------------------------------------------------
-- Server version	8.0.32

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
  `Employee_ID` varchar(12) NOT NULL,
  `account_name` varchar(45) NOT NULL,
  `account_password` varchar(45) NOT NULL,
  `isAdmin` enum('1','0') DEFAULT NULL,
  PRIMARY KEY (`idAccount`),
  UNIQUE KEY `account_nam_UNIQUE` (`account_name`),
  KEY `account-employee_idx` (`Employee_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1,'20520437','TienDat','admin','1');
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
  PRIMARY KEY (`Bill_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (3,123456789013,'2023-05-06','14:04:27',3631.00,20520437),(4,123456789012,'2023-05-06','14:15:46',7017.00,20520437),(5,123456789014,'2023-05-06','14:15:53',4047.00,20520437),(6,123456789013,'2023-05-06','14:15:58',2472.00,20520437),(7,123456789012,'2023-05-28','11:07:25',22875.00,20520437),(8,123456789013,'2023-05-29','13:03:10',83.00,20520437),(9,123456789012,'2023-05-31','09:22:44',13198.00,20520437),(10,123456789012,'2023-06-01','13:47:35',-10917.00,20520437),(11,123456789012,'2023-06-01','21:00:01',4565.00,20520437),(12,123456789012,'2023-06-01','21:36:57',-792.00,20520437),(13,123456789012,'2023-06-02','11:10:15',-1083.00,20520437),(14,123456789013,'2023-06-03','12:01:17',9352.00,20520437),(15,123456789012,'2023-06-03','12:06:00',42.00,20520437),(16,123456789012,'2023-06-05','12:28:55',301960.28,20520437);
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
  `Room_ID` smallint NOT NULL,
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
) ENGINE=InnoDB AUTO_INCREMENT=71 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkin`
--

LOCK TABLES `checkin` WRITE;
/*!40000 ALTER TABLE `checkin` DISABLE KEYS */;
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
INSERT INTO `checkin_history` VALUES (1,123456789012,101,'2023-05-20 00:00:00','2023-05-21 00:00:00',2,20520437),(39,123456789013,101,'2023-05-05 09:30:00','2023-05-06 12:00:00',2,20520437),(40,123456789012,102,'2023-05-05 13:10:00','2023-05-07 12:00:00',2,20520437),(41,123456789012,101,'2023-05-05 12:40:00','2023-05-07 15:40:00',1,20520437),(42,123456789014,103,'2023-05-05 18:30:00','2023-05-07 18:30:00',3,20520437),(43,123456789014,104,'2023-05-05 18:30:00','2023-05-07 18:30:00',3,20520437),(44,123456789013,105,'2023-05-05 12:30:00','2023-05-07 12:30:00',2,20520437),(45,123456789013,106,'2023-05-05 12:30:00','2023-05-07 12:30:00',2,20520437),(46,123456789012,102,'2023-05-26 11:30:00','2023-05-28 12:30:00',1,20520437),(47,123456789012,104,'2023-05-26 11:30:00','2023-05-28 12:30:00',1,20520437),(48,123456789012,104,'2023-05-28 12:30:00','2023-05-29 06:30:00',2,20520437),(49,123456789013,102,'2023-05-29 11:40:00','2023-05-30 12:30:00',4,20520437),(50,123456789013,101,'2023-05-29 11:40:00','2023-05-30 12:30:00',4,20520437),(51,123456789012,102,'2023-05-29 12:30:00','2023-05-30 12:30:00',5,20520437),(52,123456789012,101,'2023-05-29 12:30:00','2023-05-30 12:30:00',5,20520437),(53,123456789012,105,'2023-05-29 12:30:00','2023-05-30 12:30:00',5,20520437),(55,123456789012,102,'2023-05-31 12:30:00','2023-06-02 12:30:00',1,20520437),(56,123456789012,101,'2023-05-31 12:30:00','2023-06-02 12:30:00',2,20520437),(57,123456789012,102,'2023-06-01 12:30:00','2023-06-02 12:30:00',1,20520437),(58,123456789012,103,'2023-06-01 12:30:00','2023-06-02 12:30:00',1,20520437),(59,123456789013,105,'2023-06-02 12:30:00','2023-06-04 12:30:00',2,20520437),(60,123456789013,106,'2023-06-02 12:30:00','2023-06-04 12:30:00',3,20520437),(61,123456789012,101,'2023-06-02 12:30:00','2023-06-04 12:30:00',2,20520437),(62,123456789012,102,'2023-06-02 12:30:00','2023-06-04 12:30:00',3,20520437),(63,123456789012,103,'2023-06-01 12:30:00','2023-06-03 12:30:00',1,20520437),(64,123456789013,104,'2023-06-01 12:30:00','2023-06-02 14:30:00',1,20520437),(65,123456789012,101,'2023-06-02 12:30:00','2023-06-04 12:30:00',1,20520437),(66,123456789012,102,'2023-06-03 12:30:00','2023-06-04 12:30:00',1,20520437),(67,123456789012,103,'2023-06-02 12:00:00','2023-06-03 12:00:00',1,20520437),(68,123456789012,101,'2023-06-03 10:30:00','2023-06-04 12:30:00',2,20520437),(69,123456789012,102,'2023-06-03 12:30:00','2023-06-04 12:30:00',3,20520437),(70,123456789012,101,'2023-06-03 12:30:00','2023-06-04 12:30:00',3,20520437);
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
) ENGINE=InnoDB AUTO_INCREMENT=85 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout`
--

LOCK TABLES `checkout` WRITE;
/*!40000 ALTER TABLE `checkout` DISABLE KEYS */;
INSERT INTO `checkout` VALUES (47,123456789013,101,'14:04:27','2023-05-06',28,2464.00,1167.00,3631.00,20520437,NULL),(48,123456789012,102,'14:15:46','2023-05-06',25,2470.00,1042.00,3512.00,20520437,NULL),(49,123456789012,101,'14:15:46','2023-05-06',25,2464.00,1042.00,3506.00,20520437,NULL),(50,123456789014,103,'14:15:53','2023-05-06',19,2464.00,792.00,3256.00,20520437,NULL),(51,123456789014,104,'14:15:53','2023-05-06',19,0.00,792.00,792.00,20520437,NULL),(52,123456789013,105,'14:15:58','2023-05-06',25,0.00,1.00,1.00,20520437,NULL),(53,123456789013,106,'14:15:58','2023-05-06',25,2470.00,1.00,2471.00,20520437,NULL),(54,123456789013,105,'14:15:58','2023-05-10',25,240.00,1.00,24771.00,20520437,NULL),(55,123456789012,101,'11:07:25','2023-05-28',203,0.00,18958.00,18958.00,20520437,179),(56,123456789012,102,'11:07:25','2023-05-28',47,0.00,1958.00,1958.00,20520437,0),(57,123456789012,101,'11:07:25','2023-05-28',203,0.00,18958.00,18958.00,20520437,179),(58,123456789012,102,'11:07:25','2023-05-28',47,0.00,1958.00,1958.00,20520437,0),(59,123456789012,104,'11:07:25','2023-05-28',47,0.00,1958.00,1958.00,20520437,0),(60,123456789013,102,'13:03:10','2023-05-29',1,0.00,42.00,42.00,20520437,0),(61,123456789013,101,'13:03:10','2023-05-29',1,0.00,42.00,42.00,20520437,0),(62,123456789012,104,'09:22:44','2023-05-31',68,3696.00,5833.00,9529.00,20520437,50),(63,123456789012,102,'09:22:44','2023-05-31',44,0.00,1833.00,1833.00,20520437,20),(64,123456789012,101,'09:22:44','2023-05-31',44,0.00,1833.00,1833.00,20520437,20),(65,123456789012,105,'09:22:44','2023-05-31',44,0.00,2.00,2.00,20520437,20),(67,123456789012,102,'21:00:01','2023-06-01',32,0.00,1333.00,1333.00,20520437,0),(68,123456789012,101,'21:00:01','2023-06-01',32,0.00,1333.00,1333.00,20520437,0),(69,123456789012,102,'21:00:01','2023-06-01',8,0.00,333.00,333.00,20520437,0),(70,123456789012,103,'21:00:01','2023-06-01',8,1232.00,333.00,1565.00,20520437,0),(71,123456789012,101,'21:36:57','2023-06-01',-14,0.00,-583.00,-583.00,20520437,0),(72,123456789012,102,'21:36:57','2023-06-01',-14,0.00,-583.00,-583.00,20520437,0),(73,123456789012,103,'21:36:57','2023-06-01',9,0.00,375.00,375.00,20520437,0),(74,123456789012,101,'11:10:15','2023-06-02',-1,0.00,-42.00,-42.00,20520437,0),(75,123456789012,102,'11:10:15','2023-06-02',-25,0.00,-1042.00,-1042.00,20520437,0),(76,123456789012,103,'11:10:15','2023-06-02',0,0.00,0.00,0.00,20520437,0),(77,123456789013,105,'12:01:17','2023-06-03',23,3696.00,1.00,3697.00,20520437,0),(78,123456789013,105,'12:01:17','2023-06-03',23,3696.00,1.00,3697.00,20520437,0),(79,123456789013,105,'12:01:17','2023-06-03',23,3696.00,1.00,3697.00,20520437,0),(80,123456789013,106,'12:01:17','2023-06-03',23,0.00,1.00,1.00,20520437,0),(81,123456789013,104,'12:01:17','2023-06-03',47,3696.00,1958.00,5654.00,20520437,21),(82,123456789012,101,'12:06:00','2023-06-03',1,0.00,42.00,42.00,20520437,0),(83,123456789012,102,'12:28:55','2023-06-05',47,0.00,1958.33,1958.33,20520437,23),(84,123456789012,101,'12:28:55','2023-06-05',47,300000.00,1.96,300001.97,20520437,23);
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
  PRIMARY KEY (`Coupon_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `coupon`
--

LOCK TABLES `coupon` WRITE;
/*!40000 ALTER TABLE `coupon` DISABLE KEYS */;
INSERT INTO `coupon` VALUES ('123456789012_FirstCheckin',123456789012,20,'Khả dụng'),('123456789013_FirstCheckin',123456789013,20,'Khả dụng');
/*!40000 ALTER TABLE `coupon` ENABLE KEYS */;
UNLOCK TABLES;

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
  `Employee_Salary` decimal(9,3) NOT NULL,
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
INSERT INTO `employee` VALUES (20520437,'Trần Tiến Đạt','2002-02-11','Nam','Linh Trung','0328950020','20520437@gm.uit.edu.vn','012345678910','Quản lý',15000.000,'2024-04-23','Vắng'),(20520438,'abc','2001-01-01','Nam','acv','234','g','34','a',10.000,'0001-01-01','Vắng'),(20520439,'Tra','0001-01-01','Nam','a','092','a','1','a',0.000,'0001-01-01','Có mặt');
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
  `Guest_Phone` varchar(45) NOT NULL,
  `Guest_Notes` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`Guest_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest`
--

LOCK TABLES `guest` WRITE;
/*!40000 ALTER TABLE `guest` DISABLE KEYS */;
INSERT INTO `guest` VALUES (123456789012,'Nguyễn Văn B',3,'Mới',2000,'012345654','avc'),(123456789013,'Trần Văn A',1,'Mới',2000,'1234567',''),(123456789014,'Nguyễn Văn d',1,'Mới',2000,'012345654',NULL),(123456789015,'Nguyễn Văn f',1,'Mới',2000,'012345654',NULL),(123456789017,'trần',3,'Mới',2002,'09234','t');
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
  PRIMARY KEY (`Log_Number`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `log`
--

LOCK TABLES `log` WRITE;
/*!40000 ALTER TABLE `log` DISABLE KEYS */;
INSERT INTO `log` VALUES (1,'2023-06-01 00:00:00','Đặt phòng','Thành công','ab',NULL),(2,'2023-06-01 21:41:38','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 103',NULL),(3,'2023-06-03 12:05:55','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 101',NULL),(4,'2023-06-03 14:58:26','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng 102',NULL),(5,'2023-06-03 14:58:26','Nhận phòng','Thành công','Checkin cho khách hàng 123456789012 vào phòng  101',NULL);
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
  `Room_ID` smallint DEFAULT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  `Expected_Checkin_Date` datetime DEFAULT NULL,
  `Number_ofGuest` int DEFAULT NULL,
  `Expected_Checkout_Date` datetime DEFAULT NULL,
  `Reservation_Status` enum('Quá hạn','Hủy','Đặt trước','Thành công') DEFAULT NULL,
  PRIMARY KEY (`Reservation_ID`),
  KEY `reservation-guest_idx` (`Guest_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=58 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (43,123456789012,'2023-05-03 00:00:00',101,NULL,'2023-05-01 00:00:00',2,'2023-05-03 00:00:00','Thành công'),(44,123456789012,'2023-05-04 10:17:15',103,20520437,'2023-05-06 12:13:00',2,'2023-05-07 14:15:00','Thành công'),(45,123456789013,'2023-05-06 10:50:48',101,20520437,'2023-05-06 13:00:00',5,'2023-05-07 12:30:00','Thành công'),(46,123456789013,'2023-05-06 10:50:48',102,20520437,'2023-05-06 13:00:00',5,'2023-05-07 12:30:00','Thành công'),(47,123456789013,'2023-05-06 10:50:48',103,20520437,'2023-05-06 13:00:00',5,'2023-05-07 12:30:00','Thành công'),(50,123456789012,'2023-05-26 11:12:10',102,20520437,'2023-05-26 12:30:00',1,'2023-05-28 12:30:00','Thành công'),(51,123456789012,'2023-05-26 11:12:10',104,20520437,'2023-05-26 12:30:00',1,'2023-05-28 12:30:00','Thành công'),(52,123456789012,'2023-05-31 22:09:23',101,20520437,'2023-05-30 12:30:00',2,'2023-06-02 12:30:00','Thành công'),(53,123456789013,'2023-06-01 20:08:16',104,20520437,'2023-06-01 12:30:00',2,'2023-06-02 12:00:00','Thành công'),(54,123456789013,'2023-06-01 20:10:28',105,20520437,'2023-06-01 12:30:00',2,'2023-06-02 12:00:00','Thành công'),(55,123456789013,'2023-06-01 20:12:42',106,20520437,'2023-06-02 12:30:00',2,'2023-06-04 12:30:00','Thành công'),(56,123456789012,'2023-06-01 21:09:55',101,20520437,'2023-06-02 12:30:00',2,'2023-06-04 12:30:00','Thành công'),(57,123456789012,'2023-06-01 21:09:55',102,20520437,'2023-06-02 12:30:00',3,'2023-06-04 12:30:00','Thành công');
/*!40000 ALTER TABLE `reservation` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room`
--

DROP TABLE IF EXISTS `room`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room` (
  `Room_ID` smallint NOT NULL,
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
INSERT INTO `room` VALUES (101,'King',1.00,'Đang sửa chữa',1),(102,'Queen',1000.00,'Trống',1),(103,'Prince',1000.00,'Trống',1),(104,'King',1.00,'Đang sửa chữa',1),(105,'Queen',1.00,'Trống',2),(106,'Joker',1.00,'Trống',2);
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
  `Room_ID` smallint NOT NULL,
  `Service_ID` int NOT NULL,
  `Service_Date` date NOT NULL,
  `Service_Time` time NOT NULL,
  `Service_Quantity` smallint NOT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  `Note` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`Room_Service_ID`),
  KEY `Room-RoomService_idx` (`Room_ID`),
  KEY `Service-RoomService_idx` (`Service_ID`),
  CONSTRAINT `Room-RoomService` FOREIGN KEY (`Room_ID`) REFERENCES `room` (`Room_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=45 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room_service`
--

LOCK TABLES `room_service` WRITE;
/*!40000 ALTER TABLE `room_service` DISABLE KEYS */;
/*!40000 ALTER TABLE `room_service` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `service`
--

DROP TABLE IF EXISTS `service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `service` (
  `Service_ID` tinyint NOT NULL AUTO_INCREMENT,
  `Service_Name` varchar(45) NOT NULL,
  `Service_Price` decimal(10,2) NOT NULL,
  `Service_Description` varchar(200) DEFAULT NULL,
  `Service_Status` enum('Khả dụng','Bận') NOT NULL,
  PRIMARY KEY (`Service_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Đơn vị: Ngàn';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (2,'abd',100000.00,'abc','Khả dụng'),(3,'abch',3.00,'rtt','Bận'),(4,'Nước suối',10000.00,'Lavie','Khả dụng'),(5,'7up',20000.00,'7up 1 lon','Khả dụng');
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
INSERT INTO `used_service` VALUES (28,101,2,'2023-05-06','12:30:00',2,20520437,NULL),(29,102,2,'2023-05-06','14:10:00',2,20520437,NULL),(30,102,3,'2023-05-06','14:10:00',2,20520437,NULL),(31,101,2,'2023-05-06','20:00:00',2,20520437,NULL),(32,103,2,'2023-05-06','20:00:00',2,20520437,NULL),(33,106,2,'2023-05-06','13:30:00',2,20520437,NULL),(34,106,3,'2023-05-06','13:30:00',2,20520437,NULL),(35,103,2,'2023-05-30','12:30:00',1,20520437,NULL),(36,104,2,'2023-05-29','12:30:00',1,20520437,NULL),(37,104,2,'2023-05-29','12:30:00',1,20520437,NULL),(38,104,2,'2023-05-29','12:30:00',1,20520437,NULL),(39,104,2,'2023-06-03','12:30:00',3,20520437,NULL),(40,105,2,'2023-06-02','12:30:00',1,20520437,'abc'),(41,105,2,'2023-06-17','20:30:00',2,20520437,'ác'),(42,101,2,'2023-06-03','19:30:00',1,20520437,'123'),(43,101,2,'2023-06-04','12:30:00',1,20520437,'avc'),(44,101,2,'2023-06-04','12:30:00',1,20520437,'123');
/*!40000 ALTER TABLE `used_service` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-06-05 19:07:31
