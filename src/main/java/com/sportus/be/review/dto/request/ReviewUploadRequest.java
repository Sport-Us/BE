package com.sportus.be.review.dto.request;

import com.sportus.be.place.domain.Place;
import com.sportus.be.review.domain.Review;
import com.sportus.be.user.domain.User;
import jakarta.validation.constraints.DecimalMax;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.math.BigDecimal;

public record ReviewUploadRequest(
        @Positive
        Long placeId,

        @DecimalMin("0.0")  // 최소 평점
        @DecimalMax("5.0")  // 최대 평점
        BigDecimal rating,

        @Size(min = 1, max = 500)  // 최대 500자
        String content
) {
    public Review toEntity(Place place, User user, String imageUrl) {
        return Review.builder()
                .place(place)
                .rating(rating)
                .content(content)
                .user(user)
                .reviewImageUrl(imageUrl)
                .build();
    }
}
