package be.ucll.exceptions;

public class NotFoundException extends Exception{
    public NotFoundException() {
        super("This has not been found!");
    }
}
