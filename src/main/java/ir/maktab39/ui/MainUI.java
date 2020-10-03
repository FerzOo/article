package ir.maktab39.ui;

import ir.maktab39.*;
import ir.maktab39.dto.ArticleSearchDto;
import ir.maktab39.dto.UserInfo;
import ir.maktab39.entities.Article;
import ir.maktab39.entities.Role;
import ir.maktab39.entities.User;
import ir.maktab39.entities.embeddable.Address;
import ir.maktab39.exceptions.NotFoundException;
import ir.maktab39.exceptions.UniqueConstraintException;
import ir.maktab39.services.article.ArticleService;
import ir.maktab39.services.article.ArticleServiceImpl;
import ir.maktab39.services.role.RoleService;
import ir.maktab39.services.role.RoleServiceImpl;
import ir.maktab39.services.user.UserService;
import ir.maktab39.services.user.UserServiceImpl;
import ir.maktab39.ui.userUI.BaseUI;

import javax.persistence.EntityManager;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Scanner;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Function;
import java.util.function.Predicate;

public class MainUI extends BaseUI {

    static {
        try {
            Class.forName("ir.maktab39.JPAUtility");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private UserService userService = (UserService) ComponentFactory.
            getSingletonObject(UserServiceImpl.class);
    private RoleService roleService = (RoleService) ComponentFactory.
            getSingletonObject(RoleServiceImpl.class);
    private ArticleService articleService = (ArticleService) ComponentFactory.
            getSingletonObject(ArticleServiceImpl.class);

    public static void main(String[] args) {
        new MainUI().start();
    }

    private void start() {
        try {
            EntityManager entityManager = JPAUtility.getEntityManager();
            EntityManager entityManager2 = JPAUtility.getEntityManager2();
            Session.setEntityManager(entityManager);
            Session.setEntityManager2(entityManager2);
            init();
            showMenuAndGetCommand();
        } catch (Exception e) {
            ErrorHandler.showMessage(e);
        }
    }

    private void init() {
        try {
            Role admin = new Role();
            admin.setTitle("admin");
            User user = new User();
            user.setUsername("ahmad");
            user.setPassword("333");
            user.setRole(admin);
            Role writer = new Role();
            User user2 = new User();
            user2.setUsername("ali");
            user2.setPassword("111");
            user2.setRole(writer);
            writer.setTitle("writer");
            userService.save(user);
            userService.save(user2);
        } catch (Exception e) {
            ErrorHandler.showMessage(e);
        }
    }

    private void showMenuAndGetCommand() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        int command;
        while (true) {
            try {
                System.out.println("1)login");
                System.out.println("2)register");
                System.out.println("3)view articles");
                System.out.println("4)users info");
                System.out.println("5)admin users");
                System.out.println("6)search By criteria and dto");
                command = scanner.nextInt();
                switch (command) {
                    case 1:
                        login();
                        break;
                    case 2:
                        register();
                        break;
                    case 3:
                        showAndReturnArticlesDependsOnPublication(true);
                        break;
                    case 4:
                        Function<User, UserInfo> function =
                                (a) -> {
                                    UserInfo userInfo = new UserInfo();
                                    userInfo.setUsername(a.getUsername());
                                    return userInfo;
                                };
                        getUsersInfo(function);
                        break;
                    case 5:
                        Predicate<User> predicate =
                                (a) -> a.getRole().getTitle().equals("admin");
                        showAdminUsers(predicate);
                        break;
                    case 6:
                        ArticleSearchDto dto = new ArticleSearchDto();
                        dto.setTitle("sa");
                        dto.setPublished(false);
                        List<Article> articles = articleService.search(dto);
                        showEntity(articles);
                        break;
                }
            } catch (Exception e) {
                ErrorHandler.showMessage(e);
            }
        }
    }

    private void showAdminUsers(Predicate<User> predicate) {
        List<User> list = userService.findAll(predicate);
        showEntity(list);
    }

    private void getUsersInfo(Function<User, UserInfo> function) {
        List<UserInfo> list = userService.findAll(function);
        showEntity(list);
    }


    private void login() throws NotFoundException, SQLException, ClassNotFoundException, IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("username:");
        String username = scanner.next();
        System.out.println("password:");
        String password = scanner.next();
        User user = userService.getUser(username, password);
        Session.setUser(user);
        Role role = user.getRole();
        String simpleUIClassName = firstCharToUpperCase(role.getTitle());
        String uiClassPath = "ir.maktab39.ui.userUI." + simpleUIClassName + "UI";
        Class uiClass = Class.forName(uiClassPath);
        Object uiInstance = uiClass.newInstance();
        Method startMethod = uiClass.getMethod("showMenuAndGetCommand");
        startMethod.invoke(uiInstance);
    }

    private String firstCharToUpperCase(String str) {

        if (str == null || str.length() == 0)
            return "";

        if (str.length() == 1)
            return str.toUpperCase();

        char[] chArr = str.toCharArray();
        chArr[0] = Character.toUpperCase(chArr[0]);

        return new String(chArr);
    }

    private static void logout() {
        Session.destroy();
    }

    private void register() throws SQLException, UniqueConstraintException, ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("username:");
        String username = scanner.next();
        System.out.println("national code:");
        String nationalCode = scanner.next();
        System.out.println("birthday(dd/MM/yyyy):");
        Date birthday = new SimpleDateFormat("dd/MM/yyyy")
                .parse(scanner.next());

        System.out.println("country:");
        String country = scanner.next();
        System.out.println("city:");
        String city = scanner.next();
        System.out.println("street:");
        String street = scanner.next();
        System.out.println("postcode:");
        String postcode = scanner.next();
        Address address = new Address();
        address.setCountry(country);
        address.setCity(city);
        address.setStreet(street);
        address.setPostcode(postcode);

        Role writer = roleService.getRole("writer");

        User user = new User();
        user.setUsername(username);
        user.setPassword(nationalCode);
        user.setNationalCode(nationalCode);
        user.setBirthday(birthday);
        user.setAddress(address);
        user.setRole(writer);
        userService.save(user);
    }

}
