package be.ucll.models;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

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
    private final List<Player> players = new ArrayList<Player>();

    @Column(name = "matches")
    private final List<Match> matches = new ArrayList<Match>();

    public Team() {
    }
    private Team(TeamBuilder builder) {
        setId(builder.id);
        setName(builder.name);
        this.players.addAll(builder.players);
        this.matches.addAll(builder.matches);
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
            if (matchid != null && match.getId().equals(id)) {
                return match;
            }
        }
        return null;
    }

    public void addPlayer( Player player){
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
            this.players.addAll(copy.getPlayers());
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

        public TeamBuilder players(List<Player> players) {
            this.players = players;
            return this;
        }

        public TeamBuilder matches(List<Match> matches) {
            this.matches = matches;
            return this;
        }

        public Team build() {
            return new Team(this);
        }
    }
}
