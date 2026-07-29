-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th6 08, 2026 lúc 09:01 AM
-- Phiên bản máy phục vụ: 10.4.32-MariaDB
-- Phiên bản PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `baitaplon-javaswing`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chisodiennuoc`
--

CREATE TABLE `chisodiennuoc` (
  `ID` int(11) NOT NULL,
  `Maphong` varchar(10) NOT NULL,
  `Thang` int(11) NOT NULL CHECK (`Thang` between 1 and 12),
  `Nam` int(11) NOT NULL,
  `Chisodiencu` int(11) NOT NULL CHECK (`Chisodiencu` >= 0),
  `Chisodienmoi` int(11) NOT NULL,
  `Chisonuoccu` int(11) NOT NULL CHECK (`Chisonuoccu` >= 0),
  `Chisonuocmoi` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `chitiethoadon`
--

CREATE TABLE `chitiethoadon` (
  `MaCT` int(11) NOT NULL,
  `MaHoaDon` int(11) NOT NULL,
  `MaDV` varchar(50) DEFAULT NULL,
  `Tenkhoanmuc` varchar(100) NOT NULL,
  `Donvitinh` varchar(20) DEFAULT NULL,
  `Soluong` decimal(10,2) NOT NULL,
  `Dongia` decimal(18,0) NOT NULL,
  `Thanhtien` decimal(18,0) GENERATED ALWAYS AS (`Soluong` * `Dongia`) STORED
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `dichvu`
--

CREATE TABLE `dichvu` (
  `MaDV` varchar(50) NOT NULL,
  `TenDV` varchar(50) NOT NULL,
  `Donvitinh` varchar(50) DEFAULT NULL,
  `Dongia` decimal(18,0) NOT NULL CHECK (`Dongia` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hoadon`
--

