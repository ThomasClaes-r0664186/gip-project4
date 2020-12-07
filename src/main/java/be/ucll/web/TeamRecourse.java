package be.ucll.web;
import be.ucll.dao.OrganisationRepository;
import be.ucll.dao.TeamRepository;
import be.ucll.dto.TeamDTO;
import be.ucll.exceptions.OrganisationNotFound;
import be.ucll.exceptions.TeamAlreadyExists;
import be.ucll.models.Organisation;
import be.ucll.models.Team;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("team")
public class TeamResource {

    private TeamRepository teamRepository;
    private OrganisationRepository organisationRepository;
    @Autowired
    public TeamResource(TeamRepository teamRepository, OrganisationRepository organisationRepository) {
        this.teamRepository = teamRepository;
        this.organisationRepository = organisationRepository;
    }

    @PostMapping("/")
    public ResponseEntity<Team> createTeam(@RequestBody TeamDTO teamDTO) throws TeamAlreadyExists, OrganisationNotFound {
        if (teamRepository.findTeamByNameIgnoreCase(teamDTO.getName()).isPresent()) throw new TeamAlreadyExists(teamDTO.getName());
        if (organisationRepository.findOrganisationByNameIgnoreCase(teamDTO.getOrganisationName()).isPresent()){
            Organisation organisation = organisationRepository.findOrganisationByNameIgnoreCase(teamDTO.getOrganisationName()).get();
            Team newTeam = teamRepository.save(new Team.TeamBuilder()
                    .id(teamDTO.getId())
                    .name(teamDTO.getName())
                    .organisation(organisation)
                    .build());
            return ResponseEntity.status(HttpStatus.CREATED).body(newTeam);

        }
        throw new OrganisationNotFound(teamDTO.getOrganisationName());
    }
}

