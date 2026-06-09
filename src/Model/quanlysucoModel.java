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
public class quanlysucoModel {
     private String maSC;
    private int maTS;
    private String maPhong;
    private Date ngayBao;
    private String moTa;
    private String trangThai;
    private double chiPhi;

    public quanlysucoModel() {}

    public quanlysucoModel(String maSC, int maTS, String maPhong, Date ngayBao, String moTa, String trangThai, double chiPhi) {
        this.maSC = maSC;
        this.maTS = maTS;
        this.maPhong = maPhong;
        this.ngayBao = ngayBao;
        this.moTa = moTa;
        this.trangThai = trangThai;
        this.chiPhi = chiPhi;
    }

    // Getters và Setters
    public String getMaSC() { return maSC; }
    public void setMaSC(String maSC) { this.maSC = maSC; }
    public int getMaTS() { return maTS; }
    public void setMaTS(int maTS) { this.maTS = maTS; }
    public String getMaPhong() { return maPhong; }
    public void setMaPhong(String maPhong) { this.maPhong = maPhong; }
    public Date getNgayBao() { return ngayBao; }
    public void setNgayBao(Date ngayBao) { this.ngayBao = ngayBao; }
    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }
    public String getTrangThai() { return trangThai; }
    public void setTrangThai(String trangThai) { this.trangThai = trangThai; }
    public double getChiPhi() { return chiPhi; }
    public void setChiPhi(double chiPhi) { this.chiPhi = chiPhi; }

}
