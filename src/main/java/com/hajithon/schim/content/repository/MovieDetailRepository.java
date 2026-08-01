package com.hajithon.schim.content.repository;

import com.hajithon.schim.content.detail.MovieDetail;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieDetailRepository extends JpaRepository<MovieDetail, Long> {
}
