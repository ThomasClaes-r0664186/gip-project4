package be.ucll.exceptions;

public class TeamNotFound extends Exception{
    public TeamNotFound(String teamName){
        super("Team "+teamName+" was not found");
    }
}
