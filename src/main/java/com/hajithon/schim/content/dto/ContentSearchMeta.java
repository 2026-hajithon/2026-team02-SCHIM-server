package com.hajithon.schim.content.dto;

public record ContentSearchMeta(
        int page,
        int size,
        boolean hasNext
) {
}
