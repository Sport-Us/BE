package com.sportus.be.auth.application;

import static com.sportus.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.sportus.be.auth.util.JwtTokenProvider;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.exception.UserNotFoundException;
import com.sportus.be.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class LoginService {

    @Value("${app.onboarding.uri}")
    private String onboardingUri;
    @Value("${app.oauth2.successRedirectUri}")
    private String successRedirectUri;

    private final UserRepository userService;
    private final JwtTokenProvider jwtTokenProvider;
    private final PasswordEncoder passwordEncoder;

    public void login(String email, String password, HttpServletResponse response) {

        User user = userService.findByEmail(email)
                .orElseThrow(() -> new UserNotFoundException(USER_NOT_FOUND));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new IllegalArgumentException("Password does not match");
        }

        //토큰 생성 후 리턴
        String accessToken = jwtTokenProvider.createAccessToken(user);
        jwtTokenProvider.createRefreshToken(user, response);

        response.setHeader("Authorization", "Bearer " + accessToken);

        try {
            if (user.getIsOnboarded()) {
                response.sendRedirect(successRedirectUri); // 온보딩이 완료되었다면 메인 페이지로 리다이렉트
            } else {
                response.sendRedirect(onboardingUri); // 온보딩이 완료되지 않았다면 리다이렉트
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
