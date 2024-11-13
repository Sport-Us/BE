package com.sportus.be.place.dto.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record SearchPlaceResponse(
        Long placeId,
        String name,
        String address,
        Long distance,
        Object category,
        Double rating,
        Long reviewCount
) {
    @JsonProperty("category")
    public String getCategoryAsString() {
        return category != null ? category.toString() : ""; // enum을 String으로 변환
    }

    @JsonProperty("rating")
    public Double getFormattedRating() {
        return Math.round(rating * 10) / 10.0;
    }
}
