package be.ucll.models;

import javax.persistence.*;
import java.util.ArrayList;
//TODO: implement setter for Team
@Entity
@Table(name = "team", schema = "liquibase")
public class Team {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @Column(name = "players")
    private List<Player> players = new ArrayList<>();

    @Column(name = "matches")
    private List<Match> matches = new ArrayList<>();

    public Team() {
    }
    private Team(TeamBuilder builder) {
        setId(builder.id);
        setName(builder.name);
        setPlayers(builder.players);
        setMatches(builder.matches);
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Match> getMatches() {
        return matches;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Match getMatch(Long matchid) {
        for (Match match : matches) {
            if (match.getId().equals(id) {
                //return hier de juiste id
            }
            return match;
        }


        public void addPlayer(Player player){
            if (player == null) {
                players.add(player);
            } else {
                throw new IllegalArgumentException("player can not be null");
            }
        }
        public void removePlayer (Long playerid){
            if (playerid == null) {
                throw new IllegalArgumentException("playerid can not be null");
            } else {
                players.removeIf(player -> player.getId().equals(id));
            }
        }


    }


    public static final class TeamBuilder {
        private Long id;
        private String name;
        private List<Player> players = new ArrayList<>();
        private List<Match> matches = new ArrayList<>();

        private TeamBuilder() {
        }
        public TeamBuilder(Team copy) {
            this.id = copy.getId();
            this.name = copy.getName();
            this.players = copy.getPlayers();
            this.matches= copy.getMatches();

        }
        public static TeamBuilder aTeam() {
            return new TeamBuilder();
        }

        public TeamBuilder withId(Long id) {
            this.id = id;
            return this;
        }
        public TeamBuilder name(String val) {
            name = val;
            return this;
        }

        public TeamBuilder withPlayers(List<Player> players) {
            this.players = players;
            return this;
        }

        public TeamBuilder withMatches(List<Match> matches) {
            this.matches = matches;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }
}
