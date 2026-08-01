package com.hajithon.schim.guestbook;

import com.hajithon.schim.guestbook.dto.GuestbookCreateRequest;
import com.hajithon.schim.guestbook.dto.GuestbookOpenResponse;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface GuestbookService {
    Guestbook create(UUID userId, MultipartFile image, GuestbookCreateRequest request);

    GuestbookOpenResponse open(UUID userId, Long guestbookId);
}
