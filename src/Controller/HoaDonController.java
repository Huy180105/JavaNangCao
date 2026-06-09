/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.HoaDonDao;
import Model.HoaDonModel;
import UI.HoaDonPanel;
import java.text.DecimalFormat;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author DELL
 */
public class HoaDonController {
    private HoaDonPanel view;
    private HoaDonDao dao;
    private List<Object[]> danhSachPhongCho; // Lưu MaPhong, Thang, Nam tạm thời
    // Tạo formatter dùng chung cho toàn class
    private DecimalFormat formatter = new DecimalFormat("###,###,###");

    public String formatMoney(double money) {
    return formatter.format(money);
}

    public HoaDonController(HoaDonPanel view) {
        this.view = view;
        this.dao = new HoaDonDao();

        doDuLieuVaoComboPhong();
        doDuLieuVaoBang();
        
        
        // Khi chọn Mã phòng -> load Năm
        this.view.getCboMaPhong().addActionListener(e -> doDuLieuNamTheoPhong());

        // Khi chọn Năm -> load Tháng
        this.view.getCboNam().addActionListener(e -> doDuLieuThangTheoNam());
    }

    /* =======================
       1. ĐỔ DỮ LIỆU COMBO PHÒNG
       ======================= */
    public void doDuLieuVaoComboPhong() {
        var cbo = view.getCboMaPhong();

        // Gỡ listener tạm thời
        var listeners = cbo.getActionListeners();
        for (var l : listeners) cbo.removeActionListener(l);

        danhSachPhongCho = dao.layDanhSachChoLapHoaDon();
        cbo.removeAllItems();

        if (danhSachPhongCho != null && !danhSachPhongCho.isEmpty()) {
            Set<String> uniqueRooms = new HashSet<>();
            for (Object[] obj : danhSachPhongCho) {
                uniqueRooms.add(obj[0].toString());
            }
            for (String maPhong : uniqueRooms) {
                cbo.addItem(maPhong);
            }
        }

        // Gắn lại listener
        for (var l : listeners) cbo.addActionListener(l);
    }

    /* =======================
       2. ĐỔ DỮ LIỆU NĂM THEO PHÒNG
       ======================= */
    public void doDuLieuNamTheoPhong() {
        Object phong = view.getCboMaPhong().getSelectedItem();
        if (phong == null) return;

        var cboNam = view.getCboNam();
        var listeners = cboNam.getActionListeners();
        for (var l : listeners) cboNam.removeActionListener(l);

        cboNam.removeAllItems();

        List<Integer> dsNam = dao.layNamChuaLap(phong.toString());
        for (Integer nam : dsNam) {
            cboNam.addItem(nam.toString());
        }

        for (var l : listeners) cboNam.addActionListener(l);

        // Sau khi có năm -> load tháng
        doDuLieuThangTheoNam();
    }

    /* =======================
       3. ĐỔ DỮ LIỆU THÁNG THEO NĂM + PHÒNG
       ======================= */
    public void doDuLieuThangTheoNam() {
        Object phong = view.getCboMaPhong().getSelectedItem();
        Object nam = view.getCboNam().getSelectedItem();

        var cboThang = view.getCboThang();
        cboThang.removeAllItems();

        if (phong == null || nam == null) return;

        try {
            List<Integer> dsThang = dao.layThangChuaLap(
                    phong.toString(),
                    Integer.parseInt(nam.toString())
            );

            for (Integer thang : dsThang) {
                cboThang.addItem(thang.toString());
            }
        } catch (Exception e) {
            System.out.println("Lỗi nạp tháng: " + e.getMessage());
        }
    }

