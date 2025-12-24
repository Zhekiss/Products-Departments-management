<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>Товары</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">
            Товары
            <c:if test="${not empty departmentId}">
                (Отдел: ${departmentId})
            </c:if>
        </h1>

        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>

        <div class="mb-3">
            <a href="products?action=new" class="btn btn-success">Добавить товар</a>
            <a href="index.jsp" class="btn btn-secondary">На главную</a>
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Цена</th>
                    <th>ID отдела</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="product" items="${products}">
                    <tr>
                        <td>${product.productId}</td>
                        <td>${product.name}</td>
                        <td>${product.price}</td>
                        <td>${product.departmentId}</td>
                        <td>
                            <a href="products?action=edit&id=${product.productId}"
                               class="btn btn-sm btn-warning">Редактировать</a>
                            <a href="products?action=delete&id=${product.productId}"
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Удалить товар?')">🗑️ Удалить</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:if test="${empty products}">
            <div class="alert alert-warning">Товары не найдены</div>
        </c:if>
    </div>
</body>
</html>