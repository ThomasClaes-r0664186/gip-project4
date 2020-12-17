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

    @Column(name = "is_winner")
    private boolean isWinner;

    @Column(name = "match_id")
    private Long matchID;

    public Match(){}

    private Match(MatchBuilder builder){
        setId(builder.id);
        setDate(builder.date);
        setTeam1(builder.team1);
        setIsWinner(builder.isWinner);
        setMatchId(builder.matchID);
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

    public boolean getIsWinner() {
        return isWinner;
    }

    public void setIsWinner(boolean isWinner) {
        this.isWinner = isWinner;
    }

    public Long getMatchId() {
        return matchID;
    }

    public void setMatchId(Long matchID) {
        this.matchID = matchID;
    }

    public static final class MatchBuilder {
        private Long id;
        private Date date;
        private Team team1;
        private boolean isWinner;
        private Long matchID;

        public MatchBuilder() {
        }

        public MatchBuilder(Match copy){
            this.id = copy.id;
            this.date = copy.date;
            this.team1 = copy.team1;
            this.isWinner = copy.isWinner;
            this.matchID = copy.matchID;
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

        public MatchBuilder isWinner(boolean isWinner) {
            this.isWinner = isWinner;
            return this;
        }

        public MatchBuilder matchID(Long matchID) {
            this.matchID = matchID;
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
                    ", isWinner='" + isWinner + '\'' +
                    ", matchID='" + ((matchID==null)?"":matchID.toString()) + '\'' +
                    '}';
    }
}
