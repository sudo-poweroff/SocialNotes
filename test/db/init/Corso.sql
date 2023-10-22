CREATE TABLE IF NOT EXISTS Corso(
    `CodiceCorso` int NOT NULL AUTO_INCREMENT,
    `Nome` varchar(50) NOT NULL,
    `NomeDipartimento` varchar(400) DEFAULT NULL,
    `Denominazione` varchar(300) DEFAULT NULL,
    PRIMARY KEY (`CodiceCorso`)
);