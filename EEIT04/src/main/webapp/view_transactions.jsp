<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("index.html"); // 未登入導回登入頁面
        return;
    }

    List<tw.shawn.model.Transaction> transactions = (List<tw.shawn.model.Transaction>) request.getAttribute("transactions");
%>
<html>
<head>
    <title>查看交易明細</title>
    <style>
        table { width: 80%; border-collapse: collapse; margin-top: 20px; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: left; }
        th { background-color: #f2f2f2; }
        .income { color: green; }
        .expense { color: red; }
    </style>
</head>
<body>
    <h1>交易明細</h1>

    <c:if test="${not empty transactions}">
        <table>
            <thead>
                <tr>
                    <th>日期</th>
                    <th>描述</th>
                    <th>金額</th>
                    <th>類型</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="transaction" items="${transactions}">
                    <tr>
                        <td><fmt:formatDate value="${transaction.date}" pattern="yyyy-MM-dd HH:mm:ss"/></td>
                        <td>${transaction.description}</td>
                        <td><fmt:formatNumber value="${transaction.amount}" pattern="#,##0.00"/></td>
                        <td class="${transaction.type}">${transaction.type == 'income' ? '收入' : '支出'}</td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </c:if>
    <c:if test="${empty transactions}">
        <p>沒有交易記錄。</p>
    </c:if>

    <p><a href="home.jsp">返回首頁</a></p>
</body>
</html>