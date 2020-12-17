package be.ucll.dao;

import be.ucll.models.Match;
import be.ucll.models.Team;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.Optional;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {
    Optional<Match> findMatchByTeam1AndTeam2(Team team1, Team team2);
    Optional<Match> findMatchByDate(Date date);
}