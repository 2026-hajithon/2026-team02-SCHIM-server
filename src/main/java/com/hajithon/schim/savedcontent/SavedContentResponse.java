package com.hajithon.schim.savedcontent;

import java.time.LocalDateTime;

public record SavedContentResponse(
        Long contentId,
        boolean saved,
        LocalDateTime savedAt
) {
}