    /* =======================
       4. ĐỔ DỮ LIỆU BẢNG HÓA ĐƠN
       ======================= */
    public void doDuLieuVaoBang() {
        DefaultTableModel model = (DefaultTableModel) view.getTableHoaDon().getModel();
        model.setRowCount(0); 

        List<HoaDonModel> list = dao.getListHoaDon();
        for (HoaDonModel hd : list) {
            // Kiểm tra tiền null hoặc bằng 0
            String hienThiTien;
            if (hd.getTongTien() == null || hd.getTongTien() == 0) {
                hienThiTien = "Chờ tính";
            } else {
                hienThiTien = formatMoney(hd.getTongTien());
            }

            model.addRow(new Object[]{
                hd.getMaHD(),
                hd.getMaPhong(),
                hd.getThang() + "/" + hd.getNam(),
                hd.getNgayLap(),
                hienThiTien, // Giá trị đã được định dạng
                hd.getTrangThai()
            });
        }
    }
    public void hienThiChiTiet() {
        // 1. Kiểm tra xem người dùng có chọn dòng nào không
        int row = view.getTableHoaDon().getSelectedRow();
        if (row < 0) return;

        try {
            // 2. Lấy dữ liệu từ các cột trên dòng được chọn của bảng Hóa Đơn
            // Giả định thứ tự cột: 0: Mã HD, 1: Phòng, 2: Tháng/Năm, 3: Ngày lập, 4: Tổng tiền, 5: Trạng thái
            String maHD = view.getTableHoaDon().getValueAt(row, 0).toString();
            String maPhong = view.getTableHoaDon().getValueAt(row, 1).toString();
            String thangNam = view.getTableHoaDon().getValueAt(row, 2).toString();
            String ngayLap = view.getTableHoaDon().getValueAt(row, 3).toString();
            String tongTien = view.getTableHoaDon().getValueAt(row, 4).toString();
            String trangThai = view.getTableHoaDon().getValueAt(row, 5).toString();

            // 3. Tách Tháng/Năm từ chuỗi "1/2025" để lấy giá trị riêng biệt
            String[] parts = thangNam.split("/");
            int thang = Integer.parseInt(parts[0]);
            int nam = Integer.parseInt(parts[1]);

            // 4. Cập nhật dữ liệu lên các thành phần điều khiển (Header)
            view.getCboMaPhong().setSelectedItem(maPhong);
            view.getCboNam().setSelectedItem(String.valueOf(nam));
            view.getCboThang().setSelectedItem(String.valueOf(thang));
            view.getTxtNgayLap().setText(ngayLap);
            view.getTxtTongTien().setText(tongTien);

            // Cập nhật trạng thái cho ComboBox để khớp với bảng
            view.getCboTrangThai().setSelectedItem(trangThai);

            // 5. Đổ dữ liệu vào bảng CHI TIẾT HÓA ĐƠN (phần bên dưới)
            DefaultTableModel modelChiTiet = (DefaultTableModel) view.getTableChiTiet().getModel();
            modelChiTiet.setRowCount(0); // Xóa dữ liệu cũ trước khi nạp mới

            // Gọi DAO để lấy danh sách chi tiết dịch vụ (Tiền phòng, điện, nước...)
            List<Object[]> data = dao.layDuLieuChiTietDayDu(maHD, maPhong, thang, nam);

            if (data != null) {
                for (Object[] obj : data) {
                    modelChiTiet.addRow(obj);
                }
            }

        } catch (Exception e) {
            System.err.println("Lỗi khi hiển thị chi tiết: " + e.getMessage());
            e.printStackTrace();
        }
    }

