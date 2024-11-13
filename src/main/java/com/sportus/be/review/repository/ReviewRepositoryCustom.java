package com.sportus.be.review.repository;

import com.sportus.be.review.dto.response.ReviewSimpleResponse;
import java.util.List;

public interface ReviewRepositoryCustom {

    List<ReviewSimpleResponse> getReviewSimpleResponse(Long placeId, Long lastReviewId, Long size);

    List<ReviewSimpleResponse> getMyFirstReview(Long userId);

    List<ReviewSimpleResponse> getMyReview(Long userId, Long lastReviewId, Long size);

    Boolean hasNextMyReview(Long userId, Long lastReviewId, Long size);
}
