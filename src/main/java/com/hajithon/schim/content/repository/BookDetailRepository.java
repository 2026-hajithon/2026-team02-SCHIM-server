package com.hajithon.schim.content.repository;

import com.hajithon.schim.content.detail.BookDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetail, Long> {
}
