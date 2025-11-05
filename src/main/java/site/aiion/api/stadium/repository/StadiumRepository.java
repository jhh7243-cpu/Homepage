package site.aiion.api.stadium.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.aiion.api.stadium.domain.Stadium;

@Repository
public interface StadiumRepository extends JpaRepository<Stadium, Long> {
    
}

