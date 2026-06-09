/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package baocaothongke.model;
import java.util.Date;
/**
 *
 * @author Dell
 */
public class DoanhThuModel{ // Đổi tên từ baocaodoanhthu thành DoanhThuDTO
    
    private int maHD;
    private Date ngayLap;
    private String tenPhong;
    
    private double tongTien;
    private String trangThai;
    private String tenKhach;

    public DoanhThuModel(int maHD, Date ngayLap, String tenPhong,
                  double tongTien, String trangThai, String tenKhach) {
        this.maHD = maHD;
        this.ngayLap = ngayLap;
        this.tenPhong = tenPhong;
        this.tongTien = tongTien;
        this.trangThai = trangThai;
        this.tenKhach = tenKhach;
    }

    public int getMaHD() { return maHD; }
    public Date getNgayLap() { return ngayLap; }
    public String getTenPhong() { return tenPhong; }
    public double getTongTien() { return tongTien; }
    public String getTrangThai() { return trangThai; }
    public String getTenKhach() { return tenKhach; }
}