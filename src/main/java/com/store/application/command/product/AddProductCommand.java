package com.store.application.command.product;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.ProductMenu;
import java.math.BigDecimal;

public class AddProductCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        try {
            System.out.println("\n--- ДОБАВЛЕНИЕ ТОВАРА ---");

            System.out.print("Введите название товара: ");
            String name = consoleApp.getScanner().nextLine();

            System.out.print("Введите цену товара: ");
            BigDecimal price = new BigDecimal(consoleApp.getScanner().nextLine());

            System.out.print("Введите ID отдела: ");
            Long departmentId = Long.parseLong(consoleApp.getScanner().nextLine());

            var product = consoleApp.getProductService().createProduct(name, price, departmentId);
            System.out.printf("Товар '%s' успешно добавлен с ID: %d%n",
                    product.getName(), product.getProductId());

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат числа.");
        } catch (IllegalArgumentException e) {
            System.out.println("Ошибка: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Ошибка при добавлении товара: " + e.getMessage());
        }
        return new ProductMenu();
    }
}