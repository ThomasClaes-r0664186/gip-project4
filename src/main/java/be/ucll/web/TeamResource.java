package be.ucll.web;
import be.ucll.dao.OrganisationRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.PlayerDTO;
import be.ucll.dto.TeamDTO;
import be.ucll.exceptions.*;
import be.ucll.models.Organisation;
import be.ucll.models.Player;
import be.ucll.models.Team;
import be.ucll.service.models.Summoner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpClientErrorException;

@RestController
@RequestMapping("/team")
public class TeamResource {

    private TeamRepository teamRepository;
    private OrganisationRepository organisationRepository;
    @Autowired
    public TeamResource(TeamRepository teamRepository, OrganisationRepository organisationRepository) {
        this.teamRepository = teamRepository;
        this.organisationRepository = organisationRepository;
    }

    @PostMapping("")
    public ResponseEntity<Team> createTeam(@RequestBody TeamDTO teamDTO) throws TeamAlreadyExists, OrganisationNotFound, TeamNameIsNullOrEmpty, TeamOrganisationIsNullOrEmpty {
        if (teamDTO.getName() == null || teamDTO.getName().isEmpty()) throw new TeamNameIsNullOrEmpty();
        if (teamDTO.getOrganisationName() == null || teamDTO.getOrganisationName().isEmpty()) throw new TeamOrganisationIsNullOrEmpty();
        if (teamRepository.findTeamByNameIgnoreCase(teamDTO.getName()).isPresent()) throw new TeamAlreadyExists(teamDTO.getName());
        if (organisationRepository.findOrganisationByNameIgnoreCase(teamDTO.getOrganisationName()).isPresent()){
            Organisation organisation = organisationRepository.findOrganisationByNameIgnoreCase(teamDTO.getOrganisationName()).get();
            Team newTeam = teamRepository.save(new Team.TeamBuilder()
                    .name(teamDTO.getName())
                    .organisation(organisation)
                    .build());
            return ResponseEntity.status(HttpStatus.CREATED).body(newTeam);

        }
        throw new OrganisationNotFound(teamDTO.getOrganisationName());
    }

    // team Updaten
    @PutMapping  ("/{id}")                         // localhost:8080/team?id=
    public ResponseEntity<Team> updateTeam(@PathVariable("id") Long id, @RequestBody TeamDTO teamDTO) throws TeamNotFound, TeamNameIsNullOrEmpty, OrganisationNotFound, TeamAlreadyExists {
        Team team;
        if (id <= 0) throw new TeamNotFound();
        if (teamDTO.getName() == null || teamDTO.getName().isEmpty()) throw new TeamNameIsNullOrEmpty();
        if (teamDTO.getOrganisationName() == null || teamDTO.getOrganisationName().isEmpty()) throw new OrganisationNotFound();
        if (teamRepository.findById(id).isPresent()){
             team = teamRepository.findById(id).get();
            if (!team.getName().equals(teamDTO.getName())){
                teamALreadyExists(teamDTO.getName());
                team.setName(teamDTO.getName());
            }
        }else{
            throw new TeamNotFound(teamDTO.getName());
        }

        if (!team.getOrganisation().getName().equals(teamDTO.getOrganisationName())){

            if (organisationRepository.findOrganisationByNameIgnoreCase(teamDTO.getOrganisationName()).isPresent()){
                Organisation organisation = organisationRepository.findOrganisationByNameIgnoreCase(teamDTO.getOrganisationName()).get();
                team.setOrganisation(organisation);
            }else{
                throw new OrganisationNotFound(teamDTO.getOrganisationName());
            }
        }
        teamRepository.save(team);
        return ResponseEntity.status(HttpStatus.OK).body(team);
    }

    @GetMapping // teamname veranderen naar id
    public ResponseEntity<TeamDTO> getTeam(@RequestParam("teamName") String teamName) throws TeamNotFound {
        //controleren of team in onze db bestaat
        if(teamRepository.findTeamByNameIgnoreCase(teamName).isPresent()) {
            //team opvragen en teruggeven
            Team team = teamRepository.findTeamByNameIgnoreCase(teamName).get();
            return ResponseEntity.status(HttpStatus.OK).body(new TeamDTO(team.getName(), team.getOrganisation().getName()));
        }
        throw new TeamNotFound(teamName);
    }

    @DeleteMapping // id veranderd
    public ResponseEntity deleteTeam(@PathVariable("id") String teamName) throws TeamNotFound {
        //We gaan controleren of het team waarvan de teamName gegeven is, wel bestaat in onze db
        if(teamRepository.findTeamByNameIgnoreCase(teamName).isPresent()) {

            //Zo ja, dan verwijderen we deze
            teamRepository.delete(teamRepository.findTeamByNameIgnoreCase(teamName).get());
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }

        //zo niet => exception
        throw new TeamNotFound(teamName);
    }

    private boolean teamALreadyExists(String teamName) throws TeamAlreadyExists{
        if (teamRepository.findTeamByNameIgnoreCase(teamName).isPresent()){
            throw new TeamAlreadyExists(teamName);
        }else{
            return false;
        }
    }

}

