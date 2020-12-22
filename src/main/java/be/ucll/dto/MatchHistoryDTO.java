package be.ucll.dto;

import be.ucll.models.Player;

import java.util.List;

public class MatchHistoryDTO {

    private Long teamId;
    private String won;
    private Integer killsTeam1;
    private Integer deathsTeam1;
    private Integer assistsTeam1;
    private Integer killsTeam2;
    private Integer deathsTeam2;
    private Integer assistsTeam2;
    private String matchDate;
    private List<PlayerStatsDTO> playerStatsDTOS;

    public MatchHistoryDTO(Long teamId, String won, Integer killsTeam1, Integer deathsTeam1, Integer assistsTeam1, Integer killsTeam2, Integer deathsTeam2, Integer assistsTeam2, String matchDate, List<PlayerStatsDTO> playerStatsDTOS) {
        this.teamId = teamId;
        this.won = won;
        this.killsTeam1 = killsTeam1;
        this.deathsTeam1 = deathsTeam1;
        this.assistsTeam1 = assistsTeam1;
        this.killsTeam2 = killsTeam2;
        this.deathsTeam2 = deathsTeam2;
        this.assistsTeam2 = assistsTeam2;
        this.matchDate = matchDate;
        this.playerStatsDTOS = playerStatsDTOS;
    }

    public MatchHistoryDTO() {
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public Integer getKillsTeam1() {
        return killsTeam1;
    }

    public void setKillsTeam1(Integer killsTeam1) {
        this.killsTeam1 = killsTeam1;
    }

    public Integer getDeathsTeam1() {
        return deathsTeam1;
    }

    public void setDeathsTeam1(Integer deathsTeam1) {
        this.deathsTeam1 = deathsTeam1;
    }

    public Integer getAssistsTeam1() {
        return assistsTeam1;
    }

    public void setAssistsTeam1(Integer assistsTeam1) {
        this.assistsTeam1 = assistsTeam1;
    }

    public Integer getKillsTeam2() {
        return killsTeam2;
    }

    public void setKillsTeam2(Integer killsTeam2) {
        this.killsTeam2 = killsTeam2;
    }

    public Integer getDeathsTeam2() {
        return deathsTeam2;
    }

    public void setDeathsTeam2(Integer deathsTeam2) {
        this.deathsTeam2 = deathsTeam2;
    }

    public Integer getAssistsTeam2() {
        return assistsTeam2;
    }

    public void setAssistsTeam2(Integer assistsTeam2) {
        this.assistsTeam2 = assistsTeam2;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public List<PlayerStatsDTO> getPlayerStatsDTOS() {
        return playerStatsDTOS;
    }

    public void setPlayerStatsDTOS(List<PlayerStatsDTO> playerStatsDTOS) {
        this.playerStatsDTOS = playerStatsDTOS;
    }
}

