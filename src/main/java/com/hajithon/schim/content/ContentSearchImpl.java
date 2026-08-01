package com.hajithon.schim.content;

import com.hajithon.schim.content.dto.*;
import com.hajithon.schim.content.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ContentSearchImpl implements ContentService{

    private final ContentRepository contentRepository;
    private final BookDetailRepository bookDetailRepository;
    private final MovieDetailRepository movieDetailRepository;
    private final MusicDetailRepository musicDetailRepository;
    private final PlaceDetailRepository placeDetailRepository;
    private final PerformanceDetailRepository performanceDetailRepository;
    private final List<ContentSearchPort> searchPorts;

    @Override
    public ContentSearchPage search(ContentSearchQuery query) {
        List<Content> internalMatches = contentRepository
                .findByCategoryAndTitleContainingIgnoreCase(query.category(), query.keyword());
        List<ContentSearchResponse> results = new ArrayList<>();
        Set<Long> usedContentIds = new HashSet<>();
        boolean hasNext = false;

        ContentSearchPort port = findPort(query.category());
        if (port != null) {
            ExternalContentPage externalPage = port.search(query);
            hasNext = externalPage.hasNext();

            for (ExternalContent external : externalPage.contents()) {
                Optional<Content> matched = contentRepository
                        .findByProviderAndExternalId(external.provider(),external.externalId());
                if (matched.isPresent()) {
                    Content content = matched.get();
                    usedContentIds.add(content.getId());
                    results.add(toResponse(content));
                } else {
                    results.add(toResponse(external));
                }
            }
        }

        for (Content content : internalMatches) {
            if (usedContentIds.add(content.getId())) {
                results.add(toResponse(content));
            }
        }

        results.sort(Comparator.comparing(r -> r.contentId() == null));

        return new ContentSearchPage(results, hasNext);
    }

    private ContentSearchResponse toResponse(Content content) {
        return new ContentSearchResponse(
                content.getId(), content.getProvider(), content.getExternalId(), content.getCategory(),
                content.getTitle(), content.getDescription(),
                loadDetails(content), countGuestbooks(content)
        );
    }

    private ContentSearchResponse toResponse(ExternalContent external) {
        return new ContentSearchResponse(
                null, external.provider(), external.externalId(), external.category(),
                external.title(), external.description(),
                external.details(), 0
        );
    }

    private ContentDetails loadDetails(Content content) {
        return switch (content.getCategory()) {
            case BOOK -> bookDetailRepository.findById(content.getId())
                    .<ContentDetails>map(d -> new BookDetails(d.getAuthor(), d.getPublisher(), d.getPublicationYear(), d.getPageCount()))
                    .orElse(null);
            case MOVIE -> movieDetailRepository.findById(content.getId())
                    .<ContentDetails>map(d -> new MovieDetails(d.getDirector(), d.getGenre(), d.getReleaseYear(), d.getRuntimeMinutes()))
                    .orElse(null);
            case MUSIC -> musicDetailRepository.findById(content.getId())
                    .<ContentDetails>map(d -> new MusicDetails(d.getArtist(), d.getAlbum(), d.getReleaseDate(),
                            d.getDurationMillis() == null ? null : d.getDurationMillis().longValue()))
                    .orElse(null);
            case PLACE -> placeDetailRepository.findById(content.getId())
                    .<ContentDetails>map(d -> new PlaceDetails(d.getAddress(), d.getBusinessType()))
                    .orElse(null);
            case PERFORMANCE -> performanceDetailRepository.findById(content.getId())
                    .<ContentDetails>map(d -> new PerformanceDetails(d.getVenue(), d.getPeriod(), d.getRuntimeMinutes(), d.getAgeRating()))
                    .orElse(null);
            case ETC -> new EtcDetails();
        };
    }

    private ContentSearchPort findPort(Category category) {
        return searchPorts.stream()
                .filter(p -> p.supports(category))
                .findFirst()
                .orElse(null);
    }

    private long countGuestbooks(Content content) {
        return 0L; // TODO: 방명록 도메인 생기면 실제 COUNT 쿼리로 교체
    }
}