CREATE TABLE `hoadon` (
  `MaHD` int(11) NOT NULL,
  `MaHopdong` int(11) DEFAULT NULL,
  `Thang` int(11) NOT NULL CHECK (`Thang` between 1 and 12),
  `Nam` int(11) NOT NULL,
  `Ngaylap` date DEFAULT curdate(),
  `Tongtien` decimal(18,0) NOT NULL CHECK (`Tongtien` >= 0),
  `Trangthai` varchar(20) NOT NULL DEFAULT 'Chưa trả'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `hopdong`
--

CREATE TABLE `hopdong` (
  `MaHopDong` int(11) NOT NULL,
  `Maphong` varchar(10) NOT NULL,
  `Makhach` int(11) NOT NULL,
  `Ngaylap` date NOT NULL,
  `Ngaybatdau` date NOT NULL,
  `Ngayketthuc` date DEFAULT NULL,
  `Tiencoc` decimal(18,0) NOT NULL CHECK (`Tiencoc` >= 0),
  `Giathuethang` decimal(18,0) NOT NULL,
  `TrangThaiHopDong` varchar(20) NOT NULL DEFAULT 'Hiệu lực'
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `hopdong`
--

INSERT INTO `hopdong` (`MaHopDong`, `Maphong`, `Makhach`, `Ngaylap`, `Ngaybatdau`, `Ngayketthuc`, `Tiencoc`, `Giathuethang`, `TrangThaiHopDong`) VALUES
(1, 'P101', 101, '2026-01-05', '2026-01-05', '2026-04-05', 3000000, 5000000, 'Hiệu lực'),
(2, 'P102', 102, '2026-01-05', '2026-01-05', '2026-02-05', 4000000, 4500000, 'Hiệu lực'),
(4, 'P201', 201, '2026-01-05', '2026-01-05', '2026-07-05', 123456, 123456, 'Hiệu lực'),
(5, 'P202', 202, '2026-01-05', '2026-01-05', '2026-05-05', 2000000, 3000000, 'Hiệu lực'),
(6, 'P101', 301, '2026-01-05', '2026-01-05', '2026-01-05', 1000000, 3000000, 'Hiệu lực'),
(7, 'P301', 4, '2026-01-05', '2026-01-05', '2026-04-15', 5414545, 526554, 'Hiệu lực');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `khachthue`
--

CREATE TABLE `khachthue` (
  `Makhach` int(11) NOT NULL,
  `Hoten` varchar(100) NOT NULL,
  `CCCD` varchar(20) DEFAULT NULL,
  `SDT` varchar(15) DEFAULT NULL,
  `Gioitinh` varchar(10) DEFAULT NULL,
  `Ngaysinh` date DEFAULT NULL,
  `Quequan` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `khachthue`
--

INSERT INTO `khachthue` (`Makhach`, `Hoten`, `CCCD`, `SDT`, `Gioitinh`, `Ngaysinh`, `Quequan`) VALUES
(3, 'manh', '2235987655', '122334509', 'Nam', '2026-01-12', 'hà tĩnh'),
(4, 'huyền', '098765434', '1234556677', 'Nữ', '2026-01-02', 'hà nội'),
(101, 'Nguyễn Văn A', '0123456789', '0912345678', 'Nam', '2000-01-01', 'Hà Nội'),
(102, 'Trần Thị B', '0123456788', '0912345679', 'Nữ', '1998-05-12', 'Đà Nẵng'),
(201, 'Lê Văn C', '0123456787', '0912345680', 'Nam', '1995-10-20', 'TP HCM'),
(202, 'Phạm Minh D', '0123456786', '0912345681', 'Nam', '2001-03-15', 'Cần Thơ'),
(301, 'Hoàng Thị E', '0123456785', '0912345682', 'Nữ', '1992-12-30', 'Hải Phòng'),
(302, 'Đỗ Lan Hương', '0098765432', '0987626443', 'Nữ', '2005-10-08', 'Hải Phòng'),
(305, 'Nguyễn Quang Huy', '0987654567', '0345782344', 'Nam', '2023-01-05', 'Hà Tĩnh');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `loaiphong`
--

CREATE TABLE `loaiphong` (
  `Maloaiphong` int(11) NOT NULL,
  `Tenloai` varchar(50) NOT NULL,
  `Dongia` decimal(18,0) NOT NULL CHECK (`Dongia` >= 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `loaiphong`
--

INSERT INTO `loaiphong` (`Maloaiphong`, `Tenloai`, `Dongia`) VALUES
(1, 'Phòng tiêu chuẩn', 2000000),
(2, 'Phòng cao cấp', 3500000),
(3, 'Phòng VIP', 5000000);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `phongtro`
--

CREATE TABLE `phongtro` (
  `Maphong` varchar(10) NOT NULL,
  `Tenphong` varchar(50) NOT NULL,
  `Dientich` int(11) DEFAULT NULL,
  `Trangthai` varchar(20) NOT NULL DEFAULT 'Trống',
  `Maloaiphong` int(11) DEFAULT NULL,
  `Makhach` int(11) DEFAULT NULL,
  `Mats` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `phongtro`
--

INSERT INTO `phongtro` (`Maphong`, `Tenphong`, `Dientich`, `Trangthai`, `Maloaiphong`, `Makhach`, `Mats`) VALUES
('P101', 'Phòng 101 - Tầng 1', 25, 'Đang thuê', 1, 101, 1),
('P102', 'Phòng 102 - Tầng 1', 25, 'Đang thuê', 1, 102, 1),
('P201', 'Phòng 201 - Tầng 2', 30, 'Đang thuê', 2, 201, 2),
('P202', 'Phòng 202 - Tầng 2', 30, 'Đang thuê', 2, 202, 2),
('P301', 'Phòng VIP 301', 50, 'Đang thuê', 3, 301, 3);

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taikhoan`
--

CREATE TABLE `taikhoan` (
  `Tendangnhap` varchar(50) NOT NULL,
  `Matkhau` varchar(50) NOT NULL,
  `Hoten` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taikhoan`
--

INSERT INTO `taikhoan` (`Tendangnhap`, `Matkhau`, `Hoten`) VALUES
('admin', '123', 'Huy');

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `taisan`
--

CREATE TABLE `taisan` (
  `Mats` int(11) NOT NULL,
  `Tents` varchar(100) NOT NULL,
  `Soluong` int(11) NOT NULL CHECK (`Soluong` >= 0),
  `Tinhtrang` varchar(50) DEFAULT NULL,
  `Maphong` varchar(10) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `taisan`
--

INSERT INTO `taisan` (`Mats`, `Tents`, `Soluong`, `Tinhtrang`, `Maphong`) VALUES
(1, 'Giường gỗ', 1, 'Mới', NULL),
(2, 'Tủ lạnh mini', 1, 'Tốt', NULL),
(3, 'Điều hòa Inverter', 1, 'Mới', NULL);

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `chisodiennuoc`
--
ALTER TABLE `chisodiennuoc`
  ADD PRIMARY KEY (`ID`),
  ADD UNIQUE KEY `Maphong` (`Maphong`,`Thang`,`Nam`);

--
-- Chỉ mục cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD PRIMARY KEY (`MaCT`),
  ADD KEY `MaHoaDon` (`MaHoaDon`),
  ADD KEY `MaDV` (`MaDV`);

--
-- Chỉ mục cho bảng `dichvu`
--
ALTER TABLE `dichvu`
  ADD PRIMARY KEY (`MaDV`);

--
-- Chỉ mục cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD PRIMARY KEY (`MaHD`),
  ADD KEY `MaHopdong` (`MaHopdong`);

--
-- Chỉ mục cho bảng `hopdong`
--
ALTER TABLE `hopdong`
  ADD PRIMARY KEY (`MaHopDong`),
  ADD KEY `Maphong` (`Maphong`),
  ADD KEY `Makhach` (`Makhach`);

--
-- Chỉ mục cho bảng `khachthue`
--
ALTER TABLE `khachthue`
  ADD PRIMARY KEY (`Makhach`),
  ADD UNIQUE KEY `CCCD` (`CCCD`);

--
-- Chỉ mục cho bảng `loaiphong`
--
ALTER TABLE `loaiphong`
  ADD PRIMARY KEY (`Maloaiphong`);

--
-- Chỉ mục cho bảng `phongtro`
--
ALTER TABLE `phongtro`
  ADD PRIMARY KEY (`Maphong`),
  ADD KEY `Maloaiphong` (`Maloaiphong`),
  ADD KEY `Makhach` (`Makhach`),
  ADD KEY `FK_Phongtro_TaiSan` (`Mats`);

--
-- Chỉ mục cho bảng `taikhoan`
--
ALTER TABLE `taikhoan`
  ADD PRIMARY KEY (`Tendangnhap`);

--
-- Chỉ mục cho bảng `taisan`
--
ALTER TABLE `taisan`
  ADD PRIMARY KEY (`Mats`),
  ADD KEY `Maphong` (`Maphong`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `chisodiennuoc`
--
ALTER TABLE `chisodiennuoc`
  MODIFY `ID` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  MODIFY `MaCT` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  MODIFY `MaHD` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT cho bảng `hopdong`
--
ALTER TABLE `hopdong`
  MODIFY `MaHopDong` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=8;

--
-- AUTO_INCREMENT cho bảng `khachthue`
--
ALTER TABLE `khachthue`
  MODIFY `Makhach` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=307;

--
-- AUTO_INCREMENT cho bảng `loaiphong`
--
ALTER TABLE `loaiphong`
  MODIFY `Maloaiphong` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT cho bảng `taisan`
--
ALTER TABLE `taisan`
  MODIFY `Mats` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Các ràng buộc cho các bảng đã đổ
--

--
-- Các ràng buộc cho bảng `chisodiennuoc`
--
ALTER TABLE `chisodiennuoc`
  ADD CONSTRAINT `chisodiennuoc_ibfk_1` FOREIGN KEY (`Maphong`) REFERENCES `phongtro` (`Maphong`);

--
-- Các ràng buộc cho bảng `chitiethoadon`
--
ALTER TABLE `chitiethoadon`
  ADD CONSTRAINT `chitiethoadon_ibfk_1` FOREIGN KEY (`MaHoaDon`) REFERENCES `hoadon` (`MaHD`) ON DELETE CASCADE,
  ADD CONSTRAINT `chitiethoadon_ibfk_2` FOREIGN KEY (`MaDV`) REFERENCES `dichvu` (`MaDV`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `hoadon`
--
ALTER TABLE `hoadon`
  ADD CONSTRAINT `hoadon_ibfk_1` FOREIGN KEY (`MaHopdong`) REFERENCES `hopdong` (`MaHopDong`) ON DELETE CASCADE;

--
-- Các ràng buộc cho bảng `hopdong`
--
ALTER TABLE `hopdong`
  ADD CONSTRAINT `hopdong_ibfk_1` FOREIGN KEY (`Maphong`) REFERENCES `phongtro` (`Maphong`),
  ADD CONSTRAINT `hopdong_ibfk_2` FOREIGN KEY (`Makhach`) REFERENCES `khachthue` (`Makhach`);

--
-- Các ràng buộc cho bảng `phongtro`
--
ALTER TABLE `phongtro`
  ADD CONSTRAINT `FK_Phongtro_TaiSan` FOREIGN KEY (`Mats`) REFERENCES `taisan` (`Mats`) ON DELETE SET NULL,
  ADD CONSTRAINT `phongtro_ibfk_1` FOREIGN KEY (`Maloaiphong`) REFERENCES `loaiphong` (`Maloaiphong`) ON DELETE SET NULL,
  ADD CONSTRAINT `phongtro_ibfk_2` FOREIGN KEY (`Makhach`) REFERENCES `khachthue` (`Makhach`) ON DELETE SET NULL;

--
-- Các ràng buộc cho bảng `taisan`
--
ALTER TABLE `taisan`
  ADD CONSTRAINT `taisan_ibfk_1` FOREIGN KEY (`Maphong`) REFERENCES `phongtro` (`Maphong`) ON DELETE SET NULL;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
