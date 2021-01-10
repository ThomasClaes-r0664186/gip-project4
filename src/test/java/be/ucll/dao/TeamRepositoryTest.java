package be.ucll.dao;

import be.ucll.AbstractIntegrationTest;
import be.ucll.models.Team;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class TeamRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    TeamRepository teamRepository;

    @Test
    void saveTeam(){
        Team team = new Team.TeamBuilder()
                .name("TeamJaimie")
                .build();

        team = teamRepository.save(team);

        assertNotNull(team.getId());

        Optional<Team> found = teamRepository.findById(team.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), team.getName());

    }

    @Test
    void deleteTeam(){
        Team team = new Team.TeamBuilder()
                .name("TeamJaimie")
                .build();

        team = teamRepository.save(team);

        assertNotNull(team.getId());
        teamRepository.delete(team);
        Optional<Team> found = teamRepository.findById(team.getId());

        assertFalse(found.isPresent());

    }

    @Test
    void findTeamById(){
        Team team = new Team.TeamBuilder()
                .name("TeamJaimie")
                .build();

        team = teamRepository.save(team);

        Optional<Team> found = teamRepository.findTeamById(team.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), team.getName());
        assertEquals(found.get().getId(), team.getId());
    }

    @Test
    void findTeamByNameIgnoreCase(){
        Team team = new Team.TeamBuilder()
                .name("TeamJaimie")
                .build();

        team = teamRepository.save(team);

        Optional<Team> found = teamRepository.findTeamByNameIgnoreCase(team.getName());
        assertTrue(found.isPresent());
        assertEquals(found.get().getName(), team.getName());
    }
}
