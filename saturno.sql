-- phpMyAdmin SQL Dump
-- version 4.9.1
-- https://www.phpmyadmin.net/
--
-- Host: localhost
-- Generation Time: Nov 30, 2022 at 09:25 PM
-- Server version: 8.0.17
-- PHP Version: 7.3.10

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `saturno`
--

-- --------------------------------------------------------

--
-- Table structure for table `almacen`
--

CREATE TABLE `almacen` (
  `id` int(11) NOT NULL,
  `nombre` varchar(16) NOT NULL,
  `distrito` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `almacen`
--

INSERT INTO `almacen` (`id`, `nombre`, `distrito`) VALUES
(1, 'Moscu', 1),
(2, 'Kazan', 2),
(3, 'Omsk', 3);

--
-- Triggers `almacen`
--
DELIMITER $$
CREATE TRIGGER `Almacen_bi` BEFORE INSERT ON `almacen` FOR EACH ROW begin
	if NEW.nombre = '' then
		signal sqlstate '45000' set
			message_text = 'nombre no puede ser cadena vacía.';
 	end if;
end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `Almacen_bu` BEFORE UPDATE ON `almacen` FOR EACH ROW begin
	if NEW.nombre = '' then
		signal sqlstate '45000' set
			message_text = 'nombre no puede ser cadena vacía.';
 	end if;
end
$$
DELIMITER ;

-- --------------------------------------------------------

--
-- Table structure for table `material`
--

CREATE TABLE `material` (
  `id` int(11) NOT NULL,
  `almacen_id` int(11) NOT NULL,
  `nombre` varchar(32) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci NOT NULL,
  `descripcion` varchar(256) CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci DEFAULT NULL,
  `stock` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

--
-- Dumping data for table `material`
--

INSERT INTO `material` (`id`, `almacen_id`, `nombre`, `descripcion`, `stock`) VALUES
(1, 2, 'Botella de agua 1L', 'Botella de agua de 1 L', 10),
(2, 1, 'Cartucho Ak-47', 'Cartucho de balas de ak-47', 5),
(4, 2, 'Abrigo', 'Abrigo tela azul.', 3);

--
-- Triggers `material`
--
DELIMITER $$
CREATE TRIGGER `material_bi` BEFORE INSERT ON `material` FOR EACH ROW begin 
	if NEW.descripcion= '' THEN
    	signal sqlstate '45000' SET
        	message_text = 'Descripcion no puede ser cadena vacia.';
 	end if;
end
$$
DELIMITER ;
DELIMITER $$
CREATE TRIGGER `material_bu` BEFORE UPDATE ON `material` FOR EACH ROW begin 
	if NEW.descripcion= '' THEN
    	signal sqlstate '45000' SET
        	message_text = 'Descripcion no puede ser cadena vacia.';
 	end if;
end
$$
DELIMITER ;

--
-- Indexes for dumped tables
--

--
-- Indexes for table `almacen`
--
ALTER TABLE `almacen`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `id` (`id`);

--
-- Indexes for table `material`
--
ALTER TABLE `material`
  ADD PRIMARY KEY (`id`),
  ADD UNIQUE KEY `nombre` (`nombre`),
  ADD KEY `almacen_id` (`almacen_id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `almacen`
--
ALTER TABLE `almacen`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=57;

--
-- AUTO_INCREMENT for table `material`
--
ALTER TABLE `material`
  MODIFY `id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=189;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `material`
--
ALTER TABLE `material`
  ADD CONSTRAINT `material_ibfk_1` FOREIGN KEY (`almacen_id`) REFERENCES `almacen` (`id`) ON DELETE RESTRICT ON UPDATE RESTRICT;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
