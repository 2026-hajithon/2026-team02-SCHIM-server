package com.hajithon.schim.guestbook;

import org.springframework.data.jpa.repository.JpaRepository;

public interface GuestbookRepository extends JpaRepository<Guestbook, Long> {
}
