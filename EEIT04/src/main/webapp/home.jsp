<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>首頁</title>
</head>
<body>
    <h1>歡迎來到首頁！</h1>

    <%
        String loggedInUser = (String) session.getAttribute("loggedInUser");
        if (loggedInUser != null) {
    %>
        <p>您已登入，使用者名稱是：<%= loggedInUser %></p>
        <br>
        <p><a href="report.jsp">查看交易報表</a></p>
        <p><a href="logoutServlet">登出</a></p>
    <%
        } else {
    %>
        <p style="color:red;">您尚未登入。</p>
        <p><a href="index.jsp">返回登入頁面</a></p>
    <%
        }
    %>
</body>
</html>