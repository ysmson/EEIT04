<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%
    Integer userId = (Integer) session.getAttribute("userId");
    if (userId == null) {
        response.sendRedirect("index.html"); // 未登入導回登入頁面
        return;
    }

    int year = (Integer) request.getAttribute("year");
    int month = (Integer) request.getAttribute("month"); // 0 = 一月, 11 = 十二月
%>
<html>
<head>
    <title>行事曆</title>
    <style>
        table { width: 100%; border-collapse: collapse; }
        th, td { border: 1px solid #ccc; padding: 8px; text-align: center; vertical-align: top; }
        .today { background-color: #f0f0f0; }
        .has-transaction { color: green; font-weight: bold; }
        .has-event { color: blue; font-weight: bold; }
        .day-content { display: flex; flex-direction: column; align-items: flex-start; }
        .transaction-item { font-size: 0.8em; color: green; margin-bottom: 2px; }
        .event-item { font-size: 0.8em; color: blue; margin-bottom: 2px; }
    </style>
</head>
<body>
    <h1>行事曆 - ${year} 年 ${month + 1} 月</h1>
    <table>
        <thead>
            <tr>
                <th>日</th><th>一</th><th>二</th><th>三</th><th>四</th><th>五</th><th>六</th>
            </tr>
        </thead>
        <tbody>
        <c:forEach var="week" begin="0" end="5">
            <tr>
                <c:forEach var="dayOfWeek" begin="1" end="7">
                    <c:set var="dayOfMonth">
                        ${week * 7 + dayOfWeek - (firstDayOfWeek - 1)}
                    </c:set>
                    <c:if test="${dayOfMonth > 0 && dayOfMonth <= daysInMonth}">
                        <fmt:formatDate value="${calendar.time}" pattern="yyyy-MM-dd" var="currentDateStr"/>
                        <c:set var="isToday" value="${year == todayYear && month == todayMonth && dayOfMonth == todayDay}"/>
                        <td class="${isToday ? 'today' : ''}">
                            <div class="day-content">
                                <div>
                                    <c:set var="hasTransaction" value="false"/>
                                    <c:forEach var="transaction" items="${monthlyTransactions}">
                                        <fmt:formatDate value="${transaction.date}" pattern="yyyy-MM-dd" var="transactionDateStr"/>
                                        <c:if test="${transactionDateStr == currentDateStr}">
                                            <c:set var="hasTransaction" value="true"/>
                                        </c:if>
                                    </c:forEach>
                                    <c:set var="hasEvent" value="false"/>
                                    <c:forEach var="event" items="${monthlyEvents}">
                                        <fmt:formatDate value="${event.date}" pattern="yyyy-MM-dd" var="eventDateStr"/>
                                        <c:if test="${eventDateStr == currentDateStr}">
                                            <c:set var="hasEvent" value="true"/>
                                        </c:if>
                                    </c:forEach>

                                    <c:if test="${hasTransaction && hasEvent}">
                                        <span class="has-transaction">●</span><span class="has-event">●</span>
                                    </c:if>
                                    <c:if test="${hasTransaction && !hasEvent}">
                                        <span class="has-transaction">●</span>
                                    </c:if>
                                    <c:if test="${!hasTransaction && hasEvent}">
                                        <span class="has-event">●</span>
                                    </c:if>
                                    ${dayOfMonth}
                                </div>
                                <c:forEach var="transaction" items="${monthlyTransactions}">
                                    <fmt:formatDate value="${transaction.date}" pattern="yyyy-MM-dd" var="transactionDateStr"/>
                                    <c:if test="${transactionDateStr == currentDateStr}">
                                        <div class="transaction-item">${transaction.description} (${transaction.amount})</div>
                                    </c:if>
                                </c:forEach>
                                <c:forEach var="event" items="${monthlyEvents}">
                                    <fmt:formatDate value="${event.date}" pattern="yyyy-MM-dd" var="eventDateStr"/>
                                    <c:if test="${eventDateStr == currentDateStr}">
                                        <div class="event-item">${event.title}</div>
                                    </c:if>
                                </c:forEach>
                            </div>
                        </td>
                    </c:if>
                    <c:if test="${dayOfMonth <= 0 || dayOfMonth > daysInMonth}">
                        <td>&nbsp;</td>
                    </c:if>
                </c:forEach>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <p><a href="home.jsp">返回首頁</a></p>
</body>
</html>