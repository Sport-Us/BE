package com.sportus.be.place.dto.response;

import java.util.List;

public record AIRecommendResponse(
        int maxDistance,
        List<String> categories,
        String sortType
) {
}
