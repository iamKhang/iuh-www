<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Add New Product</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body>
<div class="container mt-5">
    <h2 class="text-center mb-4">Add New Product</h2>

    <!-- Form không sử dụng action mà dùng JavaScript -->
    <form id="productForm" class="needs-validation" novalidate>
        <div class="form-group">
            <label for="productName">Product Name</label>
            <input type="text" class="form-control" id="productName" name="productName" required>
            <div class="invalid-feedback">Please enter the product name.</div>
        </div>

        <div class="form-group">
            <label for="description">Description</label>
            <textarea class="form-control" id="description" name="description" rows="3" required></textarea>
            <div class="invalid-feedback">Please enter a product description.</div>
        </div>

        <div class="form-group">
            <label for="imgPath">Image Path</label>
            <input type="text" class="form-control" id="imgPath" name="imgPath" required>
            <div class="invalid-feedback">Please enter the image path.</div>
        </div>

        <div class="form-group">
            <label for="price">Price (VND)</label>
            <input type="number" class="form-control" id="price" name="price" required>
            <div class="invalid-feedback">Please enter the product price.</div>
        </div>

        <div class="form-group">
            <label for="applyDate">Apply Date</label>
            <input type="date" class="form-control" id="applyDate" name="applyDate" required>
            <div class="invalid-feedback">Please select the apply date.</div>
        </div>

        <button type="button" onclick="submitForm()" class="btn btn-success btn-block">Add Product</button>
    </form>
</div>

<script>
    function submitForm() {
        const form = document.getElementById('productForm');

        // Lấy dữ liệu từ form và tạo đối tượng dữ liệu
        const data = {
            name: form.productName.value,
            description: form.description.value,
            imgPath: form.imgPath.value,
            prices: [
                {
                    value: parseInt(form.price.value),
                    applyDate: form.applyDate.value,
                    note: "",
                }
            ]
        };

        console.log(data);

        // Gửi yêu cầu POST tới server bằng fetch
        fetch("http://localhost:8080/week03_lab_leHoangKhang_21083791-1.0-SNAPSHOT/api/products/add", {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify(data)
        })
            .then(response => {
                if (!response.ok) {
                    throw new Error('Network response was not ok');
                }
                return response.json();
            })
            .then(data => {
                console.log(data);
                alert("Product added successfully!");
            })
            .catch(error => {
                console.error('There was a problem with the fetch operation:', error);
                alert("Failed to add product.");
            });
    }
</script>

</body>
</html>
