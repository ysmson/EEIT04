package tw.shawn.dao;

import tw.shawn.model.Event;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

public class EventDAO {

    private final String JDBC_URL = "jdbc:mysql://localhost:8080/account";
    private final String JDBC_USER = "root";
    private final String JDBC_PASSWORD = "";

    public EventDAO() {
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

    public ArrayList<Event> getEventsByUserIdAndDate(int userId, Date date) {
        ArrayList<Event> events = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = sdf.format(date);
        String sql = "SELECT event_id, title, description FROM events WHERE user_id = ? AND DATE(date) = ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, formattedDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setDate(date); // 設定為查詢的日期
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 處理 SQL 異常
        }
        return events;
    }

    public ArrayList<Event> getEventsByUserIdAndDateRange(int userId, Date startDate, Date endDate) {
        ArrayList<Event> events = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String formattedStartDate = sdf.format(startDate);
        String formattedEndDate = sdf.format(endDate);
        String sql = "SELECT event_id, date, title, description FROM events WHERE user_id = ? AND DATE(date) >= ? AND DATE(date) <= ?";

        try (Connection conn = getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            pstmt.setString(2, formattedStartDate);
            pstmt.setString(3, formattedEndDate);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                Event event = new Event();
                event.setEventId(rs.getInt("event_id"));
                event.setDate(rs.getDate("date"));
                event.setTitle(rs.getString("title"));
                event.setDescription(rs.getString("description"));
                events.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // 處理 SQL 異常
        }
        return events;
    }

    // 你可以在這裡加入其他與行程相關的資料庫操作方法，例如新增、修改、刪除行程
}