package com.hajithon.schim.discovery;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface DiscoveryRepository extends JpaRepository<Discovery, Long> {
    Optional<Discovery> findByUserIdAndGuestbookId(UUID userId, Long guestbookID);
}
