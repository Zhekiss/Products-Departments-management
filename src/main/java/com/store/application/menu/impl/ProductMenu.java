package com.store.application.menu.impl;

import com.store.application.command.product.*;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;

public class ProductMenu implements Menu {
    @Override
    public Menu show(ConsoleApplication consoleApp) {
        System.out.println("\n=== УПРАВЛЕНИЕ ТОВАРАМИ ===");
        System.out.println("1. Показать все товары");
        System.out.println("2. Добавить товар");
        System.out.println("3. Редактировать товар");
        System.out.println("4. Удалить товар");
        System.out.println("5. Найти товар по названию");
        System.out.println("6. Показать товары по отделу");
        System.out.println("0. Назад в главное меню");
        System.out.print("Выберите опцию: ");

        String choice = consoleApp.getScanner().nextLine();

        switch (choice) {
            case "1":
                return new ShowAllProductsCommand().execute(consoleApp);
            case "2":
                return new AddProductCommand().execute(consoleApp);
            case "3":
                return new EditProductCommand().execute(consoleApp);
            case "4":
                return new DeleteProductCommand().execute(consoleApp);
            case "5":
                return new SearchProductsCommand().execute(consoleApp);
            case "6":
                return new ShowProductsByDepartmentCommand().execute(consoleApp);
            case "0":
                return new MainMenu();
            default:
                System.out.println("Неверный выбор! Попробуйте снова.");
        }
        return this;
    }
}