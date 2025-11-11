package com.store.application.command.department;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.DepartmentMenu;

public class ShowAllDepartmentsCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        System.out.println("\n--- ВСЕ ОТДЕЛЫ ---");

        var departments = consoleApp.getDepartmentService().getAllDepartments();
        if (departments.isEmpty()) {
            System.out.println("Отделы не найдены.");
        } else {
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