package com.hajithon.schim.content.kakao;

import com.hajithon.schim.common.exception.BusinessException;
import com.hajithon.schim.common.exception.ErrorCode;
import com.hajithon.schim.content.*;
import com.hajithon.schim.content.dto.BookDetails;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClient;
import org.springframework.web.client.RestClientException;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Component
public class KakaoBookSearchAdapter implements ContentSearchPort {
    private final RestClient restClient;

    public KakaoBookSearchAdapter(@Value("${kakao.rest-api-key}") String apiKey) {
        this.restClient = RestClient.builder()
                .baseUrl("https://dapi.kakao.com")
                .defaultHeader("Authorization", "KakaoAK " + apiKey)
                .build();
    }

    @Override
    public boolean supports(Category category) {
        return category == Category.BOOK;
    }

    @Override
    public ExternalContentPage search(ContentSearchQuery query) {
        try {
            KakaoBookSearchResponse response = restClient.get()
                    .uri(uriBuilder -> uriBuilder
                            .path("/v3/search/book")
                            .queryParam("query", query.keyword())
                            .queryParam("page", query.page())
                            .queryParam("size", query.size())
                            .build())
                    .retrieve()
                    .body(KakaoBookSearchResponse.class);

            if (response == null || response.documents() == null) {
                throw new BusinessException(ErrorCode.EXTERNAL_SEARCH_FAILED);
            }

            boolean hasNext = response.meta() != null && !response.meta().isEnd();
            return ExternalContentPage.of(toExternalContents(response.documents()), hasNext);
        } catch (RestClientException e) {
            throw new BusinessException(ErrorCode.EXTERNAL_SEARCH_FAILED);
        }
    }

    private List<ExternalContent> toExternalContents(List<KakaoBookSearchResponse.Document> documents) {
        Set<String> seenIsbns = new LinkedHashSet<>();
        List<ExternalContent> results = new ArrayList<>();

        for (KakaoBookSearchResponse.Document doc : documents) {
            String isbn = extractIsbn(doc.isbn());
            if (isbn.isBlank() || !seenIsbns.add(isbn)) {
                continue;
            }

            BookDetails details = new BookDetails(
                    extractAuthor(doc),
                    doc.publisher(),
                    extractYear(doc.datetime()),
                    null // 카카오 도서 검색은 쪽수를 안 줌
            );

            results.add(ExternalContent.of(
                    Provider.KAKAO_BOOK,
                    isbn,
                    Category.BOOK,
                    doc.title(),
                    doc.contents(),
                    details
            ));
        }

        return results;
    }

    private String extractIsbn(String isbn) {
        if (isbn == null || isbn.isBlank()) {
            return "";
        }
        String[] parts = isbn.trim().split("\\s+");
        return parts[parts.length - 1];
    }

    private String extractAuthor(KakaoBookSearchResponse.Document doc) {
        if (doc.authors() != null && !doc.authors().isEmpty()) {
            return doc.authors().getFirst();
        }
        if (doc.translators() != null && !doc.translators().isEmpty()) {
            return doc.translators().getFirst();
        }
        return null;
    }

    private Integer extractYear(String datetime) {
        if (datetime == null || datetime.length() < 4) {
            return null;
        }
        try {
            return Integer.parseInt(datetime.substring(0, 4));
        } catch (NumberFormatException e) {
            return null;
        }
    }
}
