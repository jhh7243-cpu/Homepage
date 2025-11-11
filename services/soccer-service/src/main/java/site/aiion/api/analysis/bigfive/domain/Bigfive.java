package site.aiion.api.analysis.bigfive.domain;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import site.aiion.api.analysis.totalanalysis.domain.TotalAnalysis;
import site.aiion.api.diary.domain.Diary;
import site.aiion.api.user.domain.User;

@Entity
@Data
@Table(name = "bigfive_result")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Bigfive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;   // PK

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "diary_id")
    private Diary diary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "total_analysis_id")
    private TotalAnalysis totalAnalysis;

    @Column(name = "openness_score", nullable = false)
    private Integer opennessScore;          // 개방성

    @Column(name = "conscientiousness_score", nullable = false)
    private Integer conscientiousnessScore; // 성실성

    @Column(name = "extraversion_score", nullable = false)
    private Integer extraversionScore;      // 외향성

    @Column(name = "agreeableness_score", nullable = false)
    private Integer agreeablenessScore;     // 친화성

    @Column(name = "neuroticism_score", nullable = false)
    private Integer neuroticismScore;       // 신경성

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    @PreUpdate
    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }
}
