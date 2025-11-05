package site.aiion.api.diary.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;


@Data
@Entity
@Table(name = "diaries", schema = "public")
@Builder
public class Diary 
{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Integer year;
    private Integer month;
    private Integer day;
    private String weekday;
    private String title;
    @Column(name = "content", length = 5000)
    private String content;
}
