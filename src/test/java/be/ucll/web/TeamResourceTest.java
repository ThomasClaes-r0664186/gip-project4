package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.OrganisationRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamDTO;
import be.ucll.exceptions.OrganisationNotFound;
import be.ucll.exceptions.TeamAlreadyExists;
import be.ucll.models.Organisation;
import be.ucll.models.Team;
import org.junit.Assert;
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


    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private TeamResource teamResource;

    @BeforeEach
    void setUp() throws TeamAlreadyExists, OrganisationNotFound {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        Organisation organisation = new Organisation.OrganisationBuilder()
                .name("firstOrganisation")
                .providerID(1L)
                .tournamentID(1L)
                .build();
        organisationRepository.save(organisation);

        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .organisation(organisation)
                .build();
        teamRepository.save(testTeam);


    }

    @Test
    void createTeam() throws Exception{

    }

    @Test
    void createTeamAlreadyExists() throws Exception {
        // given
        TeamDTO teamDTO = new TeamDTO("testTeam", "firstOrganisation");

        // when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
            .content(toJson(teamDTO))
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        // then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This team: " + teamDTO.getName() + " already exist!", responseMessage);
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