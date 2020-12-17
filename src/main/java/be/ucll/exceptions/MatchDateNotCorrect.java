package be.ucll.exceptions;

import java.text.ParseException;

public class MatchDateNotCorrect extends Exception {
    public MatchDateNotCorrect() {
        super("The match date was not correctly filled in, format DD/MM/YYYY");
    }
    public MatchDateNotCorrect(String date) {
        super("The match date "+date+" has already expired");
    }
}
