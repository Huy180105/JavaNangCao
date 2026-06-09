    /*
     * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
     * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
     */
    package Model;

    import java.sql.Date;

    /**
     *
     * @author DELL
     */
    public class HoaDonModel {
        private String maHD;
        private String maPhong;
        private int thang;
        private int nam;
        private Date ngayLap;
        private Double tongTien; // Để Double để có thể nhận giá trị null như ý định của bạn
        private String trangThai;

        // 1. Hàm tạo không tham số (Default Constructor)
        // Dùng khi bạn muốn khởi tạo đối tượng trống rồi mới dùng các hàm Set
        public HoaDonModel() {
        }

        // 2. Hàm tạo có đầy đủ tham số
        // Dùng khi bạn lấy dữ liệu từ Database lên để hiển thị vào bảng Hóa Đơn
        public HoaDonModel(String maHD, String maPhong, int thang, int nam, Date ngayLap, Double tongTien, String trangThai) {
            this.maHD = maHD;
            this.maPhong = maPhong;
            this.thang = thang;
            this.nam = nam;
            this.ngayLap = ngayLap;
            this.tongTien = tongTien;
            this.trangThai = trangThai;
        }

        // 3. Hàm tạo đặc thù cho việc "Lập hóa đơn" mới (Tổng tiền = null)
        // Chỉ cần truyền các thông tin cơ bản, các trường khác tự định nghĩa
        public HoaDonModel(String maPhong, int thang, int nam, Date ngayLap) {
            this.maPhong = maPhong;
            this.thang = thang;
            this.nam = nam;
            this.ngayLap = ngayLap;
            this.tongTien = null; // Theo đúng logic bạn muốn là bắt buộc để null
            this.trangThai = "Chưa trả"; // Mặc định trạng thái ban đầu
        }

        public String getMaHD() {
            return maHD;
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

        public Date getNgayLap() {
            return ngayLap;
        }

        public Double getTongTien() {
            return tongTien;
        }

        public String getTrangThai() {
            return trangThai;
        }

        public void setMaHD(String maHD) {
            this.maHD = maHD;
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

        public void setNgayLap(Date ngayLap) {
            this.ngayLap = ngayLap;
        }

        public void setTongTien(Double tongTien) {
            this.tongTien = tongTien;
        }

        public void setTrangThai(String trangThai) {
            this.trangThai = trangThai;
        }

    }
