package site.aiion.api.soccer.team.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.aiion.api.soccer.team.domain.Team;

@Repository
public interface TeamRepository extends JpaRepository<Team, Long> {
    @Query("SELECT t FROM Team t WHERE t.team_uk = :teamUk")
    Optional<Team> findByTeam_uk(@Param("teamUk") String teamUk);
}
