package xyz.ndlr.service.database_util;


import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import xyz.ndlr.model.entity.Account;

/**
 * Utilitaire pour obtenir une sessionFactory pour se connecter à la BD. N'est qu'utilisé par
 * QueryExecutor
 */
class HibernateUtil {
    private static final SessionFactory sessionFactory;

    static {
        try {
            sessionFactory = createSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Is the database service started?\n" + e);
        }
    }

    private static SessionFactory createSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}