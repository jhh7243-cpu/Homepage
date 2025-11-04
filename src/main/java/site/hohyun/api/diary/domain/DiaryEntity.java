package site.hohyun.api.diary.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.NoArgsConstructor;
import lombok.Data;

@Data
@Entity
@Table(name = "diaries")
@NoArgsConstructor

public class DiaryEntity 
{
    
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id private Long diaryId;
    
    private Integer year;
    
    private Integer month;
    
    private Integer day;
    
    private String weekday;
    
    private String title;
    
    private String content;
}
