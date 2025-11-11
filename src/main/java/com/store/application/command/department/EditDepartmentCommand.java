package com.store.application.command.department;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.DepartmentMenu;

public class EditDepartmentCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        try {
            System.out.println("\n--- РЕДАКТИРОВАНИЕ ОТДЕЛА ---");

            System.out.print("Введите ID отдела для редактирования: ");
            Long departmentId = Long.parseLong(consoleApp.getScanner().nextLine());

            System.out.print("Введите новое название отдела: ");
            String name = consoleApp.getScanner().nextLine();

            System.out.print("Введите новые часы работы: ");
            String workingHours = consoleApp.getScanner().nextLine();

            boolean success = consoleApp.getDepartmentService().updateDepartment(departmentId, name, workingHours);
            if (success) {
                System.out.println("Отдел успешно обновлен.");
            } else {
                System.out.println("Отдел с указанным ID не найден.");
            }

        } catch (NumberFormatException e) {
            System.out.println("Ошибка: неверный формат ID.");
        } catch (Exception e) {
            System.out.println("Ошибка при редактировании отдела: " + e.getMessage());
        }
        return new DepartmentMenu();
    }
}