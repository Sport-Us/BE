package com.sportus.be.recommend.dto.response;

import java.util.List;

public record MongoUserResponseList(
        List<MongoUserResponse> mongoUserResponseList
) {

    public static MongoUserResponseList from(List<MongoUserResponse> mongoUserResponseList) {
        return new MongoUserResponseList(mongoUserResponseList);
    }
}
