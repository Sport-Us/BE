package com.sportus.be.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sportus.be.user.domain.type.Provider;

public record NaverOAuth2UserInfo(
        Response response
) implements OAuth2UserInfo {

    @JsonIgnoreProperties(ignoreUnknown = true)
    @JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
    public record Response(
            String id,
            String nickname,
            String profileImage,
            String email,
            String name
    ) {
    }

    @Override
    public String getSocialId() {
        return response.id();
    }

    @Override
    public String getNickname() {
        return response.nickname();
    }


    @Override
    public String getName() {
        return response.name();
    }

    @Override
    public String getProfileImage() {
        return response.profileImage();
    }

    @Override
    public String getEmail() {
        return response.email();
    }

    @Override
    public Provider getOAuthProvider() {
        return Provider.NAVER;
    }
}