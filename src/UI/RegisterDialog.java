package UI;

import DAO.TaiKhoanDAO;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

public class RegisterDialog extends JDialog {

    private final JTextField txtHoTen = new JTextField(20);
    private final JTextField txtUser = new JTextField(20);
    private final JPasswordField txtPass = new JPasswordField(20);
    private final JPasswordField txtConfirmPass = new JPasswordField(20);

    public RegisterDialog(JFrame parent) {
        super(parent, "Dang ky tai khoan", true);
        initComponents();
    }

    private void initComponents() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        addRow(formPanel, gbc, 0, "Ho ten:", txtHoTen);
        addRow(formPanel, gbc, 1, "Ten dang nhap:", txtUser);
        addRow(formPanel, gbc, 2, "Mat khau:", txtPass);
        addRow(formPanel, gbc, 3, "Xac nhan mat khau:", txtConfirmPass);

        JButton btnRegister = new JButton("Dang ky");
        JButton btnCancel = new JButton("Huy");
        btnRegister.addActionListener(e -> register());
        btnCancel.addActionListener(e -> dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(btnRegister);
        buttonPanel.add(btnCancel);

        add(formPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);
        pack();
        setResizable(false);
        setLocationRelativeTo(getParent());
    }

    private void addRow(JPanel panel, GridBagConstraints gbc, int row, String label, java.awt.Component component) {
        gbc.gridx = 0;
        gbc.gridy = row;
        gbc.weightx = 0;
        panel.add(new JLabel(label), gbc);

        gbc.gridx = 1;
        gbc.weightx = 1;
        panel.add(component, gbc);
    }

    private void register() {
        String hoTen = txtHoTen.getText().trim();
        String user = txtUser.getText().trim();
        String pass = new String(txtPass.getPassword());
        String confirmPass = new String(txtConfirmPass.getPassword());

        if (hoTen.isEmpty() || user.isEmpty() || pass.isEmpty() || confirmPass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Vui long nhap day du thong tin!");
            return;
        }

        if (!pass.equals(confirmPass)) {
            JOptionPane.showMessageDialog(this, "Mat khau xac nhan khong khop!");
            return;
        }

        TaiKhoanDAO dao = new TaiKhoanDAO();
        if (dao.register(hoTen, user, pass)) {
            JOptionPane.showMessageDialog(this, "Dang ky thanh cong! Ban co the dang nhap ngay.");
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Ten dang nhap da ton tai hoac dang ky that bai!");
        }
    }
}
