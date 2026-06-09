/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;


import DAO.DoanhThuDAO;
import UI.baocaodoanhthu;
import baocaothongke.model.DoanhThuModel;
import java.util.ArrayList; // Thêm import này
import java.util.List;
/**
 *
 * @author Dell
 */
public class DoanhThuController {
 private baocaodoanhthu view;
    private DoanhThuDAO dao;
    private List<DoanhThuModel> dataGoc; // Lưu trữ dữ liệu sau khi nhấn "Xem"

    public DoanhThuController(baocaodoanhthu view) {
        this.view = view;
        this.dao = new DoanhThuDAO();
        // Load dữ liệu lần đầu
        xemBaoCao();
    }

    // Hàm này gọi Database theo Tháng/Năm
    public void xemBaoCao() {
        int thang = view.getThang();
        int nam = view.getNam();
        
        // Lấy toàn bộ dữ liệu của tháng/năm đó về
        dataGoc = dao.timKiem(thang, nam);
        
        view.hienThiBang(dataGoc);
        tinhTong(dataGoc);
        view.clearForm(); // Xóa các ô nhập tìm kiếm cũ
    }

    // Hàm này lọc trên dataGoc (Không gọi lại DB)
    public void timKiem() {
        // Lấy thông tin từ View
    int thang = view.getThang();
    int nam = view.getNam();
    String maHD = view.getMaHD();
    String phong = view.getMaPhong();
    String trangThai = view.getTrangThai();

    // Gọi DAO để lấy dữ liệu đã lọc từ SQL
    List<DoanhThuModel> ketQua = dao.timKiemNangCao(thang, nam, maHD, phong, trangThai);

    // Hiển thị lên bảng
    view.hienThiBang(ketQua);
    tinhTong(ketQua);
    }

    public void reset() {
        view.clearForm();
        if (dataGoc != null) {
            view.hienThiBang(dataGoc);
            tinhTong(dataGoc);
        }
    }

    private void tinhTong(List<DoanhThuModel> list) {
        double tong = 0, daTT = 0, chuaTT = 0;

        if (list != null) {
            for (DoanhThuModel dt : list) {
                tong += dt.getTongTien();
                // Dùng contains để tránh lỗi dư khoảng trắng hoặc chữ hoa/thường
                if (dt.getTrangThai().toLowerCase().contains("đã thanh toán")) {
                    daTT += dt.getTongTien();
                } else {
                    chuaTT += dt.getTongTien();
                }
            }
        }
        view.getLbTongTien().setText(String.format("%,.0f vnd", tong));
        view.getLbDaThanhToan().setText(String.format("%,.0f vnd", daTT));
        view.getLbChuaThanhToan().setText(String.format("%,.0f vnd", chuaTT));
    }
}