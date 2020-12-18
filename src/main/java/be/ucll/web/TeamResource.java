package be.ucll.web;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamDTO;
import be.ucll.exceptions.AlreadyExistsException;
import be.ucll.exceptions.NotFoundException;
import be.ucll.exceptions.ParameterInvalidException;
import be.ucll.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/team")
public class TeamResource {

    private TeamRepository teamRepository;
    @Autowired
    public TeamResource(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    @PostMapping("")
    public ResponseEntity<Team> createTeam(@RequestBody TeamDTO teamDTO) throws ParameterInvalidException, AlreadyExistsException {
        if (teamDTO.getName() == null || teamDTO.getName().isEmpty()) throw new ParameterInvalidException();

        if (teamRepository.findTeamByNameIgnoreCase(teamDTO.getName()).isPresent()) throw new AlreadyExistsException(teamDTO.getName());

        Team newTeam = teamRepository.save(new Team.TeamBuilder()
                .name(teamDTO.getName())
                .build());
        return ResponseEntity.status(HttpStatus.CREATED).body(newTeam);
    }

    // team Updaten
    @PutMapping  ("/{id}")                         // localhost:8080/team?id=
        public ResponseEntity<Team> updateTeam(@PathVariable("id") Long id, @RequestBody TeamDTO teamDTO) throws ParameterInvalidException, NotFoundException, AlreadyExistsException {
        Team team;
        if (id <= 0) throw new ParameterInvalidException(id.toString());
        if (teamDTO.getName() == null || teamDTO.getName().isEmpty()) throw new ParameterInvalidException(teamDTO.getName());
        if (teamRepository.findById(id).isPresent()){
             team = teamRepository.findById(id).get();
            if (!team.getName().equals(teamDTO.getName())){
                teamALreadyExists(teamDTO.getName());
                team.setName(teamDTO.getName());
            }
        }else{
            throw new NotFoundException(teamDTO.getName());
        }

        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }
     @GetMapping("/{id}") // teamname veranderen naar id
        public ResponseEntity<TeamDTO> getTeam(@PathVariable("id") Long id)  throws NotFoundException, ParameterInvalidException {
            if (id <= 0) throw new ParameterInvalidException (id.toString());
            //controleren of team in onze db bestaat
            if(teamRepository.findById(id).isPresent()) {
                //team opvragen en teruggeven
                Team team = teamRepository.findById(id).get();
                return ResponseEntity.status(HttpStatus.OK).body(new TeamDTO(team.getName()));
            }
            throw new NotFoundException(id.toString());
        }

    @DeleteMapping ("/{id}")// id veranderd
    public ResponseEntity deleteTeam(@PathVariable("id") Long id) throws NotFoundException, ParameterInvalidException {
        if (id <= 0) throw new ParameterInvalidException(id.toString());
        //controleren of team in onze db bestaat

        if(teamRepository.findById(id).isPresent()) {
            Team t = teamRepository.findById(id).get();
            teamRepository.delete(t);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        throw new NotFoundException(id.toString());
    }

    private void teamALreadyExists(String teamName) throws AlreadyExistsException{
        if (teamRepository.findTeamByNameIgnoreCase(teamName).isPresent()){
            throw new AlreadyExistsException(teamName);
        }
    }

}

