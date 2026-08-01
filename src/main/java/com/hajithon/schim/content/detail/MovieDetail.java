package com.hajithon.schim.content.detail;

import com.hajithon.schim.content.Content;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_movie_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MovieDetail {

    @Id
    @Column(name = "content_id")
    private Long contentId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private String director; // 감독
    private String genre; // 장르

    @Column(name = "release_year")
    private Integer releaseYear;

    @Column(name = "runtime_minute")
    private Integer runtimeMinutes;

    public MovieDetail(
            Content content,
            String director,
            String genre,
            Integer releaseYear,
            Integer runtimeMinutes
    ) {
        this.content = content;
        this.director = director;
        this.genre = genre;
        this.releaseYear = releaseYear;
        this.runtimeMinutes = runtimeMinutes;
    }
}
