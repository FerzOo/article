package ir.maktab39.base.repository;

import ir.maktab39.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;

public abstract class BaseRepositoryImpl<PK extends Serializable, E>
        implements BaseRepository<PK, E> {

    protected EntityManager getEntityManager() {
        return Session.getEntityManager();
    }

    @Override
    public void startTransaction() {
        getEntityManager().getTransaction().begin();
    }

    @Override
    public void commit() {
        getEntityManager().getTransaction().commit();
    }

    @Override
    public void rollback() {
        EntityTransaction entityTransaction = getEntityManager().getTransaction();
        entityTransaction.rollback();
    }

    @Override
    public void save(E e) {
        EntityManager entityManager = getEntityManager();
        if (entityManager.isJoinedToTransaction())
            entityManager.persist(e);
        else {
            startTransaction();
            entityManager.persist(e);
            commit();
        }
    }

    @Override
    public E find(PK id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    @Override
    public void removeById(PK id) {
        EntityManager entityManager = getEntityManager();
        if (entityManager.isJoinedToTransaction())
            removeById0(id);
        else {
            startTransaction();
            removeById0(id);
            commit();
        }
    }

    private void removeById0(PK id) {
        Query query = getEntityManager().createQuery
                ("remove o from " + getEntityClass().getSimpleName()
                        + " o where o.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(E e) {
        EntityManager entityManager = getEntityManager();
        if (entityManager.isJoinedToTransaction())
            entityManager.merge(e);
        else {
            startTransaction();
            entityManager.merge(e);
            commit();
        }
    }

    @Override
    public List<E> findAll() {
        Query query = getEntityManager().createQuery
                ("select o from " + getEntityClass().getSimpleName() + " o");
        return query.getResultList();
    }
}
