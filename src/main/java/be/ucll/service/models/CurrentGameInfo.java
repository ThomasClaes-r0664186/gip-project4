package be.ucll.service.models;

import be.ucll.service.models.currentGameInfo.BannedChampion;
import be.ucll.service.models.currentGameInfo.Observers;
import be.ucll.service.models.currentGameInfo.Participant;

import java.util.List;

public class CurrentGameInfo {

    private int gameId;
    private int mapId;
    private String gameMode;
    private String gameType;
    private int gameQueueConfigId;
    private List<Participant> participants = null;
    private Observers observers;
    private String platformId;
    private List<BannedChampion> bannedChampions = null;
    private int gameStartTime;
    private int gameLength;

    public int getGameId() {
        return gameId;
    }

    public void setGameId(int gameId) {
        this.gameId = gameId;
    }

    public int getMapId() {
        return mapId;
    }

    public void setMapId(int mapId) {
        this.mapId = mapId;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }

    public String getGameType() {
        return gameType;
    }

    public void setGameType(String gameType) {
        this.gameType = gameType;
    }

    public int getGameQueueConfigId() {
        return gameQueueConfigId;
    }

    public void setGameQueueConfigId(int gameQueueConfigId) {
        this.gameQueueConfigId = gameQueueConfigId;
    }

    public List<Participant> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Participant> participants) {
        this.participants = participants;
    }

    public Observers getObservers() {
        return observers;
    }

    public void setObservers(Observers observers) {
        this.observers = observers;
    }

    public String getPlatformId() {
        return platformId;
    }

    public void setPlatformId(String platformId) {
        this.platformId = platformId;
    }

    public List<BannedChampion> getBannedChampions() {
        return bannedChampions;
    }

    public void setBannedChampions(List<BannedChampion> bannedChampions) {
        this.bannedChampions = bannedChampions;
    }

    public int getGameStartTime() {
        return gameStartTime;
    }

    public void setGameStartTime(int gameStartTime) {
        this.gameStartTime = gameStartTime;
    }

    public int getGameLength() {
        return gameLength;
    }

    public void setGameLength(int gameLength) {
        this.gameLength = gameLength;
    }

}