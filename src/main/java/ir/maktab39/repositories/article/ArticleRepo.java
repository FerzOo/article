package ir.maktab39.repositories.article;

import ir.maktab39.base.repository.BaseRepository;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.User;

import java.util.List;

public interface ArticleRepo extends BaseRepository<Long, Article> {

    void startTransaction2();

    void commit2();

    void rollback2();

    List<Article> findUserArticles(User user);

    List<Article> findArticlesDependsOnPublication(boolean isPublished);
}
