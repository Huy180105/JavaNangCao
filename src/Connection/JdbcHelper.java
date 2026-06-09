package Connection;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

public class JdbcHelper {

    // ========================================================================
    // 1. CẤU HÌNH KẾT NỐI CHO MYSQL (phpMyAdmin)
    // ========================================================================
    
    // Driver của MySQL
    static String driver = "com.mysql.cj.jdbc.Driver"; 
    
    // Đường dẫn kết nối
    // LƯU Ý: Thay 'QuanLyPhongTro' bằng tên chính xác database bạn đã tạo trong phpMyAdmin
    static String dbName = "baitaplon-javaswing";
    static String serverUrl = "jdbc:mysql://localhost:3306/?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    static String dburl = "jdbc:mysql://localhost:3306/" + dbName + "?useSSL=false&serverTimezone=UTC&allowPublicKeyRetrieval=true";
    
    // Tài khoản mặc định của XAMPP/phpMyAdmin thường là:
    static String user = "root"; 
    static String pass = ""; // Mật khẩu thường để trống
    // ========================================================================

    // Nạp Driver
    static {
        try {
            Class.forName(driver);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("Khong tim thay MySQL JDBC Driver. Hay them file mysql-connector-j vao thu muc lib cua project.", e);
        }
    }

    /**
     * Hàm getConnection
     */
    public static Connection getConnection() throws SQLException {
        try {
            return DriverManager.getConnection(dburl, user, pass);
        } catch (SQLException e) {
            if (e.getErrorCode() == 1049) {
                createDatabaseIfMissing();
                return DriverManager.getConnection(dburl, user, pass);
            }
            throw e;
        }
    }

    private static void createDatabaseIfMissing() throws SQLException {
        try (Connection conn = DriverManager.getConnection(serverUrl, user, pass);
             PreparedStatement stmt = conn.prepareStatement("CREATE DATABASE IF NOT EXISTS `" + dbName + "` CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci")) {
            stmt.executeUpdate();
        }
    }

    public static String getExistingColumn(Connection conn, String tableName, String... columnNames) throws SQLException {
        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " LIMIT 0");
             ResultSet rs = stmt.executeQuery()) {
            ResultSetMetaData metaData = rs.getMetaData();
            for (String candidate : columnNames) {
                for (int i = 1; i <= metaData.getColumnCount(); i++) {
                    String actualName = metaData.getColumnName(i);
                    if (actualName.equalsIgnoreCase(candidate)) {
                        return actualName;
                    }
                }
            }
        }
        throw new SQLException("Khong tim thay cot phu hop trong bang " + tableName);
    }

    public static boolean hasColumn(Connection conn, String tableName, String columnName) throws SQLException {
        DatabaseMetaData metaData = conn.getMetaData();
        try (ResultSet rs = metaData.getColumns(conn.getCatalog(), null, tableName, columnName)) {
            if (rs.next()) {
                return true;
            }
        }

        try (PreparedStatement stmt = conn.prepareStatement("SELECT * FROM " + tableName + " LIMIT 0");
             ResultSet rs = stmt.executeQuery()) {
            ResultSetMetaData rsMetaData = rs.getMetaData();
            for (int i = 1; i <= rsMetaData.getColumnCount(); i++) {
                if (rsMetaData.getColumnName(i).equalsIgnoreCase(columnName)) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Hàm hỗ trợ tạo PreparedStatement
     */
    public static PreparedStatement getStmt(String sql, Object... args) throws SQLException {
        Connection conn = getConnection();
        PreparedStatement stmt;
        if (sql.trim().startsWith("{")) {
            stmt = conn.prepareCall(sql);
        } else {
            stmt = conn.prepareStatement(sql);
        }
        for (int i = 0; i < args.length; i++) {
            stmt.setObject(i + 1, args[i]);
        }
        return stmt;
    }

    /**
     * Hàm executeUpdate (Thêm, Sửa, Xóa)
     */
    public static int executeUpdate(String sql, Object... args) {
        try {
            PreparedStatement stmt = getStmt(sql, args);
            try {
                return stmt.executeUpdate();
            } finally {
                stmt.getConnection().close();
            }
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thực thi Update: " + e);
        }
    }
    
    // Hàm update (tên ngắn) để tương thích code cũ
    public static int update(String sql, Object... args) {
        return executeUpdate(sql, args);
    }

    /**
     * Hàm query (Lấy dữ liệu)
     */
    public static ResultSet query(String sql, Object... args) {
        try {
            PreparedStatement stmt = getStmt(sql, args);
            return stmt.executeQuery();
        } catch (SQLException e) {
            throw new RuntimeException("Lỗi thực thi Query: " + e);
        }
    }
}
