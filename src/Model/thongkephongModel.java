/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baocaothongke.model;
import java.sql.Date;
/**
 *
 * @author Dell
 */
public class thongkephongModel {
  private String maPhong;
    private String tenPhong;
    private String tenLoai;
    private int dienTich;
    private double giaThue;
    private String tenKhach;
    private String trangThaiPhong;
    private String trangThaiHopDong;
    private Date ngayKetThuc;

    // Constructor, Getter và Setter
    public thongkephongModel(String maPhong, String tenPhong, String tenLoai,
                             int dienTich, double giaThue, String tenKhach,
                             String trangThaiPhong, String trangThaiHopDong,
                             Date ngayKetThuc) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.tenLoai = tenLoai;
        this.dienTich = dienTich;
        this.giaThue = giaThue;
        this.tenKhach = tenKhach;
        this.trangThaiPhong = trangThaiPhong;
        this.trangThaiHopDong = trangThaiHopDong;
        this.ngayKetThuc = ngayKetThuc;
    }

   // Getter
    public String getMaPhong() {
        return maPhong;
    }

    public String getTenPhong() {
        return tenPhong;
    }

    public String getTenLoai() {
        return tenLoai;
    }

    public int getDienTich() {
        return dienTich;
    }

    public double getGiaThue() {
        return giaThue;
    }

    public String getTenKhach() {
        return tenKhach;
    }

    public String getTrangThaiPhong() {
        return trangThaiPhong;
    }

    public String getTrangThaiHopDong() {
        return trangThaiHopDong;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }
}
