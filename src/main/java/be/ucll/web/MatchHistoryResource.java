package be.ucll.web;

import be.ucll.dao.MatchRepository;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dto.MatchHistoryDTO;
import be.ucll.dto.PlayerStatsDTO;
import be.ucll.exceptions.NotFoundException;
import be.ucll.models.Match;
import be.ucll.models.TeamPlayer;
import be.ucll.service.MatchHistoryService;
import be.ucll.service.models.match.Participant;
import be.ucll.service.models.match.ParticipantIdentity;
import be.ucll.service.models.match.Player;
import be.ucll.service.models.match.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("matchhistory")
public class MatchHistoryResource {

    MatchHistoryService matchHistoryService;
    MatchRepository matchRepository;
    TeamPlayerRepository teamPlayerRepository;
    PlayerRepository playerRepository;

    @Autowired
    public MatchHistoryResource(MatchHistoryService matchHistoryService, MatchRepository matchRepository, TeamPlayerRepository teamPlayerRepository, PlayerRepository playerRepository) {
        this.matchHistoryService = matchHistoryService;
        this.matchRepository = matchRepository;
        this.teamPlayerRepository = teamPlayerRepository;
        this.playerRepository = playerRepository;
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
    public ResponseEntity<List<MatchHistoryDTO>> getMatchHistory(@RequestParam(value = "teamId", defaultValue = "0") Long teamId) throws NotFoundException {

        // we nemen 1 partici. van team 100 (via lol naam of id)
        // dan kijken we of deze in onze databank zit --> zo ja: team 100 = ons team, zo niet team 100 ander team
        // als het ons team is: getwin van team 100,

        List<Long> machIds = matchRepository.findAll().stream()
                .filter(teamId.equals(0L) ? m -> m.getTeam1().getId() > 0 : m -> m.getTeam1().getId().equals(teamId))
                .map(m -> m.getMatchId())
                .collect(Collectors.toList());

        List<be.ucll.service.models.Match> matchesFromLol = getMatchHistoriesFromLol(machIds);

        List<MatchHistoryDTO> matchHistoryDTOList = new ArrayList<>();

        for (be.ucll.service.models.Match m  : matchesFromLol){
            matchHistoryDTOList.add(createMatchHistoryDTO(m));
        }


        return ResponseEntity.status(HttpStatus.OK).body(matchHistoryDTOList);

    }

    private List<be.ucll.service.models.Match> getMatchHistoriesFromLol(List<Long> matchIds){
        List<be.ucll.service.models.Match> matches = new ArrayList<>();
        for (Long id: matchIds
             ) {
             matches.add(matchHistoryService.getMatch(id).get());
        }
        return matches;
    }



    private MatchHistoryDTO createMatchHistoryDTO(be.ucll.service.models.Match match) throws NotFoundException {

        Team team100 = match.getTeams().stream()
                .filter(t -> t.getTeamId() == 100)
                .findFirst().orElseThrow();

        Team team200 = match.getTeams().stream()
                .filter(t -> t.getTeamId() == 200)
                .findFirst().orElseThrow();

        List<Participant> participantsTeam100 = match.getParticipants().stream()
                .filter(t -> t.getTeamId() == 100)
                .collect(Collectors.toList());

        List<Participant> participantsTeam200 = match.getParticipants().stream()
                .filter(t -> t.getTeamId() == 200)
                .collect(Collectors.toList());


        boolean isTeam100Win = team100.getWin().equals("Win");

        boolean isTeam200Win = team200.getWin().equals("Win");


        List<Long> allParticipantsIdsTeam100 = participantsTeam100.stream()
                .map(p -> p.getParticipantId())
                .collect(Collectors.toList());

        List<Long> allParticipantsIdsTeam200 = participantsTeam200.stream()
                .map(p -> p.getParticipantId())
                .collect(Collectors.toList());


        List<ParticipantIdentity> participantIdentitiesTeam100 = new ArrayList<>();
        for (ParticipantIdentity p : match.getParticipantIdentities()){
            for (Long id : allParticipantsIdsTeam100){
                if(p.getParticipantId().equals(id)){
                    participantIdentitiesTeam100.add(p);
                }
            }
        }

        List<ParticipantIdentity> participantIdentitiesTeam200 = new ArrayList<>();
        for (ParticipantIdentity p : match.getParticipantIdentities()){
            for (Long id : allParticipantsIdsTeam200){
                if(p.getParticipantId().equals(id)){
                    participantIdentitiesTeam200.add(p);
                }
            }
        }


        List<Player> allPlayersTeam100 = participantIdentitiesTeam100.stream()
                .map(p -> p.getPlayer())
                .collect(Collectors.toList());

        List<Player> allPlayersTeam200 = participantIdentitiesTeam200.stream()
                .map(p -> p.getPlayer())
                .collect(Collectors.toList());



        List<Long> allKillsTeam100 = participantsTeam100.stream()
                .map(p -> p.getStats().getKills())
                .collect(Collectors.toList());

        Long totalKillsTeam100 = allKillsTeam100.stream().mapToLong(i -> i.longValue()).sum();



        List<Long> allKillsTeam200 = participantsTeam200.stream()
                .map(p -> p.getStats().getKills())
                .collect(Collectors.toList());

        Long totalKillsTeam200 = allKillsTeam200.stream().mapToLong(i -> i.longValue()).sum();



        List<Long> allDeathsTeam100 = participantsTeam100.stream()
                .map(p -> p.getStats().getDeaths())
                .collect(Collectors.toList());

        Long totalDeathsTeam100 = allDeathsTeam100.stream().mapToLong(i -> i.longValue()).sum();

        List<Long> allDeathsTeam200 = participantsTeam200.stream()
                .map(p -> p.getStats().getDeaths())
                .collect(Collectors.toList());

        Long totalDeathsTeam200 = allDeathsTeam200.stream().mapToLong(i -> i.longValue()).sum();



        List<Long> allAssistsTeam100 = participantsTeam100.stream()
                .map(p -> p.getStats().getAssists())
                .collect(Collectors.toList());

        Long totalAssistsTeam100 = allAssistsTeam100.stream().mapToLong(i -> i.longValue()).sum();



        List<Long> allAssistsTeam200 = participantsTeam200.stream()
                .map(p -> p.getStats().getAssists())
                .collect(Collectors.toList());

        Long totalAssistsTeam200 = allAssistsTeam200.stream().mapToLong(i -> i.longValue()).sum();


        if (matchRepository.findMatchByMatchID(match.getGameId()).isEmpty()) throw new NotFoundException(match.getGameId().toString());

        Match match1 = matchRepository.findMatchByMatchID(match.getGameId()).get();

        be.ucll.models.Team team1 = match1.getTeam1();

        List<TeamPlayer> teamPlayers = teamPlayerRepository.findPlayersByTeam(team1);

        Optional<be.ucll.models.Player> playerFromDb = teamPlayers.stream()
                .map(tp -> tp.getPlayer())
                .findAny();

        if (playerFromDb.isEmpty()) throw new NotFoundException("player");

        boolean isWeAreTeam100 = false;

        for (Player p : allPlayersTeam100){
            if (playerFromDb.get().getLeagueName().equals(p.getSummonerName())){
                isWeAreTeam100 = true;
            }
        }

        MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();


        if (isWeAreTeam100){
            /*MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();*/
            matchHistoryDTO.setTeamId(team1.getId());
            matchHistoryDTO.setWon1(team100.getWin());
            matchHistoryDTO.setMatchDate(match1.getDate().toString());

            matchHistoryDTO.setKillsTeam1(totalKillsTeam100);
            matchHistoryDTO.setAssistsTeam1(totalAssistsTeam100);
            matchHistoryDTO.setDeathsTeam1(totalDeathsTeam100);

            List<PlayerStatsDTO> playersTeam1 = allPlayersTeam100.stream()
                    .map(p -> {
                        be.ucll.models.Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(p.getSummonerName()).get();
                        PlayerStatsDTO playerStatsDTO = new PlayerStatsDTO();
                        playerStatsDTO.setPlayerId(player.getId());
                        playerStatsDTO.setSummonerName(p.getSummonerName());

                        return playerStatsDTO;
                    }).collect(Collectors.toList());

            matchHistoryDTO.setPlayersTeam1(playersTeam1);


            matchHistoryDTO.setKillsTeam2(totalKillsTeam200);
            matchHistoryDTO.setAssistsTeam2(totalAssistsTeam200);
            matchHistoryDTO.setDeathsTeam2(totalDeathsTeam200);

            List<String> usersTeamEnemy = allPlayersTeam200.stream()
                    .map(p -> p.getSummonerName())
                    .collect(Collectors.toList());

            matchHistoryDTO.setPlayersTeam2(usersTeamEnemy);

       } else {
           /*MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();*/
           matchHistoryDTO.setTeamId(team1.getId());
           matchHistoryDTO.setWon1(team200.getWin());
           matchHistoryDTO.setMatchDate(match1.getDate().toString());

           matchHistoryDTO.setKillsTeam1(totalKillsTeam200);
           matchHistoryDTO.setAssistsTeam1(totalAssistsTeam200);
           matchHistoryDTO.setDeathsTeam1(totalDeathsTeam200);

           List<PlayerStatsDTO> playersTeam1 = allPlayersTeam200.stream()
                   .map(p -> {
                       be.ucll.models.Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(p.getSummonerName()).get();
                       PlayerStatsDTO playerStatsDTO = new PlayerStatsDTO();
                       playerStatsDTO.setPlayerId(player.getId());
                       playerStatsDTO.setSummonerName(p.getSummonerName());

                       return playerStatsDTO;
                   }).collect(Collectors.toList());

           matchHistoryDTO.setPlayersTeam1(playersTeam1);


           matchHistoryDTO.setKillsTeam2(totalKillsTeam100);
           matchHistoryDTO.setAssistsTeam2(totalAssistsTeam100);
           matchHistoryDTO.setDeathsTeam2(totalDeathsTeam100);

           List<String> usersTeamEnemy = allPlayersTeam100.stream()
                   .map(p -> p.getSummonerName())
                   .collect(Collectors.toList());

           matchHistoryDTO.setPlayersTeam2(usersTeamEnemy);
       }

        return matchHistoryDTO;

    }


}
