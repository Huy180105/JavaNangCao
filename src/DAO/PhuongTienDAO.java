package DAO;

import Connection.JdbcHelper;
import Model.PhuongTien;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PhuongTienDAO {
    public PhuongTienDAO() {
        ensurePhuongTienTable();
    }

    private void ensurePhuongTienTable() {
        String createSql = "CREATE TABLE IF NOT EXISTS phuongtien ("
                + "MaPT INT AUTO_INCREMENT PRIMARY KEY, "
                + "Makhach INT NOT NULL, "
                + "Loaixe VARCHAR(50) NOT NULL, "
                + "Hieuxe VARCHAR(100), "
                + "Bienso VARCHAR(30) NOT NULL UNIQUE, "
                + "Mauxe VARCHAR(50), "
                + "Ngaydangky DATE NOT NULL, "
                + "INDEX idx_phuongtien_makhach (Makhach)"
                + ")";

        try (Connection con = JdbcHelper.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate(createSql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public List<PhuongTien> getAll() {
        ensureTable();
        List<PhuongTien> list = new ArrayList<>();
        String sql = "SELECT pt.*, k.Hoten, k.CCCD, k.SDT, k.Ngaysinh "
                + "FROM phuongtien pt JOIN khachthue k ON pt.Makhach = k.Makhach";

        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(mapResultSetToModel(rs));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return list;
    }

    public boolean insert(PhuongTien pt, String cccdChuXe) {
        ensureTable();
        String sqlFindKhach = "SELECT Makhach FROM khachthue WHERE CCCD = ?";
        String sqlInsertPT = "INSERT INTO phuongtien(Makhach, Loaixe, Hieuxe, Bienso, Mauxe, Ngaydangky) VALUES(?,?,?,?,?,?)";

        try (Connection con = JdbcHelper.getConnection()) {
            int maKhach = -1;
            try (PreparedStatement psFind = con.prepareStatement(sqlFindKhach)) {
                psFind.setString(1, cccdChuXe);
                try (ResultSet rs = psFind.executeQuery()) {
                    if (rs.next()) {
                        maKhach = rs.getInt("Makhach");
                    }
                }
            }

            if (maKhach == -1) {
                return false;
            }

            try (PreparedStatement ps = con.prepareStatement(sqlInsertPT)) {
                ps.setInt(1, maKhach);
                ps.setString(2, pt.getLoaiXe());
                ps.setString(3, pt.getHieuXe());
                ps.setString(4, pt.getBienSo());
                ps.setString(5, pt.getMauXe());
                ps.setDate(6, new java.sql.Date(pt.getNgayDangKy().getTime()));
                return ps.executeUpdate() > 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean delete(int maPT) {
        ensureTable();
        String sql = "DELETE FROM phuongtien WHERE MaPT = ?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, maPT);
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean update(PhuongTien pt) {
        ensureTable();
        String sql = "UPDATE phuongtien SET Loaixe=?, Hieuxe=?, Bienso=?, Mauxe=?, Ngaydangky=? WHERE MaPT=?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, pt.getLoaiXe());
            ps.setString(2, pt.getHieuXe());
            ps.setString(3, pt.getBienSo());
            ps.setString(4, pt.getMauXe());
            ps.setDate(5, new java.sql.Date(pt.getNgayDangKy().getTime()));
            ps.setInt(6, pt.getMaPT());
            return ps.executeUpdate() > 0;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<PhuongTien> findByKeyword(String keyword) {
        ensureTable();
        List<PhuongTien> list = new ArrayList<>();
        String sql = "SELECT pt.*, k.Hoten, k.CCCD, k.SDT, k.Ngaysinh "
                + "FROM phuongtien pt JOIN khachthue k ON pt.Makhach = k.Makhach "
                + "WHERE k.Hoten LIKE ? OR pt.Bienso LIKE ? OR k.CCCD LIKE ?";
        try (Connection con = JdbcHelper.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            String key = "%" + keyword + "%";
            ps.setString(1, key);
            ps.setString(2, key);
            ps.setString(3, key);

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

    private PhuongTien mapResultSetToModel(ResultSet rs) throws SQLException {
        PhuongTien pt = new PhuongTien();
        pt.setMaPT(rs.getInt("MaPT"));
        pt.setMaKhach(rs.getInt("Makhach"));
        pt.setLoaiXe(rs.getString("Loaixe"));
        pt.setHieuXe(rs.getString("Hieuxe"));
        pt.setBienSo(rs.getString("Bienso"));
        pt.setMauXe(rs.getString("Mauxe"));
        pt.setNgayDangKy(rs.getDate("Ngaydangky"));
        pt.setTenChuSoHuu(rs.getString("Hoten"));
        pt.setCccd(rs.getString("CCCD"));
        pt.setSdt(rs.getString("SDT"));
        pt.setNgaySinhChu(rs.getDate("Ngaysinh"));
        return pt;
    }

    private void ensureTable() {
        String sql = """
            CREATE TABLE IF NOT EXISTS phuongtien (
                MaPT INT AUTO_INCREMENT PRIMARY KEY,
                Makhach INT NOT NULL,
                Loaixe VARCHAR(50) NOT NULL,
                Hieuxe VARCHAR(100),
                Bienso VARCHAR(30) NOT NULL,
                Mauxe VARCHAR(50),
                Ngaydangky DATE,
                UNIQUE KEY uk_phuongtien_bienso (Bienso)
            )
        """;

        try (Connection con = JdbcHelper.getConnection();
             Statement st = con.createStatement()) {
            st.executeUpdate(sql);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
