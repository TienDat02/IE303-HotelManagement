CREATE DATABASE  IF NOT EXISTS `hotelmanagement` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `hotelmanagement`;
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
  `Total_Cost` decimal(10,0) DEFAULT NULL,
  `Employee_ID` bigint DEFAULT NULL,
  PRIMARY KEY (`Bill_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `bill`
--

LOCK TABLES `bill` WRITE;
/*!40000 ALTER TABLE `bill` DISABLE KEYS */;
INSERT INTO `bill` VALUES (3,123456789013,'2023-05-06','14:04:27',3631,20520437),(4,123456789012,'2023-05-06','14:15:46',7017,20520437),(5,123456789014,'2023-05-06','14:15:53',4047,20520437),(6,123456789013,'2023-05-06','14:15:58',2472,20520437);
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
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
INSERT INTO `checkin_history` VALUES (39,123456789013,101,'2023-05-05 09:30:00','2023-05-06 12:00:00',2,20520437),(40,123456789012,102,'2023-05-05 13:10:00','2023-05-07 12:00:00',2,20520437),(41,123456789012,101,'2023-05-05 12:40:00','2023-05-07 15:40:00',1,20520437),(42,123456789014,103,'2023-05-05 18:30:00','2023-05-07 18:30:00',3,20520437),(43,123456789014,104,'2023-05-05 18:30:00','2023-05-07 18:30:00',3,20520437),(44,123456789013,105,'2023-05-05 12:30:00','2023-05-07 12:30:00',2,20520437),(45,123456789013,106,'2023-05-05 12:30:00','2023-05-07 12:30:00',2,20520437);
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
  `Room_ID` smallint DEFAULT NULL,
  `Checkout_Time` time NOT NULL,
  `Checkout_Date` date DEFAULT NULL,
  `Total_Hours` int DEFAULT NULL,
  `Service_Total` decimal(10,0) DEFAULT NULL,
  `Room_Total` decimal(10,0) DEFAULT NULL,
  `Total` decimal(10,0) DEFAULT NULL,
  `Employee_ID` bigint NOT NULL,
  PRIMARY KEY (`Checkout_ID`),
  KEY `checkout-guest_idx` (`Guest_ID`),
  KEY `checkout-employee_idx` (`Employee_ID`),
  CONSTRAINT `checkout-employee` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`),
  CONSTRAINT `checkout-guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=54 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `checkout`
--

LOCK TABLES `checkout` WRITE;
/*!40000 ALTER TABLE `checkout` DISABLE KEYS */;
INSERT INTO `checkout` VALUES (47,123456789013,101,'14:04:27','2023-05-06',28,2464,1167,3631,20520437),(48,123456789012,102,'14:15:46','2023-05-06',25,2470,1042,3512,20520437),(49,123456789012,101,'14:15:46','2023-05-06',25,2464,1042,3506,20520437),(50,123456789014,103,'14:15:53','2023-05-06',19,2464,792,3256,20520437),(51,123456789014,104,'14:15:53','2023-05-06',19,0,792,792,20520437),(52,123456789013,105,'14:15:58','2023-05-06',25,0,1,1,20520437),(53,123456789013,106,'14:15:58','2023-05-06',25,2470,1,2471,20520437);
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
INSERT INTO `employee` VALUES (20520437,'Trần Tiến Đạt','2002-02-11','Nam','Linh Trung','0328950020','20520437@gm.uit.edu.vn','012345678910','Quản lý',15000.000,'2024-04-23','Vắng');
INSERT INTO `employee` VALUES 
(20520438,'John Doe','1990-01-01','Nam','123 Main St','1234567890','johndoe@example.com','123456789012','Bảo vệ',5000.000,'2022-01-01','Có mặt'),
(20520439,'Jane Doe','1995-01-01','Nữ','456 Oak Ave','0987654321','janedoe@example.com','098765432109','Lao công',3000.000,'2022-01-01','Có mặt'),
(20520440,'Bob Smith','1985-01-01','Nam','789 Elm St','0555555555','bobsmith@example.com','555555555555','Bảo vệ',2000.000,'2022-01-01','Có mặt'),
(20520441,'Alice Johnson','2000-01-01','Nữ','321 Pine St','0111111111','alicejohnson@example.com','111111111111','Bảo vệ',2500.000,'2022-01-01','Có mặt'),
(20520442,'Tom Brown','1998-01-01','Nam','654 Cedar Ave','0222222222','tombrown@example.com','222222222222','Đầu bếp',4000.000,'2022-01-01','Có mặt'),
(20520443,'Sara Lee','1992-01-01','Nữ','987 Maple St','0333333333','saralee@example.com','333333333333','Đầu bếp',3500.000,'2022-01-01','Có mặt'),
(20520444,'David Kim','1993-01-01','Nam','246 Walnut St','0444444444','davidkim@example.com','444444444444','Bồi bàn',4500.000,'2022-01-01','Có mặt'),
(20520445,'Emily Chen','1994-01-01','Nữ','135 Birch Ave','0777777777','emilychen@example.com','777777777777','Lao công',3500.000,'2022-01-01','Có mặt'),
(20520446,'Michael Nguyen','1991-01-01','Nam','864 Oak St','0888888888','michaelnguyen@example.com','888888888888','Lao công',5000.000,'2022-01-01','Có mặt'),
(20520447,'Jessica Wang','1996-01-01','Nữ','753 Elm Ave','0999999999','jessicawang@example.com','999999999999','Lao công',4000.000,'2022-01-01','Có mặt');

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
INSERT INTO `guest` VALUES (123456789012,'Nguyễn Văn B',2000,'012345654','avc'),(123456789013,'Trần Văn A',2000,'1234567',''),(123456789014,'Nguyễn Văn d',2000,'012345654',NULL),(123456789015,'Nguyễn Văn f',2000,'012345654',NULL),(123456789016,'Nguyễn Văn r',2000,'012345654',NULL),(123456789017,'trn',2002,'09234','t'),(123456789019,'trter',2002,'09234','t');
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
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reservation`
--

