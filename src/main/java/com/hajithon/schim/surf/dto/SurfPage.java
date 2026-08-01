package com.hajithon.schim.surf.dto;

import java.util.List;

public record SurfPage(List<SurfCardResponse> items, boolean hasNext, String nextCursor) {
}
