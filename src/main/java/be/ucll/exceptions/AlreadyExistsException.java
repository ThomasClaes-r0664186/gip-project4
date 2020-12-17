package be.ucll.exceptions;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException(String p) {
        super("This: " + p + " already exists!");
    }
}
