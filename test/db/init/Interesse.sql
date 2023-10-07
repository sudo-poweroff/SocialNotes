CREATE TABLE IF NOT EXISTS Corso(
    `Username` varchar(30) CHARACTER SET latin1 COLLATE latin1_swedish_ci NOT NULL,
    `CodiceCorso` int NOT NULL,
    PRIMARY KEY (`Username`,`CodiceCorso`)
);