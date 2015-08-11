package com.priadko.arduino.util;

import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {

    private static final SessionFactory sessionFactory = createSessionFactory();
    private static StandardServiceRegistry serviceRegistry;

  public static SessionFactory createSessionFactory() {
    Configuration configuration = new Configuration();
    configuration.configure();
    serviceRegistry = new StandardServiceRegistryBuilder().applySettings(
      configuration.getProperties()).build();
    return configuration.buildSessionFactory(serviceRegistry);
  }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    public static void shutdown() {
        // Close caches and connection pools
        getSessionFactory().close();
    }

}
