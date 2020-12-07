package be.ucll.dto;

public class TeamDTO {

    private String name;
    private String organisationName;

    public TeamDTO( String name, String organisationName) {
        this.name = name;
        this.organisationName = organisationName;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getOrganisationName() {
        return organisationName;
    }

    public void setOrganisationName(String organisationName) {
        this.organisationName = organisationName;
    }
}
