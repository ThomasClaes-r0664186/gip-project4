package be.ucll.service.models.match;

public class Rune {
    private Long runeId=null;
    private Long rank=null;

    /**
     *
     * @param runeId
     * @param rank
     */
    public Rune(Long runeId, Long rank) {
        this.runeId = runeId;
        this.rank = rank;
    }

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
