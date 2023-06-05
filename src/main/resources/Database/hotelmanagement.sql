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
INSERT INTO `account` VALUES (1,'20520437','TienDat','admin','1'),(2,'12345678','Nhanvien1','nhanvien','0');
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
INSERT INTO `guest` VALUES (109876543210,'Karen Adams',2,'Mới',1983,'0987654321','Needs a room with a balcony'),(123450987654,'Christopher Lee',3,'Mới',1988,'0912873456','Requires a vegetarian meal'),(123456789012,'John Doe',3,'Mới',1990,'0924184088','Likes extra pillows'),(123456789101,'Brian Wilson',3,'Mới',1989,'0912345678','Requires an early check-in'),(162749503871,'Amelia Miller',4,'Mới',1981,'0912345678','Requires a room with a working desk'),(165098374512,'Emma Miller',4,'Mới',1981,'0912347650','Requires a room with a working desk'),(209384756109,'Matthew Turner',5,'Mới',1979,'0987561039','Needs an extra bed'),(210987654321,'Laura Davis',4,'Mới',1980,'0987123456','Allergic to scented products'),(234501928374,'Matthew Turner',2,'Mới',1994,'0912876543','Requires extra towels'),(234567890123,'Robert Brown',2,'Mới',1992,'0912876543','Requests a wake-up call'),(273598106741,'Ava Walker',2,'Mới',1991,'0912345678','Requires a quiet room'),(298457610923,'Emily Thompson',1,'Mới',1995,'0912874596','Requests a non-allergenic pillow'),(321098765432,'Elizabeth Turner',5,'Mới',1979,'0987654321','Needs an extra bed'),(345601928374,'Jennifer Phillips',1,'Mới',1990,'0987123456','Has a special dietary restriction'),(345678901234,'Jessica Anderson',1,'Mới',1989,'0987654321','Requires a crib for a baby'),(395817264019,'Henry White',2,'Mới',1990,'0987654321','Prefers a room with a city view'),(405681392751,'Sophia Thompson',1,'Mới',1995,'0912876534','Requests a non-allergenic pillow'),(415630827941,'Ethan Smith',1,'Mới',1988,'0987123456','Has a specific room preference'),(456781234567,'Ryan Martinez',1,'Mới',1993,'0912347890','Requests a late check-out'),(456789012345,'Michael Johnson',1,'Mới',1995,'0987654321','Prefers a room with a view'),(543210987654,'Sarah Miller',1,'Mới',1988,'0987123456','Has a specific room preference'),(560943817256,'Michelle Anderson',3,'Mới',1989,'0912384756','Requires an early check-in'),(561093827456,'Olivia Walker',2,'Mới',1991,'0912347609','Requires a quiet room'),(567890123456,'Emily Davis',5,'Mới',1978,'0987123456','Needs an extra blanket'),(567890192837,'Rebecca Baker',5,'Mới',1977,'0987123654','Needs a room with a mini-fridge'),(621098347510,'William Turner',5,'Mới',1979,'0987123456','Needs an extra bed'),(678901234567,'Daniel Wilson',4,'Mới',1983,'0912345678','Has a late check-in'),(678919283746,'Kevin Rodriguez',4,'Mới',1982,'0912876543','Requires a room with a king-size bed'),(759032816094,'Daniel Smith',1,'Mới',1988,'0987123094','Has a specific room preference'),(765092384561,'Adam Wilson',2,'Mới',1983,'0987650192','Needs a room with a balcony'),(789012345678,'Sarah Williams',4,'Mới',1980,'0976543210','Allergic to feathers'),(789065432109,'Stephanie Clark',4,'Mới',1981,'0987123456','Allergic to seafood'),(789543021867,'James Adams',4,'Mới',1980,'0987123456','Allergic to scented products'),(830917562401,'Isabella Davis',3,'Mới',1989,'0912305846','Requires an early check-in'),(876543210987,'Jonathan Roberts',3,'Mới',1987,'0912876543','Requires a laptop-friendly workspace'),(890123456789,'David Taylor',3,'Mới',1987,'0912765432','Has a pet allergy'),(890198273645,'Andrew Hill',3,'Mới',1986,'0912345678','Requires a room with a view'),(901234567890,'Amanda Thompson',2,'Mới',1991,'0987654321','Prefers a room on a higher floor'),(901287364509,'Michelle Thompson',2,'Mới',1992,'0987123654','Prefers a room on the ground floor'),(908723146501,'Mia Roberts',3,'Mới',1987,'0912876543','Requires a laptop-friendly workspace'),(935126480971,'Jackson White',2,'Mới',1990,'0987650142','Prefers a room with a city view'),(938561092374,'Ryan Adams',4,'Mới',1980,'0987120956','Allergic to scented products'),(956140823791,'Benjamin Wilson',2,'Mới',1983,'0987654321','Needs a room with a balcony'),(983741650921,'Sophia Roberts',3,'Mới',1987,'0912874095','Requires a laptop-friendly workspace'),(987654321012,'Christopher Walker',2,'Mới',1991,'0912345678','Requires a quiet room'),(987654321098,'Jane Smith',2,'Mới',1985,'0912345678','Requires a non-smoking room'),(987659876598,'Melissa Harris',2,'Mới',1984,'0987123654','Needs a room with a bathtub');
/*!40000 ALTER TABLE `guest` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `guest1`
--

DROP TABLE IF EXISTS `guest1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `guest1` (
  `Guest_ID` bigint DEFAULT NULL,
  `Guest_Name` text,
  `Number_Of_Checkin` text,
  `Guest_Tier` bigint DEFAULT NULL,
  `Guest_BirthYear` int DEFAULT NULL,
  `Guest_Phone` int DEFAULT NULL,
  `Guest_Notes` text
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `guest1`
--

LOCK TABLES `guest1` WRITE;
/*!40000 ALTER TABLE `guest1` DISABLE KEYS */;
/*!40000 ALTER TABLE `guest1` ENABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci COMMENT='Đơn vị: Ngàn';
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `service`
--

LOCK TABLES `service` WRITE;
/*!40000 ALTER TABLE `service` DISABLE KEYS */;
INSERT INTO `service` VALUES (6,'Dịch vụ đưa đón sân bay',250000.00,'Đưa khách từ sân bay đến khách sạn và ngược lại','Khả dụng'),(7,'Dịch vụ thuê xe',350000.00,'Cho thuê xe ô tô hoặc xe máy','Khả dụng'),(8,'Dịch vụ giặt là',100000.00,'Giặt là và là ủi quần áo','Khả dụng'),(9,'Dịch vụ hướng dẫn du lịch',200000.00,'Hướng dẫn viên du lịch đi kèm','Khả dụng'),(10,'Dịch vụ phòng họp',400000.00,'Cho thuê phòng họp và trang thiết bị','Khả dụng'),(11,'Dịch vụ spa',300000.00,'Trị liệu spa và mát-xa','Khả dụng'),(12,'Dịch vụ nhà hàng',150000.00,'Dịch vụ ăn uống tại nhà hàng','Khả dụng'),(13,'Dịch vụ đặt tour',180000.00,'Đặt tour du lịch và vé tham quan','Khả dụng'),(14,'Dịch vụ y tế',500000.00,'Dịch vụ y tế và chăm sóc sức khỏe','Khả dụng'),(15,'Dịch vụ wifi',50000.00,'Cung cấp internet wifi trong khách sạn','Khả dụng'),(16,'Buffet sáng',150000.00,'Dịch vụ buffet sáng với đa dạng món ăn','Khả dụng'),(17,'Phục vụ phòng',80000.00,'Dịch vụ phục vụ thức ăn và đồ uống trong phòng','Khả dụng'),(18,'Quầy bar',100000.00,'Dịch vụ phục vụ thức uống tại quầy bar','Khả dụng'),(19,'Dịch vụ đồ uống',50000.00,'Dịch vụ phục vụ đồ uống trong khuôn viên khách sạn','Khả dụng'),(20,'Dịch vụ phòng ăn',120000.00,'Dịch vụ phục vụ bữa ăn tại phòng ăn','Khả dụng'),(21,'Thực đơn đặc biệt',90000.00,'Thực đơn đặc biệt dành cho khách hàng','Khả dụng'),(22,'Dịch vụ hẹn giờ',50000.00,'Dịch vụ hẹn giờ phục vụ đồ uống','Khả dụng'),(23,'Trà chiều',60000.00,'Dịch vụ phục vụ trà chiều','Khả dụng'),(24,'Dịch vụ đồ ăn nhanh',70000.00,'Dịch vụ phục vụ đồ ăn nhanh','Khả dụng'),(25,'Dịch vụ đồ ăn tự chọn',80000.00,'Dịch vụ phục vụ đồ ăn tự chọn theo yêu cầu','Khả dụng');
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

-- Dump completed on 2023-06-05 22:08:54
