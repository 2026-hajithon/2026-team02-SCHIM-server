package com.hajithon.schim.content.dto;

import java.time.LocalDate;

public record PerformanceDetails(
        String venue,
        String period,
        Integer runtimeMinutes,
        String ageRating
) implements ContentDetails {
}
