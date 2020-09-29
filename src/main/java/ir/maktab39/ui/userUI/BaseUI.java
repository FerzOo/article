package ir.maktab39.ui.userUI;

import ir.maktab39.ComponentFactory;
import ir.maktab39.Repository;
import ir.maktab39.Session;
import ir.maktab39.entities.User;
import ir.maktab39.services.article.ArticleService;
import ir.maktab39.services.article.ArticleServiceImpl;
import ir.maktab39.services.user.UserService;
import ir.maktab39.services.user.UserServiceImpl;

import java.sql.SQLException;
import java.util.Scanner;

public class BaseUI {

    private UserService userService = (UserService) ComponentFactory.
            getSingletonObject(UserServiceImpl.class);

    protected void changePassword() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter new password");
        String password = scanner.next();
        User user = Session.getUser();
        user.setPassword(password);
        userService.update(user);
        System.out.println("password changed!");
    }
}
