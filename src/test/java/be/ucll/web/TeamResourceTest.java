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


    @BeforeEach
    void setUp(){
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
    @AfterEach
    void tearDown() {
    }

    @Test
    void createTeamOk() throws Exception{
        //Given
        TeamDTO teamDTO = new TeamDTO("teamNaam", "firstOrganisation");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
        .content(toJson(teamDTO))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        Team gemaaktTeam = fromMvcResult(mvcResult, Team.class);

        //Then
        assertEquals(gemaaktTeam.getName(), teamDTO.getName());
        assertEquals(gemaaktTeam.getOrganisation().getName(), teamDTO.getOrganisationName());
    }

    @Test // bij deze test kijken we of het te maken team al bestaat op basis van naam
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

    @Test // bij deze test kijken we na of de mee te geven teamnaam niet null is (teamDTO in requestbody)
    void CreateTeamNameNotNull() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO(null, "firstOrganisation");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("Team is null or empty!", responseMessage);
    }

    @Test // bij deze test kijken we na of de mee te geven teamnaam niet leeg(een lege string) is (teamDTO in requestbody)
    void CreateTeamNameNotEmpty() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO("", "firstOrganisation");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("Team is null or empty!", responseMessage);
    }

    @Test // bij deze test kijken we na of de mee te geven organisationName(die in de DB aanwezig moet zijn) al bestaat
    void CreateTeamOrganisationDoesNotExist() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO("test", "secondOrganisation");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This organisation: " + teamDTO.getOrganisationName() + " has not been found!", responseMessage);
    }

    @Test // bij deze test kijken we na of de mee te geven organisationName null is
    void CreateTeamOrganisationIsNotNull() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO("test", null );

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("Organisation is null or empty!", responseMessage);
    }

    @Test // bij deze test kijken we na of de mee te geven organisationName(die in de DB aanwezig moet zijn) al bestaat
    void CreateTeamOrganisationIsNotEmpty() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO("test", "" );

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("Organisation is null or empty!", responseMessage);
    }

    @Test
    void updateTeamOk() throws Exception {
        /*
        //Given
        TeamDTO teamDTO = new TeamDTO("team123", "firstOrganisation");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .param("name", teamDTO.getName())
                .param("")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        Team gemaaktTeam = fromMvcResult(mvcResult, Team.class);

        //Then
        assertEquals(gemaaktTeam.getName(), teamDTO.getName());
        assertEquals(gemaaktTeam.getOrganisation().getName(), teamDTO.getOrganisationName());

         */
    }

    @Test
    void getTeam() {
    }

    @Test
    void deleteTeam() {
    }
}