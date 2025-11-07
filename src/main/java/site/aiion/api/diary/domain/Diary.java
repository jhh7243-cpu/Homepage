package site.aiion.api.diary.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import site.aiion.api.analysis.bigfive.domain.Bigfive;
import site.aiion.api.analysis.mbti.domain.Mbti;
import site.aiion.api.analysis.emotion.domain.Emotion;
import java.util.List;
@Data
@Entity
@Table(name = "diaries")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Diary 
{
    @Id
    private Long id;
    private Integer year;
    private Integer month;
    private Integer day;
    private String weekday;
    private String title;
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;



    @OneToMany(mappedBy = "diary")
    private List<Bigfive> bigfive;

    @OneToMany(mappedBy = "diary")
    private List<Mbti> mbti;

    @OneToMany(mappedBy = "diary")
    private List<Emotion> emotion;
}
