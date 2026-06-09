/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Connection.JdbcHelper;
import java.sql.*;

public class TaiKhoanDAO {
    public TaiKhoanDAO() {
        ensureTaiKhoanTable();
        seedDefaultAccount();
    }

    public boolean checkLogin(String user, String pass) {
        String sql = "SELECT * FROM TaiKhoan WHERE Tendangnhap = ? AND Matkhau = ?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, user);
            pst.setString(2, pass);
            ResultSet rs = pst.executeQuery();
            return rs.next(); 
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean usernameExists(String user) {
        String sql = "SELECT 1 FROM TaiKhoan WHERE Tendangnhap = ?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, user);
            try (ResultSet rs = pst.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean register(String hoTen, String user, String pass) {
        if (usernameExists(user)) {
            return false;
        }

        String sql = "INSERT INTO TaiKhoan (Hoten, Tendangnhap, Matkhau) VALUES (?, ?, ?)";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement pst = con.prepareStatement(sql)) {
            pst.setString(1, hoTen);
            pst.setString(2, user);
            pst.setString(3, pass);
            return pst.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private void ensureTaiKhoanTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS TaiKhoan ("
                + "MaTK INT AUTO_INCREMENT PRIMARY KEY, "
                + "Hoten VARCHAR(100) NOT NULL DEFAULT '', "
                + "Tendangnhap VARCHAR(50) NOT NULL UNIQUE, "
                + "Matkhau VARCHAR(100) NOT NULL"
                + ")";

        try (Connection con = JdbcHelper.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate(createSql);
            addHotenColumnIfMissing(con);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void addHotenColumnIfMissing(Connection con) throws SQLException {
        DatabaseMetaData metaData = con.getMetaData();
        try (ResultSet rs = metaData.getColumns(con.getCatalog(), null, "TaiKhoan", "Hoten")) {
            if (!rs.next()) {
                try (Statement st = con.createStatement()) {
                    st.executeUpdate("ALTER TABLE TaiKhoan ADD COLUMN Hoten VARCHAR(100) NOT NULL DEFAULT ''");
                }
            }
        }
    }

    private void seedDefaultAccount() {
        if (!usernameExists("admin")) {
            register("Quan tri vien", "admin", "admin123");
        }
    }
}
