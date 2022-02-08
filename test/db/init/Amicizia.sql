CREATE TABLE IF NOT EXISTS `Amicizia` (
  `Username1` varchar(30) NOT NULL,
  `Username2` varchar(30) NOT NULL,
  `DataInizio` date NOT NULL,
  PRIMARY KEY (`Username1`,`Username2`)
  );