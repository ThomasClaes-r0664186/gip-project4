package be.ucll.exceptions;

public class AlreadyExistsException extends Exception{
    public AlreadyExistsException() {
        super("This already exists!");
    }
}
