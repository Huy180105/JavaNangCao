/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Connection.JdbcHelper;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import Model.HoaDonModel;

/**
 *
 * @author DELL
 */
public class HoaDonDao {
    /* =====================================================
       1. LẤY DANH SÁCH PHÒNG – THÁNG – NĂM
          ĐÃ CHỐT CHỈ SỐ NHƯNG CHƯA LẬP HÓA ĐƠN
       ===================================================== */
    public List<Object[]> layDanhSachChoLapHoaDon() {
    List<Object[]> list = new ArrayList<>();

    String sql = """
        SELECT DISTINCT p.Maphong
        FROM phongtro p
        JOIN chisodiennuoc cs
             ON p.Maphong = cs.Maphong
        ORDER BY p.Maphong
    """;

    try (Connection conn = JdbcHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            list.add(new Object[]{
                rs.getString("Maphong")
            });
        }

        System.out.println("DEBUG Combo phòng = " + list.size());

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}



    /* =====================================================
       2. LẤY DANH SÁCH HÓA ĐƠN
       ===================================================== */
    /* =====================================================
   2. LẤY DANH SÁCH HÓA ĐƠN (Đã sửa lại câu lệnh SQL)
   ===================================================== */
public List<HoaDonModel> getListHoaDon() {
    List<HoaDonModel> list = new ArrayList<>();

    // Sử dụng JOIN để lấy tên phòng từ bảng hopdong thay vì MaHopDong khô khan
    try (Connection conn = JdbcHelper.getConnection()) {
        String maHoaDonColumn = getMaHoaDonColumn(conn);
        String sql = """
            SELECT hd.*, h.Maphong
            FROM hoadon hd
            JOIN hopdong h ON hd.MaHopDong = h.MaHopDong
            ORDER BY hd.%s DESC
        """.formatted(maHoaDonColumn);

        try (PreparedStatement pstmt = conn.prepareStatement(sql);
         ResultSet rs = pstmt.executeQuery()) {

        while (rs.next()) {
            list.add(new HoaDonModel(
                rs.getString(maHoaDonColumn),
                rs.getString("Maphong"), // Bây giờ đã có Maphong nhờ lệnh JOIN ở trên
                rs.getInt("Thang"),
                rs.getInt("Nam"),
                rs.getDate("Ngaylap"),
                rs.getObject("Tongtien") != null ? rs.getDouble("Tongtien") : 0.0,
                rs.getString("Trangthai")
            ));
        }
        }

    } catch (Exception e) {
        System.out.println("Lỗi tại getListHoaDon: " + e.getMessage());
        e.printStackTrace();
    }
    return list;
}

    /* =====================================================
       3. LẤY DANH SÁCH NĂM CHƯA LẬP HÓA ĐƠN CỦA PHÒNG
       ===================================================== */
    public List<Integer> layNamChuaLap(String maPhong) {
    List<Integer> list = new ArrayList<>();

    String sql = """
        SELECT DISTINCT cs.Nam
        FROM chisodiennuoc cs
        WHERE cs.Maphong = ?
        ORDER BY cs.Nam
    """;

    try (Connection conn = JdbcHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, maPhong);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(rs.getInt("Nam"));
        }

