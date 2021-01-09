package be.ucll.service.models.currentGameInfo;


public class BannedChampion {

    private int championId;
    private int teamId;
    private int pickTurn;

    public BannedChampion(int championId, int teamId, int pickTurn) {
        this.championId = championId;
        this.teamId = teamId;
        this.pickTurn = pickTurn;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(int pickTurn) {
        this.pickTurn = pickTurn;
    }

}