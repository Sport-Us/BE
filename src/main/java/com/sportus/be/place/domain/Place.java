package com.sportus.be.place.domain;

import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;
import java.time.LocalTime;
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

    @Column(name = "region", nullable = false)
    private String region;

    @Enumerated(EnumType.STRING)
    @Column(name = "facility_category")
    private FacilityCategory facilityCategory;

    @Enumerated(EnumType.STRING)
    @Column(name = "lecture_category")
    private LectureCategory lectureCategory;

    @Column(name = "address", nullable = false)
    private String address;

    @Column(name = "open_time", nullable = false)
    private LocalTime openTime;

    @Column(name = "close_time", nullable = false)
    private LocalTime closeTime;

    @Column(name = "rest_info", nullable = false)
    private String restInfo;

    @Column(name = "location", nullable = false, columnDefinition = "POINT SRID 4326")
    private Point location;

    @Builder
    public Place(String name, String region, FacilityCategory facilityCategory, LectureCategory lectureCategory,
                 String address, LocalTime openTime, LocalTime closeTime, String restInfo, Point location) {
        this.name = name;
        this.region = region;
        this.facilityCategory = facilityCategory;
        this.lectureCategory = lectureCategory;
        this.address = address;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.restInfo = restInfo;
        this.location = location;
    }
}
