package com.springmvc.service.database_util;


import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.mapping.PersistentClass;

import java.util.Collection;
import java.util.Iterator;

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
        StandardServiceRegistry standardServiceRegistry = new StandardServiceRegistryBuilder()
                .configure("hibernate.cfg.xml").build();
        Metadata metadata = new MetadataSources(standardServiceRegistry)
                .getMetadataBuilder().build();

        Collection<PersistentClass> entityBindings = metadata.getEntityBindings();
        Iterator<PersistentClass> iterator = entityBindings.iterator();
        while (iterator.hasNext()) {
            PersistentClass persistentClass = iterator.next();
            System.out.println(persistentClass.getClassName() +
                    persistentClass.getMappedClass() + persistentClass.getEntityName() +
                    persistentClass.getLoaderName());
        }

        return metadata.getSessionFactoryBuilder().build();
    }
}