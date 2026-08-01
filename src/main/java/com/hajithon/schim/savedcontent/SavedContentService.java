package com.hajithon.schim.savedcontent;

import com.hajithon.schim.content.Category;
import com.hajithon.schim.savedcontent.dto.SavedContentPage;

import java.util.UUID;

public interface SavedContentService {
    SavedContentResponse save(UUID userId, Long contentId);
    void unsave(UUID userId, Long contentId);
    SavedContentPage getMyShelf(UUID userId, Category category, String cursor, int limit);
}
