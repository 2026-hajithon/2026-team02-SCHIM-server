package com.hajithon.schim.content;

import java.util.List;

public record ExternalContentPage(
        List<ExternalContent> contents,
        boolean hasNext
) {
    public static ExternalContentPage of(
            List<ExternalContent> contents,
            boolean hasNext
    ) {
        return new ExternalContentPage(
                List.copyOf(contents),
                hasNext
        );
    }

    public static ExternalContentPage empty() {
        return new ExternalContentPage(List.of(), false);
    }
}
