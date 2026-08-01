package com.hajithon.schim.content.detail;

import com.hajithon.schim.content.Content;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_book_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class BookDetail {

    @Id
    @Column(name = "content_id")
    private Long contentId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private String author; // 저자
    private String publisher; // 출판사

    @Column(name = "publication_year")
    private Integer publicationYear; // 출간연도

    @Column(name = "page_count")
    private Integer pageCount; // 쪽수

    public BookDetail(
            Content content,
            String author,
            String publisher,
            Integer publicationYear,
            Integer pageCount
    ) {
        this.content = content;
        this.author = author;
        this.publisher = publisher;
        this.publicationYear = publicationYear;
        this.pageCount = pageCount;
    }
}
