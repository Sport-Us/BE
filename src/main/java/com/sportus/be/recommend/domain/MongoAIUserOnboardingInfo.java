package com.sportus.be.recommend.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "ai_user_onboarding_info")
public class MongoAIUserOnboardingInfo {

    @MongoId
    @Field(name = "_id")
    private String id;

    @Field(name = "onboarding_type")
    private String onboardingType;

    @Field(name = "content")
    private String content;

    @Builder
    public MongoAIUserOnboardingInfo(String onboardingType, String content) {
        this.onboardingType = onboardingType;
        this.content = content;
    }
}