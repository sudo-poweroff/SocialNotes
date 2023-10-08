CREATE TABLE IF NOT EXISTS Interesse(
    `Username` varchar(30) NOT NULL,
    `CodiceCorso` int NOT NULL,
    PRIMARY KEY (`Username`,`CodiceCorso`)
);