package site.aiion.api.analysis.emotion.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EmotionDTO {
    
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
}

