package ir.maktab39.repositories.article;

import ir.maktab39.Session;
import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Category;
import ir.maktab39.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;
import java.util.stream.Collectors;

public class ArticleRepoImpl extends BaseRepositoryImpl<Long, Article> implements ArticleRepo {

    protected EntityManager getEntityManager2() {
        return Session.getEntityManager2();
    }

    @Override
    public Class<Article> getEntityClass() {
        return Article.class;
    }

    @Override
    public void commit2() {
        getEntityManager2().getTransaction().commit();
    }

    @Override
    public void rollback2() {
        getEntityManager2().getTransaction().rollback();
    }

    @Override
    public List<Article> findUserArticles(User user) {
        List<Article> articles = getEntityManager()
                .createNamedQuery("select * from article where article.author.id=:id")
                .setParameter("id", user.getId())
                .getResultList();
        List<Long> articleIds = articles.stream()
                .map(Article::get)
                .collect(Collectors.toList());
        List<Category> categories = getEntityManager2()
                .createQuery("select o from Category o where o.id in :ids")
                .setParameter("ids",articleIds)
                .getResultList();
    }

    @Override
    public List<Article> findArticlesDependsOnPublication(boolean isPublished) {
        return getEntityManager()
                .createQuery("select o from Article o where o.isPublished" +
                        "=:isPublished ")
                .setParameter("isPublished", isPublished)
                .getResultList();
    }

    @Override
    public void save(Article article) {
        try {
            EntityManager entityManager = getEntityManager();
            EntityManager entityManager2 = getEntityManager2();
            Category category = article.getCategory();
            if (entityManager2.isJoinedToTransaction()) {
                entityManager2.persist(category);
                entityManager2.flush();
            } else {
                startTransaction();
                entityManager2.persist(category);
                entityManager2.flush();
            }
            if (entityManager.isJoinedToTransaction()) {
                entityManager.persist(article);
                entityManager.createNativeQuery("UPDATE article set " +
                        "article.category_id=? WHERE article.id=?")
                        .setParameter(1, category.getId())
                        .setParameter(2, article.getId())
                        .executeUpdate();
            } else {
                startTransaction();
                entityManager.persist(article);
                entityManager.createNativeQuery("UPDATE article set " +
                        "article.category_id=? WHERE article.id=?")
                        .setParameter(1, category.getId())
                        .setParameter(2, article.getId())
                        .executeUpdate();
            }
            commit();//must be a atomic with commit2()
            commit2();
        } catch (Exception e) {
            rollback();
            rollback2();
        }
    }

    @Override
    public void update(Article article) {
        try {
            EntityManager entityManager = getEntityManager();
            EntityManager entityManager2 = getEntityManager2();
            Category category = article.getCategory();
            if (entityManager2.isJoinedToTransaction()) {
                entityManager2.merge(category);
                entityManager2.flush();
            } else {
                startTransaction();
                entityManager2.merge(category);
                entityManager2.flush();
            }
            if (entityManager.isJoinedToTransaction()) {
                entityManager.merge(article);
                entityManager.createNativeQuery("UPDATE article set " +
                        "article.category_id=? WHERE article.id=?")
                        .setParameter(1, category.getId())
                        .setParameter(2, article.getId())
                        .executeUpdate();
            } else {
                startTransaction();
                entityManager.merge(article);
                entityManager.createNativeQuery("UPDATE article set " +
                        "article.category_id=? WHERE article.id=?")
                        .setParameter(1, category.getId())
                        .setParameter(2, article.getId())
                        .executeUpdate();
            }
            commit();//must be a atomic with commit2()
            commit2();
        } catch (Exception e) {
            rollback();
            rollback2();
        }
    }
}
