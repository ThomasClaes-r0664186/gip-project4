package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.OrganisationRepository;
import be.ucll.dao.TeamRepository;
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
    private Long teamId;
    private Long currentOrganisationId;
    private Long newOrganisationId;
    @Autowired
    private OrganisationRepository organisationRepository;

    @Autowired
    private TeamRepository teamRepository;


    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        Organisation currentOrganisation = new Organisation.OrganisationBuilder()
                .name("firstOrganisation")
                .providerID(1L)
                .tournamentID(1L)
                .build();
        this.currentOrganisationId = organisationRepository.save(currentOrganisation).getId();

        Organisation newOrganisation = new Organisation.OrganisationBuilder()
                .name("secondOrganisation")
                .providerID(2L)
                .tournamentID(2L)
                .build();
        this.newOrganisationId = organisationRepository.save(newOrganisation).getId();

        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .organisation(currentOrganisation)
                .build();
         this.teamId = teamRepository.save(testTeam).getId();


    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void createTeamOk() throws Exception{
        //Given
        TeamDTO teamDTO = new TeamDTO("teamNaam", currentOrganisationId);

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
        .content(toJson(teamDTO))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        Team gemaaktTeam = fromMvcResult(mvcResult, Team.class);

        //Then
        assertEquals( teamDTO.getName(), gemaaktTeam.getName());
        assertEquals(currentOrganisationId, gemaaktTeam.getOrganisation().getId());
    }

    @Test // bij deze test kijken we of het te maken team al bestaat op basis van naam
    void createTeamAlreadyExists() throws Exception {
        // given
        TeamDTO teamDTO = new TeamDTO("testTeam", currentOrganisationId);

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
        TeamDTO teamDTO = new TeamDTO(null, currentOrganisationId);

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
    void CreateTeamNameEmpty() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO("", currentOrganisationId);

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
        TeamDTO teamDTO = new TeamDTO("test", 50L);

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("Please specify a valid id!", responseMessage);
    }

    @Test // bij deze test kijken we na of de mee te geven organisationName null is
    void CreateTeamOrganisationIsNull() throws Exception {
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

    @Test // bij deze test kijken we na of de mee te geven organisationName null is
    void CreateTeamOrganisationIsNegative() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO("test", -5L );

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
    void updateTeamNameOk() throws Exception {
        // we willen bij de update niet de organizationName veranderen, maar de organisatie in het algemeen.
        // deze organisatie moet al in de databank zitten
        // Given
        TeamDTO teamDTO = new TeamDTO("veranderdTeam", currentOrganisationId);

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // we willen teamDTO mappen naar een team -->
        Team t = fromMvcResult(mvcResult, Team.class);

        //Then
        assertEquals("veranderdTeam", t.getName());
        assertEquals(currentOrganisationId, t.getOrganisation().getId());
    }

    @Test
    void updateTeamOrganisationOk() throws Exception {
        // we willen bij de update niet de organizationName veranderen, maar de organisatie in het algemeen.
        // deze organisatie moet al in de databank zitten
        // Given
        TeamDTO teamDTO = new TeamDTO("testTeam", newOrganisationId);

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // we willen teamDTO mappen naar een team -->
        Team t = fromMvcResult(mvcResult, Team.class);

        //Then
        assertEquals("testTeam", t.getName());
        assertEquals(newOrganisationId, t.getOrganisation().getId());
    }

    @Test
    void updateTeamNameIsNull() throws Exception {
        // we willen bij de update niet de organizationName veranderen, maar de organisatie in het algemeen.
        // deze organisatie moet al in de databank zitten
        // Given
        TeamDTO teamDTO = new TeamDTO(null, currentOrganisationId);

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("Team is null or empty!", responseMessage);

    }

    @Test
    void updateTeamNameIsEmpty() throws Exception {
        // we willen bij de update niet de organizationName veranderen, maar de organisatie in het algemeen.
        // deze organisatie moet al in de databank zitten
        // Given
        TeamDTO teamDTO = new TeamDTO("", currentOrganisationId);

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("Team is null or empty!", responseMessage);

    }

    @Test
    void updateTeamOrganisationIsNull() throws Exception {
        // we willen bij de update niet de organizationName veranderen, maar de organisatie in het algemeen.
        // deze organisatie moet al in de databank zitten
        // Given
        TeamDTO teamDTO = new TeamDTO("", null);

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("Team is null or empty!", responseMessage);

    }

    @Test
    void getTeam() {
    }

    @Test
    void deleteTeam() {
    }

}