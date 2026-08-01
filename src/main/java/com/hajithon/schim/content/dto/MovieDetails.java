package com.hajithon.schim.content.dto;

import java.util.List;

// director, runtimeMinutes가 null일 수 있음.
public record MovieDetails(
        String director,
        String genre,
        Integer releaseYear,
        Integer runtimeMinutes
) implements ContentDetails {
}
