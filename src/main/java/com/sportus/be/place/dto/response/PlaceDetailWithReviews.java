package com.sportus.be.place.dto.response;

import com.sportus.be.review.dto.response.ReviewSimpleResponse;
import java.util.List;

public record PlaceDetailWithReviews(
        Boolean hasNext,
        PlaceDetailResponse placeDetail,
        List<ReviewSimpleResponse> recentReviews
) {
}
