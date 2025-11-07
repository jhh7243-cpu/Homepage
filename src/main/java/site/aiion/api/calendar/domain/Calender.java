package site.aiion.api.calendar.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@Table(name = "calenders")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Calender {
    
    @Id
    private Long id;
    
    private Integer year;
    
    private Integer month;
    
    private Integer day;
    
    private String weekday;
    
    private String title;
    
    @Column(name = "content", columnDefinition = "TEXT")
    private String content;
}

