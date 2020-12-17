package be.ucll.dao;

import be.ucll.models.Organisation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface OrganisationRepository extends JpaRepository<Organisation, Long> {
    Optional<Organisation> findOrganisationByNameIgnoreCase(String name);
}
