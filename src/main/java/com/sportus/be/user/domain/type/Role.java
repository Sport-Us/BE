package com.sportus.be.user.domain.type;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
@RequiredArgsConstructor
public enum Role {
    ADMIN("ROLE_ADMIN"),
    USER("ROLE_USER"),;

    private final String key;

    public Collection<? extends GrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.name()));
    }

    public static Role from(String key) {
        for (Role role : values()) {
            if (role.key.equals(key)) {
                return role;
            }
        }
        throw new IllegalArgumentException("Invalid Role key: " + key);
    }
}