package site.aiion.api.stadium.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "stadium", schema = "public")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StadiumEntity {
    
    @Id
    private String stadiumId;
    
    private String stadiumName;
    
    private String hometeamId;
    
    private Integer seatCount;
    
    private String address;
    
    private String ddd;
    
    private String tel;
}

