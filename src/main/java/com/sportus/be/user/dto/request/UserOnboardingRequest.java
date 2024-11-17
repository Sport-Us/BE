package com.sportus.be.user.dto.request;

import com.sportus.be.recommend.domain.MongoAIUserOnboardingInfo;
import com.sportus.be.user.domain.Onboarding;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.OnboardingType;

public record UserOnboardingRequest(
        OnboardingType onboardingType,
        String content
) {

    public Onboarding toEntity(User user) {
        return Onboarding.builder()
                .user(user)
                .onboardingType(onboardingType)
                .content(content)
                .build();
    }

    public MongoAIUserOnboardingInfo toMongoEntity() {
        return MongoAIUserOnboardingInfo.builder()
                .onboardingType(onboardingType.name())
                .content(content)
                .build();
    }
}
