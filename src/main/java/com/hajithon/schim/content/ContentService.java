package com.hajithon.schim.content;

import com.hajithon.schim.content.dto.ContentSearchPage;
import com.hajithon.schim.content.dto.ContentSearchResponse;
import com.hajithon.schim.content.dto.ResolveContentCommand;

public interface ContentService {
    ContentSearchPage search(ContentSearchQuery query);
    Content resolve(ResolveContentCommand command);
    ContentSearchResponse getDetail(Long contentId);
}
