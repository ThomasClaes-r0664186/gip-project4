package be.ucll.models;

import org.springframework.data.annotation.Id;

import javax.persistence.*;

@Entity
@Table(name = "Player", schema= "liquibase")

///TODO setters not implemented in Player
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "match")
    private final List<Match>matches;
    @Column(name = "accountId")
    private String accountId;
    @Column(name = "name")
    private String name;


    private Player(PlayerBuilder playerBuilder){
        setId(playerBuilder.id);
        setAccountId(playerBuilder.accountId);
        setName(playerBuilder.name);
    }

    public Player(String name){
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public List<Match> getMatches() {
        return matches;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static final class PlayerBuilder {
        private Long id;
        private List<Match> matches;
        private String accountId;
        private String name;

        private PlayerBuilder() {
        }

        public static PlayerBuilder aPlayer() {
            return new PlayerBuilder();
        }

        public PlayerBuilder(Player copy){
            this.id = copy.id;
            this.accountId = copy.accountId;
            this.name = copy.name;
            this.matches = copy.matches;
        }

        public PlayerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PlayerBuilder matches(List<Match> matches) {
            this.matches = matches;
            return this;
        }

        public PlayerBuilder id(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public PlayerBuilder name(String name) {
            this.name = name;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
