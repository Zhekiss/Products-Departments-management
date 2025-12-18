<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${title != null ? title : 'Отделы'}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h1 class="mb-4">${title != null ? title : 'Отделы магазина'}</h1>

        <c:if test="${not empty message}">
            <div class="alert alert-info">${message}</div>
        </c:if>

        <div class="mb-3">
            <a href="departments?action=new" class="btn btn-success">➕ Добавить отдел</a>
            <a href="index.jsp" class="btn btn-secondary">🏠 На главную</a>
        </div>

        <table class="table table-striped">
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Название</th>
                    <th>Часы работы</th>
                    <th>Действия</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="department" items="${departments}">
                    <tr>
                        <td>${department.departmentId}</td>
                        <td>${department.name}</td>
                        <td>${department.workingHours}</td>
                        <td>
                            <a href="departments?action=edit&id=${department.departmentId}"
                               class="btn btn-sm btn-warning">✏️ Редактировать</a>
                            <a href="departments?action=delete&id=${department.departmentId}"
                               class="btn btn-sm btn-danger"
                               onclick="return confirm('Удалить отдел?')">🗑️ Удалить</a>
                            <a href="products?action=byDepartment&departmentId=${department.departmentId}"
                               class="btn btn-sm btn-info">📦 Товары</a>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:if test="${empty departments}">
            <div class="alert alert-warning">Отделы не найдены</div>
        </c:if>
    </div>
</body>
</html>