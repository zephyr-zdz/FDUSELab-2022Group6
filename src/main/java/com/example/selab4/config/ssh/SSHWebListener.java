package com.example.selab4.config.ssh;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class SSHWebListener implements ServletContextListener {

    private SSHConnection conexionssh;

    public SSHWebListener() {
        super();
    }
    public void contextInitialized(ServletContextEvent arg0) {
        System.out.println("SSHWebListener initialized ... !");
        try {
            conexionssh = new SSHConnection();
        } catch (Throwable e) {
            e.printStackTrace();
        }
    }
    public void contextDestroyed(ServletContextEvent arg0) {
        System.out.println("SSHWebListener destroyed ... !");
        conexionssh.closeSSH();
    }
}
