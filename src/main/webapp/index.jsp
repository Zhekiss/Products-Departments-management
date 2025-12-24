<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Система управления магазином</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
    <style>
        body {
            background-color: #f8f9fa;
            padding-top: 50px;
        }
        .container {
            max-width: 800px;
        }
        .card {
            box-shadow: 0 4px 6px rgba(0,0,0,0.1);
            transition: transform 0.3s;
        }
        .card:hover {
            transform: translateY(-5px);
        }
    </style>
</head>
<body>
    <div class="container text-center">
        <h1 class="mb-4">Система управления магазином</h1>
        <p class="lead mb-5">Управление товарами и отделами</p>

        <div class="row">
            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">Управление товарами</h5>
                        <p class="card-text">Добавление, редактирование и удаление товаров</p>
                        <a href="products?action=list" class="btn btn-primary">Перейти к товарам</a>
                    </div>
                </div>
            </div>

            <div class="col-md-6 mb-4">
                <div class="card h-100">
                    <div class="card-body">
                        <h5 class="card-title">Управление отделами</h5>
                        <p class="card-text">Управление отделами магазина</p>
                        <a href="departments?action=list" class="btn btn-primary">Перейти к отделам</a>
                    </div>
                </div>
            </div>
        </div>

        <div class="mt-5">
            <a href="departments?action=withoutProducts" class="btn btn-outline-info me-2">
                Отделы без товаров
            </a>
        </div>
    </div>

    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>