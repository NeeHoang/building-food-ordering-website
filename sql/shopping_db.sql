CREATE DATABASE  IF NOT EXISTS `foodorder` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `foodorder`;
-- MySQL dump 10.13  Distrib 8.0.42, for Win64 (x86_64)
--
-- Host: localhost    Database: foodorder
-- ------------------------------------------------------
-- Server version	8.0.42

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
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (1,'Món Việt','Các món ăn truyền thống Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35'),(2,'Đồ nướng','Các món nướng thơm ngon','2025-04-12 12:06:35','2025-04-12 12:06:35'),(3,'Món chay','Dành cho người ăn chay','2025-04-12 12:06:35','2025-04-12 12:06:35'),(4,'Tráng miệng','Các món ngọt tráng miệng','2025-04-12 12:06:35','2025-04-12 12:06:35'),(5,'Đồ uống đặc biệt','Các loại đồ uống đặc sản','2025-04-12 12:06:35','2025-04-12 12:06:35'),(6,'Đồ ăn nhanh','Các loại đồ ăn nhanh','2025-04-12 12:06:35','2025-04-14 08:56:18'),(7,'Nước ngọt','Các loại nước ngọt','2025-04-12 12:06:35','2025-04-14 08:56:18'),(8,'Nước có ga','Các loại nước có ga phổ biến','2025-04-12 12:06:35','2025-04-14 08:56:18');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_items`
--

DROP TABLE IF EXISTS `order_items`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_items` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `quantity` int NOT NULL,
  `price` decimal(10,2) NOT NULL,
  `total_price` decimal(10,2) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_items_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_items_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=28 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_items`
--

LOCK TABLES `order_items` WRITE;
/*!40000 ALTER TABLE `order_items` DISABLE KEYS */;
INSERT INTO `order_items` VALUES (1,1,1,2,50000.00,100000.00),(2,1,6,1,15000.00,15000.00),(3,2,2,2,20000.00,40000.00),(4,2,7,1,25000.00,25000.00),(5,3,3,2,70000.00,140000.00),(6,4,4,1,30000.00,30000.00),(7,4,5,1,60000.00,60000.00),(8,5,9,1,30000.00,30000.00),(9,6,10,2,35000.00,70000.00),(10,6,6,2,15000.00,30000.00),(11,7,1,1,50000.00,50000.00),(12,7,7,1,25000.00,25000.00),(13,8,3,3,70000.00,210000.00),(14,9,12,1,55000.00,55000.00),(15,10,8,2,20000.00,40000.00),(16,10,11,2,22000.00,44000.00),(17,11,13,2,17000.00,34000.00),(18,12,4,1,30000.00,30000.00),(19,12,5,1,60000.00,60000.00),(20,13,15,3,18000.00,54000.00),(21,14,14,2,20000.00,40000.00),(22,15,10,1,35000.00,35000.00),(23,16,6,1,15000.00,15000.00),(24,17,11,2,22000.00,44000.00),(25,18,2,1,20000.00,20000.00),(26,19,7,2,25000.00,50000.00),(27,20,9,3,30000.00,90000.00);
/*!40000 ALTER TABLE `order_items` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `status` enum('pending','confirmed','delivered','canceled') DEFAULT 'pending',
  `total_price` decimal(10,2) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `voucher_id` int DEFAULT NULL,
  `full_name` varchar(100) NOT NULL,
  `address` varchar(120) DEFAULT NULL,
  `note` varchar(200) DEFAULT NULL,
  `phone_number` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_voucher` (`voucher_id`),
  CONSTRAINT `fk_voucher` FOREIGN KEY (`voucher_id`) REFERENCES `vouchers` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,'pending',120000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Nguyễn Văn A','Hà Nội','','0123456789'),(2,'confirmed',85000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Trần Thị B','TP.HCM','','0987654321'),(3,'delivered',150000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Lê Văn C','Đà Nẵng','','0912345678'),(4,'delivered',95000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Phạm Thị D','Hải Phòng','','0978123456'),(5,'canceled',50000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Hoàng Văn E','Nha Trang','','0965432187'),(6,'pending',130000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Nguyễn Văn F','Huế','','0911111111'),(7,'confirmed',78000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Trần Văn G','Quảng Ninh','','0922222222'),(8,'delivered',210000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Ngô Thị H','Cần Thơ','','0933333333'),(9,'pending',67000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Đỗ Văn I','Bắc Giang','','0944444444'),(10,'pending',123000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Đinh Thị J','Nam Định','','0955555555'),(11,'pending',100000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Nguyễn Văn K','Thanh Hóa','','0966666666'),(12,'confirmed',88000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Trần Văn L','Vĩnh Phúc','','0977777777'),(13,'delivered',99000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Phan Thị M','Bến Tre','','0988888888'),(14,'pending',71000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Hà Văn N','Lạng Sơn','','0999999999'),(15,'delivered',105000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Lý Thị O','Sơn La','','0900000000'),(16,'pending',93000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Ngô Văn P','Ninh Bình','','0901234567'),(17,'confirmed',87000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Trịnh Thị Q','Hòa Bình','','0907654321'),(18,'delivered',149000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Lưu Văn R','Hưng Yên','','0908765432'),(19,'confirmed',95000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Chu Văn S','Quảng Bình','','0903456789'),(20,'pending',111000.00,'2025-05-23 07:47:41','2025-05-23 07:47:41',NULL,'Tạ Thị T','Hà Tĩnh','','0906543210');
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `payments`
--

DROP TABLE IF EXISTS `payments`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `payments` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `payment_method` enum('cash','card','online') NOT NULL,
  `payment_status` enum('pending','completed','failed') DEFAULT 'pending',
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  CONSTRAINT `payments_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=21 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `payments`
--

LOCK TABLES `payments` WRITE;
/*!40000 ALTER TABLE `payments` DISABLE KEYS */;
INSERT INTO `payments` VALUES (1,1,'cash','completed','2025-05-23 07:47:57'),(2,2,'card','completed','2025-05-23 07:47:57'),(3,3,'online','completed','2025-05-23 07:47:57'),(4,4,'cash','completed','2025-05-23 07:47:57'),(5,5,'card','failed','2025-05-23 07:47:57'),(6,6,'online','completed','2025-05-23 07:47:57'),(7,7,'cash','pending','2025-05-23 07:47:57'),(8,8,'card','completed','2025-05-23 07:47:57'),(9,9,'online','pending','2025-05-23 07:47:57'),(10,10,'cash','completed','2025-05-23 07:47:57'),(11,11,'card','completed','2025-05-23 07:47:57'),(12,12,'online','completed','2025-05-23 07:47:57'),(13,13,'cash','completed','2025-05-23 07:47:57'),(14,14,'card','pending','2025-05-23 07:47:57'),(15,15,'online','completed','2025-05-23 07:47:57'),(16,16,'cash','completed','2025-05-23 07:47:57'),(17,17,'card','completed','2025-05-23 07:47:57'),(18,18,'online','completed','2025-05-23 07:47:57'),(19,19,'cash','completed','2025-05-23 07:47:57'),(20,20,'card','completed','2025-05-23 07:47:57');
/*!40000 ALTER TABLE `payments` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_inventory`
--

DROP TABLE IF EXISTS `product_inventory`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_inventory` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `price` decimal(10,2) DEFAULT NULL,
  `quantity` int DEFAULT NULL,
  `status` enum('available','out_of_stock') DEFAULT NULL,
  `is_current` tinyint(1) DEFAULT '0',
  `start_date` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `end_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `product_inventory_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=34 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_inventory`
--

LOCK TABLES `product_inventory` WRITE;
/*!40000 ALTER TABLE `product_inventory` DISABLE KEYS */;
INSERT INTO `product_inventory` VALUES (1,1,45000.00,100,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(2,1,50000.00,80,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(3,2,20000.00,50,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(4,2,18000.00,40,'available',1,'2025-05-23 07:35:58',NULL),(5,3,70000.00,60,'available',1,'2025-05-23 07:35:58',NULL),(6,3,75000.00,30,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(7,4,30000.00,100,'available',1,'2025-05-23 07:35:58',NULL),(8,4,28000.00,80,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(9,5,60000.00,90,'available',1,'2025-05-23 07:35:58',NULL),(10,5,55000.00,70,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(11,6,15000.00,120,'available',1,'2025-05-23 07:35:58',NULL),(12,6,14000.00,100,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(13,7,25000.00,110,'available',1,'2025-05-23 07:35:58',NULL),(14,7,23000.00,90,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(15,8,20000.00,130,'available',1,'2025-05-23 07:35:58',NULL),(16,8,18000.00,100,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(17,9,30000.00,60,'available',1,'2025-05-23 07:35:58',NULL),(18,9,29000.00,40,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(19,10,35000.00,70,'available',1,'2025-05-23 07:35:58',NULL),(20,10,33000.00,50,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(21,11,22000.00,90,'available',1,'2025-05-23 07:35:58',NULL),(22,11,21000.00,60,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(23,12,55000.00,85,'available',1,'2025-05-23 07:35:58',NULL),(24,12,53000.00,65,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(25,13,17000.00,95,'available',1,'2025-05-23 07:35:58',NULL),(26,13,16000.00,75,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(27,14,20000.00,90,'available',1,'2025-05-23 07:35:58',NULL),(28,14,19000.00,70,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(29,15,18000.00,120,'available',1,'2025-05-23 07:35:58',NULL),(30,15,16000.00,100,'available',0,'2025-05-23 07:35:58','2025-05-25 09:10:59'),(31,16,25000.00,10,'available',1,'2025-05-23 07:35:58',NULL),(33,1,25000.00,10,'available',1,'2025-05-25 09:10:59',NULL);
/*!40000 ALTER TABLE `product_inventory` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `description` text,
  `image_url` varchar(255) DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `type` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Phở bò đặc biệt','Phở truyền thống với nước dùng đậm đà','pho_bo.jpg',2,'2025-04-12 12:06:35','2025-05-25 10:12:42','TRUYEN_THONG'),(2,'Bánh mì pate','Bánh mì Việt Nam với pate đặc biệt','banh_mi.jpg',1,'2025-04-12 12:06:35','2025-04-12 12:06:35','FAST_FOOD'),(3,'Gà nướng','Gà nướng thơm ngon với gia vị đặc biệt','ga_nuong.jpg',2,'2025-04-12 12:06:35','2025-04-12 12:06:35','NUONG'),(4,'Nem rán','Nem truyền thống giòn rụm','nem_ran.jpg',1,'2025-04-12 12:06:35','2025-04-12 12:06:35','CHIEN'),(5,'Bún chả','Bún chả Hà Nội đặc sản','bun_cha.jpg',1,'2025-04-12 12:06:35','2025-04-12 12:06:35','TRUYEN_THONG'),(6,'Chè đậu đen','Chè ngọt thanh giải nhiệt','che_dau_den.jpg',4,'2025-04-12 12:06:35','2025-04-12 12:06:35','CHE'),(7,'Cà phê sữa đá','Cà phê đặc sản Việt Nam','ca_phe.jpg',5,'2025-04-12 12:06:35','2025-04-12 12:06:35','CA_PHE'),(8,'Bia Hà Nội','Bia truyền thống Hà Nội','bia.jpg',5,'2025-04-12 12:06:35','2025-04-12 12:06:35','BIA'),(9,'Đậu hũ chiên','Đậu hũ chiên giòn dành cho người chay','dau_hu.jpg',3,'2025-04-12 12:06:35','2025-04-12 12:06:35','CHAY'),(10,'Bánh xèo','Bánh xèo miền Tây đặc sản','banh_xeo.jpg',1,'2025-04-12 12:06:35','2025-04-12 12:06:35','TRUYEN_THONG'),(11,'Sinh tố bơ','Sinh tố bơ thơm ngon bổ dưỡng','sinh_to.jpg',5,'2025-04-12 12:06:35','2025-04-12 12:06:35','SINH_TO'),(12,'Cơm tấm sườn','Cơm tấm sườn nướng Sài Gòn','com_tam.jpg',1,'2025-04-12 12:06:35','2025-04-12 12:06:35','COM'),(13,'Bánh flan','Bánh flan tráng miệng thơm ngon','banh_flan.jpg',4,'2025-04-12 12:06:35','2025-04-12 12:06:35','TRANG_MIENG'),(14,'Trà đào','Trà đào mát lạnh','tra_dao.jpg',5,'2025-04-12 12:06:35','2025-04-12 12:06:35','TRA'),(15,'Bánh bao','Bánh bao nhân thịt truyền thống','banh_bao.jpg',1,'2025-04-12 12:06:35','2025-04-12 12:06:35','HAP'),(16,'Xôi thịt chả chứng','Xôi nóng hổi,cùng topping và nước thịt đậm đà','xoi_thit.jpg',1,'2025-05-24 23:38:45','2025-05-25 09:53:38','TRUYEN_THONG');
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `reviews`
--

DROP TABLE IF EXISTS `reviews`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `reviews` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `rating` int DEFAULT NULL,
  `comment` text,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `reviews_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `reviews_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `reviews_chk_1` CHECK (((`rating` >= 1) and (`rating` <= 5)))
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `reviews`
--

LOCK TABLES `reviews` WRITE;
/*!40000 ALTER TABLE `reviews` DISABLE KEYS */;
INSERT INTO `reviews` VALUES (1,1,1,5,'Phở rất ngon, nước dùng đậm đà','2025-04-12 12:06:35'),(2,2,2,4,'Bánh mì ngon nhưng hơi ít nhân','2025-04-12 12:06:35'),(3,3,3,5,'Gà nướng thơm ngon, gia vị đặc biệt','2025-04-12 12:06:35'),(4,4,4,3,'Nem hơi khô, cần cải thiện','2025-04-12 12:06:35'),(5,5,5,5,'Bún chả ngon đúng điệu Hà Nội','2025-04-12 12:06:35'),(6,1,6,4,'Chè đậu đen ngọt thanh, vừa miệng','2025-04-12 12:06:35'),(7,2,7,5,'Cà phê đậm đà hương vị Việt','2025-04-12 12:06:35'),(8,3,8,3,'Bia ngon nhưng hơi đắng','2025-04-12 12:06:35'),(9,4,9,4,'Đậu hũ chiên giòn, ăn chay rất tốt','2025-04-12 12:06:35'),(10,5,10,5,'Bánh xèo giòn rụm, nhân đầy đủ','2025-04-12 12:06:35');
/*!40000 ALTER TABLE `reviews` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(50) NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (2,'admin'),(3,'shipper'),(1,'user');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL,
  `email` varchar(100) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone` varchar(15) DEFAULT NULL,
  `address` varchar(255) DEFAULT NULL,
  `created_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP,
  `updated_at` timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `role_id` int DEFAULT '1',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email` (`email`),
  KEY `fk_role` (`role_id`),
  CONSTRAINT `fk_role` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Nguyễn Văn A','a@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0123456789','Hà Nội, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',1),(2,'Trần Thị B','b@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0987654321','TP.HCM, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',1),(3,'Lê Văn C','c@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0912345678','Đà Nẵng, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',1),(4,'Phạm Thị D','d@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0978123456','Hải Phòng, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',2),(5,'Hoàng Văn E','e@example.com','$2a$10$xJwL5v5Jz5UZJZ5UZJZ5Ue','0965432187','Nha Trang, Việt Nam','2025-04-12 12:06:35','2025-04-12 12:06:35',3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `vouchers`
--

DROP TABLE IF EXISTS `vouchers`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `vouchers` (
  `id` int NOT NULL AUTO_INCREMENT,
  `code` varchar(50) NOT NULL,
  `discount_percent` decimal(5,2) DEFAULT NULL,
  `valid_from` date NOT NULL,
  `valid_to` date NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `code` (`code`),
  CONSTRAINT `vouchers_chk_1` CHECK (((`discount_percent` >= 0) and (`discount_percent` <= 100)))
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `vouchers`
--

LOCK TABLES `vouchers` WRITE;
/*!40000 ALTER TABLE `vouchers` DISABLE KEYS */;
INSERT INTO `vouchers` VALUES (1,'VIETFOOD20',20.00,'2025-04-01','2025-06-30'),(2,'HOTMEAL15',15.00,'2025-05-01','2025-07-31'),(3,'NEWCUS10',10.00,'2025-01-01','2025-12-31'),(4,'SPECIAL25',25.00,'2025-04-15','2025-05-15'),(5,'WEEKEND30',30.00,'2025-04-01','2025-12-31');
/*!40000 ALTER TABLE `vouchers` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2025-05-25 22:18:58
