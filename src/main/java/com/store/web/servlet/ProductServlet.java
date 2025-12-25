package com.store.web.servlet;

import com.store.domain.models.Product;
import com.store.web.context.AppContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/products")
public class ProductServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String action = request.getParameter("action");

        if (action == null || action.equals("list")) {
            List<Product> products = AppContext.getInstance()
                    .getProductService().getAllProducts();
            request.setAttribute("products", products);
            request.getRequestDispatcher("/product/list.jsp").forward(request, response);

        } else if (action.equals("new")) {
            request.setAttribute("departments", AppContext.getInstance()
                    .getDepartmentService().getAllDepartments());
            request.getRequestDispatcher("/product/form.jsp").forward(request, response);

        } else if (action.equals("edit")) {
            Long id = Long.parseLong(request.getParameter("id"));
            AppContext.getInstance().getProductService().getProductById(id)
                    .ifPresent(product -> request.setAttribute("product", product));
            request.setAttribute("departments", AppContext.getInstance()
                    .getDepartmentService().getAllDepartments());
            request.getRequestDispatcher("/product/form.jsp").forward(request, response);

        } else if (action.equals("delete")) {
            Long id = Long.parseLong(request.getParameter("id"));
            boolean deleted = AppContext.getInstance().getProductService().deleteProduct(id);
            request.setAttribute("message", deleted ? "Товар удален" : "Не удалось удалить товар");
            response.sendRedirect("products?action=list");

        } else if (action.equals("byDepartment")) {
            Long departmentId = Long.parseLong(request.getParameter("departmentId"));
            List<Product> products = AppContext.getInstance()
                    .getProductService().getProductsByDepartment(departmentId);
            request.setAttribute("products", products);
            request.setAttribute("departmentId", departmentId);
            request.getRequestDispatcher("/product/list.jsp").forward(request, response);
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String idParam = request.getParameter("id");
        String name = request.getParameter("name");
        String priceParam = request.getParameter("price");
        String departmentIdParam = request.getParameter("departmentId");

        BigDecimal price = new BigDecimal(priceParam);
        Long departmentId = Long.parseLong(departmentIdParam);

        if (idParam == null || idParam.isEmpty()) {
            Product product = AppContext.getInstance()
                    .getProductService().createProduct(name, price, departmentId);
            request.setAttribute("message", "Товар создан: " + product.getName());
        } else {
            Long id = Long.parseLong(idParam);
            boolean updated = AppContext.getInstance()
                    .getProductService().updateProduct(id, name, price, departmentId);
            request.setAttribute("message", updated ? "Товар обновлен" : "Ошибка обновления");
        }

        response.sendRedirect("products?action=list");
    }
}