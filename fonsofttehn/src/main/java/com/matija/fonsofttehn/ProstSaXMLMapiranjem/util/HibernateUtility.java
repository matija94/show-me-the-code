package com.matija.fonsofttehn.ProstSaXMLMapiranjem.util;

import org.hibernate.*;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;

public class HibernateUtility {

    private static final SessionFactory sessionFactory;

    static {
        try {
            // Kreira SessionFactory na osnovu hibernate.cfg.xml
            StandardServiceRegistry standardRegistry = new StandardServiceRegistryBuilder().configure("hibernate.cfg.xml").build();
            Metadata metaData = new MetadataSources(standardRegistry).getMetadataBuilder().build();
            sessionFactory = metaData.getSessionFactoryBuilder().build();
        } catch (Throwable ex) {
            System.err.println("Inicijalno kreiranje SessionFactory nije uspjelo! " + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
