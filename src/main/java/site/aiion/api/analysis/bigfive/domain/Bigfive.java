package site.aiion.api.analysis.bigfive.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import site.aiion.api.diary.domain.Diary;
@Data
@Entity
@Table(name = "bigfive")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bigfive {
    
    @Id
    private Long id;
    
    private String five_name;
    
    private String f_level;
    
    private Integer f_point;
    
    private String f_action;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
    
}

