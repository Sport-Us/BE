package com.sportus.be.auth.dto.response;

import com.sportus.be.user.domain.type.Provider;

public interface OAuth2UserInfo {

    String getSocialId(); //소셜 식별 값 : 구글 - "sub", 카카오 - "id", 네이버 - "id"
    String getNickname();
    String getProfileImage();
    String getName();
    String getEmail();
    Provider getOAuthProvider();
}