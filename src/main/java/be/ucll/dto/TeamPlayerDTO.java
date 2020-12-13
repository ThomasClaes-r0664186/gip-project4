package be.ucll.dto;

public class TeamPlayerDTO {

    private Long id;
    private String teamName;
    private String playerName;
    private boolean isReserve;

    public TeamPlayerDTO() {
    }

    public TeamPlayerDTO(Long id, String teamName, String playerName) {
        this.id = id;
        this.teamName = teamName;
        this.playerName = playerName;
    }

    public TeamPlayerDTO(Long id, String teamName, String playerName, boolean isReserve) {
        this.id = id;
        this.teamName = teamName;
        this.playerName = playerName;
        this.isReserve = isReserve;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isReserve() {
        return isReserve;
    }

    public void setReserve(boolean reserve) {
        this.isReserve = reserve;
    }
}
