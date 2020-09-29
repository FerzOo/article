package ir.maktab39.base.repository;

import ir.maktab39.Session;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collector;
import java.util.stream.Collectors;

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
        try {
            EntityManager entityManager = getEntityManager();
            if (entityManager.isJoinedToTransaction())
                entityManager.persist(e);
            else {
                startTransaction();
                entityManager.persist(e);
                commit();
            }
        } catch (Exception e1) {
            rollback();
        }

    }

    @Override
    public E find(PK id) {
        return getEntityManager().find(getEntityClass(), id);
    }

    @Override
    public void removeById(PK id) {
        try {
            EntityManager entityManager = getEntityManager();
            if (entityManager.isJoinedToTransaction())
                removeById0(id);
            else {
                startTransaction();
                removeById0(id);
                commit();
            }
        } catch (Exception e) {
            rollback();
        }
    }

    private void removeById0(PK id) {
        Query query = getEntityManager().createQuery
                ("delete from " + getEntityClass().getSimpleName()
                        + " o where o.id=:id");
        query.setParameter("id", id);
        query.executeUpdate();
    }

    @Override
    public void update(E e) {
        try {
            EntityManager entityManager = getEntityManager();
            if (entityManager.isJoinedToTransaction())
                entityManager.merge(e);
            else {
                startTransaction();
                entityManager.merge(e);
                commit();
            }
        } catch (Exception e1) {
            rollback();
        }
    }

    @Override
    public List<E> findAll() {
        return findAll0();
    }

    @Override
    public List<E> findAll(Predicate<E> predicate) {
        List<E> list = findAll0();
        return list.stream().filter(predicate)
                .collect(Collectors.toList());
    }

    @Override
    public List findAll(Function<E, ?> function) {
        List<E> list = findAll0();
        return list.stream().map(function)
                .collect(Collectors.toList());
    }

    private List<E> findAll0() {
        return getEntityManager().createQuery
                ("select o from " + getEntityClass().getSimpleName() + " o")
                .getResultList();
    }
}
