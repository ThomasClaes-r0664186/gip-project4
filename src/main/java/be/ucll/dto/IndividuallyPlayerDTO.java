package be.ucll.dto;

import java.util.List;

public class IndividuallyPlayerDTO {
    private Long matchId;
    private String matchDate;
    private String won;
    private Long kills;
    private Long deaths;
    private Long assists;
    private List<PlayerStatsDTO> playersTeam1;
    private List<String> playersTeam2;

    public Long getMatchId() {
        return matchId;
    }

    public void setMatchId(Long matchId) {
        this.matchId = matchId;
    }

    public String getMatchDate() {
        return matchDate;
    }

    public void setMatchDate(String matchDate) {
        this.matchDate = matchDate;
    }

    public String getWon() {
        return won;
    }

    public void setWon(String won) {
        this.won = won;
    }

    public Long getKills() {
        return kills;
    }

    public void setKills(Long kills) {
        this.kills = kills;
    }

    public Long getDeaths() {
        return deaths;
    }

    public void setDeaths(Long deaths) {
        this.deaths = deaths;
    }

    public Long getAssists() {
        return assists;
    }

    public void setAssists(Long assists) {
        this.assists = assists;
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
