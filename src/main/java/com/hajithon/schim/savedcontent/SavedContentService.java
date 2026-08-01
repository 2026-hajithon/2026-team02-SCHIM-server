package com.hajithon.schim.savedcontent;

import java.util.UUID;

public interface SavedContentService {
    SavedContentResponse save(UUID userId, Long contentId);
    void unsave(UUID userId, Long contentId);
}
