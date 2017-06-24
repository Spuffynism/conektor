package com.springmvc.service.database_util;

import com.springmvc.model.Datable;
import org.hibernate.Query;

import java.util.Date;
import java.util.List;

/**
 * This class Contains basic CRUD operations that can be made on T entities which are mapped to
 * tables by their respectives services (ex.: TService<T>).
 *
 * @param <T> the object that is managed by its service which extends this class
 */
public abstract class AbstractService<T> {
    private final Class<T> tClass;

    protected AbstractService(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * TODO
     * Get a T by its id
     *
     * @param id the T's id
     * @return the T
     */
    @SuppressWarnings(value = "unchecked")
    public T get(int id) {
        return new QueryExecutor<>(session -> {
            return (T) session.get(tClass, id);
        }).execute();
    }

    /**
     * TODO
     * Gets a T's creation date
     *
     * @param id the T's id
     * @return the T's creation date
     */
    public Date getDateCreated(int id) {
        return (Date) getSinglePropertyById("dateCreated", id);
    }

    /**
     * Gets a T's last modification date
     *
     * @param id the T's id
     * @return the T's last modification date
     */
    public Date getDateModified(int id) {
        return (Date) getSinglePropertyById("dateModified", id);
    }

    private void checkIfDatable() throws ClassCastException {
        if (!Datable.class.isAssignableFrom(tClass)) {
            throw new ClassCastException(tClass.getName() + " is not assignable from " +
                    Datable.class.getName());
        }
    }

    public Object getSinglePropertyById(String propertyName, int id) {
        return new QueryExecutor<>(session -> {
            String hql = String.format("select %s from %s where id = :id",
                    propertyName, tClass.getSimpleName());
            Query query = session.createQuery(hql);
            query.setParameter("id", id);

            return session.get(tClass, id);
        }).execute();
    }

    /**
     * Gets all the Ts
     *
     * @return a T List
     */
    @SuppressWarnings(value = "unchecked")
    public List<T> getAll() {
        return new QueryExecutor<>(session -> {
            return (List<T>) session.createQuery("from " + tClass.getSimpleName()).list();
        }).execute();
    }

    /**
     * Adds a T
     *
     * @param t le T to add
     * @return the added T
     */
    public T add(T t) {
        return new QueryExecutor<>(session -> {
            session.beginTransaction();
            session.save(t);
            session.getTransaction().commit();

            return t;
        }).execute();
    }

    /**
     * Updates a T
     *
     * @param t the T to update
     * @return the updated T
     */
    public T update(T t) {
        return new QueryExecutor<>(session -> {
            session.beginTransaction();
            session.update(t);
            session.getTransaction().commit();

            return t;
        }).execute();
    }

    /**
     * Deletes a T by its id
     *
     * @param id the T's id to delete
     */
    @SuppressWarnings(value = "unchecked")
    public void delete(int id) {
        new QueryExecutor<Void>(session -> {
            T t = (T) session.load(tClass, id);

            session.beginTransaction();
            session.delete(t);
            session.getTransaction().commit();

            return null;
        }).execute();
    }

    /**
     * Checks if a T exists by its id
     *
     * @param id the T's id
     * @return if the T exists or not
     */
    public Boolean exists(int id) {
        return new QueryExecutor<>(session -> get(id) != null).execute();
    }
}