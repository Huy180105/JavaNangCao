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
import quanlykhachthuehopdong.model.hopdongModel;


/**
 *
 * @author Admin
 */
public class hopdongDao {
    public List<hopdongModel> getAll() {
        List<hopdongModel> list = new ArrayList<>();
        String sql = "SELECT * FROM hopdong";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new hopdongModel(
                    rs.getInt("MaHopDong"),              
                    rs.getString("Maphong"),         
                    rs.getInt("Makhach"),            
                    rs.getDouble("Tiencoc"),         
                    rs.getDouble("Giathuethang"),    
                    rs.getDate("Ngaylap"),          
                    rs.getDate("Ngaybatdau"),        
                    rs.getDate("Ngayketthuc"),       
                    rs.getString("TrangThaiHopDong")
                ));
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
// Hàm này để lấy danh sách mã phòng từ bảng PHONG đổ vào ComboBox
    public List<String> getListMaPhong() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT Maphong FROM phongtro"; // Lấy từ bảng phòng nhé
        try {
            Connection conn = JdbcHelper.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Maphong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
        public List<String> getListMaKhach() {
        List<String> list = new ArrayList<>();
        // Chỉ lấy đúng cột Makhach thôi cho nhẹ
        String sql = "SELECT Makhach FROM khachthue ORDER BY Makhach ASC";

        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                // Lấy dữ liệu cột Makhach rồi ép sang String để add vào list
                list.add(rs.getString("Makhach"));
            }
        } catch (SQLException e) { 
            e.printStackTrace(); 
        }
        return list;
    }
    public boolean insert(hopdongModel hd) {
        String sql = "INSERT INTO hopdong(Maphong, Makhach, Ngaylap, Ngaybatdau, Ngayketthuc, Tiencoc, Giathuethang, TrangThaiHopDong) VALUES(?,?,?,?,?,?,?,?)";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, hd.getMaPhong());
            ps.setInt(2, hd.getMaKhach());
            ps.setDate(3, hd.getNgayLap());
            ps.setDate(4, hd.getNgayBatDau());
            ps.setDate(5, hd.getNgayKetThuc());
            ps.setDouble(6, hd.getTienCoc());
            ps.setDouble(7, hd.getGiaThue());
            ps.setString(8, hd.getTrangThai());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { 
            System.err.println("Lỗi SQL: " + e.getMessage()); 
            return false; }
    }
    public boolean update(hopdongModel hd) {
        String sql = "UPDATE hopdong SET Maphong=?, Makhach=?, Ngaylap=?, Ngaybatdau=?, Ngayketthuc=?, Tiencoc=?, Giathuethang=?, TrangThaiHopDong=? WHERE MaHopDong=?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, hd.getMaPhong());
            ps.setInt(2, hd.getMaKhach());           
            ps.setDate(3, hd.getNgayLap());
            ps.setDate(4, hd.getNgayBatDau());
            ps.setDate(5, hd.getNgayKetThuc());
            ps.setDouble(6, hd.getTienCoc());
            ps.setDouble(7, hd.getGiaThue());
            ps.setString(8, hd.getTrangThai());
            ps.setInt(9, hd.getMaHopDong()); // Điều kiện WHERE để biết sửa đúng cái hợp đồng đó

            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
            return false;
        }
    }
    public boolean delete(int maHopDong) {
        String sql = "DELETE FROM hopdong WHERE MaHopDong=?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, maHopDong);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
            return false;
        }
    }
    public List<hopdongModel> search(String keyword) {
        List<hopdongModel> list = new ArrayList<>();
        String sql = "SELECT * FROM hopdong WHERE Maphong LIKE ? "
                   + "OR TrangThaiHopDong LIKE ? "
                   + "OR CAST(Makhach AS CHAR) LIKE ?"; 

        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            String find = "%" + keyword + "%";
            ps.setString(1, find);
            ps.setString(2, find); 
            ps.setString(3, find); 

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new hopdongModel(
                    rs.getInt("MaHopDong"),
                    rs.getString("Maphong"),
                    rs.getInt("Makhach"),
                    rs.getDouble("Tiencoc"),
                    rs.getDouble("Giathuethang"),
                    rs.getDate("Ngaylap"),
                    rs.getDate("Ngaybatdau"),
                    rs.getDate("Ngayketthuc"),
                    rs.getString("TrangThaiHopDong")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Lỗi SQL: " + e.getMessage());
        }
        return list;
    }
    public boolean isPhongDaCoHopDong(String maPhong) {
        // Chỉ kiểm tra những hợp đồng đang có trạng thái là 'Hiệu lực'
        String sql = "SELECT COUNT(*) FROM hopdong WHERE Maphong = ? AND TrangThaiHopDong = N'Hiệu lực'";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, maPhong);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1) > 0; // Nếu đếm được > 0 nghĩa là đã có hợp đồng
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
}
