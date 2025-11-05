package site.aiion.api.player.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.team.domain.Team;

import java.time.LocalDate;

@Entity
@Table(name = "players")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Player {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String player_Uk;
    
    private String player_name;
    
    private String e_player_name;
    
    private String nickname;
    
    private String join_yyyy;
    
    private String position;
    
    private Integer back_no;
    
    private String nation;
    
    private LocalDate birth_date;
    
    private String solar;
    
    private Integer height;
    
    private Integer weight;
    
    private Long team_Uk;  // 팀 ID (외래키)

    @ManyToOne
    @JoinColumn(name = "team_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Team team;
    
}

