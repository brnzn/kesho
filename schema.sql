-- MySQL dump 10.13  Distrib 5.6.17, for Linux (x86_64)
--
-- Host: localhost    Database: kesho
-- ------------------------------------------------------
-- Server version	5.6.17

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `CONTACT`
--

DROP TABLE IF EXISTS `CONTACT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CONTACT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SCHOOL_ID` bigint(20) DEFAULT NULL,
  `TITLE` varchar(10) DEFAULT NULL,
  `NAME` varchar(20) DEFAULT NULL,
  `SURNAME` varchar(20) DEFAULT NULL,
  `JOB_TITLE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CONTACT_SCHOOL_FK` (`SCHOOL_ID`),
  CONSTRAINT `CONTACT_ibfk_1` FOREIGN KEY (`SCHOOL_ID`) REFERENCES `SCHOOLS` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=160 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `CONTACT_DETAIL`
--

DROP TABLE IF EXISTS `CONTACT_DETAIL`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `CONTACT_DETAIL` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `CONTACT_ID` bigint(20) DEFAULT NULL,
  `TYPE` char(1) DEFAULT NULL,
  `VALUE` varchar(40) DEFAULT NULL,
  `comments` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `CONTACT_DETAIL_CONTACT_FK` (`CONTACT_ID`),
  CONSTRAINT `CONTACT_DETAIL_ibfk_1` FOREIGN KEY (`CONTACT_ID`) REFERENCES `CONTACT` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=404 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `EDUCATION_HISTORY`
--

DROP TABLE IF EXISTS `EDUCATION_HISTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `EDUCATION_HISTORY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STUDENT_ID` bigint(20) NOT NULL,
  `SCHOOL_ID` bigint(20) DEFAULT NULL,
  `LEVEL` varchar(20) DEFAULT NULL,
  `CLASS` varchar(15) DEFAULT NULL,
  `START_DATE` date NOT NULL,
  `COURSE` varchar(15) DEFAULT NULL,
  `SECONDARY_LEVEL_1` varchar(40) DEFAULT NULL,
  `SECONDARY_LEVEL_2` varchar(40) DEFAULT NULL,
  `COMMENTS` varchar(100) DEFAULT NULL,
  `PREDICTED_END_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `EDUCATION_HISTORY_STUDENTS_FK` (`STUDENT_ID`),
  KEY `EDUCATION_HISTORY_ibfk_2` (`SCHOOL_ID`),
  CONSTRAINT `EDUCATION_HISTORY_ibfk_1` FOREIGN KEY (`STUDENT_ID`) REFERENCES `STUDENTS` (`ID`),
  CONSTRAINT `EDUCATION_HISTORY_ibfk_2` FOREIGN KEY (`SCHOOL_ID`) REFERENCES `SCHOOLS` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=13638 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FAMILY`
--

DROP TABLE IF EXISTS `FAMILY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FAMILY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FAMILY_NAME` varchar(50) NOT NULL,
  `HOME_LOCATION` varchar(20) DEFAULT NULL,
  `ALIVE_PARENTS` tinyint(1) unsigned DEFAULT NULL,
  `MARTIAL_STATUS` tinyint(1) DEFAULT NULL,
  `PRIMARY_CARETAKER` varchar(20) DEFAULT NULL,
  `HOME_SUB_LOCATION` varchar(20) DEFAULT NULL,
  `HOME_CLUSTER_ID` varchar(10) DEFAULT NULL,
  `MAIN_CONTACT_NAME` varchar(20) DEFAULT NULL,
  `MOBLIE` varchar(20) DEFAULT NULL,
  `IS_PHONE_OWNER` tinyint(1) DEFAULT NULL,
  `PHONE_OWNER_NAME` varchar(20) DEFAULT NULL,
  `NUM_KESHO_STUDENTS_AT_ADDRS` tinyint(1) DEFAULT NULL,
  `FAMILY_PROFILE` text,
  `NUM_ADULTS_AT_ADDRS` tinyint(1) DEFAULT NULL,
  `NUM_CHILDREN_AT_ADDRS` tinyint(1) DEFAULT NULL,
  `NUM_OF_WIVES` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `FAMILY_NAME_UNIQUE` (`FAMILY_NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=3157 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `FAMILY_PROFILE`
--

DROP TABLE IF EXISTS `FAMILY_PROFILE`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `FAMILY_PROFILE` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `FAMILY_ID` bigint(20) NOT NULL,
  `DATE` date NOT NULL,
  `COMMENTS` varchar(1200) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_FAMILY_PROFILE_FAMILY_idx` (`FAMILY_ID`),
  CONSTRAINT `fk_FAMILY_PROFILE_FAMILY` FOREIGN KEY (`FAMILY_ID`) REFERENCES `FAMILY` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=645 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `PAYMENT_ARRANGEMENTS`
--

DROP TABLE IF EXISTS `PAYMENT_ARRANGEMENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `PAYMENT_ARRANGEMENTS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `SPONSOR_ID` bigint(20) NOT NULL,
  `STUDENT_ID` bigint(20) NOT NULL,
  `START_DATE` date NOT NULL,
  `END_DATE` date DEFAULT NULL,
  `TYPE` varchar(20) NOT NULL,
  `AMOUNT` decimal(19,6) NOT NULL,
  `CURRENCY` varchar(10) NOT NULL,
  PRIMARY KEY (`ID`),
  KEY `PAYMENT_ARRANGEMENTS_SPONSOR_FK` (`SPONSOR_ID`),
  KEY `PAYMENT_ARRANGEMENTS_STUDENT_FK` (`STUDENT_ID`),
  CONSTRAINT `PAYMENT_ARRANGEMENTS_ibfk_1` FOREIGN KEY (`SPONSOR_ID`) REFERENCES `SPONSORS` (`ID`),
  CONSTRAINT `PAYMENT_ARRANGEMENTS_ibfk_2` FOREIGN KEY (`STUDENT_ID`) REFERENCES `STUDENTS` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SCHOOLS`
