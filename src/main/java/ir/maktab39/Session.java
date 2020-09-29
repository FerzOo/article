package ir.maktab39;

import ir.maktab39.entities.User;

import javax.persistence.EntityManager;

public class Session {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<EntityManager> entityManagerThreadLocal =
            new ThreadLocal<>();

    public static void destroy(){
        closeAndRemoveEntityManager();
        removeUser();
    }

    public static User getUser(){
        return userThreadLocal.get();
    }

    public static void setUser(User user){
        userThreadLocal.set(user);
    }

    private static void removeUser(){
        userThreadLocal.remove();
    }

    public static EntityManager getEntityManager(){
        return entityManagerThreadLocal.get();
    }

    public static void setEntityManager(EntityManager entityManager){
        entityManagerThreadLocal.set(entityManager);
    }

    private static void closeAndRemoveEntityManager(){
        entityManagerThreadLocal.get().close();
        entityManagerThreadLocal.remove();
    }

}
