package com.sportus.be.cardnews.dto.response;

import java.util.List;
import lombok.Builder;

@Builder
public record CardNewsResponseList(
        List<CardNewsResponse> cardNewsList
) {
    public static CardNewsResponseList from(List<CardNewsResponse> cardNewsList) {
        return CardNewsResponseList.builder()
                .cardNewsList(cardNewsList)
                .build();
    }
}