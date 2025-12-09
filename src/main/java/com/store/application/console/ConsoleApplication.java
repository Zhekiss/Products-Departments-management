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

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.Statement;
import java.util.Scanner;

public class ConsoleApplication {
    private final DepartmentService departmentService;
    private final ProductService productService;
    private final Scanner scanner;
    private Menu currentMenu;

    public ConsoleApplication() {
        // Создаем подключение к БД
        DBConnection dbConnection = new DBConnection();

        // Инициализируем БД (создаем таблицы и заполняем данные)
        initializeDatabase(dbConnection);

        // Создаем JDBC репозитории
        DepartmentRepository departmentRepository = new JdbcDepartmentRepository(dbConnection);
        ProductRepository productRepository = new JdbcProductRepository(dbConnection);

        this.departmentService = new DepartmentService(departmentRepository, productRepository);
        this.productService = new ProductService(productRepository, departmentRepository);
        this.scanner = new Scanner(System.in);
        this.currentMenu = new MainMenu();
    }

    private void initializeDatabase(DBConnection dbConnection) {
        try (Connection connection = dbConnection.getConnection();
             Statement statement = connection.createStatement()) {

            // Читаем и выполняем schema.sql
            String schemaSql = Files.readString(Path.of("src/main/resources/sql/schema.sql"));
            statement.execute(schemaSql);

            // Читаем и выполняем data.sql
            String dataSql = Files.readString(Path.of("src/main/resources/sql/data.sql"));
            statement.execute(dataSql);

        } catch (Exception e) {
            throw new RuntimeException("Ошибка инициализации базы данных: " + e.getMessage(), e);
        }
    }

    // Остальные методы без изменений...
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