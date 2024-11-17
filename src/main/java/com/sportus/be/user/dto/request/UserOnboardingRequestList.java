package com.sportus.be.user.dto.request;

import com.sportus.be.recommend.domain.MongoUser;
import com.sportus.be.user.domain.User;
import java.util.List;

public record UserOnboardingRequestList(
        List<UserOnboardingRequest> userOnboardingRequestList
) {

    public MongoUser toMongoUser(User user) {
        return MongoUser.builder()
                .userId(user.getId())
                .aiUserOnboardingInfoList(userOnboardingRequestList.stream()
                        .map(UserOnboardingRequest::toMongoEntity)
                        .toList())
                .build();
    }
}
