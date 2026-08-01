package com.hajithon.schim.surf;

import java.time.LocalDateTime;

public interface SurfRow {
    Long getGuestbookId();
    String getImageUrl();
    String getAuthorNickname();
    LocalDateTime getCreatedAt();
}
