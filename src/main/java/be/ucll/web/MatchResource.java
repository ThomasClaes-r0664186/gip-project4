package be.ucll.web;

import be.ucll.dao.MatchRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.MatchDTO;
import be.ucll.exceptions.MatchDateNotCorrect;
import be.ucll.exceptions.MatchTeamAlreadyAssigned;
import be.ucll.exceptions.TeamNotFound;
import be.ucll.exceptions.UsernameAlreadyExists;
import be.ucll.models.Match;
import be.ucll.models.Team;
import io.swagger.annotations.ApiOperation;
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

    @ApiOperation("Eeen match creëren")
    @PostMapping
    public ResponseEntity<Match> createMatch(@RequestBody MatchDTO matchDTO) throws MatchDateNotCorrect, MatchTeamAlreadyAssigned, TeamNotFound {
        //Parse datum
        Optional<Date> matchDate = null;
        try {
            matchDate = Optional.ofNullable(new SimpleDateFormat("dd/MM/yyyy").parse(matchDTO.getDate()));
        }catch(ParseException e){
            throw new MatchDateNotCorrect(e.getErrorOffset());
        }

        //Teams opzoeken
        Optional<Team> team1 = teamRepository.findTeamByNameIgnoreCase(matchDTO.getNameTeam1());
        Optional<Team> team2 = teamRepository.findTeamByNameIgnoreCase(matchDTO.getNameTeam2());
        //TeamName juist ingegeven en gevonden
        if(team1.isPresent() && team2.isPresent() && matchDate.isPresent()){
            //Geen match gevonden waar beide teams in zitten en op dezelfde datum afspeelt
            if(     !matchRepository.findMatchByTeam1AndTeam2(team1.get(),team2.get()).isPresent()
                ||  !matchRepository.findMatchByTeam1AndTeam2(team2.get(),team1.get()).isPresent()
                ||  !matchRepository.findMatchByDate(matchDate.get()).isPresent()
            ){
                Match newMatch = matchRepository.save(
                    new Match.MatchBuilder()
                        .team1Id(team1.get())
                        .team2Id(team2.get())
                        .date(matchDate.get())
                        .build()
                );
                return ResponseEntity.status(HttpStatus.CREATED).body(newMatch);
            }else{
                throw new MatchTeamAlreadyAssigned(matchDTO.getDate());
            }
        }else{
            if(team1.isEmpty()){
                throw new TeamNotFound(matchDTO.getNameTeam1());
            }else if(team2.isEmpty()){
                throw new TeamNotFound(matchDTO.getNameTeam2());
            }else{
                throw new RuntimeException("This exception shouldn't have happend");
            }
        }
    }

}


