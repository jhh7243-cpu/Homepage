package site.aiion.api.analysis.totalanalysis.domain;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import site.aiion.api.user.domain.User;
import java.time.LocalDateTime;
import site.aiion.api.analysis.bigfive.domain.Bigfive;
import site.aiion.api.analysis.mbti.domain.Mbti;
import site.aiion.api.analysis.emotion.domain.EmotionAnalysis;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "total_analysis")
public class TotalAnalysis 
{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK

    // User와 연결 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // 분석 실행 시각
    @Column(nullable = false)
    private LocalDateTime createdAt;

    // 연관된 세부 분석 결과들
    @OneToOne(mappedBy = "totalAnalysis", cascade = CascadeType.ALL)
    private Bigfive bigFiveTotal;

    @OneToOne(mappedBy = "totalAnalysis", cascade = CascadeType.ALL)
    private Mbti mbtiTotal;

    @OneToOne(mappedBy = "totalAnalysis", cascade = CascadeType.ALL)
    private EmotionAnalysis emotionTotal;

    
}
