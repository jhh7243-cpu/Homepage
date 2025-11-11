package site.aiion.api.analysis.bigfive.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class BigfiveDTO {

    private final Long id;
    private final Long userId;
    private final Long diaryId;
    private final Integer opennessScore;
    private final Integer conscientiousnessScore;
    private final Integer extraversionScore;
    private final Integer agreeablenessScore;
    private final Integer neuroticismScore;
    private final LocalDateTime updatedAt;

    public static BigfiveDTO of(Bigfive entity) {
        return BigfiveDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .diaryId(entity.getDiary() != null ? entity.getDiary().getId() : null)
                .opennessScore(entity.getOpennessScore())
                .conscientiousnessScore(entity.getConscientiousnessScore())
                .extraversionScore(entity.getExtraversionScore())
                .agreeablenessScore(entity.getAgreeablenessScore())
                .neuroticismScore(entity.getNeuroticismScore())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
