package com.hajithon.schim.content;

import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;
import com.hajithon.schim.common.response.ApiResponse;
import com.hajithon.schim.content.dto.ContentSearchMeta;
import com.hajithon.schim.content.dto.ContentSearchPage;
import com.hajithon.schim.content.dto.ContentSearchResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class ContentController {

    private final ContentService contentService;

    @GetMapping("/api/contents/search")
    public ResponseEntity<ApiResponse<List<ContentSearchResponse>>> search(
            @RequestParam String keyword,
            @RequestParam String category,
            @RequestParam(defaultValue = "1") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        Category parsedCategory = parseCategory(category);
        ContentSearchQuery query = ContentSearchQuery.of(keyword, parsedCategory, page, size);
        ContentSearchPage result = contentService.search(query);
        return ResponseEntity.ok(ApiResponse.of(result.items(), new ContentSearchMeta(page, size, result.hasNext())));
    }

    private Category parseCategory(String category) {
        try {
            return Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_CATEGORY);
        }
    }
}
