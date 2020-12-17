package be.ucll.dto;

import java.util.Date;

public class MatchDTO {

    private Long teamId;
    private String date;

    /**
     *
     * @param teamId - Id van het team
     * @param date - in formaat DD/MM/YYYY, wordt nadien geconverteerd naar Date Object
     */
    public MatchDTO(Long teamId, String date) {
        this.teamId = teamId;
        this.date = date;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
