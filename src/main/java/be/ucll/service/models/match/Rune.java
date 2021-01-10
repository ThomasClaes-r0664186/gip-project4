package be.ucll.service.models.match;

public class Rune {
    private Long runeId;
    private Long rank;

    /**
     *
     * @param runeId
     * @param rank
     */
    public Rune(Long runeId, Long rank) {
        this.runeId = runeId;
        this.rank = rank;
    }

    public Rune(){
        /**Lege contstructor voor error van JSON=>DTO te vermijden**/
    }

    //Getters en setters

    public Long getRuneId() {
        return runeId;
    }

    public void setRuneId(Long runeId) {
        this.runeId = runeId;
    }

    public Long getRank() {
        return rank;
    }

    public void setRank(Long rank) {
        this.rank = rank;
    }
}
