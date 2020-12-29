package be.ucll.dao;

import be.ucll.AbstractIntegrationTest;
import be.ucll.models.Match;
import be.ucll.models.Team;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class MatchRepositoryTest  extends AbstractIntegrationTest {

    @Autowired
    MatchRepository matchRepository;

    @Autowired
    TeamRepository teamRepository;

    @Test
    void saveMatch() {
        Match match = new Match.MatchBuilder()
                .date(new Date())
                .matchID(4841161542L)
                .build();

        match = matchRepository.save(match);
        assertNotNull(match.getId());

        Optional<Match> found = matchRepository.findById(match.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getDate(), match.getDate());
        assertEquals(found.get().getMatchId(), match.getMatchId());
    }

    @Test
    void saveMatchWithTeam() {
        Team teamTeam = new Team.TeamBuilder()
                .name("team")
                .build();

       Team team =  teamRepository.save(teamTeam);

        Match match = new Match.MatchBuilder()
                .date(new Date())
                .matchID(4841161542L)
                .team1Id(team)
                .build();

        match = matchRepository.save(match);
        assertNotNull(match.getId());
        assertNotNull(match.getTeam1().getId());

        Optional<Team> found = teamRepository.findById(match.getTeam1().getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), match.getTeam1().getName());
    }


    @Test
    void deleteMatchWithTeam() {
        Team teamTeam = new Team.TeamBuilder()
                .name("team")
                .build();

        Team team =  teamRepository.save(teamTeam);

        Match match = new Match.MatchBuilder()
                .date(new Date())
                .matchID(4841161542L)
                .team1Id(team)
                .build();

        match = matchRepository.save(match);
        assertNotNull(match.getId());
        assertNotNull(match.getTeam1().getId());

        matchRepository.delete(match);

        Optional<Match> foundMatch = matchRepository.findById(match.getId());
        assertTrue(foundMatch.isEmpty());
        Optional<Match> foundTeam = matchRepository.findById(match.getTeam1().getId());
        assertTrue(foundTeam.isEmpty());
    }


    @Test
    void findMatchByTeam1AndAndDate() {
        Team teamTeam = new Team.TeamBuilder()
                .name("team")
                .build();

        Team team =  teamRepository.save(teamTeam);

        Match match = new Match.MatchBuilder()
                .date(new Date())
                .matchID(4841161542L)
                .team1Id(team)
                .build();

        match = matchRepository.save(match);
        assertNotNull(match.getId());
        assertNotNull(match.getTeam1().getId());

        Optional<Match> found = matchRepository.findMatchByTeam1AndAndDate(match.getTeam1(), new Date());
        assertTrue(found.isPresent());
        assertEquals(found.get().getMatchId(), match.getMatchId());
        assertEquals(found.get().getDate(), match.getDate());

    }


    @Test
    void findMatchById() {
        Team teamTeam = new Team.TeamBuilder()
                .name("team")
                .build();

        Team team =  teamRepository.save(teamTeam);

        Match match = new Match.MatchBuilder()
                .date(new Date())
                .matchID(4841161542L)
                .team1Id(team)
                .build();

        match = matchRepository.save(match);
        assertNotNull(match.getId());
        assertNotNull(match.getTeam1().getId());

        Optional<Match> found = matchRepository.findMatchById(match.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getMatchId(), match.getMatchId());
        assertEquals(found.get().getDate(), match.getDate());

    }

    @Test
    void findMatchByMatchID() {
        Team teamTeam = new Team.TeamBuilder()
                .name("team")
                .build();

        Team team =  teamRepository.save(teamTeam);

        Match match = new Match.MatchBuilder()
                .date(new Date())
                .matchID(4841161542L)
                .team1Id(team)
                .build();

        match = matchRepository.save(match);
        assertNotNull(match.getId());
        assertNotNull(match.getTeam1().getId());

        Optional<Match> found = matchRepository.findMatchByMatchID(match.getMatchId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getMatchId(), match.getMatchId());
        assertEquals(found.get().getDate(), match.getDate());

    }

    @Test
    void findMatchByTeam1() {
        Team teamTeam = new Team.TeamBuilder()
                .name("team")
                .build();

        Team team =  teamRepository.save(teamTeam);

        Match match = new Match.MatchBuilder()
                .date(new Date())
                .matchID(4841161542L)
                .team1Id(team)
                .build();

        match = matchRepository.save(match);
        assertNotNull(match.getId());
        assertNotNull(match.getTeam1().getId());

        Optional<Match> found = matchRepository.findMatchByTeam1(match.getTeam1());
        assertTrue(found.isPresent());
        assertEquals(found.get().getMatchId(), match.getMatchId());
        assertEquals(found.get().getDate(), match.getDate());

    }


}
