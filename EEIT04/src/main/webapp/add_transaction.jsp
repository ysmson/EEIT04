<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>新增交易</title>
</head>
<body>
    <h1>新增交易</h1>

    <%
        String errorMessage = (String) request.getAttribute("errorMessage");
        if (errorMessage != null) {
    %>
        <p style="color:red;"><%= errorMessage %></p>
    <%
        }
    %>

    <form action="addTransaction" method="post">
        <label for="date">日期：</label>
        <input type="date" id="date" name="date" required><br><br>

        <label for="description">描述：</label>
        <input type="text" id="description" name="description" required><br><br>

        <label for="amount">金額：</label>
        <input type="number" id="amount" name="amount" step="0.01" required><br><br>

        <label for="type">類型：</label>
        <select id="type" name="type" required>
            <option value="income">收入</option>
            <option value="expense">支出</option>
        </select><br><br>

        <input type="submit" value="新增交易">
    </form>

    <br>
    <a href="home.jsp">返回首頁</a>  </body>
</html>