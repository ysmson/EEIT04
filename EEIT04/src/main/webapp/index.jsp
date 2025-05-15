<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>登入</title>
</head>
<body>
    <h1>登入</h1>

    <% if (request.getAttribute("errorMessage") != null) { %>
        <p style="color:red;"><%= request.getAttribute("errorMessage") %></p>
    <% } %>

    <form action="${pageContext.request.contextPath}/loginServlet" method="post">
        <div>
            <label for="username">帳號：</label>
            <input type="text" id="username" name="username" required>
        </div>
        <div>
            <label for="password">密碼：</label>
            <input type="password" id="password" name="password" required>
        </div>
        <button type="submit">登入</button>
    </form>

    <p>還沒有帳號？<a href="${pageContext.request.contextPath}/register.jsp">註冊</a></p>
</body>
</html>
