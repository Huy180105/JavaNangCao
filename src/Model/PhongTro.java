/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author Quang Huy
 */
public class PhongTro {
    private String maPhong;
    private String tenPhong;
    private int dienTich;
    private String trangThai;

    public PhongTro() {}
    public PhongTro(String maPhong, String tenPhong, int dienTich, String trangThai) {
        this.maPhong = maPhong;
        this.tenPhong = tenPhong;
        this.dienTich = dienTich;
        this.trangThai = trangThai;
    }
    // Tạo Getter và Setter (Chuột phải chọn Insert Code > Getter and Setter)
    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }
    public String getTenPhong() { return tenPhong; }
    public void setTenPhong(String tenPhong) { this.tenPhong = tenPhong; }
    public int getDienTich() { return dienTich; }
    public void setDienTich(int dienTich) { this.dienTich = dienTich; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
}
