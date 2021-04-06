<%-- 
    Document   : PageSearch
    Created on : Jan 6, 2021, 1:00:58 PM
    Author     : Thuy Linh
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
    <head>

        <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/css/bootstrap.min.css" rel="stylesheet" id="bootstrap-css">
        <link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" id="bootstrap-css">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-alpha1/dist/js/bootstrap.bundle.min.js"></script>
        <script src="https://cdnjs.cloudflare.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
        <script src="jquery-3.3.1.slim.min.js"></script>
    </head>
    <div style="background: url(https://images.freeimages.com/images/premium/previews/6817/6817534-fast-food-background.jpg)" class="page-holder bg-cover">
        <body>
            <c:set var="fullname" value="${sessionScope.FULLNAME}"/>
            <c:if test="${not empty fullname}">
                <font color="blue">
                Welcome,${sessionScope.FULLNAME}
                </font>
                </br> 
                <form action="DispatchServlet">
                    <input type="submit" value="Log Out" name="btnAction" />
                </form>
                <a href="SearchHistory.jsp">Shopping History</a>
            </c:if>
            <c:if test="${empty fullname}">
                <a href="PageLogin.html">Log In</a>
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
                            <option value="${cate.cateID}"> ${cate.cateName}</option>
                        </c:forEach>
                    </select>
                </c:if>
                <input type="submit" value="Search" name="btnAction" />
            </form>
            <form action="DispatchServlet" >
                <c:if test="${not empty fullname}"> <input type="submit" value="View cart" name="btnAction" /> </c:if>
                </form>

            <c:set var="searchFood" value="${requestScope.RESULT_SEARCH}"/>
            <c:if test="${not empty searchFood}">
                <div class="row g-3">
                    <c:forEach var="foodSearch" items="${searchFood}">

                        <div class="col-md-4">
                            <form action="DispatchServlet">
                                <c:if test="${true eq foodSearch.status}">
                                    <div class="card"> <img src="images/${foodSearch.image}" class="card-img-top" width="200" height="200">
                                        <div class="card-body">
                                            <div class="d-flex justify-content-between"> <span class="font-weight-bold"> ${foodSearch.name} <input type="hidden" name="txtFoodID" value="${foodSearch.foodID}" /></span> <span class="font-weight-bold"> Price: ${foodSearch.price}</span> </div>
                                            <p class="card-text mb-1 mt-1">Description: ${foodSearch.description}</p>
                                            <div class="d-flex align-items-center flex-row"> <img src="https://i.imgur.com/e9VnSng.png" width="20"> <span class="guarantee"> Quantity:  ${foodSearch.quantity}</span> </div>
                                        </div>
                                        <div class="card-body">
                                            <div class="text-right buttons"><input type="submit" value="Add to cart" name="btnAction" /> </div>
                                        </div>
                                    </div>
                                </c:if>
                            </form>  
                        </div>



                    </c:forEach>
                </div>
            </c:if>



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
                                    <c:param name="page"    value="${pageID}"/>
                                    <c:param name="txtSearchValue"    value="${param.txtSearchValue}"/>
                                    <c:param name="txtMinPrice"  value="${param.txtMinPrice}"/>
                                    <c:param name="txtMaxPrice"  value="${param.txtMaxPrice}"/>
                                    <c:param name="cboCate"      value="${param.cboCate}"/>
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
    </div>
</html>
