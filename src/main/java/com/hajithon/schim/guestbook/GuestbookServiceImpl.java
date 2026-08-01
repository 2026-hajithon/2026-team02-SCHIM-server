package com.hajithon.schim.guestbook;

import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;
import com.hajithon.schim.content.Content;
import com.hajithon.schim.content.ContentService;
import com.hajithon.schim.content.dto.ContentSearchResponse;
import com.hajithon.schim.discovery.Discovery;
import com.hajithon.schim.discovery.DiscoveryRepository;
import com.hajithon.schim.guestbook.dto.GuestbookCreateRequest;
import com.hajithon.schim.guestbook.dto.GuestbookOpenResponse;
import com.hajithon.schim.savedcontent.SavedContentRepository;
import com.hajithon.schim.storage.StorageService;
import com.hajithon.schim.user.User;
import com.hajithon.schim.user.UserRepository;
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
    private final DiscoveryRepository discoveryRepository;
    private final UserRepository userRepository;
    private final SavedContentRepository savedContentRepository;

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

    @Override
    public GuestbookOpenResponse open(UUID userId, Long guestbookId) {
        Guestbook guestbook = guestbookRepository.findById(guestbookId)
                .filter(g -> g.getDeletedAt() == null)
                .orElseThrow(() -> new BusinessException(ErrorCode.GUESTBOOK_NOT_FOUND));

        if (guestbook.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.CANNOT_OPEN_OWN_GUESTBOOK);
        }

        Discovery discovery = discoveryRepository
                .findByUserIdAndGuestbookId(userId, guestbookId)
                .orElseGet(() -> discoveryRepository.save(
                        Discovery.create(userId, guestbookId, guestbook.getContentId())
                ));

        ContentSearchResponse content = contentService.getDetail(guestbook.getContentId());
        User author = userRepository.findById(guestbook.getUserId())
                .orElseThrow(() -> new BusinessException(ErrorCode.USER_NOT_FOUND));

        boolean saved = savedContentRepository.existsByUserIdAndContentId(userId, guestbook.getContentId());

        return new GuestbookOpenResponse(
                guestbook.getId(),
                content,
                author.getNickname(),
                saved,
                discovery.getOpenedAt()
        );
    }
}
