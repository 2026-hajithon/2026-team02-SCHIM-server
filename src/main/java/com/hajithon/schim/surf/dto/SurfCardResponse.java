package com.hajithon.schim.surf.dto;

import java.time.LocalDateTime;

public record SurfCardResponse(
        Long guestbookId,
        String imageUrl,
        String authorNickname,
        LocalDateTime createdAt
) {
}
