package site.aiion.api.team.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.player.domain.Player;
import site.aiion.api.stadium.domain.Stadium;

import java.util.List;


@Entity
@Table(name = "team")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Team {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String team_Uk;
    
    private String region_name;
    
    private String team_name;
    
    private String e_team_name;
    
    private String orig_yyyy;
    
    private String zip_code1;
    
    private String zip_code2;
    
    private String address;
    
    private String ddd;
    
    private String tel;
    
    private String fax;
    
    private String homepage;
    
    private String owner;
    
    private Long stadium_Uk;  // 경기장 ID (외래키)

    @ManyToOne
    @JoinColumn(name = "stadium_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Stadium stadium;

    @OneToMany(mappedBy = "team")
    private List<Player> players;
    
}
