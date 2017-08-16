package xyz.ndlr.service.database_util;

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
     * @return le résultat T de la QueryFunction
     */
    public T execute() {
        Session session = HibernateUtil.getSessionFactory().openSession();

        return queryFunction.apply(session);
    }

    private void setQueryFunction(QueryFunction<T> queryFunction) {
        this.queryFunction = queryFunction;
    }
}