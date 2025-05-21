package tw.shawn.servlet;

import tw.shawn.dao.TransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

@WebServlet("/report")
public class ReportServlet extends HttpServlet {
    private TransactionDAO transactionDAO = new TransactionDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("index.html"); // 未登入導回登入頁面
            return;
        }

        String reportType = request.getParameter("type"); // 取得報表類型 (日、月、年)
        String dateStr = request.getParameter("date");   // 取得日期 (可能是日、月、年的部分)

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        Date startDate = null;
        Date endDate = null;

        try {
            if ("day".equals(reportType) && dateStr != null) {
                startDate = sdf.parse(dateStr);
                endDate = startDate;
            } else if ("month".equals(reportType) && dateStr != null) {
                SimpleDateFormat monthSdf = new SimpleDateFormat("yyyy-MM");
                Date monthDate = monthSdf.parse(dateStr);
                cal.setTime(monthDate);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = cal.getTime();
                cal.add(Calendar.MONTH, 1);
                cal.add(Calendar.DAY_OF_MONTH, -1);
                endDate = cal.getTime();
            } else if ("year".equals(reportType) && dateStr != null) {
                SimpleDateFormat yearSdf = new SimpleDateFormat("yyyy");
                Date yearDate = yearSdf.parse(dateStr);
                cal.setTime(yearDate);
                cal.set(Calendar.MONTH, Calendar.JANUARY);
                cal.set(Calendar.DAY_OF_MONTH, 1);
                startDate = cal.getTime();
                cal.set(Calendar.MONTH, Calendar.DECEMBER);
                cal.set(Calendar.DAY_OF_MONTH, 31);
                endDate = cal.getTime();
            } else {
                // 預設顯示所有交易或提供選擇報表類型的介面
                List<tw.shawn.model.Transaction> allTransactions = transactionDAO.getTransactionsByUserId(userId);
                request.setAttribute("transactions", allTransactions);
                request.getRequestDispatcher("report.jsp").forward(request, response);
                return;
            }

            if (startDate != null && endDate != null) {
                List<tw.shawn.model.Transaction> transactions =
                        transactionDAO.getTransactionsByUserIdAndDateRange(userId, startDate, endDate);

                // 計算總收入和總支出
                BigDecimal totalIncome = BigDecimal.ZERO;
                BigDecimal totalExpense = BigDecimal.ZERO;
                for (tw.shawn.model.Transaction transaction : transactions) {
                    if ("income".equals(transaction.getType())) {
                        totalIncome = totalIncome.add(transaction.getAmount());
                    } else if ("expense".equals(transaction.getType())) {
                        totalExpense = totalExpense.add(transaction.getAmount());
                    }
                }

                request.setAttribute("transactions", transactions);
                request.setAttribute("totalIncome", totalIncome);
                request.setAttribute("totalExpense", totalExpense);
                request.setAttribute("netBalance", totalIncome.subtract(totalExpense));
                request.setAttribute("reportType", reportType);
                request.setAttribute("reportDate", dateStr);

                request.getRequestDispatcher("report.jsp").forward(request, response);
            }

        } catch (ParseException e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "日期格式錯誤");
            request.getRequestDispatcher("report.jsp").forward(request, response);
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // 通常報表顯示也使用 GET 請求 
    }
}