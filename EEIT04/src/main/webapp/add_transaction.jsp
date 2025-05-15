<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("index.html"); // 未登入導回登入頁面
        return;
    }
%>
<html>
<head>
    <title>新增交易</title>
</head>
<body>
    <h1>新增交易</h1>
    <form action="addTransaction" method="post">
        <div>
            <label for="date">日期:</label>
            <input type="date" id="date" name="date" required>
        </div>
        <div>
            <label for="description">描述:</label>
            <input type="text" id="description" name="description" required>
        </div>
        <div>
            <label for="amount">金額:</label>
            <input type="number" id="amount" name="amount" step="0.01" required>
        </div>
        <div>
            <label for="type">類型:</label>
            <select id="type" name="type" required>
                <option value="income">收入</option>
                <option value="expense">支出</option>
            </select>
        </div>
        <button type="submit">新增</button>
        <% if (request.getAttribute("errorMessage") != null) { %>
            <p style="color: red;"><%= request.getAttribute("errorMessage") %></p>
        <% } %>
        <p><a href="home.jsp">返回首頁</a></p>
    </form>
</body>
</html>