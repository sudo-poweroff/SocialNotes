CREATE TABLE IF NOT EXISTS Messaggio (
  `IDMessaggio` int NOT NULL AUTO_INCREMENT,
  `Testo` longtext NOT NULL,
  `DataInvio` datetime NOT NULL,
  `Username` varchar(30) DEFAULT NULL,
  `ChatID` int NOT NULL,
  PRIMARY KEY (`IDMessaggio`)
);