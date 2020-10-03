package ir.maktab39.repositories.article;

import ir.maktab39.Session;
import ir.maktab39.base.repository.BaseRepositoryImpl;
import ir.maktab39.dto.ArticleSearchDto;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Article_;
import ir.maktab39.entities.Category;
import ir.maktab39.entities.User;

import javax.persistence.*;
import javax.persistence.criteria.*;
import javax.persistence.metamodel.SingularAttribute;
import java.util.ArrayList;
import java.util.HashMap;
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

    public List<Article> search(ArticleSearchDto dto) {
        EntityManager em = getEntityManager();
        CriteriaBuilder cb = em.getCriteriaBuilder();
        CriteriaQuery<Article> cq = cb.createQuery(Article.class);
        Root<Article> root = cq.from(Article.class);
        List<Predicate> predicates = new ArrayList<>();
        Map<ParameterExpression, Object> parValMp = new HashMap<>();
        setWhereClauses(dto, predicates, parValMp, cb, root);
        cq.where(cb.and(predicates.toArray(new Predicate[0])));
        TypedQuery<Article> query = em.createQuery(cq);
        for (Map.Entry<ParameterExpression, Object> entry : parValMp.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }
        return query.getResultList();
    }

    private void setWhereClauses
            (ArticleSearchDto dto,
             List<Predicate> prList,
             Map<ParameterExpression, Object> parValMp,
             CriteriaBuilder cb,
             Root<Article> root) {
        setStringPredicateToPrList
                (Article_.title, dto.getTitle(), prList, parValMp, cb, root);
        setStringPredicateToPrList
                (Article_.brief, dto.getBrief(), prList, parValMp, cb, root);
        setStringPredicateToPrList
                (Article_.content, dto.getContent(), prList, parValMp, cb, root);
        setBooleanPredicateToPrList
                (Article_.isPublished, dto.isPublished(), prList, parValMp, cb, root);

    }

    private void setStringPredicateToPrList
            (SingularAttribute<Article, String> fieldName, String value,
             List<Predicate> prList, Map<ParameterExpression, Object> parValMp,
             CriteriaBuilder cb, Root<Article> root) {
        if (value == null || value.isEmpty())
            return;
        ParameterExpression<String> param = cb.parameter(String.class);
        parValMp.put(param, "%" + value + "%");
        prList.add(
                cb.like(root.get(fieldName), param)
        );
    }

    private void setBooleanPredicateToPrList
            (SingularAttribute<Article, Boolean> fieldName, Boolean flag,
             List<Predicate> prList, Map<ParameterExpression, Object> parValMp,
             CriteriaBuilder cb, Root<Article> root) {
        if (flag == null)
            return;
        ParameterExpression<Boolean> param = cb.parameter(Boolean.class);
        parValMp.put(param, flag);
        prList.add(
                cb.equal(root.get(fieldName), param)
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
