package com.hajithon.schim.content.detail;

import com.hajithon.schim.content.Content;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_performance_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PerformanceDetail {
    @Id
    @Column(name = "content_id")
    private Long contentId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private String venue; // 공연장

    @Column(name = "period")
    private String period; // 공연기간

    @Column(name = "runtime_minute")
    private Integer runtimeMinutes;

    @Column(name = "age_rating")
    private String ageRating;

    public PerformanceDetail(
            Content content,
            String venue,
            String period,
            Integer runtimeMinutes,
            String ageRating
    ) {
        this.content = content;
        this.venue = venue;
        this.period = period;
        this.runtimeMinutes = runtimeMinutes;
        this.ageRating = ageRating;
    }
}
