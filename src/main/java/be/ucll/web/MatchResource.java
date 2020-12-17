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
import org.springframework.web.bind.annotation.*;

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
    public ResponseEntity<Match> createMatch(@RequestBody MatchDTO matchDTO) throws ParameterInvalidException, AlreadyExistsException {
        //Parse datum
        Optional<Date> matchDate = Optional.ofNullable(parseDate(matchDTO.getDate()));

        //Team opzoeken
        Optional<Team> team1 = teamRepository.findTeamById(matchDTO.getTeamId());
        //TeamName juist ingegeven en gevonden
        if(team1.isPresent() && matchDate.isPresent()){

            //Kijk dat de datum niet in het verleden is
            if(isDateExpired(matchDate.get())){
                throw new ParameterInvalidException("Date has expired, "+matchDTO.getDate());
            }

            //Geen match gevonden waar het team in zit en op dezelfde datum afspeelt
            if(!matchRepository.findMatchByTeam1AndAndDate(team1.get(),matchDate.get()).isPresent()){
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
    @GetMapping
    public ResponseEntity<Match> getMatch(@RequestParam("matchId") Long matchId) throws NotFoundException {
        Optional<Match> match = matchRepository.findMatchById(matchId);
        if(match.isEmpty()){
            throw new NotFoundException(matchId.toString());
        }
        return ResponseEntity.status(HttpStatus.OK).body(match.get());
    }

    @Operation(
            summary = "Update match",
            description = "Update an already created match"
    )
    @PutMapping
    public ResponseEntity<Match> updateMatch(@RequestParam("matchId") Long matchId,@RequestBody MatchDTO matchDTO) throws NotFoundException, ParameterInvalidException {
        Optional<Match> match = matchRepository.findMatchById(matchId);
        if(match.isEmpty()){
            throw new NotFoundException(matchId.toString());
        }
        //check date
        Date newDate = parseDate(matchDTO.getDate());
        if(isDateExpired(newDate)){
            throw new ParameterInvalidException("Date has expired, "+matchDTO.getDate());
        }
        //check teamId
        Optional<Team> team = teamRepository.findTeamById(matchDTO.getTeamId());
        if(team.isEmpty()){
            throw new NotFoundException(matchDTO.getTeamId().toString());
        }

        match.get().setDate(newDate);
        match.get().setTeam1(team.get());
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

}


