-- MySQL dump 10.19  Distrib 10.3.29-MariaDB, for debian-linux-gnueabihf (armv7l)
--
-- Host: localhost    Database: SocialNotes
-- ------------------------------------------------------
-- Server version	10.3.29-MariaDB-0+deb10u1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `Acquisto`
--

DROP TABLE IF EXISTS `Acquisto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Acquisto` (
  `Username` varchar(30) NOT NULL,
  `CodiceMateriale` int(11) NOT NULL,
  `DataAcquisto` date NOT NULL,
  PRIMARY KEY (`Username`,`CodiceMateriale`),
  KEY `CodiceMateriale` (`CodiceMateriale`),
  CONSTRAINT `Acquisto_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Acquisto_ibfk_2` FOREIGN KEY (`CodiceMateriale`) REFERENCES `Materiale` (`CodiceMateriale`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Amicizia`
--

DROP TABLE IF EXISTS `Amicizia`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Amicizia` (
  `Username1` varchar(30) NOT NULL,
  `Username2` varchar(30) NOT NULL,
  `DataInizio` date NOT NULL,
  PRIMARY KEY (`Username1`,`Username2`),
  KEY `Username2` (`Username2`),
  CONSTRAINT `Amicizia_ibfk_1` FOREIGN KEY (`Username1`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Amicizia_ibfk_2` FOREIGN KEY (`Username2`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Chat`
--

DROP TABLE IF EXISTS `Chat`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Chat` (
  `ChatID` int(11) NOT NULL AUTO_INCREMENT,
  `Titolo` longtext NOT NULL,
  PRIMARY KEY (`ChatID`)
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Contenuto`
--

DROP TABLE IF EXISTS `Contenuto`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Contenuto` (
  `CodiceNews` int(11) NOT NULL,
  `IdFile` int(11) NOT NULL,
  PRIMARY KEY (`CodiceNews`,`IdFile`),
  KEY `IdFile` (`IdFile`),
  CONSTRAINT `Contenuto_ibfk_2` FOREIGN KEY (`IdFile`) REFERENCES `Files` (`IdFile`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Contenuto_ibfk_3` FOREIGN KEY (`CodiceNews`) REFERENCES `News` (`CodiceNews`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Corso`
--

DROP TABLE IF EXISTS `Corso`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Corso` (
  `CodiceCorso` int(11) NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  PRIMARY KEY (`CodiceCorso`)
) ENGINE=InnoDB AUTO_INCREMENT=46 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Dipartimento`
--

DROP TABLE IF EXISTS `Dipartimento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Dipartimento` (
  `Nome` varchar(400) NOT NULL,
  `Denominazione` varchar(300) NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  PRIMARY KEY (`Nome`,`Denominazione`),
  KEY `Denominazione` (`Denominazione`),
  CONSTRAINT `Dipartimento_ibfk_1` FOREIGN KEY (`Denominazione`) REFERENCES `Universita` (`Denominazione`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Feedback`
--

DROP TABLE IF EXISTS `Feedback`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Feedback` (
  `CodiceMateriale` int(11) NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL,
  `DataFeed` datetime NOT NULL,
  `Commento` longtext NOT NULL,
  `Valutazione` int(11) NOT NULL,
  PRIMARY KEY (`CodiceMateriale`,`Username`,`DataFeed`),
  KEY `Username` (`Username`),
  CONSTRAINT `Feedback_ibfk_1` FOREIGN KEY (`CodiceMateriale`) REFERENCES `Materiale` (`CodiceMateriale`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Feedback_ibfk_2` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Temporary table structure for view `FeedbackMedia`
--

--
-- Temporary table structure for view `FeedbackUser`
--

--
-- Table structure for table `Files`
--

DROP TABLE IF EXISTS `Files`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Files` (
  `FileName` varchar(100) NOT NULL,
  `Formato` varchar(40) NOT NULL,
  `Contenuto` longblob NOT NULL,
  `Dimensione` int(11) NOT NULL,
  `IdFile` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IdFile`)
) ENGINE=InnoDB AUTO_INCREMENT=120 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Materiale`
--

DROP TABLE IF EXISTS `Materiale`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Materiale` (
  `CodiceMateriale` int(11) NOT NULL AUTO_INCREMENT,
  `DataCaricamento` date NOT NULL,
  `Keywords` longtext DEFAULT NULL,
  `Costo` int(3) DEFAULT NULL,
  `Descrizione` varchar(100) NOT NULL,
  `Hidden` tinyint(1) NOT NULL,
  `CodiceCorso` int(11) NOT NULL,
  `Username` varchar(30) DEFAULT NULL,
  `Anteprima` longblob NOT NULL,
  `IdFile` int(11) DEFAULT NULL,
  PRIMARY KEY (`CodiceMateriale`),
  KEY `CodiceCorso` (`CodiceCorso`),
  KEY `Username` (`Username`),
  KEY `IdFile` (`IdFile`),
  CONSTRAINT `Materiale_ibfk_1` FOREIGN KEY (`CodiceCorso`) REFERENCES `Corso` (`CodiceCorso`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `Materiale_ibfk_2` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `Materiale_ibfk_3` FOREIGN KEY (`IdFile`) REFERENCES `Files` (`IdFile`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=79 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Messaggio`
--

DROP TABLE IF EXISTS `Messaggio`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Messaggio` (
  `IDMessaggio` int(11) NOT NULL AUTO_INCREMENT,
  `Testo` longtext NOT NULL,
  `DataInvio` datetime NOT NULL,
  `Username` varchar(30) DEFAULT NULL,
  `ChatID` int(11) NOT NULL,
  PRIMARY KEY (`IDMessaggio`),
  KEY `Username` (`Username`),
  KEY `ChatID` (`ChatID`),
  CONSTRAINT `Messaggio_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE SET NULL ON UPDATE CASCADE,
  CONSTRAINT `Messaggio_ibfk_3` FOREIGN KEY (`ChatID`) REFERENCES `Chat` (`ChatID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=579 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `MetodoPagamento`
--

DROP TABLE IF EXISTS `MetodoPagamento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `MetodoPagamento` (
  `NumeroCarta` varchar(16) NOT NULL,
  `DataScadenza` date NOT NULL,
  `NomeIntestatario` varchar(30) NOT NULL,
  `CognomeIntestatario` varchar(30) NOT NULL,
  `Username` varchar(30) NOT NULL,
  PRIMARY KEY (`NumeroCarta`),
  KEY `Username` (`Username`),
  CONSTRAINT `MetodoPagamento_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `News`
--

DROP TABLE IF EXISTS `News`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `News` (
  `CodiceNews` int(11) NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(300) NOT NULL,
  `Argomento` varchar(400) NOT NULL,
  `Contenuto` longtext NOT NULL,
  `Username` varchar(30) NOT NULL,
  `DataCaricamento` date NOT NULL,
  PRIMARY KEY (`CodiceNews`),
  KEY `Username` (`Username`),
  CONSTRAINT `News_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=62 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Partecipazione`
--

DROP TABLE IF EXISTS `Partecipazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Partecipazione` (
  `Username` varchar(30) NOT NULL,
  `ChatID` int(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Username`,`ChatID`),
  KEY `ChatID` (`ChatID`),
  CONSTRAINT `Partecipazione_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Partecipazione_ibfk_2` FOREIGN KEY (`ChatID`) REFERENCES `Chat` (`ChatID`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=80 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Segnalazione`
--

DROP TABLE IF EXISTS `Segnalazione`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Segnalazione` (
  `CodiceSegnalazione` int(11) NOT NULL AUTO_INCREMENT,
  `Stato` int(1) NOT NULL,
  `Motivo` longtext NOT NULL,
  `Username` varchar(30) NOT NULL,
  PRIMARY KEY (`CodiceSegnalazione`),
  KEY `Username` (`Username`),
  CONSTRAINT `Segnalazione_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Universita`
--

DROP TABLE IF EXISTS `Universita`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Universita` (
  `Denominazione` varchar(300) NOT NULL,
  `Indirizzo` varchar(400) DEFAULT NULL,
  `Telefono` char(11) DEFAULT NULL,
  `Email` varchar(30) NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  PRIMARY KEY (`Denominazione`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `Utente`
--

DROP TABLE IF EXISTS `Utente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `Utente` (
  `Username` varchar(30) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Img` longblob DEFAULT NULL,
  `Email` varchar(50) NOT NULL,
  `Pass` varchar(30) NOT NULL,
  `DataNascita` date DEFAULT NULL,
  `Coin` int(11) DEFAULT NULL,
  `Ban` date DEFAULT NULL,
  `Denominazione` varchar(300) DEFAULT NULL,
  `dipName` varchar(400) DEFAULT NULL,
  `Ruolo` int(1) NOT NULL,
  PRIMARY KEY (`Username`),
  KEY `Denominazione` (`Denominazione`),
  KEY `DipName` (`dipName`),
  CONSTRAINT `Utente_ibfk_3` FOREIGN KEY (`Denominazione`) REFERENCES `Dipartimento` (`Denominazione`) ON DELETE NO ACTION ON UPDATE CASCADE,
  CONSTRAINT `Utente_ibfk_4` FOREIGN KEY (`dipName`) REFERENCES `Dipartimento` (`Nome`) ON DELETE NO ACTION ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Final view structure for view `FeedbackMedia`
--

--
-- Final view structure for view `FeedbackUser`
--


-- Dump completed on 2022-03-05 15:24:31
