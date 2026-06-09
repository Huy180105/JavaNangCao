package DAO;

import Connection.JdbcHelper;
import baocaothongke.model.DoanhThuModel;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DoanhThuDAO {

    public List<DoanhThuModel> timKiem(Integer thang, Integer nam) {
        List<DoanhThuModel> list = new ArrayList<>();

        try (Connection con = JdbcHelper.getConnection()) {
            String maHoaDonColumn = getMaHoaDonColumn(con);
            String sql = """
                SELECT
                    hd.%s AS MaHD,
                    hd.Ngaylap,
                    pt.Tenphong,
                    hd.Tongtien,
                    hd.Trangthai,
                    kt.Hoten
                FROM hoadon hd
                JOIN hopdong h ON hd.MaHopDong = h.MaHopDong
                JOIN phongtro pt ON h.Maphong = pt.Maphong
                JOIN khachthue kt ON h.Makhach = kt.Makhach
                WHERE hd.Thang = ? AND hd.Nam = ?
            """.formatted(maHoaDonColumn);

            try (PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, thang);
                ps.setInt(2, nam);
                addRows(list, ps);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return list;
    }

    public List<DoanhThuModel> timKiemNangCao(int thang, int nam, String maHD, String tenPhong, String trangThai) {
        List<DoanhThuModel> list = new ArrayList<>();

        try (Connection con = JdbcHelper.getConnection()) {
            String maHoaDonColumn = getMaHoaDonColumn(con);
            StringBuilder sql = new StringBuilder("""
                SELECT
                    hd.%s AS MaHD,
                    hd.Ngaylap,
                    pt.Tenphong,
                    hd.Tongtien,
                    hd.Trangthai,
                    kt.Hoten
                FROM hoadon hd
                JOIN hopdong h ON hd.MaHopDong = h.MaHopDong
                JOIN phongtro pt ON h.Maphong = pt.Maphong
                JOIN khachthue kt ON h.Makhach = kt.Makhach
                WHERE hd.Thang = ? AND hd.Nam = ?
            """.formatted(maHoaDonColumn));

            if (maHD != null && !maHD.isEmpty()) {
                sql.append(" AND hd.").append(maHoaDonColumn).append(" = ? ");
            }
            if (tenPhong != null && !tenPhong.isEmpty()) {
                sql.append(" AND pt.Tenphong LIKE ? ");
            }
            if (trangThai != null && !trangThai.trim().equals("Tất cả")) {
                sql.append(" AND hd.Trangthai = ? ");
            }

            try (PreparedStatement ps = con.prepareStatement(sql.toString())) {
                int index = 1;
                ps.setInt(index++, thang);
                ps.setInt(index++, nam);

                if (maHD != null && !maHD.isEmpty()) {
                    ps.setInt(index++, Integer.parseInt(maHD));
                }
                if (tenPhong != null && !tenPhong.isEmpty()) {
                    ps.setString(index++, "%" + tenPhong + "%");
                }
                if (trangThai != null && !trangThai.trim().equals("Tất cả")) {
                    ps.setString(index++, trangThai.trim());
                }

                addRows(list, ps);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        return list;
    }

    private void addRows(List<DoanhThuModel> list, PreparedStatement ps) throws SQLException {
        try (ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                list.add(new DoanhThuModel(
                        rs.getInt("MaHD"),
                        rs.getDate("Ngaylap"),
                        rs.getString("Tenphong"),
                        rs.getDouble("Tongtien"),
                        rs.getString("Trangthai"),
                        rs.getString("Hoten")
                ));
            }
        }
    }

    private String getMaHoaDonColumn(Connection conn) throws SQLException {
        return JdbcHelper.getExistingColumn(conn, "hoadon", "MaHoaDon", "MaHD");
    }
}
