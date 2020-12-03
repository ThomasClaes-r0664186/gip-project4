package be.ucll.dto;

//TODO setters not implemented in Player

//TODO add firstname and lastname to playerDTO

// Deze DTO werd oorsponkelijk gebruikt omdat sommige velden overbodig waren. Dit moet nog bekeken worden of deze DTO nog nuttig is.

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;

public class PlayerDTO {

    private String leagueName;
    private String firstName;
    private String lastName;

    public PlayerDTO(String leagueName, String firstName, String lastName) {
        this.leagueName = leagueName;
        this.firstName = firstName;
        this.lastName = lastName;
    }


    public String getLeagueName() {
        return leagueName;
    }

    public void setLeagueName(String leagueName) {
        this.leagueName = leagueName;
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
}
