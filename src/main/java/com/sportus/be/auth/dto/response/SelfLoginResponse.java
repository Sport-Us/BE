package com.sportus.be.auth.dto.response;

public record SelfLoginResponse(
        String accessToken,
        Boolean isOnboarded
) {

    public static SelfLoginResponse of(String accessToken, Boolean isOnboarded) {
        return new SelfLoginResponse(accessToken, isOnboarded);
    }
}
