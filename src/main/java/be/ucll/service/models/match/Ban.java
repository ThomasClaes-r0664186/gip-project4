package be.ucll.service.models.match;


public class Ban {

    private Long pickTurn;
    private Long championId;

    /**
     *
     * @param pickTurn Turn during which the champion was banned.
     * @param championId Banned championId.
     */
    public Ban(Long pickTurn, Long championId) {
        setPickTurn(pickTurn);
        setChampionId(championId);
    }

    public Ban(){
        /**Lege contstructor voor error van JSON=>DTO te vermijden**/
    }

    //Getters en setters
    public Long getPickTurn() {
        return pickTurn;
    }

    public void setPickTurn(Long pickTurn) {
        this.pickTurn = pickTurn;
    }

    public Long getChampionId() {
        return championId;
    }

    public void setChampionId(Long championId) {
        this.championId = championId;
    }
}