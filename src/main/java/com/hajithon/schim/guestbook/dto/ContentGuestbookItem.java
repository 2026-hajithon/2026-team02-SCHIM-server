package com.hajithon.schim.guestbook.dto;

import java.time.LocalDateTime;

public record ContentGuestbookItem(
        Long guestbookId,
        String imageUrl,
        String authorNickname,
        boolean isMine,
        LocalDateTime createdAt
) {
}
