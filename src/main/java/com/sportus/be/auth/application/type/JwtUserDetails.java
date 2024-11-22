package com.sportus.be.auth.application.type;

import com.sportus.be.user.domain.type.Role;
import io.jsonwebtoken.Claims;
import lombok.Builder;

@Builder
public record JwtUserDetails(
        Long userId,
        Role role
) {
    public static JwtUserDetails fromClaim(Claims claims) {
        return JwtUserDetails.builder()
                .userId(Long.valueOf(claims.getSubject()))
                .role(Role.valueOf(claims.get("role").toString()))
                .build();
    }
}
