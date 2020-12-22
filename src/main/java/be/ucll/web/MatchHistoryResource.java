package be.ucll.web;

import be.ucll.dao.MatchRepository;
import be.ucll.dto.MatchHistoryDTO;
import be.ucll.models.Match;
import be.ucll.service.MatchHistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.websocket.server.PathParam;
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
        List<Match> matchesInDb = matchRepository.findAll();

        List<Long> matchIdFromDb = matchesInDb.stream()
                .map(Match::getMatchId)
                .collect(Collectors.toList());

        //List<be.ucll.service.models.Match> matchesLeagueResponse = matchHistoryService.getMatches(matchIdFromDb);

        matchesInDb.stream()
                .map(x -> {
                    MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();
                    matchHistoryDTO.setMatchDate(x.getDate().toString());
                    matchHistoryDTO.setTeamId(x.getTeam1().getId());
                    matchHistoryDTO.setWon(matchHistoryService.getMatch(x.getMatchId()).get().getTeams().get(0).getWin());
                })
        /*
        matchesLeagueResponse.stream()
                .map(x -> {
                    MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();
                    matchHistoryDTO.setMatchDate();
                })

        List<MatchHistoryDTO> allMatches = matchesInDb
                .stream()
                .map(x -> {
                    MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();
                    matchHistoryDTO.setMatchDate(x.getDate().toString());
                    matchHistoryDTO.setDeaths();
                })

         */
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
