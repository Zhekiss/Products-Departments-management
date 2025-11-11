package com.store.application.menu.impl;

import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;

public class MainMenu implements Menu {
    @Override
    public Menu show(ConsoleApplication consoleApp) {
        System.out.println("\n=== ГЛАВНОЕ МЕНЮ МАГАЗИНА ===");
        System.out.println("1. Управление отделами");
        System.out.println("2. Управление товарами");
        System.out.println("0. Выход");
        System.out.print("Выберите опцию: ");

        String choice = consoleApp.getScanner().nextLine();

        switch (choice) {
            case "1":
                return new DepartmentMenu();
            case "2":
                return new ProductMenu();
            case "0":
                System.out.println("Выход из приложения...");
                System.exit(0);
            default:
                System.out.println("Неверный выбор! Попробуйте снова.");
        }
        return this;
    }
}