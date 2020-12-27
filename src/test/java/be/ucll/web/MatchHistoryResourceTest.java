package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.MatchRepository;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.models.Match;
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


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

    private Long testTeamId;
    private Long testTeam2Id;


    private Long testPvppownersId;
    private Long testDragoriusId;
    private Long testDorriShokouhId;
    private Long testTwinniesDadId;
    private Long testXellaniaId;
    private Long testHylloiId;
    private Long testMiguelin8Id;
    private Long testAnagumaInuId;
    private Long testMarcoilfusoId;

    @BeforeEach
    void setUp() throws ParseException {

        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();

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
                .build();
        testPvppownersId = playerRepository.save(playerPvppowners).getId();

        Player playerDragorius = new Player.PlayerBuilder()
                .firstName("Charles")
                .lastName("Rubens")
                .leagueName("Dragorius")
                .build();
        testDragoriusId = playerRepository.save(playerDragorius).getId();

        Player playerDorriShokouh = new Player.PlayerBuilder()
                .firstName("Marie")
                .lastName("Lansier")
                .leagueName("DorriShokouh")
                .build();
        testDorriShokouhId = playerRepository.save(playerDorriShokouh).getId();

        Player playerTwinniesDad = new Player.PlayerBuilder()
                .firstName("Fries")
                .lastName("Londaz")
                .leagueName("TwinniesDad")
                .build();
        testTwinniesDadId = playerRepository.save(playerTwinniesDad).getId();

        Player playerXellania = new Player.PlayerBuilder()
                .firstName("Frade")
                .lastName("Loeqd")
                .leagueName("Xellania")
                .build();
        testXellaniaId = playerRepository.save(playerXellania).getId();

        Player playerHylloi = new Player.PlayerBuilder()
                .firstName("Feop")
                .lastName("Loi")
                .leagueName("hylloi")
                .build();
        testHylloiId = playerRepository.save(playerHylloi).getId();

        Player playerMiguelin8 = new Player.PlayerBuilder()
                .firstName("Geoe")
                .lastName("Maezz")
                .leagueName("miguelin8")
                .build();
        testMiguelin8Id = playerRepository.save(playerMiguelin8).getId();

        Player playerAnagumaInu = new Player.PlayerBuilder()
                .firstName("anna")
                .lastName("Iuna")
                .leagueName("AnagumaInu")
                .build();
        testAnagumaInuId = playerRepository.save(playerAnagumaInu).getId();

        Player playerMarcoilfuso = new Player.PlayerBuilder()
                .firstName("Marco")
                .lastName("Fusoliner")
                .leagueName("Marcoilfuso")
                .build();
        testMarcoilfusoId = playerRepository.save(playerMarcoilfuso).getId();



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

        matchRepository.save(match1);

        Match match2 = new Match.MatchBuilder()
                .date(date2)
                .team1Id(team2)
                .matchID(4841161542L)
                .build();

        matchRepository.save(match2);

    }


    @Test
    void getMatchHistoryAllOk() throws Exception {
        final String EXPECTED_RESPONS = "[{\"teamId\":1,\"won1\":\"Fail\",\"killsTeam1\":41,\"deathsTeam1\":54,\"assistsTeam1\":92,\"killsTeam2\":54,\"deathsTeam2\":41,\"assistsTeam2\":118,\"matchDate\":\"Sun Dec 20 00:00:00 CET 2020\",\"playersTeam1\":[{\"summonerName\":\"Marcoilfuso\",\"playerId\":9},{\"summonerName\":\"pvppowners\",\"playerId\":1},{\"summonerName\":\"AnagumaInu\",\"playerId\":8},{\"summonerName\":\"miguelin8\",\"playerId\":7},{\"summonerName\":\"hylloi\",\"playerId\":6}],\"playersTeam2\":[\"Connie Lingus\",\"AemaethDragon\",\"mobby2010\",\"Ethaire\",\"Hi im YÃ¸ne\"]},{\"teamId\":2,\"won1\":\"Fail\",\"killsTeam1\":33,\"deathsTeam1\":51,\"assistsTeam1\":101,\"killsTeam2\":51,\"deathsTeam2\":33,\"assistsTeam2\":165,\"matchDate\":\"Mon Dec 21 00:00:00 CET 2020\",\"playersTeam1\":[{\"summonerName\":\"Xellania\",\"playerId\":5},{\"summonerName\":\"pvppowners\",\"playerId\":1},{\"summonerName\":\"TwinniesDad\",\"playerId\":4},{\"summonerName\":\"DorriShokouh\",\"playerId\":3},{\"summonerName\":\"Dragorius\",\"playerId\":2}],\"playersTeam2\":[\"KamÃ®yada\",\"metroYRN\",\"ParÃ´\",\"Habibti PIease \",\"ThatWasInVaÃ½ne\"]}]";

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/matchhistory"))
                .andExpect(status().isOk())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(EXPECTED_RESPONS, responsMessage);

    }


}
