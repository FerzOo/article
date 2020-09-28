package ir.maktab39.ui.userUI;

import ir.maktab39.Repository;

import java.sql.SQLException;
import java.util.Scanner;

public class BaseUI {
    private Repository repository = Repository.getInstance();

    protected void changePassword() throws SQLException {
        Scanner scanner = new Scanner(System.in);
        System.out.println("enter new password");
        String password = scanner.next();
        repository.changePassword(password);
        System.out.println("password changed!");
    }
}
