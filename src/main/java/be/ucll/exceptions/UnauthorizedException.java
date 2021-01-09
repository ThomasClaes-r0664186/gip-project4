package be.ucll.exceptions;

public class UnauthorizedException extends Exception{
    public UnauthorizedException() {
        super("You are unauthorized to do this");
    }
}

