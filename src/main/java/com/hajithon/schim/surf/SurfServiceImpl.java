package com.hajithon.schim.surf;

import com.hajithon.schim.guestbook.GuestbookRepository;
import com.hajithon.schim.surf.dto.SurfCardResponse;
import com.hajithon.schim.surf.dto.SurfPage;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SurfServiceImpl implements SurfService {
    private static final int DEFAULT_LIMIT = 20;
    private static final int MAX_LIMIT = 50;

    private final GuestbookRepository guestbookRepository;

    @Override
    public SurfPage getFeed(UUID userId, String cursor, int limit, boolean revisit) {
        int size = Math.min(limit <= 0 ? DEFAULT_LIMIT : limit, MAX_LIMIT);

        boolean hasCursor = cursor != null && !cursor.isBlank();
        LocalDateTime cursorCreatedAt = null;
        Long cursorId = null;
        if (hasCursor) {
            SurfCursor.Decoded decoded = SurfCursor.decode(cursor);
            cursorCreatedAt = decoded.createdAt();
            cursorId = decoded.id();
        }

        List<SurfRow> rows = revisit
                ? guestbookRepository.findRevisit(userId, hasCursor, cursorCreatedAt, cursorId, size + 1)
                : guestbookRepository.findUnvisited(userId, hasCursor, cursorCreatedAt, cursorId, size + 1);

        boolean hasNext = rows.size() > size;
        List<SurfRow> page = hasNext ? rows.subList(0, size) : rows;

        List<SurfCardResponse> items = new ArrayList<>();
        for (SurfRow row : page) {
            items.add(new SurfCardResponse(row.getGuestbookId(), row.getImageUrl(), row.getAuthorNickname(), row.getCreatedAt()));
        }

        String nextCursor = hasNext
                ? SurfCursor.encode(page.get(page.size() - 1).getCreatedAt(), page.get(page.size() - 1).getGuestbookId())
                : null;

        return new SurfPage(items, hasNext, nextCursor);
    }
}
