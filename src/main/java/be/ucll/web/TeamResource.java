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
    public ResponseEntity<Team> createTeam(@RequestBody TeamDTO teamDTO) throws TeamAlreadyExists, OrganisationNotFound {
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
    @PutMapping
    public TeamDTO updateTeam(@RequestParam("teamName") String teamName, @RequestBody TeamDTO teamDTO) throws HttpClientErrorException, TeamNotFound, TeamAlreadyExists, OrganisationNotFound {
        //We gaan controleren of het team waarvan de teamName gegeven is, of deze wel bestaat indien deze niet bestaat,
        // laten we zien dat de teamName niet gevonden is
        if (teamRepository.findTeamByNameIgnoreCase(teamName).isPresent()){

            //Wanneer dit team bestaat, gaan we die in een Team object steken.
            Team team =  teamRepository.findTeamByNameIgnoreCase(teamName).get();

            //Dan kijken we of ze de team naam van de speler willen veranderen
            if (!team.getName().equals(teamDTO.getName())){

                //we gaan controleren of de nieuwe team naam al in onze databank zit, indien dit het geval is, komt er
                //een exception teamAlreadyExists
                teamALreadyExists(teamDTO.getName());
            }
            //controleren of de gebruiker een andere organisatie naam wilt
            //TODO wanneer we de naam van de organisatie veranderen, moet deze org. dan al bestaan in de database?
            // gelieve deze methode eens na te kijken. Wij zijn niet zelfzeker van onze code!
            if (organisationRepository.findOrganisationByNameIgnoreCase(teamDTO.getOrganisationName()).isPresent()){
                try{
                    Organisation organisation = organisationRepository.findOrganisationByNameIgnoreCase(teamDTO.getOrganisationName()).get();
                    organisation.setName(teamDTO.getOrganisationName());
                }
                catch (Exception e){
                    e.getMessage();
                }

            }

            //spele opslaan in db en de nieuwe speler tonen aan gebruiker.

            teamRepository.save(team);
            return new TeamDTO(team.getName(), team.getOrganisation().getName());
        }

        //Exception om te tonen dat de speler waarvan je iets wilt aanpassen niet bestaat.
        throw new TeamNotFound(teamName);
    }

    @GetMapping
    public ResponseEntity<TeamDTO> getTeam(@RequestParam("teamName") String teamName) throws TeamNotFound {
        //controleren of team in onze db bestaat
        if(teamRepository.findTeamByNameIgnoreCase(teamName).isPresent()) {
            //team opvragen en teruggeven
            Team team = teamRepository.findTeamByNameIgnoreCase(teamName).get();
            return ResponseEntity.status(HttpStatus.OK).body(new TeamDTO(team.getName(), team.getOrganisation().getName()));
        }
        throw new TeamNotFound(teamName);
    }

    @DeleteMapping
    public ResponseEntity deleteTeam(@RequestParam("teamName") String teamName) throws TeamNotFound {
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

