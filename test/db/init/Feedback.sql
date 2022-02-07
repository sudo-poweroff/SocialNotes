CREATE TABLE IF NOT EXISTS Feedback (
  `CodiceMateriale` int NOT NULL AUTO_INCREMENT,
  `Username` varchar(30) NOT NULL,
  `DataFeed` datetime NOT NULL,
  `Commento` longtext NOT NULL,
  `Valutazione` int NOT NULL,
  PRIMARY KEY (`CodiceMateriale`,`Username`,`DataFeed`)
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

CREATE TABLE IF NOT EXISTS Materiale (
  `CodiceMateriale` int NOT NULL AUTO_INCREMENT,
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

CREATE VIEW IF NOT EXISTS FeedbackMedia AS
Select CodiceMateriale, ROUND(AVG(Valutazione)) AS ValutazioneMedia
FROM Feedback
GROUP BY CodiceMateriale;

CREATE VIEW IF NOT EXISTS FeedbackUser AS
SELECT NULL AS feedback,Username,nome,Cognome,Denominazione,dipName,Img from Utente
UNION
SELECT ROUND(AVG(ValutazioneMedia)) AS feedback, Utente.Username, Utente.Nome, Utente.Cognome,Utente.Denominazione, Utente.dipName, Img
FROM Materiale LEFT JOIN FeedbackMedia ON Materiale.CodiceMateriale = FeedbackMedia.CodiceMateriale INNER JOIN Utente ON Materiale.Username = Utente.Username
group by Utente.Username;


