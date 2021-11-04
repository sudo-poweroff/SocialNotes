drop database if exists SocialNotes;
create database SocialNotes;
use SocialNotes;


create table Universita(
	Denominazione varchar(30) primary key,
    Indirizzo varchar(40) not null,
    Telefono char(10) not null,
    Email varchar(30) not null,
    Logo blob,
    Descrizione varchar(500) not null 
);


create table Dipartimento(
	Nome varchar(40) not null,
    Denominazione varchar(30) not null,
    Descrizione varchar(500) not null,
    foreign key (Denominazione) references Universita(Denominazione)
    on update cascade
    on delete cascade,
    primary key(Nome,Denominazione)
);


create table Corso(
	CodiceCorso int auto_increment primary key,
    CFU int(2) not null,
    Nome varchar(50) not null,
	Docente varchar(60) not null
);


create table Formato(
	Nome varchar(40) not null,
    Denominazione varchar(30) not null,
    CodiceCorso int auto_increment not null,
    foreign key (Nome) references Dipartimento(Nome)
    on update cascade
    on delete cascade,
    foreign key (Denominazione) references Dipartimento(Denominazione)
    on update cascade
    on delete cascade,
    foreign key (CodiceCorso) references Corso(CodiceCorso)
    on update cascade
    on delete cascade,
    primary key(Nome,Denominazione,CodiceCorso)
);


create table Utente(
	Username varchar(30) primary key,
    Nome varchar(30) not null,
    Cognome varchar(30) not null,
    Img blob,
    Email varchar(50) not null,
    Pass varchar(30) not null,
    DataNascita date not null,
    Matricola varchar(20),
    UltimoAccesso datetime not null,
    Coin int not null,
    Ban date,
    Denominazione varchar(30) not null,
    DipName varchar(40) not null,
    foreign key (Denominazione) references Dipartimento(Denominazione)
    on update cascade
    on delete no action,
    foreign key (DipName) references Dipartimento(Nome)
    on update cascade
    on delete no action
);


create table Amicizia(
	Username1 varchar(30) not null,
    Username2 varchar(30) not null,
    DataInizio date not null,
    foreign key (Username1) references Utente(Username)
    on update cascade
    on delete cascade,
    foreign key (Username2) references Utente(Username)
    on update cascade
    on delete cascade,
    primary key (Username1,Username2)
);


create table Ruolo(
	IDRuolo int auto_increment primary key,
    Ruolo varchar(30) not null
);


create table Copre(
	Username varchar(30) not null,
	IDRuolo int auto_increment not null,
	foreign key (Username) references Utente(Username)
    on update cascade
    on delete cascade,
    foreign key (IDRuolo) references Ruolo(IDRuolo)
    on update cascade
    on delete cascade
);



create table MetodoPagamento(
	NumeroCarta varchar(16) primary key,
    DataScadenza date not null,
    NomeIntestatario varchar(30) not null,
    CognomeIntestatario varchar(30) not null,
    Username varchar(30) not null,
    foreign key (Username) references Utente(Username)
    on update cascade
    on delete cascade
);


create table News(
	CodiceNews int auto_increment primary key,
    Titolo varchar(50) not null,
    Argomento varchar(30) not null,
    Contenuto longtext not null,
    Username varchar(30) not null,
    foreign key (Username) references Utente(Username)
    on update cascade
    on delete no action
);


create table Files(
	FileName varchar(100) primary key,
    Formato varchar(10) not null,
    Contenuto longblob not null,
	Dimensione int not null
);


create table Contenuto(
	CodiceNews int auto_increment not null,
	FileName varchar(100) not null,
    foreign key (CodiceNews) references News(CodiceNews)
    on update cascade
    on delete cascade,
    foreign key (FileName) references Files(FileName)
    on update cascade
    on delete cascade,
    primary key(CodiceNews,FileName)
);


create table Materiale(
	CodiceMateriale int auto_increment primary key,
    DataCaricamento date not null,
    Keywords longtext,
    Costo int(3),
    Descrizione varchar(100) not null,
    Hidden boolean,
    CodiceCorso int not null,
    Username varchar(30) not null,
    FileName varchar(100) not null,
    foreign key (CodiceCorso) references Corso(CodiceCorso)
    on update cascade
    on delete no action,
    foreign key (Username) references  Utente(Username)
    on update cascade
    on delete cascade,
    foreign key (FileName) references Files(FileName)
    on update cascade
    on delete cascade
);


create table Feedback(
	CodiceMateriale int auto_increment,
    Username varchar(30) not null,
    DataFeed datetime not null,
    Commento longtext,
    Valutazione int(1),
	foreign key (CodiceMateriale) references Materiale(CodiceMateriale)
    on update cascade
    on delete cascade,
    foreign key (Username) references Utente(Username)
    on update cascade
    on delete cascade,
    primary key(CodiceMateriale,Username,DataFeed)
);


create table Chat(
	ChatID int auto_increment primary key,
    Titolo varchar(50) not null
);


create table Partecipazione(
	Username varchar(30) not null,
    ChatID int auto_increment not null,
    foreign key (Username) references Utente(Username)
    on update cascade
    on delete cascade,
    foreign key (ChatID) references Chat(ChatID)
    on update cascade
    on delete cascade,
    primary key(Username,ChatID)
);


create table Messaggio(
	IDMessaggio int auto_increment primary key,
    Testo longtext not null,
    DataInvio datetime not null,
    Username varchar(30),
    FileName varchar(100),
    ChatID int not null,
    foreign key (Username) references Utente(Username)
    on update cascade
    on delete set null,
    foreign key (FileName) references Files(FileName)
    on update cascade
    on delete cascade,
    foreign key (ChatID) references Chat(ChatID)
    on update cascade
    on delete cascade
);