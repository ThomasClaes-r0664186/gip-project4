package be.ucll.exceptions;

public class PlayerNotFound extends Exception {
    public PlayerNotFound(String playerName) {
        super("This player: " + playerName + " has not been found!");
    }
}
