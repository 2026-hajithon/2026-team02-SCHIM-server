package com.hajithon.schim.savedcontent;

import java.time.LocalDateTime;

public interface SavedContentRow {
    Long getContentId();
    String getCategory();
    String getTitle();
    LocalDateTime getSavedAt();
    Long getGuestbookCount();
}
