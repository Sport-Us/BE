package com.sportus.be.user.domain.type;

import java.util.Collection;
import java.util.Collections;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

@Getter
public enum Role {
    ADMIN,
    USER,
    ;

    public Collection<? extends GrantedAuthority> getAuthority() {
        return Collections.singletonList(new SimpleGrantedAuthority(this.name()));
    }
}