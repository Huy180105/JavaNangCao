package UI;

import model.Phong;
import java.util.List;

public interface PhongView {
    void hienThiDanhSach(List<Phong> list);
    void hienThiThongBao(String message);
    void hienThiLoi(String message);
    void xoaTrangForm();
}