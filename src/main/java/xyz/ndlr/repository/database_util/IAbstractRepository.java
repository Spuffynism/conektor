package xyz.ndlr.repository.database_util;

import xyz.ndlr.domain.Limit;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

public interface IAbstractRepository<T> {
    /**
     * Get a T by its id
     *
     * @param id the T's id
     * @return the T
     */
    T get(Serializable id);

    /**
     * Gets a T's creation date
     *
     * @param id the T's id
     * @return the T's creation date
     */
    Date getDateCreated(Serializable id);

    /**
     * Gets a T's last modification date
     *
     * @param id the T's id
     * @return the T's last modification date
     */
    Date getDateModified(Serializable id);

    Object getPropertyById(String propertyName, Serializable id);

    /**
     * Gets all the Ts
     *
     * @return a T List
     */
    @SuppressWarnings(value = "unchecked")
    List<T> getAll(Limit limit);

    /**
     * Adds a T
     *
     * @param t le T to add
     * @return the added T
     */
    T add(T t);

    /**
     * Updates a T
     *
     * @param t the T to update
     * @return the updated T
     */
    T update(T t);

    /**
     * Deletes a T by its id
     *
     * @param id the T's id to delete
     */
    void delete(Serializable id);

    /**
     * Checks if a T exists by its id
     *
     * @param id the T's id
     * @return if the T exists or not
     */
    Boolean exists(Serializable id);
}
