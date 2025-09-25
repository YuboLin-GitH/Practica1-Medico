DROP DATABASE IF EXISTS Usuario;
CREATE DATABASE Usuario;
Use Usuario;


CREATE TABLE Paciente(
idPaciente int unsigned auto_increment primary key, 
dni varchar(9),
nombre varchar(30),
pass varchar(256),
direccion varchar(100),
telefono int(9) 
 );
 
  INSERT INTO Paciente  VALUES
 (1,"12345678A","David","david","c/ AAA", 611222333),
 (2,"34564546B","Angel","angel","c/ BBB", 611512183),
 (3,"62145448C","Lucia","lucia","c/ CCC", 611224013),
 (4,"91321654D","Martina","martina","c/ DDD", 618434555),
 (5,"51248345E","Sofia","sofia","c/ EEE", 649161161),
 (6,"84345876F","Hugo","hugo","c/ FFF", 616713488),
 (7,"81431548G","Leo","leo","c/ GGG", 668453178),
 (8,"11501548H","Daniel","daniel","c/ HHH", 691246578);
 

CREATE TABLE Especialidad(
 idEsp int unsigned auto_increment primary key,
 nombreEsp enum("Cirugía","Dermatología","Pediatría","Oftalmología")
);
 
 CREATE TABLE Cita(
 idCita int unsigned auto_increment primary key,
 fechaCita date,
 fk_idEsp int unsigned,
 FOREIGN KEY (fk_idEsp) REFERENCES Especialidad(idEsp),
  fk_idPaciente int unsigned,
 FOREIGN KEY (fk_idPaciente) REFERENCES Especialidad(idPaciente)
 );
 
 
 
