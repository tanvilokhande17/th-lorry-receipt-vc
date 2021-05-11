-- --------------------------------------------------------
-- Host:                         affinidi.cbon44jkon6h.ap-southeast-1.rds.amazonaws.com
-- Server version:               8.0.23 - Source distribution
-- Server OS:                    Linux
-- HeidiSQL Version:             10.2.0.5599
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;


-- Dumping database structure for VCLR
CREATE DATABASE IF NOT EXISTS `VCLR` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `VCLR`;

-- Dumping structure for table VCLR.LORRYRECEIPT
CREATE TABLE IF NOT EXISTS `LORRYRECEIPT` (
  `id` int NOT NULL AUTO_INCREMENT,
  `receiptNumber` varchar(50) NOT NULL,
  `status` enum('BOOKED','PICKEDUP','INTRANSIT','DELIVERED','COMPLETED','DECLINED','CANCELLED','DELAYED') NOT NULL DEFAULT 'BOOKED',
  `pickUpConsignerSignatureTime` datetime DEFAULT NULL,
  `pickUpDriverSignatureTime` datetime DEFAULT NULL,
  `deliveryDriverSignatureTime` datetime DEFAULT NULL,
  `deliveryConsigneeSignatureTime` datetime DEFAULT NULL,
  `createdAt` datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=39 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table VCLR.LORRYRECEIPTUSERROLE
CREATE TABLE IF NOT EXISTS `LORRYRECEIPTUSERROLE` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lorryReceiptId` int NOT NULL,
  `userRoleId` int NOT NULL,
  `vcId` varchar(50) DEFAULT NULL,
  `isVCStored` smallint NOT NULL DEFAULT '0',
  `vcUrl` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lorryReceiptId_userRoleId` (`lorryReceiptId`,`userRoleId`),
  KEY `FK_LORRYRECEIPTUSERROLE_USERROLE` (`userRoleId`),
  CONSTRAINT `FK_LORRYRECEIPTUSERROLE_LORRYRECEIPT` FOREIGN KEY (`lorryReceiptId`) REFERENCES `LORRYRECEIPT` (`id`),
  CONSTRAINT `FK_LORRYRECEIPTUSERROLE_USERROLE` FOREIGN KEY (`userRoleId`) REFERENCES `USERROLE` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=37 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table VCLR.LORRYRECEIPTVERIFICATION
CREATE TABLE IF NOT EXISTS `LORRYRECEIPTVERIFICATION` (
  `id` int NOT NULL AUTO_INCREMENT,
  `lorryReceiptUserRoleId` int NOT NULL,
  `vcUrl` text NOT NULL,
  `passKey` text NOT NULL,
  `createdAt` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `lorryReceiptId` (`lorryReceiptUserRoleId`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table VCLR.USER
CREATE TABLE IF NOT EXISTS `USER` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullName` varchar(50) NOT NULL,
  `address` varchar(50) DEFAULT NULL,
  `mobileNumber` varchar(50) NOT NULL,
  `did` text NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `mobileNumber` (`mobileNumber`)
) ENGINE=InnoDB AUTO_INCREMENT=38 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

-- Dumping structure for table VCLR.USERROLE
CREATE TABLE IF NOT EXISTS `USERROLE` (
  `id` int NOT NULL AUTO_INCREMENT,
  `userId` int NOT NULL DEFAULT '0',
  `role` enum('TRANSPORTER','CONSIGNER','CONSIGNEE','DRIVER') NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_USERROLE_USER` (`userId`),
  CONSTRAINT `FK_USERROLE_USER` FOREIGN KEY (`userId`) REFERENCES `USER` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

-- Data exporting was unselected.

/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IF(@OLD_FOREIGN_KEY_CHECKS IS NULL, 1, @OLD_FOREIGN_KEY_CHECKS) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
