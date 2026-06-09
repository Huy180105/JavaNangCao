/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.ChiSoDao;
import Model.ChiSoModel;
import UI.ChiSoDienNuocPanel;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;


/**
 *
 * @author DELL
 */
public class ChiSoController {
    private ChiSoDienNuocPanel view;
    private ChiSoDao dao;

    public ChiSoController(ChiSoDienNuocPanel view) {
        this.view = view;
    this.dao = new ChiSoDao();
    
    // Gán controller cho view TRƯỚC khi load dữ liệu
    this.view.setController(this); 

    loadMaPhongToCombo(); // Bây giờ khi gọi hàm này, controller đã khác null
    loadDataToTable();
    }

    // Hàm lấy danh sách mã phòng đổ vào ComboBox
    public void loadMaPhongToCombo() {
        List<String> list = dao.getListMaPhong();
        view.getCboMaphong().removeAllItems();
        for (String ma : list) {
            view.getCboMaphong().addItem(ma);
        }
    }

    // Hàm lấy dữ liệu từ Form (giống getModelFromView của bạn)
    private ChiSoModel getModelFromView() {
        ChiSoModel cs = new ChiSoModel();
        cs.setMaPhong(view.getCboMaphong().getSelectedItem().toString());
        cs.setThang(Integer.parseInt(view.getCboThang().getSelectedItem().toString()));
        cs.setNam(Integer.parseInt(view.getCboNam().getSelectedItem().toString()));
        cs.setDienCu(Integer.parseInt(view.getTxtDiencu().getText()));
        cs.setDienMoi(Integer.parseInt(view.getTxtDienmoi().getText()));
        cs.setNuocCu(Integer.parseInt(view.getTxtNuoccu().getText()));
        cs.setNuocMoi(Integer.parseInt(view.getTxtNuocmoi().getText()));
        return cs;
    }

    

    public void edit() {
        int rowIndex = view.getTblDiennuoc().getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn một dòng để sửa!");
            return;
        }

        try {
            ChiSoModel cs = getModelFromView();
            // Lấy ID từ cột ẩn (cột 0) để thực hiện UPDATE theo khóa chính
            int id = Integer.parseInt(view.getTblDiennuoc().getValueAt(rowIndex, 0).toString());
            cs.setId(id);

            if (dao.update(cs)) {
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
                loadDataToTable();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "Lỗi cập nhật: " + e.getMessage());
        }
    }
    // Hàm tự động cập nhật chỉ số cũ khi chọn phòng/tháng
public void capNhatChiSoCu() {
    // Kiểm tra an toàn tránh lỗi Null khi khởi tạo
    if (view.getCboMaphong().getSelectedItem() == null) return;

    String maPhong = view.getCboMaphong().getSelectedItem().toString();
    int thang = Integer.parseInt(view.getCboThang().getSelectedItem().toString());
    int nam = Integer.parseInt(view.getCboNam().getSelectedItem().toString());

    // Gọi hàm tiếng Việt từ DAO
    ChiSoModel duLieuThangTruoc = dao.layChiSoThangTruoc(maPhong, thang, nam);

    if (duLieuThangTruoc != null) {
        view.getTxtDiencu().setText(String.valueOf(duLieuThangTruoc.getDienCu()));
        view.getTxtNuoccu().setText(String.valueOf(duLieuThangTruoc.getNuocCu()));
        
        // Khóa ô nhập liệu
        view.getTxtDiencu().setEditable(false);
        view.getTxtNuoccu().setEditable(false);
    } else {
        // Nếu không có dữ liệu tháng trước
        view.getTxtDiencu().setText("");
        view.getTxtNuoccu().setText("");
        view.getTxtDiencu().setEditable(true);
        view.getTxtNuoccu().setEditable(true);
    }
}

