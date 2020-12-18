package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamPlayerDTO;
import be.ucll.models.Player;
import be.ucll.models.Team;

import be.ucll.models.TeamPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();



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
                .build();
        testLOLnameId = playerRepository.save(player).getId();

        Player player7Stijn7 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("7Stijn7")
                .build();
        test7Stijn7Id = playerRepository.save(player7Stijn7).getId();

        Player player1 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname1")
                .build();
        playerRepository.save(player1);

        Player player2 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname2")
                .build();
        playerRepository.save(player2);

        Player player3 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname3")
                .build();
        playerRepository.save(player3);

        Player player4 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname4")
                .build();
        playerRepository.save(player4);

        Player player5 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname5")
                .build();
        testLolname5 = playerRepository.save(player5).getId();

        Player player6 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname6")
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
    void addPlayerToTeamOk() throws Exception {
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = testTeamId.toString();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player" ))
                .andExpect(status().isCreated())
                .andReturn();

        TeamPlayerDTO teamPlayer = fromMvcResult(mvcResult, TeamPlayerDTO.class);

        assertEquals("LOLname", teamPlayer.getPlayerName());
        assertEquals("TestTeam", teamPlayer.getTeamName());

    }


    @Test
    void addPlayerToTeamPlayerIdNULL() throws Exception {
        final String ID_PLAYER = null;
        final String ID_TEAM = testTeamId.toString();

        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void addPlayerToTeamTeamIdNULL() throws Exception {
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = null;

        this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void addPlayerToTeamPlayerId0() throws Exception {
        final String ID_PLAYER = "0";
        final String ID_TEAM = testTeamId.toString();

       MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_PLAYER + " is not valid!", responsMessage);

    }

    @Test
    void addPlayerToTeamTeamId0() throws Exception {
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = "0";

       MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " is not valid!", responsMessage);

    }

    @Test
    void addPlayerToTeamPlayerIdNegative() throws Exception {
        final String ID_PLAYER = "-1";
        final String ID_TEAM = testTeamId.toString();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_PLAYER + " is not valid!", responsMessage);

    }

    @Test
    void addPlayerToTeamTeamIdNegative() throws Exception {
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = "-1";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " is not valid!", responsMessage);

    }

    @Test
    void addPlayerToTeamTeamIdNotFound() throws Exception {
        final String ID_PLAYER = testLOLnameId.toString();
        final String ID_TEAM = "9000";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_TEAM + " was not found!", responsMessage);

    }

    @Test
    void addPlayerToTeamPlayerIdNotFound() throws Exception {
        final String ID_PLAYER = "9000";
        final String ID_TEAM = testTeamId.toString();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_PLAYER + " was not found!", responsMessage);

    }

    @Test
    void addPlayerToTeamPlayerAlreadyInTeam() throws Exception {
        final String ID_PLAYER = test7Stijn7Id.toString();
        final String ID_TEAM = testTeamId.toString();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player"))
                .andExpect(status().isConflict())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(ID_PLAYER + " already exists!", responsMessage);

    }

    @Test
    void makePlayerReserveNotMoreThan5ActivePlayersInTeamOK() throws Exception {
        final String ID_PLAYER = testLolname5.toString();
        final String ID_TEAM = testTeam2Id.toString();
        final String IS_ACTIVE = "false";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player")
                .param("isActive", IS_ACTIVE))
                .andExpect(status().isOk())
                .andReturn();

        TeamPlayerDTO teamPlayer = fromMvcResult(mvcResult, TeamPlayerDTO.class);

        assertEquals(false, teamPlayer.isActive());

    }

    @Test
    void makePlayerReserveNotMoreThan5ActivePlayersInTeam() throws Exception {
        final String ID_PLAYER = testLolname6.toString();
        final String ID_TEAM = testTeam2Id.toString();
        final String IS_ACTIVE = "true";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/teamplayer/" + ID_TEAM + "/team/" + ID_PLAYER + "/player")
                .param("isActive", IS_ACTIVE))
                .andExpect(status().isConflict())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This team: " + ID_TEAM + " this team has enough active players", responsMessage);

    }

    @Test
    void getPlayersFromTeamOk() throws Exception {
        final String ID_TEAM = testTeamId.toString();
        final String EXPECTED_RESPONS = "[{\"leagueName\":\"7Stijn7\",\"firstName\":\"Stijn\",\"lastName\":\"Verbieren\"},{\"leagueName\":\"LOLname1\",\"firstName\":\"Stijn\",\"lastName\":\"Verbieren\"}]";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/teamplayer/" + ID_TEAM + "/team"))
                .andExpect(status().isOk())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(EXPECTED_RESPONS, responsMessage);
    }

}
