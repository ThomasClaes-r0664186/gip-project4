package be.ucll.models;

import org.hibernate.cfg.NotYetImplementedException;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;
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
    @Column(name ="providerID")
    private Long providerID;
    @Column(name ="tournamentID")
    private Long tournamentID;


    public Organisation() {
    }

    private Organisation(OrganisationBuilder organisationBuilder) {
            setId(organisationBuilder.id);
            setName(organisationBuilder.name);
            setProvideID(organisationBuilder.providerID);
            setTournamentID(organisationBuilder.tournamentID);
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

    public Long getProvideID() {
        return providerID;
    }

    public void setProvideID(Long providerID) {
        this.providerID = providerID;
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
                ", provideID=" + providerID +
                ", tournamentID=" + tournamentID +
                '}';
    }

    public static final class OrganisationBuilder {
        private Long id;
        private String name;
        private Long providerID;
        private Long tournamentID;

        private OrganisationBuilder() {
        }

        public static OrganisationBuilder anOrganisation() {
            return new OrganisationBuilder();
        }

        public OrganisationBuilder(Organisation copy){
            this.id = copy.id;
            this.name = copy.name;
            this.providerID = copy.providerID;
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

        public OrganisationBuilder withProviderID(Long providerID) {
            this.providerID = providerID;
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
