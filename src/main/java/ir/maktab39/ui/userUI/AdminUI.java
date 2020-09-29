package ir.maktab39.ui.userUI;

import ir.maktab39.ComponentFactory;
import ir.maktab39.ErrorHandler;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Role;
import ir.maktab39.entities.User;
import ir.maktab39.services.article.ArticleService;
import ir.maktab39.services.article.ArticleServiceImpl;
import ir.maktab39.services.role.RoleService;
import ir.maktab39.services.role.RoleServiceImpl;
import ir.maktab39.services.user.UserService;
import ir.maktab39.services.user.UserServiceImpl;
import ir.maktab39.services.user.admin.AdminService;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AdminUI extends BaseUI {

    private AdminService adminService = (AdminService) ComponentFactory.
            getSingletonObject(AdminService.class);
    private ArticleService articleService = (ArticleService) ComponentFactory.
            getSingletonObject(ArticleServiceImpl.class);
    private RoleService roleService = (RoleService) ComponentFactory.
            getSingletonObject(RoleServiceImpl.class);

    public void showMenuAndGetCommand() {
        Scanner scanner = new Scanner(System.in);
        int command;
        while (true) {
            try {
                System.out.println("1)publish Article");
                System.out.println("2)Cancel Publication");
                System.out.println("3)delete Article");
                System.out.println("4)create Category");
                System.out.println("5)create Tag");
                System.out.println("6)change users role");
                System.out.println("7)back");
                command = scanner.nextInt();
                switch (command) {
                    case 1:
                        publishOrCancelArticle(true);
                        break;
                    case 2:
                        publishOrCancelArticle(false);
                        break;
                    case 3:
                        deleteArticle();
                        break;
                    case 4:
                        createCategory();
                        break;
                    case 5:
                        createTag();
                        break;
                    case 6:
                        changeUserRoles();
                        break;
                    case 7:
                        return;
                }
            } catch (Exception e) {
                ErrorHandler.showMessage(e);
            }
        }
    }

    private void publishOrCancelArticle(boolean publish) {
        Scanner scanner = new Scanner(System.in);

        List<Article> list =
                showAndReturnArticlesDependsOnPublication(!publish);
        Map<Long, Article> map = listToMap(list);

        System.out.println("choose an article id");
        long articleId = scanner.nextLong();
        Article article = map.get(articleId);
        article.setPublished(publish);
        if (publish)
            article.setPublishDate(new Date());
        articleService.update(article);

        if (publish)
            System.out.println("article published!");
        else System.out.println("publication canceled!");
    }

    private void deleteArticle() {
        Scanner scanner = new Scanner(System.in);
        List<Article> list = articleService.findAll();
        showEntity(list);
        System.out.println("choose an article id");
        long articleId = scanner.nextLong();
        articleService.deleteById(articleId);
        System.out.println("article deleted :(");
    }

    private void changeUserRoles() {
        Scanner scanner = new Scanner(System.in);

        List<User> userList = adminService.findUsers();
        Map<Long,User> userMap = listToMap(userList);
        showEntity(userList);
        System.out.println("choose an user id");
        long userId = scanner.nextLong();

        List<Role> roleList = roleService.findAll();
        Map<Long,Role> roleMap = listToMap(roleList);
        showEntity(roleList);
        System.out.println("choose an role id");
        long roleId = scanner.nextLong();

        Role role = roleMap.get(roleId);
        User user = userMap.get(userId);
        user.setRole(role);
        adminService.save(user);
        System.out.println("user role changed");
    }
}
