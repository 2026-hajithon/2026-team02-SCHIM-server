package com.hajithon.schim.content.kakao;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public record KakaoBookSearchResponse(Meta meta, List<Document> documents) {

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Meta(@JsonProperty("is_end") boolean isEnd) {
    }

    @JsonIgnoreProperties(ignoreUnknown = true)
    public record Document(
            String title,
            String contents,
            String url,
            String isbn,
            String datetime,
            String publisher,
            List<String> authors,
            List<String> translators,
            String thumbnail
    ) {
    }
}
