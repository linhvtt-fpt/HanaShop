<%-- 
    Document   : PageCreateFood
    Created on : Jan 12, 2021, 7:57:00 PM
    Author     : Thuy Linh
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
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
        <h1>Create Food</h1>
        <form action="DispatchServlet">
            <input type="submit" value="Search Again" name="btnAction" />
        </form>
        <c:set var="error" value="${requestScope.CREATE_ERROR}"/>
        <form name="testform" action="DispatchServlet?btnAction=Create"  
              id="testform" enctype="multipart/form-data" method="post">
            Food ID : <input type="text" name="txtFoodID" value="" minlength="2" maxlength="10"/></br>
            <c:if test="${ not empty error}">
                <font color="red">
                ${error}
                </font></br>                
            </c:if>
            Name    : <input type="text" name="txtFoodName" value="" minlength="2" maxlength="30"/></br>
            Price   : <input type="number" name="txtPrice" value="" min="0" /></br>
            Description : <textarea rows="9" cols="70" name="txtDesciption" value=""></textarea></br>
            Quantity : <input type="number" name="txtQuantity" value="" min="0" step="1"/></br>
            Category    : 
            <c:set var="listCate" value="${sessionScope.LISTCATE}"/>
            <c:if test="${not empty listCate}">
                <select name="cboCate">
                    <c:forEach var="cate" items="${listCate}">
                        <option value="${cate.cateID}" > ${cate.cateName}</option>
                    </c:forEach>
                </select>
            </c:if>
            </br>
            File <input type="file" name="fileUp" value="" /> 
            </br> 
            <input type="submit" value="Create" name="btnAction"/>
        </form>

    </body>
</html>
