CREATE TABLE IF NOT EXISTS Partecipazione (
  `Username` varchar(30) NOT NULL,
  `ChatID` int NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`Username`,`ChatID`)
)