package site.aiion.api.team.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.aiion.api.team.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    
}
