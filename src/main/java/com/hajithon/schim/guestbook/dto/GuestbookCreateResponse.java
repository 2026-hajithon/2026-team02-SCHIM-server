package com.hajithon.schim.guestbook.dto;

import com.hajithon.schim.guestbook.Guestbook;

import java.time.LocalDateTime;

public record GuestbookCreateResponse(
        Long guestbookId,
        Long contentId,
        String imageUrl,
        LocalDateTime createdAt
) {
    public static GuestbookCreateResponse from(Guestbook guestbook) {
        return new GuestbookCreateResponse(
                guestbook.getId(), guestbook.getContentId(), guestbook.getImageUrl(), guestbook.getCreatedAt()
        );
    }
}
