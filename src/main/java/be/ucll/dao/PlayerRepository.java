package be.ucll.dao;

import be.ucll.models.Player;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface PlayerRepository extends JpaRepository<Player, Long> {
    Optional<Player> findPlayerByLeagueNameIgnoreCase(String name);
    Optional<Player> findPlayerById(Long id);
}
