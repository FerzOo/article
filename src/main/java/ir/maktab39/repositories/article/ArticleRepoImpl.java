package ir.maktab39.repositories.article;

import ir.maktab39.Session;
import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.dto.ArticleSearchDto;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Category;
import ir.maktab39.entities.User;

import javax.persistence.*;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class ArticleRepoImpl extends BaseRepositoryImpl<Long, Article> implements ArticleRepo {


    protected EntityManager getEntityManager2() {
        return Session.getEntityManager2();
    }

    @Override
    protected EntityManager getEntityManager() {
        return Session.getEntityManager();
    }

    @Override
    public Class<Article> getEntityClass() {
        return Article.class;
    }

    @Override
    public void startTransaction2() {
        getEntityManager2().getTransaction().begin();
    }

    @Override
    public void commit2() {
        getEntityManager2().getTransaction().commit();
    }

    @Override
    public void rollback2() {
        getEntityManager2().getTransaction().rollback();
    }

//    public List<Article> search(ArticleSearchDto dto) {
//        CriteriaBuilder cb = getEntityManager().getCriteriaBuilder();
//        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
//        Root<Article> root = cq.from(Article.class);
//
//    }
//
//    private List<Predicate> setWhereClauses(ArticleSearchDto dto,
//                                            List<Predicate> prList,
//                                            CriteriaBuilder cb,
//                                            Root<Article> root) {
//        setStringPredicateToPrList("title",dto.getTitle(), prList, cb, root);
//        setStringPredicateToPrList("brief",dto.getTitle(), prList, cb, root);
//        setStringPredicateToPrList("content",dto.getTitle(), prList, cb, root);
//    }

    private void setStringPredicateToPrList(String fieldName, String value,
                                   List<Predicate> prList, CriteriaBuilder cb,
                                   Root<Article> root) {
        if (value == null || value.isEmpty())
            return;
        prList.add(
                cb.like(root.get(fieldName), "%" + value.trim() + "%")
        );
    }

    private void setBooleanPredicateToPrList(String fieldName, Boolean flag,
                                   List<Predicate> prList, CriteriaBuilder cb,
                                   Root<Article> root) {
        if (flag == null)
            return;
        prList.add(
                cb.equal(root.get(fieldName), flag)
        );
    }


    @Override
    public List<Article> findUserArticles(User user) {
        List<Article> articles = getEntityManager()
                .createQuery("select o from Article o where o.author.id=:id")
                .setParameter("id", user.getId())
                .getResultList();
        return setAndReturnArticlesCategories(articles);
    }

    @Override
    public List<Article> findArticlesDependsOnPublication(boolean isPublished) {
        List<Article> articles = getEntityManager()
                .createQuery("select o from Article o where o.isPublished" +
                        "=:isPublished ")
                .setParameter("isPublished", isPublished)
                .getResultList();
        return setAndReturnArticlesCategories(articles);
    }

    private List<Article> setAndReturnArticlesCategories(List<Article> articles) {
        if (articles.size() == 0)
            return new ArrayList<>();
        List<Long> categoryIds = articles.stream()
                .map(Article::getCategoryId)
                .collect(Collectors.toList());

        List<Category> categories = getEntityManager2()
                .createQuery("select o from Category o where o.id in :ids")
                .setParameter("ids", categoryIds)
                .getResultList();
        Map<Long, Category> categoryMap = categories.stream()
                .collect(Collectors.toMap(Category::getId
                        , Function.identity()));

        articles.forEach((a) -> a.setCategory(categoryMap.get(a.getCategoryId())));
        return articles;
    }


    @Override
    public void save(Article article) {
        try {
            EntityManager entityManager = getEntityManager();
            EntityManager entityManager2 = getEntityManager2();
            Category category = article.getCategory();
            if (entityManager2.isJoinedToTransaction()) {
                entityManager2.merge(category);
                entityManager2.flush();
            } else {
                startTransaction2();
                entityManager2.merge(category);
                entityManager2.flush();
            }
            article.setCategoryId(category.getId());
            if (entityManager.isJoinedToTransaction()) {
                entityManager.persist(article);
            } else {
                startTransaction();
                entityManager.persist(article);
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
                startTransaction2();
                entityManager2.merge(category);
                entityManager2.flush();
            }
            article.setCategoryId(category.getId());
            if (entityManager.isJoinedToTransaction()) {
                entityManager.merge(article);
            } else {
                startTransaction();
                entityManager.merge(article);
            }
            commit();//must be a atomic with commit2()
            commit2();
        } catch (Exception e) {
            rollback();
            rollback2();
        }
    }
}
