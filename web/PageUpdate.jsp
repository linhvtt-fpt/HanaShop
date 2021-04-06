<%-- 
    Document   : PageUpdate
    Created on : Jan 11, 2021, 2:46:12 PM
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
        <c:set var="fullname" value="${sessionScope.ISADMIN}"/>
        <c:if test="${not empty fullname}">
            <font color="blue">
            Welcome,${sessionScope.ISADMIN}
            </font>
            </br> 
        </c:if>
        <h1>Update Food</h1>
        <c:set var="foodDetail" value="${requestScope.FOODDETAIL}"/>
        <c:if test="${not empty foodDetail}">
            <form name="testform" action="DispatchServlet?btnAction=Save" id="testform" enctype="multipart/form-data" method="post">
                <table border="1">
                    <tbody>
                        <tr>
                            <td>
                                <image src="images/${foodDetail.image}" alt="img" width="200" height="200"/></br>
                                File: <input type="file" name="fileUp" value="" />
                                <input type="text" name="txtFoodName" value="${foodDetail.name}" /> 
                                <input type="hidden" name="pk" value="${foodDetail.foodID}" /> </br> 
                                Price: <input type="text" name="txtFoodPrice" value="${foodDetail.price}" /> </br>
                                Description: <input type="text" name="txtFoodDescription" value="${foodDetail.description}" /> </br>
                                Quantity: <input type="text" name="txtQuantity" value="${foodDetail.quantity}" />
                                <c:set var="cates" value="${requestScope.CATE}"/>
                                <c:if test="${not empty cates}">
                                    <select name="cboCate">
                                        <c:forEach var="cate" items="${cates}" >
                                            <option value="${cate.cateID}">${cate.cateName}</option>
                                        </c:forEach>
                                    </select>
                                </c:if>
                                <c:set var="status" value="${foodDetail.status}"/>
                                <select name="cboStatus">
                                    <option <c:if test="${status}">selected="true"</c:if>>Active</option>
                                    <option <c:if test="${!status}">selected="true"</c:if>>Inactive</option>
                                    </select>
                                    <input type="submit" value="Save" name="btnAction" />
                                </td>
                            </tr>

                        </tbody>
                    </table>
                </form>

        </c:if>
        <c:if test="${not empty requestScope.SUCCESS}">
            ${requestScope.SUCCESS}
        </c:if>
                <form action="DispatchServlet">
                    <input type="submit" value="Search Again" name="btnAction" />
                </form>
    </body>
</html>
