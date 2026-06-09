/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.hopdongDao;
import UI.hopdongView;
import java.util.List;

import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import quanlykhachthuehopdong.model.hopdongModel;


/**
 *
 * @author Admin
 */
public class hopdongController {
    private hopdongView view;
    private hopdongDao dao;
    private java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

    public hopdongController(hopdongView view) {
        this.view = view;
        this.dao = new hopdongDao();
        loadDataToTable();
        loadComboBoxMaPhong();
        loadComboBoxMaKhach();
    }
    private hopdongModel getModelFromView() {
        hopdongModel hd = new hopdongModel();

        // 1. Lấy dữ liệu từ ComboBox (Mã phòng và Mã khách)
        hd.setMaPhong(view.getCbomaphong().getSelectedItem().toString());
        hd.setMaKhach(Integer.parseInt(view.getCbomakhach().getSelectedItem().toString()));

        // 2. Lấy dữ liệu từ JTextField (Tiền cọc và Giá thuê)
        // Cần ép kiểu từ String sang Double
        hd.setTienCoc(Double.parseDouble(view.getTxttiencoc().getText()));
        hd.setGiaThue(Double.parseDouble(view.getTxtgiathuethang().getText()));

        // 3. Lấy dữ liệu từ JDateChooser (Ngày lập, Bắt đầu, Kết thúc)
        // Chuyển từ java.util.Date sang java.sql.Date để lưu vào DB
        if (view.getDtngaylap().getDate() != null) {
            hd.setNgayLap(new java.sql.Date(view.getDtngaylap().getDate().getTime()));
        }
        if (view.getDtngaybatdau().getDate() != null) {
            hd.setNgayBatDau(new java.sql.Date(view.getDtngaybatdau().getDate().getTime()));
        }
        if (view.getDtngayketthuc().getDate() != null) {
            hd.setNgayKetThuc(new java.sql.Date(view.getDtngayketthuc().getDate().getTime()));
        }

        // 4. Lấy dữ liệu từ ComboBox Trạng thái
        hd.setTrangThai(view.getCbotrangthai().getSelectedItem().toString());

        return hd;
    }
    public void loadComboBoxMaPhong() {
    List<String> list = dao.getListMaPhong();    
    view.getCbomaphong().removeAllItems(); 
    for (String ma : list) {
        view.getCbomaphong().addItem(ma);
        }
    }
    public void loadComboBoxMaKhach() {
    List<String> listMK = dao.getListMaKhach(); 
    view.getCbomakhach().removeAllItems(); 
    for (String ma : listMK) {
        view.getCbomakhach().addItem(ma);
        }
    }
    public void loadDataToTable() {
        List<hopdongModel> list = dao.getAll();
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        
        for (hopdongModel hd : list) {
            model.addRow(new Object[]{
                hd.getMaHopDong(),
                hd.getMaPhong(), 
                hd.getMaKhach(),
               (hd.getNgayLap() != null ? sdf.format(hd.getNgayLap()) : ""),
               (hd.getNgayBatDau() != null ? sdf.format(hd.getNgayBatDau()) : ""),
               (hd.getNgayKetThuc() != null ? sdf.format(hd.getNgayKetThuc()) : ""), 
                hd.getTienCoc(), 
                hd.getGiaThue(), 
                hd.getTrangThai()
            });
        }
    }
    public void add() {
        // 1. Kiểm tra định dạng số cho Tiền cọc
        try {
            Double.parseDouble(view.getTxttiencoc().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Tiền cọc bắt buộc phải là số!");
            view.getTxttiencoc().requestFocus(); 
            return; 
        }
        
        // 2. Kiểm tra định dạng số cho Giá thuê
        try {
            Double.parseDouble(view.getTxtgiathuethang().getText());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(view, "Giá thuê tháng bắt buộc phải là số!");
            view.getTxtgiathuethang().requestFocus();
            return; 
        }
        
        // 3. Kiểm tra logic ngày tháng
        java.util.Date ngayBatDau = view.getDtngaybatdau().getDate();
        java.util.Date ngayKetThuc = view.getDtngayketthuc().getDate();
        if (ngayBatDau == null || ngayKetThuc == null) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn đầy đủ ngày bắt đầu và kết thúc!");
            return;
        }
        if (ngayKetThuc.before(ngayBatDau)) {
            JOptionPane.showMessageDialog(view, "Ngày kết thúc phải sau ngày bắt đầu!");
            view.getDtngayketthuc().requestFocus(); 
            return; 
        }

        // --- ĐOẠN QUAN TRỌNG NHẤT MÀY CẦN THÊM Ở ĐÂY ---
        // 4. Kiểm tra xem phòng này đã có hợp đồng "Hiệu lực" chưa
        String maPhongChon = view.getCbomaphong().getSelectedItem().toString();
        if (dao.isPhongDaCoHopDong(maPhongChon)) {
            JOptionPane.showMessageDialog(view, "Phòng " + maPhongChon + " hiện đã có hợp đồng còn Hiệu lực. Không thể thêm mới!", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return; // Dừng lại luôn, không cho chạy xuống lệnh insert bên dưới
        }
        // ----------------------------------------------

        // 5. Nếu vượt qua hết các kiểm tra thì mới thực hiện thêm
        hopdongModel hd = getModelFromView();
        if (hd != null && dao.insert(hd)) {
            JOptionPane.showMessageDialog(view, "Thêm hợp đồng thành công!");
            loadDataToTable();
            lamMoi();
        } else if (hd != null) {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!");
        }
    }
    public void edit() {
        int rowIndex = view.getTable().getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn hợp đồng cần sửa!");
            return;
        }

        int maHD = Integer.parseInt(view.getTable().getValueAt(rowIndex, 0).toString());
        hopdongModel hd = getModelFromView();
        if (hd != null) {
            hd.setMaHopDong(maHD);
            if (dao.update(hd)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            }
        }
    }
    public void delete() {
        int rowIndex = view.getTable().getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(view, "Chọn dòng cần xóa đã mày!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Chắc chắn xóa hợp đồng này không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int maHD = Integer.parseInt(view.getTable().getValueAt(rowIndex, 0).toString());
            if (dao.delete(maHD)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadDataToTable();
                lamMoi();
            }
        }
    }
    public void timKiem() {
        String keyword = view.getTxtTimKiem().getText().trim();
        List<hopdongModel> list = dao.search(keyword);
        
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);
        
