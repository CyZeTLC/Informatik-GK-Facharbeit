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
-- Tabellenstruktur für Tabelle `branches`
--

CREATE TABLE `branches` (
  `numeric_id` int(11) NOT NULL,
  `intersection_one` varchar(3) DEFAULT NULL,
  `intersection_two` varchar(3) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Daten für Tabelle `branches`
--

INSERT INTO `branches` (`numeric_id`, `intersection_one`, `intersection_two`) VALUES
(1, 'A', 'B'),
(2, 'B', 'C'),
(3, 'C', 'D'),
(4, 'A', 'E'),
(5, 'E', 'F'),
(6, 'B', 'F'),
(7, 'F', 'G'),
(8, 'C', 'G'),
(9, 'G', 'H'),
(10, 'D', 'H'),
(11, 'I', 'J'),
(12, 'E', 'I'),
(13, 'F', 'J'),
(14, 'K', 'L'),
(15, 'G', 'K'),
(16, 'H', 'L'),
(17, 'K', 'J'),
(18, 'M', 'N'),
(19, 'L', 'N'),
(20, 'I', 'M');

--
-- Indizes der exportierten Tabellen
--

--
-- Indizes für die Tabelle `branches`
--
ALTER TABLE `branches`
  ADD UNIQUE KEY `numeric_id` (`numeric_id`);

--
-- AUTO_INCREMENT für exportierte Tabellen
--

--
-- AUTO_INCREMENT für Tabelle `branches`
--
ALTER TABLE `branches`
  MODIFY `numeric_id` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=21;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
