package tw.shawn.servlet;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/registerServlet")  // 注意路徑，瀏覽器及 form 的 action 也要用 /register
public class RegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // 請依你的 MySQL 設定調整連線字串和帳密
    private static final String DB_URL = "jdbc:mysql://localhost:3306/account?useSSL=false&serverTimezone=Asia/Taipei&useUnicode=true&characterEncoding=UTF-8";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // GET 請求轉向註冊頁面
        request.getRequestDispatcher("/register.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setContentType("text/html; charset=UTF-8");

        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String confirmPassword = request.getParameter("confirmPassword");

        // 確認密碼一致
        if (!password.equals(confirmPassword)) {
            request.setAttribute("errorMessage", "密碼與確認密碼不一致！");
            request.getRequestDispatcher("/register.jsp").forward(request, response);
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // 載入 JDBC Driver
        } catch (ClassNotFoundException e) {
            throw new ServletException("無法載入 JDBC Driver", e);
        }

        try (Connection conn = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD)) {
            // 檢查帳號是否存在
            String checkSql = "SELECT COUNT(*) FROM user WHERE username = ?";
            try (PreparedStatement checkStmt = conn.prepareStatement(checkSql)) {
                checkStmt.setString(1, username);
                try (ResultSet rs = checkStmt.executeQuery()) {
                    if (rs.next() && rs.getInt(1) > 0) {
                        request.setAttribute("errorMessage", "帳號已存在！");
                        request.getRequestDispatcher("/register.jsp").forward(request, response);
                        return;
                    }
                }
            }

            // 新增帳號
            String insertSql = "INSERT INTO user (username, password) VALUES (?, ?)";
            try (PreparedStatement insertStmt = conn.prepareStatement(insertSql)) {
                insertStmt.setString(1, username);
                insertStmt.setString(2, password); // 密碼沒加密，正式用請加密
                int result = insertStmt.executeUpdate();
                if (result > 0) {
                    // 註冊成功，跳轉登入頁
                    response.sendRedirect(request.getContextPath() + "/index.jsp");
                } else {
                    request.setAttribute("errorMessage", "註冊失敗，請稍後再試！");
                    request.getRequestDispatcher("/register.jsp").forward(request, response);
                }
            }

        } catch (SQLException e) {
            throw new ServletException("資料庫錯誤：" + e.getMessage(), e);
        }
    }
}
