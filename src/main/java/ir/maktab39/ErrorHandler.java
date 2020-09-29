package ir.maktab39;

public class ErrorHandler {

    public static void showMessage(Exception e) {
        e.printStackTrace();
//        System.out.println(e.getMessage());
    }

    public static void showStackTrace(Exception e) {
        e.printStackTrace();
    }
}
