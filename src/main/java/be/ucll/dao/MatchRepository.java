package be.ucll.dao;

import be.ucll.models.Match;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MatchRepository extends JpaRepository<Match, Long> {

    //For now there aren't any extra repo functionalities we need.
}