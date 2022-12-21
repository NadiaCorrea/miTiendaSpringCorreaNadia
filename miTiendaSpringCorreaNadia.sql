-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: mysqlDawes:3306
-- Tiempo de generación: 21-12-2022 a las 09:13:25
-- Versión del servidor: 5.7.22
-- Versión de PHP: 8.0.19

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `miTiendaSpringCorreaNadia`
CREATE DATABASE miTiendaSpringCorreaNadia;
--
-- Creación de usuario
CREATE USER 'correaNadia'@'%' IDENTIFIED BY 'correaNadia';
GRANT ALL PRIVILEGES ON miTiendaSpringCorreaNadia.* to 'correaNadia'@'%';
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `CATEGORIES`
--

CREATE TABLE miTiendaSpringCorreaNadia.`CATEGORIES` (
  `cat_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `CATEGORIES`
--

INSERT INTO miTiendaSpringCorreaNadia.`CATEGORIES` (`cat_id`, `name`, `description`) VALUES
(1, 'frutas', 'frutas varias'),
(2, 'verduras', 'verduras varias'),
(3, 'conservas', 'productos en conserva varios'),
(4, 'bebidas', 'bebidas varias'),
(5, 'cereales', 'cereales varios');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ELEMENTS`
--

CREATE TABLE miTiendaSpringCorreaNadia.`ELEMENTS` (
  `ele_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `stock` int(11) NOT NULL DEFAULT '0',
  `category` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `ELEMENTS`
--

INSERT INTO miTiendaSpringCorreaNadia.`ELEMENTS` (`ele_id`, `name`, `description`, `price`, `stock`, `category`) VALUES
(1, 'Flour - All Purpose', 'varius nulla facilisi', 1.76, 8, 5),
(2, 'Asparagus - White, Canned', 'platea dictumst', 3.09, 6, 1),
(3, 'Squash - Guords', 'tempus vel pede', 4.91, 7, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `ORDERS`
--

CREATE TABLE miTiendaSpringCorreaNadia.`ORDERS` (
  `order_id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `iva` double DEFAULT NULL,
  `userName` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `SALES`
--

CREATE TABLE miTiendaSpringCorreaNadia.`SALES` (
  `ele_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `salesDate` datetime NOT NULL,
  `quantity` int(11) DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `USERS`
--

CREATE TABLE miTiendaSpringCorreaNadia.`USERS` (
  `userName` varchar(20) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `USERS`
--

INSERT INTO miTiendaSpringCorreaNadia.`USERS` (`userName`, `password`, `name`, `email`, `admin`) VALUES
('Nash', 'a2e8cea3392da09d1d31be3fca68efed', 'Nadia Correa', 'nadiaCorrea@gmail.com', 1),
('Pcaro', '487b5d4113bb0b62a558679bd761594f', 'Pedro Caro', 'pCaro@gmail.com', 0);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `CATEGORIES`
--
ALTER TABLE miTiendaSpringCorreaNadia.`CATEGORIES`
  ADD PRIMARY KEY (`cat_id`);

--
-- Indices de la tabla `ELEMENTS`
--
ALTER TABLE miTiendaSpringCorreaNadia.`ELEMENTS`
  ADD PRIMARY KEY (`ele_id`),
  ADD KEY `fk_elements` (`category`);

--
-- Indices de la tabla `ORDERS`
--
ALTER TABLE miTiendaSpringCorreaNadia.`ORDERS`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `fk1_order` (`userName`);

--
-- Indices de la tabla `SALES`
--
ALTER TABLE miTiendaSpringCorreaNadia.`SALES`
  ADD PRIMARY KEY (`ele_id`,`order_id`),
  ADD KEY `fk2_sales` (`order_id`);

--
-- Indices de la tabla `USERS`
--
ALTER TABLE miTiendaSpringCorreaNadia.`USERS`
  ADD PRIMARY KEY (`userName`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `CATEGORIES`
--
ALTER TABLE miTiendaSpringCorreaNadia.`CATEGORIES`
  MODIFY `cat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `ELEMENTS`
--
ALTER TABLE miTiendaSpringCorreaNadia.`ELEMENTS`
  MODIFY `ele_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `ORDERS`
--
ALTER TABLE miTiendaSpringCorreaNadia.`ORDERS`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `ELEMENTS`
--
ALTER TABLE miTiendaSpringCorreaNadia.`ELEMENTS`
  ADD CONSTRAINT `fk_elements` FOREIGN KEY (`category`) REFERENCES `CATEGORIES` (`cat_id`);

--
-- Filtros para la tabla `ORDERS`
--
ALTER TABLE miTiendaSpringCorreaNadia.`ORDERS`
  ADD CONSTRAINT `fk1_order` FOREIGN KEY (`userName`) REFERENCES `USERS` (`userName`);

--
-- Filtros para la tabla `SALES`
--
ALTER TABLE miTiendaSpringCorreaNadia.`SALES`
  ADD CONSTRAINT `fk1_sales` FOREIGN KEY (`ele_id`) REFERENCES `ELEMENTS` (`ele_id`),
  ADD CONSTRAINT `fk2_sales` FOREIGN KEY (`order_id`) REFERENCES `ORDERS` (`order_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
