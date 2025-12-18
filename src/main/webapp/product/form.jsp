<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${product != null ? 'Редактирование товара' : 'Новый товар'}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h1>${product != null ? 'Редактирование товара' : 'Добавление нового товара'}</h1>

        <form action="products" method="post" class="mt-4">
            <c:if test="${product != null}">
                <input type="hidden" name="id" value="${product.productId}">
            </c:if>

            <div class="mb-3">
                <label for="name" class="form-label">Название товара:</label>
                <input type="text" class="form-control" id="name" name="name"
                       value="${product != null ? product.name : ''}" required>
            </div>

            <div class="mb-3">
                <label for="price" class="form-label">Цена:</label>
                <input type="number" step="0.01" class="form-control" id="price" name="price"
                       value="${product != null ? product.price : ''}" required>
            </div>

            <div class="mb-3">
                <label for="departmentId" class="form-label">Отдел:</label>
                <select class="form-control" id="departmentId" name="departmentId" required>
                    <option value="">Выберите отдел</option>
                    <c:forEach var="department" items="${departments}">
                        <option value="${department.departmentId}"
                            ${product != null && product.departmentId == department.departmentId ? 'selected' : ''}>
                            ${department.name} (ID: ${department.departmentId})
                        </option>
                    </c:forEach>
                </select>
            </div>

            <button type="submit" class="btn btn-primary">
                ${product != null ? 'Сохранить изменения' : 'Создать товар'}
            </button>
            <a href="products?action=list" class="btn btn-secondary">Отмена</a>
        </form>
    </div>
</body>
</html>