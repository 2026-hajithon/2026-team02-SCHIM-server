package com.hajithon.schim.savedcontent;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface SavedContentRepository extends JpaRepository<SavedContent, Long> {
    Optional<SavedContent> findByUserIdAndContentId(UUID userId, Long contentId);
    boolean existsByUserIdAndContentId(UUID userId, Long contentId);
    void deleteByUserIdAndContentId(UUID userId, Long contentId);
}
