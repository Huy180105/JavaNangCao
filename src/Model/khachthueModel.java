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
public class khachthueModel {
    private int maKhach;
    private String hoTen;
    private String cccd;
    private String sdt;
    private String gioiTinh;
    private Date ngaySinh;
    private String queQuan;

    public khachthueModel() {}

    // Constructor để lấy dữ liệu từ DB (có ID)
    public khachthueModel(int maKhach, String hoTen, String cccd, String sdt, String gioiTinh, Date ngaySinh, String queQuan) {
        this.maKhach = maKhach;
        this.hoTen = hoTen;
        this.cccd = cccd;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.ngaySinh = ngaySinh;
        this.queQuan = queQuan;
    }

    public int getMaKhach() {
        return maKhach;
    }

    public String getHoTen() {
        return hoTen;
    }

    public String getCccd() {
        return cccd;
    }

    public String getSdt() {
        return sdt;
    }

    public String getGioiTinh() {
        return gioiTinh;
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public String getQueQuan() {
        return queQuan;
    }

    public void setMaKhach(int maKhach) {
        this.maKhach = maKhach;
    }

    public void setHoTen(String hoTen) {
        this.hoTen = hoTen;
    }

    public void setCccd(String cccd) {
        this.cccd = cccd;
    }

    public void setSdt(String sdt) {
        this.sdt = sdt;
    }

    public void setGioiTinh(String gioiTinh) {
        this.gioiTinh = gioiTinh;
    }

    public void setNgaySinh(Date ngaySinh) {
        this.ngaySinh = ngaySinh;
    }

    public void setQueQuan(String queQuan) {
        this.queQuan = queQuan;
    }
    
}
