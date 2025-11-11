package com.store.application.command.product;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.ProductMenu;

public class SearchProductsCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        System.out.println("\n--- ПОИСК ТОВАРА ПО НАЗВАНИЮ ---");

        System.out.print("Введите название товара для поиска: ");
        String name = consoleApp.getScanner().nextLine();

        var products = consoleApp.getProductService().searchProductsByName(name);
        if (products.isEmpty()) {
            System.out.println("Товары с таким названием не найдены.");
        } else {
            System.out.println("\n--- РЕЗУЛЬТАТЫ ПОИСКА ---");
            System.out.printf("%-5s %-20s %-10s %-10s%n", "ID", "Название", "Цена", "ID отдела");
            System.out.println("------------------------------------------------");
            for (var product : products) {
                System.out.printf("%-5d %-20s %-10.2f %-10d%n",
                        product.getProductId(),
                        product.getName(),
                        product.getPrice(),
                        product.getDepartmentId());
            }
        }
        return new ProductMenu();
    }
}