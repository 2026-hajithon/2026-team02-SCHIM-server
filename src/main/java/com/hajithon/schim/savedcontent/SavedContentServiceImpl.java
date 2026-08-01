package com.hajithon.schim.savedcontent;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
