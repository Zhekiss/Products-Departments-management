package com.store.application.menu.impl;

import com.store.application.command.department.*;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;

public class DepartmentMenu implements Menu {
    @Override
    public Menu show(ConsoleApplication consoleApp) {
        System.out.println("\n=== УПРАВЛЕНИЕ ОТДЕЛАМИ ===");
        System.out.println("1. Показать все отделы");
        System.out.println("2. Добавить отдел");
        System.out.println("3. Редактировать отдел");
        System.out.println("4. Удалить отдел");
        System.out.println("5. Найти отдел по названию");
        System.out.println("6. Показать отделы без товаров");
        System.out.println("7. Показать товары в отделе");
        System.out.println("0. Назад в главное меню");
        System.out.print("Выберите опцию: ");

        String choice = consoleApp.getScanner().nextLine();

        switch (choice) {
            case "1":
                return new ShowAllDepartmentsCommand().execute(consoleApp);
            case "2":
                return new AddDepartmentCommand().execute(consoleApp);
            case "3":
                return new EditDepartmentCommand().execute(consoleApp);
            case "4":
                return new DeleteDepartmentCommand().execute(consoleApp);
            case "5":
                return new SearchDepartmentsCommand().execute(consoleApp);
            case "6":
                return new ShowDepartmentsWithoutProductsCommand().execute(consoleApp);
            case "7":
                return new ShowProductsInDepartmentCommand().execute(consoleApp);
            case "0":
                return new MainMenu();
            default:
                System.out.println("Неверный выбор! Попробуйте снова.");
        }
        return this;
    }
}