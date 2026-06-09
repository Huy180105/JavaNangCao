/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;

import Connection.JdbcHelper;
import Model.ChiSoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DELL
 */
public class ChiSoDao {
    // 1. Lấy danh sách tất cả chỉ số điện nước
    public List<ChiSoModel> getAll() {
        List<ChiSoModel> list = new ArrayList<>();
        String sql = "SELECT * FROM chisodiennuoc";

        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiSoModel cs = new ChiSoModel();
                cs.setId(rs.getInt("ID"));
                cs.setMaPhong(rs.getString("Maphong"));
                cs.setThang(rs.getInt("Thang"));
                cs.setNam(rs.getInt("Nam"));
                cs.setDienCu(rs.getInt("Chisodiencu"));
                cs.setDienMoi(rs.getInt("Chisodienmoi"));
                cs.setNuocCu(rs.getInt("Chisonuoccu"));
                cs.setNuocMoi(rs.getInt("Chisonuocmoi"));
                list.add(cs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Lỗi khi lấy danh sách chỉ số: " + e.getMessage());
        }
        return list;
    }
    // Kiểm tra xem phòng này trong tháng/năm này đã có dữ liệu chưa
    public boolean checkExists(String maPhong, int thang, int nam) {
        String sql = "SELECT COUNT(*) FROM chisodiennuoc WHERE Maphong = ? AND Thang = ? AND Nam = ?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPhong);
            ps.setInt(2, thang);
            ps.setInt(3, nam);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) return rs.getInt(1) > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    // Lấy chỉ số mới của tháng gần nhất để làm chỉ số cũ cho tháng hiện tại
    // Lấy chỉ số mới của tháng trước để làm chỉ số cũ tháng này
    public ChiSoModel layChiSoThangTruoc(String maPhong, int thangHT, int namHT) {
        int thangTruoc = (thangHT == 1) ? 12 : thangHT - 1;
        int namTruoc = (thangHT == 1) ? namHT - 1 : namHT;

        String sql = "SELECT Chisodienmoi, Chisonuocmoi FROM chisodiennuoc WHERE Maphong = ? AND Thang = ? AND Nam = ?";

        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, maPhong);
            ps.setInt(2, thangTruoc);
            ps.setInt(3, namTruoc);

            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                ChiSoModel cs = new ChiSoModel();
                cs.setDienCu(rs.getInt("Chisodienmoi")); 
                cs.setNuocCu(rs.getInt("Chisonuocmoi"));
                return cs;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
    // 2. Thêm mới chỉ số
    public boolean insert(ChiSoModel cs) {
        String sql = "INSERT INTO chisodiennuoc (Maphong, Thang, Nam, Chisodiencu, Chisodienmoi, Chisonuoccu, Chisonuocmoi) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cs.getMaPhong());
            ps.setInt(2, cs.getThang());
            ps.setInt(3, cs.getNam());
            ps.setInt(4, cs.getDienCu());
            ps.setInt(5, cs.getDienMoi());
            ps.setInt(6, cs.getNuocCu());
            ps.setInt(7, cs.getNuocMoi());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 3. Cập nhật chỉ số (Sửa)
    public boolean update(ChiSoModel cs) {
        String sql = "UPDATE chisodiennuoc SET Maphong=?, Thang=?, Nam=?, Chisodiencu=?, Chisodienmoi=?, Chisonuoccu=?, Chisonuocmoi=? WHERE ID=?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, cs.getMaPhong());
            ps.setInt(2, cs.getThang());
            ps.setInt(3, cs.getNam());
            ps.setInt(4, cs.getDienCu());
            ps.setInt(5, cs.getDienMoi());
            ps.setInt(6, cs.getNuocCu());
            ps.setInt(7, cs.getNuocMoi());
            ps.setInt(8, cs.getId());
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 4. Xóa chỉ số
    public boolean delete(int id) {
        String sql = "DELETE FROM chisodiennuoc WHERE ID=?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            return ps.executeUpdate() > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    // 5. Tìm kiếm theo Mã phòng (Tương tự timKiemTheoTen của bạn)
    public List<ChiSoModel> search(String maPhong) {
        List<ChiSoModel> list = new ArrayList<>();
        String sql = "SELECT * FROM chisodiennuoc WHERE Maphong LIKE ?";

        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, "%" + maPhong + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                ChiSoModel cs = new ChiSoModel();
                cs.setId(rs.getInt("ID"));
                cs.setMaPhong(rs.getString("Maphong"));
                cs.setThang(rs.getInt("Thang"));
                cs.setNam(rs.getInt("Nam"));
                cs.setDienCu(rs.getInt("Chisodiencu"));
                cs.setDienMoi(rs.getInt("Chisodienmoi"));
                cs.setNuocCu(rs.getInt("Chisonuoccu"));
                cs.setNuocMoi(rs.getInt("Chisonuocmoi"));
                list.add(cs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }

    // 6. Lấy danh sách Mã phòng từ bảng phongtro (Để đổ vào ComboBox)
    public List<String> getListMaPhong() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT Maphong FROM phongtro";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                list.add(rs.getString("Maphong"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return list;
    }
}
