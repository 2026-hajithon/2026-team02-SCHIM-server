package com.hajithon.schim.content;

public record ContentSearchQuery(
        String keyword,
        Category category,
        int page,
        int size
) {
    public static ContentSearchQuery of(
            String keyword,
            Category category,
            int page,
            int size
    ) {
        if (keyword == null || keyword.isBlank()) {
            throw new IllegalArgumentException("검색어를 입력해주세요.");
        }

        if (page < 1) {
            throw new IllegalArgumentException("페이지는 1 이상이어야 합니다.");
        }

        if (size < 1 || size > 50) {
            throw new IllegalArgumentException("size는 1 ~ 50 이어야 합니다.");
        }

        return new ContentSearchQuery(
                keyword.trim(),
                category,
                page,
                size
        );
    }
}
