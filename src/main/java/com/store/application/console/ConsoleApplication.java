package com.store.application.console;

import com.store.application.menu.Menu;
import com.store.application.menu.impl.MainMenu;
import com.store.domain.inmemory.InMemoryDepartmentRepository;
import com.store.domain.inmemory.InMemoryProductRepository;
import com.store.domain.services.DepartmentService;
import com.store.domain.services.ProductService;
import java.util.Scanner;

public class ConsoleApplication {
    private final DepartmentService departmentService;
    private final ProductService productService;
    private final Scanner scanner;
    private Menu currentMenu;

    public ConsoleApplication() {
        InMemoryDepartmentRepository departmentRepository = new InMemoryDepartmentRepository();
        InMemoryProductRepository productRepository = new InMemoryProductRepository();
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