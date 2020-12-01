package be.ucll.web;

import be.ucll.models.Player;
import be.ucll.service.SummonerService;
import be.ucll.service.models.Summoner;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("player")
public class PlayerResource {

   private SummonerService summonerService;

   @Autowired
    public PlayerResource(SummonerService summonerService) {
        this.summonerService = summonerService;
    }

    @ApiOperation("De Summoner/Speler van league of legends creëren) ")
    @PostMapping
    public Optional<Player> createPlayer(@ApiParam(value = "De gebruikersnaam van de league of legends speler", example = "7Stijn7", required = true) @RequestBody Player player){
       if(summonerService.getSummoner(player.getLeagueName()).isPresent()){
         Summoner summoner = summonerService.getSummoner(player.getLeagueName()).get();
         return Optional.of(new Player.PlayerBuilder().leagueName(summoner.getName()).build());
       }
       return Optional.empty();
    }
}
