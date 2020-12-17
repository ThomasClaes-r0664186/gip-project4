package be.ucll.exceptions;

public class TeamAlreadyExists extends Exception{
    public TeamAlreadyExists(String name) {
        super("This team: " + name + " already exist!");
    }
}
