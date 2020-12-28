package be.ucll.web;

import be.ucll.dao.MatchRepository;
import be.ucll.dao.PlayerRepository;
import be.ucll.dao.TeamPlayerRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.IndividuallyPlayerDTO;
import be.ucll.dto.MatchHistoryDTO;
import be.ucll.dto.PlayerStatsDTO;
import be.ucll.exceptions.NotFoundException;
import be.ucll.exceptions.ParameterInvalidException;
import be.ucll.models.Match;
import be.ucll.models.TeamPlayer;
import be.ucll.service.MatchHistoryService;
import be.ucll.service.models.individuallyMatch.IndividuallyMatch;
import be.ucll.service.models.individuallyMatch.ParticipantFrames;
import be.ucll.service.models.match.Participant;
import be.ucll.service.models.match.ParticipantIdentity;
import be.ucll.service.models.match.Player;
import be.ucll.service.models.match.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
    TeamRepository teamRepository;

    @Autowired
    public MatchHistoryResource(MatchHistoryService matchHistoryService, MatchRepository matchRepository, TeamPlayerRepository teamPlayerRepository, PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.matchHistoryService = matchHistoryService;
        this.matchRepository = matchRepository;
        this.teamPlayerRepository = teamPlayerRepository;
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    @GetMapping
    public ResponseEntity<List<MatchHistoryDTO>> getMatchHistory(@RequestParam(value = "teamId", defaultValue = "0") Long teamId, @RequestParam(value = "date", defaultValue = "01-01-2000") String date) throws NotFoundException, ParseException, ParameterInvalidException {

        if (teamId < 0) throw new ParameterInvalidException(teamId.toString());

        String pattern = "dd-MM-yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
        Date dateFilter = simpleDateFormat.parse("01-01-2000");
        try {
            simpleDateFormat.parse(date);
        } catch (ParseException e) {
            throw new ParameterInvalidException(date);
        }

        List<Long> machIds = matchRepository.findAll().stream()
                .filter(teamId.equals(0L) ? m -> m.getTeam1().getId() > 0 : m -> m.getTeam1().getId().equals(teamId))
                .filter(date.equals("01-01-2000") ? m -> m.getDate().after(dateFilter) : m -> simpleDateFormat.format(m.getDate()).equals(date))
                .map(m -> m.getMatchId())
                .collect(Collectors.toList());

        List<be.ucll.service.models.Match> matchesFromLol = getMatchHistoriesFromLol(machIds);

        List<MatchHistoryDTO> matchHistoryDTOList = new ArrayList<>();

        for (be.ucll.service.models.Match m : matchesFromLol) {
            matchHistoryDTOList.add(createMatchHistoryDTO(m));
        }


        return ResponseEntity.status(HttpStatus.OK).body(matchHistoryDTOList);

    }


    @GetMapping("/{playerid}/player")
    public ResponseEntity<List<IndividuallyPlayerDTO>> getIndividuallyMatchHistory(@PathVariable("playerid") Long playerid) throws NotFoundException {

        if (playerRepository.findPlayerById(playerid).isEmpty()) throw new NotFoundException(playerid.toString());
        be.ucll.models.Player individuallyPlayer = playerRepository.findPlayerById(playerid).get();

        List<TeamPlayer> teamPlayers = teamPlayerRepository.findTeamsByPlayer(individuallyPlayer);

        List<be.ucll.models.Team> teamsFromPlayer = teamPlayers.stream()
                .filter(t -> t.getPlayer().getId().equals(playerid))
                .map(p -> p.getTeam())
                .collect(Collectors.toList());

        List<Long> matchIdsFromPlayer = teamsFromPlayer.stream()
                .map(m -> matchRepository.findMatchByTeam1(m).get().getMatchId())
                .collect(Collectors.toList());

        List<be.ucll.service.models.Match> matchesFromLol = getMatchHistoriesFromLol(matchIdsFromPlayer);


        List<IndividuallyPlayerDTO> individuallyPlayerDTOList = new ArrayList<>();

        for (be.ucll.service.models.Match m : matchesFromLol) {
            individuallyPlayerDTOList.add(createIndividuallyDTO(m, individuallyPlayer));
        }

        return ResponseEntity.status(HttpStatus.OK).body(individuallyPlayerDTOList);
    }


    private List<be.ucll.service.models.Match> getMatchHistoriesFromLol(List<Long> matchIds) {
        List<be.ucll.service.models.Match> matches = new ArrayList<>();
        for (Long id : matchIds
        ) {
            matches.add(matchHistoryService.getMatch(id).get());
        }
        return matches;
    }


    private MatchHistoryDTO createMatchHistoryDTO(be.ucll.service.models.Match match) throws NotFoundException {

        Team team100 = getTeam100(match);

        Team team200 = getTeam200(match);


        List<Player> allPlayersTeam100 = getAllPlayersTeam100(getParticipantIdentitiesTeam100(match, getAllParticipantsIdsTeam100(getParticipantsTeam100(match))));

        List<Player> allPlayersTeam200 = getAllPlayersTeam200(getParticipantIdentitiesTeam200(match, getAllParticipantsIdsTeam200(getParticipantsTeam200(match))));


        List<Long> allKillsTeam100 = getAllKillsTeam100(getParticipantsTeam100(match));

        Long totalKillsTeam100 = allKillsTeam100.stream().mapToLong(i -> i.longValue()).sum();

        List<Long> allKillsTeam200 = getAllKillsTeam200(getParticipantsTeam200(match));

        Long totalKillsTeam200 = allKillsTeam200.stream().mapToLong(i -> i.longValue()).sum();

        List<Long> allDeathsTeam100 = getAllDeathsTeam100(getParticipantsTeam100(match));

        Long totalDeathsTeam100 = allDeathsTeam100.stream().mapToLong(i -> i.longValue()).sum();

        List<Long> allDeathsTeam200 = getAllDeathsTeam200(getParticipantsTeam200(match));

        Long totalDeathsTeam200 = allDeathsTeam200.stream().mapToLong(i -> i.longValue()).sum();

        List<Long> allAssistsTeam100 = getAllAssistsTeam100(getParticipantsTeam100(match));

        Long totalAssistsTeam100 = allAssistsTeam100.stream().mapToLong(i -> i.longValue()).sum();

        List<Long> allAssistsTeam200 = getAllAssistsTeam200(getParticipantsTeam200(match));

        Long totalAssistsTeam200 = allAssistsTeam200.stream().mapToLong(i -> i.longValue()).sum();


        Match match1 = findMatchFromDatabase(match);

        be.ucll.models.Team team1 = match1.getTeam1();

        MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();

        matchHistoryDTO.setTeamId(team1.getId());
        matchHistoryDTO.setMatchDate(match1.getDate().toString());

        if (isWeAreTeam100(team1, allPlayersTeam100)) {

            matchHistoryDTO.setWon1(team100.getWin());

            matchHistoryDTO.setKillsTeam1(totalKillsTeam100);
            matchHistoryDTO.setAssistsTeam1(totalAssistsTeam100);
            matchHistoryDTO.setDeathsTeam1(totalDeathsTeam100);

            matchHistoryDTO.setPlayersTeam1(getPlayersTeam1(allPlayersTeam100));

            matchHistoryDTO.setKillsTeam2(totalKillsTeam200);
            matchHistoryDTO.setAssistsTeam2(totalAssistsTeam200);
            matchHistoryDTO.setDeathsTeam2(totalDeathsTeam200);

            matchHistoryDTO.setPlayersTeam2(getUsersTeamEnemy(allPlayersTeam200));

        } else {

            matchHistoryDTO.setWon1(team200.getWin());

            matchHistoryDTO.setKillsTeam1(totalKillsTeam200);
            matchHistoryDTO.setAssistsTeam1(totalAssistsTeam200);
            matchHistoryDTO.setDeathsTeam1(totalDeathsTeam200);

            matchHistoryDTO.setPlayersTeam1(getPlayersTeam1(allPlayersTeam200));

            matchHistoryDTO.setKillsTeam2(totalKillsTeam100);
            matchHistoryDTO.setAssistsTeam2(totalAssistsTeam100);
            matchHistoryDTO.setDeathsTeam2(totalDeathsTeam100);

            matchHistoryDTO.setPlayersTeam2(getUsersTeamEnemy(allPlayersTeam100));
        }

        return matchHistoryDTO;

    }


    private IndividuallyPlayerDTO createIndividuallyDTO(be.ucll.service.models.Match match, be.ucll.models.Player player) throws NotFoundException {

        Team team100 = getTeam100(match);

        Team team200 = getTeam200(match);

        List<Participant> participantsTeam100 = getParticipantsTeam100(match);

        List<Participant> participantsTeam200 = getParticipantsTeam200(match);

        List<Long> allParticipantsIdsTeam100 = getAllParticipantsIdsTeam100(participantsTeam100);

        List<Long> allParticipantsIdsTeam200 = getAllParticipantsIdsTeam100(participantsTeam200);

        List<ParticipantIdentity> participantIdentitiesTeam100 = getParticipantIdentitiesTeam100(match, allParticipantsIdsTeam100);

        List<ParticipantIdentity> participantIdentitiesTeam200 = getParticipantIdentitiesTeam100(match, allParticipantsIdsTeam200);

        List<Player> allPlayersTeam100 = getAllPlayersTeam100(participantIdentitiesTeam100);

        List<Player> allPlayersTeam200 = getAllPlayersTeam100(participantIdentitiesTeam200);


        Match match1 = findMatchFromDatabase(match);

        be.ucll.models.Team team1 = match1.getTeam1();


        MatchHistoryDTO matchHistoryDTO = new MatchHistoryDTO();

        matchHistoryDTO.setTeamId(team1.getId());
        matchHistoryDTO.setMatchDate(match1.getDate().toString());

        IndividuallyPlayerDTO individuallyPlayerDTO = new IndividuallyPlayerDTO();

        individuallyPlayerDTO.setMatchId(match1.getId());
        individuallyPlayerDTO.setMatchDate(match1.getDate().toString());

        if (isWeAreTeam100(team1, allPlayersTeam100)) {

            Optional<Long> participantIdPlayer = getParticipantIdPlayerTeam100(player, getParticipantIdentitiesTeam100(match, allParticipantsIdsTeam100));

            if (participantIdPlayer.isEmpty()) throw new NotFoundException("participantIdPlayer");

            Long currentGold = getCurrentGold(match, participantIdPlayer.get());

            Optional<Long> killsPlayer = getKillsPlayerTeam100(participantIdPlayer.get(), participantsTeam100);

            Optional<Long> assistsPlayer = getassistsPlayerTeam100(participantIdPlayer.get(), participantsTeam100);

            Optional<Long> deathsPlayer = getdeathsPlayerTeam100(participantIdPlayer.get(), participantsTeam100);

            individuallyPlayerDTO.setWon(team100.getWin());
            individuallyPlayerDTO.setKills(killsPlayer.get());
            individuallyPlayerDTO.setAssists(assistsPlayer.get());
            individuallyPlayerDTO.setDeaths(deathsPlayer.get());
            individuallyPlayerDTO.setCurrentGold(currentGold);
            individuallyPlayerDTO.setPlayersTeam1(getPlayersTeam1(allPlayersTeam100));
            individuallyPlayerDTO.setPlayersTeam2(getUsersTeamEnemy(allPlayersTeam200));

        } else {

            Optional<Long> participantIdPlayer = getParticipantIdPlayerTeam200(player, getParticipantIdentitiesTeam100(match, allParticipantsIdsTeam200));

            if (participantIdPlayer.isEmpty()) throw new NotFoundException("participantIdPlayer");

            Long currentGold = getCurrentGold(match, participantIdPlayer.get());

            Optional<Long> killsPlayer = getKillsPlayerTeam200(participantIdPlayer.get(), participantsTeam200);

            Optional<Long> assistsPlayer = getassistsPlayerTeam200(participantIdPlayer.get(), participantsTeam200);

            Optional<Long> deathsPlayer = getdeathsPlayerTeam200(participantIdPlayer.get(), participantsTeam200);

            individuallyPlayerDTO.setWon(team200.getWin());
            individuallyPlayerDTO.setKills(killsPlayer.get());
            individuallyPlayerDTO.setAssists(assistsPlayer.get());
            individuallyPlayerDTO.setDeaths(deathsPlayer.get());
            individuallyPlayerDTO.setCurrentGold(currentGold);
            individuallyPlayerDTO.setPlayersTeam1(getPlayersTeam1(allPlayersTeam200));
            individuallyPlayerDTO.setPlayersTeam2(getUsersTeamEnemy(allPlayersTeam100));

        }

        return individuallyPlayerDTO;
    }


    private Team getTeam100(be.ucll.service.models.Match match) {
        return match.getTeams().stream()
                .filter(t -> t.getTeamId() == 100)
                .findFirst().orElseThrow();
    }

    private Team getTeam200(be.ucll.service.models.Match match) {
        return match.getTeams().stream()
                .filter(t -> t.getTeamId() == 200)
                .findFirst().orElseThrow();
    }

    private List<Participant> getParticipantsTeam100(be.ucll.service.models.Match match) {
        return match.getParticipants().stream()
                .filter(t -> t.getTeamId() == 100)
                .collect(Collectors.toList());
    }

    private List<Participant> getParticipantsTeam200(be.ucll.service.models.Match match) {
        return match.getParticipants().stream()
                .filter(t -> t.getTeamId() == 200)
                .collect(Collectors.toList());
    }

    private List<Long> getAllParticipantsIdsTeam100(List<Participant> participantsTeam100) {
        return participantsTeam100.stream()
                .map(p -> p.getParticipantId())
                .collect(Collectors.toList());
    }


    private List<Long> getAllParticipantsIdsTeam200(List<Participant> participantsTeam200) {
        return participantsTeam200.stream()
                .map(p -> p.getParticipantId())
                .collect(Collectors.toList());
    }

    private List<ParticipantIdentity> getParticipantIdentitiesTeam100(be.ucll.service.models.Match match, List<Long> allParticipantsIdsTeam100) {
        List<ParticipantIdentity> participantIdentitiesTeam100 = new ArrayList<>();
        for (ParticipantIdentity p : match.getParticipantIdentities()) {
            for (Long id : allParticipantsIdsTeam100) {
                if (p.getParticipantId().equals(id)) {
                    participantIdentitiesTeam100.add(p);
                }
            }
        }
        return participantIdentitiesTeam100;
    }

    private List<ParticipantIdentity> getParticipantIdentitiesTeam200(be.ucll.service.models.Match match, List<Long> allParticipantsIdsTeam200) {
        List<ParticipantIdentity> participantIdentitiesTeam200 = new ArrayList<>();
        for (ParticipantIdentity p : match.getParticipantIdentities()) {
            for (Long id : allParticipantsIdsTeam200) {
                if (p.getParticipantId().equals(id)) {
                    participantIdentitiesTeam200.add(p);
                }
            }
        }
        return participantIdentitiesTeam200;
    }

    private List<Player> getAllPlayersTeam100(List<ParticipantIdentity> participantIdentitiesTeam100) {
        return participantIdentitiesTeam100.stream()
                .map(p -> p.getPlayer())
                .collect(Collectors.toList());
    }

    private List<Player> getAllPlayersTeam200(List<ParticipantIdentity> participantIdentitiesTeam200) {
        return participantIdentitiesTeam200.stream()
                .map(p -> p.getPlayer())
                .collect(Collectors.toList());
    }

    private boolean isWeAreTeam100(be.ucll.models.Team team, List<Player> allPlayersTeam100) throws NotFoundException {

        List<TeamPlayer> teamPlayers = teamPlayerRepository.findPlayersByTeam(team);

        Optional<be.ucll.models.Player> playerFromDb = teamPlayers.stream()
                .map(tp -> tp.getPlayer())
                .findAny();

        if (playerFromDb.isEmpty()) throw new NotFoundException("player");

        boolean isWeAreTeam100 = false;

        for (Player p : allPlayersTeam100) {
            if (playerFromDb.get().getLeagueName().equals(p.getSummonerName())) {
                isWeAreTeam100 = true;
            }
        }
        return isWeAreTeam100;
    }

    private be.ucll.models.Match findMatchFromDatabase(be.ucll.service.models.Match match) throws NotFoundException {
        if (matchRepository.findMatchByMatchID(match.getGameId()).isEmpty())
            throw new NotFoundException(match.getGameId().toString());
        return matchRepository.findMatchByMatchID(match.getGameId()).get();
    }

    private Optional<Long> getParticipantIdPlayerTeam100(be.ucll.models.Player player, List<ParticipantIdentity> participantIdentitiesTeam100) {
        return participantIdentitiesTeam100.stream()
                .filter(p -> p.getPlayer().getSummonerName().equals(player.getLeagueName()))
                .map(p -> p.getParticipantId())
                .findFirst();
    }

    private Optional<Long> getParticipantIdPlayerTeam200(be.ucll.models.Player player, List<ParticipantIdentity> participantIdentitiesTeam200) {
        return participantIdentitiesTeam200.stream()
                .filter(p -> p.getPlayer().getSummonerName().equals(player.getLeagueName()))
                .map(p -> p.getParticipantId())
                .findFirst();
    }

    private List<Long> getAllKillsTeam100(List<Participant> participantsTeam100){
        return participantsTeam100.stream()
                .map(p -> p.getStats().getKills())
                .collect(Collectors.toList());
    }

    private List<Long> getAllKillsTeam200(List<Participant> participantsTeam200){
        return participantsTeam200.stream()
                .map(p -> p.getStats().getKills())
                .collect(Collectors.toList());
    }

    private List<Long> getAllDeathsTeam100(List<Participant> participantsTeam100){
        return participantsTeam100.stream()
                .map(p -> p.getStats().getDeaths())
                .collect(Collectors.toList());
    }

    private List<Long> getAllDeathsTeam200(List<Participant> participantsTeam200){
        return participantsTeam200.stream()
                .map(p -> p.getStats().getDeaths())
                .collect(Collectors.toList());
    }

    private List<Long> getAllAssistsTeam100(List<Participant> participantsTeam100){
        return participantsTeam100.stream()
                .map(p -> p.getStats().getAssists())
                .collect(Collectors.toList());
    }

    private List<Long> getAllAssistsTeam200(List<Participant> participantsTeam200){
        return participantsTeam200.stream()
                .map(p -> p.getStats().getAssists())
                .collect(Collectors.toList());
    }

    private Optional<Long> getKillsPlayerTeam100(Long participantIdPlayer, List<Participant> participantsTeam100) {
        return participantsTeam100.stream()
                .filter(p -> p.getParticipantId().equals(participantIdPlayer))
                .map(p -> p.getStats().getKills())
                .findFirst();
    }

    private Optional<Long> getassistsPlayerTeam100(Long participantIdPlayer, List<Participant> participantsTeam100) {
        return participantsTeam100.stream()
                .filter(p -> p.getParticipantId().equals(participantIdPlayer))
                .map(p -> p.getStats().getAssists())
                .findFirst();
    }

    private Optional<Long> getdeathsPlayerTeam100(Long participantIdPlayer, List<Participant> participantsTeam100) {
        return participantsTeam100.stream()
                .filter(p -> p.getParticipantId().equals(participantIdPlayer))
                .map(p -> p.getStats().getDeaths())
                .findFirst();
    }

    private Optional<Long> getKillsPlayerTeam200(Long participantIdPlayer, List<Participant> participantsTeam200) {
        return participantsTeam200.stream()
                .filter(p -> p.getParticipantId().equals(participantIdPlayer))
                .map(p -> p.getStats().getKills())
                .findFirst();
    }

    private Optional<Long> getassistsPlayerTeam200(Long participantIdPlayer, List<Participant> participantsTeam200) {
        return participantsTeam200.stream()
                .filter(p -> p.getParticipantId().equals(participantIdPlayer))
                .map(p -> p.getStats().getAssists())
                .findFirst();
    }

    private Optional<Long> getdeathsPlayerTeam200(Long participantIdPlayer, List<Participant> participantsTeam200) {
        return participantsTeam200.stream()
                .filter(p -> p.getParticipantId().equals(participantIdPlayer))
                .map(p -> p.getStats().getDeaths())
                .findFirst();
    }

    private Long getCurrentGold(be.ucll.service.models.Match match, Long participantIdPlayer) {
        IndividuallyMatch individuallyMatch = matchHistoryService.getExtraMatchinfo(match.getGameId()).get();

        ParticipantFrames participantFrame = individuallyMatch.getFrames().get(individuallyMatch.getFrames().size() - 1).getParticipantFrames();

        Long currentGold;

        switch (participantIdPlayer.intValue()) {
            case 1:
                currentGold = participantFrame.getOne().getCurrentGold();
                break;
            case 2:
                currentGold = participantFrame.getTwo().getCurrentGold();
                break;
            case 3:
                currentGold = participantFrame.getThree().getCurrentGold();
                break;
            case 4:
                currentGold = participantFrame.getFour().getCurrentGold();
                break;
            case 5:
                currentGold = participantFrame.getFive().getCurrentGold();
                break;
            case 6:
                currentGold = participantFrame.getSix().getCurrentGold();
                break;
            case 7:
                currentGold = participantFrame.getSeven().getCurrentGold();
                break;
            case 8:
                currentGold = participantFrame.getEight().getCurrentGold();
                break;
            case 9:
                currentGold = participantFrame.getNine().getCurrentGold();
                break;
            case 10:
                currentGold = participantFrame.getTen().getCurrentGold();
                break;
            default:
                currentGold = 0L;
        }
        return currentGold;
    }

    private List<PlayerStatsDTO> getPlayersTeam1(List<Player> allPlayersTeam1) {
        return allPlayersTeam1.stream()
                .map(p -> {
                    be.ucll.models.Player player = playerRepository.findPlayerByLeagueNameIgnoreCase(p.getSummonerName()).get();
                    PlayerStatsDTO playerStatsDTO = new PlayerStatsDTO();
                    playerStatsDTO.setPlayerId(player.getId());
                    playerStatsDTO.setSummonerName(p.getSummonerName());

                    return playerStatsDTO;
                }).collect(Collectors.toList());
    }

    private List<String> getUsersTeamEnemy(List<Player> allPlayersTeamEnemy) {
        return allPlayersTeamEnemy.stream()
                .map(p -> p.getSummonerName())
                .collect(Collectors.toList());
    }


}
