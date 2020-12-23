package be.ucll.dao;

import be.ucll.models.Match;
import be.ucll.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findMatchByTeam1AndAndDate(Team team1,Date date);
    Optional<Match> findMatchById(Long id);

    //Optional<Match> findMatchBymatchID(Long id);
    Optional<Match> findMatchByMatchID(Long id);
}