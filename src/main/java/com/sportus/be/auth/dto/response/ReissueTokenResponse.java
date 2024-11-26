package com.sportus.be.auth.dto.response;

public record ReissueTokenResponse(
        String accessToken,
        String refreshToken
) {

    public static ReissueTokenResponse from(String accessToken, String refreshToken) {
        return new ReissueTokenResponse(accessToken, refreshToken);
    }
}
