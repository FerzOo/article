package ir.maktab39.ui.userUI;

import ir.maktab39.ErrorHandler;
import ir.maktab39.Repository;
import ir.maktab39.Session;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Category;

import java.sql.SQLException;
import java.util.*;

public class WriterUI extends BaseUI{
    private Repository repository = Repository.getInstance();

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
            repository.updateArticle(article);
        }
        System.out.println("edit done!");
    }


    private Map<Long, Category> showCategories() throws SQLException {
        Map<Long, Category> categories = repository.getCategories();
        System.out.println("categories:");
        Set<Map.Entry<Long, Category>> entries = categories.entrySet();
        for (Map.Entry<Long, Category> entry : entries) {
            System.out.println(entry.getValue());
            System.out.println("------------");
        }
        return categories;
    }

    private void insertArticle() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        showCategories();
        System.out.println("1)choose an category");
        System.out.println("2)enter new category");
        int command = scanner.nextInt();
        if (command == 2) {
            System.out.println("category title:");
            String title = scanner.nextLine();
            System.out.println("category description:");
            scanner.nextLine();
            String description = scanner.nextLine();
            Category category = new Category();
            category.setTitle(title);
            category.setDescription(description);
            repository.insertCategory(category);
            System.out.println("category inserted");
        }
        Map<Long, Category> categories = showCategories();
        System.out.println("choose category id");
        long id = scanner.nextLong();
        Category category = categories.get(id);
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
        repository.insertArticle(article);
        System.out.println("article inserted!");
    }

    public List<Article> viewArticles() throws SQLException {
        List<Article> list = repository.getUserArticles(
                Session.getUser());
        for (Article article : list) {
            System.out.println("---------------");
            System.out.println(article);
        }
        System.out.println("*****************");
        return list;
    }


}
