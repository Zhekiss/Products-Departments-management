package com.store.application.command.product;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.ProductMenu;

public class DeleteProductCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        try {
            System.out.println("\n--- УДАЛЕНИЕ ТОВАРА ---");

            System.out.print("Введите ID товара для удаления: ");
            Long productId = Long.parseLong(consoleApp.getScanner().nextLine());

            boolean success = consoleApp.getProductService().deleteProduct(productId);
            if (success) {
                System.out.println("Товар успешно удален.");
            } else {
                System.out.println("Товар с указанным ID не найден.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат ID.");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении товара: " + e.getMessage());
        }
        return new ProductMenu();
    }
}