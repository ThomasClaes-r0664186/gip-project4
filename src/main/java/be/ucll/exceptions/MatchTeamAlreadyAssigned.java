package be.ucll.exceptions;

public class MatchTeamAlreadyAssigned extends Exception{
    public MatchTeamAlreadyAssigned(String date) {
        super("This team has already been assigned to a match on date "+date);
    }
}
