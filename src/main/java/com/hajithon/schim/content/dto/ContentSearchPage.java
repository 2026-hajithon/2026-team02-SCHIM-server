package com.hajithon.schim.content.dto;

import java.util.List;

public record ContentSearchPage(List<ContentSearchResponse> items, boolean hasNext) {
}
