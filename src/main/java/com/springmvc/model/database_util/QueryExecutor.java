package com.springmvc.model.database_util;

import org.hibernate.Session;

/**
 * Exécute une fonction QueryFunction
 *
 * @param <T> le type de retour T de la QueryFunction
 */
public class QueryExecutor<T> {
    private QueryFunction<T> queryFunction;

    public QueryExecutor(QueryFunction<T> queryFunction) {
        this.setQueryFunction(queryFunction);
    }

    /**
     * - Va chercher une session
     * - Débute une transaction
     * - Exécute la QueryFunction
     * - Ferme la session
     *
     * @return le résultat T de la QueryFunction
     */
    public T execute() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        session.beginTransaction();

        T t = queryFunction.apply(session);

        session.close();

        return t;
    }

    private void setQueryFunction(QueryFunction<T> queryFunction) {
        this.queryFunction = queryFunction;
    }

    /**
     * Interface fonctionnel définissant les callback utilisé par QueryExecutor
     *
     * @param <T>
     */
    @FunctionalInterface
    public interface QueryFunction<T> {
        T apply(Session session);
    }
}