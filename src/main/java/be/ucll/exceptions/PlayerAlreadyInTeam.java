package be.ucll.exceptions;

public class PlayerAlreadyInTeam extends Exception{
    public PlayerAlreadyInTeam(String leagueName) {
        super("This user: " + leagueName + " is already in this team");
    }
}
