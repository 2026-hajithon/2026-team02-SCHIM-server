package com.hajithon.schim.content;

import com.hajithon.schim.content.dto.ContentSearchPage;

public interface ContentService {
    ContentSearchPage search(ContentSearchQuery query);
}
