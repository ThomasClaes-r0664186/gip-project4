package be.ucll.models;

import javax.persistence.*;

//TODO setters not implemented in Player
@Entity
@Table(name = "player", schema= "liquibase")
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    @Column(name = "summonerID")
    private String summonerID;
    @Column(name = "puuID")
    private String puuID;
    @Column(name = "accountId")
    private String accountId;
    @Column(name = "leagueName")
    private String leagueName;
    @Column(name = "firstName")
    private String firstName;
    @Column(name = "lastName")
    private String lastName;


    private Player(PlayerBuilder playerBuilder){
        setId(playerBuilder.id);
        setAccountId(playerBuilder.accountId);
        setLeagueName(playerBuilder.leagueName);
        setFirstName(playerBuilder.firstName);
        setLastName(playerBuilder.lastName);
        setPuuID(playerBuilder.puuID);
        setSummonerID(playerBuilder.summonerID);
    }

    public Player(){

    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSummonerID() {
        return summonerID;
    }

    public void setSummonerID(String summonerID) {
        this.summonerID = summonerID;
    }

    public String getPuuID() {
        return puuID;
    }

    public void setPuuID(String puuID) {
        this.puuID = puuID;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String name) {
        this.leagueName = name;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public static final class PlayerBuilder {
        private Long id;
        private String accountId;
        private String leagueName;
        private String firstName;
        private String lastName;
        private String summonerID;
        private String puuID;

        public PlayerBuilder() {
        }

        public static PlayerBuilder aPlayer() {
            return new PlayerBuilder();
        }

        public PlayerBuilder(Player copy){
            this.id = copy.id;
            this.accountId = copy.accountId;
            this.leagueName = copy.leagueName;
            this.firstName = copy.leagueName;
            this.lastName = copy.lastName;
            this.summonerID = copy.summonerID;
            this.puuID = copy.puuID;
        }

        public PlayerBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public PlayerBuilder accountId(String accountId) {
            this.accountId = accountId;
            return this;
        }

        public PlayerBuilder leagueName(String name) {
            this.leagueName = name;
            return this;
        }

        public PlayerBuilder firstName(String name) {
            this.firstName = name;
            return this;
        }

        public PlayerBuilder lastName(String name) {
            this.lastName = name;
            return this;
        }

        public PlayerBuilder summonerID(String summonerID) {
            this.summonerID = summonerID;
            return this;
        }

        public PlayerBuilder puuID(String puuID) {
            this.puuID = puuID;
            return this;
        }

        public Player build() {
            return new Player(this);
        }
    }
}
