package com.sportus.be.place.domain;

import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.geo.GeoJsonPoint;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexType;
import org.springframework.data.mongodb.core.index.GeoSpatialIndexed;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "place")
public class MongoPlace {

    @MongoId
    @Field(name = "_id")
    private String id;

    @Field(name = "facility_category")
    private FacilityCategory facilityCategory;

    @Field(name = "lecture_category")
    private LectureCategory lectureCategory;

    @Field(name = "address")
    private String address;

    @GeoSpatialIndexed(type = GeoSpatialIndexType.GEO_2DSPHERE)
    @Field(name = "location")
    private GeoJsonPoint location;

    @Indexed(unique = true)
    @Field(name = "place_id")
    private Long placeId;

    @Builder
    public MongoPlace(FacilityCategory facilityCategory, LectureCategory lectureCategory, String address,
                      GeoJsonPoint location, Long placeId) {
        this.facilityCategory = facilityCategory;
        this.lectureCategory = lectureCategory;
        this.address = address;
        this.location = location;
        this.placeId = placeId;
    }
}