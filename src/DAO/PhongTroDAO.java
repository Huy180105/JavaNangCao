/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

/**
 *
 * @author Quang Huy
 */
import Connection.JdbcHelper;
import Model.PhongTro;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PhongTroDAO {
    public List<PhongTro> selectAll() {
        List<PhongTro> list = new ArrayList<>();
        String sql = "SELECT Maphong, Tenphong, Dientich, Trangthai FROM Phongtro";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement pst = con.prepareStatement(sql);
             ResultSet rs = pst.executeQuery()) {
            while (rs.next()) {
                list.add(new PhongTro(
                    rs.getString("Maphong"),
                    rs.getString("Tenphong"),
                    rs.getInt("Dientich"),
                    rs.getString("Trangthai")
                ));
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
    public void update(PhongTro model) {
    // Câu lệnh SQL UPDATE: Sửa thông tin dựa trên MaPhong (Khóa chính)
    String sql = "UPDATE PhongTro SET TenPhong = ?, DienTich = ?, TrangThai = ? WHERE MaPhong = ?";
    
    JdbcHelper.update(sql, 
        model.getTenPhong(), 
        model.getDienTich(), 
        model.getTrangThai(),
        model.getMaPhong() // Tham số cuối cùng cho mệnh đề WHERE
    );
}

public void delete(String maPhong) {
    // Câu lệnh SQL DELETE: Xóa theo mã
    String sql = "DELETE FROM PhongTro WHERE MaPhong = ?";
    JdbcHelper.update(sql, maPhong);
}

    public void insert(Model.PhongTro model) {
        // Câu lệnh SQL (Bạn kiểm tra lại tên bảng trong SQL Server xem đúng là PhongTro không nhé)
        String sql = "INSERT INTO PhongTro (MaPhong, TenPhong, DienTich, TrangThai) VALUES (?, ?, ?, ?)";
        
        // Gọi JdbcHelper để thực thi lệnh
        // Lưu ý: Thứ tự các dấu ? phải khớp với thứ tự các getter ở dưới
        JdbcHelper.executeUpdate(sql,
        model.getMaPhong(),
        model.getTenPhong(),
        model.getDienTich(),
        model.getTrangThai()
);
    }
}
