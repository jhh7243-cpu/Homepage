package site.aiion.api.analysis.emotion.domain;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import site.aiion.api.analysis.totalanalysis.domain.TotalAnalysis;
import site.aiion.api.diary.domain.Diary;
import site.aiion.api.user.domain.User;
import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "emotion_analysis")
public class EmotionAnalysis {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK

    // 감정 점수 (0~100 범위)
    @Column(nullable = false)
    private Integer happinessScore;

    @Column(nullable = false)
    private Integer sadnessScore;

    @Column(nullable = false)
    private Integer angerScore;

    @Column(nullable = false)
    private Integer fearScore;

    @Column(nullable = false)
    private Integer surpriseScore;

    @Column(nullable = false)
    private Integer neutralScore;

    // 감정 키워드 (예: "우울", "기쁨")
    @Column(length = 255)
    private String dominantEmotion;

    // 기록 시각
    @Column(nullable = false)
    private LocalDateTime updatedAt;


    
    // User와 연결 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Diary와 연결 (FK)
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "total_analysis_id")
    private TotalAnalysis totalAnalysis;
}

