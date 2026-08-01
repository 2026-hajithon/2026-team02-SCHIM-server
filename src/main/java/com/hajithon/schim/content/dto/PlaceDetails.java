package com.hajithon.schim.content.dto;

import java.math.BigDecimal;

public record PlaceDetails(
        String address,
        String businessType,
        String businessHours
) implements ContentDetails {
}
