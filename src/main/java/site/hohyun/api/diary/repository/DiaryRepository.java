package site.hohyun.api.diary.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import site.hohyun.api.diary.domain.DiaryEntity;

/**
 * 일기 레포지토리
 * PostgreSQL JPA Repository
 */
@Repository
public interface DiaryRepository extends JpaRepository<DiaryEntity, Long>
{
    // JPA가 자동으로 findAll(), findById(), save(), deleteById() 메서드 제공
}
