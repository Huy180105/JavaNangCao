/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package DAO;


import Connection.JdbcHelper;
import baocaothongke.model.thongkephongModel;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.time.temporal.TemporalAdjusters;
import java.time.LocalDate;

 

/**
 *
 * @author Dell
 */
public class thongkephongDAO {
    
    public List<thongkephongModel> getThongKeTheoThang(int thang, int nam) {
        List<thongkephongModel> list = new ArrayList<>();
//
System.out.println("Số dòng lấy được: " + list.size());

    //LocalDate ngayDau = LocalDate.of(nam, thang, 1);
    //LocalDate ngayCuoi = ngayDau.withDayOfMonth(ngayDau.lengthOfMonth());

    String sql = """
        SELECT 
                          p.Maphong, 
                          p.Tenphong, 
                          lp.Tenloai, 
                          p.Dientich, 
                          lp.Dongia, 
                          IFNULL(k.Hoten, '---') AS Hoten, 
                          CASE 
                              WHEN k.Hoten IS NULL THEN 'Trống' 
                              ELSE 'Đang thuê' 
                          END AS TrangThaiPhong,
                          IFNULL(h.TrangThaiHopDong, 'N/A') AS TrangThaiHopDong, 
                          h.Ngayketthuc AS Ngayketthuc
                      FROM phongtro p 
                      LEFT JOIN loaiphong lp ON p.Maloaiphong = lp.Maloaiphong 
                      LEFT JOIN hopdong h ON p.Maphong = h.Maphong 
                          AND h.TrangThaiHopDong = 'Hiệu lực'
                         AND h.Ngaybatdau <= LAST_DAY(STR_TO_DATE(CONCAT(?, '-', ?, '-01'), '%Y-%m-%d'))
                              AND (
                                  h.Ngayketthuc IS NULL
                                  OR h.Ngayketthuc >= STR_TO_DATE(CONCAT(?, '-', ?, '-01'), '%Y-%m-%d')
                              )
                      LEFT JOIN khachthue k ON h.Makhach = k.Makhach;
    """;

    try (Connection conn = JdbcHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql)) {

      
ps.setInt(1, nam);   // YEAR(h.Ngaybatdau) < ?
        ps.setInt(2, thang);   // YEAR(h.Ngaybatdau) = ?
        ps.setInt(3, nam); // MONTH(h.Ngaybatdau) <= ?
        ps.setInt(4, thang);   // YEAR(h.Ngayketthuc) > ?
        //ps.setInt(5, nam);   // YEAR(h.Ngayketthuc) = ?
        //ps.setInt(6, thang); // MONTH(h.Ngayketthuc) >= ?

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            // giống sql 
            
            list.add(new thongkephongModel(
                rs.getString("Maphong"),
                rs.getString("Tenphong"),
                rs.getString("Tenloai"),
                rs.getInt("Dientich"),
                rs.getDouble("Dongia"),
                rs.getString("HoTen"),
                rs.getString("TrangThaiPhong"),
                rs.getString("TrangThaiHopDong"),
                rs.getDate("Ngayketthuc")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}

    
    public List<thongkephongModel> timkiem(int thang, int nam, String maPhong, String tenKhach, String trangThai) {
    List<thongkephongModel> list = new ArrayList<>();

    // 1. Câu lệnh SQL gốc (Sử dụng LEFT JOIN để lấy được cả phòng trống)
    StringBuilder sql = new StringBuilder("""
        SELECT 
            p.Maphong, 
            p.Tenphong, 
            lp.Tenloai, 
            p.Dientich, 
            lp.Dongia, 
            IFNULL(k.Hoten, '---') AS Hoten, 
            CASE 
                WHEN k.Hoten IS NULL THEN 'Trống' 
                ELSE 'Đang thuê' 
            END AS TrangThaiPhong,
            IFNULL(h.TrangThaiHopDong, 'N/A') AS TrangThaiHopDong, 
            h.Ngayketthuc AS Ngayketthuc
        FROM phongtro p 
        LEFT JOIN loaiphong lp ON p.Maloaiphong = lp.Maloaiphong 
        LEFT JOIN hopdong h ON p.Maphong = h.Maphong 
            AND h.TrangThaiHopDong = 'Hiệu lực'
            AND h.Ngaybatdau <= LAST_DAY(STR_TO_DATE(CONCAT(?, '-', ?, '-01'), '%Y-%m-%d'))
            AND (h.Ngayketthuc IS NULL OR h.Ngayketthuc >= STR_TO_DATE(CONCAT(?, '-', ?, '-01'), '%Y-%m-%d'))
        LEFT JOIN khachthue k ON h.Makhach = k.Makhach
        WHERE 1=1
    """);

    // 2. Nối thêm điều kiện động dựa trên đầu vào
    if (maPhong != null && !maPhong.isEmpty()) {
        sql.append(" AND p.Maphong LIKE ? ");
    }
    if (tenKhach != null && !tenKhach.isEmpty()) {
        sql.append(" AND IFNULL(k.Hoten, '') LIKE ? ");
    }
    if (trangThai != null && !trangThai.equals("Tất cả")) {
        // Lọc dựa trên CASE WHEN đã định nghĩa ở trên
        sql.append(" HAVING TrangThaiPhong = ? "); 
    }

    try (Connection conn = JdbcHelper.getConnection();
         PreparedStatement ps = conn.prepareStatement(sql.toString())) {

        int index = 1;
        // Set tham số cho phần JOIN (Tháng/Năm)
        ps.setInt(index++, nam);
        ps.setInt(index++, thang);
        ps.setInt(index++, nam);
        ps.setInt(index++, thang);

        // Set tham số cho phần WHERE/HAVING động
        if (maPhong != null && !maPhong.isEmpty()) {
            ps.setString(index++, "%" + maPhong + "%");
        }
        if (tenKhach != null && !tenKhach.isEmpty()) {
            ps.setString(index++, "%" + tenKhach + "%");
        }
        if (trangThai != null && !trangThai.equals("Tất cả")) {
            ps.setString(index++, trangThai);
        }

        ResultSet rs = ps.executeQuery();

        while (rs.next()) {
            list.add(new thongkephongModel(
                rs.getString("Maphong"),
                rs.getString("Tenphong"),
                rs.getString("Tenloai"),
                rs.getInt("Dientich"),
                rs.getDouble("Dongia"),
                rs.getString("Hoten"),
                rs.getString("TrangThaiPhong"),
                rs.getString("TrangThaiHopDong"),
                rs.getDate("Ngayketthuc")
            ));
        }

    } catch (Exception e) {
        e.printStackTrace();
    }

    return list;
}
}

