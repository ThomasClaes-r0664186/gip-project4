package be.ucll.dto;

import java.util.Date;

public class MatchDTO {

    private String nameTeam1;
    private String nameTeam2;
    private String date;

    /**
     *
     * @param nameTeam1 - naam van team 1, wordt nadien gezocht om Team object op te halen
     * @param nameTeam2 - naam van team 2, wordt nadien gezocht om Team object op te halen
     * @param date - in formaat DD/MM/YYYY, wordt nadien geconverteerd naar Date Object
     */
    public MatchDTO(String nameTeam1, String nameTeam2, String date) {
        this.nameTeam1 = nameTeam1;
        this.nameTeam2 = nameTeam2;
        this.date = date;
    }

    public String getNameTeam1() {
        return nameTeam1;
    }

    public void setNameTeam1(String nameTeam1) {
        this.nameTeam1 = nameTeam1;
    }

    public String getNameTeam2() {
        return nameTeam2;
    }

    public void setNameTeam2(String nameTeam2) {
        this.nameTeam2 = nameTeam2;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
