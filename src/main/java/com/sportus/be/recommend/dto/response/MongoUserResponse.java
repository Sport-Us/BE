package com.sportus.be.recommend.dto.response;

import com.sportus.be.recommend.domain.MongoUser;
import java.util.List;

public record MongoUserResponse(
        Long userId,
        List<MongoAISearchInfoResponse> aiSearchInfoList,
        List<MongoAIUserOnboardingInfoResponse> aiUserOnboardingInfoList
) {

    public static MongoUserResponse from(MongoUser domain) {
        return new MongoUserResponse(
                domain.getUserId(),
                domain.getAiSearchInfoList() != null ?
                        domain.getAiSearchInfoList().stream()
                                .map(MongoAISearchInfoResponse::from)
                                .toList() : List.of(), // 빈 리스트
                domain.getAiUserOnboardingInfoList() != null ?
                        domain.getAiUserOnboardingInfoList().stream()
                                .map(MongoAIUserOnboardingInfoResponse::from)
                                .toList() : List.of()  // 빈 리스트
        );
    }
}
