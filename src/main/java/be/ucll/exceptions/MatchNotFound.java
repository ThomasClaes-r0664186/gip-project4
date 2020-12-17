package be.ucll.exceptions;

public class MatchNotFound extends Exception{
    public MatchNotFound(long id){
        super("Match with id "+id+" was not found");
    }
}
