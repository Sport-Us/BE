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
@Document(collection = "ai_search_info")
public class MongoAISearchInfo {

    @MongoId
    @Field(name = "_id")
    private String id;

    @Field(name = "place_id")
    private Long placeId;

    @Field(name = "is_facility")
    private Boolean isFacility;

    @Field(name = "place_category")
    private String placeCategory;

    @Builder
    public MongoAISearchInfo(Long placeId, Boolean isFacility, String placeCategory) {
        this.placeId = placeId;
        this.isFacility = isFacility;
        this.placeCategory = placeCategory;
    }
}