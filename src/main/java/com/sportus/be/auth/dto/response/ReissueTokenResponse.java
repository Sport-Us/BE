package com.sportus.be.auth.dto.response;

public record ReissueTokenResponse(
        String accessToken
) {

    public static ReissueTokenResponse from(String accessToken) {
        return new ReissueTokenResponse(accessToken);
    }
}
