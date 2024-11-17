package com.sportus.be.auth.dto.response;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.sportus.be.user.domain.type.Gender;
import com.sportus.be.user.domain.type.Provider;
import java.time.LocalDate;
import lombok.extern.slf4j.Slf4j;

@Slf4j
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
            String name,
            String gender,
            String birthday,
            String birthyear
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

    @Override
    public Gender getGender() {
        if (this.response.gender.equals("M")) {
            return Gender.MALE;
        } else if (this.response.gender.equals("F")) {
            return Gender.FEMALE;
        } else {
            return Gender.UNKNOWN;
        }
    }

    @Override
    public LocalDate getBirthDate() {
        log.info("response.birthday(): {}", response.birthday());
        log.info("response.birthyear(): {}", response.birthyear());

        return LocalDate.of(
                Integer.parseInt(response.birthyear()),
                Integer.parseInt(response.birthday().substring(0, 2)),
                Integer.parseInt(response.birthday().substring(3, 5))
        );
    }
}