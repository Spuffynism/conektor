package xyz.ndlr.infrastructure.persistence.database_util;

import org.hibernate.Session;

/**
 * Executes a QueryFunction
 *
 * @param <T> the queried element
 */
public class QueryExecutor<T> {
    private QueryFunction<T> queryFunction;

    public QueryExecutor(QueryFunction<T> queryFunction) {
        this.setQueryFunction(queryFunction);
    }

    /**
     * @return QueryFunction's execution result
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