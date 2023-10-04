CREATE TABLE IF NOT EXISTS `Files` (
  `FileName` varchar(100) NOT NULL,
  `Formato` varchar(40) NOT NULL,
  `Contenuto` longblob NOT NULL,
  `Dimensione` int NOT NULL,
  `IdFile` int  NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`IdFile`)
);