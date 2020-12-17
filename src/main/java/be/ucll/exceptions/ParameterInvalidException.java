package be.ucll.exceptions;

public class ParameterInvalidException extends Exception{
    public ParameterInvalidException() {
        super("One or more parameters are invalid!");
    }
}
