CREATE DATABASE  IF NOT EXISTS `socialnotes` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `socialnotes`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: socialnotes
-- ------------------------------------------------------
-- Server version	8.1.0

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
-- Table structure for table `acquisto`
--

DROP TABLE IF EXISTS `acquisto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `acquisto` (
  `Username` varchar(30) NOT NULL,
  `CodiceMateriale` int NOT NULL,
  `DataAcquisto` date NOT NULL,
  PRIMARY KEY (`Username`,`CodiceMateriale`),
  KEY `CodiceMateriale` (`CodiceMateriale`),
  CONSTRAINT `Acquisto_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Acquisto_ibfk_2` FOREIGN KEY (`CodiceMateriale`) REFERENCES `materiale` (`CodiceMateriale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `amicizia`
--

DROP TABLE IF EXISTS `amicizia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `amicizia` (
  `Username1` varchar(30) NOT NULL,
  `Username2` varchar(30) NOT NULL,
  `DataInizio` date NOT NULL,
  PRIMARY KEY (`Username1`,`Username2`),
  KEY `Username2` (`Username2`),
  CONSTRAINT `Amicizia_ibfk_1` FOREIGN KEY (`Username1`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Amicizia_ibfk_2` FOREIGN KEY (`Username2`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `chat`
--

DROP TABLE IF EXISTS `chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `chat` (
  `ChatID` int NOT NULL AUTO_INCREMENT,
  `Titolo` longtext NOT NULL,
  PRIMARY KEY (`ChatID`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `contenuto`
--

DROP TABLE IF EXISTS `contenuto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contenuto` (
  `CodiceNews` int NOT NULL,
  `IdFile` int NOT NULL,
  PRIMARY KEY (`CodiceNews`,`IdFile`),
  KEY `IdFile` (`IdFile`),
  CONSTRAINT `Contenuto_ibfk_2` FOREIGN KEY (`IdFile`) REFERENCES `files` (`IdFile`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Contenuto_ibfk_3` FOREIGN KEY (`CodiceNews`) REFERENCES `news` (`CodiceNews`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `corso`
--

DROP TABLE IF EXISTS `corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `corso` (
  `CodiceCorso` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  `NomeDipartimento` varchar(400) DEFAULT NULL,
  `Denominazione` varchar(300) DEFAULT NULL,
  PRIMARY KEY (`CodiceCorso`),
  KEY `fk_dipartimento` (`NomeDipartimento`,`Denominazione`),
  CONSTRAINT `fk_dipartimento` FOREIGN KEY (`NomeDipartimento`, `Denominazione`) REFERENCES `dipartimento` (`Nome`, `Denominazione`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=48 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `dipartimento`
--

DROP TABLE IF EXISTS `dipartimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `dipartimento` (
  `Nome` varchar(400) NOT NULL,
  `Denominazione` varchar(300) NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  PRIMARY KEY (`Nome`,`Denominazione`),
  KEY `Denominazione` (`Denominazione`),
  CONSTRAINT `Dipartimento_ibfk_1` FOREIGN KEY (`Denominazione`) REFERENCES `universita` (`Denominazione`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `feedback`
--

DROP TABLE IF EXISTS `feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `feedback` (
  `CodiceMateriale` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL,
  `DataFeed` datetime NOT NULL,
  `Commento` longtext NOT NULL,
  `Valutazione` int NOT NULL,
  PRIMARY KEY (`CodiceMateriale`,`Username`,`DataFeed`),
  KEY `Username` (`Username`),
  CONSTRAINT `Feedback_ibfk_1` FOREIGN KEY (`CodiceMateriale`) REFERENCES `materiale` (`CodiceMateriale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Feedback_ibfk_2` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `files`
--

DROP TABLE IF EXISTS `files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `files` (
  `FileName` varchar(100) NOT NULL,
  `Formato` varchar(40) NOT NULL,
  `Contenuto` longblob NOT NULL,
  `Dimensione` int NOT NULL,
  `IdFile` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IdFile`)
) ENGINE=InnoDB AUTO_INCREMENT=134 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `interesse`
--

DROP TABLE IF EXISTS `interesse`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `interesse` (
  `Username` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
  `CodiceCorso` int NOT NULL,
  `DataInserimento` date NOT NULL,
  PRIMARY KEY (`Username`,`CodiceCorso`),
  KEY `CodiceCorso` (`CodiceCorso`),
  CONSTRAINT `interesse_ibfk_1` FOREIGN KEY (`CodiceCorso`) REFERENCES `corso` (`CodiceCorso`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `interesse_ibfk_2` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `materiale`
--

DROP TABLE IF EXISTS `materiale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `materiale` (
  `CodiceMateriale` int NOT NULL AUTO_INCREMENT,
  `DataCaricamento` date NOT NULL,
  `Keywords` longtext,
  `Costo` int DEFAULT NULL,
  `Descrizione` varchar(100) NOT NULL,
  `Hidden` tinyint(1) NOT NULL,
  `CodiceCorso` int NOT NULL,
  `Username` varchar(30) DEFAULT NULL,
  `nomeFile` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`CodiceMateriale`),
  KEY `CodiceCorso` (`CodiceCorso`),
  KEY `Username` (`Username`),
  CONSTRAINT `Materiale_ibfk_1` FOREIGN KEY (`CodiceCorso`) REFERENCES `corso` (`CodiceCorso`) ON UPDATE CASCADE,
  CONSTRAINT `Materiale_ibfk_2` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=115 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `messaggio`
--

DROP TABLE IF EXISTS `messaggio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `messaggio` (
  `IDMessaggio` int NOT NULL AUTO_INCREMENT,
  `Testo` longtext NOT NULL,
  `DataInvio` datetime NOT NULL,
  `Username` varchar(30) DEFAULT NULL,
  `ChatID` int NOT NULL,
  PRIMARY KEY (`IDMessaggio`),
  KEY `Username` (`Username`),
  KEY `ChatID` (`ChatID`),
  CONSTRAINT `Messaggio_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `Messaggio_ibfk_3` FOREIGN KEY (`ChatID`) REFERENCES `chat` (`ChatID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=579 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `metodopagamento`
--

DROP TABLE IF EXISTS `metodopagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `metodopagamento` (
  `NumeroCarta` varchar(16) NOT NULL,
  `DataScadenza` date NOT NULL,
  `NomeIntestatario` varchar(30) NOT NULL,
  `CognomeIntestatario` varchar(30) NOT NULL,
  `Username` varchar(30) NOT NULL,
  PRIMARY KEY (`NumeroCarta`),
  KEY `Username` (`Username`),
  CONSTRAINT `MetodoPagamento_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `news`
--

DROP TABLE IF EXISTS `news`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `news` (
  `CodiceNews` int NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(300) NOT NULL,
  `Argomento` varchar(400) NOT NULL,
  `Contenuto` longtext NOT NULL,
  `Username` varchar(30) NOT NULL,
  `DataCaricamento` date NOT NULL,
  PRIMARY KEY (`CodiceNews`),
  KEY `Username` (`Username`),
  CONSTRAINT `News_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=76 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `partecipazione`
--

DROP TABLE IF EXISTS `partecipazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `partecipazione` (
  `Username` varchar(30) NOT NULL,
  `ChatID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Username`,`ChatID`),
  KEY `ChatID` (`ChatID`),
  CONSTRAINT `Partecipazione_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Partecipazione_ibfk_2` FOREIGN KEY (`ChatID`) REFERENCES `chat` (`ChatID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `segnalazione`
--

DROP TABLE IF EXISTS `segnalazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `segnalazione` (
  `CodiceSegnalazione` int NOT NULL AUTO_INCREMENT,
  `Stato` int NOT NULL,
  `Motivo` longtext NOT NULL,
  `Username` varchar(30) NOT NULL,
  PRIMARY KEY (`CodiceSegnalazione`),
  KEY `Username` (`Username`),
  CONSTRAINT `Segnalazione_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `universita`
--

DROP TABLE IF EXISTS `universita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `universita` (
  `Denominazione` varchar(300) NOT NULL,
  `Indirizzo` varchar(400) DEFAULT NULL,
  `Telefono` char(11) DEFAULT NULL,
  `Email` varchar(30) NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  PRIMARY KEY (`Denominazione`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `utente`
--

DROP TABLE IF EXISTS `utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `utente` (
  `Username` varchar(30) CHARACTER SET latin1 NOT NULL,
  `Nome` varchar(30) CHARACTER SET latin1 NOT NULL,
  `Cognome` varchar(30) CHARACTER SET latin1 NOT NULL,
  `Img` longblob,
  `Email` varchar(50) CHARACTER SET latin1 NOT NULL,
  `Pass` varchar(30) CHARACTER SET latin1 NOT NULL,
  `DataNascita` date DEFAULT NULL,
  `Coin` int DEFAULT NULL,
  `Ban` date DEFAULT NULL,
  `Denominazione` varchar(300) CHARACTER SET latin1 DEFAULT NULL,
  `dipName` varchar(400) CHARACTER SET latin1 DEFAULT NULL,
  `Ruolo` int NOT NULL,
  `Verificato` tinyint NOT NULL DEFAULT '0',
  `Bloccato` datetime DEFAULT NULL,
  PRIMARY KEY (`Username`),
  KEY `Denominazione` (`Denominazione`),
  KEY `DipName` (`dipName`),
  CONSTRAINT `Utente_ibfk_3` FOREIGN KEY (`Denominazione`) REFERENCES `dipartimento` (`Denominazione`) ON UPDATE CASCADE,
  CONSTRAINT `Utente_ibfk_4` FOREIGN KEY (`dipName`) REFERENCES `dipartimento` (`Nome`) ON UPDATE CASCADE
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

-- Dump completed on 2023-10-18 11:05:51
