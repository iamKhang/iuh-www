<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Index Page</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
</head>
<body class="bg-light">

<div class="container mt-5">
    <div class="row justify-content-center">
        <div class="col-md-6 text-center">
            <h1 class="mb-4">Welcome to the Index Page</h1>
            <form action="${pageContext.request.contextPath}/api/navigate/toProduct" method="get">
                <button type="submit" class="btn btn-primary btn-block">Go to Product Page</button>
            </form>

            <form action="${pageContext.request.contextPath}/api/navigate/toPriceManagerment" method="get" class="mt-3">
                <button type="submit" class="btn btn-secondary btn-block">Go to Price Management Page</button>
            </form>
        </div>
    </div>
</div>

<script src="https://code.jquery.com/jquery-3.5.1.slim.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@4.5.2/dist/js/bootstrap.bundle.min.js"></script>

</body>
</html>
