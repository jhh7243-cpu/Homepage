package site.aiion.api.diary.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DiaryVO {
    private long diary_Id;
    private Integer year;
    private Integer month;
    private Integer day;
    private String weekday;
    private String title;
    private String content;
}
