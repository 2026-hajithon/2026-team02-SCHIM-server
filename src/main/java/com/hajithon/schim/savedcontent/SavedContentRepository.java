package com.hajithon.schim.savedcontent;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SavedContentRepository extends JpaRepository<SavedContent, Long> {
    Optional<SavedContent> findByUserIdAndContentId(UUID userId, Long contentId);
    boolean existsByUserIdAndContentId(UUID userId, Long contentId);
    void deleteByUserIdAndContentId(UUID userId, Long contentId);

    // 무지성 쿼리 추가
    @Query(value = """
        SELECT c.id AS contentId, c.category AS category, c.title AS title,
               s.saved_at AS savedAt, COUNT(g.id) AS guestbookCount
        FROM saved_contents s
        JOIN content c ON c.id = s.content_id
        LEFT JOIN guestbooks g ON g.content_id = c.id AND g.deleted_at IS NULL
        WHERE s.user_id = :userId
          AND (:category IS NULL OR c.category = :category)
          AND (:hasCursor = false OR (s.saved_at, c.id) < (CAST(:cursorSavedAt AS timestamp), :cursorContentId))
        GROUP BY c.id, s.saved_at
        ORDER BY s.saved_at DESC, c.id DESC
        LIMIT :limit
        """, nativeQuery = true)
    List<SavedContentRow> findSavedContents(
            @Param("userId") UUID userId,
            @Param("category") String category,
            @Param("hasCursor") boolean hasCursor,
            @Param("cursorSavedAt") LocalDateTime cursorSavedAt,
            @Param("cursorContentId") Long cursorContentId,
            @Param("limit") int limit
    );
}
