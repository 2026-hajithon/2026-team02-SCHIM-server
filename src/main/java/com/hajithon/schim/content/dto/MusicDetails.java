package com.hajithon.schim.content.dto;

import java.time.LocalDate;

public record MusicDetails(
        String artist,
        String album,
        LocalDate releaseDate,
        Long durationMillis
) implements ContentDetails {
}
