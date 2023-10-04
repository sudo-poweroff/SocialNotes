CREATE TABLE IF NOT EXISTS Chat (
  `ChatID` int NOT NULL AUTO_INCREMENT,
  `Titolo` longtext NOT NULL,
  PRIMARY KEY (`ChatID`)
);
CREATE TABLE IF NOT EXISTS Partecipazione(
  `Username` varchar(30) NOT NULL,
  `ChatID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Username`,`ChatID`)
);
CREATE TABLE IF NOT EXISTS `Utente` (
  `Username` varchar(30) NOT NULL,
  `Nome` varchar(30) NOT NULL,
  `Cognome` varchar(30) NOT NULL,
  `Img` blob DEFAULT NULL,
  `Email` varchar(50) NOT NULL,
  `Pass` varchar(30) NOT NULL,
  `DataNascita` date DEFAULT NULL,
  `Coin` int DEFAULT NULL,
  `Ban` date DEFAULT NULL,
  `Denominazione` varchar(300) DEFAULT NULL,
  `dipName` varchar(400) DEFAULT NULL,
  `Ruolo` int NOT NULL,
  PRIMARY KEY (`Username`)
);