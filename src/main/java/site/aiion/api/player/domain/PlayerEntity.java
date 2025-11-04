package site.aiion.api.player.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.team.domain.TeamEntity;

import java.time.LocalDate;

@Entity
@Table(name = "player", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PlayerEntity {
    
    @Id
    private String playerId;
    
    private String playerName;
    
    private String ePlayerName;
    
    private String nickname;
    
    private String joinYyyy;
    
    private String position;
    
    private Integer backNo;
    
    private String nation;
    
    private LocalDate birthDate;
    
    private String solar;
    
    private Integer height;
    
    private Integer weight;
    
    private String teamId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "team_id", insertable = false, updatable = false)
    private TeamEntity team;
}