        for (hopdongModel hd : list) {
            model.addRow(new Object[]{
                hd.getMaHopDong(), hd.getMaPhong(), hd.getMaKhach(),
                hd.getTienCoc(), hd.getGiaThue(),
                (hd.getNgayLap() != null ? sdf.format(hd.getNgayLap()) : ""),
                (hd.getNgayBatDau() != null ? sdf.format(hd.getNgayBatDau()) : ""),
                (hd.getNgayKetThuc() != null ? sdf.format(hd.getNgayKetThuc()) : ""),
                hd.getTrangThai()
            });
        }
    }
    public void hienThiChiTiet() {
        int rowIndex = view.getTable().getSelectedRow();
        if (rowIndex < 0) return;

        // 1. Đưa Mã phòng (cột 1) và Mã khách (cột 2) lên trên cùng
        view.getCbomaphong().setSelectedItem(view.getTable().getValueAt(rowIndex, 1).toString());
        view.getCbomakhach().setSelectedItem(view.getTable().getValueAt(rowIndex, 2).toString());

        // 2. Đến lượt 3 ô Ngày tháng (cột 3, 4, 5) nằm trong try-catch
        try {
            view.getDtngaylap().setDate(sdf.parse(view.getTable().getValueAt(rowIndex, 3).toString()));
            view.getDtngaybatdau().setDate(sdf.parse(view.getTable().getValueAt(rowIndex, 4).toString()));
            view.getDtngayketthuc().setDate(sdf.parse(view.getTable().getValueAt(rowIndex, 5).toString()));
        } catch (Exception e) {
            view.getDtngaylap().setDate(null);
            view.getDtngaybatdau().setDate(null);
            view.getDtngayketthuc().setDate(null);
        }

        // 3. Tiền cọc (cột 6) và Giá thuê (cột 7)
        view.getTxttiencoc().setText(view.getTable().getValueAt(rowIndex, 6).toString());
        view.getTxtgiathuethang().setText(view.getTable().getValueAt(rowIndex, 7).toString());

        // 4. Trạng thái (cột 8)
        view.getCbotrangthai().setSelectedItem(view.getTable().getValueAt(rowIndex, 8).toString());
    }
    public void lamMoi() {
        view.getCbomaphong().setSelectedIndex(0);
        view.getCbomakhach().setSelectedIndex(0);
        view.getCbotrangthai().setSelectedIndex(0);
        view.getTxttiencoc().setText("");
        view.getTxtgiathuethang().setText("");
        view.getDtngaylap().setDate(null);
        view.getDtngaybatdau().setDate(null);
        view.getDtngayketthuc().setDate(null);
        view.getTable().clearSelection();
        view.getTxtTimKiem().setText("");
        loadDataToTable();
    }
}