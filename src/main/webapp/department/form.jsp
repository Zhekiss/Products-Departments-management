<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <title>${department != null ? 'Редактирование отдела' : 'Новый отдел'}</title>
    <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css">
</head>
<body>
    <div class="container mt-4">
        <h1>${department != null ? 'Редактирование отдела' : 'Добавление нового отдела'}</h1>

        <form action="departments" method="post" class="mt-4">
            <c:if test="${department != null}">
                <input type="hidden" name="id" value="${department.departmentId}">
            </c:if>

            <div class="mb-3">
                <label for="name" class="form-label">Название отдела:</label>
                <input type="text" class="form-control" id="name" name="name"
                       value="${department != null ? department.name : ''}" required>
            </div>

            <div class="mb-3">
                <label for="workingHours" class="form-label">Часы работы:</label>
                <input type="text" class="form-control" id="workingHours" name="workingHours"
                       value="${department != null ? department.workingHours : ''}"
                       placeholder="9:00-21:00" required>
            </div>

            <button type="submit" class="btn btn-primary">
                ${department != null ? 'Сохранить изменения' : 'Создать отдел'}
            </button>
            <a href="departments?action=list" class="btn btn-secondary">Отмена</a>
        </form>
    </div>
</body>
</html>