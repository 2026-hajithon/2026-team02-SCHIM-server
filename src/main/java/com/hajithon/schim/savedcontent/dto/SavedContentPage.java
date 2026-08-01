package com.hajithon.schim.savedcontent.dto;

import java.util.List;

public record SavedContentPage(
        List<SavedContentItem> items,
        boolean hasNext,
        String nextCursor
) {
}
