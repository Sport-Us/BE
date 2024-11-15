package com.sportus.be.recommend.dto.response;

import com.sportus.be.recommend.domain.MongoAISearchInfo;

public record MongoAISearchInfoResponse(
        Long placeId,
        Boolean isFacility,
        String placeCategory
) {

    public static MongoAISearchInfoResponse from(MongoAISearchInfo domain) {
        return new MongoAISearchInfoResponse(
                domain.getPlaceId(),
                domain.getIsFacility(),
                domain.getPlaceCategory()
        );
    }
}
