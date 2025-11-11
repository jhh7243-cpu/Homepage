package site.aiion.api.analysis.bigfive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.aiion.api.analysis.bigfive.domain.Bigfive;

@Repository
public interface BigfiveRepository extends JpaRepository<Bigfive, Long> {
    
}

