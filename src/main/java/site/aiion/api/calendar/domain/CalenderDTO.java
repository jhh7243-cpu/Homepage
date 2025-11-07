package site.aiion.api.calendar.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CalenderDTO {
    
    private Long id;
    
    private Integer year;
    
    private Integer month;
    
    private Integer day;
    
    private String weekday;
    
    private String title;
    
    private String content;
}

