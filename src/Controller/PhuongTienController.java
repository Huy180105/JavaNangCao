package Controller;

import DAO.PhuongTienDAO;
import Model.PhuongTien;
import java.util.List;
import javax.swing.JOptionPane; // Dùng để thông báo lỗi lên màn hình

public class PhuongTienController {
    
    private final PhuongTienDAO ptDAO = new PhuongTienDAO();

    // Lấy danh sách để đổ vào JTable
    public List<PhuongTien> getDanhSachPhuongTien() {
        return ptDAO.getAll();
    }

    // Xử lý thêm mới
    public void themPhuongTien(PhuongTien pt, String cccd) {
        // Có thể validate dữ liệu ở đây
        if (cccd == null || cccd.isEmpty()) {
            JOptionPane.showMessageDialog(null, "Vui lòng nhập CCCD chủ xe!");
            return;
        }
        if (pt.getBienSo() == null || pt.getBienSo().isEmpty()) {
             JOptionPane.showMessageDialog(null, "Biển số không được để trống!");
             return;
        }

        // Gọi DAO để thực hiện
        boolean result = ptDAO.insert(pt, cccd);
        if (result) {
            JOptionPane.showMessageDialog(null, "Thêm phương tiện thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Thêm thất bại! Vui lòng kiểm tra lại CCCD (Khách hàng phải tồn tại trước).");
        }
    }

    // Xử lý cập nhật
    public void capNhatPhuongTien(PhuongTien pt) {
        boolean result = ptDAO.update(pt);
        if (result) {
            JOptionPane.showMessageDialog(null, "Cập nhật thành công!");
        } else {
            JOptionPane.showMessageDialog(null, "Cập nhật thất bại!");
        }
    }

    // Xử lý xóa
    public void xoaPhuongTien(int maPT) {
        int confirm = JOptionPane.showConfirmDialog(null, "Bạn có chắc chắn muốn xóa xe này?", "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            boolean result = ptDAO.delete(maPT);
            if (result) {
                JOptionPane.showMessageDialog(null, "Xóa thành công!");
            } else {
                JOptionPane.showMessageDialog(null, "Xóa thất bại!");
            }
        }
    }

    // Xử lý tìm kiếm
    public List<PhuongTien> timKiemPhuongTien(String keyword) {
        return ptDAO.findByKeyword(keyword);
    }
}