package com.hajithon.schim.guestbook.dto;

import com.hajithon.schim.content.dto.ContentSearchResponse;

import java.time.LocalDateTime;

public record GuestbookOpenResponse(
        Long guestbookId,
        ContentSearchResponse content,
        String authorNickname,
        boolean saved,
        LocalDateTime openedAt
) {
}
