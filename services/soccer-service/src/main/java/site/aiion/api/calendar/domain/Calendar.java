package site.aiion.api.calendar.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Builder.Default;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.diary.domain.Diary;
import site.aiion.api.user.domain.User;

@Data
@Entity
@Table(name = "calendars")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calendar {

    @Id
    private Long Id;   // PK

    @Column(nullable = false, length = 100)
    private String name;       // 캘린더 이름

    @Column(columnDefinition = "TEXT")
    private String description; // 설명

    private String color;       // UI 색상
    private String timezone;    // 타임존

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @Default
    @OneToMany(mappedBy = "calendar")
    private List<Diary> diaries = new ArrayList<>();

    public void addDiary(Diary diary) {
        if (diary == null || diaries.contains(diary)) {
            return;
        }
        diaries.add(diary);
        diary.changeCalendar(this);
    }

    public void removeDiary(Diary diary) {
        if (diary == null || !diaries.remove(diary)) {
            return;
        }
        diary.changeCalendar(null);
    }
}
