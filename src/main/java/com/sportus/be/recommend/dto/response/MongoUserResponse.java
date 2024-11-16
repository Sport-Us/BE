package com.sportus.be.recommend.dto.response;

import com.sportus.be.recommend.domain.MongoUser;
import java.util.List;

public record MongoUserResponse(
        Long userId,
        List<MongoAISearchInfoResponse> aiSearchInfoList
) {

    public static MongoUserResponse from(MongoUser domain) {
        return new MongoUserResponse(
                domain.getUserId(),
                domain.getAiSearchInfoList() != null ?
                        domain.getAiSearchInfoList().stream()
                                .map(MongoAISearchInfoResponse::from)
                                .toList() : List.of()
        );
    }
}
