package be.ucll.web;

import be.ucll.dao.MatchRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.MatchDTO;
import be.ucll.exceptions.*;
import be.ucll.models.Match;
import be.ucll.models.Team;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import liquibase.pro.packaged.M;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.swing.text.html.Option;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;


@RestController
@RequestMapping("match")
public class MatchResource {

    private MatchRepository matchRepository;
    private TeamRepository teamRepository;
    @Autowired
    public MatchResource(MatchRepository matchRepository, TeamRepository teamRepository){
        this.matchRepository = matchRepository;
        this.teamRepository = teamRepository;
    }

    @Operation(
            summary = "Create new match",
            description = "Using a teamname and a date (DD/MM/YYYY), create a new match"
    )
    @PostMapping
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Match> createMatch(@RequestBody MatchDTO matchDTO) throws ParameterInvalidException, AlreadyExistsException, NotFoundException {
        if (matchDTO.getDate() == null || matchDTO.getTeamId() == null) throw new ParameterInvalidException();
        if (matchDTO.getTeamId() <= 0) throw new ParameterInvalidException(matchDTO.getTeamId().toString());
        //Parse datum||
        Optional<Date> matchDate = Optional.ofNullable(parseDate(matchDTO.getDate()));
        //Team opzoeken
        Optional<Team> team1 = teamRepository.findTeamById(matchDTO.getTeamId());
        //TeamName juist ingegeven en gevonden
        if (team1.isEmpty()) throw new NotFoundException(matchDTO.getTeamId().toString());

        if(matchDate.isPresent()){

            //Kijk dat de datum niet in het verleden is
            if(isDateExpired(matchDate.get())){
                throw new ParameterInvalidException("Date has expired, "+matchDTO.getDate());
            }

            if(matchRepository.findMatchByTeam1AndAndDate(team1.get(),matchDate.get()).isPresent()){
                throw new AlreadyExistsException("Team "+team1.get().getName()+" on date "+matchDTO.getDate()+" ");
            }

            //Geen match gevonden waar het team in zit en op dezelfde datum afspeelt
            if(matchRepository.findMatchByTeam1AndAndDate(team1.get(),matchDate.get()).isEmpty()){
                Match newMatch = matchRepository.save(
                    new Match.MatchBuilder()
                        .team1Id(team1.get())
                        .date(matchDate.get())
                        .build()
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(newMatch);
            }else{
                throw new AlreadyExistsException(matchDTO.getDate());
            }
        }else{
            throw new ParameterInvalidException();
        }
    }

    @Operation(
            summary = "get match by id",
            description = "use a match id to retrieve the full match information"
    )
    @GetMapping("{matchId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Match> getMatch(@PathVariable("matchId") Long matchId) throws NotFoundException, ParameterInvalidException {
        return ResponseEntity.status(HttpStatus.OK).body(getMatchFromId(matchId));
    }

    @Operation(
            summary = "Update match",
            description = "Update an already created match"
    )
    @PutMapping("{matchId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Match> updateMatch(@PathVariable("matchId") Long matchId,@RequestBody MatchDTO matchDTO) throws NotFoundException, ParameterInvalidException, AlreadyExistsException {
        if (matchDTO.getDate() == null || matchDTO.getTeamId() == null) throw new ParameterInvalidException();
        if (matchDTO.getTeamId() <= 0) throw new ParameterInvalidException(matchDTO.getTeamId().toString());

        Match match = getMatchFromId(matchId);
        //check date
        Date newDate = parseDate(matchDTO.getDate());
        if(isDateExpired(newDate)){
            throw new ParameterInvalidException("Date has expired, "+matchDTO.getDate());
        }
        //check teamId
        Optional<Team> team = teamRepository.findTeamById(matchDTO.getTeamId()); //possibly throws exception
        if(team.isEmpty()){
            throw new NotFoundException(matchDTO.getTeamId().toString());
        }
        if(matchRepository.findMatchByTeam1AndAndDate(team.get(),newDate).isPresent()){
            throw new AlreadyExistsException("Team "+team.get().getName()+" on date "+matchDTO.getDate()+" ");
        }

        match.setDate(newDate);
        match.setTeam1(team.get());
        matchRepository.save(match);
        return ResponseEntity.status(HttpStatus.OK).body(match);
    }

    @Operation(
            summary = "Delete match",
            description = "Delete a specific match by id"
    )
    @DeleteMapping("{matchId}")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity deleteMatch(@PathVariable("matchId") Long matchId) throws ParameterInvalidException, NotFoundException {
        matchRepository.delete(getMatchFromId(matchId));
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
    @Operation(
            summary = "Set match as won",
            description = "Updates the isWinner status for this match"
    )
    @PutMapping("{matchId}/matchId/{isWinner}/isWinner")
    @PreAuthorize("hasRole('MANAGER')")
    public ResponseEntity<Match> setWinnerValue(@PathVariable("matchId") Long matchId,@PathVariable("isWinner") Boolean isWinner) throws ParameterInvalidException, NotFoundException {
        Match match = getMatchFromId(matchId);
        if(!(isWinner instanceof Boolean)){
            throw new ParameterInvalidException(isWinner.toString());
        }
        match.setIsWinner(isWinner);
        matchRepository.save(match);
        return ResponseEntity.status(HttpStatus.OK).body(match);
    }

    private Date parseDate (String matchDate) throws ParameterInvalidException {
        try {
            return new SimpleDateFormat("dd/MM/yyyy").parse(matchDate);
        }catch(ParseException e){
            throw new ParameterInvalidException(matchDate);
        }
    }
    private Boolean isDateExpired (Date date){
        return (date.before(new Date()) && !date.equals(new Date()));
    }

    private void checkId(Long id) throws ParameterInvalidException {
        if (id == null){
            throw new NullPointerException();
        }
        if (id <= 0)
            throw new ParameterInvalidException(id.toString());
    }
    private Match getMatchFromId(Long id) throws ParameterInvalidException, NotFoundException {
        checkId(id);
        Optional<Match> match = matchRepository.findMatchById(id); //possibly throws exception
        if(match.isEmpty()){
            throw new NotFoundException(id.toString());
        }else{
            return match.get();
        }
    }
}


