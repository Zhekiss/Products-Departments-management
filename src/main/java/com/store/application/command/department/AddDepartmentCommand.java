package com.store.application.command.department;

import com.store.application.command.Command;
import com.store.application.console.ConsoleApplication;
import com.store.application.menu.Menu;
import com.store.application.menu.impl.DepartmentMenu;

public class AddDepartmentCommand implements Command {
    @Override
    public Menu execute(ConsoleApplication consoleApp) {
        try {
            System.out.println("\n--- ДОБАВЛЕНИЕ ОТДЕЛА ---");

            System.out.print("Введите название отдела: ");
            String name = consoleApp.getScanner().nextLine();

            System.out.print("Введите часы работы (например, 9:00-21:00): ");
            String workingHours = consoleApp.getScanner().nextLine();

            var department = consoleApp.getDepartmentService().createDepartment(name, workingHours);
            System.out.printf("Отдел '%s' успешно добавлен с ID: %d%n",
                    department.getName(), department.getDepartmentId());

        } catch (Exception e) {
            System.out.println("Ошибка при добавлении отдела: " + e.getMessage());
        }
        return new DepartmentMenu();
    }
}