/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Model;

/**
 *
 * @author DELL
 */
public class ChiSoModel {
    private int id;
    private String maPhong;
    private int thang, nam, dienCu, dienMoi, nuocCu, nuocMoi;

    // Constructors, Getter và Setter
    public ChiSoModel() {}
    public ChiSoModel(int id, String maPhong, int thang, int nam, int dienCu, int dienMoi, int nuocCu, int nuocMoi) {
        this.id = id;
        this.maPhong = maPhong;
        this.thang = thang;
        this.nam = nam;
        this.dienCu = dienCu;
        this.dienMoi = dienMoi;
        this.nuocCu = nuocCu;
        this.nuocMoi = nuocMoi;
    }

    public int getId() {
        return id;
    }

    public String getMaPhong() {
        return maPhong;
    }

    public int getThang() {
        return thang;
    }

    public int getNam() {
        return nam;
    }

    public int getDienCu() {
        return dienCu;
    }

    public int getDienMoi() {
        return dienMoi;
    }

    public int getNuocCu() {
        return nuocCu;
    }

    public int getNuocMoi() {
        return nuocMoi;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setMaPhong(String maPhong) {
        this.maPhong = maPhong;
    }

    public void setThang(int thang) {
        this.thang = thang;
    }

    public void setNam(int nam) {
        this.nam = nam;
    }

    public void setDienCu(int dienCu) {
        this.dienCu = dienCu;
    }

    public void setDienMoi(int dienMoi) {
        this.dienMoi = dienMoi;
    }

    public void setNuocCu(int nuocCu) {
        this.nuocCu = nuocCu;
    }

    public void setNuocMoi(int nuocMoi) {
        this.nuocMoi = nuocMoi;
    }
    
}
