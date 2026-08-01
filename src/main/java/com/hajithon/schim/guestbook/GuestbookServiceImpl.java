package com.hajithon.schim.guestbook;

import com.hajithon.schim.content.Content;
import com.hajithon.schim.content.ContentService;
import com.hajithon.schim.guestbook.dto.GuestbookCreateRequest;
import com.hajithon.schim.storage.StorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class GuestbookServiceImpl implements GuestbookService {

    private final GuestbookRepository guestbookRepository;
    private final ContentService contentService;
    private final StorageService storageService;
    private final GuestbookImageValidator imageValidator;

    @Override
    @Transactional
    public Guestbook create(UUID userId, MultipartFile image, GuestbookCreateRequest request) {
        imageValidator.validate(image);

        Content content = contentService.resolve(request.toResolveCommand());

        String key = "guestbooks/" + UUID.randomUUID() + ".png";
        String imageUrl = storageService.upload(image, key);

        try {
            Guestbook guestbook = Guestbook.create(userId, content.getId(), imageUrl);
            return guestbookRepository.save(guestbook);
        } catch (RuntimeException e) {
            storageService.delete(imageUrl);
            throw e;
        }
    }
}
