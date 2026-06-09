package UI;

import Connection.JdbcHelper;
import Controller.LoaiPhongController;
import java.sql.Connection;


import java.sql.SQLException;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;
import model.LoaiPhong;


/**
 *
 * @author PC
 */
public class LoaiPhongPanel extends javax.swing.JPanel implements LoaiPhongView {
    
    private LoaiPhongController controller;
    private Connection connection;
    private int selectedMaloaiphong = -1;
    
    public LoaiPhongPanel() throws SQLException {
        initComponents();
        initController();
        loadData();
    }
    
    private void initController() throws java.sql.SQLException {
        connection = (Connection) JdbcHelper.getConnection();
        controller = new LoaiPhongController((java.sql.Connection) connection, this);
    }
    
    private void loadData() {
        controller.loadDanhSachLoaiPhong();
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        tblLoaiPhong = new javax.swing.JTable();
        jScrollPane2 = new javax.swing.JScrollPane();
        tblLoaiPhong1 = new javax.swing.JTable();
        btnThem = new javax.swing.JButton();
        btnSua = new javax.swing.JButton();
        btnXoa = new javax.swing.JButton();
        btnLamMoi = new javax.swing.JButton();
        txtDonGia = new javax.swing.JTextField();
        txtTenLoai = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txtTim = new javax.swing.JTextField();
        btnTim = new javax.swing.JButton();

        tblLoaiPhong.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã loại phòng", "Tên loại phòng", "Đơn giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblLoaiPhong.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiPhongMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblLoaiPhong);

