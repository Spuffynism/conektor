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
        Session session = null;
        T result = null;
        try {
            session = HibernateUtil.getSessionFactory().openSession();
            session.beginTransaction();

            result = queryFunction.apply(session);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            if (session != null)
                session.close();
        }

        return result;
    }

    private void setQueryFunction(QueryFunction<T> queryFunction) {
        this.queryFunction = queryFunction;
    }
}