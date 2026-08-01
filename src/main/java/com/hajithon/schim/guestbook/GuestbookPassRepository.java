package com.hajithon.schim.guestbook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookPassRepository extends JpaRepository<GuestbookPass, Long> {
}
