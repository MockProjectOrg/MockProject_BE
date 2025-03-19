package org.example.bookingbe.config;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
@WebListener
public class SessionConfig implements ServletContextListener {

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        sce.getServletContext().setSessionTimeout(30);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}