package com.hajithon.schim.content.kakao;

import com.hajithon.schim.content.Category;
import com.hajithon.schim.content.ContentSearchQuery;
import com.hajithon.schim.content.ExternalContent;
import com.hajithon.schim.content.ExternalContentPage;
import com.hajithon.schim.content.Provider;
import com.hajithon.schim.content.dto.BookDetails;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledIfEnvironmentVariable;

import static org.assertj.core.api.Assertions.assertThat;

@Tag("external")
@EnabledIfEnvironmentVariable(
        named = "KAKAO_REST_API_KEY",
        matches = ".+"
)
class KakaoBookSearchTest {

    @Test
    void 카카오에서_도서를_검색한다() {
        String apiKey = System.getenv("KAKAO_REST_API_KEY");
        KakaoBookSearchAdapter adapter =
                new KakaoBookSearchAdapter(apiKey);

        ContentSearchQuery query = ContentSearchQuery.of(
                "소년이 온다",
                Category.BOOK,
                1,
                10
        );

        ExternalContentPage result = adapter.search(query);

        assertThat(result.contents()).isNotEmpty();

        ExternalContent first = result.contents().getFirst();

        assertThat(first.provider())
                .isEqualTo(Provider.KAKAO_BOOK);
        assertThat(first.category())
                .isEqualTo(Category.BOOK);
        assertThat(first.externalId()).isNotBlank();
        assertThat(first.title()).isNotBlank();
        assertThat(first.details())
                .isInstanceOf(BookDetails.class);

        BookDetails details = (BookDetails) first.details();

        assertThat(details.author()).isNotBlank();
    }
}