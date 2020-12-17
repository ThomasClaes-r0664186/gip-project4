package be.ucll.web;

import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.PlayerDTO;
import be.ucll.dto.TeamPlayerDTO;
import be.ucll.exceptions.AlreadyExistsException;
import be.ucll.exceptions.NotFoundException;
import be.ucll.exceptions.TooManyActivePlayers;
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
    public ResponseEntity<TeamPlayerDTO> addPlayerToTeam(@RequestParam("teamName") String teamName, @RequestParam("leagueName") String leagueName) throws TooManyActivePlayers, NotFoundException, AlreadyExistsException {
        // TODO: geen twee dezelfde spelers aan team toekennnen, Max 5 main spelers
        if (teamName.trim().isBlank()) {
            throw new NotFoundException(teamName);
        }

        if (leagueName.trim().isBlank()) {
            throw new NotFoundException(leagueName);
        }

        if (teamRepository.findTeamByNameIgnoreCase(teamName).isEmpty()){
            throw new NotFoundException(teamName);
        }

        if (playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isEmpty()){
            throw new NotFoundException(leagueName);
        }

        Team team = teamRepository.findTeamByNameIgnoreCase(teamName).get();
        Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get();

        if (teamPlayerRepository.findTeamPlayerByPlayer(player).isPresent()){
            throw new AlreadyExistsException(leagueName);
        }

        TeamPlayer teamPlayer = teamPlayerRepository.save(new TeamPlayer.Builder()
                .team(team)
                .player(player)
                .isSelected(false)
                .build());

        return ResponseEntity.status(HttpStatus.CREATED).body(new TeamPlayerDTO(teamPlayer.getId(), teamPlayer.getTeam().getName(), teamPlayer.getPlayer().getLeagueName()));

    }


    @GetMapping
    public ResponseEntity<List<PlayerDTO>> getPlayersFromTeam(@RequestParam("teamName") String teamName) throws NotFoundException {

        if (teamName.trim().isBlank()) {
            throw new NotFoundException(teamName);
        }

        if (teamRepository.findTeamByNameIgnoreCase(teamName).isEmpty()){
            throw new NotFoundException(teamName);
        }

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

    @PutMapping
    public ResponseEntity<TeamPlayerDTO> makePlayerActive(@RequestParam("teamName") String teamName, @RequestParam("leagueName") String leagueName, @RequestParam("isActive") boolean isActive) throws NotFoundException, TooManyActivePlayers {

        if (teamName.trim().isBlank()) {
            throw new NotFoundException(teamName);
        }

        if (leagueName.trim().isBlank()) {
            throw new NotFoundException(leagueName);
        }

        if (isActive){
            if (teamPlayerRepository.findAllByIsSelectedIsTrue().size() >= 5){
                throw new TooManyActivePlayers(teamName);
            }
        }

        if (teamRepository.findTeamByNameIgnoreCase(teamName).isEmpty()){
            throw new NotFoundException(teamName);
        }


        if (playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isEmpty()){
            throw new NotFoundException(leagueName);
        }

        Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get();

        if (teamPlayerRepository.findTeamPlayerByPlayer(player).isEmpty()) {
            throw new NotFoundException(leagueName);
        }

        TeamPlayer teamPlayer = teamPlayerRepository.findTeamPlayerByPlayer(player).get();
        teamPlayer.setSelected(isActive);
        teamPlayerRepository.save(teamPlayer);

        return ResponseEntity.status(HttpStatus.OK).body(new TeamPlayerDTO(teamPlayer.getId(), teamPlayer.getTeam().getName(), teamPlayer.getPlayer().getLeagueName()));

    }


    @DeleteMapping
    public ResponseEntity deletePlayerFromTeam(@RequestParam("leagueName") String leagueName) throws NotFoundException {

        if (leagueName.trim().isBlank()) {
            throw new NotFoundException(leagueName);
        }

        if (playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isEmpty()){
            throw new NotFoundException(leagueName);
        }

        Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get();

        if (teamPlayerRepository.findTeamPlayerByPlayer(player).isEmpty()){
            throw new NotFoundException(leagueName);
        }

        TeamPlayer teamPlayer = teamPlayerRepository.findTeamPlayerByPlayer(player).get();
        teamPlayerRepository.delete(teamPlayer);

        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();

    }
}
