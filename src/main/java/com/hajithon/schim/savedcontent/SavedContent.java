package com.hajithon.schim.savedcontent;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "saved_contents",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "content_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class SavedContent {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "saved_at", nullable = false)
    private LocalDateTime savedAt;

    private SavedContent(UUID userId, Long contentId) {
        this.userId = userId;
        this.contentId = contentId;
        this.savedAt = LocalDateTime.now();
    }

    public static SavedContent create(UUID userId, Long contentId) {
        return new SavedContent(userId, contentId);
    }
}
