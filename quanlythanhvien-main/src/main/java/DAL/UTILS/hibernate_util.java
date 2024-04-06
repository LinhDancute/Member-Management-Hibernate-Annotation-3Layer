/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package DAL.UTILS;

import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 *
 * @author ACER
 */
public class hibernate_util {
//    private EntityManagerFactory factory;
    private static final SessionFactory SESSION_FACTORY;

//    public hibernate_util(){
//        this.factory = Persistence.createEntityManagerFactory("quanlythanhvien-main");
//    }

    static {
        Configuration configuration = new Configuration();
        configuration.configure("hibernate.cfg.xml");
        SESSION_FACTORY = configuration.buildSessionFactory();
    }
    public static SessionFactory getSessionFactory(){
        return SESSION_FACTORY;
    }
    public static void main(String args[]) {
        getSessionFactory();
    }
}
