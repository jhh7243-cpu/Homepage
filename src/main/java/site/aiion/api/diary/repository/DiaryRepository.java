package site.aiion.api.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.aiion.api.diary.domain.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    
}
