-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: b5cfnyjajb8c7ylxattd-mysql.services.clever-cloud.com:3306
-- Tiempo de generación: 06-11-2023 a las 15:41:32
-- Versión del servidor: 8.0.15-5
-- Versión de PHP: 8.2.11

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `b5cfnyjajb8c7ylxattd`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Articulos`
--

CREATE TABLE `Articulos` (
  `id_articulo` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `precio` decimal(10,2) NOT NULL,
  `imagen` varchar(255) DEFAULT NULL,
  `id_categoria` int(11) DEFAULT NULL,
  `id_marca` int(11) DEFAULT NULL,
  `id_comercio` int(11) DEFAULT NULL,
  `estado` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Articulos`
--

INSERT INTO `Articulos` (`id_articulo`, `descripcion`, `precio`, `imagen`, `id_categoria`, `id_marca`, `id_comercio`, `estado`) VALUES
(1000, 'Dedalitos', 1000.00, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699235488/kacfhtegtx0ealwffx0t.jpg', 7, 3, 6, '1'),
(1001, 'Puré ', 500.00, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699238773/q7vv5csqov7ufwr1d9qy.jpg', 5, 1, 6, '1'),
(1002, 'Arvejas', 500.00, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699239037/zzsuk8kjxhmig2aldshc.jpg', 1, 1, 6, '1'),
(1003, 'Puré ', 200.00, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699240628/lkj0jrexhucm7eyzpn1q.jpg', 1, 1, 6, '1'),
(1004, 'Abraham ', 500.00, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699241145/ptrh4o8klu6tp0bedzou.jpg', 7, 1, 6, '1'),
(1005, 'Disco', 56.00, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699241378/lv8wujzainwhs7i2iju3.jpg', 1, 1, 6, '1'),
(1006, 'Disco', 56.00, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699241378/lv8wujzainwhs7i2iju3.jpg', 1, 1, 6, '1'),
(1007, 'Fideos', 850.00, 'https://www.mamalucchetti.com.ar/wp-content/uploads/2020/04/TIRABUZON-1.png', 8, 3, 6, '1'),
(1008, 'Cerveza', 870.00, 'https://01almacen.com.ar/cdn/shop/products/CERVEZA-QUILMES-473ML-LATA_406dd2_28863.jpg?v=1625667914', 6, 8, 6, '1'),
(1009, 'Vainitas', 20.00, 'https://modomarketar.vteximg.com.br/arquivos/ids/168288/Galletitas-Dulces-Bagley-Sabor-Vainilla-X-152-G-1-10119.jpg?v=638236389665170000', 7, 11, 7, '1'),
(1010, 'ketchup', 420.00, 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTIHnmxgmOkQ5mRt11zyK06N2bjiASOJA_FVA&usqp=CAU', 8, 2, 7, '1'),
(1011, 'Agua', 200.00, 'https://jumboargentina.vtexassets.com/arquivos/ids/620132/Agua-Villa-Del-Sur-Sin-Gas-600cc-2-276804.jpg?v=637466226117600000', 6, 14, 8, '1'),
(1012, 'Alfajor Chocolate', 100.00, 'https://www.distribuidorapop.com.ar/wp-content/uploads/2022/03/ALFAJOR-FANTOCHE-CHOCOLATE-POP-PRECIO.jpg', 7, 19, 8, '1'),
(1013, '', 300.00, 'https://carrefourar.vtexassets.com/arquivos/ids/353141/7790670050766_E02.jpg?v=638247901687230000', 3, 4, 9, '1'),
(1014, 'Yogurt', 150.00, 'https://www.ilolay.com.ar/uploads/productos/1684441268-ddl-respostero-405g-210.png', 4, 17, 9, '1'),
(1015, 'tomatela', 60.00, 'http://res.cloudinary.com/dmtsek7j7/image/upload/v1699282006/un4juqxlq3i0klsecjrl.jpg', 2, 16, 6, '1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Beneficios`
--

CREATE TABLE `Beneficios` (
  `id_beneficio` int(11) NOT NULL,
  `id_comercio` int(11) DEFAULT NULL,
  `descripcion` varchar(255) NOT NULL,
  `puntos_requeridos` int(11) NOT NULL,
  `estado` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Beneficios`
--

INSERT INTO `Beneficios` (`id_beneficio`, `id_comercio`, `descripcion`, `puntos_requeridos`, `estado`) VALUES
(16, 6, '2x1 en Lacteos', 100, b'0'),
(17, 6, '2x1 en Legumbres', 100, b'0'),
(18, 6, '3x2 en Carnes', 150, b'0'),
(19, 6, '10%', 123, b'1'),
(20, 6, '10% de Descuento', 150, b'0'),
(21, 7, '3x2 en Carnes', 150, b'1'),
(22, 7, '15% de Descuento', 200, b'1'),
(23, 7, '10% de Descuento', 150, b'1'),
(24, 7, 'La segunda unidad al 50%', 100, b'1'),
(25, 6, 'Fernet 2x1', 200, b'0'),
(26, 6, 'Vodka 2x1', 2000, b'0'),
(27, 6, '50% off en segunda unidad', 500, b'0'),
(28, 6, '50% off en segunda unidad', 600, b'1'),
(29, 6, '2x1', 1000, b'1'),
(30, 6, '3x2', 400, b'1'),
(31, 6, 'descripcion', 0, b'0'),
(32, 6, 'e', 0, b'0'),
(33, 6, 'aa', 0, b'0'),
(34, 6, 'ads', 0, b'0'),
(35, 6, 'sadasf', 0, b'0'),
(36, 6, 'afe', 1, b'0'),
(37, 6, '50% off en la segunda unidad', 700, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Beneficios_Hunters`
--

CREATE TABLE `Beneficios_Hunters` (
  `id_beneficio_hunter` int(11) NOT NULL,
  `id_beneficio` int(11) DEFAULT NULL,
  `id_hunter` int(11) DEFAULT NULL,
  `estado` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Beneficios_Hunters`
--

INSERT INTO `Beneficios_Hunters` (`id_beneficio_hunter`, `id_beneficio`, `id_hunter`, `estado`) VALUES
(1, 19, 7, b'1');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Categorias`
--

CREATE TABLE `Categorias` (
  `id_categoria` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Categorias`
--

INSERT INTO `Categorias` (`id_categoria`, `descripcion`) VALUES
(1, 'Frutas'),
(2, 'Verduras'),
(3, 'Carnes'),
(4, 'Lácteos'),
(5, 'Panadería'),
(6, 'Bebidas'),
(7, 'Dulces'),
(8, 'Otros');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Cazas`
--

CREATE TABLE `Cazas` (
  `id_caza` int(11) NOT NULL,
  `id_hunter` int(11) DEFAULT NULL,
  `id_comercio` int(11) DEFAULT NULL,
  `puntos` int(11) DEFAULT '0',
  `fecha` date DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Cazas`
--

INSERT INTO `Cazas` (`id_caza`, `id_hunter`, `id_comercio`, `puntos`, `fecha`) VALUES
(1, 7, 6, 200, '2023-11-02'),
(2, 8, 8, 100, '2023-11-02'),
(3, 7, 9, 100, '2023-11-02');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Caza_x_Articulo`
--

CREATE TABLE `Caza_x_Articulo` (
  `ID_caza` int(11) NOT NULL,
  `id_articulo` int(11) NOT NULL,
  `Cantidad` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Caza_x_Articulo`
--

INSERT INTO `Caza_x_Articulo` (`ID_caza`, `id_articulo`, `Cantidad`) VALUES
(1, 1000, 10),
(1, 1001, 8),
(2, 1002, 72),
(2, 1004, 50),
(2, 1006, 15);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Comercios`
--

CREATE TABLE `Comercios` (
  `id_comercio` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `cuit` varchar(50) NOT NULL,
  `razon_social` varchar(255) NOT NULL,
  `rubro` varchar(255) NOT NULL,
  `correo_electronico` varchar(255) NOT NULL,
  `telefono` varchar(255) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `aprobado` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Comercios`
--

INSERT INTO `Comercios` (`id_comercio`, `id_usuario`, `cuit`, `razon_social`, `rubro`, `correo_electronico`, `telefono`, `direccion`, `aprobado`) VALUES
(6, 52, '12345678901', 'Comercio ABC', 'Alimentos', 'comercio@hero.com', 'androidx.appcompat.widget.AppCompatEditText{84d4459 VFED..CL. ........ 63,948-1017,1074 #7f080155 app:id/et_telefono_mc}', 'Calle Comercio 123', 'Aprobado'),
(7, 57, '15-56987561-8', 'tscutti comercio', 'Varios', 't@s.c', '1569542365', 'Calle falsa', 'Aprobado'),
(8, 58, '20333338881', 'Dia', 'Alimentos', 'dia@dia.com', '1188776655', 'garin', 'Aprobado'),
(9, 59, '208383839', 'vital', 'Alimentos ', 'juancruzrey1@hotmail.com', '1177886688', 'garin', 'Aprobado'),
(12, 134, '20332226661', 'maxiconsumo ', 'alimentos', 'juanrey3d@gmail.com', '1199227733', 'garin', 'Pendiente'),
(14, 138, '2233222444', 'test2', 'alimentos ', 'juancruzrey1@hotmail.commm', '1122874477', 'garin', 'Pendiente');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Comercios_Favoritos`
--

CREATE TABLE `Comercios_Favoritos` (
  `id_comercio_favorito` int(11) NOT NULL,
  `id_comercio` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Comercios_Favoritos`
--

INSERT INTO `Comercios_Favoritos` (`id_comercio_favorito`, `id_comercio`, `id_usuario`) VALUES
(6, 6, 51),
(8, 8, 51);

-- --------------------------------------------------------

--
-- Estructura Stand-in para la vista `estadisticas`
-- (Véase abajo para la vista actual)
--
CREATE TABLE `estadisticas` (
`COUNT(*)` bigint(21)
);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Hunters`
--

CREATE TABLE `Hunters` (
  `id_hunter` int(11) NOT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `nombre` varchar(255) NOT NULL,
  `apellido` varchar(255) NOT NULL,
  `dni` varchar(50) NOT NULL,
  `sexo` varchar(50) DEFAULT NULL,
  `correo_electronico` varchar(255) NOT NULL,
  `telefono` varchar(255) NOT NULL,
  `direccion` varchar(255) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `id_rango` int(11) DEFAULT NULL,
  `puntaje` int(11) NOT NULL DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Hunters`
--

INSERT INTO `Hunters` (`id_hunter`, `id_usuario`, `nombre`, `apellido`, `dni`, `sexo`, `correo_electronico`, `telefono`, `direccion`, `fecha_nacimiento`, `id_rango`, `puntaje`) VALUES
(7, 51, 'Hunter', 'hunter', '12345678', 'Masculino', 'hunter@hero.com', '202022626', '20202020', '2023-12-30', 1, 377),
(8, 56, 'tscutti', 'hunter', '45966366', 'Femenino', 't@s.c', '1562369565', '1562369565', '1990-01-01', 5, 0),
(9, 117, 'Felipe', 'Ruiz', '205038623', 'masculino', 'felipitopiola@yahoo.com', '115263015', 'Av Irigoyen 2212', '1998-11-11', 1, 0),
(28, 118, 'Romina', 'Diaz', '204203560', 'Femenino', 'r_diaz12202@yahoo.com', '987654321', 'Av Liniers 220', '1991-02-02', 1, 0),
(29, 119, 'Juan Cruz', 'Rey', '42305863', 'Masculino', 'jrey@corporacionacme.com', '456789123', 'Cazon 1514', '1992-03-03', 1, 0),
(30, 120, 'Ricardo', 'Fort', '25896314', 'Masculino', 'riquifort@fort.com', '321654987', 'Marabotto 2020', '1993-04-04', 1, 0),
(31, 121, 'Pablo', 'Solari', '42803676', 'Masculino', 'pabloriversolari@riverplate.com', 'Colegiales 20', 'Anastasio el pollo 1786', '1994-05-05', 1, 0),
(32, 122, 'Salomon', 'Rondon', '38203530', 'Masculino', 'rondoncito@riverplate.com', '', 'Belgrano 2520', '1995-06-06', 1, 0),
(33, 123, 'Lucas', 'Gomez', '42903876', 'Masculino', 'lucgom99@gmail.com', '987456321', 'Calzadilla 8569', '1996-07-07', 1, 0);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Marcas`
--

CREATE TABLE `Marcas` (
  `id_marca` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Marcas`
--

INSERT INTO `Marcas` (`id_marca`, `descripcion`) VALUES
(1, 'Don Satur'),
(2, 'La Campagnola'),
(3, 'Luchetti'),
(4, 'Paty'),
(5, 'La Virginia'),
(6, 'Molinos Río de la Plata'),
(7, 'Arcor'),
(8, 'Quilmes'),
(9, 'La Serenísima'),
(10, 'Sancor'),
(11, 'Bagley'),
(12, 'Terrabusi'),
(13, 'Granja del Sol'),
(14, 'Villa del Sur'),
(15, 'Gallo'),
(16, 'Molto'),
(17, 'Ilolay'),
(18, 'La Suipachense'),
(19, 'Fantoche');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Rangos`
--

CREATE TABLE `Rangos` (
  `id_rango` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL,
  `puntaje` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Rangos`
--

INSERT INTO `Rangos` (`id_rango`, `descripcion`, `puntaje`) VALUES
(1, 'Baby Hunter', 100),
(2, 'Junior Hunter', 500),
(3, 'Colossus Hunter', 1000),
(4, 'Grand Hunter', 5000),
(5, 'THE HUNTER', 10000);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Resenas`
--

CREATE TABLE `Resenas` (
  `id_resena` int(11) NOT NULL,
  `id_comercio` int(11) DEFAULT NULL,
  `id_usuario` int(11) DEFAULT NULL,
  `calificacion` int(11) DEFAULT NULL,
  `comentario` text NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Resenas`
--

INSERT INTO `Resenas` (`id_resena`, `id_comercio`, `id_usuario`, `calificacion`, `comentario`) VALUES
(1, 6, 56, 5, 'Pablo puto'),
(2, 6, 56, 3, 'Juank puto'),
(3, 6, 51, 3, 'hhhh');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Roles`
--

CREATE TABLE `Roles` (
  `id_rol` int(11) NOT NULL,
  `descripcion` varchar(255) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Roles`
--

INSERT INTO `Roles` (`id_rol`, `descripcion`) VALUES
(1, 'Comercio'),
(2, 'Hunter'),
(3, 'Administrador');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Stocks`
--

CREATE TABLE `Stocks` (
  `id_stock` int(11) NOT NULL,
  `id_articulo` int(11) DEFAULT NULL,
  `id_comercio` int(11) DEFAULT NULL,
  `fecha_vencimiento` date DEFAULT NULL,
  `cantidad` int(11) DEFAULT '0'
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Stocks`
--

INSERT INTO `Stocks` (`id_stock`, `id_articulo`, `id_comercio`, `fecha_vencimiento`, `cantidad`) VALUES
(11, 1000, 6, '2023-12-23', 200),
(12, 1010, 7, '2023-11-21', 200),
(13, 1010, 7, '2023-11-30', 20),
(14, 1001, 6, '2023-11-20', 30),
(15, 1001, 6, '2023-11-28', 30),
(16, 1009, 7, '2023-11-18', 20),
(17, 1012, 8, '2023-12-20', 10),
(18, 1011, 8, '2023-11-24', 36),
(19, 1013, 9, '2023-11-22', 8),
(20, 1014, 9, '2023-11-18', 15),
(21, 1011, 8, '2023-11-29', 30);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `Usuarios`
--

CREATE TABLE `Usuarios` (
  `id_usuario` int(11) NOT NULL,
  `id_rol` int(11) DEFAULT NULL,
  `username` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `estado` bit(1) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `Usuarios`
--

INSERT INTO `Usuarios` (`id_usuario`, `id_rol`, `username`, `password`, `estado`) VALUES
(50, 3, 'administrador', 'administrador', b'1'),
(51, 2, 'hunter', 'administrador', b'1'),
(52, 1, 'comercio', 'administrador', b'1'),
(56, 2, 'tscutti_hunter', 'tscutti', b'1'),
(57, 1, 'tscutti_comercio', 'tscutti', b'1'),
(58, 1, 'dia', 'dia', b'0'),
(59, 1, 'vital', 'vital', b'1'),
(60, 1, 'Teresa', 'kF2$7G6%&X5<9lK2', b'0'),
(61, 1, 'Nellie', 'sC7~@}cdz', b'0'),
(62, 1, 'Eldridge', 'oQ4!j)NyFH~', b'1'),
(64, 1, 'Waylon', 'bK6\'\"Z/08m}4gc', b'1'),
(65, 1, 'Luelle', 'zV1%SFU0J~J/G', b'0'),
(66, 1, 'Adrian', 'xY2@iyY+.L', b'1'),
(67, 1, 'Dacy', 'dO1!{\"X2`', b'0'),
(68, 1, 'Ella', 'qK1.jwD#>GBW,`', b'1'),
(69, 1, 'Joni', 'xS1,>/#B', b'0'),
(70, 1, 'Laurel', 'aA2&Z#NzAt|E', b'0'),
(71, 1, 'Eilis', 'bG3%p_TF6', b'1'),
(72, 1, 'Bev', 'fU8=b?<Gx6', b'1'),
(73, 1, 'Charmane', 'eP8~<9X6Vmd}', b'0'),
(74, 1, 'Dante', 'mY8K4MI', b'1'),
(75, 1, 'Gabriele', 'bR8(iXA8P$n1oD(%', b'0'),
(117, 2, 'fili_po', 'filipo_123', b'1'),
(118, 2, 'balconpiola', 'piolanga123', b'0'),
(119, 2, 'cafeconleche', 'cafecitorico', b'0'),
(120, 2, 'mantequita', 'mantecarda', b'1'),
(121, 2, 'minion', 'Xjl9I23X', b'1'),
(122, 2, 'sabriix', 'VentanaCaos233', b'1'),
(123, 2, 'Lucardio', 'lucass1234', b'1'),
(124, 1, 'juan', 'juan', b'1'),
(129, 2, 'dia2', 'dia2', b'1'),
(131, 1, 'maxiconsumo', 'maxiconsumo', b'1'),
(134, 1, 'maxi2', 'maxi2', b'1'),
(136, 1, 'test', 'test', b'1'),
(138, 1, 'test2', 'test2', b'1');

-- --------------------------------------------------------

--
-- Estructura para la vista `estadisticas`
--
DROP TABLE IF EXISTS `estadisticas`;

CREATE ALGORITHM=UNDEFINED DEFINER=`uaz9mh50egfs1quo`@`%` SQL SECURITY DEFINER VIEW `estadisticas`  AS SELECT count(0) AS `COUNT(*)` FROM `Comercios` WHERE (`Comercios`.`aprobado` = 'Aprobado') ;

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `Articulos`
--
ALTER TABLE `Articulos`
  ADD PRIMARY KEY (`id_articulo`),
  ADD KEY `id_categoria` (`id_categoria`),
  ADD KEY `id_marca` (`id_marca`),
  ADD KEY `id_comercio` (`id_comercio`);

--
-- Indices de la tabla `Beneficios`
--
ALTER TABLE `Beneficios`
  ADD PRIMARY KEY (`id_beneficio`),
  ADD KEY `id_comercio` (`id_comercio`);

--
-- Indices de la tabla `Beneficios_Hunters`
--
ALTER TABLE `Beneficios_Hunters`
  ADD PRIMARY KEY (`id_beneficio_hunter`),
  ADD KEY `id_beneficio` (`id_beneficio`),
  ADD KEY `id_hunter` (`id_hunter`);

--
-- Indices de la tabla `Categorias`
--
ALTER TABLE `Categorias`
  ADD PRIMARY KEY (`id_categoria`);

--
-- Indices de la tabla `Cazas`
--
ALTER TABLE `Cazas`
  ADD PRIMARY KEY (`id_caza`),
  ADD KEY `id_hunter` (`id_hunter`),
  ADD KEY `id_comercio` (`id_comercio`);

--
-- Indices de la tabla `Caza_x_Articulo`
--
ALTER TABLE `Caza_x_Articulo`
  ADD PRIMARY KEY (`ID_caza`,`id_articulo`),
  ADD KEY `id_articulo` (`id_articulo`);

--
-- Indices de la tabla `Comercios`
--
ALTER TABLE `Comercios`
  ADD PRIMARY KEY (`id_comercio`),
  ADD UNIQUE KEY `correo_electronico` (`correo_electronico`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `Comercios_Favoritos`
--
ALTER TABLE `Comercios_Favoritos`
  ADD PRIMARY KEY (`id_comercio_favorito`),
  ADD KEY `id_comercio` (`id_comercio`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `Hunters`
--
ALTER TABLE `Hunters`
  ADD PRIMARY KEY (`id_hunter`),
  ADD UNIQUE KEY `correo_electronico` (`correo_electronico`),
  ADD KEY `id_rango` (`id_rango`),
  ADD KEY `id_usuario` (`id_usuario`);

--
-- Indices de la tabla `Marcas`
--
ALTER TABLE `Marcas`
  ADD PRIMARY KEY (`id_marca`);

--
-- Indices de la tabla `Rangos`
--
ALTER TABLE `Rangos`
  ADD PRIMARY KEY (`id_rango`);

--
-- Indices de la tabla `Resenas`
--
ALTER TABLE `Resenas`
  ADD PRIMARY KEY (`id_resena`),
  ADD KEY `id_usuario` (`id_usuario`),
  ADD KEY `id_comercio` (`id_comercio`);

--
-- Indices de la tabla `Roles`
--
ALTER TABLE `Roles`
  ADD PRIMARY KEY (`id_rol`);

--
-- Indices de la tabla `Stocks`
--
ALTER TABLE `Stocks`
  ADD PRIMARY KEY (`id_stock`),
  ADD KEY `id_articulo` (`id_articulo`),
  ADD KEY `id_comercio` (`id_comercio`);

--
-- Indices de la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `username` (`username`),
  ADD KEY `id_rol` (`id_rol`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `Articulos`
--
ALTER TABLE `Articulos`
  MODIFY `id_articulo` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=1016;

--
-- AUTO_INCREMENT de la tabla `Beneficios`
--
ALTER TABLE `Beneficios`
  MODIFY `id_beneficio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=38;

--
-- AUTO_INCREMENT de la tabla `Beneficios_Hunters`
--
ALTER TABLE `Beneficios_Hunters`
  MODIFY `id_beneficio_hunter` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `Categorias`
--
ALTER TABLE `Categorias`
  MODIFY `id_categoria` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `Cazas`
--
ALTER TABLE `Cazas`
  MODIFY `id_caza` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `Comercios`
--
ALTER TABLE `Comercios`
  MODIFY `id_comercio` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=15;

--
-- AUTO_INCREMENT de la tabla `Comercios_Favoritos`
--
ALTER TABLE `Comercios_Favoritos`
  MODIFY `id_comercio_favorito` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=9;

--
-- AUTO_INCREMENT de la tabla `Hunters`
--
ALTER TABLE `Hunters`
  MODIFY `id_hunter` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=34;

--
-- AUTO_INCREMENT de la tabla `Marcas`
--
ALTER TABLE `Marcas`
  MODIFY `id_marca` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=20;

--
-- AUTO_INCREMENT de la tabla `Rangos`
--
ALTER TABLE `Rangos`
  MODIFY `id_rango` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `Resenas`
--
ALTER TABLE `Resenas`
  MODIFY `id_resena` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `Roles`
--
ALTER TABLE `Roles`
  MODIFY `id_rol` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `Stocks`
--
ALTER TABLE `Stocks`
  MODIFY `id_stock` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=22;

--
-- AUTO_INCREMENT de la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=139;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `Articulos`
--
ALTER TABLE `Articulos`
  ADD CONSTRAINT `Articulos_ibfk_1` FOREIGN KEY (`id_categoria`) REFERENCES `Categorias` (`id_categoria`),
  ADD CONSTRAINT `Articulos_ibfk_2` FOREIGN KEY (`id_marca`) REFERENCES `Marcas` (`id_marca`),
  ADD CONSTRAINT `Articulos_ibfk_3` FOREIGN KEY (`id_comercio`) REFERENCES `Comercios` (`id_comercio`);

--
-- Filtros para la tabla `Beneficios`
--
ALTER TABLE `Beneficios`
  ADD CONSTRAINT `Beneficios_ibfk_1` FOREIGN KEY (`id_comercio`) REFERENCES `Comercios` (`id_comercio`);

--
-- Filtros para la tabla `Beneficios_Hunters`
--
ALTER TABLE `Beneficios_Hunters`
  ADD CONSTRAINT `Beneficios_Hunters_ibfk_1` FOREIGN KEY (`id_beneficio`) REFERENCES `Beneficios` (`id_beneficio`),
  ADD CONSTRAINT `Beneficios_Hunters_ibfk_2` FOREIGN KEY (`id_hunter`) REFERENCES `Hunters` (`id_hunter`);

--
-- Filtros para la tabla `Cazas`
--
ALTER TABLE `Cazas`
  ADD CONSTRAINT `Cazas_ibfk_1` FOREIGN KEY (`id_hunter`) REFERENCES `Hunters` (`id_hunter`),
  ADD CONSTRAINT `Cazas_ibfk_2` FOREIGN KEY (`id_comercio`) REFERENCES `Comercios` (`id_comercio`);

--
-- Filtros para la tabla `Caza_x_Articulo`
--
ALTER TABLE `Caza_x_Articulo`
  ADD CONSTRAINT `Caza_x_Articulo_ibfk_1` FOREIGN KEY (`ID_caza`) REFERENCES `Cazas` (`id_caza`),
  ADD CONSTRAINT `Caza_x_Articulo_ibfk_2` FOREIGN KEY (`id_articulo`) REFERENCES `Articulos` (`id_articulo`);

--
-- Filtros para la tabla `Comercios`
--
ALTER TABLE `Comercios`
  ADD CONSTRAINT `Comercios_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios` (`id_usuario`);

--
-- Filtros para la tabla `Comercios_Favoritos`
--
ALTER TABLE `Comercios_Favoritos`
  ADD CONSTRAINT `Comercios_Favoritos_ibfk_1` FOREIGN KEY (`id_comercio`) REFERENCES `Comercios` (`id_comercio`),
  ADD CONSTRAINT `Comercios_Favoritos_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios` (`id_usuario`);

--
-- Filtros para la tabla `Hunters`
--
ALTER TABLE `Hunters`
  ADD CONSTRAINT `Hunters_ibfk_1` FOREIGN KEY (`id_rango`) REFERENCES `Rangos` (`id_rango`),
  ADD CONSTRAINT `Hunters_ibfk_2` FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios` (`id_usuario`);

--
-- Filtros para la tabla `Resenas`
--
ALTER TABLE `Resenas`
  ADD CONSTRAINT `Resenas_ibfk_1` FOREIGN KEY (`id_usuario`) REFERENCES `Usuarios` (`id_usuario`),
  ADD CONSTRAINT `Resenas_ibfk_2` FOREIGN KEY (`id_comercio`) REFERENCES `Comercios` (`id_comercio`);

--
-- Filtros para la tabla `Stocks`
--
ALTER TABLE `Stocks`
  ADD CONSTRAINT `Stocks_ibfk_1` FOREIGN KEY (`id_articulo`) REFERENCES `Articulos` (`id_articulo`),
  ADD CONSTRAINT `Stocks_ibfk_2` FOREIGN KEY (`id_comercio`) REFERENCES `Comercios` (`id_comercio`);

--
-- Filtros para la tabla `Usuarios`
--
ALTER TABLE `Usuarios`
  ADD CONSTRAINT `Usuarios_ibfk_1` FOREIGN KEY (`id_rol`) REFERENCES `Roles` (`id_rol`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
