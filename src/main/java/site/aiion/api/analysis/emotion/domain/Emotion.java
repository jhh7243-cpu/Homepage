package site.aiion.api.analysis.emotion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

import site.aiion.api.diary.domain.Diary;

@Data
@Entity
@Table(name = "emotion")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Emotion {
    
    @Id
    private Long id;
    
    private Integer joy;
    
    private Integer sadness;
    
    private Integer anger;
    
    private Integer fear;
    
    private Integer disgust;
    
    private Integer surprise;
    
    private Float sentiment_score;
    
    private String dominant_emotion;
    
    private LocalDateTime analyzed_at;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
}

