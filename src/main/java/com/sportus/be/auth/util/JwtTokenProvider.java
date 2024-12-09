package com.sportus.be.auth.util;

import static com.sportus.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.sportus.be.auth.application.type.JwtProperties;
import com.sportus.be.auth.application.type.JwtUserDetails;
import com.sportus.be.auth.dto.CustomOAuth2User;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.exception.UserNotFoundException;
import com.sportus.be.user.repository.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Header;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.JwtBuilder;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.annotation.PostConstruct;
import java.security.Key;
import java.util.Date;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class JwtTokenProvider {

    private final JwtProperties jwtProperties;
    private final UserRepository userRepository;

    private Key key;
    private static final String USER_ROLE = "role";

    @PostConstruct
    public void setKey() {
        byte[] keyBytes = Decoders.BASE64.decode(jwtProperties.secretKey());
        this.key = Keys.hmacShaKeyFor(keyBytes);
    }

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(Object user) {
        return createToken(user, jwtProperties.accessTokenExpiration());
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken(Object user) {
        return createToken(user, jwtProperties.refreshTokenExpiration());
    }

    /**
     * JWT 토큰 생성 메소드
     */
    private String createToken(Object user, long expiration) {
        long now = (new Date()).getTime();
        Date validity = new Date(now + expiration);

        JwtBuilder builder = Jwts.builder()
                .setIssuedAt(new Date(now))
                .setExpiration(validity)
                .setIssuer(jwtProperties.issuer())
                .setHeaderParam(Header.TYPE, Header.JWT_TYPE)
                .signWith(key, SignatureAlgorithm.HS512);

        if (user instanceof User regularUser) {
            builder.setSubject(regularUser.getId().toString())
                    .addClaims(Map.of(USER_ROLE, regularUser.getRole().name()));
        } else if (user instanceof CustomOAuth2User oAuth2User) {
            builder.setSubject(oAuth2User.getUserId().toString())
                    .addClaims(Map.of(USER_ROLE, oAuth2User.getRole().name()));
        } else {
            throw new IllegalArgumentException("Unsupported user type: " + user.getClass().getName());
        }

        return builder.compact();
    }


    public boolean validateToken(final String token) {
        try {
            Jws<Claims> claims = Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token);

            log.info("login user: {}", claims.getBody().getSubject());
            return claims.getBody().getExpiration().after(new Date());
        } catch (Exception e) {
            log.error("Token validation error: ", e);
            return false;
        }
    }

    public User getUser(String token) {
        Long id = Long.parseLong(Jwts.parserBuilder().setSigningKey(key).build()
                .parseClaimsJws(token).getBody().getSubject());

        log.info("in getUser() id: {}", id);

        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));
    }

    public JwtUserDetails getJwtUserDetails(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        return JwtUserDetails.fromClaim(claims);
    }
}