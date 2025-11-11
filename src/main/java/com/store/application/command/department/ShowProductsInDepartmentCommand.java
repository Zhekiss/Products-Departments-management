package com.store.application.command.department;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.DepartmentMenu;

public class ShowProductsInDepartmentCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        try {
            System.out.println("\n--- ТОВАРЫ В ОТДЕЛЕ ---");

            System.out.print("Введите ID отдела: ");
            Long departmentId = Long.parseLong(consoleApp.getScanner().nextLine());

            var departmentOpt = consoleApp.getDepartmentService().getDepartmentById(departmentId);
            if (departmentOpt.isEmpty()) {
                System.out.println("Отдел с указанным ID не найден.");
                return new DepartmentMenu();
            }

            var department = departmentOpt.get();
            var products = consoleApp.getDepartmentService().getProductsInDepartment(departmentId);

            if (products.isEmpty()) {
                System.out.printf("В отделе '%s' товары не найдены.%n", department.getName());
            } else {
                System.out.printf("\n--- ТОВАРЫ В ОТДЕЛЕ: %s ---%n", department.getName());
                System.out.printf("%-5s %-20s %-10s%n", "ID", "Название", "Цена");
                System.out.println("----------------------------------");
                for (var product : products) {
                    System.out.printf("%-5d %-20s %-10.2f%n",
                            product.getProductId(),
                            product.getName(),
                            product.getPrice());
                }
            }
        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат ID отдела.");
        } catch (Exception e) {
            System.out.println("Ошибка: " + e.getMessage());
        }
        return new DepartmentMenu();
    }
}