package be.ucll.service.models.individuallyMatch;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ParticipantFrames {

    @JsonProperty("1")
    private Participant participant;

    @JsonProperty("2")
    private Participant two;

    @JsonProperty("3")
    private Participant three;

    @JsonProperty("4")
    private Participant four;

    @JsonProperty("5")
    private Participant five;

    @JsonProperty("6")
    private Participant six;

    @JsonProperty("7")
    private Participant seven;

    @JsonProperty("8")
    private Participant eight;

    @JsonProperty("9")
    private Participant nine;

    @JsonProperty("10")
    private Participant ten;

    public Participant getOne() {
        return participant;
    }

    public void setOne(Participant participant) {
        this.participant = participant;
    }

    public Participant getParticipant() {
        return participant;
    }

    public void setParticipant(Participant participant) {
        this.participant = participant;
    }

    public Participant getTwo() {
        return two;
    }

    public void setTwo(Participant two) {
        this.two = two;
    }

    public Participant getThree() {
        return three;
    }

    public void setThree(Participant three) {
        this.three = three;
    }

    public Participant getFour() {
        return four;
    }

    public void setFour(Participant four) {
        this.four = four;
    }

    public Participant getFive() {
        return five;
    }

    public void setFive(Participant five) {
        this.five = five;
    }

    public Participant getSix() {
        return six;
    }

    public void setSix(Participant six) {
        this.six = six;
    }

    public Participant getSeven() {
        return seven;
    }

    public void setSeven(Participant seven) {
        this.seven = seven;
    }

    public Participant getEight() {
        return eight;
    }

    public void setEight(Participant eight) {
        this.eight = eight;
    }

    public Participant getNine() {
        return nine;
    }

    public void setNine(Participant nine) {
        this.nine = nine;
    }

    public Participant getTen() {
        return ten;
    }

    public void setTen(Participant ten) {
        this.ten = ten;
    }
}
