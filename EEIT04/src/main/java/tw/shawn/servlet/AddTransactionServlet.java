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
import java.util.Date;

@WebServlet("/addTransaction")
public class AddTransactionServlet extends HttpServlet {
    private TransactionDAO transactionDAO = new TransactionDAO();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("index.html"); // 如果未登入，導回登入頁面
            return;
        }

        String dateStr = request.getParameter("date");
        String description = request.getParameter("description");
        String amountStr = request.getParameter("amount");
        String type = request.getParameter("type");

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = null;
        BigDecimal amount = null;

        try {
            date = sdf.parse(dateStr);
            amount = new BigDecimal(amountStr);
        } catch (ParseException e) {
            e.printStackTrace();
            // 處理日期格式錯誤
            request.setAttribute("errorMessage", "日期格式錯誤，請使用YYYY-MM-DD 格式");
            request.getRequestDispatcher("add_transaction.jsp").forward(request, response);
            return;
        } catch (NumberFormatException e) {
            e.printStackTrace();
            // 處理金額格式錯誤
            request.setAttribute("errorMessage", "金額格式錯誤，請輸入有效的數字");
            request.getRequestDispatcher("add_transaction.jsp").forward(request, response);
            return;
        }

        transactionDAO.addTransaction(userId, date, description, amount, type);
        response.sendRedirect("view_transactions"); // 新增成功後導向查看交易明細頁面
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // 處理 GET 請求，通常是導向新增交易表單頁面
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");
        if (userId == null) {
            response.sendRedirect("index.html");
            return;
        }
        request.getRequestDispatcher("add_transaction.jsp").forward(request, response);
    }
}