package be.ucll.web;

import be.ucll.dto.PlayerDTO;
import be.ucll.models.Player;
import be.ucll.dao.PlayerRepository;
import be.ucll.service.SummonerService;
import be.ucll.service.models.Summoner;
import be.ucll.exceptions.*;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//TODO url shouldn't contain verbs => you can deduct what is going to happen from POST values

@RestController
@RequestMapping("player")
public class PlayerResource {

    private SummonerService summonerService;
    private PlayerRepository playerRepository;

    @Autowired
    public PlayerResource(SummonerService summonerService, PlayerRepository playerRepository) {
        this.summonerService = summonerService;
        this.playerRepository = playerRepository;
    }

    @ApiOperation("De Summoner/Speler van league of legends creëren) ")
    @PostMapping("/create")
    public ResponseEntity<PlayerDTO> createPlayer(@RequestBody Player player) throws UsernameNotValid, UsernameAlreadyExists {
        if (playerRepository.findPlayerByLeagueNameIgnoreCase(player.getLeagueName()).isPresent()) throw new UsernameAlreadyExists(player.getLeagueName());
        if(summonerService.getSummoner(player.getLeagueName()).isPresent()){
            Summoner summoner = summonerService.getSummoner(player.getLeagueName()).get();
            Player newPlayer = playerRepository.save(new Player.PlayerBuilder().accountId(summoner.getAccountId()).leagueName(summoner.getName()).build());
            return ResponseEntity.status(HttpStatus.CREATED).body(new PlayerDTO(newPlayer.getId(), newPlayer.getAccountId(), newPlayer.getLeagueName()));
        }
        throw new UsernameNotValid(player.getLeagueName());
    }
}
