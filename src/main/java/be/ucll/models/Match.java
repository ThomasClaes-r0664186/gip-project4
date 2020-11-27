package be.ucll.models;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name ="match", schema = "liquibase")
public class Match {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "date")
    private Date date;

    @Column(name = "team1Id")
    private Long team1Id;

    @Column(name = "team2Id")
    private Long team2Id;

    @Column(name = "winnerId")
    private Long winnerId;

    @Column(name = "matchID")
    private Long matchID;

    @Column(name = "tournamentCode")
    private String tournamentCode;

    //todo: implement setters for Match.

    public Match(){}

    private Match(MatchBuilder builder){
        setId(builder.id);
        setDate(builder.date);
        setTeam1Id(builder.team1Id);
        setTeam2Id(builder.team2Id);
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

    public Long getTeam1Id() {
        return team1Id;
    }

    public void setTeam1Id(Long team1Id) {
        this.team1Id = team1Id;
    }

    public Long getTeam2Id() {
        return team2Id;
    }

    public void setTeam2Id(Long team2Id) {
        this.team2Id = team2Id;
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
        private Long team1Id;
        private Long team2Id;
        private Long winnerId;
        private Long matchID;
        private String tournamentCode;

        private MatchBuilder() {
        }

        public MatchBuilder(Match copy){
            this.id = copy.id;
            this.date = copy.date;
            this.team1Id = copy.team1Id;
            this.winnerId = copy.winnerId;
            this.team2Id = copy.team2Id;
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

        public MatchBuilder team1Id(Long team1Id) {
            this.team1Id = team1Id;
            return this;
        }

        public MatchBuilder team2Id(Long team2Id) {
            this.team2Id = team2Id;
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
                    ", team1Id='" + team1Id.toString() + '\'' +
                    ", team2Id='" + team2Id.toString() + '\'' +
                    ", winnerId='" + winnerId.toString() + '\'' +
                    ", matchID='" + matchID.toString() + '\'' +
                    ", tournamentCode='" + tournamentCode + '\'' +
                    '}';
    }
}