        System.out.println("DEBUG năm = " + list);

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}



    /* =====================================================
       4. LẤY DANH SÁCH THÁNG CHƯA LẬP HÓA ĐƠN
          THEO PHÒNG + NĂM
       ===================================================== */
    public List<Integer> layThangChuaLap(String maPhong, int nam) {
    List<Integer> list = new ArrayList<>();

    String sql = """
        SELECT DISTINCT cs.Thang
        FROM chisodiennuoc cs
        WHERE cs.Maphong = ?
          AND cs.Nam = ?
        ORDER BY cs.Thang;
    """;

    try (Connection conn = JdbcHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

        ps.setString(1, maPhong);
        ps.setInt(2, nam);
        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(rs.getInt("Thang"));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}

    public int layMaHopDongTheoPhong(String maPhong) {
    // CODE CŨ (Dễ bị lỗi): 
    // String sql = "SELECT MaHopDong FROM hopdong WHERE Maphong = ? AND Trangthai = N'Hiệu lực'";
    
    // CODE MỚI (Sửa lại như sau):
    // Sử dụng LIKE và thêm dấu % ở 2 đầu để bao quát trường hợp thừa khoảng trắng
    String sql = "SELECT MaHopDong FROM hopdong WHERE Maphong = ? AND TrangThaiHopDong LIKE N'%Hiệu lực%'";
    
    try (Connection conn = JdbcHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {
         
        // Nên thêm trim() để xóa khoảng trắng thừa ở mã phòng nếu có
        ps.setString(1, maPhong.trim());
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
            return rs.getInt("MaHopDong");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return -1; // Không tìm thấy hợp đồng hợp lệ
}
    /* =====================================================
       5. LẬP HÓA ĐƠN (KHÔNG INSERT MaHD)
       ===================================================== */
    public boolean LapHoaDon(HoaDonModel hd, int maHopDongReal) {
        String sql = "INSERT INTO hoadon (MaHopDong, Thang, Nam, Ngaylap, Tongtien, Trangthai) VALUES (?, ?, ?, ?, 0, ?)";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, maHopDongReal); // Truyền ID số đã tìm thấy
            pstmt.setInt(2, hd.getThang());
            pstmt.setInt(3, hd.getNam());
            pstmt.setDate(4, new java.sql.Date(hd.getNgayLap().getTime()));
            pstmt.setString(5, hd.getTrangThai());

            return pstmt.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    /* =====================================================
       6. XÓA HÓA ĐƠN
       ===================================================== */
    public boolean delete(String maHD) {
        try (Connection conn = JdbcHelper.getConnection()) {
            String sql = "DELETE FROM hoadon WHERE " + getMaHoaDonColumn(conn) + " = ?";
            try (PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, maHD);
            return pstmt.executeUpdate() > 0;
            }

        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<Object[]> layDuLieuChiTietDayDu(String maHD, String maPhong, int thang, int nam) {
    List<Object[]> list = new ArrayList<>();
    try (Connection conn = JdbcHelper.getConnection()) {
        
        // 1. Lấy tiền phòng cơ bản từ bảng hopdong
        double giaPhong = 0;
        String sqlPhong = "SELECT Giathuethang FROM hopdong WHERE Maphong = ? AND TrangThaiHopDong = N'Hiệu lực'";
        PreparedStatement ps1 = conn.prepareStatement(sqlPhong);
        ps1.setString(1, maPhong);
        ResultSet rs1 = ps1.executeQuery();
        if (rs1.next()) {
            giaPhong = rs1.getDouble("Giathuethang");
            list.add(new Object[]{"Tiền phòng", 1, giaPhong, giaPhong});
        }

        // 2. Lấy chỉ số để tính số lượng Điện và Nước
        int soDienTieuThu = 0; // Đổi tên biến: viết liền, không dấu
        int soNuocTieuThu = 0; 

        String sqlCS = "SELECT Chisodiencu, Chisodienmoi, Chisonuoccu, Chisonuocmoi FROM chisodiennuoc WHERE Maphong = ? AND Thang = ? AND Nam = ?";
        PreparedStatement ps2 = conn.prepareStatement(sqlCS);
        ps2.setString(1, maPhong);
        ps2.setInt(2, thang);
        ps2.setInt(3, nam);
        ResultSet rs2 = ps2.executeQuery();

        if (rs2.next()) {
            // Công thức: Số lượng = Mới - Cũ
            soDienTieuThu = rs2.getInt("Chisodienmoi") - rs2.getInt("Chisodiencu");
            soNuocTieuThu = rs2.getInt("Chisonuocmoi") - rs2.getInt("Chisonuoccu");
        }

        // 3. Quét bảng dichvu để lấy đơn giá và đổ vào bảng chi tiết
        String sqlDV = "SELECT Tendv, Dongia FROM dichvu";
        PreparedStatement psDV = conn.prepareStatement(sqlDV);
        ResultSet rsDV = psDV.executeQuery();

        while (rsDV.next()) {
            String tenDV = rsDV.getString("Tendv");
            double donGia = rsDV.getDouble("Dongia");
            double thanhTien = 0;
            int soLuong = 0;

            if (tenDV.contains("điện") || tenDV.contains("Điện")) {
                soLuong = soDienTieuThu; // Sử dụng tên biến mới đã sửa
                thanhTien = soLuong * donGia;
            } else if (tenDV.contains("nước") || tenDV.contains("Nước")) {
                soLuong = soNuocTieuThu; // Sử dụng tên biến mới đã sửa
                thanhTien = soLuong * donGia;
            } else {
                soLuong = 1;
                thanhTien = donGia;
            }

            if (soLuong > 0) {
                list.add(new Object[]{tenDV, soLuong, donGia, thanhTien});
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
    return list;
}
// Hàm hỗ trợ lấy đơn giá dịch vụ nhanh
private double layDonGiaTuTen(Connection conn, String tenDV) throws Exception {
    String sql = "SELECT Dongia FROM dichvu WHERE Tendv LIKE ?";
    PreparedStatement ps = conn.prepareStatement(sql);
    ps.setString(1, "%" + tenDV + "%");
    ResultSet rs = ps.executeQuery();
    return rs.next() ? rs.getDouble("Dongia") : 0;
}
    public boolean updateTongTien(String maHD, double tongTien) {
        try (Connection con = JdbcHelper.getConnection()) {
            String sql = "UPDATE hoadon SET Tongtien = ? WHERE " + getMaHoaDonColumn(con) + " = ?";
            try (PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setDouble(1, tongTien);
            ps.setString(2, maHD);

            return ps.executeUpdate() > 0;
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private String getMaHoaDonColumn(Connection conn) throws SQLException {
        return JdbcHelper.getExistingColumn(conn, "hoadon", "MaHoaDon", "MaHD");
    }
    
}
