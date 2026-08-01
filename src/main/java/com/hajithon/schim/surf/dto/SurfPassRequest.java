package com.hajithon.schim.surf.dto;

import java.util.List;

public record SurfPassRequest(
        List<Long> guestbookIds
) {
}
