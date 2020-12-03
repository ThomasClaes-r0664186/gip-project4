package be.ucll.exceptions;

public class UsernameAlreadyExists extends Exception{
    public UsernameAlreadyExists(String username) {
        super("This user: " + username + " already exists!");
    }
}
