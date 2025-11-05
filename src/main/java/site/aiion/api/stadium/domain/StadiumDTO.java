package site.aiion.api.stadium.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StadiumDTO {
    private Long id;
    private String stadium_Uk;
    private String stadium_name;
    private String hometeam_Uk;
    private Integer seat_count;
    private String address;
    private String ddd;
    private String tel;
}
