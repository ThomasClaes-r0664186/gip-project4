package be.ucll.models;

import javax.persistence.*;

@Entity
@Table(name= "team_player", schema= "liquibase" )
public class TeamPlayer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name="player_id")
    private Player player;

    @Column(name="is_selected")
    private Boolean isSelected;

    public TeamPlayer(Builder builder){
        setId(builder.id);
        setTeam(builder.team);
        setPlayer(builder.player);
        setSelected(builder.isSelected);
    }

    public TeamPlayer (){}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Boolean isSelected() {
        return isSelected;
    }

    public void setSelected(Boolean selected) {
        isSelected = selected;
    }

    public static final class Builder {
        private Long id;
        private Team team;
        private Player player;
        private Boolean isSelected;

        public Builder(TeamPlayer copy) {
            this.id = copy.getId();
            this.team = copy.getTeam();
            this.isSelected = copy.isSelected();
            this.player = copy.getPlayer();
        }

        public Builder() {

        }

        public Builder id(Long id) {
            this.id = id;
            return this;
        }

        public Builder team(Team team) {
            this.team = team;
            return this;
        }

        public Builder player(Player player) {
            this.player = player;
            return this;
        }

        public Builder isSelected(Boolean isSelected) {
            this.isSelected = isSelected;
            return this;
        }

        public TeamPlayer build() {
            return new TeamPlayer(this);
        }
    }

    @Override
    public String toString() {
        return "TeamPlayer{" +
                "id=" + id +
                ", teamId=" + team +
                ", playerID=" + player +
                ", isSelected=" + isSelected +
                '}';
    }
}
