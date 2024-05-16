-- phpMyAdmin SQL Dump
-- version 4.9.5deb2
-- https://www.phpmyadmin.net/
--
-- Host: localhost:3306
-- Erstellungszeit: 16. Mai 2024 um 18:50
-- Server-Version: 10.5.22-MariaDB-1:10.5.22+maria~ubu1804
-- PHP-Version: 7.3.33-5+ubuntu18.04.1+deb.sury.org+1

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET AUTOCOMMIT = 0;
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Datenbank: `cyze_java_school_graph`
--

-- --------------------------------------------------------

--
-- Tabellenstruktur für Tabelle `intersections`
--

CREATE TABLE `intersections` (
  `name` varchar(2) DEFAULT NULL,
  `loc_x` int(11) DEFAULT NULL,
  `loc_y` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `intersections`
--

INSERT INTO `intersections` (`name`, `loc_x`, `loc_y`) VALUES
('A', 50, 50),
('B', 50, 150),
('C', 50, 250),
('D', 50, 350),
('E', 150, 50),
('F', 150, 150),
('G', 150, 250),
('H', 150, 350),
('I', 250, 50),
('J', 250, 150),
('K', 250, 250),
('L', 250, 350),
('M', 400, 50),
('N', 400, 350);

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `intersections`
--
ALTER TABLE `intersections`
  ADD UNIQUE KEY `name` (`name`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
