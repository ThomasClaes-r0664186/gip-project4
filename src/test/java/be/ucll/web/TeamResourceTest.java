package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamDTO;
import be.ucll.models.Player;
import be.ucll.models.Role;
import be.ucll.models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class TeamResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp(){
        passwordEncoder = new BCryptPasswordEncoder();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
    }

    @Test
    void createTeamOk() throws Exception{

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
      Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
//Given
        TeamDTO teamDTO = new TeamDTO("teamNaam");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .with(httpBasic(testPvppowners.getLeagueName(), password))
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

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        teamRepository.save(testTeam).getId();

        // given
        TeamDTO teamDTO = new TeamDTO("testTeam");

        // when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .with(httpBasic(testPvppowners.getLeagueName(), password))
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

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        //Given
        TeamDTO teamDTO = new TeamDTO(null);

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .with(httpBasic(testPvppowners.getLeagueName(), password))
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
        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        //Given
        TeamDTO teamDTO = new TeamDTO("");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .with(httpBasic(testPvppowners.getLeagueName(), password))
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        //Then
        String responseMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This parameter may not be empty", responseMessage);
    }

    @Test
    void createTeamNotAuthorization() throws Exception{

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
//Given
        TeamDTO teamDTO = new TeamDTO("teamNaam");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void createTeamBadAuthorizationBadPassword() throws Exception{

        final String PASSWORD = "test";
        final String BAD_PASSWORD = "lala";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
//Given
        TeamDTO teamDTO = new TeamDTO("teamNaam");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .with(httpBasic(testPvppowners.getLeagueName(), BAD_PASSWORD))
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void createTeamBadAuthorizationBadUsername() throws Exception{

        final String PASSWORD = "test";
        final String BAD_USERNAME = "lala";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
//Given
        TeamDTO teamDTO = new TeamDTO("teamNaam");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .with(httpBasic(BAD_USERNAME, PASSWORD))
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void createTeamBadAuthorizationBadRole() throws Exception{

        final String PASSWORD = "test";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
//Given
        TeamDTO teamDTO = new TeamDTO("teamNaam");

        //When
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/team")
                .with(httpBasic(testPvppowners.getLeagueName(), PASSWORD))
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void updateTeamOk() throws Exception {
        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        // Given
        TeamDTO teamDTO = new TeamDTO("veranderdTeam");

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password))
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
    void updateTeamNotAuthorization() throws Exception {
        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
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
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void updateTeamBadAuthorizationBadPassword() throws Exception {

        final String PASSWORD = "test";
        final String BAD_PASSWORD = "lala";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        // Given
        TeamDTO teamDTO = new TeamDTO("veranderdTeam");

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), BAD_PASSWORD))
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void updateTeamBadAuthorizationBadUsername() throws Exception {

        final String PASSWORD = "test";
        final String BAD_USERNAME = "lala";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        // Given
        TeamDTO teamDTO = new TeamDTO("veranderdTeam");

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .with(httpBasic(BAD_USERNAME, PASSWORD))
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void updateTeamBadAuthorizationBadRole() throws Exception {

        final String PASSWORD = "test";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        // Given
        TeamDTO teamDTO = new TeamDTO("veranderdTeam");

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), PASSWORD))
                .content(toJson(teamDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void updateTeamNameIsNull() throws Exception {

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        // Given
        TeamDTO teamDTO = new TeamDTO(null);

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password))
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
        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        // Given
        TeamDTO teamDTO = new TeamDTO("");

        //when
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password))
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

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isOk())
                .andReturn();
        Team getTeam = fromMvcResult(mvcResult, Team.class);

        assertEquals("testTeam", getTeam.getName());
    }

    @Test
    void getTeamNotAuthorization() throws Exception {

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + teamId))
                .andExpect(status().isUnauthorized())
                .andReturn();

    }

    @Test
    void getTeamBadAuthorizationBadPassword() throws Exception {
        final String PASSWORD = "test";
        final String BAD_PASSWORD = "lala";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), BAD_PASSWORD)))
                .andExpect(status().isUnauthorized())
                .andReturn();

    }

    @Test
    void getTeamBadAuthorizationBadUsername() throws Exception {
        final String PASSWORD = "test";
        final String BAD_USERNAME = "lala";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + teamId)
                .with(httpBasic(BAD_USERNAME, PASSWORD)))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void getTeamBadAuthorizationBadRole() throws Exception {

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void getTeamIdIsNegative() throws Exception {
        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + -5L )
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(-5L +" is not valid!", responsMessage );
    }
    @Test
    void getTeamIdIs0() throws Exception {

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/team/" + 0L )
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(0L +" is not valid!", responsMessage );
    }

    @Test
    void deleteTeamOk()throws Exception{

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
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
    void deleteTeamNotAuthorization()throws Exception{

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + teamId))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void deleteTeamBadPassword()throws Exception{

        String PASSWORD = "test";
        String BAD_PASSWORD = "";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), BAD_PASSWORD)))
                .andExpect(status().isUnauthorized())
                .andReturn();

    }

    @Test
    void deleteTeamBadUsername()throws Exception{

        String PASSWORD = "test";
        String BAD_USERNAME = "lala";

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode(PASSWORD))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + teamId)
                .with(httpBasic(BAD_USERNAME, PASSWORD)))
                .andExpect(status().isUnauthorized())
                .andReturn();
    }

    @Test
    void deleteTeamBadAuthorizationBadRole()throws Exception{

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        Team testTeam = new Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(testTeam).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();
    }

    @Test
    void deleteTeamIdNegative()throws Exception{

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
        String ID = "-1";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + ID)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(  ID +" is not valid!", responsMessage);
    }

    @Test
    void deleteTeamId0()throws Exception{

        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.MANAGER)
                .password(passwordEncoder.encode("test"))
                .build();
        Player  testPvppowners = playerRepository.save(playerPvppowners);

        final String password = "test";
String ID = "0";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/team/" + ID)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(  "0 is not valid!", responsMessage);
    }
}