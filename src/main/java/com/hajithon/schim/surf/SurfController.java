package com.hajithon.schim.surf;

import com.hajithon.schim.common.auth.LoginUser;
import com.hajithon.schim.common.response.ApiResponse;
import com.hajithon.schim.surf.dto.SurfPage;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api")
public class SurfController {

    private final SurfService surfService;

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

    private record SurfMeta(String nextCursor, boolean hasNext) {}
}
