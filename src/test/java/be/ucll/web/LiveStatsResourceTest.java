package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.models.Player;
import be.ucll.models.Role;
import be.ucll.models.Team;
import be.ucll.models.TeamPlayer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class LiveStatsResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    private Player testPvppowners;

    @BeforeEach
    void setUp() {

        passwordEncoder = new BCryptPasswordEncoder();
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac)
                .apply(springSecurity()).build();
        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testPvppowners = playerRepository.save(playerPvppowners);
    }

    @Test
    void getLiveStatsTeamId0() throws Exception {
        final String password = "test";
        final String TEAM_ID = "0";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/liveStats/" + TEAM_ID)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( TEAM_ID + " is not valid!", responsMessage );

    }

    @Test
    void getLiveStatsTeamIdNegative() throws Exception {
        final String password = "test";
        final String TEAM_ID = "-1";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/liveStats/" + TEAM_ID)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( TEAM_ID + " is not valid!", responsMessage );

    }

    @Test
    void getLiveStatsTeamIdNotFound() throws Exception {
        final String password = "test";
        final String TEAM_ID = "10";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/liveStats/" + TEAM_ID)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( TEAM_ID + " was not found!", responsMessage );

    }

    @Test
    void getLiveStatsTeamPlayersNotFound() throws Exception {
        final String password = "test";        Team team = new Team.TeamBuilder()
                .name("team")
                .build();

        Long teamId = teamRepository.save(team).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/liveStats/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("teamPlayers of " + teamId + " was not found!", responsMessage );

    }

    @Test
    void getLiveStatsOfPlayersNotFound() throws Exception {
        final String password = "test";        Player player = new Player.PlayerBuilder()
                .leagueName("7Stijn7")
                .firstName("Stijn")
                .lastName("Verbieren")
                .summonerID("1xXnvDRMw7EtYUfyqVVE4axhW-TywmiIfVVIZ72dOd92u08")
                .accountId("b8PgZkOcjRUx7oiHgkD_BhJJ7B3rIGOwMN_1crvtdep39KA")
                .puuID("saaENlT6jyW8dyAS3nOyk8SRXQjXs7qubys0Pls06P9Dk8hVgGOVgntYxAQilz_OxlJbQBL-0Ay5rw")
                .build();

        Team team = new Team.TeamBuilder()
                .name("team")
                .build();

       Long teamId = teamRepository.save(team).getId();

        playerRepository.save(player).getId();

        TeamPlayer teamPlayer = new TeamPlayer.Builder()
                .team(team)
                .player(player)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/liveStats/" + teamId)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("live stats of players in team " + teamId + " was not found!", responsMessage );

    }

}
