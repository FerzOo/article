package ir.maktab39.repositories.tag;


import ir.maktab39.Session;
import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TagRepoImpl extends BaseRepositoryImpl<Long, Tag> implements TagRepo {

    @Override
    public Class<Tag> getEntityClass() {
        return Tag.class;
    }
    @Override
    protected EntityManager getEntityManager() {
        return Session.getEntityManager();
    }

    @Override
    public Tag getTag(String title) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select o from" +
                getEntityClass().getSimpleName() + " o where o.title=:t").setParameter("t", title);
        return (Tag) query.getSingleResult();
    }

}
