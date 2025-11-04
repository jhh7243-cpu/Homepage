package site.hohyun.api.diary.domain;

import lombok.Getter;
import lombok.AllArgsConstructor;

@Getter
@AllArgsConstructor
public class DiaryVO 
{
    private Long id;
    private String year;
    private String month;
    private String day;
    private String Weekday;
    private String title;
    private String content;
}
