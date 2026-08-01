package com.hajithon.schim.content.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.hajithon.schim.content.Category;
import com.hajithon.schim.content.Provider;

public record ResolveContentCommand(
        Long contentId,
        Provider provider,
        String externalId,
        Category category,
        String title,
        String description,
        JsonNode details
) {
}
