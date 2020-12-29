package be.ucll.web;

import be.ucll.dao.MatchRepository;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.exceptions.NotFoundException;
import be.ucll.models.Player;
import be.ucll.models.Team;
import be.ucll.models.TeamPlayer;
import be.ucll.service.LiveStatsService;
import be.ucll.service.MatchHistoryService;
import be.ucll.service.models.CurrentGameInfo;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("liveStats")
public class LiveStatsResource {
    TeamRepository teamRepository;
    TeamPlayerRepository teamPlayerRepository;
    PlayerRepository playerRepository;
    LiveStatsService liveStatsService;

    @Autowired
    public LiveStatsResource(TeamRepository teamRepository,TeamPlayerRepository teamPlayerRepository, PlayerRepository playerRepository, LiveStatsService liveStatsService) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.liveStatsService = liveStatsService;
        this.teamPlayerRepository = teamPlayerRepository;
    }
    @Operation(
            summary = "Get live stats",
            description = "Get live stats from players in a team"
    )
    @GetMapping("/{teamId}")
    public ResponseEntity<List<CurrentGameInfo>> getLiveStats(@PathVariable("teamId") Long teamId) throws NotFoundException {
        //1. vraag team op
        Optional<Team> team = teamRepository.findTeamById(teamId);
        if(team.isEmpty()){
            throw new NotFoundException(teamId.toString());
        }
        System.out.println("found team "+team.get().getName()); //debug

        //2. vraag teamplayers op van het team
        List<TeamPlayer> teamPlayers = teamPlayerRepository.findPlayersByTeam(team.get());
        if(teamPlayers.isEmpty()){
            throw new NotFoundException("teamPlayers of "+teamId.toString());
        }
        System.out.println("found teamplayers "+teamPlayers.toString()); //debug
        System.out.println(teamPlayers.size()+" size"); //debug

        //3. Maak request voor alle players active games
        List<CurrentGameInfo> liveStats = null;
        teamPlayers.forEach((teamPlayer -> {
            Player player = teamPlayer.getPlayer();
            System.out.println("found player "+player.getSummonerID()); //debug
            Optional<CurrentGameInfo> currentGameInfo = liveStatsService.getActiveGames(player.getSummonerID());
            System.out.println("currentGameInfo "+currentGameInfo.toString()); //debug
            if(currentGameInfo.isPresent()){
                System.out.println("currentGameInfo "+currentGameInfo.get().toString()); //debug
                liveStats.add(currentGameInfo.get());
            }else{
                System.out.println("no currentGameInfo "); //debug
            }
        }));
        System.out.println(liveStats.toString());

        //4. Check of liveStats minstens 2 heeft
        if(liveStats.isEmpty()){
            throw new NotFoundException("live stats of players in team "+teamId);
        }
        if(liveStats.size()<2){
            throw new NotFoundException("Too few players active in team "+teamId);
        }

        return ResponseEntity.status(HttpStatus.OK).body(liveStats);
    }
}
