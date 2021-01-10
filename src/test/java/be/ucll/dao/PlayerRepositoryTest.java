package be.ucll.dao;

import be.ucll.AbstractIntegrationTest;
import be.ucll.models.Player;
import be.ucll.models.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class PlayerRepositoryTest extends AbstractIntegrationTest {

    @Autowired
    PlayerRepository playerRepository;

    @Test
    void safePlayer() {
        Player player7Stijn7 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("7Stijn7")
                .role(Role.PLAYER)
                .password("test")
                .accountId("b8PgZkOcjRUx7oiHgkD_BhJJ7B3rIGOwMN_1crvtdep39KA")
                .puuID("saaENlT6jyW8dyAS3nOyk8SRXQjXs7qubys0Pls06P9Dk8hVgGOVgntYxAQilz_OxlJbQBL-0Ay5rw")
                .summonerID("1xXnvDRMw7EtYUfyqVVE4axhW-TywmiIfVVIZ72dOd92u08")
                .build();

        player7Stijn7 = playerRepository.save(player7Stijn7);
        assertNotNull(player7Stijn7.getId());

        Optional<Player> found = playerRepository.findById(player7Stijn7.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getLeagueName(), player7Stijn7.getLeagueName());
        assertEquals(found.get().getFirstName(), player7Stijn7.getFirstName());
        assertEquals(found.get().getLastName(), player7Stijn7.getLastName());
        assertEquals(found.get().getRole(), player7Stijn7.getRole());
        assertEquals(found.get().getPassword(), player7Stijn7.getPassword());
        assertEquals(found.get().getAccountId(), player7Stijn7.getAccountId());
        assertEquals(found.get().getSummonerID(), player7Stijn7.getSummonerID());
        assertEquals(found.get().getPuuID(), player7Stijn7.getPuuID());
    }


    @Test
    void deletePlayer() {
        Player player7Stijn7 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("7Stijn7")
                .role(Role.PLAYER)
                .password("test")
                .accountId("b8PgZkOcjRUx7oiHgkD_BhJJ7B3rIGOwMN_1crvtdep39KA")
                .puuID("saaENlT6jyW8dyAS3nOyk8SRXQjXs7qubys0Pls06P9Dk8hVgGOVgntYxAQilz_OxlJbQBL-0Ay5rw")
                .summonerID("1xXnvDRMw7EtYUfyqVVE4axhW-TywmiIfVVIZ72dOd92u08")
                .build();

        player7Stijn7 = playerRepository.save(player7Stijn7);
        assertNotNull(player7Stijn7.getId());

        playerRepository.delete(player7Stijn7);

        Optional<Player> found = playerRepository.findById(player7Stijn7.getId());
        assertTrue(found.isEmpty());
    }

    @Test
    void findPlayerByLeagueNameIgnoreCase() {
        Player player7Stijn7 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("7Stijn7")
                .role(Role.PLAYER)
                .password("test")
                .accountId("b8PgZkOcjRUx7oiHgkD_BhJJ7B3rIGOwMN_1crvtdep39KA")
                .puuID("saaENlT6jyW8dyAS3nOyk8SRXQjXs7qubys0Pls06P9Dk8hVgGOVgntYxAQilz_OxlJbQBL-0Ay5rw")
                .summonerID("1xXnvDRMw7EtYUfyqVVE4axhW-TywmiIfVVIZ72dOd92u08")
                .build();

        player7Stijn7 = playerRepository.save(player7Stijn7);
        assertNotNull(player7Stijn7.getLeagueName());

        Optional<Player> found = playerRepository.findPlayerByLeagueNameIgnoreCase(player7Stijn7.getLeagueName());
        assertTrue(found.isPresent());
        assertEquals(found.get().getLeagueName(), player7Stijn7.getLeagueName());
        assertEquals(found.get().getFirstName(), player7Stijn7.getFirstName());
        assertEquals(found.get().getLastName(), player7Stijn7.getLastName());
        assertEquals(found.get().getRole(), player7Stijn7.getRole());
        assertEquals(found.get().getPassword(), player7Stijn7.getPassword());
        assertEquals(found.get().getAccountId(), player7Stijn7.getAccountId());
        assertEquals(found.get().getSummonerID(), player7Stijn7.getSummonerID());
        assertEquals(found.get().getPuuID(), player7Stijn7.getPuuID());

    }

    @Test
    void findPlayerById() {
        Player player7Stijn7 = new Player.PlayerBuilder()
                .firstName("Stijn")
                .lastName("Verbieren")
                .leagueName("7Stijn7")
                .role(Role.PLAYER)
                .password("test")
                .accountId("b8PgZkOcjRUx7oiHgkD_BhJJ7B3rIGOwMN_1crvtdep39KA")
                .puuID("saaENlT6jyW8dyAS3nOyk8SRXQjXs7qubys0Pls06P9Dk8hVgGOVgntYxAQilz_OxlJbQBL-0Ay5rw")
                .summonerID("1xXnvDRMw7EtYUfyqVVE4axhW-TywmiIfVVIZ72dOd92u08")
                .build();

        player7Stijn7 = playerRepository.save(player7Stijn7);
        assertNotNull(player7Stijn7.getId());

        Optional<Player> found = playerRepository.findPlayerById(player7Stijn7.getId());
        assertTrue(found.isPresent());
        assertEquals(found.get().getLeagueName(), player7Stijn7.getLeagueName());
        assertEquals(found.get().getFirstName(), player7Stijn7.getFirstName());
        assertEquals(found.get().getLastName(), player7Stijn7.getLastName());
        assertEquals(found.get().getRole(), player7Stijn7.getRole());
        assertEquals(found.get().getPassword(), player7Stijn7.getPassword());
        assertEquals(found.get().getAccountId(), player7Stijn7.getAccountId());
        assertEquals(found.get().getSummonerID(), player7Stijn7.getSummonerID());
        assertEquals(found.get().getPuuID(), player7Stijn7.getPuuID());
    }
    

}

