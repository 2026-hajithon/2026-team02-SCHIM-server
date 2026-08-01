package com.hajithon.schim.guestbook;

import com.hajithon.schim.common.auth.LoginUser;
import com.hajithon.schim.common.response.ApiResponse;
import com.hajithon.schim.guestbook.dto.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class GuestbookController {
    private final GuestbookService guestbookService;

    @PostMapping(value = "/guestbooks", consumes = "multipart/form-data")
    public ResponseEntity<ApiResponse<GuestbookCreateResponse>> create(
            @LoginUser UUID userId,
            @RequestPart("image") MultipartFile image,
            @RequestPart("content") GuestbookCreateRequest request
    ) {
        Guestbook guestbook = guestbookService.create(userId, image, request);
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.of(GuestbookCreateResponse.from(guestbook)));
    }

    @PostMapping("/guestbooks/{guestbookId}/open")
    public ResponseEntity<ApiResponse<GuestbookOpenResponse>> open(
            @LoginUser UUID userId,
            @PathVariable Long guestbookId
    ) {
        GuestbookOpenResponse response = guestbookService.open(userId, guestbookId);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @GetMapping("/me/guestbooks/{guestbookId}")
    public ResponseEntity<ApiResponse<GuestbookDetailResponse>> getMyGuestbookDetail(
            @LoginUser UUID userId, @PathVariable Long guestbookId
    ) {
        GuestbookDetailResponse response = guestbookService.getMyGuestbookDetail(userId, guestbookId);

        return ResponseEntity.ok(ApiResponse.of(response));
    }

    @GetMapping("/contents/{contentId}/guestbooks")
    public ResponseEntity<ApiResponse<List<ContentGuestbookItem>>> getGuestbooksByContent(
            @LoginUser UUID userId,
            @PathVariable Long contentId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "20") int limit
) {
        ContentGuestbookPage page = guestbookService.getGuestbooksByContent(userId, contentId, cursor, limit);
        return ResponseEntity.ok(ApiResponse.of(page.items(), new ContentGuestbookMeta(page.nextCursor(), page.hasNext())));
    }

    private record ContentGuestbookMeta(String nextCursor, boolean hasNext) {
    }
}
