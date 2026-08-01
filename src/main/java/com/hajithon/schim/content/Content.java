package com.hajithon.schim.content;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(
        name = "content",
        uniqueConstraints = @UniqueConstraint(columnNames = {"provider", "external_id"}),
        indexes = @Index(name = "idx_contents_category_title", columnList = "category, title")
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Content {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Category category;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Provider provider;

    @Column(name = "external_id", nullable = false)
    private String externalId;

    @Column(nullable = false)
    private String title;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public static Content create(
            Category category,
            Provider provider,
            String externalId,
            String title,
            String description
    ) {
        Content content = new Content();
        content.category = category;
        content.provider = provider;
        content.externalId = externalId;
        content.title = title;
        content.description = description;
        content.createdAt = LocalDateTime.now();
        return content;
    }
}
