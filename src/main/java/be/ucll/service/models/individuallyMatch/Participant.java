package be.ucll.service.models.individuallyMatch;

import com.fasterxml.jackson.annotation.JsonRootName;

@JsonRootName(value = "1")
public class Participant {

    private Long participantId;
    private Long currentGold;
    private Long totalGold;
    private Long level;
    private Long xp;
    private Long minionsKilled;
    private Long jungleMinionsKilled;
    private Long dominionScore;
    private Long teamScore;

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Long getCurrentGold() {
        return currentGold;
    }

    public void setCurrentGold(Long currentGold) {
        this.currentGold = currentGold;
    }

    public Long getTotalGold() {
        return totalGold;
    }

    public void setTotalGold(Long totalGold) {
        this.totalGold = totalGold;
    }

    public Long getLevel() {
        return level;
    }

    public void setLevel(Long level) {
        this.level = level;
    }

    public Long getXp() {
        return xp;
    }

    public void setXp(Long xp) {
        this.xp = xp;
    }

    public Long getMinionsKilled() {
        return minionsKilled;
    }

    public void setMinionsKilled(Long minionsKilled) {
        this.minionsKilled = minionsKilled;
    }

    public Long getJungleMinionsKilled() {
        return jungleMinionsKilled;
    }

    public void setJungleMinionsKilled(Long jungleMinionsKilled) {
        this.jungleMinionsKilled = jungleMinionsKilled;
    }

    public Long getDominionScore() {
        return dominionScore;
    }

    public void setDominionScore(Long dominionScore) {
        this.dominionScore = dominionScore;
    }

    public Long getTeamScore() {
        return teamScore;
    }

    public void setTeamScore(Long teamScore) {
        this.teamScore = teamScore;
    }
}
