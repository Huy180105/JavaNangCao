/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.DichvuDao;
import Model.DichVuModel;
import UI.DonGiaDichVuPanel;



/**
 *
 * @author DELL
 */
public class DichVuController {
    
    private DonGiaDichVuPanel view; 
    private DichvuDao dao;

    public DichVuController(DonGiaDichVuPanel view) {
        this.view = view;
        this.dao = new DichvuDao();
    }

    // Hàm lấy dữ liệu từ các TextField trên View
    private DichVuModel getModelFromView() {
    return new DichVuModel(
        view.getTxtMaDV().getText(),
        view.getTxtTenDV().getText(),
        view.getCboDonvitinh().getSelectedItem().toString(), // Đơn vị tính phải ở đây
        Double.parseDouble(view.getTxtDonGia().getText())    // Đơn giá phải ở cuối
    );
}

    public void add() {
        DichVuModel dv = getModelFromView();
        if (dao.Them(dv)) {
            javax.swing.JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadDataToTable(); // Load lại bảng để thấy dữ liệu mới
        }
    }

    public void edit() {
        DichVuModel dv = getModelFromView();
        if (dao.Sua(dv)) {
            javax.swing.JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            loadDataToTable();
        }
    }

    public void delete() {
        String maDV = view.getTxtMaDV().getText();
        int confirm = javax.swing.JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa?");
        if (confirm == javax.swing.JOptionPane.YES_OPTION) {
            if (dao.Xoa(maDV)) {
                javax.swing.JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadDataToTable();
            }
        }
    }
    public void loadDataToTable() {
    java.util.List<DichVuModel> list = dao.getListDichVu(); 
    javax.swing.table.DefaultTableModel model = (javax.swing.table.DefaultTableModel) view.getTable().getModel();
    model.setRowCount(0); 

    // Khởi tạo định dạng số
    java.text.DecimalFormatSymbols symbols = new java.text.DecimalFormatSymbols();
    symbols.setGroupingSeparator('.'); // Đặt dấu phân cách là dấu chấm
    java.text.DecimalFormat df = new java.text.DecimalFormat("#,###", symbols);

    for (DichVuModel dv : list) {
        // Chuyển đổi số double sang chuỗi định dạng đẹp
        String donGiaDep = df.format(dv.getDonGia());

        model.addRow(new Object[]{
            dv.getMaDV(),
            dv.getTenDV(),
            dv.getDonViTinh(),
            donGiaDep // Sử dụng chuỗi đã định dạng thay vì số gốc
        });
    }
}
    public void lamMoi() {
    // Xóa trắng các ô nhập liệu text
    view.getTxtMaDV().setText("");
    view.getTxtTenDV().setText("");
    view.getTxtDonGia().setText("");

    
    // Đưa ComboBox về lựa chọn đầu tiên
    view.getCboDonvitinh().setSelectedIndex(0);
    
    // Bỏ chọn dòng trên bảng (nếu có)
    view.getTable().clearSelection();
    
    // Tùy chọn: Load lại toàn bộ bảng nếu bạn đang ở chế độ tìm kiếm
    loadDataToTable();
}
    
    public void hienThiChiTiet() {
    // 1. Lấy dòng được chọn
    int rowIndex = view.getTable().getSelectedRow();
    if (rowIndex < 0) return;

    // 2. Lấy dữ liệu từ bảng (Cột 0: Mã, 1: Tên, 2: Đơn vị, 3: Đơn giá)
    String maDV = view.getTable().getValueAt(rowIndex, 0).toString();
    String tenDV = view.getTable().getValueAt(rowIndex, 1).toString();
    String donVi = view.getTable().getValueAt(rowIndex, 2).toString();
    String donGia = view.getTable().getValueAt(rowIndex, 3).toString();

    // 3. Đổ ngược lên các ô nhập liệu
    view.getTxtMaDV().setText(maDV);
    view.getTxtTenDV().setText(tenDV);
    view.getCboDonvitinh().setSelectedItem(donVi);
    
    // Xử lý bỏ dấu chấm ở đơn giá để có thể sửa/lưu mà không bị lỗi số
    view.getTxtDonGia().setText(donGia.replace(".", ""));
}
}

