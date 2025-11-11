package site.aiion.api.diary.repository;

import java.time.LocalDate;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import site.aiion.api.calendar.domain.Calendar;
import site.aiion.api.diary.domain.Diary;

@Repository
public interface DiaryRepository extends JpaRepository<Diary, Long> {
    boolean existsByCalendar(Calendar calendar);

    boolean existsByDiaryDate(LocalDate diaryDate);

    Optional<Diary> findByDiaryDate(LocalDate diaryDate);
}
