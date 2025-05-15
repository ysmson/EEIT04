package tw.shawn.dao;

import tw.shawn.model.Transaction;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

public class TransactionDAO {

	private final String JDBC_URL = "jdbc:mysql://localhost:8080/account";
    private final String JDBC_USER = "root";
    private final String JDBC_PASSWORD = "";

    public TransactionDAO() {
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

    public void addTransaction(int userId, Date date, String description, BigDecimal amount, String type) {
        String sql = "INSERT INTO transactions (user_id, date, description, amount, type) VALUES (?, ?, ?, ?, ?)";
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, formattedDate);
            pstmt.setString(3, description);
            pstmt.setBigDecimal(4, amount);
            pstmt.setString(5, type);
            pstmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            // 處理 SQL 異常
        }
    }

    public ArrayList<Transaction> getTransactionsByUserId(int userId) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String sql = "SELECT transaction_id, date, description, amount, type FROM transactions WHERE user_id = ? ORDER BY date DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setUserId(userId);
                transaction.setDate(rs.getDate("date"));
                transaction.setDescription(rs.getString("description"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setType(rs.getString("type"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 處理 SQL 異常
        }
        return transactions;
    }

    public ArrayList<Transaction> getTransactionsByUserIdAndDateRange(int userId, Date startDate, Date endDate) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = sdf.format(startDate);
        String formattedEndDate = sdf.format(endDate);
        String sql = "SELECT transaction_id, date, description, amount, type FROM transactions WHERE user_id = ? AND date BETWEEN ? AND ? ORDER BY date DESC";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, formattedStartDate);
            pstmt.setString(3, formattedEndDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Transaction transaction = new Transaction();
                transaction.setTransactionId(rs.getInt("transaction_id"));
                transaction.setUserId(userId);
                transaction.setDate(rs.getDate("date"));
                transaction.setDescription(rs.getString("description"));
                transaction.setAmount(rs.getBigDecimal("amount"));
                transaction.setType(rs.getString("type"));
                transactions.add(transaction);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 處理 SQL 異常
        }
        return transactions;
    }

    // 你可以在這裡加入其他與交易相關的資料庫操作方法，例如查詢特定月份或年份的交易
}