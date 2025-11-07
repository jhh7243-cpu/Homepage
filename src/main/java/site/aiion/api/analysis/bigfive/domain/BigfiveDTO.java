package site.aiion.api.analysis.bigfive.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class BigfiveDTO {
    
    private Long id;
    private String five_name;
    private String f_level;
    private Integer f_point;
    private String f_action;
}

