package com.hajithon.schim.content.dto;

import com.hajithon.schim.content.Category;
import com.hajithon.schim.content.Provider;

public record ContentSearchResponse(
        Long contentId,
        Provider provider, // 제공하는 곳
        String externalId, // 외부 서비스에서 콘텐츠를 구분하는 고유 ID
        Category category, // 콘텐츠 종류
        String title,
        String description,
        ContentDetails details,
        long guestbookCount // 작성된 방명록 수
) {
}
