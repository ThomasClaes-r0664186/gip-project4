package be.ucll.service.models.individuallyMatch;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;

public class ParticipantFrames {

    @JsonProperty("1")
    private One one;

    @JsonProperty("2")
    private Two two;

    @JsonProperty("3")
    private Three three;

    @JsonProperty("4")
    private Four four;

    @JsonProperty("5")
    private Five five;

    @JsonProperty("6")
    private Six six;

    @JsonProperty("7")
    private Seven seven;

    @JsonProperty("8")
    private Eight eight;

    @JsonProperty("9")
    private Nine nine;

    @JsonProperty("10")
    private Ten ten;

    public One getOne() {
        return one;
    }

    public void setOne(One one) {
        this.one = one;
    }

    public Two getTwo() {
        return two;
    }

    public void setTwo(Two two) {
        this.two = two;
    }

    public Three getThree() {
        return three;
    }

    public void setThree(Three three) {
        this.three = three;
    }

    public Four getFour() {
        return four;
    }

    public void setFour(Four four) {
        this.four = four;
    }

    public Five getFive() {
        return five;
    }

    public void setFive(Five five) {
        this.five = five;
    }

    public Six getSix() {
        return six;
    }

    public void setSix(Six six) {
        this.six = six;
    }

    public Seven getSeven() {
        return seven;
    }

    public void setSeven(Seven seven) {
        this.seven = seven;
    }

    public Eight getEight() {
        return eight;
    }

    public void setEight(Eight eight) {
        this.eight = eight;
    }

    public Nine getNine() {
        return nine;
    }

    public void setNine(Nine nine) {
        this.nine = nine;
    }

    public Ten getTen() {
        return ten;
    }

    public void setTen(Ten ten) {
        this.ten = ten;
    }
}
