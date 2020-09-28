package ir.maktab39.services.article;

import ir.maktab39.base.service.BaseService;
import ir.maktab39.entities.Article;

import java.util.List;

public interface ArticleService extends BaseService<Article, Long> {
    List<Article> getArticles();
}
