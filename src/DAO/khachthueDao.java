/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;
import Connection.JdbcHelper;
import java.sql.*;
import java.util.List;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import quanlykhachthuehopdong.model.khachthueModel;
/**
 *
 * @author Admin
 */
public class khachthueDao {
    // Hàm lấy danh sách tất cả khách thuê để hiện lên Table
    public List<khachthueModel> getAll() {
        List<khachthueModel> list = new ArrayList<>();
        String sql = "SELECT * FROM khachthue";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                khachthueModel kt = new khachthueModel(
                    rs.getInt("Makhach"), rs.getString("Hoten"),
                    rs.getString("CCCD"), rs.getString("SDT"),
                    rs.getString("Gioitinh"), rs.getDate("Ngaysinh"),
                    rs.getString("Quequan")
                );
                list.add(kt);
            }
        } catch (SQLException e) { e.printStackTrace(); }
        return list;
    }
    public String checkTrung(String cccd, String sdt) {
        String sql = "SELECT CCCD, SDT FROM khachthue WHERE CCCD = ? OR SDT = ?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, cccd);
            ps.setString(2, sdt);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    if (rs.getString("CCCD").equals(cccd)) return "Không được trùng CCCD!";
                    if (rs.getString("SDT").equals(sdt)) return "Số điện thoại này có người dùng rồi!";
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null; 
    }
    // Hàm thêm mới (giống logic bài báo cáo mày muốn)
    public boolean insert(khachthueModel kt) {
        String sql = "INSERT INTO khachthue(Hoten, CCCD, SDT, Gioitinh, Ngaysinh, Quequan) VALUES(?,?,?,?,?,?)";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, kt.getHoTen());
            ps.setString(2, kt.getCccd());
            ps.setString(3, kt.getSdt());
            ps.setString(4, kt.getGioiTinh());
            ps.setDate(5, new java.sql.Date(kt.getNgaySinh().getTime())); // Chuyển Date của JDateChooser sang SQL Date
            ps.setString(6, kt.getQueQuan());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) { e.printStackTrace(); return false; }
    }
    
    public boolean update(khachthueModel kt) {
        String sql = "UPDATE khachthue SET Hoten=?, CCCD=?, SDT=?, Gioitinh=?, Ngaysinh=?, Quequan=? WHERE Makhach=?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setString(1, kt.getHoTen());
            ps.setString(2, kt.getCccd());
            ps.setString(3, kt.getSdt());
            ps.setString(4, kt.getGioiTinh());
            
            // Ép kiểu Date từ JDateChooser sang SQL Date
            ps.setDate(5, new java.sql.Date(kt.getNgaySinh().getTime()));
            
            ps.setString(6, kt.getQueQuan());
            ps.setInt(7, kt.getMaKhach()); // Điều kiện WHERE để biết sửa ông nào
            
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Hàm Xóa khách thuê theo ID
    public boolean delete(int maKhach) {
        String sql = "DELETE FROM khachthue WHERE Makhach=?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            
            ps.setInt(1, maKhach);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    // Hàm tìm kiếm khách thuê theo tên hoặc số điện thoại
    public List<khachthueModel> search(String keyword) {
        List<khachthueModel> list = new ArrayList<>();

        String sql = "SELECT * FROM khachthue WHERE Hoten LIKE ? OR SDT LIKE ? OR CCCD LIKE ? OR Quequan LIKE ?";

        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            String find = "%" + keyword + "%";            
            ps.setString(1, find);
            ps.setString(2, find);
            ps.setString(3, find);
            ps.setString(4, find);
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(new khachthueModel(
                    rs.getInt("Makhach"), rs.getString("Hoten"),
                    rs.getString("CCCD"), rs.getString("SDT"),
                    rs.getString("Gioitinh"), rs.getDate("Ngaysinh"),
                    rs.getString("Quequan")
                ));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }
}
