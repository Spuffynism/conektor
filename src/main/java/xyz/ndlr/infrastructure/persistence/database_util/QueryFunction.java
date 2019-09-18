package xyz.ndlr.infrastructure.persistence.database_util;

import org.hibernate.Session;

/**
 * Callbacks used by QueryExecutor
 *
 * @param <T> the queried element
 */
@FunctionalInterface
public interface QueryFunction<T> {
    T apply(Session session);
}
