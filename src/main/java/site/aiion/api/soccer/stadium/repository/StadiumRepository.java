package site.aiion.api.soccer.stadium.repository;

import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import site.aiion.api.soccer.stadium.domain.Stadium;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    @Query("SELECT s FROM Stadium s WHERE s.stadium_uk = :stadiumUk")
    Optional<Stadium> findByStadium_uk(@Param("stadiumUk") String stadiumUk);
}

