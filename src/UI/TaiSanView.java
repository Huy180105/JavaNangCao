package view;

import model.TaiSan;
import java.util.List;

public interface TaiSanView {
    void hienThiDanhSach(List<TaiSan> list);
    void hienThiThongBao(String message);
    void hienThiLoi(String message);
    void xoaTrangForm();
}
