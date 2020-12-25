package be.ucll.dto;

import java.util.List;

public class MatchHistoryDTO {

    private Long teamId;
    private String won1;
    private Long killsTeam1;
    private Long deathsTeam1;
    private Long assistsTeam1;
    private Long killsTeam2;
    private Long deathsTeam2;
    private Long assistsTeam2;
    private String matchDate;
    private List<PlayerStatsDTO> playersTeam1;
    private List<String> playersTeam2;


    public MatchHistoryDTO() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getWon1() {
        return won1;
    }

    public void setWon1(String won1) {
        this.won1 = won1;
    }

    public Long getKillsTeam1() {
        return killsTeam1;
    }

    public void setKillsTeam1(Long killsTeam1) {
        this.killsTeam1 = killsTeam1;
    }

    public Long getDeathsTeam1() {
        return deathsTeam1;
    }

    public void setDeathsTeam1(Long deathsTeam1) {
        this.deathsTeam1 = deathsTeam1;
    }

    public Long getAssistsTeam1() {
        return assistsTeam1;
    }

    public void setAssistsTeam1(Long assistsTeam1) {
        this.assistsTeam1 = assistsTeam1;
    }

    public Long getKillsTeam2() {
        return killsTeam2;
    }

    public void setKillsTeam2(Long killsTeam2) {
        this.killsTeam2 = killsTeam2;
    }

    public Long getDeathsTeam2() {
        return deathsTeam2;
    }

    public void setDeathsTeam2(Long deathsTeam2) {
        this.deathsTeam2 = deathsTeam2;
    }

    public Long getAssistsTeam2() {
        return assistsTeam2;
    }

    public void setAssistsTeam2(Long assistsTeam2) {
        this.assistsTeam2 = assistsTeam2;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public List<PlayerStatsDTO> getPlayersTeam1() {
        return playersTeam1;
    }

    public void setPlayersTeam1(List<PlayerStatsDTO> playersTeam1) {
        this.playersTeam1 = playersTeam1;
    }

    public List<String> getPlayersTeam2() {
        return playersTeam2;
    }

    public void setPlayersTeam2(List<String> playersTeam2) {
        this.playersTeam2 = playersTeam2;
    }
}

