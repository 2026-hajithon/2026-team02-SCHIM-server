package com.hajithon.schim.guestbook.dto;

import com.hajithon.schim.content.dto.ContentSearchResponse;

import java.time.LocalDateTime;

public record GuestbookDetailResponse(
        Long guestbookId,
        String imageUrl,
        ContentSearchResponse content,
        Stats stats,
        LocalDateTime createdAt,
        LocalDateTime updatedAt
) {
    public record Stats(long passCount, long openCount){}
}
