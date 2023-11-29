package jm.task.core.jdbc.util;


import jm.task.core.jdbc.model.User;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;
import org.hibernate.service.ServiceRegistry;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

import static com.mysql.cj.conf.PropertyKey.USER;
import static org.hibernate.cfg.AvailableSettings.PASS;


public class Util {

    public static SessionFactory sessionFactory;
    public static SessionFactory getSessionFactory() {
        if(sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties setting = new Properties();
                setting.put(Environment.DRIVER, "com.mysql.jdbc.Driver");
                setting.put(Environment.URL, "jdbc:mysql://localhost:3306/sys");
                setting.put(USER, "root");
                setting.put(PASS, "12345678");

                setting.put(Environment.SHOW_SQL, "true");
                setting.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                setting.put(Environment.HBM2DDL_AUTO, "create-drop");

                configuration.setProperties(setting);
                configuration.addAnnotatedClass(User.class);

                ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
                        .applySettings(configuration.getProperties()).build();
                sessionFactory = configuration.buildSessionFactory(serviceRegistry);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } return sessionFactory;
    }


    private static final String USERNAME = "root";
    private static final String PASSWORD = "12345678";
    private static final String URL = "jdbc:mysql://localhost:3306/sys";
    public static Connection connection;



    public static Connection getConnection() {
        Connection useConnection = connection;
        try {
            if (useConnection == null) {
                synchronized (Connection.class) {
                    useConnection = connection;
                    if (useConnection == null) {
                        connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
                        useConnection = connection;
                        System.out.println("Соединение произошло");
                    }
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("Ошибка соединения");
        }
        return useConnection;

    }


}
