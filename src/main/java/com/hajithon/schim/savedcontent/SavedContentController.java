package com.hajithon.schim.savedcontent;

import com.hajithon.schim.common.auth.LoginUser;
import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;
import com.hajithon.schim.common.response.ApiResponse;
import com.hajithon.schim.content.Category;
import com.hajithon.schim.savedcontent.dto.SavedContentItem;
import com.hajithon.schim.savedcontent.dto.SavedContentPage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SavedContentController {

    private final SavedContentService savedContentService;

    @PostMapping("/contents/{contentId}/save")
    public ResponseEntity<ApiResponse<SavedContentResponse>> save(
            @LoginUser UUID userId,
            @PathVariable Long contentId
    ) {
        SavedContentResponse response = savedContentService.save(userId, contentId);
        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @DeleteMapping("/contents/{contentId}/save")
    public ResponseEntity<Void> unsave(
            @LoginUser UUID userId,
            @PathVariable Long contentId
    ) {
        savedContentService.unsave(userId, contentId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me/saved-contents")
    public ResponseEntity<ApiResponse<List<SavedContentItem>>> getMyShelf(
            @LoginUser UUID userId,
            @RequestParam(required = false) String category,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "20") int limit
    ) {
        Category parseCategory = category != null ? parseCategory(category) : null;
        SavedContentPage page = savedContentService.getMyShelf(userId, parseCategory, cursor, limit);

        return ResponseEntity.ok(ApiResponse.of(page.items(), new ShelfMeta(page.nextCursor(), page.hasNext())));
    }

    private Category parseCategory(String category) {
        try {
            return Category.valueOf(category);
        } catch (IllegalArgumentException e) {
            throw new BusinessException(ErrorCode.INVALID_CATEGORY);
        }
    }

    private record ShelfMeta(String nextCursor, boolean hasNext) {}
}
