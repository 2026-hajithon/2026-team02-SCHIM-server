package com.hajithon.schim.savedcontent.dto;

import com.hajithon.schim.content.Category;

public record SavedContentSummary(
        Long contentId,
        Category category,
        String title
) {
}
