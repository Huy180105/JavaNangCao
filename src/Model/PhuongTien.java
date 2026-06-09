package Model;

import java.util.Date;

public class PhuongTien {
    // Thuộc tính chính của bảng PhuongTien
    private int maPT;
    private int maKhach; // Khóa ngoại
    private String loaiXe;
    private String hieuXe;
    private String bienSo;
    private String mauXe;
    private Date ngayDangKy;
    
    // Thuộc tính mở rộng (Flatten Data) lấy từ bảng KhachThue
    private String tenChuSoHuu;
    private String cccd;
    private String sdt;
    private Date ngaySinhChu;

    // 1. Constructor rỗng
    public PhuongTien() {
    }

    // 2. Constructor đầy đủ (Dùng khi Map dữ liệu từ SQL lên)
    public PhuongTien(int maPT, int maKhach, String loaiXe, String hieuXe, String bienSo, String mauXe, Date ngayDangKy, String tenChuSoHuu, String cccd, String sdt, Date ngaySinhChu) {
        this.maPT = maPT;
        this.maKhach = maKhach;
        this.loaiXe = loaiXe;
        this.hieuXe = hieuXe;
        this.bienSo = bienSo;
        this.mauXe = mauXe;
        this.ngayDangKy = ngayDangKy;
        this.tenChuSoHuu = tenChuSoHuu;
        this.cccd = cccd;
        this.sdt = sdt;
        this.ngaySinhChu = ngaySinhChu;
    }

    // 3. Hàm toString (Quan trọng để debug và hiển thị combobox nếu cần)
    @Override
    public String toString() {
        return hieuXe + " - " + bienSo + " (" + tenChuSoHuu + ")";
    }

    // --- GETTER & SETTER ---
    public int getMaPT() { return maPT; }
    public void setMaPT(int maPT) { this.maPT = maPT; }

    public int getMaKhach() { return maKhach; }
    public void setMaKhach(int maKhach) { this.maKhach = maKhach; }

    public String getLoaiXe() { return loaiXe; }
    public void setLoaiXe(String loaiXe) { this.loaiXe = loaiXe; }

    public String getHieuXe() { return hieuXe; }
    public void setHieuXe(String hieuXe) { this.hieuXe = hieuXe; }

    public String getBienSo() { return bienSo; }
    public void setBienSo(String bienSo) { this.bienSo = bienSo; }

    public String getMauXe() { return mauXe; }
    public void setMauXe(String mauXe) { this.mauXe = mauXe; }

    public Date getNgayDangKy() { return ngayDangKy; }
    public void setNgayDangKy(Date ngayDangKy) { this.ngayDangKy = ngayDangKy; }

    public String getTenChuSoHuu() { return tenChuSoHuu; }
    public void setTenChuSoHuu(String tenChuSoHuu) { this.tenChuSoHuu = tenChuSoHuu; }

    public String getCccd() { return cccd; }
    public void setCccd(String cccd) { this.cccd = cccd; }

    public String getSdt() { return sdt; }
    public void setSdt(String sdt) { this.sdt = sdt; }

    public Date getNgaySinhChu() { return ngaySinhChu; }
    public void setNgaySinhChu(Date ngaySinhChu) { this.ngaySinhChu = ngaySinhChu; }
}