package site.aiion.api.team.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.stadium.domain.StadiumEntity;

@Entity
@Table(name = "team", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TeamEntity {
    
    @Id
    private String teamId;
    
    private String regionName;
    
    private String teamName;
    
    private String eTeamName;
    
    private String origYyyy;
    
    private String zipCode1;
    
    private String zipCode2;
    
    private String address;
    
    private String ddd;
    
    private String tel;
    
    private String fax;
    
    private String homepage;
    
    private String owner;
    
    private String stadiumId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "stadium_id", insertable = false, updatable = false)
    private StadiumEntity stadium;
}
