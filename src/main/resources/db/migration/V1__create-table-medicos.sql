    create table medicos (
        id bigint not null auto_increment,
        nombre varchar(255) not null,
        email varchar(255) not null UNIQUE,
        documento varchar(255) not null UNIQUE,
        especialidad varchar (100) not null,
        calle varchar(255) not null,
        distrito varchar(255),
        complemento varchar(255),
        numero varchar(255),
        ciudad varchar(255) not null,

        primary key (id)
    );