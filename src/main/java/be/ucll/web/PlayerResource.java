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
    // De functie wordt aangeroepen door middel van een postrequest. met als input: JSON-object Player: { "leagueName" : "7Stijn7" }
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerDTO player) throws UsernameNotValid, UsernameAlreadyExists {
        // Daarna wordt er aan de playerRepository gevraagd of deze speler al gevonden is (op basis van de username), en al in onze databank zit.
        // Zoja, Gooit het een exception: dat de speler al bestaat in ons systeem.
        if (playerRepository.findPlayerByLeagueNameIgnoreCase(player.getLeagueName()).isPresent()) throw new UsernameAlreadyExists(player.getLeagueName());
        //Indien de 'Player' toch nog niet gevonden werd. Wordt de 'summonerService' aangeroepen. Deze service gaat de league of legends api raadplegen.
        // En returnt 'De Summoner' indien het een bestaande username bij league of legends is.
        if(summonerService.getSummoner(player.getLeagueName()).isPresent()){
            Summoner summoner = summonerService.getSummoner(player.getLeagueName()).get();
            // daarna wordt deze 'summoner' omgezet in een 'Player' die dan kan opgeslagen worden in onze databank.
            Player newPlayer = playerRepository.save(new Player.PlayerBuilder()
                    .accountId(summoner.getAccountId())
                    .leagueName(summoner.getName())
                    .firstName(player.getFirstName())
                    .lastName(player.getLastName())
                    .summonerID(summoner.getId())
                    .puuID(summoner.getPuuid())
                    .build());
            // Nu returnen we status: 201 created. Omdat onze player succesvol is aangemaakt.
            // Ook geven we als respons-body een PlayerDTO mee. TODO: Een aparte playerDTO is in de toekomst misschien niet meer nodig omdat alle velden van player kunnen gebruikt worden.
            return ResponseEntity.status(HttpStatus.CREATED).body(newPlayer);
        }
        // Indien ook de summonerService geen geldige summoner terug krijgt als respons. gooien we een exception dat de spelersnaam ongeldig is.
        throw new UsernameNotValid(player.getLeagueName());
    }
}
