<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Add Product</title>
</head>
<body>
<h2>Add New Product</h2>
<form action="${pageContext.request.contextPath}/api/products/add" method="POST">
    <label for="name">Product Name:</label><br>
    <input type="text" id="name" name="name" required><br><br>

    <label for="description">Description:</label><br>
    <textarea id="description" name="description" required></textarea><br><br>

    <label for="manufacturerName">Manufacturer Name:</label><br>
    <input type="text" id="manufacturerName" name="manufacturerName" required><br><br>

    <label for="unit">Unit:</label><br>
    <input type="text" id="unit" name="unit" required><br><br>

    <label for="status">Status:</label><br>
    <select id="status" name="status" required>
        <option value="1">Active</option>
        <option value="2">Inactive</option>
    </select><br><br>

    <label for="price">Price:</label><br>
    <input type="number" id="price" name="price" step="0.01" required><br><br>

    <button type="submit">Add Product</button>
</form>
</body>
</html>
