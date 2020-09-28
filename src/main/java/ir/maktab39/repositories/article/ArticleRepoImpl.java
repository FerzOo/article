package ir.maktab39.repositories.article;

import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.Article;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ArticleRepoImpl extends BaseRepositoryImpl<Long, Article> implements ArticleRepo {

    public ArticleRepoImpl(Class<Article> entityClass) {
        super(entityClass);
    }

    @Override
    public List<Article> getArticles() {
        EntityManager entityManager = getEntityManager();
        Query query = entityManager.createQuery("select o from " +
                entityClass.getSimpleName() + " o ");
        return query.getResultList();
    }
}
