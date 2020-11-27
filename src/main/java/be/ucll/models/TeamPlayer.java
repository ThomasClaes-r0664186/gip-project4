package be.ucll.models;

import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.*;

//TODO: implement setter for TeamPLayer
@Entity
@Table(name= "TeamPlayer", schema= "liquibase" )
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="teamId")
    private Long teamId;

    @Column(name="playerID")
    private Long playerID;

    @Column(name="isSelected")
    private Boolean isSelected;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        throw new NotYetImplementedException();
        this.id = id;
    }

    public Long getTeamId() {
        return teamId;
    }

    public void setTeamId(Long teamId) {

        throw new NotYetImplementedException();
        this.teamId = teamId;
    }

    public Long getPlayerID() {
        return playerID;
    }

    public void setPlayerID(Long playerID) {
        throw new NotYetImplementedException();
        this.playerID = playerID;
    }

    public Boolean isSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        throw new NotYetImplementedException();
        isSelected = selected;
    }

    public static final class Builder {
        private Long id;
        private Long teamId;
        private Long playerID;
        private Boolean isSelected;

        private Builder() {
        }

        public static Builder teamPlayer() {
            return new Builder();
        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder teamId(Long teamId) {
            this.teamId = teamId;
            return this;
        }

        public Builder playerID(Long playerID) {
            this.playerID = playerID;
            return this;
        }

        public Builder isSelected(Boolean isSelected) {
            this.isSelected = isSelected;
            return this;
        }

        public TeamPlayer build() {
            TeamPlayer teamPlayer = new TeamPlayer();
            teamPlayer.setId(id);
            teamPlayer.setTeamId(teamId);
            teamPlayer.setPlayerID(playerID);
            teamPlayer.isSelected = this.isSelected;
            return teamPlayer;
        }
    }

}
