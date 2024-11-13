package com.sportus.be.place.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;

public record PlaceDetailResponse(
        Long placeId,
        String name,
        String address,
        @JsonIgnore
        FacilityCategory facilityCategory,
        @JsonIgnore
        LectureCategory lectureCategory,
        String detailInfo,
        Double rating,
        Boolean isBookmarked
) {
    @JsonProperty("category")
    public String getCategoryAsString() {
        return facilityCategory != null ? facilityCategory.toString() : lectureCategory.toString();
    }

    @JsonProperty("rating")
    public Double getFormattedRating() {
        return Math.round(rating * 10) / 10.0;
    }
}
