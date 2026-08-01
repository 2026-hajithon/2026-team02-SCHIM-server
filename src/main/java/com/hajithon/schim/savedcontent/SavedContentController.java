package com.hajithon.schim.savedcontent;

import com.hajithon.schim.common.auth.LoginUser;
import com.hajithon.schim.common.response.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}
