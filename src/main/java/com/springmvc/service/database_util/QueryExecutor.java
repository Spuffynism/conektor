package com.springmvc.service.database_util;

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
     * - Gets a session
     * - Starts a transaction
     * - Executes the QueryFunction
     * - Closes the sessions
     *
     * @return le résultat T de la QueryFunction
     */
    public T execute() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        return queryFunction.apply(session);
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