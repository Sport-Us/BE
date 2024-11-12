package com.sportus.be.auth.application;

import com.sportus.be.auth.util.JwtTokenProvider;
import com.sportus.be.user.application.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserService userService;
    private final JwtTokenProvider jwtTokenProvider;

    public String getTestToken(Long userId) {
        return jwtTokenProvider.createAccessToken(userService.getUserById(userId));
    }
}