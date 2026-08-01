package com.hajithon.schim.guestbook;

import java.time.LocalDateTime;
import java.util.UUID;

public interface ContentGuestbookRow {
    Long getGuestbookId();
    String getImageUrl();
    String getAuthorNickname();
    UUID getUserId();
    LocalDateTime getCreatedAt();
}
