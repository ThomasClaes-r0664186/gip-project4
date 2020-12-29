package be.ucll.service.models.currentGameInfo;

import java.util.List;

public class Perks {

    private List<Integer> perkIds = null;
    private int perkStyle;
    private int perkSubStyle;

    public List<Integer> getPerkIds() {
        return perkIds;
    }

    public void setPerkIds(List<Integer> perkIds) {
        this.perkIds = perkIds;
    }

    public int getPerkStyle() {
        return perkStyle;
    }

    public void setPerkStyle(int perkStyle) {
        this.perkStyle = perkStyle;
    }

    public int getPerkSubStyle() {
        return perkSubStyle;
    }

    public void setPerkSubStyle(int perkSubStyle) {
        this.perkSubStyle = perkSubStyle;
    }

}