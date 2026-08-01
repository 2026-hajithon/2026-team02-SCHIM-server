package com.hajithon.schim.content.dto;

public record PlaceDetails(
        String address,
        String businessType
) implements ContentDetails {
}
