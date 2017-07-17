package com.springmvc.service.database_util;

import org.hibernate.Session;

/**
 * Interface fonctionnel définissant les callback utilisé par QueryExecutor
 *
 * @param <T>
 */
@FunctionalInterface
public interface QueryFunction<T> {
    T apply(Session session);
}
