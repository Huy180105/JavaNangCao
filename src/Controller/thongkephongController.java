/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.thongkephongDAO;
import UI.thongkephongView;
import baocaothongke.model.thongkephongModel;
import java.util.List;
/**
 *
 * @author Dell
 */
public class thongkephongController {
 private thongkephongDAO dao ;
private thongkephongView view;
private List<thongkephongModel> dataGoc;
    // Gọi khi bấm nút "Xem thống kê"
public thongkephongController(thongkephongView view){
    this.view=view;
    this.dao=new thongkephongDAO();
    
    xemThongKe();
}

    public void xemThongKe() {

        int thang = view.getcbthang();
        int nam   = view.getcbnam();

      dataGoc = dao.getThongKeTheoThang(thang,nam);

        view.hienThiBang(dataGoc);
        tinhThongKe(dataGoc);
    }

    // TÍNH TOÁN CÁC LABEL – VIẾT CỰC DỄ
    private void tinhThongKe(
                             List<thongkephongModel> list) {

        int tongPhong = list.size();
        int phongTrong = 0;
        int phongDangThue = 0;
        int tongKhach = 0;

        for (thongkephongModel p : list) {

            if ("Trống".equalsIgnoreCase(p.getTrangThaiPhong())) {
                phongTrong++;
            } else {
                phongDangThue++;

                // Có khách thì mới đếm
                if (p.getTenKhach() != null && !p.getTenKhach().equals("---")) {
                    tongKhach++;
                }
            }
        }

        // Đổ dữ liệu lên View
        view.getlbtongphong().setText(String.valueOf(tongPhong));
        view.getlbptr().setText(String.valueOf(phongTrong));
        view.getlbpdt().setText(String.valueOf(phongDangThue));
        view.getlbkhachthue().setText(String.valueOf(tongKhach));
    }
    //hàm tìm kiếm lọc trên dataGoc ko gọi lại nữa
    public void timKiem(){
       // 1. Lấy thông tin từ View (Sử dụng đúng tên hàm bạn đã định nghĩa)
    int thang = view.getcbthang();
    int nam = view.getcbnam();
    String maPhong = view.gettxtmaphongtk();
    String tenKhach = view.gettxtkhachtk();
    String trangThai = view.getcbtrangthaitk();

    // 2. Gọi DAO để lấy dữ liệu đã lọc từ SQL
    // Bạn nên cập nhật hàm timKiem trong DAO để nhận đủ các tham số này
    List<thongkephongModel> ketQua = dao.timkiem(thang, nam, maPhong, tenKhach, trangThai);

    // 3. Hiển thị lên bảng và tính toán lại các ô thống kê (Labels)
    view.hienThiBang(ketQua);
    tinhThongKe(ketQua);
        
    }
    public void reset() {
    // 1. Xóa trắng các JTextField và đưa ComboBox về mặc định
    view.clearForm();
    
    // 2. Nếu đã có dữ liệu gốc (dataGoc) thì hiển thị lại
    if (dataGoc != null) {
        view.hienThiBang(dataGoc);
        tinhThongKe(dataGoc);
    }
}
    
}
