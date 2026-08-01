package com.hajithon.schim.content.dto;

import java.util.List;

// director, runtimeMinutes가 null일 수 있음.
public record MovieDetails(
        String director,
        List<String> genres,
        Integer releaseYear,
        Integer runtimeMinutes
) implements ContentDetails {
    public MovieDetails {
        genres = genres == null ? List.of() : List.copyOf(genres);
    }
}
