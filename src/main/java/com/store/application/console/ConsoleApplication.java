package com.store.application.console;

import com.store.application.menu.Menu;
import com.store.application.menu.impl.MainMenu;
import com.store.domain.jdbc.JdbcDepartmentRepository;
import com.store.domain.jdbc.JdbcProductRepository;
import com.store.domain.repositories.DepartmentRepository;
import com.store.domain.repositories.ProductRepository;
import com.store.domain.services.DepartmentService;
import com.store.domain.services.ProductService;
import com.store.sql.config.DBConnection;
import java.util.Scanner;

public class ConsoleApplication {
    private final DepartmentService departmentService;
    private final ProductService productService;
    private final Scanner scanner;
    private Menu currentMenu;

    public ConsoleApplication() {
        DBConnection dbConnection = new DBConnection();

        DepartmentRepository departmentRepository = new JdbcDepartmentRepository(dbConnection);
        ProductRepository productRepository = new JdbcProductRepository(dbConnection);

        this.departmentService = new DepartmentService(departmentRepository, productRepository);
        this.productService = new ProductService(productRepository, departmentRepository);
        this.scanner = new Scanner(System.in);
        this.currentMenu = new MainMenu();
    }

    public void start() {
        System.out.println("=== СИСТЕМА УПРАВЛЕНИЯ МАГАЗИНОМ ===");
        System.out.println("Добро пожаловать в систему управления товарами и отделами!");

        while (true) {
            try {
                currentMenu = currentMenu.show(this);
            } catch (Exception e) {
                System.out.println("Произошла ошибка: " + e.getMessage());
                System.out.println("Возврат в главное меню...");
                currentMenu = new MainMenu();
            }
        }
    }

    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public ProductService getProductService() {
        return productService;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public static void main(String[] args) {
        ConsoleApplication app = new ConsoleApplication();
        app.start();
    }
}