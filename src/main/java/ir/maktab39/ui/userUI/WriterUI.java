package ir.maktab39.ui.userUI;

import ir.maktab39.ComponentFactory;
import ir.maktab39.ErrorHandler;
import ir.maktab39.Session;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Category;
import ir.maktab39.services.article.ArticleService;
import ir.maktab39.services.article.ArticleServiceImpl;
import ir.maktab39.services.category.CategoryService;
import ir.maktab39.services.category.CategoryServiceImpl;

import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class WriterUI extends BaseUI {

    private ArticleService articleService = (ArticleService) ComponentFactory.
            getSingletonObject(ArticleServiceImpl.class);
    private CategoryService categoryService = (CategoryService) ComponentFactory.
            getSingletonObject(CategoryServiceImpl.class);

    public void showMenuAndGetCommand() {
        Scanner scanner = new Scanner(System.in);
        int command;
        while (true) {
            try {
                System.out.println("1)view articles");
                System.out.println("2)edit articles");
                System.out.println("3)insert article");
                System.out.println("4)change password");
                System.out.println("5)back");
                command = scanner.nextInt();
                switch (command) {
                    case 1:
                        viewArticles();
                        break;
                    case 2:
                        editArticles();
                        break;
                    case 3:
                        insertArticle();
                        break;
                    case 4:
                        changePassword();
                        break;
                    case 5:
                        return;
                }
            } catch (Exception e) {
                ErrorHandler.showMessage(e);
            }
        }
    }

    private void editArticles() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        List<Article> list = viewArticles();
        System.out.println("choose a article id");
        long id = scanner.nextLong();
        Article article = null;
        for (Article article1 : list) {
            if (article1.getId() == id) {
                article = article1;
                break;
            }
        }
        System.out.println(article);
        System.out.println("------->");
        System.out.println("are you want to publish article? y/n");
        String entry = scanner.next();
        if (entry.equals("y") && article != null) {
            article.setPublished(true);
            articleService.update(article);
        }
        System.out.println("edit done!");
    }

    private List<Category> getCategories() {
        return categoryService.findAll();
    }

    private List<Category> showAndReturnCategories() throws SQLException {
        List<Category> categories = getCategories();
        System.out.println("categories:");
        for (Category category : categories) {
            System.out.println(category);
            System.out.println("------------");
        }
        return categories;
    }

    private void insertArticle() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showAndReturnCategories();
        System.out.println("1)choose an category");
        System.out.println("2)enter new category");
        int command = scanner.nextInt();
        if (command == 2) {
            System.out.println("category title:");
            scanner.nextLine();
            String title = scanner.nextLine();
            System.out.println("category description:");
            String description = scanner.nextLine();
            Category category = new Category();
            category.setTitle(title);
            category.setDescription(description);
            categoryService.save(category);
            System.out.println("category inserted");
        }
        List<Category> categories = showAndReturnCategories();
        Map<Long, Category> categoryMap = categories.stream()
                .collect(Collectors.toMap(Category::getId, Function.identity()));
        System.out.println("choose category id");
        long id = scanner.nextLong();
        Category category = categoryMap.get(id);
        System.out.println("title:");
        String title = scanner.next();
        System.out.println("brief:");
        scanner.nextLine();
        String brief = scanner.nextLine();
        System.out.println("content:");
        String content = scanner.nextLine();
        Article article = new Article();
        article.setTitle(title);
        article.setBrief(brief);
        article.setCreateDate(new Date());
        article.setContent(content);
        article.setAuthor(Session.getUser());
        article.setCategory(category);
        article.setPublished(false);
        articleService.save(article);
        System.out.println("article inserted!");
    }

    public List<Article> viewArticles() throws SQLException {
        List<Article> list = articleService.findUserArticles(
                Session.getUser());
        for (Article article : list) {
            System.out.println("---------------");
            System.out.println(article);
        }
        System.out.println("*****************");
        return list;
    }


}
