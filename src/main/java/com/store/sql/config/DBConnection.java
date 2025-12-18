package com.store.sql.config;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private Connection connection;

    public DBConnection() {
        try {
            Class.forName("org.postgresql.Driver");
            this.connection = DriverManager.getConnection(
                    DBConfig.URL,
                    DBConfig.USER,
                    DBConfig.PASSWORD
            );
            initializeDatabase();
        } catch (Exception e) {
            throw new RuntimeException("Ошибка подключения к базе данных: " + e.getMessage(), e);
        }
    }

    private void initializeDatabase() {
        try (Statement stmt = connection.createStatement()) {
            // Загружаем schema.sql
            String schema = readResourceFile("schema.sql");
            stmt.execute(schema);

            // Загружаем data.sql
            String data = readResourceFile("data.sql");
            stmt.execute(data);

            System.out.println("База данных инициализирована успешно");
        } catch (Exception e) {
            System.err.println("Ошибка при инициализации базы данных: " + e.getMessage());
            // Можно продолжить, если таблицы уже существуют
        }
    }

    private String readResourceFile(String filename) throws Exception {
        StringBuilder content = new StringBuilder();
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(
                        getClass().getClassLoader().getResourceAsStream(filename),
                        StandardCharsets.UTF_8
                )
        )) {
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
        }
        return content.toString();
    }

    public Connection getConnection() {
        return connection;
    }
}