package com.sportus.be.place.dto.response;

import com.sportus.be.review.dto.response.ReviewSimpleResponse;
import java.util.List;

public record PlaceDetailWithReviews(
        Boolean hasNext,
        PlaceDetailResponse placeDetail,
        List<ReviewSimpleResponse> recentReviews
) {

    public boolean isFacility() {
        return placeDetail().facilityCategory() != null;
    }

    public String getCategoryAsString() {
        return placeDetail().facilityCategory() != null ?
                placeDetail().facilityCategory().toString() : placeDetail().lectureCategory().toString();
    }
}
