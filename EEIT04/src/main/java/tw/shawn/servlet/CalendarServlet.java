package tw.shawn.servlet;

import tw.shawn.dao.EventDAO;
import tw.shawn.dao.TransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/calendar")
public class CalendarServlet extends HttpServlet {
    private TransactionDAO transactionDAO = new TransactionDAO();
    private EventDAO eventDAO = new EventDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("index.html"); // 未登入導回登入頁面
            return;
        }

        // 取得目前年月
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH); // Calendar 的月份是從 0 開始

        // 設定查詢的日期範圍 (當月的第一天和最後一天)
        Calendar firstDayCal = Calendar.getInstance();
        firstDayCal.set(year, month, 1);
        Date firstDayOfMonth = firstDayCal.getTime();

        Calendar lastDayCal = Calendar.getInstance();
        lastDayCal.set(year, month, firstDayCal.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayOfMonth = lastDayCal.getTime();

        // 取得當月的使用者交易記錄
        List<tw.shawn.model.Transaction> monthlyTransactions =
                transactionDAO.getTransactionsByUserIdAndDateRange(userId, firstDayOfMonth, lastDayOfMonth);

        // 取得當月的使用者行程
        List<tw.shawn.model.Event> monthlyEvents =
                eventDAO.getEventsByUserIdAndDateRange(userId, firstDayOfMonth, lastDayOfMonth); // 確保 EventDAO 支援日期範圍查詢

        // 計算當月第一天是星期幾 (1 = 星期日, 7 = 星期六)
        int firstDayOfWeek = firstDayCal.get(Calendar.DAY_OF_WEEK);

        // 取得當月總共有幾天
        int daysInMonth = firstDayCal.getActualMaximum(Calendar.DAY_OF_MONTH);

        // 將所有需要的資訊放入 request 屬性中
        request.setAttribute("year", year);
        request.setAttribute("month", month);
        request.setAttribute("monthlyTransactions", monthlyTransactions);
        request.setAttribute("monthlyEvents", monthlyEvents);
        request.setAttribute("firstDayOfWeek", firstDayOfWeek);
        request.setAttribute("daysInMonth", daysInMonth);
        request.setAttribute("calendar", firstDayCal); // 設定 calendar 物件

        request.getRequestDispatcher("calendar.jsp").forward(request, response);
    }

    // 你可能需要處理 POST 請求來新增或修改行程
}