package be.ucll.dto;

public class TeamDTO {

    private String name;
    private Long organisationId;

    public TeamDTO(String name, Long organisationId) {
        this.name = name;
        this.organisationId = organisationId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getOrganisationId() {
        return organisationId;
    }

    public void setOrganisationId(Long organisationId) {
        this.organisationId = organisationId;
    }
}
