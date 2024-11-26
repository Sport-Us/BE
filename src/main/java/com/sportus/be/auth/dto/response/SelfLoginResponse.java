package com.sportus.be.auth.dto.response;

public record SelfLoginResponse(
        String accessToken,
        String refreshToken,
        Boolean isOnboarded
) {

    public static SelfLoginResponse of(String accessToken, String refreshToken, Boolean isOnboarded) {
        return new SelfLoginResponse(accessToken, refreshToken, isOnboarded);
    }
}
