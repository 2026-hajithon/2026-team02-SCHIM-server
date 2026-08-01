package com.hajithon.schim.content.detail;

import com.hajithon.schim.content.Content;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "content_music_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class MusicDetail {
    @Id
    @Column(name = "content_id")
    private Long contentId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private String artist; // 아티스트
    private String album; // 앨범

    @Column(name = "release_date")
    private LocalDate releaseDate; // 발매일

    @Column(name = "duration_millis")
    private Integer durationMillis; // 곡 시간

    public MusicDetail(
            Content content,
            String artist,
            String album,
            LocalDate releaseDate,
            Integer durationMillis
    ) {
        this.content = content;
        this.artist = artist;
        this.album = album;
        this.releaseDate = releaseDate;
        this.durationMillis = durationMillis;
    }
}
