package be.ucll.service.models.currentGameInfo;

import java.util.List;

public class Participant {

    private int teamId;
    private int spell1Id;
    private int spell2Id;
    private int championId;
    private int profileIconId;
    private String summonerName;
    private boolean bot;
    private String summonerId;
    private List<Object> gameCustomizationObjects = null;
    private Perks perks;

    public Participant(int teamId, int spell1Id, int spell2Id, int championId, int profileIconId, String summonerName, boolean bot, String summonerId, List<Object> gameCustomizationObjects, Perks perks) {
        this.teamId = teamId;
        this.spell1Id = spell1Id;
        this.spell2Id = spell2Id;
        this.championId = championId;
        this.profileIconId = profileIconId;
        this.summonerName = summonerName;
        this.bot = bot;
        this.summonerId = summonerId;
        this.gameCustomizationObjects = gameCustomizationObjects;
        this.perks = perks;
    }

    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int teamId) {
        this.teamId = teamId;
    }

    public int getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(int spell1Id) {
        this.spell1Id = spell1Id;
    }

    public int getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(int spell2Id) {
        this.spell2Id = spell2Id;
    }

    public int getChampionId() {
        return championId;
    }

    public void setChampionId(int championId) {
        this.championId = championId;
    }

    public int getProfileIconId() {
        return profileIconId;
    }

    public void setProfileIconId(int profileIconId) {
        this.profileIconId = profileIconId;
    }

    public String getSummonerName() {
        return summonerName;
    }

    public void setSummonerName(String summonerName) {
        this.summonerName = summonerName;
    }

    public boolean isBot() {
        return bot;
    }

    public void setBot(boolean bot) {
        this.bot = bot;
    }

    public String getSummonerId() {
        return summonerId;
    }

    public void setSummonerId(String summonerId) {
        this.summonerId = summonerId;
    }

    public List<Object> getGameCustomizationObjects() {
        return gameCustomizationObjects;
    }

    public void setGameCustomizationObjects(List<Object> gameCustomizationObjects) {
        this.gameCustomizationObjects = gameCustomizationObjects;
    }

    public Perks getPerks() {
        return perks;
    }

    public void setPerks(Perks perks) {
        this.perks = perks;
    }

}