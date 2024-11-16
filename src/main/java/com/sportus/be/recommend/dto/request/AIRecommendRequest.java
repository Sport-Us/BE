package com.sportus.be.recommend.dto.request;

import lombok.Builder;

@Builder
public record AIRecommendRequest(
        String query,
        boolean tokenStream
) {

    public static AIRecommendRequest of(String query, boolean tokenStream) {
        return AIRecommendRequest.builder()
                .query(query)
                .tokenStream(tokenStream)
                .build();
    }
}
