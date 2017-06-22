package com.springmvc.model.database_util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

/**
 * Utilitaire pour obtenir une sessionFactory pour se connecter à la BD. N'est qu'utilisé par
 * QueryExecutor
 */
class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = createSessionFactory();
        } catch (Throwable e) {
            throw new ExceptionInInitializerError(e);
        }
    }

    /**
     * Get une factory de session
     *
     * @return une factory de session
     */
    static SessionFactory getSessionFactory() {
        return sessionFactory;
    }

    /**
     * Crée la sessionFactory. Remplace le buildSessionFactory qui est désormais deprecated.
     *
     * @return la sessionFactory
     */
    private static SessionFactory createSessionFactory() {
        Configuration configuration = new Configuration();
        configuration.configure();

        ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(
                configuration.getProperties()).buildServiceRegistry();

        return configuration.buildSessionFactory(serviceRegistry);
    }
}