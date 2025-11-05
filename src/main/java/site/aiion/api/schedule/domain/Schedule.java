package site.aiion.api.schedule.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.stadium.domain.Stadium;


@Entity
@Table(name = "schedules")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Schedule {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String sche_date;
    
    private Long stadium_Uk;  // 경기장 ID
    
    private String gubun;
    
    private String hometeam_Uk;  // 홈팀 ID
    
    private String awayteam_Uk;  // 원정팀 ID
    
    private Integer home_score;
    
    private Integer away_score;
    
    @ManyToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Stadium stadium;
    
}

