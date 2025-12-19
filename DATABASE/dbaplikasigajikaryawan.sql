-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1:3305
-- Generation Time: Dec 18, 2025 at 02:12 PM
-- Server version: 10.4.28-MariaDB
-- PHP Version: 8.2.4

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `dbaplikasigajikaryawan`
--

-- --------------------------------------------------------

--
-- Table structure for table `tbgaji`
--

CREATE TABLE `tbgaji` (
  `ktp` varchar(20) NOT NULL,
  `kodepekerjaan` varchar(10) NOT NULL,
  `gajibersih` double DEFAULT 0,
  `gajikotor` double DEFAULT 0,
  `tunjangan` double DEFAULT 0
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbgaji`
--

INSERT INTO `tbgaji` (`ktp`, `kodepekerjaan`, `gajibersih`, `gajikotor`, `tunjangan`) VALUES
('450096', 'JC-001', 10, 10, 10),
('450139', 'JC-001', 10, 10, 10),
('450690', 'JC-004', 4000000, 4500000, 50000),
('450888', 'JC-003', 5000000, 6500000, 100000),
('450024', 'JC-001', 10, 10, 10),
('001', 'JC-001', 1000, 1000, 1230),
('001', 'JC-002', 1000, 100, 100);

-- --------------------------------------------------------

--
-- Table structure for table `tbkaryawan`
--

CREATE TABLE `tbkaryawan` (
  `ktp` varchar(15) NOT NULL,
  `nama` varchar(30) DEFAULT NULL,
  `ruang` int(11) DEFAULT NULL,
  `password` varchar(32) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbkaryawan`
--

INSERT INTO `tbkaryawan` (`ktp`, `nama`, `ruang`, `password`) VALUES
('001', 'ASDACOCO', 1, 'dc5c7986daef50c1e02ab09b442ee34f'),
('0139', 'Verrel', 1, '0cc175b9c0f1b6a831c399e269772661'),
('450024', 'Abdul Hanif', 2, 'da40526f219afb8a602e12c727aed58d'),
('450096', 'Alfreza', 1, '69370e55a7c6e8fe31b087b8fce04efe'),
('450139', 'Verrel Aulia', 1, 'e0da8ba3cb657eae224c41c40cceca1b');

-- --------------------------------------------------------

--
-- Table structure for table `tbpekerjaan`
--

CREATE TABLE `tbpekerjaan` (
  `kodepekerjaan` varchar(15) NOT NULL,
  `namapekerjaan` varchar(50) NOT NULL,
  `jumlahtugas` int(10) NOT NULL DEFAULT 1
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `tbpekerjaan`
--

INSERT INTO `tbpekerjaan` (`kodepekerjaan`, `namapekerjaan`, `jumlahtugas`) VALUES
('JC-001', 'Dosen', 1),
('JC-002', 'Instruktor', 1),
('JC-003', 'Supir', 2),
('JC-004', 'Mekanik Mobil', 2),
('JC-005', 'Penyanyi', 2);

--
-- Indexes for dumped tables
--

--
-- Indexes for table `tbkaryawan`
--
ALTER TABLE `tbkaryawan`
  ADD PRIMARY KEY (`ktp`);

--
-- Indexes for table `tbpekerjaan`
--
ALTER TABLE `tbpekerjaan`
  ADD PRIMARY KEY (`kodepekerjaan`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
