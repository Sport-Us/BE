package com.sportus.be.recommend.domain;

import java.util.List;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;
import org.springframework.data.mongodb.core.mapping.MongoId;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Document(collection = "mongo_user")
public class MongoUser {

    @MongoId
    @Field(name = "_id")
    private String id;

    @Indexed(unique = true)
    @Field(name = "user_id")
    private Long userId;

    private List<MongoAISearchInfo> aiSearchInfoList;

    private List<MongoAIUserOnboardingInfo> aiUserOnboardingInfoList;

    @Builder
    public MongoUser(Long userId, List<MongoAISearchInfo> aiSearchInfoList,
                     List<MongoAIUserOnboardingInfo> aiUserOnboardingInfoList) {
        this.userId = userId;
        this.aiSearchInfoList = aiSearchInfoList;
        this.aiUserOnboardingInfoList = aiUserOnboardingInfoList;
    }
}