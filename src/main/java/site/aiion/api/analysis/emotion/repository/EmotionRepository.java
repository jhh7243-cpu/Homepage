package site.aiion.api.analysis.emotion.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.aiion.api.analysis.emotion.domain.Emotion;

@Repository
public interface EmotionRepository extends JpaRepository<Emotion, Long> {
    
}

