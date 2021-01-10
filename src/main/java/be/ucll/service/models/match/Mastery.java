package be.ucll.service.models.match;

public class Mastery {

    private Long rank;
    private Long masteryId;

    public Mastery(Long rank, Long masteryId) {
        setRank(rank);
        setMasteryId(masteryId);
    }

    public Mastery(){
        /**Lege contstructor voor error van JSON=>DTO te vermijden**/
    }

    //Getters en setters

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
