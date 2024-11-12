package com.sportus.be.place.dto.response;

import com.sportus.be.place.domain.MongoPlace;
import lombok.Builder;

@Builder
public record MapPlaceResponse(
        String category,
        String address,
        double longitude,
        double latitude,
        long placeId
) {

    public static MapPlaceResponse from(MongoPlace mongoPlace) {
        return MapPlaceResponse.builder()
                .category(mongoPlace.getFacilityCategory() != null ? mongoPlace.getFacilityCategory().name() : mongoPlace.getLectureCategory().name())
                .address(mongoPlace.getAddress())
                .longitude(mongoPlace.getLocation().getX())
                .latitude(mongoPlace.getLocation().getY())
                .placeId(mongoPlace.getPlaceId())
                .build();
    }
}
