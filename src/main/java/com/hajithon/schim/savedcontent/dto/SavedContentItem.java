package com.hajithon.schim.savedcontent.dto;

import java.time.LocalDateTime;

public record SavedContentItem(
        SavedContentSummary content,
        long guestbookCount,
        LocalDateTime savedAt
) {
}
