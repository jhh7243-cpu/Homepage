package site.aiion.api.analysis.mbti.domain;

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
@Table(name = "mbti_result")
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Mbti {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "mbti_type", nullable = false, length = 4)
    private String mbtiType;

    @Column(name = "ei_score", nullable = false)
    private Integer eiScore;

    @Column(name = "sn_score", nullable = false)
    private Integer snScore;

    @Column(name = "tf_score", nullable = false)
    private Integer tfScore;

    @Column(name = "jp_score", nullable = false)
    private Integer jpScore;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "diary_id", nullable = false)
    private Diary diary;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "total_analysis_id")
    private TotalAnalysis totalAnalysis;

    @PrePersist
    @PreUpdate
    private void touch() {
        this.updatedAt = LocalDateTime.now();
    }
}

