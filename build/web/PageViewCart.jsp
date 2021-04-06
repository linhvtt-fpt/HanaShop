<%-- 
    Document   : PageViewCart
    Created on : Jan 13, 2021, 10:16:17 PM
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
        <h1>Your Cart</h1>

        <c:set var="cart" value="${sessionScope.CUSTCART}"/>
        <c:if test="${not empty cart.items}">
            <table border="1">
                <thead>
                    <tr>
                        <th>NO.</th>
                        <th>Name</th>
                        <th>Price</th>
                        <th>Quantity</th>
                        <th>Total</th>
                        <th>Choose</th>
                    </tr>
                </thead>
                <form action="DispatchServlet">
                    <tbody>
                        <c:forEach var="listItems" items="${cart.items}" varStatus="counter">
                            <tr>
                                <td>
                                    ${counter.count}
                                </td>
                                <td>
                                    ${listItems.value.name} <input type="hidden" name="key" value="${listItems.key}" />
                                </td>
                                <td>
                                    ${listItems.value.price}
                                </td>
                                <td>
                                    <input type="number" name="txtQuantity" min="1" value="${listItems.value.quantity}"/>

                                </td>
                                <td>
                                    ${listItems.value.price * listItems.value.quantity}
                                </td>
                                <td>
                                    <input type="checkbox" name="chkBox" value="${listItems.key}" />
                                </td>
                            </tr>
                        </c:forEach>
                        <c:set var="total" value="${sessionScope.TOTAL}"/>
                        <c:if test="${not empty total}">
                        <td colspan="6">
                            Total:  ${total}
                        </td>
                    </c:if>
                    <tr>
                        <td colspan="6">
                            <input type="submit" value="Remove" name="btnAction" onclick="return confirm('Are you want to REMOVE items from your cart?')"/>
                            <input type="submit" value="Check out" name="btnAction" />
                            <input type="submit" value="Update Cart" name="btnAction" />
                        </td>
                        <td>
                            <c:set var="errorBill" value="${sessionScope.ERROR}"/>
                            <c:if test="${not empty errorBill}">
                                <font color="red">${errorBill}</font>
                            </c:if>
                            <c:set var="errorQuantity" value="${sessionScope.ERROR_CHECKOUT}"/>
                            <c:if test="${not empty errorQuantity}">
                                <font color="red">${errorQuantity}</font>
                            </c:if>    
                        </td>
                    </tr>

                    </tbody>
                </form>
            </table>
        </c:if>

        <c:if test="${empty cart.items}">
            No item!
           
        </c:if>
             <form action="DispatchServlet">
                <input type="submit" value="Add Items To Your Cart" name="btnAction" />
            </form>
    </body>
</html>
