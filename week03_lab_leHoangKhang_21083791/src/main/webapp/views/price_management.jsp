<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý giá sản phẩm</title>
    <script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
    <script>
        function showPrices() {
            const productSelect = document.getElementById("productSelect");
            const selectedProductId = productSelect.value;


            const priceLists = document.querySelectorAll(".price-list");
            priceLists.forEach(list => list.style.display = "none");
            if (selectedProductId) {
                const selectedPriceList = document.getElementById("prices-" + selectedProductId);
                if (selectedPriceList) {
                    selectedPriceList.style.display = "block";
                    updateChart(selectedProductId);
                }
            } else {

                if (window.myChart) window.myChart.destroy();
            }
        }

        function updateChart(productId) {
            const labels = [];
            const data = [];

            const priceItems = document.querySelectorAll(`#prices-${productId} li`);
            priceItems.forEach(item => {
                const date = item.getAttribute("data-date");
                const value = item.getAttribute("data-value");
                labels.push(date);
                data.push(value);
            });

            // Hiển thị biểu đồ giá của sản phẩm đã chọn
            const ctx = document.getElementById('priceChart').getContext('2d');
            if (window.myChart) window.myChart.destroy(); // Hủy biểu đồ cũ nếu có
            window.myChart = new Chart(ctx, {
                type: 'line',
                data: {
                    labels: labels,
                    datasets: [{
                        label: 'Biểu đồ giá (VND)',
                        data: data,
                        fill: false,
                        borderColor: 'rgba(75, 192, 192, 1)',
                        tension: 0.1
                    }]
                },
                options: {
                    scales: {
                        x: {
                            title: {
                                display: true,
                                text: 'Ngày áp dụng'
                            }
                        },
                        y: {
                            title: {
                                display: true,
                                text: 'Giá (VND)'
                            },
                            beginAtZero: true
                        }
                    }
                }
            });
        }
    </script>
</head>
<body>

<h2>Quản lý giá sản phẩm</h2>

<select id="productSelect" onchange="showPrices()">
    <option value="">Chọn mã sản phẩm</option>
    <c:forEach var="product" items="${products}">
        <option value="${product.id}">${product.id} - ${product.name}</option>
    </c:forEach>
</select>

<h3>Giá của sản phẩm đã chọn:</h3>

<c:forEach var="product" items="${products}">
    <ul id="prices-${product.id}" class="price-list" style="display:none;">
        <c:forEach var="price" items="${product.prices}">
            <li data-date="${price.applyDate}" data-value="${price.value}">
                Giá: ${price.value}, Ngày áp dụng: ${price.applyDate}
            </li>
        </c:forEach>
    </ul>
</c:forEach>

<div style="width: 80%; margin-top: 30px;">
    <canvas id="priceChart"></canvas>
</div>

</body>
</html>