// Hàm thêm có kiểm tra logic như bạn yêu cầu
public void themMoi() {
    try {
        ChiSoModel cs = getModelFromView();
        
        // 1. Kiểm tra trùng
        if (dao.checkExists(cs.getMaPhong(), cs.getThang(), cs.getNam())) {
            JOptionPane.showMessageDialog(view, "Phòng này tháng này đã có chỉ số rồi!");
            return;
        }

        // 2. Kiểm tra chỉ số mới >= cũ
        if (cs.getDienMoi() < cs.getDienCu() || cs.getNuocMoi() < cs.getNuocCu()) {
            JOptionPane.showMessageDialog(view, "Lỗi: Chỉ số mới phải lớn hơn hoặc bằng chỉ số cũ!");
            return;
        }

        if (dao.insert(cs)) {
            JOptionPane.showMessageDialog(view, "Thêm thành công!");
            loadDataToTable();
            lamMoi();
        }
    } catch (Exception e) {
        JOptionPane.showMessageDialog(view, "Vui lòng kiểm tra lại dữ liệu nhập!");
    }
}
    public void delete() {
        int rowIndex = view.getTblDiennuoc().getSelectedRow();
        if (rowIndex < 0) {
            JOptionPane.showMessageDialog(view, "Vui lòng chọn dòng cần xóa!");
            return;
        }

        int id = Integer.parseInt(view.getTblDiennuoc().getValueAt(rowIndex, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(view, "Bạn có chắc muốn xóa?");
        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(id)) {
                JOptionPane.showMessageDialog(view, "Xóa thành công!");
                loadDataToTable();
                lamMoi();
            }
        }
    }

    public void loadDataToTable() {
        List<ChiSoModel> list = dao.getAll();
        DefaultTableModel model = (DefaultTableModel) view.getTblDiennuoc().getModel();
        model.setRowCount(0);

        for (ChiSoModel cs : list) {
            model.addRow(new Object[]{
                cs.getId(),
                cs.getMaPhong(),
                cs.getThang() + "/" + cs.getNam(), // Hiển thị gộp Tháng/Năm
                cs.getDienCu(),
                cs.getDienMoi(),
                cs.getNuocCu(),
                cs.getNuocMoi()
            });
        }
    }
    
    public void lamMoi() {
        // Xóa trắng các ô văn bản
    view.getTxtDiencu().setText("");
    view.getTxtDienmoi().setText("");
    view.getTxtNuoccu().setText("");
    view.getTxtNuocmoi().setText("");
    

    // Đặt lại các ComboBox về lựa chọn đầu tiên
    view.getCboMaphong().setSelectedIndex(0);
    view.getCboThang().setSelectedIndex(0);
    view.getCboNam().setSelectedIndex(0);

    // Bỏ chọn dòng trong bảng (nếu có)
    view.getTblDiennuoc().clearSelection();
    loadDataToTable();
    }

    

    public void hienThiChiTiet() {
        int rowIndex = view.getTblDiennuoc().getSelectedRow();
        if (rowIndex < 0) return;

        // Lấy dữ liệu từ bảng
        String maPhong = view.getTblDiennuoc().getValueAt(rowIndex, 1).toString();
        String thangNam = view.getTblDiennuoc().getValueAt(rowIndex, 2).toString();
        String dienCu = view.getTblDiennuoc().getValueAt(rowIndex, 3).toString();
        String dienMoi = view.getTblDiennuoc().getValueAt(rowIndex, 4).toString();
        String nuocCu = view.getTblDiennuoc().getValueAt(rowIndex, 5).toString();
        String nuocMoi = view.getTblDiennuoc().getValueAt(rowIndex, 6).toString();

        // Đổ ngược lên View
        view.getCboMaphong().setSelectedItem(maPhong);
        
        // Xử lý chuỗi "Tháng/Năm"
        if (thangNam.contains("/")) {
            String[] parts = thangNam.split("/");
            view.getCboThang().setSelectedItem(parts[0]);
            view.getCboNam().setSelectedItem(parts[1]);
        }

        view.getTxtDiencu().setText(dienCu);
        view.getTxtDienmoi().setText(dienMoi);
        view.getTxtNuoccu().setText(nuocCu);
        view.getTxtNuocmoi().setText(nuocMoi);
    }
}
