package ir.maktab39;

import ir.maktab39.entities.User;

import javax.persistence.EntityManager;

public class Session {

    private static ThreadLocal<User> userThreadLocal = new ThreadLocal<>();
    private static ThreadLocal<EntityManager> entityManagerThreadLocal =
            new ThreadLocal<>();
    private static ThreadLocal<EntityManager> entityManagerThreadLocal2 =
            new ThreadLocal<>();

    public static void destroy(){
        closeAndRemoveEntityManagers();
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

    private static void closeAndRemoveEntityManagers(){
        entityManagerThreadLocal.get().close();
        entityManagerThreadLocal.remove();
        entityManagerThreadLocal2.get().close();
        entityManagerThreadLocal2.remove();
    }
    public static EntityManager getEntityManager2(){
        return entityManagerThreadLocal2.get();
    }

    public static void setEntityManager2(EntityManager entityManager){
        entityManagerThreadLocal2.set(entityManager);
    }
}
