<%-- 
    Document   : PageSearchForAdmin
    Created on : Jan 12, 2021, 8:00:27 AM
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
            <form action="DispatchServlet">
                <input type="submit" value="Log Out" name="btnAction" /></br>
                </br> <a href="PageCreateFood.jsp">Create New Food</a>
            </form>
        </c:if>
        <h1><font color="red">Hana Shop</font></h1>
        <form action="DispatchServlet">
            <font color="gray"> Search Value </font>
            <input type="text" name="txtSearchValue" value="${param.txtSearchValue}" /> </br>
            </br>
            <font color="gray">Price</font> <input  style="width:50px; height:15px; font-size:20px;" type="text" name="txtMinPrice" value="${param.txtMinPrice}" />
            <font color="gray">  to </font> <input  style="width:50px; height:15px; font-size:20px;" type="text" name="txtMaxPrice" value="${param.txtMaxPrice}" /> </br>
            </br>
            <font color="gray"> Cate </font>
            <c:set var="listCate" value="${sessionScope.LISTCATE}"/>
            <c:if test="${not empty listCate}">
                <select name="cboCate">
                    <option >ALL</option>
                    <c:forEach var="cate" items="${listCate}">
                        <option value="${cate.cateID}" > ${cate.cateName}</option>
                    </c:forEach>
                </select>
            </c:if>

            <input type="submit" value="Search" name="btnAction" />
        </form>


        <table border="1">     
            <tbody>
                <tr>
                    <c:set var="searchFood" value="${requestScope.RESULT_SEARCH}"/>
                    <c:if test="${not empty searchFood}">
                        <c:forEach var="foodSearch" items="${searchFood}">
                    <form action="DispatchServlet">
                        <td>
                            <image src="images/${foodSearch.image}" alt="img" width="200" height="200"/></br>
                            ${foodSearch.name}</br> <input type="hidden" name="txtFoodID" value="${foodSearch.foodID}" />
                            Price: ${foodSearch.price}</br> 
                            Description: ${foodSearch.description}</br>
                            Quantity:  ${foodSearch.quantity}
                            <c:if test="${not empty listCate}">
                                <select name="cboCate">
                                    <c:forEach var="cate" items="${listCate}">
                                        <option <c:if test="${cate.cateID eq foodSearch.cateID}"> selected="true"</c:if>>${cate.cateName}</option>
                                    </c:forEach>
                                </select>
                            </c:if>
                            <c:url var="urlRewriting" value="DispatchServlet">
                                <c:param name="btnAction"       value="Delete"/>
                                <c:param name="pk"              value="${foodSearch.foodID}"/>
                                <c:param name="searchName"      value="${valueSearch}"/>
                                <c:param name="searchMinPrice"  value="${valueMinPrice}"/>
                                <c:param name="searchMaxPrice"  value="${valueMaxPrice}"/>
                                <c:param name="searchCate"      value="${valueCate}"/>
                            </c:url>

                   <c:url var="urlRewritingforUpdate" value="DispatchServlet">
                                <c:param name="btnAction"       value="Update"/>
                                <c:param name="pk"              value="${foodSearch.foodID}"/>
                                <c:param name="searchName"      value="${valueSearch}"/>
                                <c:param name="searchMinPrice"  value="${valueMinPrice}"/>
                                <c:param name="searchMaxPrice"  value="${valueMaxPrice}"/>
                                <c:param name="searchCate"      value="${valueCate}"/>
                            </c:url> 
                            <c:if test="${foodSearch.status}"><a href="${urlRewriting}" onclick="return confirm('Are you sure you want to continue DELETE it ?')">Delete</a></c:if>   
                            <a href="${urlRewritingforUpdate}"> Update </a>
                        </td>
                    </form>
                </c:forEach>
            </c:if>
        </tr>
    </tbody>
</table>
<c:set var="currentPage" value="${requestScope.currentPage}"/>    
<c:set var="pageSize" value="${requestScope.PAGESIZE}"/>
<table border="1">                     
    <tr>
        <c:forEach var="pageID" begin="1" end="${pageSize}">
            <c:choose>
                <c:when test="${currentPage eq pageID}">
                    <td>
                        ${pageID}
                    </td>
                </c:when>
                <c:otherwise>
                    <c:url var="nextPage" value="DispatchServlet">
                        <c:param name="btnAction" value="Search"/>
                        <c:param name="page" value="${pageID}"/>
                        <c:param name="txtSearchValue" value="${param.txtSearchValue}"/>
                        <c:param name="txtMinPrice" value="${param.txtMinPrice}"/>
                        <c:param name="txtMaxPrice" value="${param.txtMaxPrice}"/>
                        <c:param name="cboCate" value="${param.cboCate}"/>
                    </c:url>
                    <td>
                        <a href="${nextPage}">${pageID}</a>
                    </td>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </tr>
</table>

</body>
</html>
