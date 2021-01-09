package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.MatchRepository;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.IndividuallyPlayerDTO;
import be.ucll.dto.MatchHistoryDTO;
import be.ucll.models.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;

public class MatchHistoryResourceTest  extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    MatchHistoryResource matchHistoryResource;

    @Autowired
    private MatchRepository matchRepository;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PlayerRepository playerRepository;

    @Autowired
    private TeamPlayerRepository teamPlayerRepository;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder;

    private Long testTeamId;
    private Long testTeam2Id;


    private Player testPvppowners;
    private Player testDragorius;
    private Player testDorriShokouh;
    private Player testTwinniesDad;
    private Player testXellania;
    private Player testHylloi;
    private Player testMiguelin8;
    private Player testAnagumaInu;
    private Player testMarcoilfuso;

    private Long testMatch1Id;
    private Long testMatch2Id;

    @BeforeEach
    void setUp() throws ParseException {

        passwordEncoder = new BCryptPasswordEncoder();

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).apply(springSecurity()).build();

        Team team = new Team.TeamBuilder()
                .name("TestTeam")
                .build();
        testTeamId = teamRepository.save(team).getId();

        Team team2 = new Team.TeamBuilder()
                .name("TestTeam2")
                .build();
        testTeam2Id = teamRepository.save(team2).getId();



        Player playerPvppowners = new Player.PlayerBuilder()
                .firstName("jaimie")
                .lastName("haesevoets")
                .leagueName("pvppowners")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testPvppowners = playerRepository.save(playerPvppowners);

        Player playerDragorius = new Player.PlayerBuilder()
                .firstName("Charles")
                .lastName("Rubens")
                .leagueName("Dragorius")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testDragorius = playerRepository.save(playerDragorius);

        Player playerDorriShokouh = new Player.PlayerBuilder()
                .firstName("Marie")
                .lastName("Lansier")
                .leagueName("DorriShokouh")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testDorriShokouh = playerRepository.save(playerDorriShokouh);

        Player playerTwinniesDad = new Player.PlayerBuilder()
                .firstName("Fries")
                .lastName("Londaz")
                .leagueName("TwinniesDad")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testTwinniesDad = playerRepository.save(playerTwinniesDad);

        Player playerXellania = new Player.PlayerBuilder()
                .firstName("Frade")
                .lastName("Loeqd")
                .leagueName("Xellania")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testXellania = playerRepository.save(playerXellania);

        Player playerHylloi = new Player.PlayerBuilder()
                .firstName("Feop")
                .lastName("Loi")
                .leagueName("hylloi")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testHylloi = playerRepository.save(playerHylloi);

        Player playerMiguelin8 = new Player.PlayerBuilder()
                .firstName("Geoe")
                .lastName("Maezz")
                .leagueName("miguelin8")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testMiguelin8 = playerRepository.save(playerMiguelin8);

        Player playerAnagumaInu = new Player.PlayerBuilder()
                .firstName("anna")
                .lastName("Iuna")
                .leagueName("AnagumaInu")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testAnagumaInu = playerRepository.save(playerAnagumaInu);

        Player playerMarcoilfuso = new Player.PlayerBuilder()
                .firstName("Marco")
                .lastName("Fusoliner")
                .leagueName("Marcoilfuso")
                .role(Role.PLAYER)
                .password(passwordEncoder.encode("test"))
                .build();
        testMarcoilfuso = playerRepository.save(playerMarcoilfuso);



        TeamPlayer teamPlayer1 = new TeamPlayer.Builder()
                .team(team)
                .player(playerPvppowners)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer1);


        TeamPlayer teamPlayer2 = new TeamPlayer.Builder()
                .team(team)
                .player(playerDragorius)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer2);


        TeamPlayer teamPlayer3 = new TeamPlayer.Builder()
                .team(team)
                .player(playerDorriShokouh)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer3);


        TeamPlayer teamPlayer4 = new TeamPlayer.Builder()
                .team(team2)
                .player(playerTwinniesDad)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer4);

        TeamPlayer teamPlayer5 = new TeamPlayer.Builder()
                .team(team)
                .player(playerXellania)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer5);


        TeamPlayer teamPlayer6 = new TeamPlayer.Builder()
                .team(team2)
                .player(playerPvppowners)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer6);

        TeamPlayer teamPlayer7 = new TeamPlayer.Builder()
                .team(team2)
                .player(playerHylloi)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer7);

        TeamPlayer teamPlayer8 = new TeamPlayer.Builder()
                .team(team2)
                .player(playerMiguelin8)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer8);

        TeamPlayer teamPlayer9 = new TeamPlayer.Builder()
                .team(team2)
                .player(playerAnagumaInu)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer9);

        TeamPlayer teamPlayer10 = new TeamPlayer.Builder()
                .team(team2)
                .player(playerMarcoilfuso)
                .isSelected(true)
                .build();

        teamPlayerRepository.save(teamPlayer10);

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date date1 = simpleDateFormat.parse("20-12-2020");
        Date date2 = simpleDateFormat.parse("21-12-2020");


        Match match1 = new Match.MatchBuilder()
                .date(date1)
                .team1Id(team)
                .matchID(4795138627L)
                .build();

        testMatch1Id = matchRepository.save(match1).getId();

        Match match2 = new Match.MatchBuilder()
                .date(date2)
                .team1Id(team2)
                .matchID(4841161542L)
                .build();

        testMatch2Id = matchRepository.save(match2).getId();

    }


    @Test
    void getMatchHistoryAllOk() throws Exception {
        final String password = "test";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory")
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isOk())
                .andReturn();

    }

    @Test
    void getMatchHistoryFilterDateOk() throws Exception {
        final String DATE = "20-12-2020";
        final String password = "test";
        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory")
                .param("date", DATE)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();

        // this uses a TypeReference to inform Jackson about the Lists's generic type
        List<MatchHistoryDTO> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<MatchHistoryDTO>>() {});

        assertEquals("Sun Dec 20 00:00:00 CET 2020", actual.get(0).getMatchDate());

    }

    @Test
    void getMatchHistoryFilterDateInvalid() throws Exception {
        final String DATE = "*";
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory")
                .param("date", DATE)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(DATE + " is not valid!", responsMessage);
    }

    @Test
    void getMatchHistoryFilterDateNotFound() throws Exception {
        final String DATE = "03-05-2016";
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory")
                .param("date", DATE)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(DATE + " was not found!", responsMessage);
    }

    @Test
    void getMatchHistoryFilterTeamIdOk() throws Exception {
        final String TEAM = testTeamId.toString();
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory")
                .param("teamId", TEAM)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        List<MatchHistoryDTO> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<MatchHistoryDTO>>() {});

        assertEquals(testTeamId, actual.get(0).getTeamId());

    }

    @Test
    void getMatchHistoryFilterTeamIdInvalid() throws Exception {
        final String TEAM = "*";
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory")
                .param("teamId", TEAM)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    void getMatchHistoryFilterTeamIdNotFound() throws Exception {
        final String TEAM = "925";
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory")
                .param("teamId", TEAM)
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(TEAM + " was not found!", responsMessage);

    }


    @Test
    void getIndividuallyMatchHistoryAllOk() throws Exception {
        final String PLAYER_ID = testPvppowners.getId().toString();
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory/" + PLAYER_ID + "/player")
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isOk())
                .andReturn();
    }

    @Test
    void getIndividuallyMatchHistoryALLPlayerNotFound() throws Exception {
        final String PLAYER_ID = "52869";
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory/" + PLAYER_ID + "/player")
                .with(httpBasic(testPvppowners.getLeagueName(), password)))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( PLAYER_ID + " was not found!", responsMessage);

    }

    @Test
    void getIndividuallyMatchHistoryFilterMatchIdOk() throws Exception {
        final String MATCH_ID = testMatch1Id.toString();
        final String PLAYER_ID = testPvppowners.getId().toString();
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory/" + PLAYER_ID + "/player")
                .with(httpBasic(testPvppowners.getLeagueName(), password))
                .param("matchId", MATCH_ID))
                .andExpect(status().isOk())
                .andReturn();

        ObjectMapper mapper = new ObjectMapper();
        List<IndividuallyPlayerDTO> actual = mapper.readValue(mvcResult.getResponse().getContentAsString(), new TypeReference<List<IndividuallyPlayerDTO>>() {});

        assertEquals(MATCH_ID, actual.get(0).getMatchId().toString());

    }

    @Test
    void getIndividuallyMatchHistoryFilterMatchIdInvalid() throws Exception {
        final String MATCH_ID = "*";
        final String PLAYER_ID = testPvppowners.getId().toString();
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory/" + PLAYER_ID + "/player")
                .with(httpBasic(testPvppowners.getLeagueName(), password))
                .param("matchId", MATCH_ID))
                .andExpect(status().isBadRequest())
                .andReturn();

    }

    @Test
    void getIndividuallyMatchHistoryFilterMatchIdNotFound() throws Exception {
        final String MATCH_ID = "9569";
        final String PLAYER_ID = testPvppowners.getId().toString();
        final String password = "test";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory/" + PLAYER_ID  + "/player")
                .with(httpBasic(testPvppowners.getLeagueName(), password))
                .param("matchId", MATCH_ID))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(MATCH_ID + " was not found!", responsMessage);

    }

}
