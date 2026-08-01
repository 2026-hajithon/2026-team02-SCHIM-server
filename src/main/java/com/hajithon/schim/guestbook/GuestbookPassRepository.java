package com.hajithon.schim.guestbook;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface GuestbookPassRepository extends JpaRepository<GuestbookPass, Long> {
    boolean existsByGuestbookIdAndUserId(Long guestbookId, UUID userId);
    long countByGuestbookId(Long guestbookId);
}
