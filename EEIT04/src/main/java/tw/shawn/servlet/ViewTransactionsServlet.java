package tw.shawn.servlet;

import tw.shawn.dao.TransactionDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/viewTransactions")
public class ViewTransactionsServlet extends HttpServlet {
    private TransactionDAO transactionDAO = new TransactionDAO();

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Integer userId = (Integer) session.getAttribute("userId");

        if (userId == null) {
            response.sendRedirect("index.html"); // 未登入導回登入頁面
            return;
        }

        List<tw.shawn.model.Transaction> transactions = transactionDAO.getTransactionsByUserId(userId);

        request.setAttribute("transactions", transactions);
        request.getRequestDispatcher("view_transactions.jsp").forward(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response); // 通常查看交易明細使用 GET 請求 
    }
}