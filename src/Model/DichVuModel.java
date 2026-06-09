/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author DELL
 */
public class DichVuModel {
    private String maDV;
    private String tenDV;
    private String donViTinh;
    private double donGia;

    public DichVuModel() {}

    public DichVuModel(String maDV, String tenDV, String donViTinh, double donGia) {
        this.maDV = maDV;
        this.tenDV = tenDV;
        this.donViTinh = donViTinh;
        this.donGia = donGia;
    }

    public String getMaDV() {
        return maDV;
    }

    public String getTenDV() {
        return tenDV;
    }

    public String getDonViTinh() {
        return donViTinh;
    }

    public double getDonGia() {
        return donGia;
    }

    public void setMaDV(String maDV) {
        this.maDV = maDV;
    }

    public void setTenDV(String tenDV) {
        this.tenDV = tenDV;
    }

    public void setDonViTinh(String donViTinh) {
        this.donViTinh = donViTinh;
    }

    public void setDonGia(double donGia) {
        this.donGia = donGia;
    }
    
}
