package com.sportus.be.cardnews.dto.response;

import com.sportus.be.cardnews.domain.Card;
import lombok.Builder;

@Builder
public record CardResponse(
        String cardImageUrl
) {
    public static CardResponse from(Card card) {
        return CardResponse.builder()
                .cardImageUrl(card.getCardImageUrl())
                .build();
    }
}
