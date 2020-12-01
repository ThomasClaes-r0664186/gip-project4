package be.ucll.exceptions;

public class UsernameNotValid extends Exception{
    public UsernameNotValid(String username) {
        super("This user: " + username + " is not valid!");
    }
}
