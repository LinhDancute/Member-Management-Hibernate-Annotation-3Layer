-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 06, 2024 lúc 06:41 PM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `member-management`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thanhvien`
--

CREATE TABLE `thanhvien` (
  `MaTV` int(10) NOT NULL,
  `HoTen` varchar(100) NOT NULL,
  `Khoa` varchar(100) DEFAULT NULL,
  `Nganh` varchar(100) DEFAULT NULL,
  `SDT` varchar(15) DEFAULT NULL,
  `Password` varchar(10) NOT NULL,
  `Email` varchar(25) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thanhvien`
--

INSERT INTO `thanhvien` (`MaTV`, `HoTen`, `Khoa`, `Nganh`, `SDT`, `Password`, `Email`) VALUES
(1120150184, 'Trần Thị Nữ', 'GDTH', 'GDTH', '1111111111', '', NULL),
(1121530087, 'Trần Thiếu Nam', 'TLH', 'QLGD', '1111111112', '', NULL),
(1123330257, 'Ngô Tuyết Nhi', 'QTKD', 'QTKD', '1111111113', '', NULL),
(2147483647, 'Nguyễn Văn Nam', 'CNTT', 'HTTT', '123456789', '', NULL);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thietbi`
--

CREATE TABLE `thietbi` (
  `MaTB` int(10) NOT NULL,
  `TenTB` varchar(100) NOT NULL,
  `MoTaTB` text DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thietbi`
--

INSERT INTO `thietbi` (`MaTB`, `TenTB`, `MoTaTB`) VALUES
(1000001, 'Micro', 'Micro không dây MS2023'),
(1000002, 'Micro', 'Micro không dây MS2024'),
(1000003, 'Bảng điện tử', 'Bản điện tử trình chiếu');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thongtinsd`
--

CREATE TABLE `thongtinsd` (
  `MaTB` int(11) DEFAULT NULL,
  `MaTT` int(11) NOT NULL,
  `MaTV` int(11) DEFAULT NULL,
  `TGMuon` datetime(6) DEFAULT NULL,
  `TGTra` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xuly`
--

CREATE TABLE `xuly` (
  `MaTV` int(11) DEFAULT NULL,
  `MaXL` int(11) NOT NULL,
  `SoTien` double NOT NULL,
  `TrangThaiXL` int(11) NOT NULL,
  `NgayXL` datetime(6) DEFAULT NULL,
  `HinhThucXL` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `thanhvien`
--
ALTER TABLE `thanhvien`
  ADD PRIMARY KEY (`MaTV`),
  ADD UNIQUE KEY `UK_mj6iyyblc7l1550a6e1dq17li` (`SDT`);

--
-- Chỉ mục cho bảng `thietbi`
--
ALTER TABLE `thietbi`
  ADD PRIMARY KEY (`MaTB`);

--
-- Chỉ mục cho bảng `thongtinsd`
--
ALTER TABLE `thongtinsd`
  ADD PRIMARY KEY (`MaTT`),
  ADD KEY `FKfigww5ottle5lbxah5c5kbyvk` (`MaTV`),
  ADD KEY `FKeu4vojqyrkh768sodsxti4rp7` (`MaTB`);

--
-- Chỉ mục cho bảng `xuly`
--
ALTER TABLE `xuly`
  ADD PRIMARY KEY (`MaXL`),
  ADD KEY `FKa0dgbfa4l5gv4vtgvbnf44o1p` (`MaTV`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `thongtinsd`
--
ALTER TABLE `thongtinsd`
  MODIFY `MaTT` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `xuly`
--
ALTER TABLE `xuly`
  MODIFY `MaXL` int(11) NOT NULL AUTO_INCREMENT;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `thongtinsd`
--
ALTER TABLE `thongtinsd`
  ADD CONSTRAINT `FKeu4vojqyrkh768sodsxti4rp7` FOREIGN KEY (`MaTB`) REFERENCES `thietbi` (`MaTB`),
  ADD CONSTRAINT `FKfigww5ottle5lbxah5c5kbyvk` FOREIGN KEY (`MaTV`) REFERENCES `thanhvien` (`MaTV`);

--
-- Các ràng buộc cho bảng `xuly`
--
ALTER TABLE `xuly`
  ADD CONSTRAINT `FKa0dgbfa4l5gv4vtgvbnf44o1p` FOREIGN KEY (`MaTV`) REFERENCES `thanhvien` (`MaTV`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
