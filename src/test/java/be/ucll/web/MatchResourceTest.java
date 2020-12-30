package be.ucll.web;

import be.ucll.AbstractIntegrationTest;
import be.ucll.dao.MatchRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.MatchDTO;
import be.ucll.models.Match;
import be.ucll.models.Team;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.testcontainers.shaded.com.fasterxml.jackson.core.type.TypeReference;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class MatchResourceTest extends AbstractIntegrationTest {

    @Autowired
    private WebApplicationContext wac;

    private MockMvc mockMvc;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private MatchRepository matchRepository;

    @BeforeEach
    void setUp()  {
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
    }


    @Test
    void createMatchOk() throws Exception {
          Team team = new  Team.TeamBuilder()
                .name("testTeam")
                .build();
          Long teamId = teamRepository.save(team).getId();

          // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(teamId, simpleDateFormat.format(dateMatch));

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/match")
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").exists())
                .andReturn();
        Match createdMatch = fromMvcResult(mvcResult, Match.class);

        assertEquals(dateMatch, createdMatch.getDate());
        assertEquals("testTeam", createdMatch.getTeam1().getName());

    }

    @Test
    void createMatchTeamNull() throws Exception {
        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(null, simpleDateFormat.format(dateMatch));

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/match")
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This parameter may not be empty", responsMessage);

    }


    @Test
    void createMatchTeamNegative() throws Exception {
        final Long TEAM_ID = -1L;

        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(TEAM_ID, simpleDateFormat.format(dateMatch));

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/match")
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( TEAM_ID + " is not valid!", responsMessage);

    }


    @Test
    void createMatchTeam0() throws Exception {
        final Long TEAM_ID = 0L;

        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(TEAM_ID, simpleDateFormat.format(dateMatch));

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/match")
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( TEAM_ID + " is not valid!", responsMessage);

    }


    @Test
    void createMatchTeamNotFound() throws Exception {
        final Long TEAM_ID = 58569L;

        // today
        Calendar date = new GregorianCalendar();
        // reset hour, minutes, seconds and millis
        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        // next day
        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(TEAM_ID, simpleDateFormat.format(dateMatch));

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/match")
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals(TEAM_ID + " was not found!", responsMessage);

    }


    @Test
    void createMatchDateNull() throws Exception {
        Team team = new  Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(team).getId();

        MatchDTO matchDTO = new MatchDTO(teamId,null);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/match")
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals("This parameter may not be empty", responsMessage);

    }

    @Test
    void createMatchDateInvalid() throws Exception {
        final String DATE = "*";

        Team team = new  Team.TeamBuilder()
                .name("testTeam")
                .build();
        Long teamId = teamRepository.save(team).getId();

        MatchDTO matchDTO = new MatchDTO(teamId,DATE);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.post("/match")
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( DATE + " is not valid!", responsMessage);

    }


    @Test
    void getMatchOk() throws Exception {
        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .date(new Date())
                .build();

        Long MATCH_ID = matchRepository.save(match).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/match/" + MATCH_ID))
                .andExpect(status().isOk())
                .andReturn();

        Match createdMatch = fromMvcResult(mvcResult, Match.class);

        assertEquals(match.getId(), createdMatch.getId());
        assertEquals(match.getMatchId(), createdMatch.getMatchId());
        assertEquals(match.getDate(), createdMatch.getDate());

    }

    @Test
    void getMatchIdNegative() throws Exception {
        final Long MATCH_ID = -1L;

        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .date(new Date())
                .build();

        matchRepository.save(match);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/match/" + MATCH_ID))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( MATCH_ID + " is not valid!", responsMessage);

    }

    @Test
    void getMatchId0() throws Exception {
        final Long MATCH_ID = 0L;

        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .date(new Date())
                .build();

        matchRepository.save(match);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/match/" + MATCH_ID))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( MATCH_ID + " is not valid!", responsMessage);

    }

    @Test
    void getMatchIdNull() throws Exception {
        final Long MATCH_ID = null;

        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .date(new Date())
                .build();

        matchRepository.save(match);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/match/" + MATCH_ID))
                .andExpect(status().isBadRequest())
                .andReturn();
    }

    @Test
    void getMatchIdNotFound() throws Exception {
        final Long MATCH_ID = 58569L;

        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .date(new Date())
                .build();

        matchRepository.save(match);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.get("/match/" + MATCH_ID))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( MATCH_ID + " was not found!", responsMessage);

    }

    @Test
    void updateMatchOk() throws Exception {
        Team team = new  Team.TeamBuilder()
                .name("testTeam")
                .build();
        teamRepository.save(team).getId();

        Team team2 = new  Team.TeamBuilder()
                .name("testTeam2")
                .build();
        teamRepository.save(team2).getId();

        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .team1Id(team)
                .date(new Date())
                .build();

        Calendar date = new GregorianCalendar();

        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(team2.getId(), simpleDateFormat.format(dateMatch));

        final Long MATCH_ID = matchRepository.save(match).getId();

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/match/" + MATCH_ID)
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();
        Match createdMatch = fromMvcResult(mvcResult, Match.class);

        assertEquals(dateMatch, createdMatch.getDate());
        assertEquals("testTeam2", createdMatch.getTeam1().getName());
        assertEquals(match.getId(), createdMatch.getId());
        assertEquals(match.getDate(), createdMatch.getDate());
        assertEquals(match.getTeam1().getId(), createdMatch.getTeam1().getId());

    }

    @Test
    void updateMatchIdNegative() throws Exception {
        final Long MATCH_ID = -1L;

        Team team = new  Team.TeamBuilder()
                .name("testTeam")
                .build();
        teamRepository.save(team);

        Team team2 = new  Team.TeamBuilder()
                .name("testTeam2")
                .build();
        teamRepository.save(team2);

        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .team1Id(team)
                .date(new Date())
                .build();

        Calendar date = new GregorianCalendar();

        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(team2.getId(), simpleDateFormat.format(dateMatch));

        matchRepository.save(match);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/match/" + MATCH_ID)
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( MATCH_ID + " is not valid!", responsMessage);

    }

    @Test
    void updateMatchId0() throws Exception {
        final Long MATCH_ID = 0L;

        Team team = new  Team.TeamBuilder()
                .name("testTeam")
                .build();
        teamRepository.save(team);

        Team team2 = new  Team.TeamBuilder()
                .name("testTeam2")
                .build();
        teamRepository.save(team2);

        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .team1Id(team)
                .date(new Date())
                .build();

        Calendar date = new GregorianCalendar();

        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(team2.getId(), simpleDateFormat.format(dateMatch));

        matchRepository.save(match);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/match/" + MATCH_ID)
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isForbidden())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( MATCH_ID + " is not valid!", responsMessage);

    }

    @Test
    void updateMatchIdNotFound() throws Exception {
        final Long MATCH_ID = 95995L;

        Team team = new  Team.TeamBuilder()
                .name("testTeam")
                .build();
        teamRepository.save(team);

        Team team2 = new  Team.TeamBuilder()
                .name("testTeam2")
                .build();
        teamRepository.save(team2);

        Match match = new Match.MatchBuilder()
                .matchID(4841161542L)
                .team1Id(team)
                .date(new Date())
                .build();

        Calendar date = new GregorianCalendar();

        date.set(Calendar.HOUR_OF_DAY, 0);
        date.set(Calendar.MINUTE, 0);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.MILLISECOND, 0);

        date.add(Calendar.DAY_OF_MONTH, 1);

        Date dateMatch = date.getTime();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd/MM/yyyy");

        MatchDTO matchDTO = new MatchDTO(team2.getId(), simpleDateFormat.format(dateMatch));

        matchRepository.save(match);

        MvcResult mvcResult = this.mockMvc.perform(MockMvcRequestBuilders.put("/match/" + MATCH_ID)
                .content(toJson(matchDTO))
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responsMessage = mvcResult.getResponse().getContentAsString();
        assertEquals( MATCH_ID + " was not found!", responsMessage);

    }

}
