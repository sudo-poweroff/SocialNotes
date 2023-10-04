CREATE TABLE IF NOT EXISTS News (
  `CodiceNews` int NOT NULL AUTO_INCREMENT,
  `Titolo` varchar(300) NOT NULL,
  `Argomento` varchar(400) NOT NULL,
  `Contenuto` longtext NOT NULL,
  `Username` varchar(30) NOT NULL,
  `DataCaricamento` date NOT NULL,
  PRIMARY KEY (`CodiceNews`)
);