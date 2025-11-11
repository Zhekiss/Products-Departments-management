package com.store.application.command.product;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.ProductMenu;

public class ShowProductsByDepartmentCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        try {
            System.out.println("\n--- ТОВАРЫ ПО ОТДЕЛУ ---");

            System.out.print("Введите ID отдела: ");
            Long departmentId = Long.parseLong(consoleApp.getScanner().nextLine());

            var products = consoleApp.getProductService().getProductsByDepartment(departmentId);
            if (products.isEmpty()) {
                System.out.println("В данном отделе товары не найдены.");
            } else {
                System.out.printf("\n--- ТОВАРЫ В ОТДЕЛЕ %d ---%n", departmentId);
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
        return new ProductMenu();
    }
}