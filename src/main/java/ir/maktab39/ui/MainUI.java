package ir.maktab39.ui;

import ir.maktab39.*;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Role;
import ir.maktab39.entities.User;
import ir.maktab39.exceptions.NotFoundException;
import ir.maktab39.exceptions.UniqueConstraintException;
import ir.maktab39.repositories.user.UserRepo;
import ir.maktab39.repositories.user.UserRepoImpl;
import ir.maktab39.services.article.ArticleService;
import ir.maktab39.services.article.ArticleServiceImpl;
import ir.maktab39.services.role.RoleService;
import ir.maktab39.services.role.RoleServiceImpl;
import ir.maktab39.services.tag.TagService;
import ir.maktab39.services.tag.TagServiceImpl;
import ir.maktab39.services.user.UserService;
import ir.maktab39.services.user.UserServiceImpl;
import ir.maktab39.ui.userUI.WriterUI;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;

public class MainUI {

    static {
        try {
            Class.forName("ir.maktab39.JPAUtility");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private static Repository repository = Repository.getInstance();

    private static UserService userService = (UserService) ComponentFactory.
            getSingletonObject(UserServiceImpl.class);
    private static ArticleService articleService = (ArticleService) ComponentFactory.
            getSingletonObject(ArticleServiceImpl.class);
    private static TagService tagService = (TagService) ComponentFactory.
            getSingletonObject(TagServiceImpl.class);
    private static RoleService roleService = (RoleService) ComponentFactory.
            getSingletonObject(RoleServiceImpl.class);

    public static void main(String[] args) {
        try {
            EntityManager entityManager = JPAUtility.getEntityManager();
            Session.setEntityManager(entityManager);
            init();
            showMenuAndGetCommand();
        } catch (Exception e) {
            ErrorHandler.showMessage(e);
        }
    }

    private static void init() {
        try {
            Role admin = new Role();
            admin.setTitle("admin");
            Role writer = new Role();
            writer.setTitle("writer");
            roleService.save(admin);
            roleService.save(writer);
        } catch (Exception e) {
            ErrorHandler.showMessage(e);
        }
    }

    private static void showMenuAndGetCommand() throws SQLException {
        repository.startConnection();
        Scanner scanner = new Scanner(System.in);
        int command;
        while (true) {
            try {
                System.out.println("1)login");
                System.out.println("2)register");
                System.out.println("3)view articles");
                command = scanner.nextInt();
                switch (command) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 3:
                        showArticles();
                        break;
                }
            } catch (Exception e) {
                ErrorHandler.showMessage(e);
            }
        }
    }

    private static void showArticles() throws SQLException {
        List<Article> articleList = articleService.getArticles();
        for (Article article : articleList) {
            System.out.println(article);
            System.out.println("----------------");
        }
    }

    private static void login() throws NotFoundException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("username:");
        String username = scanner.next();
        System.out.println("password:");
        String password = scanner.next();
        User user = userService.getUser(username, password);
        Session.setUser(user);
        Role role = user.getRole();
        String simpleUIClassName = firstCharToUpperCase(role.getTitle());
        Class uiClass = Class.forName("ir.maktab39.ui.userUI." +
                simpleUIClassName + "UI");
        Object uiInstance = uiClass.newInstance();
        Method startMethod = uiClass.getMethod("showMenuAndGetCommand");
        startMethod.invoke(uiInstance);
    }

    private static String firstCharToUpperCase(String str) {

        if (str == null || str.length() == 0)
            return "";

        if (str.length() == 1)
            return str.toLowerCase();

        char[] chArr = str.toCharArray();
        chArr[0] = Character.toLowerCase(chArr[0]);

        return new String(chArr);
    }

    private static void logout() {
        Session.destroy();
    }

    private static void register() throws SQLException, UniqueConstraintException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("username:");
        String username = scanner.next();
        System.out.println("national code:");
        String nationalCode = scanner.next();
        System.out.println("birthday(dd/MM/yyyy):");
        Date birthday = new SimpleDateFormat("dd/MM/yyyy")
                .parse(scanner.next());
        User user = new User();
        user.setUsername(username);
        user.setPassword(nationalCode);
        user.setNationalCode(nationalCode);
        user.setBirthday(birthday);
        Role writer = roleService.getRole("writer");
        user.setRole(writer);
        userService.save(user);
    }

}
