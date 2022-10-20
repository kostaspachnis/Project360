-- MySQL dump 10.13  Distrib 8.0.28, for Win64 (x86_64)
--
-- Host: localhost    Database: cs360db
-- ------------------------------------------------------
-- Server version	8.0.28

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
-- Table structure for table `civilians`
--

DROP TABLE IF EXISTS `civilians`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `civilians` (
  `account_no` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(16) NOT NULL,
  `name` varchar(20) NOT NULL,
  `debt` double NOT NULL,
  `expiration_date` date NOT NULL,
  `credit_limit` double NOT NULL,
  `balance` double NOT NULL,
  PRIMARY KEY (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `civilians`
--

LOCK TABLES `civilians` WRITE;
/*!40000 ALTER TABLE `civilians` DISABLE KEYS */;
INSERT INTO `civilians` VALUES (1,'Petros','1234','Petros',0,'1970-01-01',200,600),(10,'Kostas','1234','Kostas',0,'1970-01-01',500,2000),(1234567812,'Amplaoumplas','1234','Amplaoumplas',0,'2026-01-25',100,1000);
/*!40000 ALTER TABLE `civilians` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `companies`
--

DROP TABLE IF EXISTS `companies`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `companies` (
  `account_no` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(16) NOT NULL,
  `name` varchar(20) NOT NULL,
  `debt` double NOT NULL,
  `exp_date` date NOT NULL,
  `credit_limit` double NOT NULL,
  `balance` double NOT NULL,
  PRIMARY KEY (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `companies`
--

LOCK TABLES `companies` WRITE;
/*!40000 ALTER TABLE `companies` DISABLE KEYS */;
INSERT INTO `companies` VALUES (10101,'Compania','1234','Compania',1000,'1970-01-01',1000,30000),(1234567813,'Csdas','1234','Csdas',0,'2022-01-25',1000,100000);
/*!40000 ALTER TABLE `companies` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dealers`
--

DROP TABLE IF EXISTS `dealers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dealers` (
  `account_no` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(16) NOT NULL,
  `name` varchar(20) NOT NULL,
  `debt` double NOT NULL,
  `earnings` double NOT NULL,
  `commission` double NOT NULL,
  PRIMARY KEY (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dealers`
--

LOCK TABLES `dealers` WRITE;
/*!40000 ALTER TABLE `dealers` DISABLE KEYS */;
INSERT INTO `dealers` VALUES (0,'Dealeridis','1234','Dealeridis',950,552,0.15),(100,'Dealeropoulos','1234','Dealeropoulos',0,900243,0.02),(101,'Dealerakis','1234','Dealerakis',-10,0,0.13),(1000,'Dealeras','1234','Dealeras',1425,129998.7,0.13),(1010,'Dealerman','1234','Dealerman',9.5,30,0.13);
/*!40000 ALTER TABLE `dealers` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transactions`
--

DROP TABLE IF EXISTS `transactions`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transactions` (
  `transactionID` int NOT NULL,
  `dealerName` varchar(20) NOT NULL,
  `dealerAccount_no` int NOT NULL,
  `customerName` varchar(20) NOT NULL,
  `customerAccount_no` int NOT NULL,
  `date` date NOT NULL,
  `amount` double NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`transactionID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transactions`
--

LOCK TABLES `transactions` WRITE;
/*!40000 ALTER TABLE `transactions` DISABLE KEYS */;
INSERT INTO `transactions` VALUES (2,'Dealeras',1000,'Petros',1,'1970-01-01',10,'return'),(4,'Dealeropoulos',100,'Petros',1,'2021-11-15',20,'charge/credit'),(6,'Dealeridis',0,'Petros',1,'1970-01-01',50,'return'),(7,'Dealeridis',0,'Petros',1,'1970-01-01',500,'charge/credit'),(8,'Dealerman',1010,'Petros',1,'2022-01-24',10,'charge/credit'),(10,'Dealeridis',0,'KP',4,'1970-01-01',1,'charge/credit');
/*!40000 ALTER TABLE `transactions` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `account_no` int NOT NULL,
  `username` varchar(20) NOT NULL,
  `password` varchar(16) NOT NULL,
  `name` varchar(20) NOT NULL,
  `debt` double NOT NULL,
  `type` varchar(45) NOT NULL,
  PRIMARY KEY (`account_no`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (0,'Dealeridis','1234','Dealeridis',500,'Dealer'),(1,'Petros','1234','Petros',0,'Civilian'),(10,'Kostas','1234','Kostas',0,'Civilian'),(100,'Dealeropoulos','1234','Dealeropoulos',0,'Dealer'),(101,'Dealerakis','1234','Dealerakis',-10,'Dealer'),(1000,'Dealeras','1234','Dealeras',1500,'Dealer'),(1010,'Dealerman','1234','Dealerman',0,'Dealer'),(10101,'Compania','1234','Compania',1000,'Company'),(1234567812,'Amplaoumplas','1234','Amplaoumplas',0,'Civilian'),(1234567813,'Csdas','1234','Csdas',0,'Company');
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-01-25 22:59:50
