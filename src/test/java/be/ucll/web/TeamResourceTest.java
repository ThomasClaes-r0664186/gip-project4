package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dto.TeamDTO;
import be.ucll.models.Organisation;
import be.ucll.models.Team;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TeamResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;
    private Organisation organisation;

    @BeforeEach
    void setUp() {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        this.organisation = new Organisation.OrganisationBuilder()
                .name("TestOranisation")
                .providerID(1L)
                .tournamentID(1L)
                .build();

    }

    @Test
    void createTeam() throws Exception{
       // GIVEN
        TeamDTO team = new TeamDTO("lolbroeken", "firstOrganisation");

        //WHEN
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(team))
                .contentType(MediaType.APPLICATION_JSON))
                //.andExpect(status().isCreated())
                //.andExpect(jsonPath("$.name").exists())
                .andReturn();

       // Team gemaaktTeam = fromMvcResult(mvcResult, Team.class);

        String responsMessage = mvcResult.getResponse().getContentAsString();
        System.out.println(responsMessage);

        //THAN
       // assertEquals(gemaaktTeam.getName(), team.getName());
       //assertEquals(gemaaktTeam.getOrganisation().getName(), team.getOrganisationName());

    }

    @AfterEach
    void tearDown() {
    }


    @Test
    void updateTeam() {
    }

    @Test
    void getTeam() {
    }

    @Test
    void deleteTeam() {
    }
}