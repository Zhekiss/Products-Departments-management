package com.store.web.context;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class AppContextListener implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        // Инициализируем контекст при запуске приложения
        AppContext.getInstance();
        System.out.println("AppContext инициализирован");
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        // Закрываем ресурсы при остановке приложения
        try {
            AppContext.getInstance().close();
            System.out.println("AppContext закрыт");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}