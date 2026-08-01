package com.hajithon.schim.content.dto;

import java.util.List;

// 카카오 도서 검색에서 pageCount 가 null일 수 있음.
public record BookDetails(
        String author,
        String publisher,
        Integer publicationYear,
        Integer pageCount
) implements ContentDetails {
}
