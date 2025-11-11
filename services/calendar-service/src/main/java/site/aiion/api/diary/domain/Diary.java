package site.aiion.api.diary.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import site.aiion.api.analysis.bigfive.domain.Bigfive;
import site.aiion.api.analysis.mbti.domain.Mbti;
import site.aiion.api.analysis.emotion.domain.EmotionAnalysis;
import java.util.List;
import site.aiion.api.calendar.domain.Calendar;
import java.time.LocalDate;
@Data
@Entity
@Table(name = "diaries")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK

    @Column(nullable = false)
    private LocalDate diaryDate; // 날짜 (year, month, day, weekday 모두 포함)

    private String title;

    @Column(columnDefinition = "TEXT")
    private String content;


    
    @ManyToOne
    @JoinColumn(name = "calendar_id")
    private Calendar calendar;

    @OneToMany(mappedBy = "diary")
    private List<Bigfive> bigfive;

    @OneToMany(mappedBy = "diary")
    private List<Mbti> mbti;

    @OneToMany(mappedBy = "diary")
    private List<EmotionAnalysis> emotion;

    public void changeCalendar(Calendar calendar) {
        if (this.calendar == calendar) {
            return;
        }
        Calendar previous = this.calendar;
        this.calendar = calendar;
        if (previous != null) {
            previous.getDiaries().remove(this);
        }
        if (calendar != null && !calendar.getDiaries().contains(this)) {
            calendar.getDiaries().add(this);
        }
    }
}
