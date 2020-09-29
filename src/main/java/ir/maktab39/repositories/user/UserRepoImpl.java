package ir.maktab39.repositories.user;

import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.User;
import ir.maktab39.exceptions.UniqueConstraintException;

import javax.jws.soap.SOAPBinding;
import javax.persistence.EntityManager;
import javax.persistence.Query;

public class UserRepoImpl extends BaseRepositoryImpl<Long, User> implements UserRepo {


    @Override
    public Class<User> getEntityClass() {
        return User.class;
    }

    public User getUser(String username, String password) {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select o from " +
                getEntityClass().getSimpleName() + " o where o.username=:u and " +
                "o.password=:p").setParameter("u", username).
                setParameter("p", password);
        return (User) query.getSingleResult();
    }

}
