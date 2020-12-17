package be.ucll.exceptions;

public class TeamNotFound extends Exception{
    public TeamNotFound(){
        super("Team was not found");
    }
}