    /* =======================
       5. LẬP HÓA ĐƠN
       ======================= */
    public void thucHienLap() {
    String maPhongChon = view.getCboMaPhong().getSelectedItem().toString();
    
    // Bước A: Tìm ID số của hợp đồng từ mã phòng
    int maHDReal = dao.layMaHopDongTheoPhong(maPhongChon); 
    
    if (maHDReal == -1) {
        JOptionPane.showMessageDialog(view, "Phòng này hiện không có hợp đồng hiệu lực!");
        return;
    }

    try {
        HoaDonModel hd = new HoaDonModel();
        hd.setThang(Integer.parseInt(view.getCboThang().getSelectedItem().toString()));
        hd.setNam(Integer.parseInt(view.getCboNam().getSelectedItem().toString()));
        hd.setNgayLap(new java.sql.Date(new Date().getTime()));
        hd.setTrangThai(view.getCboTrangThai().getSelectedItem().toString());

        // Bước B: Gọi hàm lập hóa đơn với ID số thực tế
        if (dao.LapHoaDon(hd, maHDReal)) { 
            JOptionPane.showMessageDialog(view, "Lập hóa đơn thành công!");
            lamMoi();
        } else {
            JOptionPane.showMessageDialog(view, "Lập hóa đơn thất bại!");
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    /* =======================
       6. XÓA HÓA ĐƠN
       ======================= */
    public void xoa() {
        int row = view.getTableHoaDon().getSelectedRow();
        if (row < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn hóa đơn cần xóa!");
            return;
        }

        String maHD = view.getTableHoaDon().getValueAt(row, 0).toString();
        int confirm = JOptionPane.showConfirmDialog(
                view,
                "Bạn có chắc muốn xóa hóa đơn mã: " + maHD + "?",
                "Xác nhận",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(maHD)) {
                JOptionPane.showMessageDialog(view, "Đã xóa hóa đơn!");
                lamMoi();
            }
        }
    }

    /* =======================
       7. LÀM MỚI
       ======================= */
    public void lamMoi() {
        view.getTxtTongTien().setText("");

        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        view.getTxtNgayLap().setText(sdf.format(new Date()));

        doDuLieuVaoComboPhong();
        doDuLieuVaoBang();

        if (view.getCboMaPhong().getItemCount() > 0) {
            view.getCboMaPhong().setSelectedIndex(0);
        } else {
            view.getCboNam().removeAllItems();
            view.getCboThang().removeAllItems();
        }
    }
    public void tinhTongHopTien() {
    // 1. Lấy model của bảng chi tiết hóa đơn từ View
    DefaultTableModel modelChiTiet = (DefaultTableModel) view.getTableChiTiet().getModel();
    
    double tongTien = 0;

    // 2. Duyệt qua từng dòng để cộng dồn cột "Thành tiền" (Cột chỉ số 3)
    for (int i = 0; i < modelChiTiet.getRowCount(); i++) {
        try {
            Object value = modelChiTiet.getValueAt(i, 3); // Cột Thành tiền
            if (value != null) {
                // Xử lý nếu giá trị là chuỗi có thể chứa định dạng, parse về double
                tongTien += Double.parseDouble(value.toString());
            }
        } catch (NumberFormatException e) {
            System.err.println("Lỗi định dạng số tại dòng " + i);
        }
    }

    // 3. Hiển thị tổng tiền lên TextField "Tổng tiền"
    // Bạn có thể dùng formatMoney(tongTien) nếu muốn hiển thị đẹp có dấu chấm
    view.getTxtTongTien().setText(String.valueOf(tongTien));

    // 4. Cập nhật vào Database
    // QUAN TRỌNG: Phải lấy dòng được chọn từ bảng HÓA ĐƠN chính
    int rowHoaDon = view.getTableHoaDon().getSelectedRow();
    
    if (rowHoaDon >= 0) {
        // Lấy mã hóa đơn ở cột 0 của bảng Hóa đơn
        String maHD = view.getTableHoaDon().getValueAt(rowHoaDon, 0).toString();

        // Gọi DAO để update
        if (dao.updateTongTien(maHD, tongTien)) {
            JOptionPane.showMessageDialog(view, "Đã tổng hợp và cập nhật tổng tiền thành công!");
            
            // SỬA TẠI ĐÂY: Gọi đúng tên hàm bạn đã viết ở trên
            doDuLieuVaoBang(); 
            
        } else {
            JOptionPane.showMessageDialog(view, "Cập nhật vào Database thất bại!");
        }
    } else {
        JOptionPane.showMessageDialog(view, "Vui lòng chọn một hóa đơn trong bảng phía trên để cập nhật!");
    }
}
}
