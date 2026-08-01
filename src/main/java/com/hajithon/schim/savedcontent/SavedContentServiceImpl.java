package com.hajithon.schim.savedcontent;

import com.hajithon.schim.content.Category;
import com.hajithon.schim.savedcontent.dto.SavedContentItem;
import com.hajithon.schim.savedcontent.dto.SavedContentPage;
import com.hajithon.schim.savedcontent.dto.SavedContentSummary;
import com.hajithon.schim.surf.SurfCursor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class SavedContentServiceImpl implements SavedContentService {

    private final SavedContentRepository savedContentRepository;

    @Override
    @Transactional
    public SavedContentResponse save(UUID userId, Long contentId) {
        SavedContent savedContent = savedContentRepository.findByUserIdAndContentId(userId, contentId)
                .orElseGet(() -> savedContentRepository.save(SavedContent.create(userId, contentId)));

        return new SavedContentResponse(contentId, true, savedContent.getSavedAt());
    }

    @Override
    @Transactional
    public void unsave(UUID userId, Long contentId) {
        savedContentRepository.deleteByUserIdAndContentId(userId, contentId);
    }

    @Override
    public SavedContentPage getMyShelf(UUID userId, Category category, String cursor, int limit) {
        int size = Math.min(limit <= 0 ? 20 : limit, 50);

        boolean hasCursor = cursor != null && !cursor.isBlank();
        LocalDateTime cursorSavedAt = null;
        Long cursorContentId = null;
        if (hasCursor) {
            SurfCursor.Decoded decoded = SurfCursor.decode(cursor);
            cursorSavedAt = decoded.createdAt();
            cursorContentId = decoded.id();
        }

        List<SavedContentRow> rows = savedContentRepository.findSavedContents(
                userId, category != null ? category.name() : null,
                hasCursor, cursorSavedAt, cursorContentId, size + 1
        );

        boolean hasNext = rows.size() > size;
        List<SavedContentRow> page = hasNext ? rows.subList(0, size) : rows;

        List<SavedContentItem> items = new ArrayList<>();
        for (SavedContentRow row : page) {
            SavedContentSummary summary = new SavedContentSummary(
                    row.getContentId(), Category.valueOf(row.getCategory()), row.getTitle()
            );
            items.add(new SavedContentItem(summary, row.getGuestbookCount(), row.getSavedAt()));
        }

        String nextCursor = hasNext
                ? SurfCursor.encode(page.get(page.size() - 1).getSavedAt(), page.get(page.size() - 1).getContentId())
                : null;

        return new SavedContentPage(items, hasNext, nextCursor);
    }
}
