package com.hajithon.schim.content;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface ContentRepository extends JpaRepository<Content, Long> {

    Optional<Content> findByProviderAndExternalId(Provider provider, String externalId);

    List<Content> findByCategoryAndTitleContainingIgnoreCase(Category category, String keyword);
}
