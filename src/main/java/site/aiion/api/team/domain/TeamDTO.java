package site.aiion.api.team.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TeamDTO {
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
    private Long stadium_Uk;
}
