package com.sportus.be.auth.handler;

import com.sportus.be.auth.dto.CustomOAuth2User;
import com.sportus.be.auth.util.JwtTokenProvider;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.exception.UserNotFoundException;
import com.sportus.be.user.exception.errorcode.UserErrorCode;
import com.sportus.be.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Component
@RequiredArgsConstructor
public class OAuth2LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler {

    @Value("${app.oauth2.successRedirectUri}")
    private String redirectUri;
    @Value("${app.onboarding.uri}")
    private String onboardingUri;

    private final JwtTokenProvider tokenProvider;
    private final UserRepository userRepository;

    @Override
    public void onAuthenticationSuccess(
            HttpServletRequest request, HttpServletResponse response, Authentication authentication
    ) throws IOException {
        CustomOAuth2User oAuth2User = (CustomOAuth2User) authentication.getPrincipal();
        String targetUrl = determineTargetUrl(oAuth2User, response);

        if (response.isCommitted()) {
            log.debug("Response has already been committed");
            return;
        }

        getRedirectStrategy().sendRedirect(request, response, targetUrl);
    }


    private String determineTargetUrl(CustomOAuth2User oAuth2User, HttpServletResponse response) {
        // JWT 생성
        String accessToken = tokenProvider.createAccessToken(oAuth2User);
        tokenProvider.createRefreshToken(oAuth2User, response);

        User user = userRepository.findById(oAuth2User.getUserId())
                .orElseThrow(() -> new UserNotFoundException(UserErrorCode.USER_NOT_FOUND));

        if (!user.getIsOnboarded()) {
            redirectUri = onboardingUri;
        }

        String uriWithTokens = UriComponentsBuilder.fromHttpUrl(redirectUri)
                .queryParam("accessToken", accessToken)
                .toUriString();

        return UriComponentsBuilder.fromUriString(uriWithTokens)
                .build().toUriString();
    }
}