-- MySQL dump 10.13  Distrib 8.0.23, for Win64 (x86_64)
--
-- Host: localhost    Database: paymybuddybdd
-- ------------------------------------------------------
-- Server version	8.0.23

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
-- Table structure for table `bank_account`
--

DROP TABLE IF EXISTS `bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `bank_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `iban` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_699j998jxie2f134gfnu86q96` (`iban`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `money_transaction_with_bank_account`
--

DROP TABLE IF EXISTS `money_transaction_with_bank_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `money_transaction_with_bank_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `date` datetime NOT NULL,
  `tax_amount` double NOT NULL,
  `withdraw` bit(1) NOT NULL,
  `bank_id` bigint NOT NULL,
  `user_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKsafjh8mmb74qf2vsiypjwgu08` (`bank_id`),
  KEY `FKecob3k2o6eawtrx1gr6srw6dh` (`user_id`),
  CONSTRAINT `FKecob3k2o6eawtrx1gr6srw6dh` FOREIGN KEY (`user_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKsafjh8mmb74qf2vsiypjwgu08` FOREIGN KEY (`bank_id`) REFERENCES `bank_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `money_transaction_with_user_account`
--

DROP TABLE IF EXISTS `money_transaction_with_user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `money_transaction_with_user_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `amount` double NOT NULL,
  `date` datetime NOT NULL,
  `tax_amount` double NOT NULL,
  `receiver_id` bigint NOT NULL,
  `sender_id` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKf1dygs9v3mvtdabdky63hx564` (`receiver_id`),
  KEY `FKosnjogw046bnllpyajqx49q2k` (`sender_id`),
  CONSTRAINT `FKf1dygs9v3mvtdabdky63hx564` FOREIGN KEY (`receiver_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKosnjogw046bnllpyajqx49q2k` FOREIGN KEY (`sender_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account`
--

DROP TABLE IF EXISTS `user_account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `balance` double NOT NULL,
  `login_mail` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_o5nx1a0r336jnqgmaw3vk0ldd` (`login_mail`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account_bank_account_list`
--

DROP TABLE IF EXISTS `user_account_bank_account_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account_bank_account_list` (
  `user_account_id` bigint NOT NULL,
  `bank_account_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_hrefr8u0pukjcgee7lmxcagy3` (`bank_account_list_id`),
  KEY `FKn7oo6s682dfgvxdhr4tbnc6xb` (`user_account_id`),
  CONSTRAINT `FK43xu7bmv8eevku8pq06blymma` FOREIGN KEY (`bank_account_list_id`) REFERENCES `bank_account` (`id`),
  CONSTRAINT `FKn7oo6s682dfgvxdhr4tbnc6xb` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account_friend_list`
--

DROP TABLE IF EXISTS `user_account_friend_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account_friend_list` (
  `user_account_id` bigint NOT NULL,
  `friend_list_id` bigint NOT NULL,
  KEY `FK77s7t5cko7xvn4krfus3ppd0q` (`friend_list_id`),
  KEY `FKodnakka2ef39ie2v3lkas89fl` (`user_account_id`),
  CONSTRAINT `FK77s7t5cko7xvn4krfus3ppd0q` FOREIGN KEY (`friend_list_id`) REFERENCES `user_account` (`id`),
  CONSTRAINT `FKodnakka2ef39ie2v3lkas89fl` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account_money_transaction_with_bank_account_list`
--

DROP TABLE IF EXISTS `user_account_money_transaction_with_bank_account_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account_money_transaction_with_bank_account_list` (
  `user_account_id` bigint NOT NULL,
  `money_transaction_with_bank_account_list_id` bigint NOT NULL,
  UNIQUE KEY `UK_mf9utccy4cjspn60l1ubgk01w` (`money_transaction_with_bank_account_list_id`),
  KEY `FK5plefeoxg4f3ly6gft07uchjt` (`user_account_id`),
  CONSTRAINT `FK434ske1pu6taul5p9fu98xp2s` FOREIGN KEY (`money_transaction_with_bank_account_list_id`) REFERENCES `money_transaction_with_bank_account` (`id`),
  CONSTRAINT `FK5plefeoxg4f3ly6gft07uchjt` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `user_account_money_transaction_with_user_account_list`
--

DROP TABLE IF EXISTS `user_account_money_transaction_with_user_account_list`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `user_account_money_transaction_with_user_account_list` (
  `user_account_id` bigint NOT NULL,
  `money_transaction_with_user_account_list_id` bigint NOT NULL,
  KEY `FKm0fhi2d2np93w9l8cy3sewku2` (`money_transaction_with_user_account_list_id`),
  KEY `FKmkxbnvn6fw74trl8ojbmao9sr` (`user_account_id`),
  CONSTRAINT `FKm0fhi2d2np93w9l8cy3sewku2` FOREIGN KEY (`money_transaction_with_user_account_list_id`) REFERENCES `money_transaction_with_user_account` (`id`),
  CONSTRAINT `FKmkxbnvn6fw74trl8ojbmao9sr` FOREIGN KEY (`user_account_id`) REFERENCES `user_account` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2022-03-19 14:10:54
