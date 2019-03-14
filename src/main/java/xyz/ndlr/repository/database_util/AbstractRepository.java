package xyz.ndlr.repository.database_util;

import org.hibernate.Query;
import xyz.ndlr.domain.AbstractDatable;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * This class Contains basic CRUD operations that can be made on T entities which are mapped to
 * tables by their respective services (ex.: TService<T>).
 *
 * @param <T> the object that is managed by its service which extends this class
 */
public abstract class AbstractRepository<T> implements IAbstractRepository<T> {
    private final Class<T> tClass;

    protected AbstractRepository(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Get a T by its id
     *
     * @param id the T's id
     * @return the T
     */
    public T get(Serializable id) {
        return new QueryExecutor<>(session -> session.get(tClass, id)).execute();
    }

    /**
     * Gets a T's creation date
     *
     * @param id the T's id
     * @return the T's creation date
     */
    public Date getDateCreated(Serializable id) {
        return (Date) getPropertyById("dateCreated", id);
    }

    /**
     * Gets a T's last modification date
     *
     * @param id the T's id
     * @return the T's last modification date
     */
    public Date getDateModified(Serializable id) {
        return (Date) getPropertyById("dateModified", id);
    }

    private void checkIfDatable() throws ClassCastException {
        if (!AbstractDatable.class.isAssignableFrom(tClass)) {
            throw new ClassCastException(tClass.getName() + " is not assignable from " +
                    AbstractDatable.class.getName());
        }
    }

    public Object getPropertyById(String propertyName, Serializable id) {
        String hql = String.format("select %s from %s where id = :id",
                propertyName, tClass.getSimpleName());

        return new QueryExecutor<>(session -> {
            Query query = session.createQuery(hql);
            query.setParameter("id", id);

            return query.uniqueResult();
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
            session.save(t);

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
            session.update(t);

            return t;
        }).execute();
    }

    /**
     * Deletes a T by its id
     *
     * @param id the T's id to delete
     */
    public void delete(Serializable id) {
        new QueryExecutor<Void>(session -> {
            T t = session.load(tClass, id);
            session.delete(t);

            return null;
        }).execute();
    }

    /**
     * Checks if a T exists by its id
     *
     * @param id the T's id
     * @return if the T exists or not
     */
    public Boolean exists(Serializable id) {
        return new QueryExecutor<>(session -> get(id) != null).execute();
    }
}