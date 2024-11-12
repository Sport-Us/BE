package com.sportus.be.place.domain;

import com.sportus.be.bookmark.domain.BookMark;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import com.sportus.be.review.domain.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.locationtech.jts.geom.Point;

@Table(name = "place", indexes = {
        @Index(name = "idx_place_location", columnList = "location")
})
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "facility_category")
    private FacilityCategory facilityCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "lecture_category")
    private LectureCategory lectureCategory;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "detail_info", nullable = false)
    private String detailInfo;

    @Column(name = "region", nullable = false)
    private String region;

    @Column(name = "location", nullable = false, columnDefinition = "POINT SRID 4326")
    private Point location;

    @OneToMany(mappedBy = "place", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    @OneToMany(mappedBy = "place", orphanRemoval = true, cascade = CascadeType.REMOVE)
    private List<BookMark> bookMarkList;

    @Builder
    public Place(String name, FacilityCategory facilityCategory, LectureCategory lectureCategory, String region,
                 String address, String detailInfo, Point location) {
        this.name = name;
        this.facilityCategory = facilityCategory;
        this.lectureCategory = lectureCategory;
        this.address = address;
        this.detailInfo = detailInfo;
        this.location = location;
        this.region = region;
    }
}
