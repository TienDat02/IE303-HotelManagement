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
  `Guest_ID` bigint NOT NULL,
  `Room_ID` smallint NOT NULL,
  `Total_Cost` decimal(9,3) NOT NULL,
  PRIMARY KEY (`Bill_ID`),
  KEY `bill-guest_idx` (`Guest_ID`),
  KEY `bill-room_idx` (`Room_ID`),
  CONSTRAINT `bill-guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`),
  CONSTRAINT `bill-room` FOREIGN KEY (`Room_ID`) REFERENCES `room` (`Room_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (7,123456789012,102,1000.000),(8,123456789012,103,3470.000),(9,123456789012,101,3235.000);
/*!40000 ALTER TABLE `bill` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkin`
--

DROP TABLE IF EXISTS `checkin`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkin` (
  `Checkin_ID` int NOT NULL AUTO_INCREMENT,
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
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `Checkin_ID` int NOT NULL,
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
INSERT INTO `checkin_history` VALUES (1,123456789012,101,'2023-05-01 00:00:00','2023-05-02 00:00:00',2,20520437);
/*!40000 ALTER TABLE `checkin_history` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `checkout`
--

DROP TABLE IF EXISTS `checkout`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `checkout` (
  `Checkout_ID` int NOT NULL AUTO_INCREMENT,
  `Guest_ID` bigint NOT NULL,
  `Checkout_Time` time NOT NULL,
  `Checkout_Date` date DEFAULT NULL,
  `Total_Time` int DEFAULT NULL,
  `Employee_ID` bigint NOT NULL,
  `Room_ID` smallint DEFAULT NULL,
  PRIMARY KEY (`Checkout_ID`),
  KEY `checkout-guest_idx` (`Guest_ID`),
  KEY `checkout-employee_idx` (`Employee_ID`),
  CONSTRAINT `checkout-employee` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`),
  CONSTRAINT `checkout-guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout`
--

LOCK TABLES `checkout` WRITE;
/*!40000 ALTER TABLE `checkout` DISABLE KEYS */;
INSERT INTO `checkout` VALUES (10,123456789012,'13:29:20','2023-05-03',0,20520437,101),(11,123456789012,'13:39:04','2023-05-03',0,20520437,101),(12,123456789012,'13:40:47','2023-05-03',0,20520437,101),(13,123456789012,'13:45:33','2023-05-03',0,20520437,101),(14,123456789012,'14:00:06','2023-05-03',1,20520437,102),(15,123456789012,'14:05:07','2023-05-03',1,20520437,102),(16,123456789012,'14:07:08','2023-05-03',1,20520437,102),(17,123456789012,'14:08:18','2023-05-03',1,20520437,102),(18,123456789012,'20:01:11','2023-05-03',1,20520437,103),(19,123456789012,'20:50:12','2023-05-03',2,20520437,101);
/*!40000 ALTER TABLE `checkout` ENABLE KEYS */;
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
INSERT INTO `employee` VALUES (20520437,'Trần Tiến Đạt','2002-02-11','Nam','Linh Trung','0328950020','20520437@gm.uit.edu.vn','012345678910','Quản lý',15000.000,'2024-04-23','Vắng'),(20520438,'abc','2001-01-01','Nam','acv','234','g','34','a',10.000,'0001-01-01','Vắng'),(20520439,'Tra','0001-01-01','Nam','a','092','a','1','a',0.000,'0001-01-01','Vắng');
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
  `Guest_BirthYear` year NOT NULL,
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
INSERT INTO `guest` VALUES (123456789012,'Nguyễn Văn B',2000,'012345654','avc'),(123456789014,'Nguyễn Văn d',2000,'012345654',NULL),(123456789015,'Nguyễn Văn f',2000,'012345654',NULL),(123456789016,'Nguyễn Văn r',2000,'012345654',NULL),(123456789017,'trn',2002,'09234','t'),(123456789019,'trter',2002,'09234','t');
/*!40000 ALTER TABLE `guest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reservation`
--

DROP TABLE IF EXISTS `reservation`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reservation` (
  `Reservation_ID` int NOT NULL AUTO_INCREMENT,
  `Guest_ID` bigint DEFAULT NULL,
  `Reserve_Date` datetime DEFAULT NULL,
  `Room_ID` smallint DEFAULT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  `Expected_Checkin_Date` datetime DEFAULT NULL,
  `Number_ofGuest` int DEFAULT NULL,
  `Expected_Checkout_Date` datetime DEFAULT NULL,
  `Reservation_Status` enum('Hủy','Đặt trước','Thành công') DEFAULT NULL,
  PRIMARY KEY (`Reservation_ID`),
  KEY `reservation-guest_idx` (`Guest_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (43,123456789012,'2023-05-03 00:00:00',101,NULL,'2023-05-03 00:00:00',2,'2023-05-03 00:00:00','Thành công');
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
  `Room_Price` float NOT NULL,
  `Room_Status` enum('Trống','Đang thuê','Chờ trả','Đang sửa chữa','Đặt trước') NOT NULL,
  `Room_Floor` tinyint NOT NULL,
  PRIMARY KEY (`Room_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `room`
--

LOCK TABLES `room` WRITE;
/*!40000 ALTER TABLE `room` DISABLE KEYS */;
INSERT INTO `room` VALUES (101,'King',1000,'Trống',1),(102,'Queen',1000,'Trống',1),(103,'Prince',1000,'Trống',1),(104,'King',1000,'Trống',1),(105,'Queen',1,'Trống',2),(106,'Joker',1,'Trống',2);
/*!40000 ALTER TABLE `room` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `room_service`
--

DROP TABLE IF EXISTS `room_service`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `room_service` (
  `Room_Service_ID` int NOT NULL AUTO_INCREMENT,
  `Room_ID` smallint NOT NULL,
  `Service_ID` int NOT NULL,
  `Service_Date` date NOT NULL,
  `Service_Time` time NOT NULL,
  `Service_Quantity` smallint NOT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`Room_Service_ID`),
  KEY `Room-RoomService_idx` (`Room_ID`),
  KEY `Service-RoomService_idx` (`Service_ID`),
  CONSTRAINT `Room-RoomService` FOREIGN KEY (`Room_ID`) REFERENCES `room` (`Room_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
  `Service_Price` decimal(9,3) NOT NULL,
  `Service_Description` varchar(200) DEFAULT NULL,
  `Service_Status` enum('Trống','Bận') NOT NULL,
  PRIMARY KEY (`Service_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Đơn vị: Ngàn';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (2,'abd',1232.000,'abc','Trống'),(3,'abch',3.000,'rtt','Trống');
/*!40000 ALTER TABLE `service` ENABLE KEYS */;
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
  PRIMARY KEY (`Used_Service_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `used_service`
--

LOCK TABLES `used_service` WRITE;
/*!40000 ALTER TABLE `used_service` DISABLE KEYS */;
INSERT INTO `used_service` VALUES (9,101,2,'2023-05-03','14:15:00',1,NULL),(10,101,2,'2023-05-03','12:00:00',1,20520437),(11,101,2,'2023-05-03','20:30:00',1,20520437),(12,101,3,'2023-05-03','20:30:00',1,20520437),(13,103,2,'2023-05-03','20:30:00',2,20520437),(14,103,3,'2023-05-03','20:30:00',2,20520437);
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

-- Dump completed on 2023-05-03 21:02:00
