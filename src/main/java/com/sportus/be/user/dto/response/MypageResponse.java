package com.sportus.be.user.dto.response;

import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.Provider;
import lombok.Builder;

@Builder
public record MypageResponse(
        String profileImageUrl,
        String nickname,
        Provider provider
) {
    public static MypageResponse from(User user) {
        return MypageResponse.builder()
                .profileImageUrl(user.getProfileImageUrl())
                .nickname(user.getNickname())
                .provider(user.getProvider())
                .build();
    }
}