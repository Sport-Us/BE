package com.sportus.be.bookmark.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sportus.be.place.domain.type.FacilityCategory;
import com.sportus.be.place.domain.type.LectureCategory;
import lombok.Builder;

@Builder
public record BookMarkPlaceResponse(
        Long bookMarkId,
        Long placeId,
        String name,
        String address,
        FacilityCategory facilityCategory,
        LectureCategory lectureCategory,
        Double rating,
        Long reviewCount
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
