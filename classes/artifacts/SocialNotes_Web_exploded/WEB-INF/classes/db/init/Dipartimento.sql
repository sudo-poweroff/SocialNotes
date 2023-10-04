CREATE TABLE IF NOT EXISTS Dipartimento(
  `Nome` varchar(400) NOT NULL,
  `Denominazione` varchar(300) NOT NULL,
  `Descrizione` varchar(500) NOT NULL,
  PRIMARY KEY (`Nome`,`Denominazione`)
);