create database labjavafx;

use labjavafx;

create table persona (
id_persona int auto_increment not null primary key,
nombres varchar (50) not null,
apellidos varchar (50) not null,
telefono int
);

create table usuario (
id_usuario int auto_increment not null primary key,
email varchar (50) not null,
contrasenia varchar (50) not null,
_id_persona int not null,
foreign key (_id_persona) references persona (id_persona)
);

delimiter $$
create procedure sp_agregar_persona (in _nombres varchar (50), in _apellidos varchar(50), in _telefono int)
begin
	insert into persona (persona.nombres, persona.apellidos, persona.telefono)
    values (_nombres, _apellidos, _telefono);
end$$
delimiter ;

call sp_agregar_persona('Jose','montufar',55585558);

delimiter $$
create procedure sp_buscar_usuario (in _email varchar(50), in _password varchar(50))
begin
	select * from usuario where usuario.email = _email and usuario.contrasenia = _password;
end$$
delimiter ;

delimiter $$
create procedure sp_agregar_usuario (in _email varchar(50), in _contrasenia varchar(50), in idPersona int)
begin
	insert into usuario (usuario.email, usuario.contrasenia, usuario._id_persona)
    values (_email, _contrasenia, idPersona);
end$$
delimiter ;

call sp_agregar_usuario ('jbmontufar85@gmail.com','1234',1);

delimiter $$
create procedure sp_buscarPersona()
begin
	select id_persona from persona order by id_persona desc limit 1;
end$$
delimiter ;

call sp_buscarPersona;

