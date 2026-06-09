/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package quanlykhachthuehopdong.model;

import java.sql.Date;

/**
 *
 * @author Admin
 */
public class hopdongModel {
    private int maHopDong;
    private String maPhong;
    private int maKhach;
    private double tienCoc;
    private double giaThue;
    private Date ngayLap;
    private Date ngayBatDau;
    private Date ngayKetThuc;
    private String trangThai;

    public hopdongModel() {}

    // Constructor đầy đủ
    public hopdongModel(int maHopDong, String maPhong, int maKhach, double tienCoc, double giaThue, Date ngayLap, Date ngayBatDau, Date ngayKetThuc, String trangThai) {
        this.maHopDong = maHopDong;
        this.maPhong = maPhong;
        this.maKhach = maKhach;
        this.tienCoc = tienCoc;
        this.giaThue = giaThue;
        this.ngayLap = ngayLap;
        this.ngayBatDau = ngayBatDau;
        this.ngayKetThuc = ngayKetThuc;
        this.trangThai = trangThai;
    }

    public int getMaHopDong() {
        return maHopDong;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public int getMaKhach() {
        return maKhach;
    }

    public double getTienCoc() {
        return tienCoc;
    }

    public double getGiaThue() {
        return giaThue;
    }

    public Date getNgayLap() {
        return ngayLap;
    }

    public Date getNgayBatDau() {
        return ngayBatDau;
    }

    public Date getNgayKetThuc() {
        return ngayKetThuc;
    }

    public String getTrangThai() {
        return trangThai;
    }

    public void setMaHopDong(int maHopDong) {
        this.maHopDong = maHopDong;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public void setMaKhach(int maKhach) {
        this.maKhach = maKhach;
    }

    public void setTienCoc(double tienCoc) {
        this.tienCoc = tienCoc;
    }

    public void setGiaThue(double giaThue) {
        this.giaThue = giaThue;
    }

    public void setNgayLap(Date ngayLap) {
        this.ngayLap = ngayLap;
    }

    public void setNgayBatDau(Date ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public void setNgayKetThuc(Date ngayKetThuc) {
        this.ngayKetThuc = ngayKetThuc;
    }

    public void setTrangThai(String trangThai) {
        this.trangThai = trangThai;
    }
    
}