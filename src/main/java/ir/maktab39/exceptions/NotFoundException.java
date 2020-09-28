package ir.maktab39.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String type) {
        super("the requested " + type + " not found!");
    }
}
