package be.ucll.exceptions;

public class TooManyActivePlayersException extends Exception{
    public TooManyActivePlayersException(String team) {
        super("This team: " + team + " this team has enough active players");
    }
}
