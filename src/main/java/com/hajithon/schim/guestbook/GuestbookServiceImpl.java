package com.hajithon.schim.guestbook;

import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;
import com.hajithon.schim.content.Content;
import com.hajithon.schim.content.ContentService;
import com.hajithon.schim.content.dto.ContentSearchResponse;
import com.hajithon.schim.discovery.Discovery;
import com.hajithon.schim.discovery.DiscoveryRepository;
import com.hajithon.schim.guestbook.dto.*;
import com.hajithon.schim.savedcontent.SavedContentRepository;
import com.hajithon.schim.storage.StorageService;
import com.hajithon.schim.surf.SurfCursor;
import com.hajithon.schim.user.User;
import com.hajithon.schim.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

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
    private final GuestbookPassRepository guestbookPassRepository;

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

    @Override
    public GuestbookDetailResponse getMyGuestbookDetail(UUID userId, Long guestbookId) {
        Guestbook guestbook = guestbookRepository.findById(guestbookId)
                .filter(g -> g.getDeletedAt() == null)
                .orElseThrow(() -> new BusinessException(ErrorCode.GUESTBOOK_NOT_FOUND));

        if (!guestbook.getUserId().equals(userId)) {
            throw new BusinessException(ErrorCode.GUESTBOOK_FORBIDDEN);
        }

        ContentSearchResponse content = contentService.getDetail(guestbook.getContentId());
        long passCount = guestbookPassRepository.countByGuestbookId(guestbookId);
        long openCount = discoveryRepository.countByGuestbookId(guestbookId);

        return new GuestbookDetailResponse(
                guestbook.getId(), guestbook.getImageUrl(), content,
                new GuestbookDetailResponse.Stats(passCount, openCount),
                guestbook.getCreatedAt(), guestbook.getUpdatedAt()
        );
    }

    @Override
    public ContentGuestbookPage getGuestbooksByContent(UUID userId, Long contentId, String cursor, int limit) {
        int size = Math.min(limit <= 0 ? 20 : limit, 50);

        boolean hasCursor = cursor != null && !cursor.isBlank();
        LocalDateTime cursorCreatedAt = null;
        Long cursorId = null;
        if (hasCursor) {
            SurfCursor.Decoded decoded = SurfCursor.decode(cursor);
            cursorCreatedAt = decoded.createdAt();
            cursorId = decoded.id();
        }

        List<Guestbook> rows = guestbookRepository.findByContentIdWithCursor(
                contentId, hasCursor, cursorCreatedAt, cursorId, PageRequest.of(0, size + 1)
        );

        boolean hasNext = rows.size() > size;
        List<Guestbook> page = hasNext ? rows.subList(0, size) : rows;

        Set<UUID> authorIds = page.stream().map(Guestbook::getUserId).collect(Collectors.toSet());
        Map<UUID, String> nicknameByUserId = userRepository.findAllById(authorIds).stream()
                .collect(Collectors.toMap(User::getId, User::getNickname));

        List<ContentGuestbookItem> items = new ArrayList<>();
        for (Guestbook g : page) {
            items.add(new ContentGuestbookItem(
                    g.getId(), g.getImageUrl(), nicknameByUserId.get(g.getUserId()),
                    g.getUserId().equals(userId), g.getCreatedAt()
            ));
        }

        String nextCursor = hasNext
                ? SurfCursor.encode(page.get(page.size() - 1).getCreatedAt(), page.get(page.size() - 1).getId())
                : null;

        return new ContentGuestbookPage(items, hasNext, nextCursor);
    }
}
