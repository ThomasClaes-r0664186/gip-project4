package be.ucll.dao;

import be.ucll.AbstractIntegrationTest;
import be.ucll.models.Player;
import be.ucll.models.Team;
import be.ucll.models.TeamPlayer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TeamPlayerRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    TeamPlayerRepository teamPlayerRepository;

    @Autowired
    TeamRepository teamRepository;
    @Autowired
    PlayerRepository playerRepository;

    @Test
    void saveTeamPlayer() {
        Team team = new Team.TeamBuilder()
                .name("G2")
                .build();
        teamRepository.save(team);

        Player player = new Player.PlayerBuilder()
                .accountId("2")
                .firstName("Wannes")
                .lastName(" Verschraegen")
                .leagueName("WannesV")
                .puuID("1")
                .summonerID("3")
                .build();
        playerRepository.save(player);

        TeamPlayer teamPlayerWannes = new TeamPlayer.Builder()
                .player(player)
                .team(team)
                .isSelected(true)
                .build();
        TeamPlayer teamPlayer = teamPlayerRepository.save(teamPlayerWannes);
        assertNotNull(teamPlayer.getId());

        Optional<TeamPlayer> found = teamPlayerRepository.findById(teamPlayer.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getPlayer().getAccountId(), teamPlayerWannes.getPlayer().getAccountId());
        assertEquals(found.get().getPlayer().getFirstName(), teamPlayerWannes.getPlayer().getFirstName());
        assertEquals(found.get().getPlayer().getLastName(), teamPlayerWannes.getPlayer().getLastName());
        assertEquals(found.get().getPlayer().getPuuID(), teamPlayerWannes.getPlayer().getPuuID());
        assertEquals(found.get().getPlayer().getLeagueName(), teamPlayerWannes.getPlayer().getLeagueName());
        assertEquals(found.get().getPlayer().getSummonerID(), teamPlayerWannes.getPlayer().getSummonerID());
        assertEquals(found.get().getTeam().getName(), teamPlayerWannes.getTeam().getName());
        assertEquals(found.get().getTeam().getId(), teamPlayerWannes.getTeam().getId());


    }

    @Test
    void deleteTeamPlayer() {
        Team team = new Team.TeamBuilder()
                .name("G2")
                .build();
        teamRepository.save(team);

        Player player = new Player.PlayerBuilder()
                .accountId("2")
                .firstName("Wannes")
                .lastName(" Verschraegen")
                .leagueName("WannesV")
                .puuID("1")
                .summonerID("3")
                .build();
        playerRepository.save(player);

        TeamPlayer teamPlayerWannes = new TeamPlayer.Builder()
                .player(player)
                .team(team)
                .isSelected(true)
                .build();
        TeamPlayer teamPlayer = teamPlayerRepository.save(teamPlayerWannes);
        assertNotNull(teamPlayer.getId());

        teamPlayerRepository.delete(teamPlayer);

        Optional<TeamPlayer> found = teamPlayerRepository.findById(teamPlayer.getId());
        assertTrue(found.isEmpty());


    }

    @Test
    void findPlayersByTeam() {
        Team team = new Team.TeamBuilder()
                .name("G2")
                .build();
        teamRepository.save(team);

        Player player = new Player.PlayerBuilder()
                .accountId("2")
                .firstName("Wannes")
                .lastName(" Verschraegen")
                .leagueName("WannesV")
                .puuID("1")
                .summonerID("3")
                .build();
        playerRepository.save(player);

        TeamPlayer teamPlayerWannes = new TeamPlayer.Builder()
                .player(player)
                .team(team)
                .isSelected(true)
                .build();
        TeamPlayer teamPlayer = teamPlayerRepository.save(teamPlayerWannes);
        assertNotNull(teamPlayer.getId());

        List<TeamPlayer> found = teamPlayerRepository.findPlayersByTeam(teamPlayer.getTeam());
        assertTrue(found.size() > 0);
        assertEquals(found.get(0).getPlayer().getAccountId(), teamPlayerWannes.getPlayer().getAccountId());
        assertEquals(found.get(0).getPlayer().getFirstName(), teamPlayerWannes.getPlayer().getFirstName());
        assertEquals(found.get(0).getPlayer().getLastName(), teamPlayerWannes.getPlayer().getLastName());
        assertEquals(found.get(0).getPlayer().getPuuID(), teamPlayerWannes.getPlayer().getPuuID());
        assertEquals(found.get(0).getPlayer().getLeagueName(), teamPlayerWannes.getPlayer().getLeagueName());
        assertEquals(found.get(0).getPlayer().getSummonerID(), teamPlayerWannes.getPlayer().getSummonerID());
        assertEquals(found.get(0).getTeam().getName(), teamPlayerWannes.getTeam().getName());
        assertEquals(found.get(0).getTeam().getId(), teamPlayerWannes.getTeam().getId());


    }

    @Test
    void findAllByIsSelectedIsTrue() {
        Team team = new Team.TeamBuilder()
                .name("G2")
                .build();
        teamRepository.save(team);

        Player player = new Player.PlayerBuilder()
                .accountId("2")
                .firstName("Wannes")
                .lastName(" Verschraegen")
                .leagueName("WannesV")
                .puuID("1")
                .summonerID("3")
                .build();
        playerRepository.save(player);

        TeamPlayer teamPlayerWannes = new TeamPlayer.Builder()
                .player(player)
                .team(team)
                .isSelected(true)
                .build();
        TeamPlayer teamPlayer = teamPlayerRepository.save(teamPlayerWannes);
        assertNotNull(teamPlayer.getId());

        List<TeamPlayer> found = teamPlayerRepository.findAllByIsSelectedIsTrue();
        assertTrue(found.size() > 0);
        assertEquals(found.get(0).getPlayer().getAccountId(), teamPlayerWannes.getPlayer().getAccountId());
        assertEquals(found.get(0).getPlayer().getFirstName(), teamPlayerWannes.getPlayer().getFirstName());
        assertEquals(found.get(0).getPlayer().getLastName(), teamPlayerWannes.getPlayer().getLastName());
        assertEquals(found.get(0).getPlayer().getPuuID(), teamPlayerWannes.getPlayer().getPuuID());
        assertEquals(found.get(0).getPlayer().getLeagueName(), teamPlayerWannes.getPlayer().getLeagueName());
        assertEquals(found.get(0).getPlayer().getSummonerID(), teamPlayerWannes.getPlayer().getSummonerID());
        assertEquals(found.get(0).getTeam().getName(), teamPlayerWannes.getTeam().getName());
        assertEquals(found.get(0).getTeam().getId(), teamPlayerWannes.getTeam().getId());
    }
    @Test
    void findTeamPlayerByPlayer() {
        Team team = new Team.TeamBuilder()
                .name("G2")
                .build();
        teamRepository.save(team);

        Player player = new Player.PlayerBuilder()
                .accountId("2")
                .firstName("Wannes")
                .lastName(" Verschraegen")
                .leagueName("WannesV")
                .puuID("1")
                .summonerID("3")
                .build();
        playerRepository.save(player);

        TeamPlayer teamPlayerWannes = new TeamPlayer.Builder()
                .player(player)
                .team(team)
                .isSelected(true)
                .build();
        TeamPlayer teamPlayer = teamPlayerRepository.save(teamPlayerWannes);
        assertNotNull(teamPlayer.getId());

        Optional<TeamPlayer> found = teamPlayerRepository.findTeamPlayerByPlayer(player);
        assertTrue(found.isPresent());
        assertEquals(found.get().getPlayer().getAccountId(), teamPlayerWannes.getPlayer().getAccountId());
        assertEquals(found.get().getPlayer().getFirstName(), teamPlayerWannes.getPlayer().getFirstName());
        assertEquals(found.get().getPlayer().getLastName(), teamPlayerWannes.getPlayer().getLastName());
        assertEquals(found.get().getPlayer().getPuuID(), teamPlayerWannes.getPlayer().getPuuID());
        assertEquals(found.get().getPlayer().getLeagueName(), teamPlayerWannes.getPlayer().getLeagueName());
        assertEquals(found.get().getPlayer().getSummonerID(), teamPlayerWannes.getPlayer().getSummonerID());
        assertEquals(found.get().getTeam().getName(), teamPlayerWannes.getTeam().getName());
        assertEquals(found.get().getTeam().getId(), teamPlayerWannes.getTeam().getId());
    }
    @Test
    void findTeamsByPlayer() {
        Team team = new Team.TeamBuilder()
                .name("G2")
                .build();
        teamRepository.save(team);

        Player player = new Player.PlayerBuilder()
                .accountId("2")
                .firstName("Wannes")
                .lastName(" Verschraegen")
                .leagueName("WannesV")
                .puuID("1")
                .summonerID("3")
                .build();
        playerRepository.save(player);

        TeamPlayer teamPlayerWannes = new TeamPlayer.Builder()
                .player(player)
                .team(team)
                .isSelected(true)
                .build();
        TeamPlayer teamPlayer = teamPlayerRepository.save(teamPlayerWannes);
        assertNotNull(teamPlayer.getId());

        List<TeamPlayer> found = teamPlayerRepository.findTeamsByPlayer(player);
        assertTrue((found.size() > 0 ));
        assertEquals(found.get(0).getPlayer().getAccountId(), teamPlayerWannes.getPlayer().getAccountId());
        assertEquals(found.get(0).getPlayer().getFirstName(), teamPlayerWannes.getPlayer().getFirstName());
        assertEquals(found.get(0).getPlayer().getLastName(), teamPlayerWannes.getPlayer().getLastName());
        assertEquals(found.get(0).getPlayer().getPuuID(), teamPlayerWannes.getPlayer().getPuuID());
        assertEquals(found.get(0).getPlayer().getLeagueName(), teamPlayerWannes.getPlayer().getLeagueName());
        assertEquals(found.get(0).getPlayer().getSummonerID(), teamPlayerWannes.getPlayer().getSummonerID());
        assertEquals(found.get(0).getTeam().getName(), teamPlayerWannes.getTeam().getName());
        assertEquals(found.get(0).getTeam().getId(), teamPlayerWannes.getTeam().getId());
    }
}