--

DROP TABLE IF EXISTS `SCHOOLS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SCHOOLS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(60) DEFAULT NULL,
  `ADDRESS_LINE1` varchar(40) DEFAULT NULL,
  `ADDRESS_LINE2` varchar(30) DEFAULT NULL,
  `ADDRESS_LINE3` varchar(30) DEFAULT NULL,
  `COUNTY` varchar(20) DEFAULT NULL,
  `COUNTRY` varchar(20) DEFAULT NULL,
  `POSTCODE` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  UNIQUE KEY `unique_school_name` (`NAME`)
) ENGINE=InnoDB AUTO_INCREMENT=10198 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `SPONSORS`
--

DROP TABLE IF EXISTS `SPONSORS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `SPONSORS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(30) DEFAULT NULL,
  `SURNAME` varchar(25) DEFAULT NULL,
  `ADDRESS_LINE_1` varchar(50) DEFAULT NULL,
  `ADDRESS_LINE_2` varchar(50) DEFAULT NULL,
  `ADDRESS_LINE_3` varchar(50) DEFAULT NULL,
  `COUNTY` varchar(20) DEFAULT NULL,
  `POSTCODE` varchar(10) DEFAULT NULL,
  `COUNTRY` varchar(20) DEFAULT NULL,
  `PAYEE_TYPE` varchar(15) DEFAULT NULL,
  `HOW_SPONSOR_FOUND_US` varchar(15) DEFAULT NULL,
  `ANONYMOUS` bit(1) DEFAULT b'0',
  `ACTIVE` bit(1) DEFAULT b'0',
  `START_DATE` date DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  `LEVEL_OF_PARTICIPATION` varchar(15) DEFAULT NULL,
  `COMMENTS` varchar(100) DEFAULT NULL,
  `EMAIL1` varchar(45) DEFAULT NULL,
  `EMAIL2` varchar(45) DEFAULT NULL,
  `PHONE` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10261 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `STUDENTS`
--

DROP TABLE IF EXISTS `STUDENTS`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STUDENTS` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(20) DEFAULT NULL,
  `GENDER` char(1) DEFAULT NULL,
  `YEAR_OF_BIRTH` smallint(6) unsigned DEFAULT NULL COMMENT 'YYYY',
  `DISABILITY` tinyint(1) unsigned DEFAULT NULL,
  `CONTACT_NUMBER` varchar(15) DEFAULT NULL,
  `HOME_LOCATION` varchar(20) DEFAULT NULL,
  `ENRICHMENT_SUPPORT` tinyint(1) unsigned DEFAULT NULL COMMENT 'YES/NO',
  `FINANCIAL_SUPPORT_STATUS` varchar(15) DEFAULT NULL COMMENT 'Graduated more than 2 years ago or Sponsorship withdrawn',
  `FINANCIAL_SUPPORT` tinyint(1) unsigned DEFAULT NULL COMMENT 'Yes / No / On hold',
  `FINANCIAL_SUPPORT_STATUS_DETAILS` varchar(50) DEFAULT NULL,
  `EMAIL` varchar(40) DEFAULT NULL,
  `FACEBOOK` varchar(40) DEFAULT NULL,
  `START_DATE` date DEFAULT NULL,
  `LEVEL_OF_SUPPORT` varchar(20) DEFAULT NULL COMMENT 'Kesho agreed level of Financial Support',
  `TOPUP_NEEDED` tinyint(1) unsigned DEFAULT NULL,
  `SHORTFALL` int(10) unsigned DEFAULT NULL,
  `ALUMNI_MEMBER` int(10) unsigned DEFAULT NULL,
  `FAMILY_ID` bigint(20) NOT NULL,
  `LEAVER_STATUS` varchar(33) DEFAULT NULL,
  `TOTAL_SPONSORSHIP_REQUIRED` int(10) unsigned DEFAULT NULL,
  `END_DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `FAMILY_STUDENT_FK` (`FAMILY_ID`),
  CONSTRAINT `FAMILY_STUDENT_FK` FOREIGN KEY (`FAMILY_ID`) REFERENCES `FAMILY` (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=10517 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `STUDENT_CONTACT`
--

DROP TABLE IF EXISTS `STUDENT_CONTACT`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STUDENT_CONTACT` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `OWNER_ID` bigint(20) NOT NULL,
  `VALUE` varchar(40) NOT NULL,
  `TYPE` varchar(1) NOT NULL,
  `COMMENTS` varchar(30) DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_STUDENT_CONTACT_1_idx` (`OWNER_ID`)
) ENGINE=InnoDB AUTO_INCREMENT=3281 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `STUDENT_HISTORY`
--

DROP TABLE IF EXISTS `STUDENT_HISTORY`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `STUDENT_HISTORY` (
  `ID` bigint(20) NOT NULL AUTO_INCREMENT,
  `STUDENT_ID` bigint(20) NOT NULL,
  `DETAILS` varchar(1150) NOT NULL,
  `DATE` date DEFAULT NULL,
  PRIMARY KEY (`ID`),
  KEY `fk_STUDENT_HISTORY_STUDENT_idx` (`STUDENT_ID`),
  CONSTRAINT `fk_STUDENT_HISTORY_STUDENT` FOREIGN KEY (`STUDENT_ID`) REFERENCES `STUDENTS` (`ID`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=94980 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-07-10 18:33:47
