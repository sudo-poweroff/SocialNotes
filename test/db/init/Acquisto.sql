CREATE TABLE `Acquisto` (
  `Username` varchar(30) NOT NULL,
  `CodiceMateriale` int(11) NOT NULL,
  `DataAcquisto` date NOT NULL,
  PRIMARY KEY (`Username`,`CodiceMateriale`),
  KEY `CodiceMateriale` (`CodiceMateriale`),
  CONSTRAINT `Acquisto_ibfk_1` FOREIGN KEY (`Username`) REFERENCES `Utente` (`Username`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Acquisto_ibfk_2` FOREIGN KEY (`CodiceMateriale`) REFERENCES `Materiale` (`CodiceMateriale`) ON DELETE CASCADE ON UPDATE CASCADE
)