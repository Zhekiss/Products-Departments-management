package com.store.application.command.department;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.DepartmentMenu;

public class DeleteDepartmentCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        try {
            System.out.println("\n--- УДАЛЕНИЕ ОТДЕЛА ---");

            System.out.print("Введите ID отдела для удаления: ");
            Long departmentId = Long.parseLong(consoleApp.getScanner().nextLine());

            boolean success = consoleApp.getDepartmentService().deleteDepartment(departmentId);
            if (success) {
                System.out.println("Отдел успешно удален.");
            } else {
                System.out.println("Не удалось удалить отдел. Возможно, в нем есть товары или отдел не найден.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат ID.");
        } catch (Exception e) {
            System.out.println("Ошибка при удалении отдела: " + e.getMessage());
        }
        return new DepartmentMenu();
    }
}