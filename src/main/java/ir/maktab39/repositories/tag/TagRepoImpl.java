package ir.maktab39.repositories.tag;


import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.Tag;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class TagRepoImpl extends BaseRepositoryImpl<Long, Tag> implements TagRepo {

    public TagRepoImpl(Class<Tag> entityClass) {
        super(entityClass);
    }

    @Override
    public Tag getTag(String title) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select o from" +
                entityClass.getSimpleName() + " o where o.title=:t").setParameter("t", title);
        return (Tag) query.getSingleResult();
    }
}
