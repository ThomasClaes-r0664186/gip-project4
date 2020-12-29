package be.ucll.service.models.individuallyMatch;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class IndividuallyMatch {

    @JsonProperty("frames")
    private List<Frame> frames;

    public List<Frame> getFrames() {
        return frames;
    }

    public void setFrames(List<Frame> frames) {
        this.frames = frames;
    }
}
