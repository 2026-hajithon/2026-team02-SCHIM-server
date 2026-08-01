package com.hajithon.schim.guestbook.dto;

import com.fasterxml.jackson.databind.JsonNode;
import com.hajithon.schim.content.Category;
import com.hajithon.schim.content.Provider;
import com.hajithon.schim.content.dto.ResolveContentCommand;

public record GuestbookCreateRequest(
        Long contentId,
        Provider provider,
        String externalId,
        Category category,
        String title,
        String description,
        JsonNode details
) {
    public ResolveContentCommand toResolveCommand() {
        return new ResolveContentCommand(contentId, provider,
                externalId, category, title, description, details);
    }
}
