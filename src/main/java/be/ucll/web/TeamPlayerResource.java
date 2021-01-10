package be.ucll.web;

import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.PlayerDTO;
import be.ucll.dto.TeamPlayerDTO;
import be.ucll.exceptions.AlreadyExistsException;
import be.ucll.exceptions.NotFoundException;
import be.ucll.exceptions.ParameterInvalidException;
import be.ucll.exceptions.TooManyActivePlayersException;
import be.ucll.models.Player;
import be.ucll.models.Team;
import be.ucll.models.TeamPlayer;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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

    @Operation(
            summary = "Add a player to a team",
            description = "Provide an existing player id and an existing team id to add this player to the team"
    )
    @PostMapping("/{teamId}/team/{playerId}/player")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TeamPlayerDTO> addPlayerToTeam(@PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId) throws TooManyActivePlayersException, NotFoundException, AlreadyExistsException, ParameterInvalidException {
        // TODO: geen twee dezelfde spelers aan team toekennnen, Max 5 main spelers
        if (teamId <= 0) throw new ParameterInvalidException(teamId.toString());

        if (playerId <= 0) throw new ParameterInvalidException(playerId.toString());

        if (teamRepository.findTeamById(teamId).isEmpty()) throw new NotFoundException(teamId.toString());

        if (playerRepository.findPlayerById(playerId).isEmpty()) throw new NotFoundException(playerId.toString());

        Team team = teamRepository.findTeamById(teamId).get();
        Player player = playerRepository.findPlayerById(playerId).get();

        if (teamPlayerRepository.findTeamPlayerByPlayer(player).isPresent()) throw new AlreadyExistsException(playerId.toString());

        TeamPlayer teamPlayer = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team)
                .player(player)
                .isSelected(false)
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TeamPlayerDTO(teamPlayer.getId(), teamPlayer.getTeam().getName(), teamPlayer.getPlayer().getLeagueName()));
    }

    @Operation(
            summary = "Get players from a team",
            description = "Retrieve all players from an existing team id "
    )
    @GetMapping("/{teamId}/team")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<List<PlayerDTO>> getPlayersFromTeam(@PathVariable("teamId") Long teamId) throws NotFoundException, ParameterInvalidException {

        if (teamId <= 0) throw new ParameterInvalidException(teamId.toString());

        if (teamRepository.findTeamById(teamId).isEmpty()) throw new NotFoundException(teamId.toString());

        Team team = teamRepository.findTeamById(teamId).get();
        List<TeamPlayer> players = teamPlayerRepository.findPlayersByTeam(team);

        List<PlayerDTO> playersNames = players.stream()
                .map(player -> {
                    PlayerDTO playerDTO = new PlayerDTO();
                    playerDTO.setLeagueName(player.getPlayer().getLeagueName());
                    playerDTO.setFirstName(player.getPlayer().getFirstName());
                    playerDTO.setLastName(player.getPlayer().getLastName());
                    return playerDTO;
                })
                .collect(toList());

        return ResponseEntity.status(HttpStatus.OK).body(playersNames);

    }

    @Operation(
            summary = "Update player activity status",
            description = "Provide an existing player id and an existing team id to change if a player is active or not "
    )
    @PutMapping("/{teamId}/team/{playerId}/player")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<TeamPlayerDTO> makePlayerActive(@PathVariable("teamId") Long teamId, @PathVariable("playerId") Long playerId, @RequestParam("isActive") boolean isActive) throws NotFoundException, TooManyActivePlayersException, ParameterInvalidException {

        if (playerId <= 0) throw new ParameterInvalidException(playerId.toString());

        if (teamId <= 0) throw new ParameterInvalidException(teamId.toString());

        if (isActive){
            if (teamPlayerRepository.findAllByIsSelectedIsTrue().size() >= 5) throw new TooManyActivePlayersException(teamId.toString());
        }

        if (teamRepository.findTeamById(teamId).isEmpty()) throw new NotFoundException(teamId.toString());

        if (playerRepository.findPlayerById(playerId).isEmpty()) throw new NotFoundException(playerId.toString());

        Player player = playerRepository.findPlayerById(playerId).get();

        if (teamPlayerRepository.findTeamPlayerByPlayer(player).isEmpty()) throw new NotFoundException(playerId.toString());

        TeamPlayer teamPlayer = teamPlayerRepository.findTeamPlayerByPlayer(player).get();
        teamPlayer.setSelected(isActive);
        teamPlayerRepository.save(teamPlayer);

        return ResponseEntity.status(HttpStatus.OK).body(new TeamPlayerDTO(teamPlayer.getId(), teamPlayer.getTeam().getName(), teamPlayer.getPlayer().getLeagueName()));

    }

    @Operation(
            summary = "Delete a player from a team",
            description = "Delete a player from a team based on an existing player id"
    )
    @DeleteMapping("/{playerId}/player")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity deletePlayerFromTeam(@PathVariable("playerId") Long playerId) throws NotFoundException, ParameterInvalidException {

        if (playerId <= 0) throw new ParameterInvalidException(playerId.toString());

        if (playerRepository.findPlayerById(playerId).isEmpty()) throw new NotFoundException(playerId.toString());

        Player player = playerRepository.findPlayerById(playerId).get();

        if (teamPlayerRepository.findTeamPlayerByPlayer(player).isEmpty()) throw new NotFoundException(playerId.toString());

        TeamPlayer teamPlayer = teamPlayerRepository.findTeamPlayerByPlayer(player).get();
        teamPlayerRepository.delete(teamPlayer);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
