package site.aiion.api.schedule.domain;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ScheduleDTO implements Serializable {
    private Long id;
    private String sche_date;
    private Long stadium_Uk;
    private String gubun;
    private String hometeam_Uk;
    private String awayteam_Uk;
    private Integer home_score;
    private Integer away_score;
}

