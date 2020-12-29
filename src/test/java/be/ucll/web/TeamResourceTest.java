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

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TeamResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;


    @BeforeEach
    void setUp(){
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
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
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        teamRepository.save(testTeam).getId();

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
        assertEquals( teamDTO.getName() + " already exists!", responseMessage);
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
        assertEquals("This parameter may not be empty", responseMessage);
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
        assertEquals("This parameter may not be empty", responseMessage);
    }



    @Test
    void updateTeamOk() throws Exception {
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

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
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

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
        assertEquals(teamDTO.getName() + " is not valid!", responseMessage);

    }

    @Test
    void updateTeamNameIsEmpty() throws Exception {
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        // Given
        TeamDTO teamDTO = new TeamDTO("");

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(teamDTO.getName() + " is not valid!", responseMessage);

    }
    @Test
    void getTeamOk() throws Exception {
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + teamId))
                .andExpect(status().isOk())
                .andReturn();
        Team getTeam = fromMvcResult(mvcResult, Team.class);

        assertEquals("testTeam", getTeam.getName());
    }
    @Test
    void getTeamIdIsNegative() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + -5L ))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(-5L +" is not valid!", responsMessage );
    }
    @Test
    void getTeamIdIs0() throws Exception {

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + 0L ))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(0L +" is not valid!", responsMessage );
    }
    
    @Test
    void deleteTeamOk()throws Exception{
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + teamId))
                .andExpect(status().isNoContent())
                .andReturn();
        try {
            Team team = teamRepository.findTeamById(teamId).get();
            fail(); // als er geen exception gegooid word dan faalt de test
        }catch (Exception e){
            assertTrue(true);
        }

        String responsMessage = mvcResult.getResponse().getErrorMessage();
        assertNull(responsMessage);

    }

    @Test
    void deleteTeamId0()throws Exception{
        String ID = "0";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + ID))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(  "0 is not valid!", responsMessage);
    }
}