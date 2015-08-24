package com.priadko.arduino.web;

import com.priadko.arduino.hardware.SerialPortListener;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

public class HardwareListener implements ServletContextListener {

    private SerialPortListener serialPortListener;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        ServletContext ctx = servletContextEvent.getServletContext();
        WebApplicationContext springContext = WebApplicationContextUtils.getWebApplicationContext(ctx);
        serialPortListener = (SerialPortListener) springContext.getBean("serialPortListener");

        serialPortListener.initialize();
    }

    @Override
    public void contextDestroyed(ServletContextEvent servletContextEvent) {
        serialPortListener.close();
    }
}
