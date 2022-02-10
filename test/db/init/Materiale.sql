CREATE TABLE IF NOT EXISTS Materiale (              
  `CodiceMateriale` int NOT NULL AUTO_INCREMENT,
  `DataCaricamento` date NOT NULL,
  `Keywords` longtext DEFAULT NULL,                   
  `Costo` int DEFAULT NULL,         
  `Descrizione` varchar(100) NOT NULL,                
  `Hidden` int NOT NULL,                       
  `CodiceCorso` int NOT NULL,                     
  `Username` varchar(30) DEFAULT NULL,
  `Anteprima` longblob NOT NULL,
  `IdFile` int DEFAULT NULL,
  PRIMARY KEY (`CodiceMateriale`)
  );
  
  
  CREATE TABLE IF NOT EXISTS Corso(
  `CodiceCorso` int NOT NULL AUTO_INCREMENT,
  `Nome` varchar(50) NOT NULL,
  PRIMARY KEY (`CodiceCorso`)
);
  
 

