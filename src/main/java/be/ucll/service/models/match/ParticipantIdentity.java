package be.ucll.service.models.match;


public class ParticipantIdentity {

    public Player player;
    public Long participantId;

    /**
     *
     * @param player Player information not included in the response for custom matches. Custom matches are considered private unless a tournament code was used to create the match.
     * @param participantId
     */
    public ParticipantIdentity(Player player, Long participantId) {
        setPlayer(player);
        setParticipantId(participantId);
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }
}