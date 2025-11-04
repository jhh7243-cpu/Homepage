package site.aiion.api.schedule.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.stadium.domain.StadiumEntity;
import site.aiion.api.team.domain.TeamEntity;

@Entity
@Table(name = "schedule", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@IdClass(ScheduleDTO.class)
public class ScheduleEntity {
    
    @Id
    private String scheDate;
    
    @Id
    private String stadiumId;
    
    private String gubun;
    
    private String hometeamId;
    
    private String awayteamId;
    
    private Integer homeScore;
    
    private Integer awayScore;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", insertable = false, updatable = false)
    private StadiumEntity stadium;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "hometeam_id", insertable = false, updatable = false)
    private TeamEntity homeTeam;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "awayteam_id", insertable = false, updatable = false)
    private TeamEntity awayTeam;
}

