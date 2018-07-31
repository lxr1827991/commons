package com.lxr.commons.web;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class ServerStartupListener implements ServletContextListener {

    public void contextInitialized(ServletContextEvent sce) {
        // 将web应用名称（路径）保存到application范围中
        ServletContext application = sce.getServletContext();
        String path = application.getContextPath();
      WebContext.contextPath = path;
    }

    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
}
