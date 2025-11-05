package site.aiion.api.stadium.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.schedule.domain.Schedule;
import site.aiion.api.team.domain.Team;

import java.util.List;


@Entity
@Table(name = "stadiums")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Stadium {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String stadium_Uk;  
    
    private String stadium_name;
    
    private String hometeam_Uk;
    
    private Integer seat_count;
    
    private String address;
    
    private String ddd;
    
    private String tel;

    @OneToMany(mappedBy = "stadium")
    private List<Schedule> schedules;

    @OneToMany(mappedBy = "stadium")
    private List<Team> teams;
}

