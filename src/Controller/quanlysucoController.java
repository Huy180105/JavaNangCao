/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controller;

import DAO.quanlysucoDAO;
import UI.quanlysucoView;
import baocaothongke.model.quanlysucoModel;

import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.DefaultComboBoxModel;
/**
 *
 * @author Dell
 */
public class quanlysucoController {
    private quanlysucoView view;
    private quanlysucoDAO dao;

    public quanlysucoController(quanlysucoView view) { 
        this.view = view;
        this.dao = new quanlysucoDAO();
        khoiTaoDuLieu();
    }

   private void khoiTaoDuLieu() {
        loadComboPhong();
        loadComboTaiSan();
        loadTable();
    }

   
    private void loadComboPhong() {
       view.getMaPhong().setModel(//get lấy dl có sẵn trong view ,gán dl vào combobox
            new DefaultComboBoxModel<>(
                dao.getDSMaPhong().toArray(new String[0])
            )
        );
    }


    public void loadComboTaiSan() {
        view.getMaTS().removeAllItems();

        Object p = view.getMaPhong().getSelectedItem();
        if (p == null) return;
//ts là integer, combobox là string nên trình bày như này 
        for (Integer ts : dao.getDSMaTSTheoPhong(p.toString())) {
            view.getMaTS().addItem(ts.toString());
        }
    }

 
    public void loadTable() {
         view.hienThiBang(dao.getAll());
    }

    
    public void themSuCo() {
        try {
            quanlysucoModel sc = layDuLieuTuForm();// nhập từ bàn phím
            if(dao.checkma(sc.getMaSC())){
                JOptionPane.showMessageDialog(view,"Mã sự cố đã tồn tài , không thể thêm");
                return;
            }
            if (dao.insert(sc)) {// gọi insert trong 
                loadTable();
                view.clearForm();
                JOptionPane.showMessageDialog(view, "Thêm thành công!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, e.getMessage());
        }
    }

   
    public void suaSuCo() {
        try {
            quanlysucoModel sc = layDuLieuTuForm();
            if(!dao.checkma(sc.getMaSC())){
                JOptionPane.showMessageDialog(view,"không tìm thấy mã sự cố để sửa");
            return;
            }
            if (dao.update(sc)) {
                loadTable();
                JOptionPane.showMessageDialog(view, "Cập nhật thành công!");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(view, "sửa lỗi");
        }
    }

    // 
    public void xoaSuCo() {
        String maSC = view.getMaSC().getText().trim();

        if (maSC.isEmpty()) {
            JOptionPane.showMessageDialog(view, "Nhập mã sự cố cần xóa!");
            return;
        }
         if(!dao.checkma(maSC)){
                JOptionPane.showMessageDialog(view,"không tìm thấy mã sự cố để sửa");
            }
        int confirm = JOptionPane.showConfirmDialog(
            view,
            "Bạn có chắc muốn xóa mã " + maSC + " ?",
            "Xác nhận",
            JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            if (dao.delete(maSC)) {
                JOptionPane.showMessageDialog(view, "Đã xóa!");
                loadTable();
                view.clearForm();
            }
        }
    }

    // 
    public void timKiem() {
        view.hienThiBang(dao.timKiem(view.getTimKiem().getText().trim()));
    }

    // 
    public void reset() {
        view.clearForm();
        loadTable();
    }

    // 
    private quanlysucoModel layDuLieuTuForm() throws Exception {

        // Mã sự cố
        String maSC = view.getMaSC().getText().trim();
        if (maSC.isEmpty()) throw new Exception("Mã sự cố không được trống!");

        // Mã phòng
        Object phongObj = view.getMaPhong().getSelectedItem();
        if (phongObj == null) throw new Exception("Chưa chọn phòng!");
        String maPhong = phongObj.toString();

        // Mã tài sản
        Object tsObj = view.getMaTS().getSelectedItem();
        if (tsObj == null) throw new Exception("Chưa chọn tài sản!");
        int maTS = Integer.parseInt(tsObj.toString());

        // Ngày báo
       java.util.Date utilDate = view.getNgayBao(); 

if (utilDate == null) {
    throw new Exception("Ngày báo không được trống!");
}

// Chuyển đổi sang java.sql.Date để lưu vào MySQL
java.sql.Date ngayBao = new java.sql.Date(utilDate.getTime());

        // Mô tả
        String moTa = view.getMoTa().getText().trim();

        // Trạng thái
        String trangThai = view.getTrangThai().getSelectedItem().toString();

        // Chi phí
        String cp = view.getChiPhi().getText().trim();
        if (cp.isEmpty()) throw new Exception("Chi phí không được trống!");
        double chiPhi;
        try {
            chiPhi = Double.parseDouble(cp);
        } catch (NumberFormatException e) {
            throw new Exception("Chi phí phải là số!");
        }

        return new quanlysucoModel(
            maSC, maTS, maPhong, ngayBao, moTa, trangThai, chiPhi
        );
    }
}
