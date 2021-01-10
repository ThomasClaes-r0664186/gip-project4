package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamPlayerDTO;
import be.ucll.models.Player;
import be.ucll.models.Role;
import be.ucll.models.Team;

import be.ucll.models.TeamPlayer;
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
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;

public class TeamPlayerResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;


    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    private Long testTeamId;
    private Long testTeam2Id;


    private Long testLOLnameId;
    private Long test7Stijn7Id;
    private Long testLolname5;
    private Long testLolname6;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private Player testPvppowners;

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();
        passwordEncoder = new BCryptPasswordEncoder();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .password("test")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testPvppowners = playerRepository.save(playerPvppowners);


        Team team = new Team.TeamBuilder()
                .name("TestTeam")
                .build();
        testTeamId = teamRepository.save(team).getId();

        Team team2 = new Team.TeamBuilder()
                .name("TestTeam2")
                .build();
        testTeam2Id = teamRepository.save(team2).getId();



        Player player = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname")
                .password("test")
                .role(Role.PLAYER)
                .build();
        testLOLnameId = playerRepository.save(player).getId();

        Player player7Stijn7 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .password("test")
                .leagueName("7Stijn7")
                .role(Role.PLAYER)
                .build();
        test7Stijn7Id = playerRepository.save(player7Stijn7).getId();

        Player player1 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname1")
                .role(Role.PLAYER)
                .password("test")
                .build();
        playerRepository.save(player1);

        Player player2 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .password("test")
                .leagueName("LOLname2")
                .role(Role.PLAYER)
                .build();
        playerRepository.save(player2);

        Player player3 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname3")
                .role(Role.PLAYER)
                .password("test")
                .build();
        playerRepository.save(player3);

        Player player4 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname4")
                .password("test")
                .role(Role.PLAYER)
                .build();
        playerRepository.save(player4);

        Player player5 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname5")
                .password("test")
                .role(Role.PLAYER)
                .build();
        testLolname5 = playerRepository.save(player5).getId();

        Player player6 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .password("test")
                .leagueName("LOLname6")
                .role(Role.PLAYER)
                .build();
        testLolname6 = playerRepository.save(player6).getId();



        TeamPlayer teamPlayer = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team)
                .player(player7Stijn7)
                .isSelected(false)
                .build());

        teamPlayerRepository.save(teamPlayer);


        TeamPlayer teamPlayer1 = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team)
                .player(player1)
                .isSelected(true)
                .build());

        teamPlayerRepository.save(teamPlayer1);

        TeamPlayer teamPlayer2 = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team2)
                .player(player2)
                .isSelected(true)
                .build());

        teamPlayerRepository.save(teamPlayer2);

        TeamPlayer teamPlayer3 = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team2)
                .player(player3)
                .isSelected(true)
                .build());

        teamPlayerRepository.save(teamPlayer3);

        TeamPlayer teamPlayer4 = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team2)
                .player(player4)
                .isSelected(true)
                .build());

        teamPlayerRepository.save(teamPlayer4);

        TeamPlayer teamPlayer5 = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team2)
                .player(player5)
                .isSelected(true)
                .build());

        teamPlayerRepository.save(teamPlayer5);

        TeamPlayer teamPlayer6 = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team2)
                .player(player6)
                .isSelected(true)
                .build());

        teamPlayerRepository.save(teamPlayer6);

    }


    @Test
    void addPlayerToTeamOk() throws Exception{
        final String password = "test";
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = testTeamId.toString();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player" ).with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isCreated())
                .andReturn();

        TeamPlayerDTO teamPlayer = fromMvcResult(mvcResult, TeamPlayerDTO.class);

        assertEquals("LOLname", teamPlayer.getPlayerName());
        assertEquals("TestTeam", teamPlayer.getTeamName());

    }


    @Test
    void addPlayerToTeamPlayerIdNULL() throws Exception{
        final String password = "test";
        final String ID_PLAYER = null;
        final String ID_TEAM = testTeamId.toString();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void addPlayerToTeamTeamIdNULL() throws Exception{
        final String password = "test";
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = null;

        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void addPlayerToTeamPlayerId0() throws Exception{
        final String password = "test";
        final String ID_PLAYER = "0";
        final String ID_TEAM = testTeamId.toString();

       MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_PLAYER + " is not valid!", responsMessage);

    }

    @Test
    void addPlayerToTeamTeamId0() throws Exception{
        final String password = "test";
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = "0";

       MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " is not valid!", responsMessage);

    }

    @Test
    void addPlayerToTeamPlayerIdNegative() throws Exception{
        final String password = "test";
        final String ID_PLAYER = "-1";
        final String ID_TEAM = testTeamId.toString();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_PLAYER + " is not valid!", responsMessage);

    }

    @Test
    void addPlayerToTeamTeamIdNegative() throws Exception{
        final String password = "test";
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = "-1";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " is not valid!", responsMessage);

    }

    @Test
    void addPlayerToTeamTeamIdNotFound() throws Exception{
        final String password = "test";
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = "9000";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " was not found!", responsMessage);

    }

    @Test
    void addPlayerToTeamPlayerIdNotFound() throws Exception{
        final String password = "test";
        final String ID_PLAYER = "9000";
        final String ID_TEAM = testTeamId.toString();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_PLAYER + " was not found!", responsMessage);

    }

    @Test
    void addPlayerToTeamPlayerAlreadyInTeam() throws Exception{
        final String password = "test";
        final String ID_PLAYER = test7Stijn7Id.toString();
        final String ID_TEAM = testTeamId.toString();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isConflict())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_PLAYER + " already exists!", responsMessage);

    }

    @Test
    void makePlayerReserveNotMoreThan5ActivePlayersInTeamOK() throws Exception{
        final String password = "test";
        final String ID_PLAYER = testLolname5.toString();
        final String ID_TEAM = testTeam2Id.toString();
        final String IS_ACTIVE = "false";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password))
                .param("isActive", IS_ACTIVE))
                .andExpect(status().isOk())
                .andReturn();

        TeamPlayerDTO teamPlayer = fromMvcResult(mvcResult, TeamPlayerDTO.class);

        assertEquals(false, teamPlayer.isActive());

    }

    @Test
    void makePlayerReserveNotMoreThan5ActivePlayersInTeam() throws Exception{
        final String password = "test";
final String ID_PLAYER = testLolname6.toString();
        final String ID_TEAM = testTeam2Id.toString();
        final String IS_ACTIVE = "true";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player").with(httpBasic(testPvppowners.getLeagueName(), password))
                .param("isActive", IS_ACTIVE))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This team: " + ID_TEAM + " this team has enough active players", responsMessage);

    }

    @Test
    void getPlayersFromTeamOk() throws Exception{
        final String password = "test";
        final String ID_TEAM = testTeamId.toString();
        final String EXPECTED_RESPONS = "[{\"leagueName\":\"7Stijn7\",\"firstName\":\"Stijn\",\"lastName\":\"Verbieren\"},{\"leagueName\":\"LOLname1\",\"firstName\":\"Stijn\",\"lastName\":\"Verbieren\"}]";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teamplayer/" + ID_TEAM + "/team").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isOk())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(EXPECTED_RESPONS, responsMessage);
    }

    @Test
    void getPlayersFromTeamTeamId0() throws Exception{
        final String password = "test";
        final String ID_TEAM = "0";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teamplayer/" + ID_TEAM + "/team").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " is not valid!", responsMessage);
    }

    @Test
    void getPlayersFromTeamTeamIdNegative() throws Exception{
        final String password = "test";
        final String ID_TEAM = "-1";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teamplayer/" + ID_TEAM + "/team").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " is not valid!", responsMessage);
    }

    @Test
    void getPlayersFromTeamTeamIdNotFound() throws Exception{
        final String password = "test";
        final String ID_TEAM = "90000";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teamplayer/" + ID_TEAM + "/team").with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " was not found!", responsMessage);
    }

    @Test
    void deletePlayerFromTeamOk() throws Exception{
        final String password = "test";
        final String ID_TEAM = testLolname5.toString();
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/teamplayer/" + ID_TEAM + "/player").with(httpBasic(testPvppowners.getLeagueName(), password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("", responsMessage );
    }

    @Test
    void deletePlayerFromTeam0() throws Exception{
        final String password = "test";
        final String ID_TEAM = "0";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/teamplayer/" + ID_TEAM + "/player").with(httpBasic(testPvppowners.getLeagueName(), password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " is not valid!", responsMessage );
    }

    @Test
    void deletePlayerFromTeamNegative() throws Exception{
        final String password = "test";
        final String ID_TEAM = "-1";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/teamplayer/" + ID_TEAM + "/player").with(httpBasic(testPvppowners.getLeagueName(), password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " is not valid!", responsMessage );
    }

    @Test
    void deletePlayerFromTeamNotFound() throws Exception{
        final String password = "test";
        final String ID_TEAM = "90000";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.delete("/teamplayer/" + ID_TEAM + "/player").with(httpBasic(testPvppowners.getLeagueName(), password))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( ID_TEAM + " was not found!", responsMessage );
    }



}
