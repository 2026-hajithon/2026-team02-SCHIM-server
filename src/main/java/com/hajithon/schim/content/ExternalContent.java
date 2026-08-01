package com.hajithon.schim.content;

import com.hajithon.schim.content.dto.ContentDetails;

public record ExternalContent(
        Provider provider,
        String externalId,
        Category category,
        String title,
        String description,
        ContentDetails details
) {
    public static ExternalContent of(
            Provider provider,
            String externalId,
            Category category,
            String title,
            String description,
            ContentDetails details
    ) {
        return new ExternalContent(
                provider,
                externalId,
                category,
                title,
                description,
                details
        );
    }
}
