-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 20, 2024 lúc 07:34 PM
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
  `MaTV` int(11) NOT NULL,
  `Email` varchar(255) DEFAULT NULL,
  `HoTen` varchar(255) DEFAULT NULL,
  `Khoa` varchar(255) DEFAULT NULL,
  `Nganh` varchar(255) DEFAULT NULL,
  `Password` varchar(255) DEFAULT NULL,
  `SDT` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thanhvien`
--

INSERT INTO `thanhvien` (`MaTV`, `Email`, `HoTen`, `Khoa`, `Nganh`, `Password`, `SDT`) VALUES
(1120150184, '1120150184@sv.sgu.edu.vn', 'Trần Thị Nữ', 'GDTH', 'GDTH', '1120150184', '0986777444'),
(1121410321, '1121410321@sv.sgu.edu.vn', 'Nguyễn Hoàng Minh', 'CNTT', 'KTPM', '1121410321', '0986555666'),
(1121410322, '1121410322@sv.sgu.edu.vn', 'Hoàng Thị Thanh Huyền', 'QTKD', 'KDTM', '1121410322', '0266777552'),
(1121410326, '1121410326@sv.sgu.edu.vn', 'Võ Thị Diễm My', 'CNTT', 'KTPM', '1121410326', '0266777555'),
(1121530087, '1121530087@sv.sgu.edu.vn', 'Trần Thiếu Nam', 'CNTT', 'KTPM', '1121530087', '1111111112'),
(1123330250, '1123330250@sv.sgu.edu.vn', 'Lương Nhật Hà', 'QTKD', 'QTKS', '1123330250', '0111111120'),
(1123330251, '1123330251@sv.sgu.edu.vn', 'Đặng Văn Anh', 'SP', 'NNA', '1123330251', '0111111123'),
(1123330257, '1123330257@sv.sgu.edu.vn', 'Ngô Tuyết Nhi', 'QTKD', 'QTKD', '1123330257', '0111111113');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thietbi`
--

CREATE TABLE `thietbi` (
  `MaTB` int(11) NOT NULL,
  `MotaTB` varchar(255) DEFAULT NULL,
  `TenTB` varchar(255) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `thongtinsd`
--

CREATE TABLE `thongtinsd` (
  `MaTB` int(11) DEFAULT NULL,
  `MaTT` int(11) NOT NULL,
  `MaTV` int(11) DEFAULT NULL,
  `TGDatcho` datetime(6) DEFAULT NULL,
  `TGMuon` datetime(6) DEFAULT NULL,
  `TGTra` datetime(6) DEFAULT NULL,
  `TGVao` datetime(6) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `thongtinsd`
--

INSERT INTO `thongtinsd` (`MaTB`, `MaTT`, `MaTV`, `TGDatcho`, `TGMuon`, `TGTra`, `TGVao`) VALUES
(NULL, 1, 1120150184, NULL, NULL, NULL, '2022-04-22 12:30:00.523000'),
(NULL, 2, 1121530087, NULL, NULL, NULL, '2022-04-23 10:15:30.137236'),
(NULL, 3, 1121410322, NULL, NULL, NULL, '2022-04-24 08:45:15.138400');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `xuly`
--

CREATE TABLE `xuly` (
  `MaTV` int(11) DEFAULT NULL,
  `MaXL` int(11) NOT NULL,
  `SoTien` double DEFAULT NULL,
  `TrangThaiXL` int(11) DEFAULT NULL,
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
  ADD UNIQUE KEY `UK_jw2o9kryprbnc6esvv4dw3y4t` (`Email`),
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
-- AUTO_INCREMENT cho bảng `thanhvien`
--
ALTER TABLE `thanhvien`
  MODIFY `MaTV` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2147483648;

--
-- AUTO_INCREMENT cho bảng `thietbi`
--
ALTER TABLE `thietbi`
  MODIFY `MaTB` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `thongtinsd`
--
ALTER TABLE `thongtinsd`
  MODIFY `MaTT` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

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
