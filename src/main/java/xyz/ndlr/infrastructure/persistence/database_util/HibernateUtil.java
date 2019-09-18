package xyz.ndlr.infrastructure.persistence.database_util;


import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

/**
 * Hibernate utility used to get a database session. Is only used by QueryExecutor.
 */
class HibernateUtil {
    private static final Logger logger = Logger.getLogger(HibernateUtil.class);

    private static StandardServiceRegistry registry;
    private static SessionFactory sessionFactory;

    /*static {
        try {
            sessionFactory = createSessionFactory();
        } catch (Exception e) {
            throw new ExceptionInInitializerError("Is the database service started?\n" + e);
        }
    }*/

    private static SessionFactory createSessionFactory() {
        return new Configuration().configure().buildSessionFactory();
    }

    static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                registry = new StandardServiceRegistryBuilder()
                        .configure()
                        .build();
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();
                sessionFactory = metadata.getSessionFactoryBuilder().build();
            } catch (Exception e) {
                logger.error("Has MySQL been started (hint: try `start mysqld`)?", e);
                shutdown();
            }
        }

        return sessionFactory;
    }

    public static void shutdown() {
        if (registry != null)
            StandardServiceRegistryBuilder.destroy(registry);
    }
}