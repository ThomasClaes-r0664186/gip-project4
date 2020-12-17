package be.ucll.exceptions;

import java.text.ParseException;

public class MatchDateNotCorrect extends ParseException {
    public MatchDateNotCorrect(int errorOffset) {
        super("The match date was not correctly filled in, format DD/MM/YYYY", errorOffset);
    }
}
