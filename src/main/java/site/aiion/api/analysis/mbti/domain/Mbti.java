package site.aiion.api.analysis.mbti.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import site.aiion.api.diary.domain.Diary;

@Data
@Entity
@Table(name = "mbti")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mbti {
    
    @Id
    private Long mbti_id;
    
    private String m_name;
    
    private String m_char;
    
    private String e_i;
    
    private String s_n;

    private String t_f;
    
    private String j_p;
    
    private String m_result;

    @ManyToOne
    @JoinColumn(name = "diary_id")
    private Diary diary;
}

