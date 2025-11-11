package site.aiion.api.analysis.mbti.domain;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@AllArgsConstructor
public class MbtiDTO {

    private final Long id;
    private final Long userId;
    private final Long diaryId;
    private final String mbtiType;
    private final Integer eiScore;
    private final Integer snScore;
    private final Integer tfScore;
    private final Integer jpScore;
    private final LocalDateTime updatedAt;

    public static MbtiDTO of(Mbti entity) {
        return MbtiDTO.builder()
                .id(entity.getId())
                .userId(entity.getUser() != null ? entity.getUser().getId() : null)
                .diaryId(entity.getDiary() != null ? entity.getDiary().getId() : null)
                .mbtiType(entity.getMbtiType())
                .eiScore(entity.getEiScore())
                .snScore(entity.getSnScore())
                .tfScore(entity.getTfScore())
                .jpScore(entity.getJpScore())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
