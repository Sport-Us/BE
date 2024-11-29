package com.sportus.be.review.dto.response;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public record ReviewSimpleResponse(
        Long reviewId,
        String placeName,
        String writer,
        String content,
        BigDecimal rating,
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDateTime date,
        String reviewImageUrl
) {
    @JsonProperty("reviewImageUrl")
    public String getFormattedImageUrl() {
        return reviewImageUrl == null ? "" : reviewImageUrl;
    }
}
