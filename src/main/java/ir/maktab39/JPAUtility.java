package ir.maktab39;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class JPAUtility {
    private static final EntityManagerFactory emFactory;
    private static final EntityManagerFactory emFactory2;

    static {
        emFactory = Persistence.
                createEntityManagerFactory("my-persistence-unit");
        emFactory2 = Persistence.
                createEntityManagerFactory("my-persistence-unit2");
    }

    public static EntityManager getEntityManager() {
        return emFactory.createEntityManager();
    }

    public static EntityManager getEntityManager2() {
        return emFactory2.createEntityManager();
    }

    public static void close() {
        emFactory.close();
        emFactory2.close();
    }
}
