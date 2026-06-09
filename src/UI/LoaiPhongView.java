package UI;

import model.LoaiPhong;
import java.util.List;

public interface LoaiPhongView {
    void hienThiDanhSach(List<LoaiPhong> list);
    void hienThiThongBao(String message);
    void hienThiLoi(String message);
    void xoaTrangForm();
}
