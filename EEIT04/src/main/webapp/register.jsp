<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>註冊</title>
</head>
<body>
    <h1>註冊</h1>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color:red;"><%= request.getAttribute("errorMessage") %></p>
    <% } %>

    <form action="registerServlet" method="post">
        <div>
            <label for="username">帳號：</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>
            <label for="password">密碼：</label>
            <input type="password" id="password" name="password" required>
        </div>
        <div>
            <label for="confirmPassword">確認密碼：</label>
            <input type="password" id="confirmPassword" name="confirmPassword" required>
        </div>
        <button type="submit">註冊</button>
    </form>

    <p>已經有帳號？<a href="login.jsp">回到登入</a></p>
</body>
</html>
