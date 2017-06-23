package com.springmvc.service.database_util;

import java.util.Date;
import java.util.List;

/**
 * Contient les opérations CRUD de base pouvant être faites sur des T liés à une table dans la
 * bd par leurs managers respectifs
 *
 * @param <T> l'objet managé par le service qui extend cette classe
 */
public abstract class AbstractService<T> {
    private final Class<T> tClass;

    protected AbstractService(Class<T> tClass) {
        this.tClass = tClass;
    }

    /**
     * Get un T par son id
     *
     * @param id l'id du T
     * @return le T
     */
    @SuppressWarnings(value = "unchecked")
    public T get(int id) {
        return new QueryExecutor<>(session -> {
            return (T) session.get(tClass, id);
        }).execute();
    }

    /**
     * TODO Faire ça
     * @param id
     * @return
     */
    public Date getDateCreate(int id) {
        return new QueryExecutor<>(session -> {
            return (Date) session.get(tClass, id);
        }).execute();
    }

    public Date getDateCreated(int id) {
        return new QueryExecutor<>(session -> {
            return (Date) session.get(tClass, id);
        }).execute();
    }

    /**
     * Get tous les T
     *
     * @return une List de T
     */
    @SuppressWarnings(value = "unchecked")
    public List<T> getAll() {
        return new QueryExecutor<>(session -> {
            return (List<T>) session.createQuery("from " + tClass.getSimpleName()).list();
        }).execute();
    }

    /**
     * Ajoute un T
     *
     * @param t le T à ajouter
     * @return le T ajouté
     */
    public T ajouter(T t) {
        return new QueryExecutor<>(session -> {
            session.save(t);
            session.getTransaction().commit();

            return t;
        }).execute();
    }

    /**
     * Modifie un T
     *
     * @param t le T à modifier
     * @return le T modifié
     */
    public T modifier(T t) {
        return new QueryExecutor<>(session -> {
            session.update(t);
            session.getTransaction().commit();

            return t;
        }).execute();
    }

    /**
     * Supprime un T par son id
     *
     * @param id l'identifiant du T à supprimer
     */
    @SuppressWarnings(value = "unchecked")
    public void supprimer(int id) {
        new QueryExecutor<Void>(session -> {
            T t = (T) session.load(tClass, id);
            session.delete(t);

            session.getTransaction().commit();

            return null;
        }).execute();
    }

    /**
     * Vérifie si un T existe par son id
     *
     * @param id l'identifiant du T
     * @return si le T existe ou non
     */
    public Boolean existe(int id) {
        return new QueryExecutor<>(session -> get(id) != null).execute();
    }
}