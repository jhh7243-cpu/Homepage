package site.aiion.api.soccer.stadium.domain;

import java.util.List;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.soccer.schedule.domain.Schedule;
import site.aiion.api.soccer.team.domain.Team;

@Entity
@Table(name = "stadiums")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Stadium {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String stadium_uk;
    
    private String stadium_name;
    
    private String hometeam_uk;
    
    private String seat_count;
    
    private String address;
    
    private String ddd;
    
    private String tel;
    
    @OneToMany(mappedBy = "stadium", fetch = FetchType.LAZY)
    private List<Schedule> schedules;
    
    @OneToMany(mappedBy = "stadium", fetch = FetchType.LAZY)
    private List<Team> teams;
}

