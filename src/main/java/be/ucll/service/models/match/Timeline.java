package be.ucll.service.models.match;


import be.ucll.exceptions.ParameterInvalidException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Timeline {

    private String lane = null;
    private Long participantId = null;
    private Map<String, Double> goldPerMinDeltas = null;
    private Map<String, Double> creepsPerMinDeltas = null;
    private Map<String, Double> xpPerMinDeltas;
    private String role = null;
    private Map<String, Double> damageTakenPerMinDeltas = null;
    private Map<String, Double> csDiffPerMinDeltas = null;
    private Map<String, Double> xpDiffPerMinDeltas = null;
    private Map<String, Double> damageTakenDiffPerMinDeltas = null;

    /**
     *
     * @param lane Participant's calculated lane. MID and BOT are legacy values. (Legal values: MID, MIDDLE, TOP, JUNGLE, BOT, BOTTOM)
     * @param participantId
     * @param goldPerMinDeltas Gold for a specified period.
     * @param creepsPerMinDeltas Creeps for a specified period.
     * @param xpPerMinDeltas Experience change for a specified period.
     * @param role 	Participant's calculated role. (Legal values: DUO, NONE, SOLO, DUO_CARRY, DUO_SUPPORT)
     * @param damageTakenPerMinDeltas Damage taken for a specified period.
     * @param csDiffPerMinDeltas Creep score difference versus the calculated lane opponent(s) for a specified period.
     * @param xpDiffPerMinDeltas Experience difference versus the calculated lane opponent(s) for a specified period.
     * @param damageTakenDiffPerMinDeltas Damage taken difference versus the calculated lane opponent(s) for a specified period.
     */
    public Timeline(String lane, Long participantId, Map<String, Double> goldPerMinDeltas, Map<String, Double> creepsPerMinDeltas, Map<String, Double> xpPerMinDeltas, String role, Map<String, Double> damageTakenPerMinDeltas, Map<String, Double> csDiffPerMinDeltas, Map<String, Double> xpDiffPerMinDeltas, Map<String, Double> damageTakenDiffPerMinDeltas) throws ParameterInvalidException {
        setLane(lane);
        setParticipantId(participantId);
        setGoldPerMinDeltas(goldPerMinDeltas);
        setCreepsPerMinDeltas(creepsPerMinDeltas);
        setXpPerMinDeltas(xpPerMinDeltas);
        setRole(role);
        setDamageTakenPerMinDeltas(damageTakenPerMinDeltas);
        setCsDiffPerMinDeltas(csDiffPerMinDeltas);
        setXpDiffPerMinDeltas(xpDiffPerMinDeltas);
        setDamageTakenDiffPerMinDeltas(damageTakenDiffPerMinDeltas);
    }

    public Timeline(){
        /**Lege contstructor voor error van JSON=>DTO te vermijden**/
    }

    //Getters en setters

    public String getLane() {
        return lane;
    }

    public void setLane(String lane) throws ParameterInvalidException {
        this.lane = lane;
    }

    public Long getParticipantId() {
        return participantId;
    }

    public void setParticipantId(Long participantId) {
        this.participantId = participantId;
    }

    public Map<String, Double> getGoldPerMinDeltas() {
        return goldPerMinDeltas;
    }

    public void setGoldPerMinDeltas(Map<String, Double> goldPerMinDeltas) {
        this.goldPerMinDeltas = goldPerMinDeltas;
    }

    public Map<String, Double> getCreepsPerMinDeltas() {
        return creepsPerMinDeltas;
    }

    public void setCreepsPerMinDeltas(Map<String, Double> creepsPerMinDeltas) {
        this.creepsPerMinDeltas = creepsPerMinDeltas;
    }

    public Map<String, Double> getXpPerMinDeltas() {
        return xpPerMinDeltas;
    }

    public void setXpPerMinDeltas(Map<String, Double> xpPerMinDeltas) {
        this.xpPerMinDeltas = xpPerMinDeltas;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) throws ParameterInvalidException {
        this.role = role;
    }

    public Map<String, Double> getDamageTakenPerMinDeltas() {
        return damageTakenPerMinDeltas;
    }

    public void setDamageTakenPerMinDeltas(Map<String, Double> damageTakenPerMinDeltas) {
        this.damageTakenPerMinDeltas = damageTakenPerMinDeltas;
    }

    public Map<String, Double> getCsDiffPerMinDeltas() {
        return csDiffPerMinDeltas;
    }

    public void setCsDiffPerMinDeltas(Map<String, Double> csDiffPerMinDeltas) {
        this.csDiffPerMinDeltas = csDiffPerMinDeltas;
    }

    public Map<String, Double> getXpDiffPerMinDeltas() {
        return xpDiffPerMinDeltas;
    }

    public void setXpDiffPerMinDeltas(Map<String, Double> xpDiffPerMinDeltas) {
        this.xpDiffPerMinDeltas = xpDiffPerMinDeltas;
    }

    public Map<String, Double> getDamageTakenDiffPerMinDeltas() {
        return damageTakenDiffPerMinDeltas;
    }

    public void setDamageTakenDiffPerMinDeltas(Map<String, Double> damageTakenDiffPerMinDeltas) {
        this.damageTakenDiffPerMinDeltas = damageTakenDiffPerMinDeltas;
    }
}