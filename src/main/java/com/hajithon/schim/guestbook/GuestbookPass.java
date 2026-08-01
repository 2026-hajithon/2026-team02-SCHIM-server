package com.hajithon.schim.guestbook;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "guestbook_passes",
uniqueConstraints = @UniqueConstraint(columnNames = {"guestbook_id", "user_id"}))
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class GuestbookPass {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "guestbook_id", nullable = false)
    private Long guestbookId;

    @Column(name = "user_id", unique = false)
    private UUID userId;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    private GuestbookPass(Long guestbookId, UUID userId) {
        this.guestbookId = guestbookId;
        this.userId = userId;
        this.createdAt = LocalDateTime.now();
    }

    // 정적 팩토리 메서드
    public static GuestbookPass create(Long guestbookId, UUID userId) {
        return new GuestbookPass(guestbookId, userId);
    }
}
