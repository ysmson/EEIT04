<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="java.util.List" %>
<%@ page import="java.math.BigDecimal" %>
<%@ page import="tw.shawn.model.Transaction" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>交易報表</title>
</head>
<body>
<%
    String reportType = (String) request.getAttribute("reportType");
    String reportDate = (String) request.getAttribute("reportDate");
    List<tw.shawn.model.Transaction> transactions = (List<tw.shawn.model.Transaction>) request.getAttribute("transactions");
    BigDecimal totalIncome = (BigDecimal) request.getAttribute("totalIncome");
    BigDecimal totalExpense = (BigDecimal) request.getAttribute("totalExpense");
    BigDecimal netBalance = (BigDecimal) request.getAttribute("netBalance");

    if (totalIncome == null) totalIncome = new BigDecimal(0);
    if (totalExpense == null) totalExpense = new BigDecimal(0);
    if (netBalance == null) netBalance = new BigDecimal(0);

    String reportTitle = "所有交易記錄";
    if ("day".equals(reportType) && reportDate != null) {
        reportTitle = reportDate + " 交易記錄";
    } else if ("month".equals(reportType) && reportDate != null) {
        reportTitle = reportDate.substring(0, 7) + " 交易記錄";
    }
%>
    <h1><%= reportTitle %></h1>

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
        <% if (transactions != null && !transactions.isEmpty()) { %>
            <% for (tw.shawn.model.Transaction transaction : transactions) { %>
                <tr>
                    <td><%= transaction.getDate() %></td>
                    <td><%= transaction.getDescription() %></td>
                    <td><%= transaction.getAmount() %></td>
                    <td><%= transaction.getType() %></td>
                </tr>
            <% } %>
        <% } else { %>
            <tr><td colspan="4">沒有交易記錄。</td></tr>
        <% } %>
        </tbody>
        <tfoot>
            <tr>
                <td colspan="2">總收入：</td>
                <td><%= totalIncome %></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="2">總支出：</td>
                <td><%= totalExpense %></td>
                <td></td>
            </tr>
            <tr>
                <td colspan="2">結餘：</td>
                <td><%= netBalance %></td>
                <td></td>
            </tr>
        </tfoot>
    </table>
</body>
</html>