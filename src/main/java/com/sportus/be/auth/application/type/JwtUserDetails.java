package com.sportus.be.auth.application.type;

import static com.sportus.be.user.domain.type.Role.ROLE_USER;

import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.Role;
import io.jsonwebtoken.Claims;
import lombok.Builder;

@Builder
public record JwtUserDetails(
        Long userId,
        Role role
) {
    public static JwtUserDetails from(Claims claims) {
        return JwtUserDetails.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(Role.valueOf(claims.get("role").toString()))
                .build();
    }

    public static JwtUserDetails from(User user) {
        return JwtUserDetails.builder()
                .userId(user.getId())
                .role(ROLE_USER)
                .build();
    }
}
