package DAO;

import Connection.JdbcHelper;
import baocaothongke.model.quanlysucoModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class quanlysucoDAO {
    public quanlysucoDAO() {
        ensureSuCoTable();
    }

    private void ensureSuCoTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS suco ("
                + "MaSC VARCHAR(20) PRIMARY KEY, "
                + "Mats INT NOT NULL, "
                + "Maphong VARCHAR(20) NOT NULL, "
                + "NgayBao DATE NOT NULL, "
                + "MoTaSuCo VARCHAR(255), "
                + "TrangThaiXuLy VARCHAR(50) NOT NULL, "
                + "ChiPhiSuaDuKien DOUBLE NOT NULL DEFAULT 0, "
                + "INDEX idx_suco_mats (Mats), "
                + "INDEX idx_suco_maphong (Maphong)"
                + ")";

        try (Connection conn = JdbcHelper.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(createSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<quanlysucoModel> getAll() {
        ensureTable();
        List<quanlysucoModel> list = new ArrayList<>();
        String sql = "SELECT * FROM suco";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToModel(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(quanlysucoModel sc) {
        ensureTable();
        String sql = "INSERT INTO suco (MaSC, Mats, Maphong, NgayBao, MoTaSuCo, TrangThaiXuLy, ChiPhiSuaDuKien) VALUES (?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, sc.getMaSC());
            ps.setInt(2, sc.getMaTS());
            ps.setString(3, sc.getMaPhong());
            ps.setDate(4, sc.getNgayBao());
            ps.setString(5, sc.getMoTa());
            ps.setString(6, sc.getTrangThai());
            ps.setDouble(7, sc.getChiPhi());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean update(quanlysucoModel sc) {
        ensureTable();
        String sql = "UPDATE suco SET Mats=?, Maphong=?, NgayBao=?, MoTaSuCo=?, TrangThaiXuLy=?, ChiPhiSuaDuKien=? WHERE MaSC=?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, sc.getMaTS());
            ps.setString(2, sc.getMaPhong());
            ps.setDate(3, sc.getNgayBao());
            ps.setString(4, sc.getMoTa());
            ps.setString(5, sc.getTrangThai());
            ps.setDouble(6, sc.getChiPhi());
            ps.setString(7, sc.getMaSC());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean delete(String maSC) {
        ensureTable();
        String sql = "DELETE FROM suco WHERE MaSC=?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSC);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public List<quanlysucoModel> timKiem(String keyword) {
        ensureTable();
        List<quanlysucoModel> list = new ArrayList<>();
        StringBuilder sql = new StringBuilder("SELECT * FROM suco WHERE 1=1 ");

        if (keyword != null && !keyword.isEmpty()) {
            sql.append(" AND (MaSC LIKE ? OR Maphong LIKE ? OR MoTaSuCo LIKE ? OR TrangThaiXuLy LIKE ?)");
        }

        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql.toString())) {
            if (keyword != null && !keyword.isEmpty()) {
                String val = "%" + keyword + "%";
                ps.setString(1, val);
                ps.setString(2, val);
                ps.setString(3, val);
                ps.setString(4, val);
            }

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(mapResultSetToModel(rs));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<String> getDSMaPhong() {
        List<String> list = new ArrayList<>();
        String sql = "SELECT Maphong FROM phongtro";
        try (Connection conn = JdbcHelper.getConnection();
             Statement st = conn.createStatement();
             ResultSet rs = st.executeQuery(sql)) {
            while (rs.next()) {
                list.add(rs.getString("Maphong"));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public List<Integer> getDSMaTSTheoPhong(String maPhong) {
        List<Integer> list = new ArrayList<>();
        String sql = "SELECT Mats FROM TaiSan WHERE Maphong = ?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maPhong);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    list.add(rs.getInt("Mats"));
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean checkma(String maSC) {
        ensureTable();
    String sql = "SELECT 1 FROM suco WHERE MaSC = ?";
        try (Connection conn = JdbcHelper.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, maSC);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    private quanlysucoModel mapResultSetToModel(ResultSet rs) throws Exception {
        return new quanlysucoModel(
                rs.getString("MaSC"),
                rs.getInt("Mats"),
                rs.getString("Maphong"),
                rs.getDate("NgayBao"),
                rs.getString("MoTaSuCo"),
                rs.getString("TrangThaiXuLy"),
                rs.getDouble("ChiPhiSuaDuKien")
        );
    }

    private void ensureTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS SuCo (
                MaSC VARCHAR(20) PRIMARY KEY,
                Mats INT NOT NULL,
                Maphong VARCHAR(20) NOT NULL,
                NgayBao DATE,
                MoTaSuCo TEXT,
                TrangThaiXuLy VARCHAR(100),
                ChiPhiSuaDuKien DOUBLE DEFAULT 0
            )
        """;

        try (Connection conn = JdbcHelper.getConnection();
             Statement st = conn.createStatement()) {
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
