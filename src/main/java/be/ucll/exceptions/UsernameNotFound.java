package be.ucll.exceptions;

public class UsernameNotFound extends Exception{
    public UsernameNotFound(String username) {
        super("This user: " + username + " has not been found!");
    }
}
