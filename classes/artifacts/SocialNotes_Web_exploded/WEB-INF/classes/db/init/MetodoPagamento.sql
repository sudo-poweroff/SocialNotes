CREATE TABLE IF NOT EXISTS `MetodoPagamento` (
  `NumeroCarta` varchar(16) NOT NULL,
  `DataScadenza` date NOT NULL,
  `NomeIntestatario` varchar(30) NOT NULL,
  `CognomeIntestatario` varchar(30) NOT NULL,
  `Username` varchar(30) NOT NULL,
  PRIMARY KEY (`NumeroCarta`)
);