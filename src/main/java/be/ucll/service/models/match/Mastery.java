package be.ucll.service.models.match;

public class Mastery {

    private Long rank=null;
    private Long masteryId=null;

    public Mastery(Long rank, Long masteryId) {
        setRank(rank);
        setMasteryId(masteryId);
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }

    public Long getMasteryId() {
        return masteryId;
    }

    public void setMasteryId(Long masteryId) {
        this.masteryId = masteryId;
    }
}
