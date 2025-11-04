package site.aiion.api.diary.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DiaryDTO 
{
    private long diary_Id;
    private Integer year;
    private Integer month;
    private Integer day;
    private String weekday;
    private String title;
    private String content;
}
