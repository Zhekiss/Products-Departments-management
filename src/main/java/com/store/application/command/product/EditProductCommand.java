package com.store.application.command.product;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.ProductMenu;
import java.math.BigDecimal;

public class EditProductCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        try {
            System.out.println("\n--- РЕДАКТИРОВАНИЕ ТОВАРА ---");

            System.out.print("Введите ID товара для редактирования: ");
            Long productId = Long.parseLong(consoleApp.getScanner().nextLine());

            System.out.print("Введите новое название товара: ");
            String name = consoleApp.getScanner().nextLine();

            System.out.print("Введите новую цену товара: ");
            BigDecimal price = new BigDecimal(consoleApp.getScanner().nextLine());

            System.out.print("Введите новый ID отдела: ");
            Long departmentId = Long.parseLong(consoleApp.getScanner().nextLine());

            boolean success = consoleApp.getProductService().updateProduct(productId, name, price, departmentId);
            if (success) {
                System.out.println("Товар успешно обновлен.");
            } else {
                System.out.println("Товар с указанным ID не найден или отдел не существует.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат числа.");
        } catch (Exception e) {
            System.out.println("Ошибка при редактировании товара: " + e.getMessage());
        }
        return new ProductMenu();
    }
}