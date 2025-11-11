package com.store.application.command.department;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.DepartmentMenu;

public class SearchDepartmentsCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        System.out.println("\n--- ПОИСК ОТДЕЛА ПО НАЗВАНИЮ ---");

        System.out.print("Введите название отдела для поиска: ");
        String name = consoleApp.getScanner().nextLine();

        var departments = consoleApp.getDepartmentService().searchDepartmentsByName(name);
        if (departments.isEmpty()) {
            System.out.println("Отделы с таким названием не найдены.");
        } else {
            System.out.println("\n--- РЕЗУЛЬТАТЫ ПОИСКА ---");
            System.out.printf("%-5s %-20s %-15s%n", "ID", "Название", "Часы работы");
            System.out.println("---------------------------------------------");
            for (var department : departments) {
                System.out.printf("%-5d %-20s %-15s%n",
                        department.getDepartmentId(),
                        department.getName(),
                        department.getWorkingHours());
            }
        }
        return new DepartmentMenu();
    }
}