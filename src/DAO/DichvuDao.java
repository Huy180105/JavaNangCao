/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Connection.JdbcHelper;
import Model.DichVuModel;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;


/**
 *
 * @author DELL
 */
public class DichvuDao {
    public List<DichVuModel> getListDichVu() {
        List<DichVuModel> list = new ArrayList<>();
        // Truy vấn tất cả các cột từ bảng dichvu
        String sql = "SELECT * FROM dichvu";

        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            
            while (rs.next()) {
                // Khởi tạo model và gán dữ liệu từ database
                DichVuModel dv = new DichVuModel();
                
                dv.setMaDV(rs.getString("MaDV"));
                dv.setTenDV(rs.getString("TenDV"));
                dv.setDonViTinh(rs.getString("DonViTinh"));
                dv.setDonGia(rs.getDouble("DonGia"));
                
                list.add(dv);
            }
        } catch (SQLException e) {
            // In lỗi ra console để dễ kiểm soát hoặc ném ngoại lệ
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy danh sách dịch vụ: " + e.getMessage());
        }
        return list;
    }

    public boolean Them(DichVuModel dv) {
        String sql = "INSERT INTO dichvu (MaDV, TenDV, DonGia, DonViTinh) VALUES (?, ?, ?, ?)";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dv.getMaDV());
            ps.setString(2, dv.getTenDV());
            ps.setDouble(3, dv.getDonGia());
            ps.setString(4, dv.getDonViTinh());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean Sua(DichVuModel dv) {
        String sql = "UPDATE dichvu SET TenDV=?, DonGia=?, DonViTinh=? WHERE MaDV=?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, dv.getTenDV());
            ps.setDouble(2, dv.getDonGia());
            ps.setString(3, dv.getDonViTinh());
            ps.setString(4, dv.getMaDV());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean Xoa(String maDV) {
        String sql = "DELETE FROM dichvu WHERE MaDV=?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maDV);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
    public List<DichVuModel> timKiemTheoTen(String ten) {
    List<DichVuModel> list = new ArrayList<>();
    // Sử dụng LIKE và % để tìm kiếm gần đúng
    String sql = "SELECT * FROM dichvu WHERE TenDV LIKE ?";

    try (Connection con = JdbcHelper.getConnection();
         PreparedStatement ps = con.prepareStatement(sql)) {
        
        // Truyền vào định dạng %tên%
        ps.setString(1, "%" + ten + "%");
        
        ResultSet rs = ps.executeQuery();
        while (rs.next()) {
            DichVuModel dv = new DichVuModel();
            dv.setMaDV(rs.getString("MaDV"));
            dv.setTenDV(rs.getString("TenDV"));
            dv.setDonViTinh(rs.getString("DonViTinh"));
            dv.setDonGia(rs.getDouble("DonGia"));
            list.add(dv);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }
    return list;
}
}
