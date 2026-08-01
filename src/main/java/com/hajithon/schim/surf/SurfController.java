package com.hajithon.schim.surf;

import com.hajithon.schim.common.auth.LoginUser;
import com.hajithon.schim.common.response.ApiResponse;
import com.hajithon.schim.guestbook.SurfPassService;
import com.hajithon.schim.surf.dto.SurfPage;
import com.hajithon.schim.surf.dto.SurfPassRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SurfController {

    private final SurfService surfService;
    private final SurfPassService surfPassService;

    @GetMapping("/surf")
    public ResponseEntity<ApiResponse<?>> surf(
            @LoginUser UUID userId,
            @RequestParam(required = false) String cursor,
            @RequestParam(defaultValue = "20") int limit,
            @RequestParam(defaultValue = "false") boolean revisit
    ) {
        SurfPage page = surfService.getFeed(userId, cursor, limit, revisit);
        return ResponseEntity.ok(ApiResponse.of(page.items(), new SurfMeta(page.nextCursor(), page.hasNext())));
    }

    @PostMapping("/surf/passes")
    public ResponseEntity<Void> recordPasses(@LoginUser UUID userId, @RequestBody SurfPassRequest request) {
        surfPassService.recordPasses(userId, request.guestbookIds());

        return ResponseEntity.noContent().build();
    }

    private record SurfMeta(String nextCursor, boolean hasNext) {}
}
