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
-- Estructura de tabla para la tabla `categories`
--

CREATE TABLE miTiendaSpringCorreaNadia.`categories` (
  `cat_id` int(11) NOT NULL,
  `name` varchar(50) NOT NULL,
  `description` varchar(200) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `categories`
--

INSERT INTO miTiendaSpringCorreaNadia.`categories` (`cat_id`, `name`, `description`) VALUES
(1, 'frutas', 'frutas varias'),
(2, 'verduras', 'verduras varias'),
(3, 'conservas', 'productos en conserva varios'),
(4, 'bebidas', 'bebidas varias'),
(5, 'cereales', 'cereales varios');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `elements`
--

CREATE TABLE miTiendaSpringCorreaNadia.`elements` (
  `ele_id` int(11) NOT NULL,
  `name` varchar(100) NOT NULL,
  `description` varchar(200) DEFAULT NULL,
  `price` double DEFAULT NULL,
  `stock` int(11) NOT NULL DEFAULT '0',
  `category` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `elements`
--

INSERT INTO miTiendaSpringCorreaNadia.`elements` (`ele_id`, `name`, `description`, `price`, `stock`, `category`) VALUES
(1, 'Flour - All Purpose', 'varius nulla facilisi', 1.76, 8, 5),
(2, 'Asparagus - White, Canned', 'platea dictumst', 3.09, 6, 1),
(3, 'Squash - Guords', 'tempus vel pede', 4.91, 7, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `orders`
--

CREATE TABLE miTiendaSpringCorreaNadia.`orders` (
  `order_id` int(11) NOT NULL,
  `date` date DEFAULT NULL,
  `iva` double DEFAULT NULL,
  `username` varchar(20) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `element_order`
--

CREATE TABLE miTiendaSpringCorreaNadia.`element_order` (
  `ele_id` int(11) NOT NULL,
  `order_id` int(11) NOT NULL,
  `quantity` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `users`
--

CREATE TABLE miTiendaSpringCorreaNadia.`users` (
  `username` varchar(20) NOT NULL,
  `password` varchar(32) DEFAULT NULL,
  `name` varchar(100) DEFAULT NULL,
  `email` varchar(100) DEFAULT NULL,
  `admin` tinyint(1) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Volcado de datos para la tabla `users`
--

INSERT INTO miTiendaSpringCorreaNadia.`users` (`username`, `password`, `name`, `email`, `admin`) VALUES
('nadia', 'a2e8cea3392da09d1d31be3fca68efed', 'nadia c', 'nadialiac@hotmail.com', 0),
('Nash', 'a2e8cea3392da09d1d31be3fca68efed', 'Nadia Correa', 'nadiaCorrea@gmail.com', 1),
('Pcaro', '487b5d4113bb0b62a558679bd761594f', 'Pedro Caro', 'pCaro@gmail.com', 0);
--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla  `categories`
--
ALTER TABLE miTiendaSpringCorreaNadia.`categories`
  ADD PRIMARY KEY (`cat_id`);
  
--
-- Indices de la tabla `elements`
--
ALTER TABLE miTiendaSpringCorreaNadia.`elements`
  ADD PRIMARY KEY (`ele_id`),
  ADD KEY `fk_elements` (`category`);

--
-- Indices de la tabla `orders`
--
ALTER TABLE miTiendaSpringCorreaNadia.`orders`
  ADD PRIMARY KEY (`order_id`),
  ADD KEY `fk1_order` (`username`) USING BTREE;

--
-- Indices de la tabla `elementOrder`
--
ALTER TABLE miTiendaSpringCorreaNadia.`element_order`
  ADD PRIMARY KEY (`ele_id`,`order_id`),
  ADD KEY `fk2_sales` (`order_id`);
  
--
-- Indices de la tabla `users`
--
ALTER TABLE miTiendaSpringCorreaNadia.`users`
  ADD PRIMARY KEY (`username`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `categories`
--
ALTER TABLE miTiendaSpringCorreaNadia.`categories`
  MODIFY `cat_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=6;

--
-- AUTO_INCREMENT de la tabla `elements`
--
ALTER TABLE miTiendaSpringCorreaNadia.`elements`
  MODIFY `ele_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `orders`
--
ALTER TABLE miTiendaSpringCorreaNadia.`orders`
  MODIFY `order_id` int(11) NOT NULL AUTO_INCREMENT;
--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `elements`
--
ALTER TABLE miTiendaSpringCorreaNadia.`elements`
  ADD CONSTRAINT `fk_elements` FOREIGN KEY (`category`) REFERENCES `categories` (`cat_id`);
  
--
-- Filtros para la tabla `orders`
--
ALTER TABLE miTiendaSpringCorreaNadia.`orders`
  ADD CONSTRAINT `fk1_order` FOREIGN KEY (`username`) REFERENCES `users` (`username`);

--
-- Filtros para la tabla `element_order`
--
ALTER TABLE miTiendaSpringCorreaNadia.`element_order`
  ADD CONSTRAINT `fk1_sales` FOREIGN KEY (`ele_id`) REFERENCES `elements` (`ele_id`),
  ADD CONSTRAINT `fk2_sales` FOREIGN KEY (`order_id`) REFERENCES `orders` (`order_id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
