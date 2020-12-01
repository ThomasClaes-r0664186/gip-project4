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

    @ManyToOne
    @JoinColumn(name = "organisation_id")
    private Organisation organisation;

    public Team() {
    }
    private Team(TeamBuilder builder) {
        setId(builder.id);
        setName(builder.name);
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

    public static final class TeamBuilder {
        private Long id;
        private String name;

        private TeamBuilder() {
        }
        public TeamBuilder(Team copy) {
            this.id = copy.getId();
            this.name = copy.getName();
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

        public Team build() {
            return new Team(this);
        }
    }
}
