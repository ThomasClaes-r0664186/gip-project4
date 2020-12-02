package be.ucll.dto;

//TODO setters not implemented in Player

//TODO add firstname and lastname to playerDTO

// Deze DTO werd oorsponkelijk gebruikt omdat sommige velden overbodig waren. Dit moet nog bekeken worden of deze DTO nog nuttig is.

public class PlayerDTO {
    private Long id;
    private String accountId;
    private String name;

    public PlayerDTO(Long id,String accountId, String name) {
        this.accountId = accountId;
        this.name = name;
        this.id = id;
    }

    public PlayerDTO(String accountId, String name) {
        this.accountId = accountId;
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAccountId() {
        return accountId;
    }

    public void setAccountId(String accountId) {
        this.accountId = accountId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
