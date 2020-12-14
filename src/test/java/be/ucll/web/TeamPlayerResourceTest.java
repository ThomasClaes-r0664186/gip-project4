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
        teamRepository.save(team);

        Player player = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("LOLname")
                .build();
        playerRepository.save(player);

        Player player7Stijn7 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("7Stijn7")
                .build();
        playerRepository.save(player7Stijn7);

        TeamPlayer teamPlayer = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team)
                .player(player7Stijn7)
                .isSelected(false)
                .build());

        teamPlayerRepository.save(teamPlayer);

    }


    @Test
    void addPlayerToTeamOk() throws Exception {
        final String LEAGUE_NAME = "LOLname";
        final String TEAM_NAME = "TestTeam";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME))
                .andExpect(status().isCreated())
                .andReturn();

        TeamPlayerDTO teamPlayer = fromMvcResult(mvcResult, TeamPlayerDTO.class);

        assertEquals(LEAGUE_NAME, teamPlayer.getPlayerName());
        assertEquals(TEAM_NAME, teamPlayer.getTeamName());

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
    void addPlayerToTeamPlayerNotMoreThan5ActivePlayersInTeam() throws Exception {
        final String LEAGUE_NAME = "LOLname";
        final String TEAM_NAME = "TestTeam";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/teamplayer")
                .param("leagueName", LEAGUE_NAME)
                .param("teamName", TEAM_NAME))
                .andExpect(status().isConflict())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This team: " + LEAGUE_NAME + " this team has enough active players", responsMessage);

    }


}
