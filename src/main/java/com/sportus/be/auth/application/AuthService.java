package com.sportus.be.auth.application;

import static com.sportus.be.auth.exception.errorcode.AuthErrorCode.INVALID_EMAIL;
import static com.sportus.be.user.exception.errorcode.UserErrorCode.USER_NOT_FOUND;

import com.sportus.be.auth.dto.request.CreateUserRequest;
import com.sportus.be.auth.dto.request.SignInRequest;
import com.sportus.be.auth.exception.InvalidEmailException;
import com.sportus.be.auth.exception.TokenNotValidException;
import com.sportus.be.auth.exception.errorcode.AuthErrorCode;
import com.sportus.be.auth.util.JwtTokenProvider;
import com.sportus.be.global.application.AWSStorageService;
import com.sportus.be.user.domain.User;
import com.sportus.be.user.domain.type.Provider;
import com.sportus.be.user.exception.UserNotFoundException;
import com.sportus.be.user.repository.UserRepository;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final AWSStorageService awsStorageService;
    private final PasswordEncoder passwordEncoder;
    private final LoginService loginService;
    private final JwtTokenProvider jwtTokenProvider;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;
    @Value("${app.oauth2.successRedirectUri}")
    private String successRedirectUri;

    private static final String DUMMY_PROFILE_IMAGE_URL = "/profile/ic_profile.svg";

    public String getTestToken(Long userId) {
        return jwtTokenProvider.createAccessToken(userRepository.findById(userId).orElseThrow());
    }

    public void signUp(CreateUserRequest request, MultipartFile file) {
        if (!userRepository.existsByEmailAndProvider(request.email(), Provider.SELF)) {
            String imagePath = "https://" + bucket + ".s3.ap-northeast-2.amazonaws.com" + DUMMY_PROFILE_IMAGE_URL;

            if (file != null) {
                imagePath = awsStorageService.uploadFile(file, "profile");
            }

            User user = request.toUser(passwordEncoder, imagePath);
            userRepository.save(user);
        } else {
            throw new InvalidEmailException(INVALID_EMAIL);
        }
    }

    public boolean validateNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public void signIn(SignInRequest signUpRequest, HttpServletResponse response) {
        loginService.login(signUpRequest.email(), signUpRequest.password(), response);
    }

    public void reIssueToken(String refreshToken, HttpServletResponse response) {

        if (!jwtTokenProvider.validateToken(refreshToken)) {
            throw new TokenNotValidException(AuthErrorCode.TOKEN_NOT_VALID);
        }

        User user = jwtTokenProvider.getUser(refreshToken);

        String accessToken = jwtTokenProvider.createAccessToken(user);
        jwtTokenProvider.createRefreshToken(user, response);

        // URI에 토큰 추가
        String uriWithTokens = UriComponentsBuilder.fromHttpUrl(successRedirectUri)
                .queryParam("accessToken", accessToken)
                .toUriString();

        try {
            response.sendRedirect(uriWithTokens);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Transactional
    public void withdraw(Long userId) {
        userRepository.findById(userId)
                .ifPresentOrElse(userRepository::delete,
                        () -> {
                            throw new UserNotFoundException(USER_NOT_FOUND);
                        });
    }
}