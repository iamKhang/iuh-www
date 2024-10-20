<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%--ftm--%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Product List</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</head>
<body>
<h1>Product List</h1>
<div id="product-list" class="container">
    <table class="table table-striped table-bordered mt-4">
        <thead class="thead-dark">
        <tr>
            <th>Mã sản phẩm</th>
            <th>Tên</th>
            <th>Ảnh sản phẩm</th>
            <th>Giá đang áp dụng</th>
            <th>Ngày áp dụng giá</th>
            <th>Hành động</th>
        </tr>
        </thead>
        <tbody>
        <!-- Lặp qua danh sách sản phẩm và hiển thị -->
        <c:forEach var="product" items="${products}">
            <tr>
                <td>${product.id}</td>
                <td>${product.name}</td>
                <td><img src="${product.imgPath}" alt="Product Image" class="img-fluid" style="height: 100px"></td>

                <!-- Tìm giá đang áp dụng -->
                <td>
                    <c:forEach var="price" items="${product.prices}">
                        <c:if test="${price.state == 1}">
                            <fmt:formatNumber value="${price.value}" type="currency" currencySymbol="₫"
                                              minFractionDigits="0" maxFractionDigits="0"/>
                        </c:if>
                    </c:forEach>
                </td>

                <!-- Ngày áp dụng giá -->
                <td>
                    <c:forEach var="price" items="${product.prices}">
                        <c:if test="${price.state == 1}">
                            ${price.applyDate}
                        </c:if>
                    </c:forEach>
                </td>

                <!-- Nút cập nhật sản phẩm -->
                <td>
                    <form action="updateProduct" method="get" class="form-inline">
                        <input type="hidden" name="productId" value="${product.id}">
                        <button type="submit" class="btn btn-primary">Cập nhật</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>

<!-- Nút thêm sản phẩm -->
<form action="toAddProduct" method="get" class="container mt-4 text-center">
    <button type="submit" class="btn btn-success">Thêm sản phẩm
    </button>
</form>

<script>

<%--    FETCH ADD--%>

</script>

</body>
</html>
