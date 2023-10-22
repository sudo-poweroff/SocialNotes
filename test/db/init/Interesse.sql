CREATE TABLE IF NOT EXISTS Interesse(
    `Username` varchar(30) NOT NULL,
    `CodiceCorso` int NOT NULL,
    `DataInserimento` date NOT NULL,
    PRIMARY KEY (`Username`,`CodiceCorso`)
);

CREATE TABLE IF NOT EXISTS Corso (
    `CodiceCorso` int NOT NULL AUTO_INCREMENT,
    `Nome` varchar(50) NOT NULL,
    `NomeDipartimento` varchar(400) DEFAULT NULL,
    `Denominazione` varchar(300) DEFAULT NULL,
    PRIMARY KEY (`CodiceCorso`)
);

CREATE TABLE IF NOT EXISTS Materiale (
    `CodiceMateriale` int NOT NULL AUTO_INCREMENT,
    `DataCaricamento` date NOT NULL,
    `Keywords` longtext,
    `Costo` int DEFAULT NULL,
    `Descrizione` varchar(100) NOT NULL,
    `Hidden` BOOLEAN NOT NULL,
    `CodiceCorso` int NOT NULL,
    `Username` varchar(30) DEFAULT NULL,
    `nomeFile` varchar(255) DEFAULT NULL,
    PRIMARY KEY (`CodiceMateriale`)
    );