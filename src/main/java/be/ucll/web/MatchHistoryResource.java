package be.ucll.web;

import be.ucll.dao.MatchRepository;
import be.ucll.dto.MatchHistoryDTO;
import be.ucll.models.Match;
import be.ucll.models.Player;
import be.ucll.service.MatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("matchhistory")
public class MatchHistoryResource {

    MatchHistoryService matchHistoryService;
    MatchRepository matchRepository;

    @Autowired
    public MatchHistoryResource(MatchHistoryService matchHistoryService, MatchRepository matchRepository) {
        this.matchHistoryService = matchHistoryService;
        this.matchRepository = matchRepository;

    }




    /*
        Ja dus de bedoeling van uw request is idd dat ge alle gespeelde matchen (dus history) van alle teams terug krijgt,
        dus een geschiedenis is idd op datum gefilterd, hoe langer geleden de match is, hoe later die match getoond wordt.
        Dan voor duidelijkheid zou ik idd zeggen pak enkel de 20 laatste, maar dat is technisch gezien niet de bedoeling,
        normaal moet je alle matchen terug geven
        die 20 kan je afhandelen in jouw gedeelte (dus niet het gezamenlijke);
        zodat je het daar ook makkelijk in kan aanpassen dat je ipv maar 20, ze allemaal wilt terug geven


         */
    @GetMapping
    public ResponseEntity<List<MatchHistoryDTO>> getMatchHistory(){

        List<Long> matchIdFromDb = matchRepository.findAll().stream()
                .map(Match::getMatchId).collect(Collectors.toList());


        List<be.ucll.service.models.Match> matches = matchHistoryService.getMatches(matchIdFromDb);

        //matches.stream().filter(x -> x.getTeams().stream().filter(e -> e.getTeamId() == 100))


        List<MatchHistoryDTO> history = matches.stream().map(x -> {

            MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();
            matchHistoryDTO.setTeamId(matchRepository.findMatchByMatchID(x.getGameId()).get().getTeam1().getId());
            matchHistoryDTO.setMatchDate(matchRepository.findMatchByMatchID(x.getGameId()).get().getDate().toString());

            // we nemen 1 partici. van team 100 (via lol naam of id)
            // dan kijken we of deze in onze databank zit --> zo ja: team 100 = ons team, zo niet team 100 ander team
            // als het ons team is: getwin van team 100,



            return matchHistoryDTO;
        }).collect(Collectors.toList());
        return ResponseEntity.status(HttpStatus.OK).body(history);

    }

    private List<be.ucll.service.models.Match> getMatchHistoryFromLol(List<Long> matchIds){
        List<be.ucll.service.models.Match> matches = new ArrayList<>();
        for (Long id: matchIds
             ) {
             matches.add(matchHistoryService.getMatch(id).get());
        }
        return matches;
    }









}
