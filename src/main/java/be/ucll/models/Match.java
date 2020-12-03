package be.ucll.models;

import javax.persistence.*;
import java.util.Date;

//Todo setters for match
@Entity
@Table(name ="match", schema = "liquibase")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Date date;

    @ManyToOne
    @JoinColumn(name = "team1_id")
    private Team team1;

    @ManyToOne
    @JoinColumn(name = "team2_id")
    private Team team2;

    @Column(name = "winner_id")
    private Long winnerId;

    @Column(name = "match_id")
    private Long matchID;

    @Column(name = "tournament_code")
    private String tournamentCode;

    public Match(){}

    private Match(MatchBuilder builder){
        setId(builder.id);
        setDate(builder.date);
        setTeam1(builder.team1);
        setTeam2(builder.team2);
        setWinnerId(builder.winnerId);
        setMatchId(builder.matchID);
        setTournamentCode(builder.tournamentCode);
    }

    public Long getId(){
        return id;
    }

    public void setId(Long id){
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Team getTeam1() {
        return team1;
    }

    public void setTeam1(Team team1) {
        this.team1 = team1;
    }

    public Team getTeam2() {
        return team2;
    }

    public void setTeam2(Team team2) {
        this.team2 = team2;
    }

    public Long getWinnerId() {
        return winnerId;
    }

    public void setWinnerId(Long winnerId) {
        this.winnerId = winnerId;
    }

    public Long getMatchId() {
        return matchID;
    }

    public void setMatchId(Long matchID) {
        this.matchID = matchID;
    }

    public String getTournamentCode() {
        return tournamentCode;
    }

    public void setTournamentCode(String tournamentCode) {
        this.tournamentCode = tournamentCode;
    }

    public static final class MatchBuilder {
        private Long id;
        private Date date;
        private Team team1;
        private Team team2;
        private Long winnerId;
        private Long matchID;
        private String tournamentCode;

        private MatchBuilder() {
        }

        public MatchBuilder(Match copy){
            this.id = copy.id;
            this.date = copy.date;
            this.team1 = copy.team1;
            this.winnerId = copy.winnerId;
            this.team2 = copy.team2;
            this.matchID = copy.matchID;
            this.tournamentCode = copy.tournamentCode;
        }

        public MatchBuilder id(Long id) {
            this.id = id;
            return this;
        }

        public MatchBuilder date(Date date) {
            this.date = date;
            return this;
        }

        public MatchBuilder team1Id(Team team1) {
            this.team1 = team1;
            return this;
        }

        public MatchBuilder team2Id(Team team2) {
            this.team2 = team2;
            return this;
        }

        public MatchBuilder winnerId(Long winnerId) {
            this.winnerId = winnerId;
            return this;
        }

        public MatchBuilder matchID(Long matchID) {
            this.matchID = matchID;
            return this;
        }

        public MatchBuilder tournamentCode(String tournamentCode) {
            this.tournamentCode = tournamentCode;
            return this;
        }

        public Match build() {
            return new Match(this);
        }
    }

    @Override
    public String toString(){
        return "Match{" +
                    "id=" + id +
                    ", date='" + date.toString() + '\'' +
                    ", team1Id='" + team1.toString() + '\'' +
                    ", team2Id='" + team2.toString() + '\'' +
                    ", winnerId='" + winnerId.toString() + '\'' +
                    ", matchID='" + matchID.toString() + '\'' +
                    ", tournamentCode='" + tournamentCode + '\'' +
                    '}';
    }
}
