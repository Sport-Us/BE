package com.sportus.be.ai.domain;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "ai_analysis")
public class MongoAISearchInfo {

    @MongoId
    @Field(name = "_id")
    private String id;

    @Field(name = "user_id")
    private Long userId;

    @Field(name = "place_id")
    private Long placeId;

    @Builder
    public MongoAISearchInfo(Long userId, Long placeId) {
        this.userId = userId;
        this.placeId = placeId;
    }
}