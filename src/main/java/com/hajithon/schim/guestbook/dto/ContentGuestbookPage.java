package com.hajithon.schim.guestbook.dto;

import java.util.List;

public record ContentGuestbookPage(
        List<ContentGuestbookItem> items,
        boolean hasNext,
        String nextCursor
) {
}