LOCK TABLES `reservation` WRITE;
/*!40000 ALTER TABLE `reservation` DISABLE KEYS */;
INSERT INTO `reservation` VALUES (43,123456789012,'2023-05-03 00:00:00',101,NULL,'2023-05-03 00:00:00',2,'2023-05-03 00:00:00','Thành công'),(44,123456789012,'2023-05-04 10:17:15',103,20520437,'2023-05-04 12:13:00',2,'2023-05-06 14:15:00','Thành công'),(45,123456789013,'2023-05-06 10:50:48',101,20520437,'2023-05-06 13:00:00',5,'2023-05-07 12:30:00','Thành công'),(46,123456789013,'2023-05-06 10:50:48',102,20520437,'2023-05-06 13:00:00',5,'2023-05-07 12:30:00','Thành công'),(47,123456789013,'2023-05-06 10:50:48',103,20520437,'2023-05-06 13:00:00',5,'2023-05-07 12:30:00','Thành công');
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
) ENGINE=InnoDB AUTO_INCREMENT=35 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
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
INSERT INTO `used_service` VALUES (28,101,2,'2023-05-06','12:30:00',2,20520437),(29,102,2,'2023-05-06','14:10:00',2,20520437),(30,102,3,'2023-05-06','14:10:00',2,20520437),(31,101,2,'2023-05-06','20:00:00',2,20520437),(32,103,2,'2023-05-06','20:00:00',2,20520437),(33,106,2,'2023-05-06','13:30:00',2,20520437),(34,106,3,'2023-05-06','13:30:00',2,20520437);
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

-- Dump completed on 2023-05-09  8:54:18

INSERT INTO `employee` VALUES 
(20520438,'John Doe','1990-01-01','Nam','123 Main St','1234567890','johndoe@example.com','123456789012','Bảo vệ',5000.000,'2022-01-01','Có mặt'),
(20520439,'Jane Doe','1995-01-01','Nữ','456 Oak Ave','0987654321','janedoe@example.com','098765432109','Lao công',3000.000,'2022-01-01','Có mặt'),
(20520440,'Bob Smith','1985-01-01','Nam','789 Elm St','0555555555','bobsmith@example.com','555555555555','Bảo vệ',2000.000,'2022-01-01','Có mặt'),
(20520441,'Alice Johnson','2000-01-01','Nữ','321 Pine St','0111111111','alicejohnson@example.com','111111111111','Bảo vệ',2500.000,'2022-01-01','Có mặt'),
(20520442,'Tom Brown','1998-01-01','Nam','654 Cedar Ave','0222222222','tombrown@example.com','222222222222','Đầu bếp',4000.000,'2022-01-01','Có mặt'),
(20520443,'Sara Lee','1992-01-01','Nữ','987 Maple St','0333333333','saralee@example.com','333333333333','Đầu bếp',3500.000,'2022-01-01','Có mặt'),
(20520444,'David Kim','1993-01-01','Nam','246 Walnut St','0444444444','davidkim@example.com','444444444444','Bồi bàn',4500.000,'2022-01-01','Có mặt'),
(20520445,'Emily Chen','1994-01-01','Nữ','135 Birch Ave','0777777777','emilychen@example.com','777777777777','Lao công',3500.000,'2022-01-01','Có mặt'),
(20520446,'Michael Nguyen','1991-01-01','Nam','864 Oak St','0888888888','michaelnguyen@example.com','888888888888','Lao công',5000.000,'2022-01-01','Có mặt'),
(20520447,'Jessica Wang','1996-01-01','Nữ','753 Elm Ave','0999999999','jessicawang@example.com','999999999999','Lao công',4000.000,'2022-01-01','Có mặt');

CREATE VIEW DoanhThu AS
SELECT b.Bill_ID, g.Guest_Name, b.Room_ID, b.Bill_Date ,r.Room_Price * c.Total_Hours AS TienPhong, SUM(rs.Service_Quantity * s.Service_Price) AS TienDichVu, b.Total_Cost
FROM bill b
JOIN guest g ON b.Guest_ID = g.Guest_ID
JOIN checkout c ON c.Guest_ID = g.Guest_ID
JOIN checkout c ON r.Room_ID = c.Room_ID
JOIN room_service rs ON b.Room_ID = rs.Room_ID
JOIN service s ON rs.Service_ID = s.Service_ID
GROUP BY b.Bill_ID, g.Guest_Name, b.Room_ID,  c.Checkout_Date ,TienPhong, b.Total_Cost
