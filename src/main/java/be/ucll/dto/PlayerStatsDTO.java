package be.ucll.dto;

public class PlayerStatsDTO {

    private String summonerName;
    private Long playerId;
    // mss nog aangeven in welk team hij zat

    public PlayerStatsDTO(String summonerName, Long playerId) {
        this.summonerName = summonerName;
        this.playerId = playerId;
    }

    public PlayerStatsDTO() {
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public Long getPlayerId() {
        return playerId;
    }

    public void setPlayerId(Long playerId) {
        this.playerId = playerId;
    }
}
