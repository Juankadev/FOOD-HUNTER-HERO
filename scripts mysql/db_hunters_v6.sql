/*VERSIÓN 5*/
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
    id_articulo INT AUTO_INCREMENT PRIMARY KEY,
    descripcion VARCHAR(255) NOT NULL,
    precio DECIMAL(10, 2) NOT NULL,
    imagen VARCHAR(255),
    id_categoria INT,
    id_marca INT,
    id_comercio INT,
	estado VARCHAR(50) NOT NULL,
    FOREIGN KEY (id_categoria) REFERENCES Categorias(id_categoria),
    FOREIGN KEY (id_marca) REFERENCES Marcas(id_marca),
   	FOREIGN KEY (id_comercio) REFERENCES Comercios(id_comercio)
) AUTO_INCREMENT = 1000;

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
    comentario TEXT,
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

CREATE TABLE Generar_Qr (
    id_qr INT AUTO_INCREMENT,
    id_comercio INT,
    id_hunter INT,
    estado TINYINT,
    PRIMARY KEY (id_qr),
    FOREIGN KEY (id_comercio) REFERENCES Comercios(id_comercio),
    FOREIGN KEY (id_hunter) REFERENCES Hunters(id_hunter)
);

/*MODIFICACIONES*/

ALTER TABLE Usuarios
MODIFY estado BIT NOT NULL;

ALTER TABLE Beneficios
ADD estado BIT NOT NULL;


ALTER TABLE Articulos
ADD id_comercio INT,
ADD FOREIGN KEY (id_comercio) REFERENCES Comercios(id_comercio);


/*INSERTS A LAS TABLAS*/

INSERT INTO Rangos (descripcion, puntaje) VALUES
    ('Baby Hunter', 500),
    ('Junior Hunter', 1500),
    ('Colossus Hunter', 2500),
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
    ('Limpieza'),
    ('Higiene'),
    ('Electrónicos'),
    ('Ropa'),
    ('Juguetes'),
    ('Hogar'),
    ('Oficina'),
    ('Deportes'),
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
    ('Fantoche'),
    ('Otros');
    
INSERT INTO Usuarios (id_rol, username, password, estado) VALUES
	(3, 'administrador', 'administrador', true),
    (1, 'hunter', 'administrador', true),
    (2, 'comercio', 'administrador', true),
    (1, 'Gian', '111', true),
    (1, 'Luq', '111', true),
    (1, 'Juank', '111', true),
    (1, 'Pablete', '111', true),
    (1, 'Tom', '111', true),
    (2, 'Dia', '222', true),
    (2, 'Carrefour', '222', true),
    (2, 'Walmart', '222', true),
    (2, 'Easy', '222', true),
    (2, 'COTO', '222', true),
    (2, 'Fravega', '222', true),
    (2, 'Musimundo', '222', true);

INSERT INTO Comercios (id_usuario, cuit, razon_social, rubro, correo_electronico, telefono, direccion, aprobado) VALUES
    (2, '12345678901', 'Comercio ABC', 'Alimentos', 'comercio@hero.com', '123-456-7890', 'Calle Comercio 123', 'Aprobado'),
    (2, '11223344556', 'Carrefour', 'Varios', 'Carrefour@hero.com', '1137845392', 'Calle Carrefour 123', 'Aprobado'),
    (2, '66778899001', 'Walmart', 'Electrodomesticos', 'Walmart@hero.com', '11137456554', 'Calle Walmart 123', 'Aprobado'),
    (2, '22446688001', 'Easy', 'Inmuebles', 'Easy@hero.com', '1198989098', 'Calle Easy 123', 'Aprobado'),
    (2, '11335577990', 'COTO', 'Alimentos', 'COTO@hero.com', '1134234354', 'Calle COTO 123', 'Aprobado'),
    (2, '12312312312', 'Fravega', 'Electrodomesticos', 'Fravega@hero.com', '1198989094', 'Calle Fravega 123', 'Aprobado'),
    (2, '45645645645', 'Musimundo', 'Electrodomesticos', 'Musimundo@hero.com', '1198989032', 'Calle Musimundo 123', 'Aprobado'),
    (2, '99999999999', 'DIA', 'Alimentos', 'DIA@hero.com', '1198989090', 'Calle DIA 123', 'Aprobado');

