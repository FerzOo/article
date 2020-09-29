package ir.maktab39.ui.userUI;

import ir.maktab39.ComponentFactory;
import ir.maktab39.ErrorHandler;
import ir.maktab39.services.article.ArticleService;
import ir.maktab39.services.article.ArticleServiceImpl;
import ir.maktab39.services.user.admin.AdminService;

import java.util.Scanner;

public class AdminUI extends BaseUI{

    private AdminService adminService = (AdminService) ComponentFactory.
            getSingletonObject(AdminService.class);

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
                System.out.println("6)back");
                command = scanner.nextInt();
                switch (command) {
                    case 1:
                        publishArticle();
                        break;
                    case 2:
                        CancelPublication();
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
                        return;
                }
            } catch (Exception e) {
                ErrorHandler.showMessage(e);
            }
        }
    }

    private void publishArticle() {

    }

    private void CancelPublication() {

    }

    private void deleteArticle() {

    }

    private void createCategory() {

    }

    private void createTag() {

    }

}
