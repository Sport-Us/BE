package com.sportus.be.cardnews.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CardNewsDetailResponse(
        List<CardResponse> cardImageUrlList
) {
    public static CardNewsDetailResponse from(List<CardResponse> cardList) {
        return CardNewsDetailResponse.builder()
                .cardImageUrlList(cardList)
                .build();
    }
}