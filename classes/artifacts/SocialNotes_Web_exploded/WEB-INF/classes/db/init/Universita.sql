CREATE TABLE IF NOT EXISTS Universita(
  `Denominazione` varchar(300) NOT NULL,
  `Indirizzo` varchar(400) DEFAULT NULL,
  `Telefono` varchar(11) DEFAULT NULL,
  `Email` varchar(30) NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  PRIMARY KEY (`Denominazione`)
);