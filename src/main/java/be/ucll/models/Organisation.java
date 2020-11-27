package be.ucll.models;

import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Locale;

//TODO: implement setters for organisation
@Entity
@Table(name = "organisation", schema = "liquibase")
public class Organisation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "name")
    private String name;
    @Column(name ="teams")
    private List<Team> teams = new ArrayList<>();
    @Column(name ="provideID")
    private Long provideID;
    @Column(name ="toernamentID")
    private Long tournamentID;


    public Organisation() {
    }

    private Organisation(OrganisationBuilder organisationBuilder) {
            setId(organisationBuilder.id);
            setName(organisationBuilder.name);
            setTeams(organisationBuilder.teams);
            setProvideID(organisationBuilder.provideID);
            setTournamentID(organisationBuilder.tournamentID);
    }



    public void addTeam(Team team){
        if (team == null){
            teams.add(team);
        }
        else {
            throw new IllegalArgumentException("Can not add empty team!");
        }
    }



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public Long getProvideID() {
        return provideID;
    }

    public void setProvideID(Long provideID) {
        this.provideID = provideID;
    }

    public Long getTournamentID() {
        return tournamentID;
    }

    public void setTournamentID(Long tournamentID) {
        this.tournamentID = tournamentID;
    }

    @Override
    public String toString() {
        return "Organisation{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", teams=" + teams +
                ", provideID=" + provideID +
                ", tournamentID=" + tournamentID +
                '}';
    }

    public static final class OrganisationBuilder {
        private Long id;
        private String name;
        private List<Team> teams;
        private Long provideID;
        private Long tournamentID;

        private OrganisationBuilder() {
        }

        public static OrganisationBuilder anOrganisation() {
            return new OrganisationBuilder();
        }

        public OrganisationBuilder(Organisation copy){
            this.id = copy.id;
            this.name = copy.name;
            this.teams = copy.teams;
            this.provideID = copy.provideID;
            this.tournamentID= copy.tournamentID;
        }

        public OrganisationBuilder withId(Long id) {
            this.id = id;
            return this;
        }

        public OrganisationBuilder withName(String name) {
            this.name = name;
            return this;
        }

        public OrganisationBuilder withTeams(List<Team> teams) {
            this.teams = teams;
            return this;
        }

        public OrganisationBuilder withProvideID(Long provideID) {
            this.provideID = provideID;
            return this;
        }

        public OrganisationBuilder withTournamentID(Long tournamentID) {
            this.tournamentID = tournamentID;
            return this;
        }

        public Organisation build() {
            return new Organisation(this);
        }
    }
}
