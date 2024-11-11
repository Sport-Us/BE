package com.sportus.be.auth.dto;

import com.sportus.be.user.domain.type.Role;
import java.util.Collection;
import java.util.Map;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.core.user.DefaultOAuth2User;

/**
 * DefaultOAuth2User를 상속하고, email과 role 필드를 추가로 가진다.
 */
@Getter
public class CustomOAuth2User extends DefaultOAuth2User {

    private final String email;
    private final Role role;
    private final Long userId;

    public CustomOAuth2User(Collection<? extends GrantedAuthority> authorities,
                            Map<String, Object> attributes, String nameAttributeKey,
                            String email, Role role, Long userId) {
        super(authorities, attributes, nameAttributeKey);
        this.email = email;
        this.role = role;
        this.userId = userId;
    }
}