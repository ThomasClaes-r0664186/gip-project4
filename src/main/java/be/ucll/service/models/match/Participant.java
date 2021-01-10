package be.ucll.service.models.match;


import be.ucll.exceptions.ParameterInvalidException;
import liquibase.pro.packaged.M;

import java.util.Arrays;
import java.util.List;

public class Participant {

    private Long spell1Id;
    private Long participantId;
    private Timeline timeline;
    private Long spell2Id;
    private Long teamId;
    private Stats stats;
    private Long championId;
    private String highestAchievedSeasonTier;
    private List<Rune> runes;
    private List<Mastery> masteries;

    /**
     *
     * @param spell1Id 	First Summoner Spell id.
     * @param participantId
     * @param timeline Participant timeline data.
     * @param spell2Id Second Summoner Spell id.
     * @param teamId 100 for blue side. 200 for red side.
     * @param stats Participant statistics.
     * @param championId
     * @param highestAchievedSeasonTier Highest ranked tier achieved for the previous season in a specific subset of queueIds, if any, otherwise null. Used to display border in game loading screen. Please refer to the Ranked Info documentation. (Legal values: CHALLENGER, MASTER, DIAMOND, PLATINUM, GOLD, SILVER, BRONZE, UNRANKED)
     */
    public Participant(Long spell1Id, Long participantId, Timeline timeline, Long spell2Id, Long teamId, Stats stats, Long championId, String highestAchievedSeasonTier, List<Rune> runes, List<Mastery> masteries) throws ParameterInvalidException {
        setSpell1Id(spell1Id);
        setParticipantId(participantId);
        setTimeline(timeline);
        setSpell2Id(spell2Id);
        setTeamId(teamId);
        setStats(stats);
        setChampionId(championId);
        setHighestAchievedSeasonTier(highestAchievedSeasonTier);
        setRunes(runes);
        setMasteries(masteries);
    }

    public Participant(){
        /**Lege contstructor voor error van JSON=>DTO te vermijden**/
    }

    //Getters en setters

    public List<Mastery> getMasteries() {
        return masteries;
    }

    public void setMasteries(List<Mastery> masteries) {
        this.masteries = masteries;
    }

    public List<Rune> getRunes() {
        return runes;
    }

    public void setRunes(List<Rune> runes) {
        this.runes = runes;
    }

    public Long getSpell1Id() {
        return spell1Id;
    }

    public void setSpell1Id(Long spell1Id) {
        this.spell1Id = spell1Id;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Timeline getTimeline() {
        return timeline;
    }

    public void setTimeline(Timeline timeline) {
        this.timeline = timeline;
    }

    public Long getSpell2Id() {
        return spell2Id;
    }

    public void setSpell2Id(Long spell2Id) {
        this.spell2Id = spell2Id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {
        this.teamId = teamId;
    }

    public Stats getStats() {
        return stats;
    }

    public void setStats(Stats stats) {
        this.stats = stats;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }

    public String getHighestAchievedSeasonTier() {
        return highestAchievedSeasonTier;
    }

    public void setHighestAchievedSeasonTier(String highestAchievedSeasonTier) {
        this.highestAchievedSeasonTier = highestAchievedSeasonTier;
    }
}