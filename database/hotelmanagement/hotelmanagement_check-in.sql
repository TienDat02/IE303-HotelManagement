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
-- Table structure for table `check-in`
--

DROP TABLE IF EXISTS `check-in`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `check-in` (
  `Checkin_ID` int NOT NULL,
  `Guest_ID` bigint NOT NULL,
  `Reservation_ID` int DEFAULT NULL,
  `Checkin_Date` date NOT NULL,
  `Checkin_Time` time NOT NULL,
  `Number_ofGuest` tinyint NOT NULL,
  `Employee_ID` bigint NOT NULL,
  `Room_ID` smallint NOT NULL,
  PRIMARY KEY (`Checkin_ID`),
  KEY `checkin-employee_idx` (`Employee_ID`),
  KEY `checkin-room_idx` (`Room_ID`),
  KEY `checkin-reservation_idx` (`Reservation_ID`),
  KEY `checkin-guest_idx` (`Guest_ID`),
  CONSTRAINT `checkin-employee` FOREIGN KEY (`Employee_ID`) REFERENCES `employee` (`Employee_ID`),
  CONSTRAINT `checkin-guest` FOREIGN KEY (`Guest_ID`) REFERENCES `guest` (`Guest_ID`),
  CONSTRAINT `checkin-reservation` FOREIGN KEY (`Reservation_ID`) REFERENCES `reservation` (`Reservation_ID`),
  CONSTRAINT `checkin-room` FOREIGN KEY (`Room_ID`) REFERENCES `room` (`Room_ID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `check-in`
--

LOCK TABLES `check-in` WRITE;
/*!40000 ALTER TABLE `check-in` DISABLE KEYS */;
/*!40000 ALTER TABLE `check-in` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2023-04-14 14:35:37
