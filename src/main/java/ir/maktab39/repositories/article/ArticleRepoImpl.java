package ir.maktab39.repositories.article;

import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.User;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

public class ArticleRepoImpl extends BaseRepositoryImpl<Long, Article> implements ArticleRepo {

    @Override
    public Class<Article> getEntityClass() {
        return Article.class;
    }

    @Override
    public List<Article> findUserArticles(User user) {
        return getEntityManager()
                .createQuery("select o from Article o where o.author.id=:id")
                .setParameter("id", user.getId())
                .getResultList();
    }

    @Override
    public List<Article> findArticlesDependsOnPublication(boolean isPublished) {
        return getEntityManager()
                .createQuery("select o from Article o where o.isPublished" +
                        "=:isPublished ")
                .setParameter("isPublished",isPublished)
                .getResultList();
    }
}
