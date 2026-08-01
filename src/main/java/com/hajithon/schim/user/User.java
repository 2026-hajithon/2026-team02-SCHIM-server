package com.hajithon.schim.user;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "member")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User {

    @Id
    @GeneratedValue
    private UUID id;

    // 닉네임은 15자까지
    @Column(nullable = false, length = 15)
    private String nickname;

    @Column(name = "anonymous_token", nullable = false, unique = true)
    private UUID anonymousToken;

    @Column(name = "last_checked_at")
    private LocalDateTime lastCheckedAt;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    // 정적 팩토리 메서드
    public static User create(String nickname) {
        return new User (nickname, UUID.randomUUID(), LocalDateTime.now());
    }

    private User(String nickname, UUID anonymousToken, LocalDateTime createdAt) {
        this.nickname = nickname;
        this.anonymousToken = anonymousToken;
        this.createdAt = createdAt;
    }

    public void changeNickname(String newNickname) {
        this.nickname = newNickname;
    }


}
