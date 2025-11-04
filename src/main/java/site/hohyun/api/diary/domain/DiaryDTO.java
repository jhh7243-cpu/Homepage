package site.hohyun.api.diary.domain;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DiaryDTO 
{
    private Long id;
    private String year;
    private String month;
    private String day;
    private String weekday;
    private String title;
    private String content;

}
