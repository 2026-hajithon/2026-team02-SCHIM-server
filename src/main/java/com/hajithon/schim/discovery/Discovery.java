package com.hajithon.schim.discovery;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name = "discoveries",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "guestbook_id"})
)
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Discovery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "guestbook_id", nullable = false)
    private Long guestbookId;

    @Column(name = "content_id", nullable = false)
    private Long contentId;

    @Column(name = "opened_at", nullable = false)
    private LocalDateTime openedAt;

    private Discovery(UUID userId, Long guestbookId, Long contentId) {
        this.userId = userId;
        this.guestbookId = guestbookId;
        this.contentId = contentId;
        this.openedAt = LocalDateTime.now();
    }

    public static Discovery create(UUID userId, Long guestbookId, Long contentId) {
        return new Discovery(userId, guestbookId, contentId);
    }
}
