package com.store.web.servlet;

import com.store.domain.models.Department;
import com.store.web.context.AppContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;

@WebServlet("/departments")
public class DepartmentServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            // Показать все отделы
            List<Department> departments = AppContext.getInstance()
                    .getDepartmentService().getAllDepartments();
            request.setAttribute("departments", departments);
            request.getRequestDispatcher("/department/list.jsp").forward(request, response);

        } else if (action.equals("new")) {
            // Форма создания нового отдела
            request.getRequestDispatcher("/department/form.jsp").forward(request, response);

        } else if (action.equals("edit")) {
            // Форма редактирования отдела
            Long id = Long.parseLong(request.getParameter("id"));
            AppContext.getInstance().getDepartmentService().getDepartmentById(id)
                    .ifPresent(department -> request.setAttribute("department", department));
            request.getRequestDispatcher("/department/form.jsp").forward(request, response);

        } else if (action.equals("delete")) {
            // Удаление отдела
            Long id = Long.parseLong(request.getParameter("id"));
            boolean deleted = AppContext.getInstance().getDepartmentService().deleteDepartment(id);
            request.setAttribute("message", deleted ? "Отдел удален" : "Не удалось удалить отдел");
            response.sendRedirect("departments?action=list");

        } else if (action.equals("withoutProducts")) {
            // Отделы без товаров
            List<Department> departments = AppContext.getInstance()
                    .getDepartmentService().getDepartmentsWithoutProducts();
            request.setAttribute("departments", departments);
            request.setAttribute("title", "Отделы без товаров");
            request.getRequestDispatcher("/department/list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String workingHours = request.getParameter("workingHours");

        if (idParam == null || idParam.isEmpty()) {
            // Создание нового отдела
            Department department = AppContext.getInstance()
                    .getDepartmentService().createDepartment(name, workingHours);
            request.setAttribute("message", "Отдел создан: " + department.getName());
        } else {
            // Обновление существующего отдела
            Long id = Long.parseLong(idParam);
            boolean updated = AppContext.getInstance()
                    .getDepartmentService().updateDepartment(id, name, workingHours);
            request.setAttribute("message", updated ? "Отдел обновлен" : "Ошибка обновления");
        }

        response.sendRedirect("departments?action=list");
    }
}