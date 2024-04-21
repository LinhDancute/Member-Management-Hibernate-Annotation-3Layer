/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package DAL.UTILS;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ACER
 */
public class hibernate_util {
    private static final SessionFactory SESSION_FACTORY;

    static {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SESSION_FACTORY = configuration.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }
    public static void main(String args[]){
        getSessionFactory();
    }
}

