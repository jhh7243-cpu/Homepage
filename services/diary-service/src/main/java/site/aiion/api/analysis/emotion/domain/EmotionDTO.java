package site.aiion.api.analysis.emotion.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private Float sentimentScore;
    private String dominantEmotion;
}

