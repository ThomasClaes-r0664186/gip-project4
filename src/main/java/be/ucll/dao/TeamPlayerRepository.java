package be.ucll.dao;

import be.ucll.models.Player;
import be.ucll.models.Team;
import be.ucll.models.TeamPlayer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TeamPlayerRepository extends JpaRepository<TeamPlayer, Long> {
    List<Player> findPlayersByTeam(Team team);
    Optional<TeamPlayer> findTeamPlayerByPlayer(Player player);
    //No extra's yet
}
