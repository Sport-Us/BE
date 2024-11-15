package com.sportus.be.recommend.dto.response;

import com.sportus.be.recommend.domain.MongoAIUserOnboardingInfo;

public record MongoAIUserOnboardingInfoResponse(
        String onboardingType,
        String content
) {

    public static MongoAIUserOnboardingInfoResponse from(MongoAIUserOnboardingInfo domain) {
        return new MongoAIUserOnboardingInfoResponse(
                domain.getOnboardingType(),
                domain.getContent()
        );
    }
}
