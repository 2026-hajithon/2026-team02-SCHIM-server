package com.hajithon.schim.content.detail;

import com.hajithon.schim.content.Content;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "content_place_detail")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class PlaceDetail {
    @Id
    @Column(name = "content_id")
    private Long contentId;

    @MapsId
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "content_id")
    private Content content;

    private String address; // 주소

    @Column(name = "business_type")
    private String businessType; // 업종

    public PlaceDetail(
            Content content,
            String address,
            String businessType
    ) {
        this.content = content;
        this.address = address;
        this.businessType = businessType;
    }
}
