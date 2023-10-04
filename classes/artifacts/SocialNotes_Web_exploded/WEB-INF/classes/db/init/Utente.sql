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
  `Bloccato` datetime DEFAULT NULL,
  PRIMARY KEY (`Username`)
);

CREATE TABLE IF NOT EXISTS `Feedback` (
  `CodiceMateriale` int NOT NULL,
  `Username` varchar(30) NOT NULL,
  `DataFeed` datetime NOT NULL,
  `Commento` longtext NOT NULL,
  `Valutazione` int NOT NULL,
  PRIMARY KEY (`CodiceMateriale`,`Username`,`DataFeed`)
);


CREATE TABLE  IF NOT EXISTS `Materiale` (
  `CodiceMateriale` int NOT NULL,
  `DataCaricamento` date NOT NULL,
  `Keywords` longtext DEFAULT NULL,
  `Costo` int DEFAULT NULL,
  `Descrizione` varchar(100) NOT NULL,
  `Hidden` int NOT NULL,
  `CodiceCorso` int NOT NULL,
  `Username` varchar(30) DEFAULT NULL,
  `Anteprima` longblob NOT NULL,
  `IdFile` int DEFAULT NULL,
  PRIMARY KEY (`CodiceMateriale`)
);

