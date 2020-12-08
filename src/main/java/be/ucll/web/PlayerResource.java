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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

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
    @PostMapping
    // De functie wordt aangeroepen door middel van een postrequest. met als input: JSON-object Player: { "leagueName" : "7Stijn7" }
    public ResponseEntity<Player> createPlayer(@RequestBody PlayerDTO player) throws UsernameNotValid, UsernameAlreadyExists {

        // check of alles ingevult is.
        if (player.getLeagueName() == null || player.getLeagueName().length() == 0
                || player.getFirstName() == null || player.getFirstName().length() == 0
                || player.getLastName() == null || player.getLastName().length() == 0 ) throw new UsernameNotValid(player.getLeagueName());

        // Daarna wordt er aan de playerRepository gevraagd of deze speler al gevonden is (op basis van de username), en al in onze databank zit.
        // Zoja, Gooit het een exception: dat de speler al bestaat in ons systeem.
        playerExists(player.getLeagueName());
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

    // player Updaten
    @PutMapping
    public PlayerDTO updatePlayer(@RequestParam("leagueName") String leagueName, @RequestBody PlayerDTO playerDTO) throws HttpClientErrorException, UsernameNotFound, UsernameAlreadyExists{
        //We gaan controleren of de speler waarvan de leagueName gegeven is, of deze wel bestaat indien deze niet bestaat,
        // laten we zien dat de username niet gevonden is
        if (playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isPresent()){

            //Wanneer deze speler bestaat, gaan we die in een Player object steken.
            Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get();

            //Dan kijken we of ze de league naam van de speler willen veranderen
            if (!player.getLeagueName().equals(playerDTO.getLeagueName())){

                //we gaan controleren of de nieuwe league naam al in onze databank zit, indien dit het geval is, komt er
                //een exception usernameAlreadyExists
                playerExists(playerDTO.getLeagueName());

                //Indien deze er nog niet in steekt, gaan we de gegevens van de speler aan de league api opvragen, als deze
                //de league naam niet kent, gaat er weer een exception komen (van de league api) en die geven we gewoon door
                //aan de gebruiker.
                Summoner summoner = summonerService.getSummoner(playerDTO.getLeagueName()).get();

                //Wanneer de speler wel bestaat in league api gaan we onze player aanpassen naar alle gegevens die we
                // terugkrijgen van de api
                player.setLeagueName(summoner.getName());
                player.setAccountId(summoner.getAccountId());
                player.setSummonerID(summoner.getId());
                player.setPuuID(summoner.getPuuid());
            }
            //controleren of de gebruiker een andere voornaam wilt
            if( !player.getFirstName().equals(playerDTO.getFirstName())){
                player.setFirstName(playerDTO.getFirstName());
            }
            //controleren of de gebruiker een andere achternaam wilt
            if( !player.getLastName().equals(playerDTO.getLastName())){
                player.setLastName(playerDTO.getLastName());
            }

            //spele opslaan in db en de nieuwe speler tonen aan gebruiker.
            playerRepository.save(player);
            return new PlayerDTO(player.getLeagueName(), player.getFirstName(), player.getLastName());
        }

        //Exception om te tonen dat de speler waarvan je iets wilt aanpassen niet bestaat.
        throw new UsernameNotFound(leagueName);
    }

    @GetMapping
    public ResponseEntity<PlayerDTO> getPlayer(@RequestParam("leagueName") String leagueName) throws UsernameNotFound {
        //controleren of speler in onze db bestaat
        if(playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isPresent()) {
            //speler opvragen en teruggeven
            Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get();
            return ResponseEntity.status(HttpStatus.OK).body((new PlayerDTO(player.getLeagueName(), player.getFirstName(), player.getLastName())));
        }
        throw new UsernameNotFound(leagueName);
    }

    @DeleteMapping
    public ResponseEntity deletePlayer(@RequestParam("leagueName") String leagueName) throws UsernameNotFound {
        //We gaan controleren of de speler waarvan de leagueName gegeven is, of deze wel bestaat in onze db
        if(playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isPresent()) {

            //Zo ja, dan verwijderen we deze
            playerRepository.delete(playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        //zo niet => exception
        throw new UsernameNotFound(leagueName);
    }

    //Helper methode om te controleren of de league naam reeds in onze db steekt
    public boolean playerExists(String leagueName) throws UsernameAlreadyExists{
        if(playerRepository.findPlayerByLeagueNameIgnoreCase(leagueName).isPresent()){
            throw new UsernameAlreadyExists(leagueName);
        }
        else{
            return false;
        }
    }

}
