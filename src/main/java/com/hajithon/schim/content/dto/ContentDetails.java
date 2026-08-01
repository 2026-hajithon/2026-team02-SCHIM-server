package com.hajithon.schim.content.dto;


public sealed interface ContentDetails permits
        MovieDetails,
        MusicDetails,
        BookDetails,
        PlaceDetails,
        PerformanceDetails,
        EtcDetails
{
}
