package com.sportus.be.auth.application;

import static com.sportus.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.sportus.be.auth.dto.response.SelfLoginResponse;
import com.sportus.be.auth.util.JwtTokenProvider;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.Provider;
import com.sportus.be.user.exception.UserNotFoundException;
import com.sportus.be.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    private final UserRepository userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public SelfLoginResponse login(
            String email, String password
    ) {
        User user = userService.findByEmailAndProvider(email, Provider.SELF)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }

        // 토큰 생성
        String accessToken = jwtTokenProvider.createAccessToken(user);
        String refreshToken = jwtTokenProvider.createRefreshToken(user);

        return SelfLoginResponse.of(accessToken, refreshToken, user.getIsOnboarded());
    }
}
