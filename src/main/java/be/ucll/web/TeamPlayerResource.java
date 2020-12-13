package be.ucll.web;

import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.PlayerDTO;
import be.ucll.dto.TeamPlayerDTO;
import be.ucll.exceptions.PlayerNotFound;
import be.ucll.exceptions.TeamNotFound;
import be.ucll.models.Player;
import be.ucll.models.Team;
import be.ucll.models.TeamPlayer;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("teamplayer")
public class TeamPlayerResource {

    private TeamRepository teamRepository;
    private PlayerRepository playerRepository;
    private TeamPlayerRepository teamPlayerRepository;

    public TeamPlayerResource(TeamRepository teamRepository, PlayerRepository playerRepository, TeamPlayerRepository teamPlayerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.teamPlayerRepository = teamPlayerRepository;
    }

    @PostMapping
    public ResponseEntity<TeamPlayerDTO> addPlayerToTeam(@RequestParam("teamName") String teamName, @RequestParam("leagueName") String leagueName) throws TeamNotFound, PlayerNotFound {

        if (teamName.trim().isBlank()) {
            throw new TeamNotFound(teamName);
        }

        if (leagueName.trim().isBlank()) {
            throw new PlayerNotFound(leagueName);
        }

        if (teamRepository.findTeamByNameIgnoreCase(teamName).isEmpty()){
            throw new TeamNotFound(teamName);
        }

        if (playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isEmpty()){
            throw new PlayerNotFound(leagueName);
        }

        Team team = teamRepository.findTeamByNameIgnoreCase(teamName).get();
        Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get();

        TeamPlayer teamPlayer = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team)
                .player(player)
                .isSelected(false)
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TeamPlayerDTO(teamPlayer.getId(), teamPlayer.getTeam().getName(), teamPlayer.getPlayer().getLeagueName()));

    }


    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getPlayersFromTeam(@RequestParam("teamName") String teamName) throws Exception {

        if (teamName.trim().isBlank()) {
            throw new Exception();
        }

        if (teamRepository.findTeamByNameIgnoreCase(teamName).isPresent()) {
            Team team = teamRepository.findTeamByNameIgnoreCase(teamName).get();
            List<Player> players = teamPlayerRepository.findPlayersByTeam(team);

            List<PlayerDTO> playersNames = players.stream()
                    .map(player -> {
                        PlayerDTO playerDTO = new PlayerDTO();
                        playerDTO.setLeagueName(player.getLeagueName());
                        playerDTO.setFirstName(player.getFirstName());
                        playerDTO.setLastName(player.getLastName());
                        return playerDTO;
                    })
                    .collect(toList());

            return ResponseEntity.status(HttpStatus.OK).body(playersNames);
        }

        throw new Exception();
    }

    @PutMapping
    public ResponseEntity<TeamPlayerDTO> makePlayerReserve(@RequestParam("leagueName") String leagueName, @RequestParam("reserve") boolean isReserve) throws Exception {

        if (leagueName.trim().isBlank()) {
            throw new Exception();
        }

        if (playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isPresent()) {
            Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get();
            if (teamPlayerRepository.findTeamPlayerByPlayer(player).isPresent()) {
                TeamPlayer teamPlayer = teamPlayerRepository.findTeamPlayerByPlayer(player).get();
                teamPlayer.setSelected(isReserve);
                teamPlayerRepository.save(teamPlayer);
                return ResponseEntity.status(HttpStatus.OK).body(new TeamPlayerDTO(teamPlayer.getId(), teamPlayer.getTeam().getName(), teamPlayer.getPlayer().getLeagueName()));
            }
        }

        throw new Exception();
    }

    @DeleteMapping
    public ResponseEntity deletePlayerFromTeam(@RequestParam("leagueName") String leagueName) throws Exception {

        if (leagueName.trim().isBlank()) {
            throw new Exception();
        }

        if (playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isPresent()) {
            Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get();

            if (teamPlayerRepository.findTeamPlayerByPlayer(player).isPresent()) {
                TeamPlayer teamPlayer = teamPlayerRepository.findTeamPlayerByPlayer(player).get();
                teamPlayerRepository.delete(teamPlayer);
                return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
            }

        }

        throw new Exception();
    }
}
