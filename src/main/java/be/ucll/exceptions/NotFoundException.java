package be.ucll.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException(String p) {
        super("This: " + p + " was not found!");
    }
}
