CREATE DATABASE db_hunter;

USE db_hunter;

CREATE TABLE Rangos (
    id_rango INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    puntaje INT NOT NULL
);

CREATE TABLE Roles (
    id_rol INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL
);

CREATE TABLE Usuarios (
    id_usuario INT AUTO_INCREMENT PRIMARY KEY,
    id_rol INT,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    estado VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_rol) REFERENCES Roles(id_rol)
);

CREATE TABLE Hunters (
	id_hunter INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    nombre VARCHAR(255) NOT NULL,
    apellido VARCHAR(255) NOT NULL,
    dni VARCHAR(50) NOT NULL,
    sexo VARCHAR(50),
    correo_electronico VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    fecha_nacimiento DATE NOT NULL,
    id_rango INT,
    puntaje INT DEFAULT 0 NOT NULL,
	FOREIGN KEY (id_rango) REFERENCES Rangos(id_rango),
	FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    CHECK (fecha_nacimiento <= CURDATE())
);

CREATE TABLE Comercios (
	id_comercio INT AUTO_INCREMENT PRIMARY KEY,
    id_usuario INT,
    cuit VARCHAR(50) NOT NULL,
    razon_social VARCHAR(255) NOT NULL,
    rubro VARCHAR(255) NOT NULL,
    correo_electronico VARCHAR(255) NOT NULL UNIQUE,
    telefono VARCHAR(255) NOT NULL,
    direccion VARCHAR(255) NOT NULL,
    aprobado VARCHAR(50) NOT NULL,
	FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);

CREATE TABLE Beneficios (
    id_beneficio INT AUTO_INCREMENT PRIMARY KEY,
    id_comercio INT,
    descripcion VARCHAR(255) NOT NULL,
    puntos_requeridos INT NOT NULL,
	FOREIGN KEY (id_comercio) REFERENCES Comercios(id_comercio)
);

CREATE TABLE Cazas (
    id_caza INT AUTO_INCREMENT PRIMARY KEY,
    id_hunter INT,
    id_comercio INT,
    puntos INT DEFAULT 0,
    fecha DATE,
    FOREIGN KEY (id_hunter) REFERENCES Hunters(id_hunter),
	FOREIGN KEY (id_comercio) REFERENCES Comercios(id_comercio)
);

CREATE TABLE Marcas (
    id_marca INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL
);

CREATE TABLE Categorias (
    id_categoria INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL
);

CREATE TABLE Articulos (
    id_articulo INT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    imagen VARCHAR(255),
    id_categoria INT,
    id_marca INT,
	estado VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria),
    FOREIGN KEY (id_marca) REFERENCES Marcas(id_marca)
);

CREATE TABLE Caza_x_Articulo (
    ID_caza INT,
    id_articulo INT,
    Cantidad INT,
    PRIMARY KEY (id_caza, id_articulo),
    FOREIGN KEY (id_caza) REFERENCES Cazas(id_caza),
    FOREIGN KEY (id_articulo) REFERENCES Articulos(id_articulo)
);

CREATE TABLE Stocks (
    id_stock INT AUTO_INCREMENT PRIMARY KEY,
    id_articulo INT,
    id_comercio INT,
    fecha_vencimiento DATE,
    cantidad INT DEFAULT 0,
    FOREIGN KEY (id_articulo) REFERENCES Articulos(id_articulo),
    FOREIGN KEY (id_comercio) REFERENCES Comercios(id_comercio)
);

CREATE TABLE Resenas (
    id_resena INT AUTO_INCREMENT PRIMARY KEY,
    id_comercio INT,
    id_usuario INT,
    calificacion INT,
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario),
    FOREIGN KEY (id_comercio) REFERENCES Comercios(id_comercio)
);

CREATE TABLE Comercios_Favoritos (
    id_comercio_favorito INT AUTO_INCREMENT,
    id_comercio INT,
    id_usuario INT,
    PRIMARY KEY (id_comercio_favorito),
    FOREIGN KEY (id_comercio) REFERENCES Comercios(id_comercio),
    FOREIGN KEY (id_usuario) REFERENCES Usuarios(id_usuario)
);


/*INSERTS A LAS TABLAS*/

INSERT INTO Rangos (descripcion, puntaje) VALUES
    ('Baby Hunter', 100),
    ('Junior Hunter', 500),
    ('Colossus Hunter', 1000),
    ('Grand Hunter', 5000),
    ('THE HUNTER', 10000);

INSERT INTO Roles (descripcion) VALUES
    ('Comercio'),
    ('Hunter'),
    ('Administrador');
    
INSERT INTO Categorias (descripcion) VALUES
    ('Frutas'),
    ('Verduras'),
    ('Carnes'),
    ('Lácteos'),
    ('Panadería'),
    ('Bebidas'),
    ('Dulces'),
    ('Otros');

INSERT INTO Marcas (descripcion) VALUES
    ('Don Satur'),
    ('La Campagnola'),
    ('Luchetti'),
    ('Paty'),
    ('La Virginia'),
    ('Molinos Río de la Plata'),
    ('Arcor'),
    ('Quilmes'),
    ('La Serenísima'),
    ('Sancor'),
    ('Bagley'),
    ('Terrabusi'),
    ('Granja del Sol'),
    ('Villa del Sur'),
    ('Gallo'),
    ('Molto'),
    ('Ilolay'),
    ('La Suipachense'),
    ('Fantoche');
    
INSERT INTO Usuarios (id_rol, username, password, estado) VALUES
	(3, 'administrador', 'administrador', 'Activo'),
    (1, 'hunter', 'administrador', 'Activo'),
    (2, 'comercio', 'administrador', 'Activo'); 

INSERT INTO Comercios (id_usuario, cuit, razon_social, rubro, correo_electronico, telefono, direccion, aprobado) VALUES
    (2, '12345678901', 'Comercio ABC', 'Alimentos', 'comercio@hero.com', '123-456-7890', 'Calle Comercio 123', 'Aprobado');

INSERT INTO Hunters (id_usuario, nombre, apellido, dni, sexo, correo_electronico, telefono, direccion, fecha_nacimiento, id_rango) VALUES
    (1, 'Hunter', 'Hero', '12345678', 'Masculino', 'hunter@hero.com', '987-654-3210', 'Calle Hunter 456', '1990-01-01', 1);   