INSERT INTO Hunters (id_usuario, nombre, apellido, dni, sexo, correo_electronico, telefono, direccion, fecha_nacimiento, id_rango) VALUES
    (1, 'Hunter', 'Hero', '12345678', 'Masculino', 'hunter@hero.com', '987-654-3210', 'Calle Hunter 456', '1990-01-01', 1),
    (1, 'Gianluca', 'Salvatori', '43520064', 'Masculino', 'gian@hero.com', '887-654-3210', 'Calle Gian 456', '2001-07-11', 2),
    (1, 'Lucas', 'Gomez', '43543345', 'Masculino', 'lucas@hero.com', '787-654-3210', 'Calle Lucas 456', '2002-05-01', 3),
    (1, 'Juan', 'Rey', '43433435', 'Masculino', 'juan@hero.com', '687-654-3210', 'Calle Juan 456', '2000-01-04', 3),
    (1, 'Pablo', 'Caero', '43540095', 'Masculino', 'pablo@hero.com', '587-654-3210', 'Calle Pablo 456', '1999-03-01', 2),
    (1, 'Tomas', 'Scutti', '43545540', 'Masculino', 'tomas@hero.com', '487-654-3210', 'Calle Tomas 456', '1999-02-01', 1);

INSERT INTO Beneficios(id_comercio, descripcion, puntos_requeridos, estado) VALUES
(1, '2x1 en Lacteos', 100, true),
(2, '2x1 en Verduras', 100, true),
(3, '3x2 en Carnes', 150, true),
(4, '3x2 en Panaderia', 150, true),
(5, '4x3 en Bebidas', 200, true),
(1, '15% de Descuento', 200, true),
(2, '10% de Descuento', 150, true),
(3, '20% de Descuento', 300, true),
(4, '30% de Descuento', 350, true),
(5, '40% de Descuento', 400, true),
(1, 'La segunda unidad al 30%', 100, true),
(2, 'La segunda unidad al 40%', 150, true),
(3, 'La segunda unidad al 50%', 200, true),
(4, 'La segunda unidad al 60%', 300, true),
(5, 'La segunda unidad al 70%', 500, true);

INSERT INTO Cazas(id_hunter,id_comercio,puntos,fecha) VALUES
(1, 1, 700, '2023-01-12'),
(2, 2, 1700, '2023-02-11'),
(3, 3, 2700, '2023-03-10'),
(4, 4, 2700, '2023-04-09'),
(5, 5, 1700, '2023-05-08'),
(1, 5, 50, '2023-06-07'),
(2, 4, 50, '2023-07-06'),
(3, 2, 50, '2023-08-05'),
(4, 3, 50, '2023-09-04'),
(5, 1, 50, '2023-10-03'),
(1, 2, 10, '2023-11-02'),
(2, 4, 10, '2023-12-01'),
(3, 1, 10, '2023-11-12'),
(4, 2, 10, '2023-10-11'),
(5, 3, 10, '2023-09-10');

INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Papas Fritas', 6, 600.0, 8, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699568160/rfsz99zcbkfvqwpyf3ow.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Pepsi (Lata 500ml)', 6, 350.0, 6, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699568318/zc0poqxojp4ze45siaax.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Pasta Dental', 6, 600.0, 8, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699568644/qgpaxlmopn8obx7bt5xu.png', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Cerveza Corona (Porrón 725ml)', 6, 900.0, 6, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699569514/sgiprnfrlczb0zy52o1m.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Toallitas Femeninas Always x 16u', 6, 350.0, 8, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699569853/kvampdim8h0wluqshk54.webp', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Milk Nutritiva Nivea', 6, 690.0, 8, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699570065/rrqzej5jquwrfckn9rsm.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Queso Danbo', 6, 695.0, 4, 10, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699571183/zt0muzbnd8yiuu99yuni.webp', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Queso Crema Patagonia', 6, 320.0, 4, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699571352/adulylisd69ktnkqxpup.webp', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Galletitas Oreo', 6, 580.0, 7, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699571565/nu8rwfmiz7epd1vhqpqx.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Galletitas Surtidas', 6, 1100.0, 7, 11, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699571713/tghfyxnxjg7eqjdheava.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Jamón Cocido Paladini', 6, 900.0, 8, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699570845/lrqzmttvjm8zu2sam4ti.webp', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Galletitas Surtidas Terrabusi', 6, 800.0, 7, 12, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699571940/kcf7hp1ickemeerrmgkt.jpg','1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Galletitas Livianas Maná', 6, 360.0, 7, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699572089/egu44og7iuv8u7htrhjp.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Kesitas', 6, 680.0, 8, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699572265/snxpd4abhpc893c2vl1s.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Coquitas', 6, 367.0, 7, 11, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699572438/pxghjwww7f82dweiqlzv.jpg', '1');
INSERT INTO Articulos (descripcion, id_comercio, precio, id_categoria, id_marca, imagen, estado) VALUES ('Galletitas Mediatarde', 6, 395.0, 8, 19, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699572611/bnakql9yim6rqqcqdxts.jpg', '1');

INSERT INTO Caza_x_Articulo(ID_caza, id_articulo, Cantidad) VALUES
(1, 1, 4),
(2, 2, 1),
(3, 3, 3),
(4, 4, 2),
(5, 5, 6),
(6, 6, 4),
(7, 1, 2),
(8, 2, 5),
(9, 3, 5),
(10, 4, 2),
(11, 5, 1),
(12, 9, 8),
(13, 10, 4),
(14, 11, 7),
(15, 12, 5);

INSERT INTO Stocks(id_articulo, id_comercio, fecha_vencimiento, cantidad) VALUES
(1000, 1, '2024-09-10', 500),
(1002, 2, '2024-09-10', 500),
(1003, 3, '2024-09-10', 500),
(1004, 1, '2025-09-10', 300),
(1005, 2, '2025-09-10', 300),
(1006, 3, '2025-09-10', 300),
(1007, 4, '2024-09-10', 500),
(1008, 4, '2024-09-10', 300),
(1009, 4, '2024-09-10', 400),
(1010, 1, '2026-09-10', 400),
(1011, 2, '2026-09-10', 400),
(1012, 3, '2026-09-10', 400),
(1013, 4, '2026-09-10', 200),
(1014, 5, '2024-09-10', 500),
(1015, 5, '2025-09-10', 400);

INSERT INTO Resenas(id_comercio, id_usuario, calificacion, comentario) VALUES
(1, 4, 1, 'Malisimo'),
(2, 5, 2, 'No volveria'),
(3, 6, 3, 'Mucho que desear'),
(4, 7, 4, 'Mala atencion'),
(5, 8, 5, 'Medio pelo'),
(1, 8, 6, 'Podria ser mejor'),
(2, 7, 7, 'Me gusto'),
(3, 6, 8, 'Muy bueno'),
(4, 5, 9, 'Excelente atencion!'),
(5, 4, 10, 'Perfecto comercio!'),
(1, 7, 9, 'Genial atencion!'),
(2, 6, 8, 'Buen comercio'),
(3, 5, 7, 'Me gusto mucho'),
(4, 4, 6, 'Safa');

INSERT INTO Comercios_Favoritos(id_comercio, id_usuario) VALUES
(2, 4),
(3, 4),
(4, 4),
(5, 5),
(6, 5),
(7, 5),
(8, 6),
(8, 7),
(7, 6),
(6, 6),
(5, 7),
(4, 8),
(3, 8),
(2, 8),
(2, 7);







