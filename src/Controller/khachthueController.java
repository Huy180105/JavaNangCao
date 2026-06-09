/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.khachthueDao;
import UI.khachthueView;
import java.sql.Date;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

import quanlykhachthuehopdong.model.khachthueModel;

/**
 *
 * @author Admin
 */
public class khachthueController {
    private khachthueView view;
    private khachthueDao dao;

    public khachthueController(khachthueView view) {
        this.view = view;
        this.dao = new khachthueDao();
        // Load dữ liệu lên bảng ngay khi khởi tạo
        loadDataToTable();
    }

    // 1. Hàm lấy dữ liệu từ các TextField/ComboBox trên View ném vào Model
    private khachthueModel getModelFromView() {
        khachthueModel kt = new khachthueModel();
        
        kt.setHoTen(view.getTxtHoTen().getText());
        kt.setCccd(view.getTxtCCCD().getText());
        kt.setSdt(view.getTxtSDT().getText());
        kt.setGioiTinh(view.getCbGioiTinh().getSelectedItem().toString());
        
        // SỬA LỖI ÉP KIỂU DATE: Chuyển từ java.util.Date sang java.sql.Date
        if (view.getDateChooser().getDate() != null) {
            java.util.Date utilDate = view.getDateChooser().getDate();
            kt.setNgaySinh(new java.sql.Date(utilDate.getTime()));
        }
        
        kt.setQueQuan(view.getTxtQueQuan().getText());
        return kt;
    }

    // 2. Đổ dữ liệu từ Database lên bảng JTable
    public void loadDataToTable() {
        // Gọi đúng hàm getAll() trong Dao của mày
        List<khachthueModel> list = dao.getAll(); 
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0); 

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");

        // SỬA LỖI: Dùng khachthueModel chứ không dùng khachthueDao ở đây
        for (khachthueModel kt : list) {
            String ngaySinhStr = (kt.getNgaySinh() != null) ? sdf.format(kt.getNgaySinh()) : "";
            
            model.addRow(new Object[]{
                kt.getMaKhach(),
                kt.getHoTen(),
                kt.getCccd(),
                kt.getSdt(),
                kt.getGioiTinh(),
                ngaySinhStr,
                kt.getQueQuan()
            });
        }
    }

    // 3. Hàm Thêm
    public void add() {
        String thongBaoLoi = dao.checkTrung(view.getTxtCCCD().getText(), view.getTxtSDT().getText());
        if (thongBaoLoi != null) {
        JOptionPane.showMessageDialog(view, thongBaoLoi);
        return; 
    }
        
    java.util.Date utilNgaySinh = view.getDateChooser().getDate();
    java.util.Date ngayHienTai = new java.util.Date();
    // 3. ĐIỀU KIỆN QUAN TRỌNG: Kiểm tra ngày sinh phải bé hơn ngày hiện tại
    if (utilNgaySinh.after(ngayHienTai)) {
        JOptionPane.showMessageDialog(view, "Ngày sinh không được lớn hơn ngày hiện tại!");
        view.getDateChooser().requestFocus();
        return;
    }
        khachthueModel kt = getModelFromView();
        if (dao.insert(kt)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadDataToTable();
            lamMoi();
        } else {
            JOptionPane.showMessageDialog(view, "Thêm thất bại!");
        }
    }

    // 4. Hàm Sửa
    public void edit() {
        int rowIndex = view.getTable().getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn khách thuê cần sửa!");
            return;
        }
        
        java.util.Date utilNgaySinh = view.getDateChooser().getDate();
        java.util.Date ngayHienTai = new java.util.Date();

        if (utilNgaySinh.after(ngayHienTai)) {
        JOptionPane.showMessageDialog(view, "Ngày sinh không được lớn hơn ngày hiện tại!");
        view.getDateChooser().requestFocus();
        return;
    }

        int maKhach = Integer.parseInt(view.getTable().getValueAt(rowIndex, 0).toString());
        khachthueModel kt = getModelFromView();
        kt.setMaKhach(maKhach);

        if (dao.update(kt)) {
            JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadDataToTable();
        }
    }

    // 5. Hàm Xóa
    public void delete() {
        int rowIndex = view.getTable().getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(view, "Chọn dòng cần xóa đã mày!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(view, "Chắc chắn xóa không?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int maKhach = Integer.parseInt(view.getTable().getValueAt(rowIndex, 0).toString());
            if (dao.delete(maKhach)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadDataToTable();
                lamMoi();
            }
        }
    }

    // 6. Hàm Tìm kiếm
    public void timKiem() {
        String keyword = view.getTxtTimKiem().getText().trim();
        List<khachthueModel> list = dao.search(keyword);
        
        DefaultTableModel model = (DefaultTableModel) view.getTable().getModel();
        model.setRowCount(0);

        java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat("dd/MM/yyyy");
        for (khachthueModel kt : list) {
            model.addRow(new Object[]{
                kt.getMaKhach(), kt.getHoTen(), kt.getCccd(),
                kt.getSdt(), kt.getGioiTinh(), 
                (kt.getNgaySinh() != null ? sdf.format(kt.getNgaySinh()) : ""), 
                kt.getQueQuan()
            });
        }
    }

    // 7. Hiển thị thông tin từ bảng lên các ô nhập
    public void hienThiChiTiet() {
        int rowIndex = view.getTable().getSelectedRow();
        if (rowIndex < 0) return;

        view.getTxtHoTen().setText(view.getTable().getValueAt(rowIndex, 1).toString());
        view.getTxtCCCD().setText(view.getTable().getValueAt(rowIndex, 2).toString());
        view.getTxtSDT().setText(view.getTable().getValueAt(rowIndex, 3).toString());
        view.getCbGioiTinh().setSelectedItem(view.getTable().getValueAt(rowIndex, 4).toString());
        
        try {
            String sDate = view.getTable().getValueAt(rowIndex, 5).toString();
            java.util.Date date = new java.text.SimpleDateFormat("dd/MM/yyyy").parse(sDate);
            view.getDateChooser().setDate(date);
        } catch (Exception e) {
            view.getDateChooser().setDate(null);
        }
        
        view.getTxtQueQuan().setText(view.getTable().getValueAt(rowIndex, 6).toString());
    }

    public void lamMoi() {
        view.getTxtHoTen().setText("");
        view.getTxtCCCD().setText("");
        view.getTxtSDT().setText("");
        view.getTxtQueQuan().setText("");
        view.getDateChooser().setDate(null);
        view.getTxtTimKiem().setText("");
        view.getTable().clearSelection();
        loadDataToTable();
    }
}
