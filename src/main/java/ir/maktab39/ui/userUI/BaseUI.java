package ir.maktab39.ui.userUI;

import ir.maktab39.ComponentFactory;
import ir.maktab39.Session;
import ir.maktab39.base.entity.BaseEntity;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Category;
import ir.maktab39.entities.Tag;
import ir.maktab39.entities.User;
import ir.maktab39.services.article.ArticleService;
import ir.maktab39.services.article.ArticleServiceImpl;
import ir.maktab39.services.category.CategoryService;
import ir.maktab39.services.category.CategoryServiceImpl;
import ir.maktab39.services.tag.TagService;
import ir.maktab39.services.tag.TagServiceImpl;
import ir.maktab39.services.user.UserService;
import ir.maktab39.services.user.UserServiceImpl;

import java.io.Serializable;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class BaseUI {

    private UserService userService = (UserService) ComponentFactory.
            getSingletonObject(UserServiceImpl.class);
    private ArticleService articleService = (ArticleService) ComponentFactory.
            getSingletonObject(ArticleServiceImpl.class);
    private CategoryService categoryService = (CategoryService) ComponentFactory.
            getSingletonObject(CategoryServiceImpl.class);
    private TagService tagService = (TagService) ComponentFactory.
            getSingletonObject(TagServiceImpl.class);

    protected void changePassword() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter new password");
        String password = scanner.next();
        User user = Session.getUser();
        user.setPassword(password);
        userService.update(user);
        System.out.println("password changed!");
    }

    protected List<Article> showAndReturnArticlesDependsOnPublication(boolean isPublished) {
        List<Article> articleList = articleService
                .findArticlesDependsOnPublication(isPublished);
        showEntity(articleList);
        return articleList;
    }

    public void showEntity(List list) {
        for (Object element : list) {
            System.out.println(element);
            System.out.println("----------------");
        }
    }

    //i need map for ui operations
    protected <PK extends Serializable, T extends BaseEntity<PK>> Map<PK, T> listToMap
    (List<T> list) {
        return list.stream()
                .collect(Collectors.toMap(BaseEntity::getId, Function.identity()));
    }

    protected void createCategory() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("category title:");
        String title = scanner.nextLine();
        System.out.println("category description:");
        String description = scanner.nextLine();
        Category category = new Category();
        category.setTitle(title);
        category.setDescription(description);
        categoryService.save(category);
        System.out.println("category inserted");
    }

    protected void createTag() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("tag title:");
        String title = scanner.nextLine();
        Tag tag = new Tag();
        tag.setTitle(title);
        tagService.save(tag);
        System.out.println("tag inserted");
    }
}
