package ir.maktab39.repositories.role;

import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.Role;

import javax.persistence.EntityManager;
import javax.persistence.Query;

public class RoleRepoImpl extends BaseRepositoryImpl<Long, Role> implements RoleRepo {

    @Override
    public Class<Role> getEntityClass() {
        return Role.class;
    }

    @Override
    public Role getRole(String title) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select o from "
                + getEntityClass().getSimpleName() + " o where o.title=:t")
                .setParameter("t", title);
        return (Role) query.getSingleResult();
    }

}
