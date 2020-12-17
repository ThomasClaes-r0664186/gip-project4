package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamDTO;
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

    @Autowired
    private TeamRepository teamRepository;


    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
         this.teamId = teamRepository.save(testTeam).getId();


    }
    @AfterEach
    void tearDown() {
    }

    @Test
    void createTeamOk() throws Exception{
        //Given
        TeamDTO teamDTO = new TeamDTO("teamNaam");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
        .content(toJson(teamDTO))
        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andReturn();
        Team gemaaktTeam = fromMvcResult(mvcResult, Team.class);

        //Then
        assertEquals( teamDTO.getName(), gemaaktTeam.getName());
    }

    @Test // bij deze test kijken we of het te maken team al bestaat op basis van naam
    void createTeamAlreadyExists() throws Exception {
        // given
        TeamDTO teamDTO = new TeamDTO("testTeam");

        // when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
            .content(toJson(teamDTO))
            .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andReturn();

        // then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        //assertEquals("This team: " + teamDTO.getName() + " already exist!", responseMessage);
    }

    @Test // bij deze test kijken we na of de mee te geven teamnaam niet null is (teamDTO in requestbody)
    void CreateTeamNameNotNull() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO(null);

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        //assertEquals("Team is null or empty!", responseMessage);
    }

    @Test // bij deze test kijken we na of de mee te geven teamnaam niet leeg(een lege string) is (teamDTO in requestbody)
    void CreateTeamNameEmpty() throws Exception {
        //Given
        TeamDTO teamDTO = new TeamDTO("");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        //assertEquals("Team is null or empty!", responseMessage);
    }



    @Test
    void updateTeamOk() throws Exception {
        // we willen bij de update niet de organizationName veranderen, maar de organisatie in het algemeen.
        // deze organisatie moet al in de databank zitten
        // Given
        TeamDTO teamDTO = new TeamDTO("veranderdTeam");

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
    }

    @Test
    void updateTeamNameIsNull() throws Exception {
        // we willen bij de update niet de organizationName veranderen, maar de organisatie in het algemeen.
        // deze organisatie moet al in de databank zitten
        // Given
        TeamDTO teamDTO = new TeamDTO(null);

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();


        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        //assertEquals("Team is null or empty!", responseMessage);

    }

    @Test
    void updateTeamNameIsEmpty() throws Exception {
        // we willen bij de update niet de organizationName veranderen, maar de organisatie in het algemeen.
        // deze organisatie moet al in de databank zitten
        // Given
        TeamDTO teamDTO = new TeamDTO("");

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();


        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        //assertEquals("Team is null or empty!", responseMessage);

    }



    @Test
    void getTeam() {
    }

    @Test
    void deleteTeam() {
    }

}