package com.hajithon.schim.user.dto;

import com.hajithon.schim.user.User;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

public record UserInfoResponse(
        UUID userId,
        String nickname,
        UUID anonymousToken,
        LocalDateTime createdAt
) {
    public static UserInfoResponse from(User user) {
        return new UserInfoResponse(
                user.getId(),
                user.getNickname(),
                user.getAnonymousToken(),
                user.getCreatedAt()
        );
    }
}
