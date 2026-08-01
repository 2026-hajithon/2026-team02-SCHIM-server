package com.hajithon.schim.user.dto;

import com.hajithon.schim.user.User;

import java.time.LocalDateTime;
import java.util.UUID;

public record UserCreateResponse(
        UUID userId,
        String nickname,
        UUID anonymousToken,
        LocalDateTime createdAt
) {
    public static UserCreateResponse from(User user) {
        return new UserCreateResponse(
                user.getId(),
                user.getNickname(),
                user.getAnonymousToken(),
                user.getCreatedAt()
        );
    }
}
