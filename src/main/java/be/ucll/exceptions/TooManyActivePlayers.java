package be.ucll.exceptions;

public class TooManyActivePlayers extends Exception{
    public TooManyActivePlayers(String team) {
        super("This team: " + team + " this team has enough active players");
    }
}
