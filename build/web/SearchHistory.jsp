<%-- 
    Document   : SearchHistory
    Created on : Jan 16, 2021, 10:29:50 AM
    Author     : Thuy Linh
--%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <c:set var="fullname" value="${sessionScope.FULLNAME}"/>
        <c:if test="${not empty fullname}">
            <font color="blue">
            Welcome,${sessionScope.FULLNAME}
            </font>
            </br> 
        </c:if>
        <h1>Your Shopping History</h1>
        <form action="DispatchServlet">
            <input type="submit" value="Shopping" name="btnAction" /></br>
            <input  style="width:400px; height:15px; font-size:15px;" type="text" name="txtSearchHistory" placeholder="Search by food name or buy date (YYYY-MM-DD)" value="${param.txtSearchHistory}" />
            <input type="submit" value="Search History" name="btnAction" />
        </form>
        <c:set var="result" value="${requestScope.SEARCHHISTORY}"/>
        <c:if test="${not empty result}">
            <table border="1">
                <thead>
                    <tr>
                        <th>No</th>
                        <th>Food</th>
                        <th>Quantity</th>
                        <th>Buy Date</th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach var="list" items="${result}" varStatus="counter">
                        <tr>
                            <td>${counter.count}</td>
                            <td>
                                ${list.foodName}
                            </td>
                            <td>
                                ${list.quantity}
                            </td>
                            <td>
                                ${list.buyDateTime}
                            </td>
                        </tr>
                    </c:forEach>

     
                </tbody>
            </table>

        </c:if>
    </body>
</html>
