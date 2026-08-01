package com.hajithon.schim.guestbook;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "guestbooks",
        indexes = {
                @Index(name = "idx_guestbooks_created_at_id", columnList = "created_at DESC, id DESC"),
                @Index(name = "idx_guestbooks_user_created_at", columnList = "user_id, created_at DESC"),
                @Index(name = "idx_guestbooks_content_id", columnList = "content_id")
        }
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Guestbook {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "image_url", nullable = false, columnDefinition = "TEXT")
    private String imageUrl;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    private Guestbook(UUID userId, Long contentId, String imageUrl) {
        this.userId = userId;
        this.contentId = contentId;
        this.imageUrl = imageUrl;
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    public static Guestbook create(UUID userId, Long contentId, String imageUrl) {
        return new Guestbook(userId, contentId, imageUrl);
    }

    public void changeImage(String imageUrl) {
        this.imageUrl = imageUrl;
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }
}