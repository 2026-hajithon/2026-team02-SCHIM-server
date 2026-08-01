package com.hajithon.schim.content;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Optional<Content> findByProviderAndExternalId(Provider provider, String externalId);

    List<Content> findByCategoryAndTitleContainingIgnoreCase(Category category, String keyword);

    @Query(value = """
            SELECT COUNT(*)
            FROM guestbooks
            WHERE content_id = :contentId
              AND deleted_at IS NULL
            """, nativeQuery = true)
    long countGuestbooksByContentId(@Param("contentId") Long contentId);
}
