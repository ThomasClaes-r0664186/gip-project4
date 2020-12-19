package be.ucll.service.models.match;

import be.ucll.exceptions.ParameterInvalidException;

import java.util.Arrays;
import java.util.List;

public class Team {

    private Boolean firstDragon;
    private List<Ban> bans = null;
    private Boolean firstInhibitor;
    private String win;
    private Boolean firstRiftHerald;
    private Boolean firstBaron;
    private Long baronKills;
    private Long riftHeraldKills;
    private Boolean firstBlood;
    private Long teamId;
    private Boolean firstTower;
    private Long vilemawKills;
    private Long inhibitorKills;
    private Long towerKills;
    private Long dominionVictoryScore;
    private Long dragonKills;

    /**
     *
     * @param firstDragon Flag indicating whether or not the team scored the first Dragon kill.
     * @param bans If match queueId has a draft, contains banned champion data, otherwise empty.
     * @param firstInhibitor Flag indicating whether or not the team destroyed the first inhibitor.
     * @param win String indicating whether or not the team won. There are only two values visibile in public match history. (Legal values: Fail, Win)
     * @param firstRiftHerald Flag indicating whether or not the team scored the first Rift Herald kill.
     * @param firstBaron Flag indicating whether or not the team scored the first Baron kill.
     * @param baronKills Number of times the team killed Baron.
     * @param riftHeraldKills 	Number of times the team killed Rift Herald.
     * @param firstBlood Flag indicating whether or not the team scored the first blood.
     * @param teamId 100 for blue side. 200 for red side.
     * @param firstTower Flag indicating whether or not the team destroyed the first tower.
     * @param vilemawKills Number of times the team killed Vilemaw.
     * @param inhibitorKills Number of inhibitors the team destroyed.
     * @param towerKills Number of towers the team destroyed.
     * @param dominionVictoryScore For Dominion matches, specifies the points the team had at game end.
     * @param dragonKills 	Number of times the team killed Dragon.
     */
    public Team(Boolean firstDragon, List<Ban> bans, Boolean firstInhibitor, String win, Boolean firstRiftHerald, Boolean firstBaron, Long baronKills, Long riftHeraldKills, Boolean firstBlood, Long teamId, Boolean firstTower, Long vilemawKills, Long inhibitorKills, Long towerKills, Long dominionVictoryScore, Long dragonKills) throws ParameterInvalidException {
        setFirstDragon(firstDragon);
        setBans(bans);
        setFirstInhibitor(firstInhibitor);
        setWin(win);
        setFirstRiftHerald(firstRiftHerald);
        setFirstBaron(firstBaron);
        setBaronKills(baronKills);
        setRiftHeraldKills(riftHeraldKills);
        setFirstBlood(firstBlood);
        setTeamId(teamId);
        setFirstTower(firstTower);
        setVilemawKills(vilemawKills);
        setInhibitorKills(inhibitorKills);
        setTowerKills(towerKills);
        setDominionVictoryScore(dominionVictoryScore);
        setDragonKills(dragonKills);
    }

    public Boolean getFirstDragon() {
        return firstDragon;
    }

    public void setFirstDragon(Boolean firstDragon) {
        this.firstDragon = firstDragon;
    }

    public List<Ban> getBans() {
        return bans;
    }

    public void setBans(List<Ban> bans) {
        this.bans = bans;
    }

    public Boolean getFirstInhibitor() {
        return firstInhibitor;
    }

    public void setFirstInhibitor(Boolean firstInhibitor) {
        this.firstInhibitor = firstInhibitor;
    }

    public String getWin() {
        return win;
    }

    public void setWin(String win) throws ParameterInvalidException {
        List wins = Arrays.asList(Types.Team.Win.values());
        if(wins.contains(Types.Timeline.Roles.valueOf(win))){
            this.win = win;
        }else{
            throw new ParameterInvalidException(win);
        }
    }

    public Boolean getFirstRiftHerald() {
        return firstRiftHerald;
    }

    public void setFirstRiftHerald(Boolean firstRiftHerald) {
        this.firstRiftHerald = firstRiftHerald;
    }

    public Boolean getFirstBaron() {
        return firstBaron;
    }

    public void setFirstBaron(Boolean firstBaron) {
        this.firstBaron = firstBaron;
    }

    public Long getBaronKills() {
        return baronKills;
    }

    public void setBaronKills(Long baronKills) {
        this.baronKills = baronKills;
    }

    public Long getRiftHeraldKills() {
        return riftHeraldKills;
    }

    public void setRiftHeraldKills(Long riftHeraldKills) {
        this.riftHeraldKills = riftHeraldKills;
    }

    public Boolean getFirstBlood() {
        return firstBlood;
    }

    public void setFirstBlood(Boolean firstBlood) {
        this.firstBlood = firstBlood;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Boolean getFirstTower() {
        return firstTower;
    }

    public void setFirstTower(Boolean firstTower) {
        this.firstTower = firstTower;
    }

    public Long getVilemawKills() {
        return vilemawKills;
    }

    public void setVilemawKills(Long vilemawKills) {
        this.vilemawKills = vilemawKills;
    }

    public Long getInhibitorKills() {
        return inhibitorKills;
    }

    public void setInhibitorKills(Long inhibitorKills) {
        this.inhibitorKills = inhibitorKills;
    }

    public Long getTowerKills() {
        return towerKills;
    }

    public void setTowerKills(Long towerKills) {
        this.towerKills = towerKills;
    }

    public Long getDominionVictoryScore() {
        return dominionVictoryScore;
    }

    public void setDominionVictoryScore(Long dominionVictoryScore) {
        this.dominionVictoryScore = dominionVictoryScore;
    }

    public Long getDragonKills() {
        return dragonKills;
    }

    public void setDragonKills(Long dragonKills) {
        this.dragonKills = dragonKills;
    }
}