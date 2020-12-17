package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.OrganisationRepository;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamPlayerDTO;
import be.ucll.models.Organisation;
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
    private OrganisationRepository organisationRepository;


    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    private Long testTeamId;
    private Long testLOLnameId;

    @BeforeEach
    void setUp() {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

        Organisation organisation = new Organisation();
        organisation.setName("organisatie");
        organisation.setProvideID(124585658L);
        organisation.setTournamentID(144485558L);
        organisationRepository.save(organisation);


        Team team = new Team.TeamBuilder()
                .name("TestTeam")
                .organisation(organisation)
                .build();
        testTeamId = teamRepository.save(team).getId();

        Team team2 = new Team.TeamBuilder()
                .name("TestTeam2")
                .organisation(organisation)
                .build();
        teamRepository.save(team2);



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
        playerRepository.save(player7Stijn7);

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
        playerRepository.save(player5);

        Player player6 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname6")
                .build();
        playerRepository.save(player6);



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
    void addPlayerToTeamLeagueNameNULL() throws Exception {
        final String LEAGUE_NAME = null;
        final String TEAM_NAME = "TestTeam";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void addPlayerToTeamTeamNameNULL() throws Exception {
        final String LEAGUE_NAME = "LOLname";
        final String TEAM_NAME = null;

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void addPlayerToTeamLeagueNameEmptyL() throws Exception {
        final String LEAGUE_NAME = "";
        final String TEAM_NAME = "TestTeam";

       MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This user: " + LEAGUE_NAME + " has not been found!", responsMessage);

    }

    @Test
    void addPlayerToTeamTeamNameEmpty() throws Exception {
        final String LEAGUE_NAME = "LOLname";
        final String TEAM_NAME = "";

       MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This team: " + TEAM_NAME + " has not been found!", responsMessage);

    }

    @Test
    void addPlayerToTeamLeageNameNotFound() throws Exception {
        final String LEAGUE_NAME = "LOLname123";
        final String TEAM_NAME = "TestTeam";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This user: " + LEAGUE_NAME + " has not been found!", responsMessage);

    }

    @Test
    void addPlayerToTeamPlayerAlreadyInTeam() throws Exception {
        final String LEAGUE_NAME = "7Stijn7";
        final String TEAM_NAME = "TestTeam";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME))
                .andExpect(status().isConflict())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This user: " + LEAGUE_NAME + " is already in this team", responsMessage);

    }

    @Test
    void makePlayerReserveNotMoreThan5ActivePlayersInTeamOK() throws Exception {
        final String LEAGUE_NAME = "LOLname5";
        final String TEAM_NAME = "TestTeam2";
        final String IS_ACTIVE = "false";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME)
                .param("isActive", IS_ACTIVE))
                .andExpect(status().isOk())
                .andReturn();

        TeamPlayerDTO teamPlayer = fromMvcResult(mvcResult, TeamPlayerDTO.class);

        assertEquals(false, teamPlayer.isActive());

    }

    @Test
    void makePlayerReserveNotMoreThan5ActivePlayersInTeam() throws Exception {
        final String LEAGUE_NAME = "LOLname6";
        final String TEAM_NAME = "TestTeam2";
        final String IS_ACTIVE = "true";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME)
                .param("isActive", IS_ACTIVE))
                .andExpect(status().isConflict())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This team: " + TEAM_NAME + " this team has enough active players", responsMessage);

    }


}