        tblLoaiPhong1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null},
                {null, null, null},
                {null, null, null},
                {null, null, null}
            },
            new String [] {
                "Mã loại phòng", "Tên loại phòng", "Đơn giá"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.Integer.class, java.lang.String.class, java.lang.Double.class
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }
        });
        tblLoaiPhong1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblLoaiPhong1MouseClicked(evt);
            }
        });
        jScrollPane2.setViewportView(tblLoaiPhong1);

        btnThem.setText("Thêm ");
        btnThem.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnThemActionPerformed(evt);
            }
        });

        btnSua.setText("Sửa ");
        btnSua.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSuaActionPerformed(evt);
            }
        });

        btnXoa.setText("Xóa");
        btnXoa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnXoaActionPerformed(evt);
            }
        });

        btnLamMoi.setText("Làm mới");
        btnLamMoi.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLamMoiActionPerformed(evt);
            }
        });

        txtDonGia.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtDonGiaActionPerformed(evt);
            }
        });

        txtTenLoai.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txtTenLoaiActionPerformed(evt);
            }
        });

        jLabel2.setText("Tên loại phòng");

        jLabel3.setText("Đơn giá ");

        jLabel1.setText("Loại phòng");

        txtTim.setText("Nhập thông tin loại phòng cần tìm");

        btnTim.setText("Tìm kiếm");
        btnTim.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnTimActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(layout.createSequentialGroup()
                    .addContainerGap()
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 201, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txtTim)
                            .addGap(18, 18, 18)
                            .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGap(43, 43, 43))
                        .addComponent(jScrollPane2)
                        .addGroup(layout.createSequentialGroup()
                            .addGap(12, 12, 12)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 101, Short.MAX_VALUE)
                                .addComponent(jLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtDonGia)
                                .addComponent(txtTenLoai)
                                .addGroup(layout.createSequentialGroup()
                                    .addGap(158, 158, 158)
                                    .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGap(0, 173, Short.MAX_VALUE)))))
                    .addContainerGap()))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap(217, Short.MAX_VALUE)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                    .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtTim, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnTim, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(46, 46, 46)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel2)
                        .addComponent(txtTenLoai, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel3)
                        .addComponent(txtDonGia, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGap(18, 18, 18)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(btnXoa, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnSua, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnLamMoi, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btnThem, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addContainerGap()))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void tblLoaiPhongMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiPhongMouseClicked
      // TODO add your handling code here:
    }//GEN-LAST:event_tblLoaiPhongMouseClicked

    private void tblLoaiPhong1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblLoaiPhong1MouseClicked
           displaySelectedRow();
    }
    
    private void displaySelectedRow() {
        int selectedRow = tblLoaiPhong.getSelectedRow();
        if (selectedRow != -1) {
            selectedMaloaiphong = Integer.parseInt(tblLoaiPhong.getValueAt(selectedRow, 0).toString());
            txtTenLoai.setText(tblLoaiPhong.getValueAt(selectedRow, 1).toString());
            txtDonGia.setText(tblLoaiPhong.getValueAt(selectedRow, 2).toString());
        }
    }
    
    private boolean validateInput() {
        if (txtTenLoai.getText().trim().isEmpty()) {
            JOptionPane.showMessageDialog(this, 
                "Vui lòng nhập tên loại phòng!",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtTenLoai.requestFocus();
            return false;
        }
        
        try {
            double dg = Double.parseDouble(txtDonGia.getText().trim());
            if (dg < 0) {
                JOptionPane.showMessageDialog(this, 
                    "Đơn giá phải >= 0!",
                    "Thông báo", JOptionPane.WARNING_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, 
                "Đơn giá phải là số!",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            txtDonGia.requestFocus();
            return false;
        }
        
        return true;        // TODO add your handling code here:
          // TODO add your handling code here:
    }//GEN-LAST:event_tblLoaiPhong1MouseClicked

    private void btnThemActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnThemActionPerformed
        if (validateInput()) {
            LoaiPhong loaiPhong = getLoaiPhongFromInput();
            controller.themLoaiPhong(loaiPhong);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnThemActionPerformed

    private void btnSuaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSuaActionPerformed
        if (selectedMaloaiphong == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn loại phòng cần sửa!",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (validateInput()) {
            LoaiPhong loaiPhong = getLoaiPhongFromInput();
            loaiPhong.setMaloaiphong(selectedMaloaiphong);
            controller.suaLoaiPhong(loaiPhong);
        }     // TODO add your handling code here:
    }//GEN-LAST:event_btnSuaActionPerformed

    private void btnXoaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnXoaActionPerformed
        int selectedRow = tblLoaiPhong.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this,
                "Vui lòng chọn loại phòng cần xóa!",
                "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int maloaiphong = Integer.parseInt(tblLoaiPhong.getValueAt(selectedRow, 0).toString());
        int confirm = JOptionPane.showConfirmDialog(this,
            "Bạn có chắc muốn xóa loại phòng này?",
            "Xác nhận", JOptionPane.YES_NO_OPTION);

        if (confirm == JOptionPane.YES_OPTION) {
            controller.xoaLoaiPhong(maloaiphong);
        }        // TODO add your handling code here:
    }//GEN-LAST:event_btnXoaActionPerformed

    private void btnLamMoiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLamMoiActionPerformed
        controller.lamMoi();
        // TODO add your handling code here:
    }//GEN-LAST:event_btnLamMoiActionPerformed

    private void txtDonGiaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtDonGiaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtDonGiaActionPerformed

    private void txtTenLoaiActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txtTenLoaiActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txtTenLoaiActionPerformed

    private void btnTimActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnTimActionPerformed
        String keyword = txtTim.getText().trim();
        controller.timKiemLoaiPhong(keyword);        // TODO add your handling code here:
    }//GEN-LAST:event_btnTimActionPerformed

  
    private LoaiPhong getLoaiPhongFromInput() {
        String tenloai = txtTenLoai.getText().trim();
        double dongia = Double.parseDouble(txtDonGia.getText().trim());
        
        return new LoaiPhong(tenloai, dongia);
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnLamMoi;
    private javax.swing.JButton btnSua;
    private javax.swing.JButton btnThem;
    private javax.swing.JButton btnTim;
    private javax.swing.JButton btnXoa;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JTable tblLoaiPhong;
    private javax.swing.JTable tblLoaiPhong1;
    private javax.swing.JTextField txtDonGia;
    private javax.swing.JTextField txtTenLoai;
    private javax.swing.JTextField txtTim;
    // End of variables declaration//GEN-END:variables

     @Override
    public void hienThiDanhSach(List<LoaiPhong> list) {
        DefaultTableModel model = (DefaultTableModel) tblLoaiPhong.getModel();
        model.setRowCount(0);
        
        for (LoaiPhong lp : list) {
            Object[] row = {
                lp.getMaloaiphong(),
                lp.getTenloai(),
                lp.getDongia()
            };
            model.addRow(row);
        }
    }
    
    @Override
    public void hienThiThongBao(String message) {
        JOptionPane.showMessageDialog(this, message, 
            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
    }
    
    @Override
    public void hienThiLoi(String message) {
        JOptionPane.showMessageDialog(this, message, 
            "Lỗi", JOptionPane.ERROR_MESSAGE);
    }
    
    @Override
    public void xoaTrangForm() {
        txtTenLoai.setText("");
        txtDonGia.setText("");
        txtTim.setText("");
        tblLoaiPhong.clearSelection();
        selectedMaloaiphong = -1;
    }
}
