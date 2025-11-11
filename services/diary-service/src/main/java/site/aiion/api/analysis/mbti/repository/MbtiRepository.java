package site.aiion.api.analysis.mbti.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.aiion.api.analysis.mbti.domain.Mbti;

@Repository
public interface MbtiRepository extends JpaRepository<Mbti, Long> {
    
}

