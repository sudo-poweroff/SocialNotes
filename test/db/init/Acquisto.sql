CREATE TABLE IF NOT EXISTS Acquisto (
  `Username` varchar(30) NOT NULL,
  `CodiceMateriale` int NOT NULL,
  `DataAcquisto` date NOT NULL,
  PRIMARY KEY (`Username`,`CodiceMateriale`)
);