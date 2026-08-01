package com.hajithon.schim.guestbook;

import com.hajithon.schim.surf.SurfRow;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {

    // 무지성 SQL
    @Query(value = """
            SELECT g.id AS guestbookId, g.image_url AS imageUrl,
            u.nickname AS authorNickname, g.created_at AS createdAt
            FROM guestbooks g
            JOIN users u ON u.id = g.user_id
            WHERE g.user_id <> :userId
              AND g.deleted_at IS NULL
              AND NOT EXISTS (SELECT 1 FROM discoveries d WHERE d.guestbook_id = g.id AND d.user_id = :userId)
              AND NOT EXISTS (SELECT 1 FROM guestbook_passes p WHERE p.guestbook_id = g.id AND p.user_id = :userId)
              AND (:hasCursor = false OR (g.created_at, g.id) < (CAST(:cursorCreatedAt AS timestamp), :cursorId))
            ORDER BY g.created_at DESC, g.id DESC
            LIMIT :limit
""", nativeQuery = true)
    List<SurfRow> findUnvisited(
            @Param("userId") UUID userId,
            @Param("hasCursor") boolean hasCursor,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            @Param("limit") int limit
    );

    @Query(value = """
            SELECT g.id AS guestbookId, g.image_url AS imageUrl, u.nickname AS authorNickname, g.created_at AS createdAt
            FROM guestbooks g
            JOIN users u ON u.id = g.user_id
            WHERE g.user_id <> :userId
              AND g.deleted_at IS NULL
              AND NOT EXISTS (SELECT 1 FROM discoveries d WHERE d.guestbook_id = g.id AND d.user_id = :userId)
              AND EXISTS (SELECT 1 FROM guestbook_passes p WHERE p.guestbook_id = g.id AND p.user_id = :userId)
              AND (:hasCursor = false OR (g.created_at, g.id) < (CAST(:cursorCreatedAt AS timestamp), :cursorId))
            ORDER BY g.created_at DESC, g.id DESC
            LIMIT :limit
            """, nativeQuery = true)
    List<SurfRow> findRevisit(
            @Param("userId") UUID userId,
            @Param("hasCursor") boolean hasCursor,
            @Param("cursorCreatedAt") LocalDateTime cursorCreatedAt,
            @Param("cursorId") Long cursorId,
            @Param("limit") int limit
    );
}
