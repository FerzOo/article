package ir.maktab39.services.article;

import ir.maktab39.base.service.BaseService;
import ir.maktab39.dto.ArticleSearchDto;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.User;

import java.util.List;

public interface ArticleService extends BaseService<Article, Long> {

    public List<Article> search(ArticleSearchDto dto);

    List<Article> findUserArticles(User user);

    List<Article> findArticlesDependsOnPublication(boolean isPublished);

}
