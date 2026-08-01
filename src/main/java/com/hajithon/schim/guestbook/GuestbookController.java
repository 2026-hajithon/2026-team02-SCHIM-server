package com.hajithon.schim.guestbook;

import com.hajithon.schim.common.auth.LoginUser;
import com.hajithon.schim.common.response.ApiResponse;
import com.hajithon.schim.guestbook.dto.GuestbookCreateRequest;
import com.hajithon.schim.guestbook.dto.GuestbookCreateResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

}
