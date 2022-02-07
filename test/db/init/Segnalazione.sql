CREATE TABLE IF NOT EXISTS Segnalazione (
  `CodiceSegnalazione` int NOT NULL AUTO_INCREMENT,
  `Stato` int NOT NULL,
  `Motivo` longtext NOT NULL,
  `Username` varchar(30) NOT NULL,
  PRIMARY KEY (`CodiceSegnalazione`)
);