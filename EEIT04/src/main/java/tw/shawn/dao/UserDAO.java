package tw.shawn.dao;

import tw.shawn.model.User;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    private final String JDBC_URL = "jdbc:mysql://localhost:8080/account";
    private final String JDBC_USER = "root";
    private final String JDBC_PASSWORD = "";

    public UserDAO() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            // 處理驅動程式找不到的錯誤
        }
    }

    private Connection getConnection() throws SQLException {
        return java.sql.DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);
    }

    public User getUserByUsername(String username) {
        User user = null;
        String sql = "SELECT user_id, username, password FROM user WHERE username = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, username);
            ResultSet rs = pstmt.executeQuery();

            if (rs.next()) {
                user = new User();
                user.setUserId(rs.getInt("user_id"));
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 處理 SQL 異常
        }
        return user;
    }

    public void addUser(User user) {
        String sql = "INSERT INTO user (username, password) VALUES (?, ?)";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setString(1, user.getUsername());
            pstmt.setString(2, user.getPassword());
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // 處理 SQL 異常
        }
    }

    // 你可以在這裡加入其他與使用者相關的資料庫操作方法，例如修改、刪除使用者
}